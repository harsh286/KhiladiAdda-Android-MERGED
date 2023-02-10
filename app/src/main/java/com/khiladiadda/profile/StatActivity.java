package com.khiladiadda.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnRedeemVoucherListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.NFanBattle;
import com.khiladiadda.network.model.response.NLudo;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.profile.interfaces.IProfilePresenter;
import com.khiladiadda.profile.interfaces.IProfileView;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;

public class StatActivity extends BaseActivity implements IOnRedeemVoucherListener, IProfileView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_ludo_wins)
    TextView mLudoWinsTV;
    @BindView(R.id.tv_ludo_loses)
    TextView mLudoLosesTV;
    @BindView(R.id.tv_snake_contest)
    TextView mSnakeContestTV;
    @BindView(R.id.tv_snake_wins)
    TextView mSnakeWinsTV;
    @BindView(R.id.tv_snake_loses)
    TextView mSnakeLosesTV;
    @BindView(R.id.tv_ludo_contest)
    TextView mLudoContestTV;
    @BindView(R.id.tv_quiz_contest)
    TextView mQuizContestTV;
    @BindView(R.id.tv_quiz_wins)
    TextView mQuizWinsTV;
    @BindView(R.id.tv_quiz_loses)
    TextView mQuizLosesTV;
    @BindView(R.id.tv_game_contest)
    TextView mGameContestTV;
    @BindView(R.id.tv_game_wins)
    TextView mGameWinsTV;
    @BindView(R.id.tv_game_loses)
    TextView mGameLosesTV;
    @BindView(R.id.btn_voucher)
    Button mVoucherBTN;
    @BindView(R.id.tv_cx_contest)
    TextView mCXContestTV;
    @BindView(R.id.tv_cx_wins)
    TextView mCXWinsTV;
    @BindView(R.id.tv_cx_loses)
    TextView mCXLosesTV;
    @BindView(R.id.tv_fb_contest)
    TextView mFBContestTV;
    @BindView(R.id.tv_fb_wins)
    TextView mFBWinsTV;
    @BindView(R.id.tv_fb_loses)
    TextView mFbLosesTV;
    @BindView(R.id.tv_ws_contest)
    TextView mWSContestTV;
    @BindView(R.id.tv_ws_wins)
    TextView mWSWinsTV;
    @BindView(R.id.tv_ws_loses)
    TextView mWSLosesTV;
    @BindView(R.id.tv_droid_contest)
    TextView mDroidContestTV;
    @BindView(R.id.tv_droid_wins)
    TextView mDroidWinsTV;
    @BindView(R.id.tv_droid_loses)
    TextView mDroidLosesTV;

    @BindView(R.id.ludotmt_contest)
    TextView mLudoTmtContestTv;
    @BindView(R.id.ludotmt_wins)
    TextView getmLudoTmtWinsTV;
    @BindView(R.id.ludotmt_loses)
    TextView getmLudoTmtLosesTV;

    @BindView(R.id.codepieces_contest)
    TextView mCodePiecesContestTv;
    @BindView(R.id.codepieces_wins)
    TextView mCodePiecesWinTv;
    @BindView(R.id.codepieces_loses)
    TextView mCodePiecesLosesTv;

    @BindView(R.id.rummy_contest)
    TextView mRummyContestTv;
    @BindView(R.id.rummy_wins)
    TextView mRummyWinTv;
    @BindView(R.id.rummy_loses)
    TextView mRummyLoseTv;

    private IProfilePresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_stat;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.my_stats);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mVoucherBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new ProfilePresenter(this);
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getProfile();
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
            case R.id.btn_voucher:
                AppDialog.showReedemVoucher(this);
                break;
        }
    }

    private void setGameLoss(NLudo game, TextView gameTV) {
        SpannableString gameLost = new SpannableString("Losses\n" + game.getLost());
        gameLost.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)), 6, gameLost.length(), 0);
        gameLost.setSpan(new StyleSpan(Typeface.BOLD), 6, gameLost.length(), 0);
        gameLost.setSpan(new RelativeSizeSpan(1.2f), 6, gameLost.length(), 0);
        gameTV.setText(gameLost);
    }

    private void setGameWon(NLudo game, TextView gameTV) {
        SpannableString ludoWin = new SpannableString("Wins\n" + game.getWon());
        ludoWin.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.color_green)), 4, ludoWin.length(), 0);
        ludoWin.setSpan(new StyleSpan(Typeface.BOLD), 4, ludoWin.length(), 0);
        ludoWin.setSpan(new RelativeSizeSpan(1.2f), 4, ludoWin.length(), 0);
        gameTV.setText(ludoWin);
    }

    private void setGameContest(NLudo game, TextView gameTV) {
        SpannableString ludpContest = new SpannableString("Contests\n" + (game.getWon() + game.getLost()));
        ludpContest.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blue_dark)), 8, ludpContest.length(), 0);
        ludpContest.setSpan(new StyleSpan(Typeface.BOLD), 8, ludpContest.length(), 0);
        ludpContest.setSpan(new RelativeSizeSpan(1.2f), 8, ludpContest.length(), 0);
        gameTV.setText(ludpContest);
    }

    @Override
    public void onApplyVoucherComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onApplyVoucherFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onSendOtpComplete(BaseResponse responseModel) {

    }

    @Override
    public void onSendOtpFailure(ApiError error) {

    }

    @Override
    public void onVerifyEmailComplete(BaseResponse responseModel) {

    }

    @Override
    public void onVerifyEmailFailure(ApiError error) {

    }

    @Override
    public void onUpdateEmailComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdateEmailFailure(ApiError error) {

    }

    @Override
    public void voucher(String number, String pin) {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.applyVocher(number, pin);
    }

    @Override
    public void onUpdatePasswordComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdatePasswordFailure(ApiError error) {

    }

    @Override
    public void onUpdateDOBComplete(ProfileResponse responseModel) {

    }

    @Override
    public void onUpdateDOBFailure(ApiError error) {

    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getResponse());
        ProfileDetails profileData = responseModel.getResponse();

        setGameContest(profileData.getnLudo(), mLudoContestTV);
        setGameContest(profileData.getnSnake(), mSnakeContestTV);
        setGameContest(profileData.getNQuiz(), mQuizContestTV);
        setGameContest(profileData.getNGame(), mGameContestTV);
        setGameContest(profileData.getnClashOfFans(), mCXContestTV);
        setGameContest(profileData.getnWordSearch(), mWSContestTV);
        setGameContest(profileData.getnGKite(), mDroidContestTV);
        setGameContest(profileData.getNQuiz(), mLudoTmtContestTv);
        setGameContest(profileData.getnRummy(), mRummyContestTv);
        setGameContest(profileData.getnCallBreak(), mCodePiecesContestTv);

        setGameWon(profileData.getnLudo(), mLudoWinsTV);
        setGameWon(profileData.getnSnake(), mSnakeWinsTV);
        setGameWon(profileData.getNQuiz(), mQuizWinsTV);
        setGameWon(profileData.getNGame(), mGameWinsTV);
        setGameWon(profileData.getnClashOfFans(), mCXWinsTV);
        setGameWon(profileData.getnWordSearch(), mWSWinsTV);
        setGameWon(profileData.getnGKite(), mDroidWinsTV);
        setGameWon(profileData.getnLudoTournament(), getmLudoTmtWinsTV);
        setGameWon(profileData.getnRummy(), mRummyWinTv);
        setGameWon(profileData.getnCallBreak(), mCodePiecesWinTv);

        setGameLoss(profileData.getnLudo(), mLudoLosesTV);
        setGameLoss(profileData.getnSnake(), mSnakeLosesTV);
        setGameLoss(profileData.getNQuiz(), mQuizLosesTV);
        setGameLoss(profileData.getNGame(), mGameLosesTV);
        setGameLoss(profileData.getnClashOfFans(), mCXLosesTV);
        setGameLoss(profileData.getnWordSearch(), mWSLosesTV);
        setGameLoss(profileData.getnGKite(), mDroidLosesTV);
        setGameLoss(profileData.getnLudoTournament(), getmLudoTmtLosesTV);
        setGameLoss(profileData.getnRummy(), mRummyLoseTv);
        setGameLoss(profileData.getnCallBreak(), mCodePiecesLosesTv);

        setFanBattleGameContest(profileData.getnFanBattle());

        hideProgress();
    }

    private void setFanBattleGameContest(NFanBattle getnFanBattle) {
        if (getnFanBattle.getPlayed() < 0) {
            SpannableString ludpContest = new SpannableString("Contests\n" + 0);
            ludpContest.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.blue_dark)), 8, ludpContest.length(), 0);
            ludpContest.setSpan(new StyleSpan(Typeface.BOLD), 8, ludpContest.length(), 0);
            ludpContest.setSpan(new RelativeSizeSpan(1.2f), 8, ludpContest.length(), 0);
            mFBContestTV.setText(ludpContest);

        } else {
            SpannableString ludpContest = new SpannableString("Contests\n" + getnFanBattle.getPlayed());
            ludpContest.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.blue_dark)), 8, ludpContest.length(), 0);
            ludpContest.setSpan(new StyleSpan(Typeface.BOLD), 8, ludpContest.length(), 0);
            ludpContest.setSpan(new RelativeSizeSpan(1.2f), 8, ludpContest.length(), 0);
            mFBContestTV.setText(ludpContest);

        }


        if (getnFanBattle.getWon() < 0) {
            SpannableString ludoWin = new SpannableString("Wins\n" + 0);
            ludoWin.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_green)), 4, ludoWin.length(), 0);
            ludoWin.setSpan(new StyleSpan(Typeface.BOLD), 4, ludoWin.length(), 0);
            ludoWin.setSpan(new RelativeSizeSpan(1.2f), 4, ludoWin.length(), 0);
            mFBWinsTV.setText(ludoWin);
        } else {
            SpannableString ludoWin = new SpannableString("Wins\n" + getnFanBattle.getWon());
            ludoWin.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_green)), 4, ludoWin.length(), 0);
            ludoWin.setSpan(new StyleSpan(Typeface.BOLD), 4, ludoWin.length(), 0);
            ludoWin.setSpan(new RelativeSizeSpan(1.2f), 4, ludoWin.length(), 0);
            mFBWinsTV.setText(ludoWin);
        }


        if (getnFanBattle.getLost() < 0) {
            SpannableString gameLost = new SpannableString("Losses\n" + getnFanBattle.getLost());
            gameLost.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 6, gameLost.length(), 0);
            gameLost.setSpan(new StyleSpan(Typeface.BOLD), 6, gameLost.length(), 0);
            gameLost.setSpan(new RelativeSizeSpan(1.2f), 6, gameLost.length(), 0);
            mFbLosesTV.setText(gameLost);
        } else {
            SpannableString gameLost = new SpannableString("Losses\n" + getnFanBattle.getLost());
            gameLost.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 6, gameLost.length(), 0);
            gameLost.setSpan(new StyleSpan(Typeface.BOLD), 6, gameLost.length(), 0);
            gameLost.setSpan(new RelativeSizeSpan(1.2f), 6, gameLost.length(), 0);
            mFbLosesTV.setText(gameLost);
        }

    }

    @Override
    public void onProfileFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

}