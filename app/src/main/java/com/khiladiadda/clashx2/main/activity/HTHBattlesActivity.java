package com.khiladiadda.clashx2.main.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.KhiladiAddaApp;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.BattleHelpActivity;
import com.khiladiadda.battle.BattlePointsActivity;
import com.khiladiadda.clashx2.football.activity.FootballSeletedActivity;
import com.khiladiadda.clashx2.football.createbattle.FootballCreateTeamActivity;
import com.khiladiadda.clashx2.kabaddi.activity.KabaddiSelectedPlayerActivity;
import com.khiladiadda.clashx2.kabaddi.createbattle.KabaddiCreateTeamActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.adapter.MyBattlesAdpater;
import com.khiladiadda.clashx2.cricket.createbattle.CreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.CreateTeamActivity;
import com.khiladiadda.clashx2.cricket.createbattle.adapter.AllBattlesAdapter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattleView;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.Coins;
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
import com.khiladiadda.utility.NetworkStatus;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;

public class HTHBattlesActivity extends BaseActivity implements ICreateBattleView, IHTHBattleView, AllBattlesAdapter.IOnItemChildClickListener, MyBattlesAdpater.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;
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
    @BindView(R.id.tv_time)
    TextView mTimeTV;
    @BindView(R.id.tv_time_left)
    TextView mTimeLeftTV;
    @BindView(R.id.tv_how_play)
    TextView mHowPlayTV;
    @BindView(R.id.tv_calculate)
    TextView mCalculateTV;
    @BindView(R.id.rv_battle)
    RecyclerView mBattleRV;
    @BindView(R.id.rl_refresh)
    RelativeLayout mRefreshTV;
    @BindView(R.id.rl_create_battle)
    RelativeLayout mCreateBattleRL;
    @BindView(R.id.btn_category)
    TextView mCategoryBTN;
    @BindView(R.id.btn_category_mybattles)
    TextView mCategoryMyBattleBTN;
    @BindView(R.id.ll_sort)
    LinearLayout mSortLL;
    @BindView(R.id.hsv_sort)
    HorizontalScrollView mHSortHSV;
    @BindView(R.id.cv_trending)
    CardView mTrendingCV;
    @BindView(R.id.tv_amount)
    TextView mAmountTV;
    @BindView(R.id.tv_points)
    TextView mPointsTV;
    @BindView(R.id.ll_newest)
    LinearLayout mNewestBattlesLL;
    @BindView(R.id.ll_highearning)
    LinearLayout mHighEarningBattlesLL;
    @BindView(R.id.ll_lowearning)
    LinearLayout mLowEaBattlesLL;
    @BindView(R.id.rv_mybattle)
    RecyclerView mMyBattleRV;
    @BindView(R.id.iv_click_image)
    ImageView mImageIV;
    @BindView(R.id.ll_amount)
    LinearLayout mAmountLL;
    @BindView(R.id.ll_inner_newest)
    LinearLayout mNewestLL;

    private String refresh, mCancelBattleID;
    private HTHResponseDetails mMatchDetail;
    private ICreateBattlePresenter mPresenter;
    private AllBattlesAdapter mBattleAdapter;
    private IHTHBattlePresenter mMatchPresenter;
    private ArrayList<BattlesDeatilsHTH> mBattleList = new ArrayList<>();
    private MyBattlesAdpater mMYBattleAdapter;
    private ArrayList<BattlesDeatilsHTH> mMyBattleList = new ArrayList<>();
    private int mBattlePosition, mType, mFromSelectedPlayer, mFromMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiver, new IntentFilter("com.khiladiadda.HTH_NOTIFY"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_NOTIFY"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverLiveMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hthbattles;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mHowPlayTV.setOnClickListener(this);
        mCalculateTV.setOnClickListener(this);
        mRefreshTV.setOnClickListener(this);
        mCreateBattleRL.setOnClickListener(this);
        mCategoryBTN.setOnClickListener(this);
        mCategoryMyBattleBTN.setOnClickListener(this);
        mSortLL.setOnClickListener(this);
        mPointsTV.setOnClickListener(this);
        mNewestLL.setOnClickListener(this);
        mHighEarningBattlesLL.setOnClickListener(this);
        mLowEaBattlesLL.setOnClickListener(this);
        mCategoryBTN.setSelected(true);
    }

    @Override
    protected void initVariables() {
        mMatchPresenter = new HTHPresenter(this);
        mPresenter = new CreateBattlePresenter(this);
        mMatchDetail = getIntent().getParcelableExtra(AppConstant.BATTLE_DATA);
        mFromMatches = getIntent().getIntExtra(AppConstant.FROM, AppConstant.FROM_CRICKET);
        mBattleAdapter = new AllBattlesAdapter(mBattleList, mAppPreference.getProfileData().getId());
        mBattleRV.setLayoutManager(new LinearLayoutManager(this));
        mBattleRV.setMotionEventSplittingEnabled(false);
        mBattleRV.setAdapter(mBattleAdapter);
        mBattleAdapter.setOnItemChildClickListener(this);
        setData();
    }

    private void setData() {
        mDateTV.setText(("Date: " + AppUtilityMethods.getConvertDateFacts(mMatchDetail.getStartDateTime())));
        TeamHTH homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeam();
        TeamHTH awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeam();
//        mNameTV.setText(mMatchDetail.getSeries().getName());
        mNameTV.setText(homeTeam.getName() + " vs " + awayTeam.getName());
        mTeamOneTV.setText(homeTeam.getName());
        Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamOneIV);
        mTeamTwoTV.setText(awayTeam.getName());
        Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamTwoIV);
        mTimeLeftTV.setText(String.format("Starts In: %s", AppUtilityMethods.getBattleRemainingTime(mMatchDetail.getStartDateTime())));
        mTimeTV.setText("Time: " + AppUtilityMethods.getConvertTimeMatch(mMatchDetail.getStartDateTime()));
        setCoin();
    }

    private void getData(String mFrom) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mCategoryBTN.setSelected(true);
            mCategoryMyBattleBTN.setSelected(false);
            mHighEarningBattlesLL.setSelected(false);
            mNewestLL.setSelected(false);
            mLowEaBattlesLL.setSelected(false);
            mImageIV.setVisibility(View.VISIBLE);
            mSortLL.setVisibility(View.VISIBLE);
            if (mFrom.equalsIgnoreCase(AppConstant.NEWESTFILTER)) {
                mPresenter.getALLBattles(mMatchDetail.getId(), "", false, false, false, true);
                mImageIV.setImageResource(R.drawable.ic_clock);
                mSortLL.callOnClick();
                refresh = AppConstant.NEWESTFILTER;
                mNewestLL.setSelected(true);
            } else if (mFrom.equalsIgnoreCase(AppConstant.HIGHFILTER)) {
                mPresenter.getALLBattles(mMatchDetail.getId(), "", false, true, false, false);
                mImageIV.setImageResource(R.drawable.ic_high_earning);
                mSortLL.callOnClick();
                refresh = AppConstant.HIGHFILTER;
                mHighEarningBattlesLL.setSelected(true);
            } else if (mFrom.equalsIgnoreCase(AppConstant.LOWFILTER)) {
                mPresenter.getALLBattles(mMatchDetail.getId(), "", false, false, true, false);
                mImageIV.setImageResource(R.drawable.ic_low_earning);
                mSortLL.callOnClick();
                refresh = AppConstant.LOWFILTER;
                mLowEaBattlesLL.setSelected(true);
            } else if (mFrom.equalsIgnoreCase(AppConstant.MYBATTLES)) {
                mPresenter.getMyBattles(mMatchDetail.getId(), "", true, false, false, false);
                mCategoryMyBattleBTN.setSelected(true);
                mCategoryBTN.setSelected(false);
                mImageIV.setVisibility(View.GONE);
                mSortLL.setVisibility(View.GONE);
                refresh = AppConstant.MYBATTLES;
            } else {
                mPresenter.getALLBattles(mMatchDetail.getId(), "", false, false, false, false);
                mImageIV.setVisibility(View.GONE);
                refresh = "";
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int mHowtoPlay = 2;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_how_play:
                Intent i = new Intent(this, BattleHelpActivity.class);
                if (mFromMatches == 1)
                    i.putExtra(AppConstant.FROM, mHowtoPlay);
                else if (mFromMatches == 2)
                    i.putExtra(AppConstant.FROM, 3);
                else if (mFromMatches == 3)
                    i.putExtra(AppConstant.FROM, 4);
                startActivity(i);
                break;
            case R.id.tv_calculate:
                Intent calculate = new Intent(this, BattlePointsActivity.class);
                if (mFromMatches == 1)
                    calculate.putExtra(AppConstant.FROM, AppConstant.FROM_CRICKET);
                else if (mFromMatches == 2)
                    calculate.putExtra(AppConstant.FROM, AppConstant.FROM_FOOTBALL);
                else if (mFromMatches == 3)
                    calculate.putExtra(AppConstant.FROM, AppConstant.FROM_KABAADI);
                startActivity(calculate);
                break;
            case R.id.rl_create_battle:
                showProgress("");
                if (new NetworkStatus(this).isInternetOn()) {
                    mType = 1;
                    mCreateBattleRL.setClickable(false);
                    mMatchPresenter.getHTHMatchList(mMatchDetail.getId(), 0);
                } else {
                    hideProgress();
                    Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_refresh:
                getData(refresh);
                mHSortHSV.setVisibility(View.GONE);
                mCategoryBTN.setVisibility(View.VISIBLE);
                mCategoryMyBattleBTN.setVisibility(View.VISIBLE);
                mTrendingCV.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_category:
                mCategoryBTN.setSelected(true);
                getData("");
                break;
            case R.id.btn_category_mybattles:
                getData(AppConstant.MYBATTLES);
                break;
            case R.id.ll_sort:
                if (mHSortHSV.getVisibility() == mHSortHSV.GONE) {
                    mHSortHSV.setVisibility(View.VISIBLE);
                    mCategoryBTN.setVisibility(View.INVISIBLE);
                    mCategoryMyBattleBTN.setVisibility(View.GONE);
                    mTrendingCV.setVisibility(View.GONE);
                } else {
                    mHSortHSV.setVisibility(View.GONE);
                    mCategoryBTN.setVisibility(View.VISIBLE);
                    mCategoryMyBattleBTN.setVisibility(View.VISIBLE);
                    mTrendingCV.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_inner_newest:
                if (refresh.equalsIgnoreCase(AppConstant.NEWESTFILTER)) {
                    getData("");
                    mHSortHSV.setVisibility(View.GONE);
                    mCategoryBTN.setVisibility(View.VISIBLE);
                    mCategoryMyBattleBTN.setVisibility(View.VISIBLE);
                    mTrendingCV.setVisibility(View.VISIBLE);
                } else {
                    getData(AppConstant.NEWESTFILTER);
                }
                break;
            case R.id.ll_highearning:
                if (refresh.equalsIgnoreCase(AppConstant.HIGHFILTER)) {
                    getData("");
                    mHSortHSV.setVisibility(View.GONE);
                    mCategoryBTN.setVisibility(View.VISIBLE);
                    mCategoryMyBattleBTN.setVisibility(View.VISIBLE);
                    mTrendingCV.setVisibility(View.VISIBLE);
                } else {
                    getData(AppConstant.HIGHFILTER);
                }
                break;
            case R.id.ll_lowearning:
                if (refresh.equalsIgnoreCase(AppConstant.LOWFILTER)) {
                    getData("");
                    mHSortHSV.setVisibility(View.GONE);
                    mCategoryBTN.setVisibility(View.VISIBLE);
                    mCategoryMyBattleBTN.setVisibility(View.VISIBLE);
                    mTrendingCV.setVisibility(View.VISIBLE);
                } else {
                    getData(AppConstant.LOWFILTER);
                }
                break;
            case R.id.tv_points:
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
        mNoDataTV.setVisibility(View.GONE);
        mBattleRV.setVisibility(View.VISIBLE);
        mMyBattleRV.setVisibility(View.GONE);
        mAmountTV.setText("Win upto " + "\u20B9" + new DecimalFormat("##.##").format(responseModel.getHighestAmountBattle()));
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mBattleList.clear();
            mBattleList.addAll(responseModel.getResponse());
            mBattleAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
            mMyBattleRV.setVisibility(View.GONE);
            mBattleRV.setVisibility(View.GONE);
        }
        double prizePool = 0;
        for (int i = 0; i < responseModel.getResponse().size(); i++) {
            prizePool = prizePool + responseModel.getResponse().get(i).getPrize();
        }
        hideProgress();
    }

    @Override
    public void onAllBattlesFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onMyBattlesComplete(BattleResponseHTH responseModel) {
        mNoDataTV.setVisibility(View.GONE);
        mBattleRV.setVisibility(View.GONE);
        mMyBattleRV.setVisibility(View.VISIBLE);
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mMyBattleList.clear();
            mMyBattleList.addAll(responseModel.getResponse());
            mMYBattleAdapter = new MyBattlesAdpater(mMyBattleList, mAppPreference.getProfileData().getId(), responseModel.isIs_lines_up(), 0);
            mMyBattleRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mMyBattleRV.setAdapter(mMYBattleAdapter);
            mMYBattleAdapter.setOnItemChildClickListener(this);
            mMYBattleAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
            mMyBattleRV.setVisibility(View.GONE);
            mBattleRV.setVisibility(View.GONE);
        }
        double prizePool = 0;
        for (int i = 0; i < responseModel.getResponse().size(); i++) {
            prizePool = prizePool + responseModel.getResponse().get(i).getPrize();
        }
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
    public void onUpdateClicked(int position) {
        mBattlePosition = position;
        showProgress(getString(R.string.txt_progress_authentication));
        mMatchPresenter.getMatchStatus(mMatchDetail.getId());
    }

    private void openSelectedPlayers(int i, int position) {
        if (mFromMatches == AppConstant.FROM_CRICKET) {
            Intent createBattle = new Intent(this, SelectedPlayers.class);
            if (i == 1) {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ACCEPT);
            } else {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
            }
            createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
            createBattle.putExtra(AppConstant.ID, mBattleList.get(position).getId());
            createBattle.putExtra(AppConstant.BATTLE_DATA, mBattleList.get(position));
            createBattle.putExtra(AppConstant.GROUP_JOINED, mBattleList.get(position).getId());
            acceptActivityResultLauncher.launch(createBattle);
        } else if (mFromMatches == AppConstant.FROM_FOOTBALL) {
            Intent createBattle = new Intent(this, FootballSeletedActivity.class);
            if (i == 1) {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ACCEPT);
            } else {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
            }
            createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
            createBattle.putExtra(AppConstant.ID, mBattleList.get(position).getId());
            createBattle.putExtra(AppConstant.BATTLE_DATA, mBattleList.get(position));
            createBattle.putExtra(AppConstant.GROUP_JOINED, mBattleList.get(position).getId());
            acceptActivityResultLauncher.launch(createBattle);
        } else {
            Intent createBattle = new Intent(this, KabaddiSelectedPlayerActivity.class);
            if (i == 1) {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ACCEPT);
            } else {
                createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
            }
            createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
            createBattle.putExtra(AppConstant.ID, mBattleList.get(position).getId());
            createBattle.putExtra(AppConstant.BATTLE_DATA, mBattleList.get(position));
            createBattle.putExtra(AppConstant.GROUP_JOINED, mBattleList.get(position).getId());
            acceptActivityResultLauncher.launch(createBattle);
        }
    }

    @Override
    public void onJoinedCLicked(int postion) {
        openSelectedPlayers(2, postion);
    }

    @Override
    public void onCanceledClicked(int position) {
        mCancelBattleID = mBattleList.get(position).getId();
        acceptAlert(this);
    }

    @Override
    public void onUpdatePlayer(int position) {
        Intent createBattle;
        if (mFromMatches == AppConstant.FROM_KABAADI) {
            createBattle = new Intent(this, KabaddiSelectedPlayerActivity.class);
            createBattle.putExtra(AppConstant.MATCH_TYPE, AppConstant.FROM_KABAADI);
        } else if (mFromMatches == AppConstant.FROM_FOOTBALL) {
            createBattle = new Intent(this, FootballSeletedActivity.class);
            createBattle.putExtra(AppConstant.MATCH_TYPE, AppConstant.FROM_FOOTBALL);
        } else {
            createBattle = new Intent(this, SelectedPlayers.class);
            createBattle.putExtra(AppConstant.MATCH_TYPE, AppConstant.FROM_CRICKET);
        }
        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
        createBattle.putExtra(AppConstant.MATCH_DATA, mMatchDetail);
        createBattle.putExtra(AppConstant.ID, mMyBattleList.get(position).getId());
        createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(position));
        createBattle.putExtra(AppConstant.GROUP_JOINED, mMyBattleList.get(position).getId());
        acceptActivityResultLauncher.launch(createBattle);
    }

    @Override
    public void onCancelCombo(int position) {
        mCancelBattleID = mMyBattleList.get(position).getId();
        acceptAlert(this);
    }

    ActivityResultLauncher<Intent> acceptActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (mAppPreference.getBoolean(AppConstant.MATCH_LIVE, false)) {
                    finish();
                } else {
                    mFromSelectedPlayer = 1;
                }
            });

    ActivityResultLauncher<Intent> createActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
            });

    private BroadcastReceiver mHTHNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.CLASHOFFANS)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    mRefreshTV.callOnClick();
                    mBattleList.clear();
                    mBattleAdapter.notifyDataSetChanged();
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
                    startActivity(new Intent(HTHBattlesActivity.this, ClashXDashBoardActivity.class));
                }
            }
        }
    };
    private BroadcastReceiver mHTHNotificationReceiverLiveMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHLIVEREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    finish();
                    Intent i = new Intent(HTHBattlesActivity.this, MyFanLeagueActivityHTH.class);
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_LIVE);
                    startActivity(i);
                }
            }
        }
    };

    @Override
    public void onGetHTHMatchListComplete(HTHMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (mType == 1) {
                if (responseModel.getResponse().size() == 0) {
                    finish();
                } else {
                    if (mFromMatches == AppConstant.FROM_CRICKET) {
                        Intent createBattle = new Intent(this, CreateTeamActivity.class);
                        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ADD);
                        createBattle.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) responseModel.getResponse());
                        createActivityResultLauncher.launch(createBattle);
                    } else if (mFromMatches == AppConstant.FROM_FOOTBALL) {
                        Intent createBattle = new Intent(this, FootballCreateTeamActivity.class);
                        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ADD);
                        createBattle.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) responseModel.getResponse());
                        createActivityResultLauncher.launch(createBattle);
                    } else if (mFromMatches == AppConstant.FROM_KABAADI) {
//                        Intent createBattle = new Intent(this, KabaddiCreateTeamActivity.class);
                        Intent createBattle = new Intent(this, KabaddiCreateTeamActivity.class);
                        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ADD);
                        createBattle.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) responseModel.getResponse());
                        createActivityResultLauncher.launch(createBattle);
                    } else {
                        Intent createBattle = new Intent(this, CreateTeamActivity.class);
                        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_ADD);
                        createBattle.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) responseModel.getResponse());
                        createActivityResultLauncher.launch(createBattle);
                    }
                }
            } else {
                if (responseModel.getResponse().size() == 0) {
                    finish();
                    Intent i = new Intent(HTHBattlesActivity.this, MyFanLeagueActivityHTH.class);
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_LIVE);
                    startActivity(i);
                }
            }
            if (!mMatchDetail.isIs_lines_up()) {
                if (responseModel.getResponse().get(0).isIs_lines_up()) {
                    mMYBattleAdapter = new MyBattlesAdpater(mMyBattleList, mAppPreference.getProfileData().getId(), responseModel.getResponse().get(0).isIs_lines_up(), 0);
                    mMyBattleRV.setLayoutManager(new LinearLayoutManager(this));
                    mMyBattleRV.setAdapter(mMYBattleAdapter);
                    mMYBattleAdapter.setOnItemChildClickListener(this);
                }
            }
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onGetHTHMatchListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onCancelBattle(HTHCancelResponse responseModel) {
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            mRefreshTV.callOnClick();
            mBattleList.clear();
            mBattleAdapter.notifyDataSetChanged();
            mAppPreference.setProfileData(responseModel.getProfile());
            setCoin();
        }
        hideProgress();
    }

    private void setCoin() {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
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
        hideProgress();
        if (responseModel.isStatus()) {
            openSelectedPlayers(1, mBattlePosition);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onMatchStatusError(ApiError error) {
        hideProgress();
    }

    private void acceptAlert(Context context) {
        if (context != null) {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_delete);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView tv_msg = dialog.findViewById(R.id.tv_msg);
            tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
            Button mOkBTN = dialog.findViewById(R.id.btn_ok);
            mOkBTN.setOnClickListener(arg0 -> {
                dialog.dismiss();
                showProgress(getString(R.string.txt_progress_authentication));
                mMatchPresenter.onCancelBattle(mCancelBattleID);
            });
            Button mNoBTN = dialog.findViewById(R.id.btn_no);
            mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new NetworkStatus(this).isInternetOn()) {
            mType = 2;
            mCreateBattleRL.setClickable(true);
            if (mFromSelectedPlayer == 1) {
                getData(AppConstant.MYBATTLES);
            } else {
                getData("");
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverMatches);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverLiveMatches);
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        mMatchPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgress();
    }
}