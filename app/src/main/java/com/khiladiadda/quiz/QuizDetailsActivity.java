package com.khiladiadda.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.league.participant.ParticipantActivity;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;
import com.khiladiadda.quiz.adapters.PrizePoolRVAdapter;
import com.khiladiadda.quiz.list.QuizListPresenter;
import com.khiladiadda.quiz.list.interfaces.IQuizListPresenter;
import com.khiladiadda.quiz.list.interfaces.IQuizListView;
import com.khiladiadda.quiz.result.PrizeBreakthroughActivity;
import com.khiladiadda.quiz.result.QuizResultActivity;
import com.khiladiadda.quiz.splash.QuizStartSplashActivity;
import com.khiladiadda.rule.QuizRuleActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;
import com.khiladiadda.wallet.AddWalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class QuizDetailsActivity extends BaseActivity implements IQuizListView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_image)
    ImageView mImageIV;
    @BindView(R.id.tv_rules)
    Button mRuleBTN;
    @BindView(R.id.btn_play)
    Button mPlayBTN;
    @BindView(R.id.tv_date)
    TextView mDateTV;
    @BindView(R.id.tv_entry)
    TextView mEntryTV;
    @BindView(R.id.tv_participated)
    TextView mParticipatedTV;
    @BindView(R.id.tv_prize_money)
    TextView mPrizeMoneyTV;
    @BindView(R.id.rv_prize_pool_breakup)
    RecyclerView mPrizePoolBreakupRV;
    @BindView(R.id.tv_registration)
    TextView mRegistrationOpenTV;
    @BindView(R.id.btn_view_participant)
    Button mViewParticipatedBTN;
    @BindView(R.id.tv_progress)
    TextView mProgressTV;
    @BindView(R.id.pb_quiz_progress)
    ProgressBar mProgressPB;
    @BindView(R.id.tv_view_prizepool)
    TextView mViewAllPrizePoolTV;

    private IQuizListPresenter mPresenter;
    private QuizListDetails mQuizDetails;
    private String mFrom;
    private long mEntryFee;
    private double mTotalWalletBal, mDepositWinWallet, mBonusBal, mBonusPayable, mWalletPayable;
    private boolean mIsPlayed, mLowBalance;

    @Override
    protected int getContentView() {
        return R.layout.activity_quiz_details;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mRuleBTN.setOnClickListener(this);
        mPlayBTN.setOnClickListener(this);
        mViewParticipatedBTN.setOnClickListener(this);
        mViewAllPrizePoolTV.setOnClickListener(this);
        mQuizDetails = getIntent().getParcelableExtra(AppConstant.DATA);
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
    }

    @Override
    protected void initVariables() {
        mPresenter = new QuizListPresenter(this);
        if (!PermissionUtils.hasStoragePermission(this)) {
            Snackbar.make(mPlayBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
        }
        showProgress(getString(R.string.text_waiting_progress));
        mPresenter.getUserPlayedQuiz(mQuizDetails.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_play:
                if (mFrom.equalsIgnoreCase(AppConstant.LIVE) || mFrom.equalsIgnoreCase(AppConstant.TRENDING)) {
                    if (new NetworkStatus(this).isInternetOn()) {
                        if (PermissionUtils.hasStoragePermission(this)) {
                            if (mIsPlayed) {
                                startPlay();
                            } else {
                                showWalletDialog();
                            }
                        } else {
                            Snackbar.make(mPlayBTN, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(mPlayBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                    }
                } else if (mFrom.equalsIgnoreCase(AppConstant.UPCOMING)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_help_upcoming_live), false);
                }
                break;
            case R.id.tv_rules:
                Intent i = new Intent(this, QuizRuleActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_QUIZ);
                startActivity(i);
                break;
            case R.id.btn_view_participant:
                Intent newScreen;
                if ((mFrom.equalsIgnoreCase(AppConstant.TRENDING) || (mFrom.equalsIgnoreCase(AppConstant.PAST) || mFrom.equalsIgnoreCase(AppConstant.LIVE))) && mIsPlayed) {
                    newScreen = new Intent(this, QuizResultActivity.class);
                    newScreen.putExtra(AppConstant.FROM, AppConstant.QUIZ_DETAILS);
                    newScreen.putExtra(AppConstant.DATA_QUIZ, mQuizDetails);
                } else {
                    newScreen = new Intent(this, ParticipantActivity.class);
                    newScreen.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_QUIZ);
                    newScreen.putExtra(AppConstant.ID, mQuizDetails.getId());
                }
                startActivity(newScreen);
                break;
            case R.id.tv_view_prizepool:
                Intent intent = new Intent(this, PrizeBreakthroughActivity.class);
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_QUIZ);
                intent.putExtra(AppConstant.DATA_QUIZ, mQuizDetails);
                startActivity(intent);
                break;
        }
    }

    private void startPlay() {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.startQuiz(mQuizDetails.getId());
    }

    @Override
    public void onStartQuizComplete(StartQuizResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().getNAttempts() < 4) {
                mAppPreference.setIsProfile(false);
                mAppPreference.setBoolean(AppConstant.IS_QUIZ_PLAYED, true);
                Intent details = new Intent(this, QuizStartSplashActivity.class);
                details.putExtra(AppConstant.DATA, mQuizDetails);
                details.putParcelableArrayListExtra(AppConstant.DATA_QUESTION, responseModel.getQuestionDetailsArrayList());
                if (responseModel.getQuestionDetailsArrayList().size() > 0 && !TextUtils.isEmpty(responseModel.getQuestionDetailsArrayList().get(0).getImage())) {
                    details.putExtra(AppConstant.FROM_QUIZ_QUESTION_IMAGE, true);
                } else {
                    details.putExtra(AppConstant.FROM_QUIZ_QUESTION_IMAGE, false);
                }
                startActivity(details);
                Properties mProperties = new Properties();
                mProperties.addAttribute("Winning",responseModel.getWalletObj().getWinning());
                mProperties.addAttribute("Deposit",responseModel.getWalletObj().getDeposit());
                mProperties.addAttribute("Bonus",responseModel.getWalletObj().getBonus());
               // mProperties.addAttribute("QuizCategoryName",responseModel.getResponse().)
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.QUIZONLY, mProperties);
                finish();
            } else {
                AppUtilityMethods.showMsg(this, getString(R.string.text_played_max_limit), false);
            }
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onStartQuizFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onQuizListComplete(QuizListResponse responseModel) {
    }

    @Override
    public void onQuizListFailure(ApiError error) {
    }

    @Override
    public void onUserPlayedQuizComplete(UserQuizPlayedResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mIsPlayed = responseModel.isPlayed();
            mQuizDetails = responseModel.getResponse();
            mAppPreference.setProfileData(responseModel.getProfile());
        }
        setData();
    }

    @Override
    public void onUserPlayedQuizFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mPlayBTN, getString(R.string.txt_storage_permission), Snackbar.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setData() {
        mActivityNameTV.setText(mQuizDetails.getName());
        if (mQuizDetails.getBonusCode() > 1 && !mIsPlayed && mEntryFee > 0) {
            showBonusPopUp(mQuizDetails.getBonusCode());
        }
        if (mQuizDetails.getImage() != null) {
            Glide.with(this).load(mQuizDetails.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(mImageIV);
        }
        mEntryFee = mQuizDetails.getEntryFees();
        Resources res = getResources();
        mDateTV.setText(String.format(res.getString(R.string.text_quiz_end_time_placeholder), AppUtilityMethods.getQuizRemainingTime(mQuizDetails.getEnd())));
        mEntryTV.setText(String.format(res.getString(R.string.text_entry_fee_placeholder), String.valueOf(mEntryFee), getString(R.string.help_coins)));
        mParticipatedTV.setText(String.format(res.getString(R.string.text_participant_placeholder), String.valueOf(mQuizDetails.getTotalparticipants())));
        mPrizeMoneyTV.setText(String.format(res.getString(R.string.text_prize_placeholder), String.valueOf(mQuizDetails.getPrizemoney()), getString(R.string.help_coins)));
        if (mQuizDetails.getPlayedparticipants() == mQuizDetails.getTotalparticipants()) {
            mProgressTV.setText(getString(R.string.help_filled) + mQuizDetails.getPlayedparticipants() + "/" + mQuizDetails.getTotalparticipants());
            mProgressPB.setProgress(100);
        } else if (mQuizDetails.getPlayedparticipants() == 0) {
            mProgressTV.setText(mQuizDetails.getPlayedparticipants() + "/" + mQuizDetails.getTotalparticipants());
            mProgressPB.setProgress(1);
        } else {
            double divideResult = (double) mQuizDetails.getPlayedparticipants() / (double) mQuizDetails.getTotalparticipants();
            double participant = divideResult * 100;
            mProgressTV.setText(getString(R.string.help_filling_fast) + mQuizDetails.getPlayedparticipants() + "/" + mQuizDetails.getTotalparticipants());
            mProgressPB.setProgress((int) participant);
        }
        if (mQuizDetails.getPrizePoolBreakthrough().size() > 0) {
            PrizePoolRVAdapter mTrendingAdapter = new PrizePoolRVAdapter(mQuizDetails.getPrizePoolBreakthrough());
            mPrizePoolBreakupRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mPrizePoolBreakupRV.setAdapter(mTrendingAdapter);
        } else {
            mPrizePoolBreakupRV.setVisibility(View.GONE);
        }
        if ((mFrom.equalsIgnoreCase(AppConstant.TRENDING) || mFrom.equalsIgnoreCase(AppConstant.LIVE)) && mIsPlayed) {
            mPlayBTN.setText(R.string.text_replay);
            mViewParticipatedBTN.setText(R.string.text_leaderboard);
        } else if (mFrom.equalsIgnoreCase(AppConstant.LIVE) && (mQuizDetails.getTotalparticipants() == mQuizDetails.getPlayedparticipants())) {
            mRegistrationOpenTV.setText(R.string.text_registration_closed);
        } else if (mFrom.equalsIgnoreCase(AppConstant.PAST)) {
            mPlayBTN.setEnabled(false);
            mViewParticipatedBTN.setText(R.string.text_leaderboard);
            mDateTV.setText(AppUtilityMethods.getConvertDateQuiz(mQuizDetails.getStart()) + " - " + AppUtilityMethods.getConvertDateQuiz(mQuizDetails.getEnd()));
        }
        getWalletData();
    }

    private void showBonusPopUp(long bonus) {
        AppUtilityMethods.showMsg(this, "Hey, Congrats!\nYou can use " + bonus + "% of entry fees from bonus wallet in this quiz. Play now and win to earn coins.", false);
    }

    private void getWalletData() {
        Coins coins = mAppPreference.getProfileData().getCoins();
        mTotalWalletBal = coins.getDeposit() + coins.getWinning() + coins.getBonus();
        mDepositWinWallet = coins.getDeposit() + coins.getWinning();
        mBonusBal = coins.getBonus();
        if (mQuizDetails.getBonusCode() > 1) {
            mBonusPayable = Math.min(((mEntryFee * mQuizDetails.getBonusCode()) / 100.0), mBonusBal);
            mWalletPayable = mEntryFee - mBonusPayable;
        } else {
            mBonusPayable = 0;
            mWalletPayable = mEntryFee;
        }
        if (mWalletPayable > mDepositWinWallet) {
            mLowBalance = true;
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
        mTotalWalletEntryTV.setText(new DecimalFormat("##.##").format(mEntryFee));
        final TextView mDepositWinWalletTV = dialog.findViewById(R.id.tv_wallet);
        mDepositWinWalletTV.setText(getString(R.string.text_deposit_winning) + " (" + String.valueOf(new DecimalFormat("##.##").format(mDepositWinWallet)) + ")");
        final TextView mDepositWinEntryTV = dialog.findViewById(R.id.tv_wallet_entry);
        mDepositWinEntryTV.setText(new DecimalFormat("##.##").format(mWalletPayable));
        final TextView mBonusBalanceTV = dialog.findViewById(R.id.tv_bonus);
        mBonusBalanceTV.setText(getString(R.string.text_bonus) + " (" + String.valueOf(new DecimalFormat("##.##").format(mBonusBal)) + ")");
        final TextView mBonusEntryTV = dialog.findViewById(R.id.tv_bonus_entry);
        mBonusEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(mBonusPayable)));
        final TextView mTotalTV = dialog.findViewById(R.id.tv_total);
        mTotalTV.setText(getString(R.string.text_total_coins));
        final TextView mTotalEntryTV = dialog.findViewById(R.id.tv_total_entry);
        mTotalEntryTV.setText(new DecimalFormat("##.##").format(mEntryFee));
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
                startActivity(new Intent(QuizDetailsActivity.this, AddWalletActivity.class));
            } else {
                startPlay();
                //Apps Flyer
                Map<String, Object> eventParameters2 = new HashMap<>();
                eventParameters2.put(AFInAppEventParameterName.REVENUE, mEntryFee); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
                eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
                eventParameters2.put(AppConstant.GAME, AppConstant.QUIZONLY);
                eventParameters2.put(AppConstant.EntryFee, mEntryFee);
                /*
                 * Send af_purchase event.
                 * Trigger: User lands on the thank you page after a successful purchase
                 */
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
                //Mo Engage
                 Properties mProperties = new Properties();
                mProperties.addAttribute(AppConstant.GAMETYPE,  AppConstant.QUIZONLY);
                MoEAnalyticsHelper.INSTANCE.trackEvent(this,  AppConstant.QUIZONLY, mProperties);

            }
        });
        dialog.show();
    }

}