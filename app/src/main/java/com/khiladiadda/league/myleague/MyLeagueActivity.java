package com.khiladiadda.league.myleague;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.leaderboard.myleague.MyLeagueLeaderboardActivity;
import com.khiladiadda.leaderboard.past.PastLeaderboardActivity;
import com.khiladiadda.league.details.LeagueDetailsActivity;
import com.khiladiadda.league.myleague.adapter.MyLeagueLiveAdapter;
import com.khiladiadda.league.myleague.adapter.MyLeaguePastAdapter;
import com.khiladiadda.league.myleague.adapter.MyLeagueUpcomingAdapter;
import com.khiladiadda.league.myleague.interfaces.IMyLeaguePresenter;
import com.khiladiadda.league.myleague.interfaces.IMyLeagueView;
import com.khiladiadda.league.team.MyTeamDialog;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.network.model.response.MyLeagueResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyLeagueActivity extends BaseActivity implements IMyLeagueView, IOnItemClickListener, MyLeagueUpcomingAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.btn_live)
    Button mLiveBTN;
    @BindView(R.id.btn_past)
    Button mPastBTN;
    @BindView(R.id.btn_upcoming)
    Button mUpcomingBTN;
    @BindView(R.id.rv_my_league)
    RecyclerView mMyLeagueRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.btn_room_password)
    Button mRoomPwdBTN;

    private IMyLeaguePresenter mPresenter;
    private MyLeagueUpcomingAdapter mUpcomingAdapter;
    private MyLeagueLiveAdapter mLiveAdapter;
    private MyLeaguePastAdapter mPastAdapter;
    private List<LeagueListDetails> mLiveList = null, mPastList = null, mUpcomingList = null;
    private boolean mUpcoming, mLive = true, mPast;
    private String mGameId;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_league;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_my_league);
        mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLiveBTN.setOnClickListener(this);
        mPastBTN.setOnClickListener(this);
        mUpcomingBTN.setOnClickListener(this);
        mRoomPwdBTN.setOnClickListener(this);
        mLiveBTN.setSelected(true);
        setaData();
    }

    private void setaData() {
        Intent intent = getIntent();
        int mFrom = intent.getIntExtra(AppConstant.FROM, 0);
        if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESBGMI) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_LITE_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_bgmi);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESTDM) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_tdm);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESFCS) {
            mGameId = mAppPreference.getString(AppConstant.FF_CLASH_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_ffcs);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESFFMAX) {
            mGameId = mAppPreference.getString(AppConstant.FF_MAX_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_ffmax);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEAGUESPG) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_pg);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESPE) {
            mGameId = mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_pe);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUESFF) {
            mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_ff);
        } else if (mFrom == AppConstant.FROM_APPFLYER_FORMYLEGUEPGNS) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "");
            mActivityNameTV.setText(R.string.text_my_league_pgnw);
            mRoomPwdBTN.setText(R.string.text_pubns_how_room_pasword);
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new MyLeaguePresenter(this);

        mLiveList = new ArrayList<>();
        mPastList = new ArrayList<>();
        mUpcomingList = new ArrayList<>();

        mLiveAdapter = new MyLeagueLiveAdapter(mLiveList);
        mPastAdapter = new MyLeaguePastAdapter(mPastList);
        mUpcomingAdapter = new MyLeagueUpcomingAdapter(mUpcomingList);

        mMyLeagueRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMyLeagueRV.setAdapter(mLiveAdapter);

        mLiveAdapter.setOnItemClickListener(this);
        mPastAdapter.setOnItemClickListener(this);
        mUpcomingAdapter.setOnItemClickListener(this);
        mUpcomingAdapter.setOnItemChildClickListener(this);

        setAnimation();

        if (TextUtils.isEmpty(mGameId)) {
            mNoDataTV.setVisibility(View.VISIBLE);
        } else {
            getData();
        }
    }

    private void setAnimation() {
        ColorDrawable[] color = {new ColorDrawable(Color.MAGENTA), new ColorDrawable(Color.YELLOW)};
        TransitionDrawable trans = new TransitionDrawable(color);
        mRoomPwdBTN.setBackground(trans);
        trans.startTransition(7000); // duration 3 seconds
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_live:
                getType(1);
                break;
            case R.id.btn_past:
                getType(2);
                break;
            case R.id.btn_upcoming:
                getType(3);
                break;
            case R.id.btn_room_password:
                if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_help_room_password_pubgns), false);
                } else {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_help_room_password), false);
                }
                break;
        }
    }

    private void getType(int type) {
        mUpcoming = false;
        mLive = false;
        mPast = false;
        mLiveBTN.setSelected(false);
        mPastBTN.setSelected(false);
        mUpcomingBTN.setSelected(false);
        if (type == 1) {
            mLive = true;
            mLiveBTN.setSelected(true);
        } else if (type == 2) {
            mPast = true;
            mPastBTN.setSelected(true);
        } else if (type == 3) {
            mUpcoming = true;
            mUpcomingBTN.setSelected(true);
        }
        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getMyLeague(mGameId, mUpcoming, mPast, mLive);
        } else {
            Snackbar.make(mLiveBTN, getString(R.string.error_internet), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMyLeagueComplete(MyLeagueResponse responseModel) {
        hideProgress();
        mLiveList.clear();
        mPastList.clear();
        mUpcomingList.clear();
        mNoDataTV.setVisibility(View.GONE);
        mRoomPwdBTN.setVisibility(View.GONE);
        if (mLive) {
            if (responseModel.getResponse().getLive().size() > 0) {
                mLiveList.addAll(responseModel.getResponse().getLive());
                mMyLeagueRV.setAdapter(mLiveAdapter);
                mLiveAdapter.notifyDataSetChanged();
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
            }
        } else if (mPast) {
            if (responseModel.getResponse().getPast().size() > 0) {
                mPastList.addAll(responseModel.getResponse().getPast());
                mMyLeagueRV.setAdapter(mPastAdapter);
                mPastAdapter.notifyDataSetChanged();
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
            }
        } else if (mUpcoming) {
            if (responseModel.getResponse().getUpcoming().size() > 0) {
                mUpcomingList.addAll(responseModel.getResponse().getUpcoming());
                mMyLeagueRV.setAdapter(mUpcomingAdapter);
                mUpcomingAdapter.notifyDataSetChanged();
                mRoomPwdBTN.setVisibility(View.VISIBLE);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMyLeagueFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onMyTeamComplete(MyTeamResponse responseModel) {
        hideProgress();
        new MyTeamDialog(this, responseModel.getResponse().getTeamName(), responseModel.getResponse().getTeamCode(), responseModel.getResponse().getTeamSlot(), responseModel.getResponse().getUsers());
    }

    @Override
    public void onMyTeamFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        LeagueListDetails detail;
        if (mLive) {
            detail = mLiveList.get(position);
        } else if (mPast) {
            detail = mPastList.get(position);
        } else {
            detail = mUpcomingList.get(position);
        }
        switch (view.getId()) {
            case R.id.cv_live:
                if (TextUtils.isEmpty(detail.getRoomId()) && TextUtils.isEmpty(detail.getRoomPassword())) {
                    if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                        AppUtilityMethods.showMsg(this, getString(R.string.text_credential_pubgns_pending), false);
                    } else {
                        AppUtilityMethods.showMsg(this, getString(R.string.text_credential_pending), false);
                    }
                } else {
                    if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                        AppDialog.showLiveCredentialDialog(this, detail.getRoomId(), detail.getRoomPassword(), 1);
                    } else {
                        AppDialog.showLiveCredentialDialog(this, detail.getRoomId(), detail.getRoomPassword(), 2);
                    }
                }
                break;
            case R.id.cv_past:
                if (detail.cancelled) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_league_cancelled_msg), false);
                } else if (detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, "")) || detail.getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.CLASHROYALE_ID, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, ""))) {
                    Intent i = new Intent(this, MyLeagueLeaderboardActivity.class);
                    i.putExtra(AppConstant.ID, detail.getId());
                    i.putExtra(AppConstant.DATA, detail);
                    startActivity(i);
                } else {
                    Intent i = new Intent(this, PastLeaderboardActivity.class);
                    i.putExtra(AppConstant.ID, detail.getId());
                    startActivity(i);
                }
                break;
            case R.id.cv_upcoming:
                Intent intent = new Intent(this, LeagueDetailsActivity.class);
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_MYLEAGUE);
                intent.putExtra(AppConstant.ID, mGameId);
                intent.putExtra(AppConstant.DATA, detail);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onViewTeamClicked(int position) {
        LeagueListDetails detail = mUpcomingList.get(position);
        String mLeagueId = mUpcomingList.get(position).getId();
        if (detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, "")) || detail.getGameCategoryId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, ""))) {
            Intent intent = new Intent(this, LeagueDetailsActivity.class);
            intent.putExtra(AppConstant.FROM, AppConstant.FROM_MYLEAGUE);
            intent.putExtra(AppConstant.ID, mGameId);
            intent.putExtra(AppConstant.DATA, detail);
            startActivity(intent);
        } else {
            if (new NetworkStatus(this).isInternetOn()) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.getMyTeam(mLeagueId);
            } else {
                Snackbar.make(mLiveBTN, getString(R.string.error_internet), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }

}