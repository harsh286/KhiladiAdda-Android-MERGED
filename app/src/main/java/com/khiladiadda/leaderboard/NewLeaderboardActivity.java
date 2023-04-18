package com.khiladiadda.leaderboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.leaderboard.adapter.LeagueLeaderBoardRVAdapter;
import com.khiladiadda.leaderboard.adapter.AllLeaderboardNewAdapter;
import com.khiladiadda.leaderboard.adapter.FbLeadBoardAdapter;
import com.khiladiadda.leaderboard.adapter.LudoAddaLeaderBoardRVAdapter;
import com.khiladiadda.leaderboard.adapter.LudoLeaderBoardRVAdapter;
import com.khiladiadda.leaderboard.interfaces.ILeaderboardPresenter;
import com.khiladiadda.leaderboard.interfaces.ILeaderboardView;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.AllLeaderBoardResponse;
import com.khiladiadda.network.model.response.AllLederBoardDetails;
import com.khiladiadda.network.model.response.LeaderboardMainResponse;
import com.khiladiadda.network.model.response.LeaderboardSubResponse;
import com.khiladiadda.network.model.response.LudoAddaMainResponse;
import com.khiladiadda.network.model.response.LudoAddaResponseDetails;
import com.khiladiadda.network.model.response.LudoLeaderboardDetails;
import com.khiladiadda.network.model.response.LudoLeaderboardResponse;
import com.khiladiadda.network.model.response.OverallLeadBoardList;
import com.khiladiadda.network.model.response.OverallLeadBoardResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponseDetails;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewLeaderboardActivity extends BaseActivity implements ILeaderboardView, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.btn_ludo)
    Button mLudoBTN;
    @BindView(R.id.btn_freefire)
    Button mFreeFireBTN;
    @BindView(R.id.rv_leaderboard)
    RecyclerView mLeagueLeaderBoardRV;
    @BindView(R.id.rv_ludo_leaderboard)
    RecyclerView mLudoLeaderBoardRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.tv_profile_name_single)
    AppCompatTextView mTitleToolbarTV;
    @BindView(R.id.tv_leaderboard)
    TextView mTitleTV;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.gold_winner)
    CircularImageView mGoldWinnerIV;
    @BindView(R.id.gold_crown)
    ImageView mGoldCrownIV;
    @BindView(R.id.text_gold_winner_name)
    TextView mGoldWinerNameTV;
    @BindView(R.id.gold_winner_coins)
    TextView mGoldCoinsTV;
    @BindView(R.id.sliver_winner)
    CircularImageView mSliverWinerIV;
    @BindView(R.id.silver_crown)
    ImageView mSilverCrownIV;
    @BindView(R.id.text_silver_winner_name)
    TextView mSilverWinerNameTV;
    @BindView(R.id.tv_sliver_winner_coins)
    TextView mSilverWinerCoinsTV;
    @BindView(R.id.bronze_winner)
    CircularImageView mBronzeWinnerIV;
    @BindView(R.id.bronze_crown)
    ImageView mBronzeCrownIV;
    @BindView(R.id.brown_winner_name)
    TextView mBrownWinnerNameTV;
    @BindView(R.id.brown_winner_coins)
    TextView mBrownWinnerCoinsTV;
    @BindView(R.id.btn_fan_battle)
    Button mFanBattleBTN;
    @BindView(R.id.rv_fanbattle_leaderboard)
    RecyclerView mFanBattledRV;
    @BindView(R.id.iv_back_toolbar)
    ImageView mToolbarBackIV;
    @BindView(R.id.sm_leaderboard)
    SwitchMaterial mALLMonthSW;
    @BindView(R.id.ll_more)
    LinearLayout mMoreLL;
    @BindView(R.id.rv_hth_leaderboard)
    RecyclerView mHTHRV;
    @BindView(R.id.rv_ludoadda_leaderboard)
    RecyclerView mLudoAddaRV;
    @BindView(R.id.rv_all_leaderboard)
    RecyclerView mAllLeaderboardRv;
    @BindView(R.id.nudge)
    NudgeView mNV;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    private ILeaderboardPresenter mPresenter;
    private String mGameId;
    private boolean mIsLoading, mIsLastPage;
    private int mCurrentPage = 0, mItemCount, mContestType, mListingType = AppConstant.LEADERBOARD_LISTING_ALL, mType = AppConstant.LEADERBOARD_TYPE_GAME;
    private LinearLayoutManager mLeagueLayoutManager, mLudoLayoutManager, mFanBattleManager, mLudoAddaManger, mHTHBattleManager, mAllLeaderboardManager;
    private List<AllLederBoardDetails> mLeagueList;
    private List<OverallLeadBoardList> mFBLists;
    private List<LudoLeaderboardDetails> mLudoList;
    private List<LeaderboardSubResponse> mAllLeaderboardSubResponsesList;
    private List<LeaderBoardHthResponseDetails> mHTHLists;
    private List<LudoAddaResponseDetails> mLudoAddaLists;
    private LeagueLeaderBoardRVAdapter mLeagueAdapter;
    private FbLeadBoardAdapter mFBAdapter;
    private LudoLeaderBoardRVAdapter mLudoAdapter;
    private HTHLeadBoardAdapter mHthAdapter;
    private LudoAddaLeaderBoardRVAdapter mLudoAddaAdapter;
    private AllLeaderboardNewAdapter mAllLeaderboardNewAdapter;
    private long mLastClickTime = 0;
    private Dialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_newleaderboard;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mToolbarBackIV.setOnClickListener(this);
        mLudoBTN.setOnClickListener(this);
        mFreeFireBTN.setOnClickListener(this);
        mALLMonthSW.setOnCheckedChangeListener(this);
        mMoreLL.setOnClickListener(this);
        mFanBattleBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new LeaderboardPresenter(this);

        String[] superHero =
                {"All", "Daily", "Weekly", "Monthly"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_list, superHero);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(this);

        mLeagueList = new ArrayList<>();
        mLeagueAdapter = new LeagueLeaderBoardRVAdapter(mLeagueList, AppConstant.LEADERBOARD_FROM_LEADERBOARD);
        mLeagueLayoutManager = new LinearLayoutManager(this);
        mLeagueLeaderBoardRV.setLayoutManager(mLeagueLayoutManager);
        mLeagueLeaderBoardRV.setAdapter(mLeagueAdapter);
        mLeagueLeaderBoardRV.addOnScrollListener(mRecyclerViewOnScrollListener);

        mLudoList = new ArrayList<>();
        mLudoAdapter = new LudoLeaderBoardRVAdapter(mLudoList, AppConstant.LEADERBOARD_FROM_LEADERBOARD);
        mLudoLayoutManager = new LinearLayoutManager(this);
        mLudoLeaderBoardRV.setLayoutManager(mLudoLayoutManager);
        mLudoLeaderBoardRV.setAdapter(mLudoAdapter);
        mLudoLeaderBoardRV.addOnScrollListener(mLudoRecyclerViewOnScrollListener);

        mFBLists = new ArrayList<>();
        mFBAdapter = new FbLeadBoardAdapter(mFBLists);
        mFanBattleManager = new LinearLayoutManager(this);
        mFanBattledRV.setLayoutManager(mFanBattleManager);
        mFanBattledRV.setAdapter(mFBAdapter);
        mFanBattledRV.addOnScrollListener(mFanBattleRecyclerViewOnScrollListener);

        mHTHLists = new ArrayList<>();
        mHthAdapter = new HTHLeadBoardAdapter(mHTHLists);
        mHTHBattleManager = new LinearLayoutManager(this);
        mHTHRV.setLayoutManager(mHTHBattleManager);
        mHTHRV.setAdapter(mHthAdapter);
        mHTHRV.addOnScrollListener(mHTHBattleRecyclerViewOnScrollListener);

        mLudoAddaLists = new ArrayList<>();
        mLudoAddaAdapter = new LudoAddaLeaderBoardRVAdapter(mLudoAddaLists);
        mLudoAddaManger = new LinearLayoutManager(this);
        mLudoAddaRV.setLayoutManager(mLudoAddaManger);
        mLudoAddaRV.setAdapter(mLudoAddaAdapter);
        mLudoAddaRV.addOnScrollListener(mLudoAddaRecyclerViewOnScrollListener);

        mAllLeaderboardSubResponsesList = new ArrayList<>();
        mAllLeaderboardNewAdapter = new AllLeaderboardNewAdapter(mAllLeaderboardSubResponsesList);
        mAllLeaderboardManager = new LinearLayoutManager(this);
        mAllLeaderboardRv.setLayoutManager(mAllLeaderboardManager);
        mAllLeaderboardRv.setAdapter(mAllLeaderboardNewAdapter);
        mAllLeaderboardRv.addOnScrollListener(mAllLeaderboardRecyclerViewOnScrollListener);

        mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
        mALLMonthSW.setChecked(false);
        mFreeFireBTN.setSelected(true);
        AppUtilityMethods.deleteCache(this);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    mTitleToolbarTV.setVisibility(View.VISIBLE);
                    mToolbarBackIV.setVisibility(View.VISIBLE);
                    mGoldWinnerIV.setVisibility(View.GONE);
                    mGoldCrownIV.setVisibility(View.GONE);
                    mGoldWinerNameTV.setVisibility(View.GONE);
                    mGoldCoinsTV.setVisibility(View.GONE);
                } else if (isShow) {
                    isShow = false;
                    mTitleToolbarTV.setVisibility(View.GONE);
                    mToolbarBackIV.setVisibility(View.GONE);
                    mBackIV.setVisibility(View.VISIBLE);
                    mGoldWinnerIV.setVisibility(View.VISIBLE);
                    mGoldCrownIV.setVisibility(View.VISIBLE);
                    mGoldWinerNameTV.setVisibility(View.VISIBLE);
                    mGoldCoinsTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            AppUtilityMethods.deleteCache(this);
            //QUIZ
            if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && (mType == AppConstant.LEADERBOARD_TYPE_QUIZ)) {
                mPresenter.getQuizAll(mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_QUIZ) {
                mPresenter.getQuizMonth(mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_QUIZ) {
                mPresenter.getQuizDaily(mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_QUIZ) {
                mPresenter.getQuizWeekly(mCurrentPage, AppConstant.PAGE_SIZE);
            }
            //GAME(DONE)
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_GAME) {
                mPresenter.getGameAll(mGameId, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_GAME) {
                mPresenter.getGameMonth(mGameId, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_GAME) {
                mPresenter.getGameDaily(mGameId, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_GAME) {
                mPresenter.getGameWeekly(mGameId, mCurrentPage, AppConstant.PAGE_SIZE);
            }
            //LUDO
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && (mType == AppConstant.LEADERBOARD_TYPE_LUDO || mType == AppConstant.LEADERBOARD_TYPE_SNAKE)) {
                mPresenter.getLudo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_ALL, mContestType);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && (mType == AppConstant.LEADERBOARD_TYPE_LUDO || mType == AppConstant.LEADERBOARD_TYPE_SNAKE)) {
                mPresenter.getLudo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_MONTHLY, mContestType);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && (mType == AppConstant.LEADERBOARD_TYPE_LUDO || mType == AppConstant.LEADERBOARD_TYPE_SNAKE)) {
                mPresenter.getLudo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_DAILY, mContestType);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && (mType == AppConstant.LEADERBOARD_TYPE_LUDO || mType == AppConstant.LEADERBOARD_TYPE_SNAKE)) {
                mPresenter.getLudo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_WEEKLY, mContestType);
            }
            //FANBATTLE
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
                mPresenter.getFanBattle(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_FANBATTLE_LISTING_ALL);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
                mPresenter.getFanBattle(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_FANBATTLE_LISTING_MONTHLY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
                mPresenter.getFanBattle(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_FANBATTLE_LISTING_DAILY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
                mPresenter.getFanBattle(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_FANBATTLE_LISTING_WEEKLY);
            }
            //HTH BATTLE
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
                mPresenter.getHTHBattles(mCurrentPage, AppConstant.PAGE_SIZE, "");
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
                mPresenter.getHTHBattles(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
                mPresenter.getHTHBattles(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_DAILY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
                mPresenter.getHTHBattles(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_WEEKLY);
            }
            //LUDOADDA
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
                mPresenter.getLudoAdda(mCurrentPage, AppConstant.PAGE_SIZE, "");
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
                mPresenter.getLudoAdda(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
                mPresenter.getLudoAdda(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_DAILY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
                mPresenter.getLudoAdda(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_WEEKLY);
            }
            //WS
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_WS) {
                mPresenter.getWS(mCurrentPage, AppConstant.PAGE_SIZE, "");
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_WS) {
                mPresenter.getWS(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_WS) {
                mPresenter.getWS(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_DAILY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_WS) {
                mPresenter.getWS(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_WEEKLY);
            }
            //DROID-O
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_DROID_DO) {
                mPresenter.getDroidDo(mCurrentPage, AppConstant.PAGE_SIZE, "");
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_DROID_DO) {
                mPresenter.getDroidDo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_DROID_DO) {
                mPresenter.getDroidDo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_DAILY);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_DROID_DO) {
                mPresenter.getDroidDo(mCurrentPage, AppConstant.PAGE_SIZE, AppConstant.TYPE_WEEKLY);
            }
            //Ludo Tournament
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
                mPresenter.getLudoTournament("", mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
                mPresenter.getLudoTournament(AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
                mPresenter.getLudoTournament(AppConstant.TYPE_DAILY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
                mPresenter.getLudoTournament(AppConstant.TYPE_WEEKLY, mCurrentPage, AppConstant.PAGE_SIZE);
            }
            //Court Piece
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_COURTPIECE) {
                mPresenter.getCourtPiece("", mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_COURTPIECE) {
                mPresenter.getCourtPiece(AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_COURTPIECE) {
                mPresenter.getCourtPiece(AppConstant.TYPE_DAILY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_COURTPIECE) {
                mPresenter.getCourtPiece(AppConstant.TYPE_WEEKLY, mCurrentPage, AppConstant.PAGE_SIZE);
            }
            //Rummy
            else if (mListingType == AppConstant.LEADERBOARD_LISTING_ALL && mType == AppConstant.LEADERBOARD_TYPE_RUMMY) {
                mPresenter.getRummy("", mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_MONTHLY && mType == AppConstant.LEADERBOARD_TYPE_RUMMY) {
                mPresenter.getRummy(AppConstant.LEADERBOARD_HTH_LISTING_MONTHLY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_DAILY && mType == AppConstant.LEADERBOARD_TYPE_RUMMY) {
                mPresenter.getRummy(AppConstant.TYPE_DAILY, mCurrentPage, AppConstant.PAGE_SIZE);
            } else if (mListingType == AppConstant.LEADERBOARD_LISTING_WEEKLY && mType == AppConstant.LEADERBOARD_TYPE_RUMMY) {
                mPresenter.getRummy(AppConstant.TYPE_WEEKLY, mCurrentPage, AppConstant.PAGE_SIZE);
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.iv_back_toolbar:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.btn_ludo:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                mType = AppConstant.LEADERBOARD_TYPE_LUDO;
                mContestType = AppConstant.TYPE_LUDO;
                setSelectedBackground();
                break;
            case R.id.btn_freefire:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                mType = AppConstant.LEADERBOARD_TYPE_GAME;
                mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
                setSelectedBackground();
                break;
            case R.id.btn_fan_battle:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                mType = AppConstant.LEADERBOARD_TYPE_FAN_BATTLE;
                setSelectedBackground();
                break;
            case R.id.ll_more:
                showGameDialog();
                break;
        }
    }

    private void setSelectedBackground() {
        AppUtilityMethods.deleteCache(this);
        mLudoBTN.setTextColor(Color.RED);
        mFreeFireBTN.setTextColor(Color.RED);
        mFanBattleBTN.setTextColor(Color.RED);
        mFanBattleBTN.setSelected(false);
        mFreeFireBTN.setSelected(false);
        mLudoBTN.setSelected(false);
        resetListVariables();
        if (mType == AppConstant.LEADERBOARD_TYPE_QUIZ) {
            mTitleToolbarTV.setText(getString(R.string.text_quiz_leaderboard));
            mTitleTV.setText(getString(R.string.text_quiz_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_LUDO) {
            mLudoBTN.setSelected(true);
            mLudoBTN.setTextColor(Color.WHITE);
            mTitleToolbarTV.setText(getString(R.string.ludo_leaderborad));
            mTitleTV.setText(getString(R.string.ludo_leaderborad));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, ""))) {
            mFreeFireBTN.setSelected(true);
            mFreeFireBTN.setTextColor(Color.WHITE);
            mTitleToolbarTV.setText(getString(R.string.freefire_leaderboard));
            mTitleTV.setText(getString(R.string.freefire_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, ""))) {
            mTitleToolbarTV.setText(getString(R.string.freefire_max_leaderboard));
            mTitleTV.setText(getString(R.string.freefire_max_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, ""))) {
            mTitleToolbarTV.setText(getString(R.string.clash_leaderborad));
            mTitleTV.setText(getString(R.string.clash_leaderborad));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
            mFanBattleBTN.setSelected(true);
            mFanBattleBTN.setTextColor(Color.WHITE);
            mTitleToolbarTV.setText(getString(R.string.fan_battle_leaderboard));
            mTitleTV.setText(getString(R.string.fan_battle_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, ""))) {
            mTitleToolbarTV.setText(getString(R.string.tdm_leaderboard));
            mTitleTV.setText(getString(R.string.tdm_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, ""))) {
            mTitleToolbarTV.setText(getString(R.string.pubg_leaderboard));
            mTitleTV.setText(getString(R.string.pubg_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
            mTitleToolbarTV.setText(getString(R.string.hth_leaderboard));
            mTitleTV.setText(getString(R.string.hth_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            mTitleToolbarTV.setText(R.string.text_pubglobal);
            mTitleTV.setText(R.string.text_pubglobal);
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            mTitleToolbarTV.setText(R.string.text_esportsper);
            mTitleTV.setText(R.string.text_esportsper);
        } else if (mType == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
            mTitleToolbarTV.setText(R.string.text_ludo_adda_leader);
            mTitleTV.setText(R.string.text_ludo_adda_leader);
        } else if (mType == AppConstant.LEADERBOARD_TYPE_GAME && mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            mTitleToolbarTV.setText(getString(R.string.pubg_ns_leaderboard));
            mTitleTV.setText(getString(R.string.pubg_ns_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_WS) {
            mTitleToolbarTV.setText(getString(R.string.text_wordsearch_leaderboard));
            mTitleTV.setText(getString(R.string.text_wordsearch_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_DROID_DO) {
            mTitleToolbarTV.setText(getString(R.string.text_droid_leaderboard));
            mTitleTV.setText(getString(R.string.text_droid_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
            mTitleToolbarTV.setText(getString(R.string.text_ludo_tournament_leaderboard));
            mTitleTV.setText(getString(R.string.text_ludo_tournament_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_COURTPIECE) {
            mTitleToolbarTV.setText(getString(R.string.text_court_piece_leaderboard));
            mTitleTV.setText(getString(R.string.text_court_piece_leaderboard));
        } else if (mType == AppConstant.LEADERBOARD_TYPE_RUMMY) {
            mTitleToolbarTV.setText(getString(R.string.text_rummy_leaderboard));
            mTitleTV.setText(getString(R.string.text_rummy_leaderboard));
        }
        getData();
    }

    private void resetListVariables() {
        mLeagueList.clear();
        mLeagueAdapter.notifyDataSetChanged();
        mLudoList.clear();
        mLudoAdapter.notifyDataSetChanged();
        mFBLists.clear();
        mFBAdapter.notifyDataSetChanged();
        mHTHLists.clear();
        mHthAdapter.notifyDataSetChanged();
        mLudoAddaLists.clear();
        mLudoAddaAdapter.notifyDataSetChanged();
        mAllLeaderboardSubResponsesList.clear();
        mAllLeaderboardNewAdapter.notifyDataSetChanged();
        mCurrentPage = 0;
        mItemCount = 0;
        mIsLastPage = false;
    }

    private void resetLeaderBoardView(int type) {
        mNoDataTV.setVisibility(View.GONE);
        mLudoLeaderBoardRV.setVisibility(View.GONE);
        mLeagueLeaderBoardRV.setVisibility(View.GONE);
        mFanBattledRV.setVisibility(View.GONE);
        mHTHRV.setVisibility(View.GONE);
        mLudoAddaRV.setVisibility(View.GONE);
        mAllLeaderboardRv.setVisibility(View.GONE);
        if ((type == AppConstant.LEADERBOARD_TYPE_GAME) || (type == AppConstant.LEADERBOARD_TYPE_QUIZ) || (type == AppConstant.LEADERBOARD_TYPE_DROID_DO) || (type == AppConstant.LEADERBOARD_TYPE_WS)) {
            mLeagueLeaderBoardRV.setVisibility(View.VISIBLE);
        } else if (type == AppConstant.LEADERBOARD_TYPE_LUDO) {
            mLudoLeaderBoardRV.setVisibility(View.VISIBLE);
        } else if (type == AppConstant.LEADERBOARD_TYPE_FAN_BATTLE) {
            mFanBattledRV.setVisibility(View.VISIBLE);
        } else if (type == AppConstant.LEADERBOARD_TYPE_HTH_BATTLE) {
            mHTHRV.setVisibility(View.VISIBLE);
        } else if (type == AppConstant.LEADERBOARD_TYPE_LUDOADDA) {
            mLudoAddaRV.setVisibility(View.VISIBLE);
        } else if (type == AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT) {
            mAllLeaderboardRv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLeaderBoardComplete(AllLeaderBoardResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_GAME);
        if (responseModel.isStatus()) {
            List<AllLederBoardDetails> leaderboardList = responseModel.getResponse();
            mItemCount = leaderboardList.size();
            if (mCurrentPage == 0) {
                mLeagueList.clear();
                if (mItemCount > 3) {
                    mLeagueList.addAll(leaderboardList.subList(3, mItemCount));
                }
                setLeaderData(leaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mLeagueList.addAll(leaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mLeagueAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderBoardFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLudoLeaderBoardComplete(LudoLeaderboardResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_LUDO);
        if (responseModel.isStatus()) {
            List<LudoLeaderboardDetails> ludoLeaderboardList = responseModel.getResponse();
            mItemCount = ludoLeaderboardList.size();
            if (mCurrentPage == 0) {
                mLudoList.clear();
                if (mItemCount > 3) {
                    mLudoList.addAll(ludoLeaderboardList.subList(3, mItemCount));
                }
                setLudoLeaderData(ludoLeaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mLudoList.addAll(ludoLeaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mLudoAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLudoLeaderBoardFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLeaderFanBattleComplete(OverallLeadBoardResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_FAN_BATTLE);
        if (responseModel.isStatus()) {
            List<OverallLeadBoardList> fbLeaderboardList = responseModel.getOverallLeadBoardLists();
            mItemCount = fbLeaderboardList.size();
            if (mCurrentPage == 0) {
                mFBLists.clear();
                if (mItemCount > 3) {
                    mFBLists.addAll(fbLeaderboardList.subList(3, mItemCount));
                }
                setFanBattleData(fbLeaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mFBLists.addAll(fbLeaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mFBAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderFanBattleFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLeaderHTHBattleComplete(LeaderBoardHthResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_HTH_BATTLE);
        if (responseModel.isStatus()) {
            List<LeaderBoardHthResponseDetails> hthLeaderboardList = responseModel.getResponse();
            mItemCount = hthLeaderboardList.size();
            if (mCurrentPage == 0) {
                mHTHLists.clear();
                if (mItemCount > 3) {
                    mHTHLists.addAll(hthLeaderboardList.subList(3, mItemCount));
                }
                setHTHBattleData(hthLeaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mHTHLists.addAll(hthLeaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mHthAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderHTHBattleFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLeaderLudoAddaComplete(LudoAddaMainResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_LUDOADDA);
        if (responseModel.isStatus()) {
            List<LudoAddaResponseDetails> ludoLeaderboardList = responseModel.getResponse();
            mItemCount = ludoLeaderboardList.size();
            if (mCurrentPage == 0) {
                mLudoAddaLists.clear();
                if (mItemCount > 3) {
                    mLudoAddaLists.addAll(ludoLeaderboardList.subList(3, mItemCount));
                }
                setLudoAddaLeaderData(ludoLeaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mLudoAddaLists.addAll(ludoLeaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mLudoAddaAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderLudoAdda(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLeaderWSComplete(AllLeaderBoardResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_GAME);
        if (responseModel.isStatus()) {
            List<AllLederBoardDetails> leaderboardList = responseModel.getResponse();
            mItemCount = leaderboardList.size();
            if (mCurrentPage == 0) {
                mLeagueList.clear();
                if (mItemCount > 3) {
                    mLeagueList.addAll(leaderboardList.subList(3, mItemCount));
                }
                setLeaderData(leaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mLeagueList.addAll(leaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mLeagueAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderWS(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLeaderDroidComplete(AllLeaderBoardResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_GAME);
        if (responseModel.isStatus()) {
            List<AllLederBoardDetails> leaderboardList = responseModel.getResponse();
            mItemCount = leaderboardList.size();
            if (mCurrentPage == 0) {
                mLeagueList.clear();
                if (mItemCount > 3) {
                    mLeagueList.addAll(leaderboardList.subList(3, mItemCount));
                }
                setLeaderData(leaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mLeagueList.addAll(leaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mLeagueAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLeaderDroidError(ApiError error) {
        hideProgress();
    }

    @Override
    public void onAllLeaderBoardComplete(LeaderboardMainResponse responseModel) {
        resetLeaderBoardView(AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT);
        if (responseModel.isStatus()) {
            List<LeaderboardSubResponse> LeaderboardList = responseModel.getResponse();
            mItemCount = LeaderboardList.size();
            if (mCurrentPage == 0) {
                mAllLeaderboardSubResponsesList.clear();
                if (mItemCount > 3) {
                    mAllLeaderboardSubResponsesList.addAll(LeaderboardList.subList(3, mItemCount));
                }
                setLeaderDataNew(LeaderboardList, mItemCount);
                if (mItemCount == 0) {
                    mAppBarLayout.setExpanded(false);
                    mNoDataTV.setVisibility(View.VISIBLE);
                }
            } else {
                mAllLeaderboardSubResponsesList.addAll(LeaderboardList);
            }
            mIsLoading = false;
            mCurrentPage++;
            if (mItemCount < AppConstant.PAGE_SIZE) {
                mIsLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mAllLeaderboardNewAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onAllLeaderBoardError(ApiError error) {
        hideProgress();
    }

    private void setHTHBattleData(List<LeaderBoardHthResponseDetails> overallLeadBoardLists, int size) {
        clearData();
        if (size >= 3) {
            setOne(overallLeadBoardLists.get(0).getName(), overallLeadBoardLists.get(0).getTotal(), overallLeadBoardLists.get(0).getDp(), overallLeadBoardLists.get(0).getnCof().getWon());
            setTwo(overallLeadBoardLists.get(1).getName(), overallLeadBoardLists.get(1).getTotal(), overallLeadBoardLists.get(1).getDp(), overallLeadBoardLists.get(1).getnCof().getWon());
            setThree(overallLeadBoardLists.get(2).getName(), overallLeadBoardLists.get(2).getTotal(), overallLeadBoardLists.get(2).getDp(), overallLeadBoardLists.get(2).getnCof().getWon());
            showTopLeaderVisible(3);
        } else if (size == 2) {
            setOne(overallLeadBoardLists.get(0).getName(), overallLeadBoardLists.get(0).getTotal(), overallLeadBoardLists.get(0).getDp(), overallLeadBoardLists.get(0).getnCof().getWon());
            setTwo(overallLeadBoardLists.get(1).getName(), overallLeadBoardLists.get(1).getTotal(), overallLeadBoardLists.get(1).getDp(), overallLeadBoardLists.get(1).getnCof().getWon());
            showTopLeaderVisible(2);
        } else if (size == 1) {
            setOne(overallLeadBoardLists.get(0).getName(), overallLeadBoardLists.get(0).getTotal(), overallLeadBoardLists.get(0).getDp(), overallLeadBoardLists.get(0).getnCof().getWon());
            showTopLeaderVisible(1);
        }
    }

    private void setLudoAddaLeaderData(List<LudoAddaResponseDetails> ludoAddaLeaderboardList, int mItemCount) {
        clearData();
        if (mItemCount >= 3) {
            setOne(ludoAddaLeaderboardList.get(0).getName(), ludoAddaLeaderboardList.get(0).getTotal(), ludoAddaLeaderboardList.get(0).getDp(), ludoAddaLeaderboardList.get(0).getTotal());
            setTwo(ludoAddaLeaderboardList.get(1).getName(), ludoAddaLeaderboardList.get(1).getTotal(), ludoAddaLeaderboardList.get(1).getDp(), ludoAddaLeaderboardList.get(1).getTotal());
            setThree(ludoAddaLeaderboardList.get(2).getName(), ludoAddaLeaderboardList.get(2).getTotal(), ludoAddaLeaderboardList.get(2).getDp(), ludoAddaLeaderboardList.get(2).getTotal());
            showTopLeaderVisible(3);
        } else if (mItemCount == 2) {
            setOne(ludoAddaLeaderboardList.get(0).getName(), ludoAddaLeaderboardList.get(0).getTotal(), ludoAddaLeaderboardList.get(0).getDp(), ludoAddaLeaderboardList.get(0).getTotal());
            setTwo(ludoAddaLeaderboardList.get(1).getName(), ludoAddaLeaderboardList.get(1).getTotal(), ludoAddaLeaderboardList.get(1).getDp(), ludoAddaLeaderboardList.get(1).getTotal());
            showTopLeaderVisible(2);
        } else if (mItemCount == 1) {
            setThree(ludoAddaLeaderboardList.get(2).getName(), ludoAddaLeaderboardList.get(2).getTotal(), ludoAddaLeaderboardList.get(2).getDp(), ludoAddaLeaderboardList.get(2).getTotal());
            showTopLeaderVisible(1);
        }
    }

    private void setLeaderDataNew(List<LeaderboardSubResponse> ludoAddaLeaderboardList, int mItemCount) {
        clearData();
        if (mItemCount >= 3) {
            setOne(ludoAddaLeaderboardList.get(0).getFullDetails().getName(), ludoAddaLeaderboardList.get(0).getTotalAmount(), ludoAddaLeaderboardList.get(0).getFullDetails().getDp(), ludoAddaLeaderboardList.get(0).getTotalAmount());
            setTwo(ludoAddaLeaderboardList.get(1).getFullDetails().getName(), ludoAddaLeaderboardList.get(1).getTotalAmount(), ludoAddaLeaderboardList.get(1).getFullDetails().getDp(), ludoAddaLeaderboardList.get(1).getTotalAmount());
            setThree(ludoAddaLeaderboardList.get(2).getFullDetails().getName(), ludoAddaLeaderboardList.get(2).getTotalAmount(), ludoAddaLeaderboardList.get(2).getFullDetails().getDp(), ludoAddaLeaderboardList.get(2).getTotalAmount());
            showTopLeaderVisible(3);
        } else if (mItemCount == 2) {
            setOne(ludoAddaLeaderboardList.get(0).getFullDetails().getName(), ludoAddaLeaderboardList.get(0).getTotalAmount(), ludoAddaLeaderboardList.get(0).getFullDetails().getDp(), ludoAddaLeaderboardList.get(0).getTotalAmount());
            setTwo(ludoAddaLeaderboardList.get(1).getFullDetails().getName(), ludoAddaLeaderboardList.get(1).getTotalAmount(), ludoAddaLeaderboardList.get(1).getFullDetails().getDp(), ludoAddaLeaderboardList.get(1).getTotalAmount());
            showTopLeaderVisible(2);
        } else if (mItemCount == 1) {
            setOne(ludoAddaLeaderboardList.get(0).getFullDetails().getName(), ludoAddaLeaderboardList.get(0).getTotalAmount(), ludoAddaLeaderboardList.get(0).getFullDetails().getDp(), ludoAddaLeaderboardList.get(0).getTotalAmount());
            showTopLeaderVisible(1);
        }
    }

    private void setLudoLeaderData(List<LudoLeaderboardDetails> leaderboardList, int size) {
        clearData();
        if (size >= 3) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getTotal(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getTotal());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getTotal(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getTotal());
            setThree(leaderboardList.get(2).getName(), leaderboardList.get(2).getTotal(), leaderboardList.get(2).getDp(), leaderboardList.get(2).getTotal());
            showTopLeaderVisible(3);
        } else if (size == 2) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getTotal(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getTotal());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getTotal(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getTotal());
            showTopLeaderVisible(2);
        } else if (size == 1) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getTotal(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getTotal());
            showTopLeaderVisible(1);
        }
    }

    private void setFanBattleData(List<OverallLeadBoardList> leaderboardList, int size) {
        clearData();
        if (size >= 3) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getWinningAmount(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getWinningAmount());
            setThree(leaderboardList.get(2).getName(), leaderboardList.get(2).getWinningAmount(), leaderboardList.get(2).getDp(), leaderboardList.get(2).getWinningAmount());
            showTopLeaderVisible(3);
        } else if (size == 2) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getWinningAmount(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getWinningAmount());
            showTopLeaderVisible(2);
        } else if (size == 1) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            showTopLeaderVisible(1);
        }
    }

    private void setLeaderData(List<AllLederBoardDetails> leaderboardList, int size) {
        clearData();
        if (size >= 3) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getWinningAmount(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getWinningAmount());
            setThree(leaderboardList.get(2).getName(), leaderboardList.get(2).getWinningAmount(), leaderboardList.get(2).getDp(), leaderboardList.get(2).getWinningAmount());
            showTopLeaderVisible(3);
        } else if (size == 2) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            setTwo(leaderboardList.get(1).getName(), leaderboardList.get(1).getWinningAmount(), leaderboardList.get(1).getDp(), leaderboardList.get(1).getWinningAmount());
            showTopLeaderVisible(2);
        } else if (size == 1) {
            setOne(leaderboardList.get(0).getName(), leaderboardList.get(0).getWinningAmount(), leaderboardList.get(0).getDp(), leaderboardList.get(0).getWinningAmount());
            showTopLeaderVisible(1);
        }
    }

    private void clearData() {
        mAppBarLayout.setExpanded(true);
        mBrownWinnerNameTV.setText("");
        mBrownWinnerCoinsTV.setText("");
        mSilverWinerNameTV.setText("");
        mSilverWinerCoinsTV.setText("");
        mGoldWinerNameTV.setText("");
        mGoldCoinsTV.setText("");
    }

    private void setOne(String name, double total, String dp, double won) {
        mGoldWinerNameTV.setText("1." + name);
        if ((won) != 0.00) {
            mGoldCoinsTV.setText("Won: " + "\u20B9" + new DecimalFormat("##.##").format(total));
        } else {
            mGoldCoinsTV.setText("Won: \u20B9 0");
        }
        if (!TextUtils.isEmpty(dp)) {
            Glide.with(this).load(dp).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(mGoldWinnerIV);
        } else {
            Glide.with(this).clear(mGoldWinnerIV);
            mGoldWinnerIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setTwo(String name, double total, String dp, double won) {
        mSilverWinerNameTV.setText("2." + name);
        if ((won) != 0.00) {
            mSilverWinerCoinsTV.setText("Won: " + "\u20B9" + new DecimalFormat("##.##").format(total));
        } else {
            mSilverWinerCoinsTV.setText("Won: \u20B9 0");
        }
        if (!TextUtils.isEmpty(dp)) {
            Glide.with(this).load(dp).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(mSliverWinerIV);
        } else {
            Glide.with(this).clear(mSliverWinerIV);
            mSliverWinerIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setThree(String name, double total, String dp, double won) {
        mBrownWinnerNameTV.setText("3." + name);
        if ((won) != 0.00) {
            mBrownWinnerCoinsTV.setText("Won: " + "\u20B9" + new DecimalFormat("##.##").format(total));
        } else {
            mBrownWinnerCoinsTV.setText("Won: \u20B9 0");
        }
        if (!TextUtils.isEmpty(dp)) {
            Glide.with(this).load(dp).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(mBronzeWinnerIV);
        } else {
            Glide.with(this).clear(mBronzeWinnerIV);
            mBronzeWinnerIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void showTopLeaderVisible(int i) {
        mGoldWinerNameTV.setVisibility(View.VISIBLE);
        mGoldCoinsTV.setVisibility(View.VISIBLE);
        mGoldWinnerIV.setVisibility(View.VISIBLE);
        mGoldWinnerIV.setVisibility(View.VISIBLE);
        mSliverWinerIV.setVisibility(View.VISIBLE);
        mSilverWinerCoinsTV.setVisibility(View.VISIBLE);
        mSilverWinerNameTV.setVisibility(View.VISIBLE);
        mSilverCrownIV.setVisibility(View.VISIBLE);
        if (i == 3) {
            mBronzeWinnerIV.setVisibility(View.VISIBLE);
            mBrownWinnerCoinsTV.setVisibility(View.VISIBLE);
            mBrownWinnerNameTV.setVisibility(View.VISIBLE);
            mBronzeCrownIV.setVisibility(View.VISIBLE);
        } else if (i == 2) {
            mBrownWinnerNameTV.setVisibility(View.GONE);
            mBrownWinnerCoinsTV.setVisibility(View.GONE);
            mBronzeWinnerIV.setVisibility(View.GONE);
            mBronzeCrownIV.setVisibility(View.GONE);
        } else if (i == 1) {
            mSilverWinerNameTV.setVisibility(View.GONE);
            mSilverWinerCoinsTV.setVisibility(View.GONE);
            mSliverWinerIV.setVisibility(View.GONE);
            mSilverCrownIV.setVisibility(View.GONE);
            mBrownWinnerNameTV.setVisibility(View.GONE);
            mBrownWinnerCoinsTV.setVisibility(View.GONE);
            mBronzeWinnerIV.setVisibility(View.GONE);
            mBronzeCrownIV.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        AppUtilityMethods.deleteCache(this);
        super.onDestroy();
    }

    private final RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLeagueLayoutManager.getChildCount();
            int totalItemCount = mLeagueLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLeagueLayoutManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private final RecyclerView.OnScrollListener mLudoRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLudoLayoutManager.getChildCount();
            int totalItemCount = mLudoLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLudoLayoutManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private final RecyclerView.OnScrollListener mFanBattleRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mFanBattleManager.getChildCount();
            int totalItemCount = mFanBattleManager.getItemCount();
            int firstVisibleItemPosition = mFanBattleManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private final RecyclerView.OnScrollListener mHTHBattleRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mHTHBattleManager.getChildCount();
            int totalItemCount = mHTHBattleManager.getItemCount();
            int firstVisibleItemPosition = mHTHBattleManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private final RecyclerView.OnScrollListener mLudoAddaRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLudoAddaManger.getChildCount();
            int totalItemCount = mLudoAddaManger.getItemCount();
            int firstVisibleItemPosition = mLudoAddaManger.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private final RecyclerView.OnScrollListener mAllLeaderboardRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mAllLeaderboardManager.getChildCount();
            int totalItemCount = mAllLeaderboardManager.getItemCount();
            int firstVisibleItemPosition = mAllLeaderboardManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= AppConstant.PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        //show bottom progress bar
        mIsLoading = true;
        getData();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mListingType = AppConstant.LEADERBOARD_LISTING_MONTHLY;
            resetListVariables();
            getData();
        } else {
            mListingType = AppConstant.LEADERBOARD_FANBATTLE_LISTING_ALL;
            resetListVariables();
            getData();
        }
    }

    @Override
    public void onBackPressed() {
        AppUtilityMethods.deleteCache(this);
        finish();
        if (mAppPreference.getIsDeepLinking()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    //newGameDialog
    public void showGameDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_game);
        ImageView mCloseIV = dialog.findViewById(R.id.iv_close);
        ImageView mFreeFireIV = dialog.findViewById(R.id.iv_freefire);
        ImageView mBGMIIV = dialog.findViewById(R.id.iv_bgmi);
        ImageView mTDMIV = dialog.findViewById(R.id.iv_tdm);
        ImageView mHTHIV = dialog.findViewById(R.id.iv_hth);
        ImageView mLUDOIV = dialog.findViewById(R.id.iv_ludo);
        ImageView mFANBAttleIV = dialog.findViewById(R.id.fanbattle);
        ImageView mQuizIV = dialog.findViewById(R.id.iv_quiz);
        ImageView mCLSQIV = dialog.findViewById(R.id.iv_clsq);
        ImageView mFREEFIREMAXIV = dialog.findViewById(R.id.iv_ff_max);
        ImageView mPUBGOBALIV = dialog.findViewById(R.id.iv_pubg_gobal);
        ImageView mESPORTSPERIV = dialog.findViewById(R.id.iv_esportsperimum);
        ImageView mLudoAdda = dialog.findViewById(R.id.iv_ludo_universe);
        ImageView mWS = dialog.findViewById(R.id.iv_wordsearch);
        ImageView mDroid = dialog.findViewById(R.id.iv_droid);
        ImageView mLudoTournament = dialog.findViewById(R.id.iv_ludo_tournament);
        ImageView mCourPiece = dialog.findViewById(R.id.iv_courtpiece);
        ImageView mRummy = dialog.findViewById(R.id.iv_rummy);
        mCloseIV.setOnClickListener(v -> {
            dialog.dismiss();
        });
        mFreeFireIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mBGMIIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.PUBG_LITE_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mTDMIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.PUBG_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mHTHIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_HTH_BATTLE;
            setSelectedBackground();
            dialog.dismiss();
        });
        mLUDOIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_LUDO;
            mContestType = AppConstant.TYPE_LUDO;
            setSelectedBackground();
            dialog.dismiss();
        });
        mFANBAttleIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_FAN_BATTLE;
            setSelectedBackground();
            dialog.dismiss();
        });
        mQuizIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_QUIZ;
            setSelectedBackground();
            dialog.dismiss();
        });
        mCLSQIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.FF_CLASH_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mFREEFIREMAXIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.FF_MAX_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mLudoAdda.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_LUDOADDA;
            setSelectedBackground();
            dialog.dismiss();
        });
        mPUBGOBALIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mESPORTSPERIV.setOnClickListener(v -> {
            mType = AppConstant.LEADERBOARD_TYPE_GAME;
            mGameId = mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "");
            setSelectedBackground();
            dialog.dismiss();
        });
        mDroid.setOnClickListener(view -> {
            mType = AppConstant.LEADERBOARD_TYPE_DROID_DO;
            setSelectedBackground();
            dialog.dismiss();
        });
        mLudoTournament.setOnClickListener(view -> {
            mType = AppConstant.LEADERBOARD_TYPE_LUDO_TOURNAMENT;
            setSelectedBackground();
            dialog.dismiss();
        });
        mCourPiece.setOnClickListener(view -> {
            mType = AppConstant.LEADERBOARD_TYPE_COURTPIECE;
            setSelectedBackground();
            dialog.dismiss();
        });
        mRummy.setOnClickListener(view -> {
            mType = AppConstant.LEADERBOARD_TYPE_RUMMY;
            setSelectedBackground();
            dialog.dismiss();
        });
        mWS.setOnClickListener(view -> {
            mType = AppConstant.LEADERBOARD_TYPE_WS;
            setSelectedBackground();
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            mListingType = AppConstant.LEADERBOARD_FANBATTLE_LISTING_ALL;
            resetListVariables();
            getData();
        } else if (i == 1) {
            mListingType = AppConstant.LEADERBOARD_LISTING_DAILY;
            resetListVariables();
            getData();
        } else if (i == 2) {
            mListingType = AppConstant.LEADERBOARD_LISTING_WEEKLY;
            resetListVariables();
            getData();
        } else if (i == 3) {
            mListingType = AppConstant.LEADERBOARD_LISTING_MONTHLY;
            resetListVariables();
            getData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}