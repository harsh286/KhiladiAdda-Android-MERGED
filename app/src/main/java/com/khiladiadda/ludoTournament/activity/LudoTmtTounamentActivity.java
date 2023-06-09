package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.ludoTournament.ip.ILudoTmtDetailsView;
import com.khiladiadda.ludoTournament.ip.ILudoTmtRoundsView;
import com.khiladiadda.ludoTournament.ip.LudoTmtJoinPresenter;
import com.khiladiadda.ludoTournament.ip.LudoTmtRoundsPresenter;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.ludoUniverse.MyLudoUniverseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.splash.SplashActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.BindView;

public class LudoTmtTounamentActivity extends BaseActivity implements ILudoTmtDetailsView, ILudoTmtRoundsView,
        IOnClickListener {

    @BindView(R.id.img_rules)
    ImageView rulesImg;
    @BindView(R.id.iv_back)
    ImageView backIv;
    @BindView(R.id.cl_match_close)
    ConstraintLayout matchCloseCl;
    @BindView(R.id.mcv_join)
    MaterialCardView mcvJoin;
    @BindView(R.id.tv_entry_fee)
    TextView entryFee;
    @BindView(R.id.tv_rounds)
    TextView roundsTv;
    @BindView(R.id.tv_start_time)
    TextView startTimeTv;
    @BindView(R.id.cl_join_tournaments)
    ConstraintLayout joinTournamentCl;
    @BindView(R.id.tv_total_participants_new)
    TextView totalParticipantsNew;
    @BindView(R.id.pb_joined)
    ProgressBar joinedPb;
    @BindView(R.id.tv_hindi_me_dekhe)
    TextView hindiDekheTv;
    @BindView(R.id.tv_view_in_english)
    TextView viewInEnglishTv;
    @BindView(R.id.tv_win_english)
    TextView winEnglishTv;
    @BindView(R.id.tv_lose_english)
    TextView loseEnglishTv;
    @BindView(R.id.tv_win_hindi)
    TextView winHindiTv;
    @BindView(R.id.tv_lose_hindi)
    TextView loseHindiTv;
    @BindView(R.id.tv_tipsHindi)
    TextView tipsInfoHindi;
    @BindView(R.id.tvTipsEnglish)
    TextView tipsInfoEnglish;
    @BindView(R.id.tv_rounds_)
    TextView RoundsTv;
    @BindView(R.id.tv_match)
    TextView matchTv;
    @BindView(R.id.tv_first_player)
    TextView firstPlayerTv;
    @BindView(R.id.tv_second_player)
    TextView secondPlayerTv;
    @BindView(R.id.iv_first_player)
    ImageView firstPlayerIv;
    @BindView(R.id.iv_second_player)
    ImageView secondPlayerIv;
    @BindView(R.id.cl_rounds)
    ConstraintLayout RoundsCl;
    @BindView(R.id.btn_status)
    MaterialCardView statusBtn;
    @BindView(R.id.btn_waiting)
    MaterialCardView waitingBtn;
    @BindView(R.id.btn_won)
    MaterialCardView wonBtn;
    @BindView(R.id.btn_opp_won)
    MaterialCardView oppWonBtn;
    @BindView(R.id.btn_play_now)
    MaterialCardView playNowBtn;
    @BindView(R.id.tv_win_prize)
    TextView winPrizeTv;
    @BindView(R.id.tv_view_all_rounds)
    TextView viewAllRoundTv;
    @BindView(R.id.cl_out_of_ludotmt)
    ConstraintLayout outOfLudoTmtCl;
    @BindView(R.id.tv_estimated_time)
    TextView mEstimatedTimeTv;
    @BindView(R.id.btn_refresh)
    MaterialCardView mRefreshBtn;
    @BindView(R.id.iv_refresh)
    ImageView mRefreshIv;
    @BindView(R.id.tv_view_more)
    TextView mViewMore;

    private Context context;
    private Activity activity;
    private IOnClickListener onClickListener;
    private LudoTmtJoinPresenter mPresenter;
    private LudoTmtRoundsPresenter mRoundPresenter;
    private LudoTmtAllTournamentResponse matchDetailsResponse;
    private LudoTmtMyMatchResponse ludoTmtMyMatchResponse;
    private String mVersion, mCurrentVersion, mLink, mFilePath;
    private Intent launchIntent;
    private Dialog mVersionDialog;
    private double mDepositWinWallet;
    private double mTotalWalletBal;
    private int gameMode = 1, mFromUnity = 0, mNoOfTime = 1;
    private boolean mIsRequestingAppInstallPermission;
    private LudoTmtRoundsDetailsMainResponse mDataResponse;
    private String tournamentId, startTime;
    private Integer mEntryFee, mPrize;
    private boolean isLive;
    private Dialog dialog;
    private Dialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtNotificationRoomReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtNotificationMatchLiveReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtJoinLobbyReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtFullLobbyReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_tounament;
    }

    @Override
    protected void initViews() {
        context = this;
        activity = this;
        onClickListener = this;
        AppSharedPreference.initialize(this);
        mPresenter = new LudoTmtJoinPresenter(this);
        mRoundPresenter = new LudoTmtRoundsPresenter(this);
        if (getIntent().getParcelableExtra("AllLudoTournaments") != null) {
            matchDetailsResponse = getIntent().getParcelableExtra("AllLudoTournaments");
            gameMode = getIntent().getIntExtra("gameMode", 1);
            winPrizeTv.setText("WIN ₹" + matchDetailsResponse.getPrize());
            mPrize = matchDetailsResponse.getPrize();
            mEntryFee = matchDetailsResponse.getEntryFees();
        } else {
            ludoTmtMyMatchResponse = getIntent().getParcelableExtra("MyLudoTournaments");
            isLive = getIntent().getBooleanExtra("isMatchLive", false);
            gameMode = ludoTmtMyMatchResponse.gettType();
            winPrizeTv.setText("WIN ₹" + ludoTmtMyMatchResponse.getPrize());
            mPrize = ludoTmtMyMatchResponse.getPrize();
            mEntryFee = ludoTmtMyMatchResponse.getEntryFees();
        }
        mLink = AppSharedPreference.getInstance().getVersion().getVersion().getLudoApkLink();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        mCurrentVersion = AppSharedPreference.getInstance().getVersion().getVersion().getLudoAddaVersion();
        Coins mCoins = AppSharedPreference.getInstance().getProfileData().getCoins();
        mDepositWinWallet = mCoins.getDeposit() + mCoins.getWinning();
        mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        getJoined();
        setupUi();
        if (isLive) {
            AppDialog.showDisclaimerDialog(this, AppConstant.LUDO_TOURNAMENT_DISCLAMIER);
        }
    }

    @Override
    protected void initVariables() {
        rulesImg.setOnClickListener(this);
        mcvJoin.setOnClickListener(this);
        backIv.setOnClickListener(this);
        joinTournamentCl.setOnClickListener(this);
        hindiDekheTv.setOnClickListener(this);
        viewInEnglishTv.setOnClickListener(this);
        playNowBtn.setOnClickListener(this);
        statusBtn.setOnClickListener(this);
        viewAllRoundTv.setOnClickListener(this);
        mRefreshBtn.setOnClickListener(this);
        mViewMore.setOnClickListener(this);
    }

    private void getCheck() {
        if (matchDetailsResponse != null) {
            if (matchDetailsResponse.getnParticipants() == matchDetailsResponse.getnParticipated()) {
                mcvJoin.setVisibility(View.INVISIBLE);
            }
        } else {
            if (ludoTmtMyMatchResponse.getnParticipants() == ludoTmtMyMatchResponse.getnParticipated()) {
                mcvJoin.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View p0) {
        switch (p0.getId()) {
            case R.id.img_rules:
                AppUtilityMethods.showTooltipFromImage(this, rulesImg, getString(R.string.english_rules), gameMode);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.mcv_join:
            case R.id.cl_join_tournaments:
                launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
                if (launchIntent != null) getVersion();
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        if (matchDetailsResponse != null) {
                            showConfirmation(Double.parseDouble(matchDetailsResponse.getEntryFees().toString()));
                        } else {
                            showConfirmation(Double.parseDouble(ludoTmtMyMatchResponse.getEntryFees().toString()));
                        }
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
                break;
            case R.id.tv_hindi_me_dekhe:
                viewInEnglishTv.setVisibility(View.VISIBLE);
                winEnglishTv.setVisibility(View.GONE);
                loseEnglishTv.setVisibility(View.GONE);
                hindiDekheTv.setVisibility(View.GONE);
                tipsInfoHindi.setVisibility(View.VISIBLE);
                tipsInfoEnglish.setVisibility(View.INVISIBLE);
                winHindiTv.setVisibility(View.VISIBLE);
                loseHindiTv.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_view_in_english:
                viewInEnglishTv.setVisibility(View.GONE);
                winEnglishTv.setVisibility(View.VISIBLE);
                loseEnglishTv.setVisibility(View.VISIBLE);
                tipsInfoHindi.setVisibility(View.INVISIBLE);
                tipsInfoEnglish.setVisibility(View.VISIBLE);
                hindiDekheTv.setVisibility(View.VISIBLE);
                winHindiTv.setVisibility(View.INVISIBLE);
                loseHindiTv.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_play_now:
                if (matchDetailsResponse != null) {
                    if (!Objects.equals(mDataResponse.getResponse().get(0).getUserFirst(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                        launchUnityWithData(mDataResponse.getResponse().get(0).getId(), matchDetailsResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(matchDetailsResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(0).getUserFirstInfo().getRandomName(), "" + mDataResponse.getResponse().get(0).getUserFirstInfo().getRandomDp(), tournamentId);
                    else
                        launchUnityWithData(mDataResponse.getResponse().get(0).getId(), matchDetailsResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(matchDetailsResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(0).getUserSecondInfo().getRandomName(), "" + mDataResponse.getResponse().get(0).getUserSecondInfo().getRandomDp(), tournamentId);
                } else {
                    if (!Objects.equals(mDataResponse.getResponse().get(0).getUserFirst(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                        launchUnityWithData(mDataResponse.getResponse().get(0).getId(), ludoTmtMyMatchResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(ludoTmtMyMatchResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(0).getUserFirstInfo().getRandomName(), "" + mDataResponse.getResponse().get(0).getUserFirstInfo().getRandomDp(), tournamentId);
                    else
                        launchUnityWithData(mDataResponse.getResponse().get(0).getId(), ludoTmtMyMatchResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(ludoTmtMyMatchResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(0).getUserSecondInfo().getRandomName(), "" + mDataResponse.getResponse().get(0).getUserSecondInfo().getRandomDp(), tournamentId);
                }
                break;
            case R.id.btn_status:
                AppDialog.showAlertDialog(this, "You have to wait till start time to play the tournament.");
                break;
            case R.id.tv_view_all_rounds:
                Intent intent = new Intent(this, LudoTmtAllRoundActivity.class);
                if (matchDetailsResponse != null)
                    intent.putExtra("AllLudoTournaments", matchDetailsResponse);
                else
                    intent.putExtra("MyLudoTournaments", ludoTmtMyMatchResponse);
                startActivity(intent);
                break;
            case R.id.btn_refresh:
                rotate(180);
                callRoundsApi();
                break;
            case R.id.tv_view_more:
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getJoined() {
        if (matchDetailsResponse != null) {
            if (matchDetailsResponse.isJoined()) {
                mcvJoin.setVisibility(View.GONE);
                callRoundsApi();
            } else {
                mcvJoin.setVisibility(View.VISIBLE);
            }
        } else {
            mcvJoin.setVisibility(View.GONE);
            callRoundsApi();
        }
    }

    private void setupUi() {
        if (matchDetailsResponse != null) {
            entryFee.setText(String.format("%s Coins", matchDetailsResponse.getEntryFees().toString()));
            roundsTv.setText("" + matchDetailsResponse.getTtLevel());
            startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(matchDetailsResponse.getStartDate()));
            startTime = AppUtilityMethods.getConvertDateTimeMatch(matchDetailsResponse.getStartDate());
            mEstimatedTimeTv.setText("Estimated End Time of Tournament: " + AppUtilityMethods.getConvertDateTimeMatch(matchDetailsResponse.getStartDate()));
            totalParticipantsNew.setText(matchDetailsResponse.getnParticipated() + "/" + matchDetailsResponse.getnParticipants());
            joinedPb.setProgress(matchDetailsResponse.getnParticipated());
            joinedPb.setMax(matchDetailsResponse.getnParticipants());
        } else {
            entryFee.setText(String.format("%s Coins", ludoTmtMyMatchResponse.getEntryFees().toString()));
            roundsTv.setText("" + ludoTmtMyMatchResponse.getTtLevel());
            startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(ludoTmtMyMatchResponse.getStartDate()));
            startTime = AppUtilityMethods.getConvertDateTimeMatch(ludoTmtMyMatchResponse.getStartDate());
            mEstimatedTimeTv.setText("Estimated End Time of Tournament: " + AppUtilityMethods.getConvertDateTimeMatch(ludoTmtMyMatchResponse.getStartDate()));
            totalParticipantsNew.setText(ludoTmtMyMatchResponse.getnParticipated() + "/" + ludoTmtMyMatchResponse.getnParticipants());
            joinedPb.setProgress(ludoTmtMyMatchResponse.getnParticipated());
            joinedPb.setMax(ludoTmtMyMatchResponse.getnParticipants());
        }
    }

    private void callJoinApi() {
        if (new NetworkStatus(this).isInternetOn()) {
            progressBar = AppDialog.getAppProgressDialogWithMessage(this, "Please wait...");
            progressBar.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (matchDetailsResponse != null) {
                        mPresenter.onJoinTournament(matchDetailsResponse.getId());
                    } else {
                        mPresenter.onJoinTournament(ludoTmtMyMatchResponse.getId());
                    }
                }
            }, 5000);

        } else {
            Snackbar.make(backIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void callRoundsApi() {
//        showProgress("");
        if (new NetworkStatus(this).isInternetOn()) {
            if (matchDetailsResponse != null) {
                mRoundPresenter.getTournamentRounds(matchDetailsResponse.getId());
            } else {
                mRoundPresenter.getTournamentRounds(ludoTmtMyMatchResponse.getId());
            }
        } else {
            Snackbar.make(backIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJoinLudoTournamentComplete(LudoTmtJoinMainResponse response) {
        if (response.isStatus()) {
            Snackbar.make(backIv, response.getMessage(), Snackbar.LENGTH_SHORT).show();
            mcvJoin.setVisibility(View.INVISIBLE);
            joinedPb.setProgress(matchDetailsResponse.getnParticipated() + 1);
            joinedPb.setMax(matchDetailsResponse.getnParticipants());
            totalParticipantsNew.setText((matchDetailsResponse.getnParticipated() + 1) + "/" + matchDetailsResponse.getnParticipants());
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mEntryFee); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, AppConstant.LUDO_TOURNAMENT);
            eventParameters2.put(AppConstant.EntryFee, mEntryFee);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties mProperties = new Properties();
            mProperties.addAttribute(AppConstant.GAMETYPE, AppConstant.LUDO_TOURNAMENT);
            mProperties.addAttribute("EnrtyFee", mEntryFee);
            if (gameMode == 1) {
                mProperties.addAttribute("Category Name", AppConstant.LT_CLASSIC);
            } else if (gameMode == 2) {
                mProperties.addAttribute("Category Name", AppConstant.LT_TIMER);
            } else {
                mProperties.addAttribute("Category Name", AppConstant.LT_SERIES);
            }
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.LUDO_TOURNAMENT, mProperties);
//            callRoundsApi();
            progressBar.dismiss();
//            hideProgress();
            AppUtilityMethods.showMsgWithCancel(this, this, "You have successfully joined the tournament. Please be ready for the tournament at the start time.", true);
//            finish();
        } else {
            hideProgress();
            progressBar.dismiss();
            Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
            mcvJoin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onJoinLudoTournamentFailure(ApiError errorMsg) {
        hideProgress();
        Toast.makeText(this, "" + errorMsg.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetRoundsTournamentComplete(LudoTmtRoundsDetailsMainResponse response) {
        if (response.isStatus()) {
            mDataResponse = response;
            tournamentId = response.getTournamentDetails().getId();
            if (isLive && response.getResponse().isEmpty()) {
                outOfLudoTmtCl.setVisibility(View.VISIBLE);
            } else {
                outOfLudoTmtCl.setVisibility(View.GONE);
            }
            if (!response.getResponse().isEmpty()) {
                viewAllRoundTv.setVisibility(View.VISIBLE);
                RoundsCl.setVisibility(View.VISIBLE);
                setRoundData(response);
            } else {
                if (response.getTournamentDetails().getOut()) {
                    outOfLudoTmtCl.setVisibility(View.VISIBLE);
                } else {
                    outOfLudoTmtCl.setVisibility(View.GONE);
                }
                if (response.getTournamentDetails().gettStatus() == 1 || response.getTournamentDetails().gettStatus() == 0 || response.getTournamentDetails().gettStatus() == 3)
                    viewAllRoundTv.setVisibility(View.VISIBLE);
                else
                    viewAllRoundTv.setVisibility(View.GONE);

                RoundsCl.setVisibility(View.GONE);
            }
        } else {
            AppDialog.showStatusFailureDialog(this, response.getMessage());
        }
        hideProgress();
    }

    @Override
    public void onGetRoundsTournamentFailure(ApiError errorMsg) {
        hideProgress();

    }

    private void setRoundData(LudoTmtRoundsDetailsMainResponse response) {
        LudoTmtRoundsDetailsResponse item = response.getResponse().get(0);
        RoundsTv.setText(String.format("ROUND %s", response.getResponse().get(0).getLevel().toString()));
//        matchTv.setText(String.format("MATCH %s", response.getTournamentDetails().getTtMatch().toString()));
        Glide.with(firstPlayerIv.getContext()).load(response.getResponse().get(0).getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(firstPlayerIv);
        if (Objects.equals(item.getUserFirst(), AppSharedPreference.initialize(this).getMasterData().getResponse().getProfile().getId())) {
            firstPlayerTv.setText("You");
            Glide.with(firstPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(firstPlayerIv);
        } else {
            firstPlayerTv.setText(item.getUserFirstInfo().getRandomName());
            Glide.with(firstPlayerIv.getContext()).load(item.getUserFirstInfo().getRandomDp()).fallback(R.drawable.profile).into(firstPlayerIv);
        }
        if (Objects.equals(item.getUserSecond(), AppSharedPreference.initialize(this).getMasterData().getResponse().getProfile().getId())) {
            secondPlayerTv.setText("You");
            Glide.with(secondPlayerIv.getContext()).load(item.getUserSecondInfo().getDp()).fallback(R.drawable.profile).into(secondPlayerIv);
        } else {
            secondPlayerTv.setText(item.getUserSecondInfo().getRandomName());
            Glide.with(secondPlayerIv.getContext()).load(item.getUserSecondInfo().getRandomDp()).fallback(R.drawable.profile).into(secondPlayerIv);
        }
        playNowBtn.setVisibility(View.GONE);
//        if (mDataResponse.getResponse().get(0).getLevel() != 1 && mDataResponse.isParticipantsFull()) {
//            playNowBtn.setVisibility(View.VISIBLE);
//        } else {
        if (item.getRoomStatus() == 1) {
            secondPlayerTv.setText("waiting...");
            ButtonEnable(item, 0, 1, 0, 0, 0);
            Glide.with(secondPlayerIv.getContext()).load(R.drawable.profile).fallback(R.drawable.profile).into(secondPlayerIv);
        } else if (item.getRoomStatus() == 2) {
            ButtonEnable(item, 1, 0, 0, 0, 0);
        } else if (item.getRoomStatus() == 3) {
            ButtonEnable(item, 0, 0, 1, 0, 0);
        } else if (item.getRoomStatus() == 4) {
            ButtonEnable(item, 0, 0, 0, 1, 0);
        } else {
            ButtonEnable(item, 0, 0, 0, 0, 1);
        }
//        }
    }

    void ButtonEnable(LudoTmtRoundsDetailsResponse item, int status, int waiting, int won, int opp_won, int play_now) {
        statusBtn.setVisibility(View.INVISIBLE);
        waitingBtn.setVisibility(View.INVISIBLE);
        wonBtn.setVisibility(View.INVISIBLE);
        oppWonBtn.setVisibility(View.INVISIBLE);
        playNowBtn.setVisibility(View.INVISIBLE);

        if (status == 1) {
//            holder.statusBtn.setVisibility(View.VISIBLE);
            if (mDataResponse.getResponse().get(0).getLevel() == 1) {
                if (matchDetailsResponse != null) {
                    if (AppUtilityMethods.getTimeLeftWithMilliSec(matchDetailsResponse.getStartDate()) >= 0 && mDataResponse.isParticipantsFull()) {
                        playNowBtn.setVisibility(View.VISIBLE);
                    } else
                        statusBtn.setVisibility(View.VISIBLE);
                } else {
                    if (AppUtilityMethods.getTimeLeftWithMilliSec(ludoTmtMyMatchResponse.getStartDate()) >= 0 && mDataResponse.isParticipantsFull()) {
                        playNowBtn.setVisibility(View.VISIBLE);
                    } else
                        statusBtn.setVisibility(View.VISIBLE);
                }
            } else {
                if (matchDetailsResponse != null) {
                    if (AppUtilityMethods.getTimeLeftWithMilliSec(matchDetailsResponse.getStartDate()) >= 0) {
                        playNowBtn.setVisibility(View.VISIBLE);
                    } else
                        statusBtn.setVisibility(View.VISIBLE);
                } else {
                    if (AppUtilityMethods.getTimeLeftWithMilliSec(ludoTmtMyMatchResponse.getStartDate()) >= 0) {
                        playNowBtn.setVisibility(View.VISIBLE);
                    } else
                        statusBtn.setVisibility(View.VISIBLE);
                }
            }
        } else if (waiting == 1) {
            waitingBtn.setVisibility(View.VISIBLE);
        } else if (won == 1) {
            if (Objects.equals(item.getUserFirst(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                wonBtn.setVisibility(View.VISIBLE);
            else
                oppWonBtn.setVisibility(View.VISIBLE);
        } else if (opp_won == 1) {
            if (Objects.equals(item.getUserSecond(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                wonBtn.setVisibility(View.VISIBLE);
            else
                oppWonBtn.setVisibility(View.VISIBLE);
        } else {
            playNowBtn.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onItemClick(int pos) {
        if (matchDetailsResponse != null) {
            if (!Objects.equals(mDataResponse.getResponse().get(pos).getUserFirst(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                launchUnityWithData(mDataResponse.getResponse().get(pos).getId(), matchDetailsResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(matchDetailsResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(pos).getUserFirstInfo().getRandomName(), "" + mDataResponse.getResponse().get(pos).getUserFirstInfo().getRandomDp(), tournamentId);
            else
                launchUnityWithData(mDataResponse.getResponse().get(pos).getId(), matchDetailsResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(matchDetailsResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(pos).getUserSecondInfo().getRandomName(), "" + mDataResponse.getResponse().get(pos).getUserSecondInfo().getRandomDp(), tournamentId);

        } else {
            if (!Objects.equals(mDataResponse.getResponse().get(pos).getUserFirst(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId()))
                launchUnityWithData(mDataResponse.getResponse().get(pos).getId(), ludoTmtMyMatchResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(ludoTmtMyMatchResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(pos).getUserFirstInfo().getRandomName(), "" + mDataResponse.getResponse().get(pos).getUserFirstInfo().getRandomDp(), tournamentId);
            else
                launchUnityWithData(mDataResponse.getResponse().get(pos).getId(), ludoTmtMyMatchResponse.getEntryFees(), mDataResponse.getResponse().get(0).getRoomCode(), Double.parseDouble(ludoTmtMyMatchResponse.getPrize().toString()), "" + mDataResponse.getResponse().get(pos).getUserSecondInfo().getRandomName(), "" + mDataResponse.getResponse().get(pos).getUserSecondInfo().getRandomDp(), tournamentId);

        }
    }

    @Override
    public void onInProgressClick() {
        AppDialog.showAlertDialog(this, "You have to wait till start time to play the tournament.");
    }

    @Override
    public void onRefresh() {
        callRoundsApi();
    }

    private void launchUnityWithData(String cId, double Amount, String mContestCode, double mWinAmount, String mRandomName, String mRandomDp, String tournamentId) {
        if (mAppPreference.getBoolean("LudoDownload", false)) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                String mAmount = String.valueOf(Amount);
                String mWAmount = String.valueOf(mWinAmount);
                if (launchIntent != null) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName(AppConstant.LudoAddaPackageName, "com.unity3d.player.UnityPlayerActivity"));
//                    launchIntent.putExtra("userToken", mAppPreference.getSessionToken());
//                    launchIntent.putExtra("contestId", cId);
//                    launchIntent.putExtra("ka_version", AppUtilityMethods.getVersion());
//                    launchIntent.putExtra("playerId", mAppPreference.getProfileData().getId());
//                    launchIntent.putExtra("amount", mAmount);
//                    launchIntent.putExtra("contestCode", mContestCode);
//                    launchIntent.putExtra("winAmount", mWAmount);
//                    launchIntent.putExtra("randomName", mRandomName);
//                    launchIntent.putExtra("randomPhoto", mRandomDp);
//                    launchIntent.putExtra("is_tournament", "true");
//                    launchIntent.putExtra("tournament_id", tournamentId);
//                    launchIntent.putExtra("contestMode", "" + gameMode);

                    intent.putExtra("userToken", mAppPreference.getSessionToken());
                    intent.putExtra("contestId", cId);
                    intent.putExtra("ka_version", AppUtilityMethods.getVersion());
                    intent.putExtra("playerId", mAppPreference.getProfileData().getId());
                    intent.putExtra("amount", mAmount);
                    intent.putExtra("contestCode", mContestCode);
                    intent.putExtra("winAmount", mWAmount);
                    intent.putExtra("randomName", mRandomName);
                    intent.putExtra("randomPhoto", mRandomDp);
                    intent.putExtra("is_tournament", "true");
                    intent.putExtra("tournament_id", tournamentId);
                    intent.putExtra("contestMode", "" + gameMode);
                    mFromUnity = 1;
                    startActivity(intent);
//                    finishAffinity();
                }
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
//            isSuccessfulGameOpen = true;
        }

    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ludoadda_download_popup);
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
            tv.setText(R.string.successful_download_ludo_adda);
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText(R.string.install_now);
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("LudoDownload", true);
                } catch (Exception e) {
                    Toast.makeText(LudoTmtTounamentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
                progressBar.setProgress(progress);

            }
        }
    };

    private void installApk(String filePath) {
        if (Build.VERSION.SDK_INT >= 26 && !LudoTmtTounamentActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(LudoTmtTounamentActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.LudoAddaPackageName, 0);
            mVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showConfirmation(Double amt) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_battle);
        AppCompatButton okBTN = dialog.findViewById(R.id.btn_send);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);
        EditText amount = dialog.findViewById(R.id.et_amount);
        LinearLayout llWinning = dialog.findViewById(R.id.ll_profit);
        llWinning.setVisibility(View.GONE);
        TextView mEstimatedProfitTV = dialog.findViewById(R.id.tv_estimated_winning);
        TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        TextView mTotalWalletBalanceTV = dialog.findViewById(R.id.tv_total_wallet_balance);
        TextView mDepositWalletTV = dialog.findViewById(R.id.tv_wallet_entry);
        TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        TextView hintTV = dialog.findViewById(R.id.tv_hint);
        mEntryFeeTV.setText(String.valueOf(amt));
        TextView heading = dialog.findViewById(R.id.tv_heading);
        heading.setText(R.string.tournament_amount);
        hintTV.setText(R.string.amout_cannotbechanged);
        LinearLayout hide = dialog.findViewById(R.id.ll_hide);
        hide.setVisibility(View.GONE);

        String currentString = String.valueOf(amt);
        String[] parts = currentString.split(Pattern.quote("."));
        String finAmount = parts[0];

        amount.setText(String.valueOf(finAmount));

        mTotalWalletBalanceTV.setText(new DecimalFormat("##.##").format(mTotalWalletBal));
        mDepositWalletTV.setText(new DecimalFormat("##.##").format(mDepositWinWallet));
        amount.setEnabled(false);
        double entry = (amt * 2);
        double earning = entry - (entry / 10);
        mEstimatedProfitTV.setText(String.valueOf(earning));
        okBTN.setOnClickListener(arg0 -> {
            if (amt <= mDepositWinWallet) {
                callJoinApi();
                dialog.dismiss();
            } else {
                mRechargeTV.setVisibility(View.VISIBLE);
                AppUtilityMethods.showRechargeMsghth(this, AppConstant.INSUFFICIENT_WALLET_RECHARGE);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    //NOTIFICATION
    private final BroadcastReceiver mLudoTmtNotificationRoomReceiver = new BroadcastReceiver() { // 71
        @Override
        public void onReceive(Context context, Intent intent) {
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_OPP_ROOM_JOINED)) {
                if (getIntent().getParcelableExtra("AllLudoTournaments") == null)
                    callRoundsApi();

            }
        }
    };
    private final BroadcastReceiver mLudoTmtNotificationMatchLiveReceiver = new BroadcastReceiver() { //72
        @Override
        public void onReceive(Context context, Intent intent) { // 72
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_OPP_ROOM_JOINED)) {
            }
        }
    };
    private final BroadcastReceiver mLudoTmtJoinLobbyReceiver = new BroadcastReceiver() { //73
        @Override
        public void onReceive(Context context, Intent intent) {
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_OPP_ROOM_JOINED)) {
                if (getIntent().getParcelableExtra("AllLudoTournaments") == null)
                    callRoundsApi();
            }
        }
    };
    private final BroadcastReceiver mLudoTmtFullLobbyReceiver = new BroadcastReceiver() { //74
        @Override
        public void onReceive(Context context, Intent intent) {
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_LOBBY_FULL)) {
                hideProgress();
                Snackbar.make(backIv, "Participants Full", Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mFromUnity == 0) {
            launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
            if (launchIntent != null) getVersion();
            else mAppPreference.setBoolean("LudoDownload", false);
            if (mIsRequestingAppInstallPermission) {
                installApk(mFilePath);
            }
        } else {
            showRestartDialog(this, "We are restarting");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentClear = new Intent(LudoTmtTounamentActivity.this, SplashActivity.class);
                    intentClear.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    dialog.dismiss();
                    startActivity(intentClear);
                    finish();
                }
            }, 3000);


        }
    }

    public void showRestartDialog(final Activity activity, String msg) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.layout_restart_dialog);
        dialog.show();
    }

    private void rotate(float degree) {
        RotateAnimation rotate = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());
        mRefreshIv.startAnimation(rotate);
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        mRoundPresenter.destroy();
        super.onDestroy();
    }
}