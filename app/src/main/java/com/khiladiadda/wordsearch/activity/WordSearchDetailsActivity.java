package com.khiladiadda.wordsearch.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesResponse;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesResponse;
import com.khiladiadda.network.model.response.WordSearchQuizResponse;
import com.khiladiadda.network.model.response.WordSearchStartMainResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingQuizResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.khiladiadda.wallet.WalletCashbackActivity;
import com.khiladiadda.wordsearch.adapter.WordSearchPrizePoolAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchStartPresenter;
import com.khiladiadda.wordsearch.listener.IOnSubClickListener;
import com.khiladiadda.wordsearch.listener.IWordSearchStartPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchStartView;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

public class WordSearchDetailsActivity extends BaseActivity implements IOnSubClickListener, IWordSearchStartView {

    @BindView(R.id.btn_rules)
    AppCompatButton mRulesCl;
    @BindView(R.id.btn_view_participant)
    AppCompatButton mParticipantsCl;
    @BindView(R.id.btn_play)
    AppCompatButton mPlayzCl;
    @BindView(R.id.pb_quiz_progress)
    ProgressBar mJoinedPb;
    @BindView(R.id.rv_prize_pool_breakup)
    RecyclerView mPrizePoolBreakup;
    @BindView(R.id.btn_view_full_pool)
    AppCompatButton mViewAllPool;
    @BindView(R.id.tv_entry)
    TextView mEntryTv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.iv_image)
    ImageView mQuizImage;
    @BindView(R.id.tv_date)
    TextView mEndDate;
    @BindView(R.id.tv_grid)
    TextView mGridTv;
    @BindView(R.id.tv_activity_name)
    TextView mTitleTv;
    @BindView(R.id.tv_progress)
    TextView mProgressesTV;
    @BindView(R.id.tv_prize_money)
    TextView mPrizeMoneyTv;
    @BindView(R.id.tv_participated)
    TextView mParticipatedIv;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIv;
    @BindView(R.id.tv_view_prizepool)
    TextView mViewPrizePoolTv;
    private WordSearchCategoriesQuizzesResponse mCategoriesQuizzesResponse;
    private IWordSearchStartPresenter mStartPresenter;
    private WordSearchQuizResponse mCategoryList;
    private WordSearchMyQuizzesResponse mMyQuizzesResponse;
    private WordSearchTrendingQuizResponse mTrendingQuizResponse;
    private int mType, mBonusCode = 0, mParticipants, mAttempt = 3, mQuizStatus;
    private int mEntryFee;
    private double mTotalWalletBal, mDepositWinWallet, mBonusBal, mBonusPayable, mWalletPayable;
    private boolean mIsPlayed = false, mLowBalance, isPlayedOnce = false, isSuccessfulGameOpen = false, isParticipantsFull = false;
    private String mQuizId, mImageUrl, mDayLeft;
    private Intent launchIntent;
    private String mQuizName, mEndTime, participants;
    private long lasPressTime;
    private String mCategoryName;
    private Dialog mVersionDialog;
    private String mLink;
    private String mVersion;
    private String mCurrentVersion, mGrid;
    private int Attempt;
    private List<PrizePoolBreakthrough> mPrizePoolBreakthroughList;

    @Override
    protected int getContentView() {
        return R.layout.activity_word_search_details;
    }

    @Override
    protected void initViews() {
        mNotificationIv.setVisibility(View.GONE);
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.WordSearchPackageName);
        if (launchIntent != null) getVersion();
        mType = getIntent().getIntExtra(AppConstant.WORD_SEARCH_TYPE, 1);
        if (mType == 1) {
            mTrendingQuizResponse = getIntent().getParcelableExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES);
            mEntryFee = mTrendingQuizResponse.getEntryFees();
            mBonusCode = mTrendingQuizResponse.getBonusCode();
            mQuizId = mTrendingQuizResponse.getId();
            mGrid = "Grid: " + mTrendingQuizResponse.getnRow() + " x " + mTrendingQuizResponse.getnColumns();
            mQuizName = mTrendingQuizResponse.getName();
            mAttempt -= mTrendingQuizResponse.getAttemptedQuiz();
            mImageUrl = mTrendingQuizResponse.getImage();
            mPrizePoolBreakthroughList = mTrendingQuizResponse.getPrizePoolBreakthrough();
            mParticipatedIv.setText("Participants: " + mTrendingQuizResponse.getTotalparticipants());
            mPrizeMoneyTv.setText("Prize: " + mTrendingQuizResponse.getPrizemoney() + " Coins");
            mDayLeft = AppUtilityMethods.getDayLeft(mTrendingQuizResponse.getEnd());
            mEndTime = mTrendingQuizResponse.getEnd();
            mQuizStatus = mTrendingQuizResponse.getQuizStatus();
            mIsPlayed = mTrendingQuizResponse.getAttemptedQuiz() == 0 ? false : true;
            isParticipantsFull = Objects.equals(mTrendingQuizResponse.getPlayedparticipants(), mTrendingQuizResponse.getTotalparticipants());
            participants = "" + mTrendingQuizResponse.getPlayedparticipants() + "/" + mTrendingQuizResponse.getTotalparticipants();
            mParticipants = getPlayedPercentage(mTrendingQuizResponse.getPlayedparticipants(), mTrendingQuizResponse.getTotalparticipants());
            showHideViewMoreButton(mTrendingQuizResponse.getPrizePoolBreakthrough().size());
            mCategoryName = mTrendingQuizResponse.getCategory_name();
        } else if (mType == 2) {
            mCategoryList = getIntent().getParcelableExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES);
            mEntryFee = mCategoryList.getEntryFees();
            mIsPlayed = mCategoryList.getAttemptedQuiz() == 0 ? false : true;
            mQuizId = mCategoryList.getId();
            mAttempt -= mCategoryList.getAttemptedQuiz();
            mImageUrl = mCategoryList.getImage();
            mQuizName = mCategoryList.getName();
            mGrid = "Grid: " + mCategoryList.getnRow() + " x " + mCategoryList.getnColumns();
            mPrizePoolBreakthroughList = mCategoryList.getPrizePoolBreakthrough();
            mParticipatedIv.setText("Participants: " + mCategoryList.getTotalparticipants());
            mPrizeMoneyTv.setText("Prize: " + mCategoryList.getPrizemoney() + " Coins");
            mDayLeft = AppUtilityMethods.getDayLeft(mCategoryList.getEnd());
            mEndTime = mCategoryList.getEnd();
            mQuizStatus = mCategoryList.getQuizStatus();
            isParticipantsFull = Objects.equals(mCategoryList.getPlayedparticipants(), mCategoryList.getTotalparticipants());
            participants = "" + mCategoryList.getPlayedparticipants() + "/" + mCategoryList.getTotalparticipants();
            mParticipants = getPlayedPercentage(mCategoryList.getPlayedparticipants(), mCategoryList.getTotalparticipants());
            showHideViewMoreButton(mCategoryList.getPrizePoolBreakthrough().size());
            mCategoryName = getIntent().getStringExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME);
        } else if (mType == 3) {
            mMyQuizzesResponse = getIntent().getParcelableExtra(AppConstant.WORD_SEARCH_MY_QUIZZES);
            mEntryFee = mMyQuizzesResponse.getEntryFees();
            mIsPlayed = mMyQuizzesResponse.getQuiz().get(0).getAttemptedQuiz() == 0 ? false : true;
            mQuizId = mMyQuizzesResponse.getQuizId();
            mAttempt -= mMyQuizzesResponse.getQuiz().get(0).getAttemptedQuiz();
            mImageUrl = mMyQuizzesResponse.getQuiz().get(0).getImage();
            mQuizName = mMyQuizzesResponse.getQuiz().get(0).getName();
            mGrid = "Grid: " + mMyQuizzesResponse.getQuiz().get(0).getnRow() + " x " + mMyQuizzesResponse.getQuiz().get(0).
                    getnColumns();
            mQuizStatus = mMyQuizzesResponse.getQuiz().get(0).getQuizStatus();
            mPrizePoolBreakthroughList = mMyQuizzesResponse.getQuiz().get(0).getPrizePoolBreakthrough();
            mParticipatedIv.setText("Participants: " + mMyQuizzesResponse.getQuiz().get(0).getTotalparticipants());
            mPrizeMoneyTv.setText("Prize: " + mMyQuizzesResponse.getQuiz().get(0).getPrizemoney() + " Coins");
            isParticipantsFull = Objects.equals(mMyQuizzesResponse.getQuiz().get(0).getPlayedparticipants(), mMyQuizzesResponse.getQuiz().get(0).getTotalparticipants());
            participants = "" + mMyQuizzesResponse.getQuiz().get(0).getPlayedparticipants() + "/" + mMyQuizzesResponse.getQuiz().get(0).getTotalparticipants();
            mDayLeft = AppUtilityMethods.getDayLeft(mMyQuizzesResponse.getQuiz().get(0).getEnd());
            mEndTime = mMyQuizzesResponse.getQuiz().get(0).getEnd();
            mParticipants = getPlayedPercentage(mMyQuizzesResponse.getQuiz().get(0).getPlayedparticipants(), mMyQuizzesResponse.getQuiz().get(0).getTotalparticipants());
            showHideViewMoreButton(mMyQuizzesResponse.getQuiz().get(0).getPrizePoolBreakthrough().size());
            mCategoryName = getIntent().getStringExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME);
        } else {
            mCategoriesQuizzesResponse = getIntent().getParcelableExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES);
            mEntryFee = mCategoriesQuizzesResponse.getEntryFees();
            mIsPlayed = mCategoriesQuizzesResponse.getAttemptedQuiz() == 0 ? false : true;
            mQuizId = mCategoriesQuizzesResponse.getId();
            mPrizeMoneyTv.setText("Prize: " + mCategoriesQuizzesResponse.getPrizemoney() + " Coins");
            mParticipatedIv.setText("Participants: " + mCategoriesQuizzesResponse.getTotalparticipants());
            mAttempt -= mCategoriesQuizzesResponse.getAttemptedQuiz();
            mImageUrl = mCategoriesQuizzesResponse.getImage();
            mQuizName = mCategoriesQuizzesResponse.getName();
            mQuizStatus = mCategoriesQuizzesResponse.getQuizStatus();
            mGrid = "Grid: " + mCategoriesQuizzesResponse.getnRow() + " x " + mCategoriesQuizzesResponse.getnColumns();
            mPrizePoolBreakthroughList = mCategoriesQuizzesResponse.getPrizePoolBreakthrough();
            isParticipantsFull = Objects.equals(mCategoriesQuizzesResponse.getPlayedparticipants(), mCategoriesQuizzesResponse.getTotalparticipants());
            participants = "" + mCategoriesQuizzesResponse.getPlayedparticipants() + "/" + mCategoriesQuizzesResponse.getTotalparticipants();
            mDayLeft = AppUtilityMethods.getDayLeft(mCategoriesQuizzesResponse.getEnd());
            mEndTime = mCategoriesQuizzesResponse.getEnd();
            mParticipants = getPlayedPercentage(mCategoriesQuizzesResponse.getPlayedparticipants(), mCategoriesQuizzesResponse.getTotalparticipants());
            showHideViewMoreButton(mCategoriesQuizzesResponse.getPrizePoolBreakthrough().size());
            mCategoryName = getIntent().getStringExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME);
        }
        mGridTv.setText(mGrid);
        mStartPresenter = new WordSearchStartPresenter(this, mQuizId);
        Glide.with(this).load(mImageUrl).placeholder(R.drawable.wordsearch_placeholder_large).into(mQuizImage);
        if (!AppUtilityMethods.getTimeDiff(mEndTime, Calendar.getInstance().getTime()))
            mEndDate.setText(mDayLeft);
        else mEndDate.setText("Ended");
        mProgressesTV.setText("Filling Fast" + participants);
        mEntryTv.setText("Entry Fee: " + mEntryFee + " Coins");
        mJoinedPb.setProgress(mParticipants);
        mTitleTv.setText(mQuizName);
        if (mAttempt < 3) {
            isPlayedOnce = true;
            mParticipantsCl.setText("Leaderboard");
        } else {
            isPlayedOnce = false;
            mParticipantsCl.setText("Participants");
        }
        if (mQuizStatus != 0) {
            mPlayzCl.setVisibility(View.GONE);
        } else {
            mPlayzCl.setVisibility(View.VISIBLE);
        }
        getWalletData();
        setupRecycler();
    }

    private int getPlayedPercentage(int played, int totalPlayer) {
        return ((played * 100) / totalPlayer);
    }

    @Override
    protected void initVariables() {
        mRulesCl.setOnClickListener(this);
        mPlayzCl.setOnClickListener(this);
        mViewAllPool.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mParticipantsCl.setOnClickListener(this);
        mViewPrizePoolTv.setOnClickListener(this);
        mLink = mAppPreference.getString(AppConstant.WS_LINK, null);
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - lasPressTime < 1000) {
            return;
        }
        lasPressTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.btn_rules:
                startActivity(new Intent(WordSearchDetailsActivity.this, WordSearchRulesActivity.class));
                break;
            case R.id.btn_view_participant:
                openLiveLeaderboard();
                break;
            case R.id.btn_play:
                if (new NetworkStatus(this).isInternetOn()) {
                    if (launchIntent != null) {
                        mCurrentVersion = mAppPreference.getString(AppConstant.WS_VERSION, null);
                        if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                            if (mIsPlayed) {
                                if (mAttempt == 0)
                                    Toast.makeText(this, "Maximum Attempt Exceed !!", Toast.LENGTH_SHORT).show();
                                else getData();
                            } else {
                                if (!isParticipantsFull) {
                                    startGames();
                                } else
                                    Toast.makeText(this, "You can't join because Participants Full", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                        }
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                    }
                } else {
                    Snackbar.make(mPlayzCl, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_view_full_pool:
                if (mViewAllPool.getText().equals("View Less")) {
                    mPrizePoolBreakup.getLayoutParams().height = 280;
                    if (mCategoriesQuizzesResponse != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mCategoriesQuizzesResponse.getPrizePoolBreakthrough()));
                    } else if (mCategoryList != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mCategoryList.getPrizePoolBreakthrough()));
                    } else if (mMyQuizzesResponse != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mMyQuizzesResponse.getQuiz().get(0).getPrizePoolBreakthrough()));
                    } else {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mTrendingQuizResponse.getPrizePoolBreakthrough()));
                    }
                    mViewAllPool.setText("View More");
                } else {
                    mPrizePoolBreakup.getLayoutParams().height = 700;
                    if (mCategoriesQuizzesResponse != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, true, mCategoriesQuizzesResponse.getPrizePoolBreakthrough()));
                    } else if (mCategoryList != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, true, mCategoryList.getPrizePoolBreakthrough()));
                    } else if (mMyQuizzesResponse != null) {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, true, mMyQuizzesResponse.getQuiz().get(0).getPrizePoolBreakthrough()));
                    } else {
                        mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, true, mTrendingQuizResponse.getPrizePoolBreakthrough()));
                    }
                    mViewAllPool.setText("View Less");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_view_prizepool:
                Intent intent = new Intent(this, WordSearchPrizeBreakthroughActivity.class);
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_QUIZ);
                intent.putExtra(AppConstant.MatchName, mQuizName);
                intent.putParcelableArrayListExtra(AppConstant.DATA_QUIZ, (ArrayList<? extends Parcelable>) mPrizePoolBreakthroughList);
                startActivity(intent);
                break;
        }
    }

    private void openLiveLeaderboard() {
        Intent intent;
        if (mQuizStatus == 0) {
            intent = new Intent(WordSearchDetailsActivity.this, WordSearchLeaderBoardActivity.class);
            intent.putExtra(AppConstant.FROM, isPlayedOnce);
        } else {
            intent = new Intent(WordSearchDetailsActivity.this, FinalLeaderBoardActivity.class);
        }
        if (mCategoriesQuizzesResponse != null) {
            intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mCategoriesQuizzesResponse.getId());
        } else if (mCategoryList != null) {
            intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mCategoryList.getId());
        } else if (mMyQuizzesResponse != null) {
            intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mMyQuizzesResponse.getQuizId());
        } else {
            intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mTrendingQuizResponse.getId());
        }
        startActivity(intent);
    }

    private void showHideViewMoreButton(int size) {
        if (size > 3) {
            mViewAllPool.setVisibility(View.VISIBLE);
        } else {
            mViewAllPool.setVisibility(View.GONE);
        }
    }

    private void setupRecycler() {
        mPrizePoolBreakup.setLayoutManager(new LinearLayoutManager(this));
        if (mCategoriesQuizzesResponse != null) {
            mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mCategoriesQuizzesResponse.getPrizePoolBreakthrough()));
        } else if (mCategoryList != null) {
            mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mCategoryList.getPrizePoolBreakthrough()));
        } else if (mMyQuizzesResponse != null) {
            mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mMyQuizzesResponse.getQuiz().get(0).getPrizePoolBreakthrough()));
        } else {
            mPrizePoolBreakup.setAdapter(new WordSearchPrizePoolAdapter(this, false, mTrendingQuizResponse.getPrizePoolBreakthrough()));
        }
    }

    private void startGames() {
        if (launchIntent != null) {
            mCurrentVersion = mAppPreference.getString(AppConstant.WS_VERSION, null);
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                showWalletDialog();
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        }
    }

    private void game() {
        if (mAttempt == 1) {
            Attempt = 2;
        } else if (mAttempt == 2) {
            Attempt = 1;
        } else {
            Attempt = 3;
        }
        String attempt = String.valueOf(Attempt);
        if (launchIntent != null) {
            mCurrentVersion = mAppPreference.getString(AppConstant.WS_VERSION, null);
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                launchIntent.putExtra("userToken", AppSharedPreference.getInstance().getSessionToken());
                launchIntent.putExtra("quizId", mQuizId);
                launchIntent.putExtra("ka_version", AppUtilityMethods.getVersion());
                launchIntent.putExtra("Acount", attempt);
                launchIntent.putExtra("user_id", mAppPreference.getProfileData().getId());
                startActivity(launchIntent);
                isSuccessfulGameOpen = true;
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        }
    }

    private void getWalletData() {
        Coins coins = mAppPreference.getProfileData().getCoins();
        mTotalWalletBal = coins.getDeposit() + coins.getWinning() + coins.getBonus();
        mDepositWinWallet = coins.getDeposit() + coins.getWinning();
        mBonusBal = coins.getBonus();
        if (mBonusCode > 1) {
            mBonusPayable = Math.min(((mEntryFee * mBonusCode) / 100.0), mBonusBal);
            mWalletPayable = mEntryFee - mBonusPayable;
        } else {
            mBonusPayable = 0;
            mWalletPayable = mEntryFee;
        }
        if (mWalletPayable > mDepositWinWallet) {
            mLowBalance = true;
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mStartPresenter.getStartQuiz();
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    public void showWalletDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_wallet_league_quiz);
        final TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        final TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        mEntryFeeTV.setText(new DecimalFormat("##.##").format(mEntryFee));
        final TextView mTotalWalletTV = dialog.findViewById(R.id.tv_total_wallet);
        mTotalWalletTV.setText(getString(R.string.text_total_wallet_bal) + " (" + String.valueOf(new DecimalFormat("##.##").format(mTotalWalletBal)) + ")");
        final TextView mTotalWalletEntryTV = dialog.findViewById(R.id.tv_total_wallet_entry);
        mTotalWalletEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(mEntryFee)));
        final TextView mDepositWinWalletTV = dialog.findViewById(R.id.tv_wallet);
        mDepositWinWalletTV.setText(getString(R.string.text_deposit_winning) + " (" + String.valueOf(new DecimalFormat("##.##").format(mDepositWinWallet)) + ")");
        final TextView mDepositWinEntryTV = dialog.findViewById(R.id.tv_wallet_entry);
        mDepositWinEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(mWalletPayable)));
        final TextView mBonusBalanceTV = dialog.findViewById(R.id.tv_bonus);
        mBonusBalanceTV.setText(getString(R.string.text_bonus) + " (" + String.valueOf(new DecimalFormat("##.##").format(mBonusBal)) + ")");
        final TextView mBonusEntryTV = dialog.findViewById(R.id.tv_bonus_entry);
        mBonusEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(mBonusPayable)));
        final TextView mTotalTV = dialog.findViewById(R.id.tv_total);
        mTotalTV.setText(getString(R.string.text_total_coins));
        final TextView mTotalEntryTV = dialog.findViewById(R.id.tv_total_entry);
        mTotalEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(mEntryFee)));
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        Button okBTN = dialog.findViewById(R.id.btn_send);
        if (mLowBalance) {
            mRechargeTV.setVisibility(View.VISIBLE);
            okBTN.setText(R.string.text_recharge);
        }
        mCancelBTN.setOnClickListener(v -> dialog.dismiss());
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (mLowBalance) {
                startActivity(new Intent(WordSearchDetailsActivity.this, WalletCashbackActivity.class));
            } else {
                getData();
                //AppFlyer
                AppUtilityMethods.appFlyersGameInvestEvent(this, mEntryFeeTV.toString(), AppConstant.WORD_SEARCH);
                //Mo Engage
                Properties mProperties = new Properties();
                mProperties.addAttribute(AppConstant.GAMETYPE, AppConstant.WORD_SEARCH);
                mProperties.addAttribute("EnrtyFee", mEntryFee);
                mProperties.addAttribute("Category Name", mCategoryName);
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.WORD_SEARCH, mProperties);
            }
        });
        dialog.show();
    }

    public void showMsg(final Context activity, String msg, boolean isCancel, boolean status) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(view -> {
            dialog.dismiss();
            if (status) startGames();
        });
        dialog.show();
    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wordsearch_download_popup);
        TextView tv = dialog.findViewById(R.id.textView9);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_download);
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
        ivCross.setOnClickListener(view -> {
            dialog.dismiss();
        });
        iv_playstore.setOnClickListener(view -> {
            if (listener != null) {
                progressBar.setVisibility(View.VISIBLE);
                listener.onDownloadVersion();
                iv_playstore.setVisibility(View.GONE);
                ivCross.setVisibility(View.GONE);
            }
        });
        dialog.show();
        return dialog;
    }

    @Override
    public void onSubItemClick(int pos) {

    }

    @Override
    public void onWordSearchStartComplete(WordSearchStartMainResponse responseModel) {
        hideProgress();
        mIsPlayed = true;
        if (responseModel.isStatus()) {
            --mAttempt;
            if (mAttempt == 3) {
                isPlayedOnce = false;
                mParticipantsCl.setText("Participants");
                showMsg(this, responseModel.getMessage(), false, responseModel.isStatus());
            } else {
                isPlayedOnce = true;
                game();
                mParticipantsCl.setText("Leaderboard");
            }
        } else {
            showMsg(this, responseModel.getMessage(), false, responseModel.isStatus());
        }
    }

    @Override
    public void onWordSearchStartFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.WordSearchPackageName);
        if (launchIntent != null) getVersion();
        else mAppPreference.setBoolean("WSDownload", false);
        if (isSuccessfulGameOpen) {
            openLiveLeaderboard();
            finish();
        }
    }

    private final IOnVesrionDownloadListener mOnVersionListener = new IOnVesrionDownloadListener() {
        @Override
        public void onDownloadVersion() {
            if (mLink != null && !mLink.isEmpty()) {
                new DownloadApk(mOnFileDownloadedListener).execute(mLink);
            }
        }
    };

    private final IOnFileDownloadedListener mOnFileDownloadedListener = new IOnFileDownloadedListener() {
        @Override
        public void onFileDownloaded(String filePath) {
            AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
            ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
            TextView tv = mVersionDialog.findViewById(R.id.textView9);
            tv.setText("Hey You have Successfully downloaded the wordsearch game, Now please click on install button to continue.");
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText("Install Now");
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("WSDownload", true);
                } catch (Exception e) {
                    Toast.makeText(WordSearchDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                progressBar.setProgress(progress);
            }
        }
    };

    private void installApk(String filePath) {
        boolean mIsRequestingAppInstallPermission;
        String mFilePath;
        if (Build.VERSION.SDK_INT >= 26 && !WordSearchDetailsActivity.this.getPackageManager().canRequestPackageInstalls()) {
            startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:com.khiladiadda")));
            mIsRequestingAppInstallPermission = true;
            mFilePath = filePath;
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(new File(filePath));
        } else {
            uri = FileProvider.getUriForFile(WordSearchDetailsActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mFilePath = null;
        mIsRequestingAppInstallPermission = false;
    }

    private void getVersion() {
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.WordSearchPackageName, 0);
            mVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mStartPresenter.destroy();
        super.onDestroy();
    }
}