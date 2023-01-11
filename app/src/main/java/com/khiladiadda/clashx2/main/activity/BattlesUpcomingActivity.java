package com.khiladiadda.clashx2.main.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.BattlePointsActivity;
import com.khiladiadda.clashx2.football.activity.FootballBattleScoreActivity;
import com.khiladiadda.clashx2.football.activity.FootballSeletedActivity;
import com.khiladiadda.clashx2.kabaddi.activity.KabaadiBattlesScoreActivity;
import com.khiladiadda.clashx2.kabaddi.activity.KabaddiSelectedPlayerActivity;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.adapter.MyBattlesAdpater;
import com.khiladiadda.clashx2.cricket.createbattle.CreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattleView;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;

import butterknife.BindView;

public class BattlesUpcomingActivity extends BaseActivity implements ICreateBattleView, MyBattlesAdpater.IOnItemChildClickListener, IHTHBattleView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_one)
    ImageView mTeamOneIV;
    @BindView(R.id.iv_two)
    ImageView mTeamTwoIV;
    @BindView(R.id.tv_team_one)
    TextView mTeamOneTV;
    @BindView(R.id.tv_team_two)
    TextView mTeamTwoTV;
    @BindView(R.id.tv_date)
    TextView mDateTV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.tv_name)
    TextView mNameTV;
    @BindView(R.id.tv_time_left)
    TextView mTimeLeftTV;
    @BindView(R.id.tv_calculate)
    TextView mCalculateTV;
    @BindView(R.id.rv_battle)
    RecyclerView mBattleRV;
    @BindView(R.id.tv_amount)
    TextView mAmountTV;
    @BindView(R.id.tv_time)
    TextView mTimeTV;
    @BindView(R.id.ll_amount)
    LinearLayout mAmountLL;
    @BindView(R.id.tv_points)
    TextView mPointsTV;

    private String mCancelBattleID;
    private ICreateBattlePresenter mPresenter;
    private MyBattlesAdpater mMYBattleAdapter;
    private ArrayList<BattlesDeatilsHTH> mMyBattleList = new ArrayList<>();
    private HTHResponseDetails mMatchDetail;
    private int mFrom;
    private int mMatch_Type;
    private IHTHBattlePresenter mCancelPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_battles_upcoming;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverLiveMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_NOTIFY"));
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mCalculateTV.setOnClickListener(this);
        mAmountLL.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mCancelPresenter = new HTHPresenter(this);
        mFrom = getIntent().getIntExtra(AppConstant.FROM, 0);
        mMatch_Type = getIntent().getIntExtra(AppConstant.MATCH_TYPE, 0);
        mMatchDetail = getIntent().getParcelableExtra(AppConstant.DATA);
        mPresenter = new CreateBattlePresenter(this);
        mMYBattleAdapter = new MyBattlesAdpater(mMyBattleList, mAppPreference.getProfileData().getId(), mMatchDetail.isIs_lines_up(), mFrom);
        mBattleRV.setLayoutManager(new LinearLayoutManager(this));
        mBattleRV.setAdapter(mMYBattleAdapter);
        mMYBattleAdapter.setOnItemChildClickListener(this);
        setData();
    }

    private void setData() {
        mDateTV.setText(("Date: " + AppUtilityMethods.getConvertDateFacts(mMatchDetail.getStartDateTime())));
        mNameTV.setText(mMatchDetail.getSeries().getName());
        TeamHTH homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeam();
        TeamHTH awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeam();
        mTeamOneTV.setText(homeTeam.getName());
        Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamOneIV);
        mTeamTwoTV.setText(awayTeam.getName());
        Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamTwoIV);
        mTimeLeftTV.setText(String.format("Starts In: %s", AppUtilityMethods.getBattleRemainingTime(mMatchDetail.getStartDateTime())));
        mTimeTV.setText("Time: " + AppUtilityMethods.getConvertTimeMatch(mMatchDetail.getStartDateTime()));
        mAmountTV.setText("Win upto " + mMatchDetail.getHighestBattle());
    }

    private void getData() {
        showProgress(getString(R.string.txt_progress_authentication));
        mMyBattleList.clear();
        mPresenter.getMyBattles((mMatchDetail.getId()), "", true, false, false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_calculate:
                startActivity(new Intent(this, BattlePointsActivity.class));
                break;
            case R.id.ll_amount:
                AppUtilityMethods.showInfo(this, mPointsTV, getString(R.string.maximum_battle));
                break;
        }
    }

    @Override
    public void onJoinComplete(CreateBattleResponse responseModel) {

    }

    @Override
    public void onJoinFailure(ApiError error) {

    }

    @Override
    public void onUpdateComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdateFailure(ApiError error) {

    }

    @Override
    public void onAcceptComplete(CreateBattleResponse responseModel) {

    }

    @Override
    public void onAcceptFailure(ApiError error) {

    }

    @Override
    public void onAllBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onAllBattlesFailure(ApiError error) {

    }

    @Override
    public void onMyBattlesComplete(BattleResponseHTH responseModel) {
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mMyBattleList.addAll(responseModel.getResponse());
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mMYBattleAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onMyBattlesFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFetchLegues(HTHMainResponse responseModel) {

    }

    @Override
    public void onFetchLegues(ApiError error) {

    }

    @Override
    public void onUpdatePLayers(BaseResponse response) {

    }

    @Override
    public void onUpdatePLayerError(ApiError error) {

    }

    @Override
    public void onPlayerStatus(HTHMainResponse responseModel) {

    }

    @Override
    public void onPlayerStatusError(ApiError error) {

    }

    @Override
    public void onUpdatePlayer(int position) {
        if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
            if (mMatch_Type == 3) {
                Intent createBattle = new Intent(this, KabaadiBattlesScoreActivity.class);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            } else if (mMatch_Type == 2) {
                Intent createBattle = new Intent(this, FootballBattleScoreActivity.class);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            } else {
                Intent createBattle = new Intent(this, BattlesScoreActivity.class);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            }
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
            if (mMatch_Type == 3) {
                Intent createBattle = new Intent(this, KabaadiBattlesScoreActivity.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            } else if (mMatch_Type == 2) {
                Intent createBattle = new Intent(this, FootballBattleScoreActivity.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            } else {
                Intent createBattle = new Intent(this, BattlesScoreActivity.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
                createBattle.putExtra(AppConstant.DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
                startActivity(createBattle);
            }
        } else {
            if (mMatch_Type == 3) {
                Intent createBattle = new Intent(this, KabaddiSelectedPlayerActivity.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
                createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                startActivityForResult(createBattle, AppConstant.REQUEST_ADD_WALLET);
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
            } else if (mMatch_Type == 2) {
                Intent createBattle = new Intent(this, FootballSeletedActivity.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
                createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                startActivityForResult(createBattle, AppConstant.REQUEST_ADD_WALLET);
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
            } else {
                Intent createBattle = new Intent(this, SelectedPlayers.class);
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
                createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
                createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
                createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
                createBattle.putExtra(AppConstant.CATEGORY, getIntent().getStringExtra(AppConstant.CATEGORY));
                startActivityForResult(createBattle, AppConstant.REQUEST_ADD_WALLET);
                createBattle.putExtra(AppConstant.MATCH_TYPE, mMatch_Type);
            }
        }
    }

    @Override
    public void onCancelCombo(int position) {
        mCancelBattleID = mMyBattleList.get(position).getId();
        acceptAlert(this);
    }

    private void acceptAlert(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            showProgress(getString(R.string.txt_progress_authentication));
            mCancelPresenter.onCancelBattle(mCancelBattleID);
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onGetHTHMatchListComplete(HTHMainResponse responseModel) {

    }

    @Override
    public void onGetHTHMatchListFailure(ApiError error) {

    }

    @Override
    public void onCancelBattle(HTHCancelResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mAppPreference.setProfileData(responseModel.getProfile());
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
            getData();
        }
    }

    @Override
    public void onCancelBattleFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetResultBattle(Result responseModel) {

    }

    @Override
    public void onResultBattleFailure(ApiError error) {

    }

    @Override
    public void onMatchStatus(HTHMainResponse responseModel) {

    }

    @Override
    public void onMatchStatusError(ApiError error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private BroadcastReceiver mHTHNotificationReceiverLiveMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHLIVEREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    back();
                }
            }
        }
    };

    private BroadcastReceiver mHTHNotificationReceiverMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHMATCHES)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    finish();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverLiveMatches);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_ADD_WALLET)
            if (resultCode == RESULT_OK) {
                back();
            }
    }

    private void back() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}