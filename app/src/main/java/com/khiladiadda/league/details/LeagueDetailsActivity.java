package com.khiladiadda.league.details;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnAddCallDutyCredentialListener;
import com.khiladiadda.dialogs.interfaces.IOnAddGameCredentialListener;
import com.khiladiadda.dialogs.interfaces.IOnCreateTeamPaymentListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnLeagueJoinListener;
import com.khiladiadda.league.adapter.LeaguePrizePoolAdapter;
import com.khiladiadda.league.details.interfaces.ILeagueDetailsPresenter;
import com.khiladiadda.league.details.interfaces.ILeagueDetailsView;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.league.participant.ParticipantActivity;
import com.khiladiadda.league.team.MyTeamDialog;
import com.khiladiadda.league.team.TeamActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.AddCredential;
import com.khiladiadda.network.model.request.GameCredential;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.CreateTeamResponse;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.quiz.result.PrizeBreakthroughActivity;
import com.khiladiadda.rule.QuizRuleActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.WalletCashbackActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LeagueDetailsActivity extends BaseActivity implements ILeagueDetailsView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.btn_my_league)
    Button mMyLeagueBTN;
    @BindView(R.id.iv_image)
    ImageView mImageIV;
    @BindView(R.id.tv_rules)
    Button mRuleBTN;
    @BindView(R.id.btn_join)
    Button mJoinBTN;
    @BindView(R.id.btn_create)
    Button mCreateBTN;
    @BindView(R.id.tv_quiz_name)
    TextView mLegueNameTV;
    @BindView(R.id.tv_date)
    TextView mDateTV;
    @BindView(R.id.tv_entry)
    TextView mEntryTV;
    @BindView(R.id.tv_participated)
    TextView mParticipatedTV;
    @BindView(R.id.tv_bonus)
    TextView mBonusTV;
    @BindView(R.id.tv_prize_money)
    TextView mPrizeMoneyTV;
    @BindView(R.id.tv_view_prizepool)
    TextView mViewAllPrizePoolTV;
    @BindView(R.id.rv_prize_pool_breakup)
    RecyclerView mPrizePoolBreakupRV;
    @BindView(R.id.tv_map)
    TextView mMapTV;
    @BindView(R.id.tv_kill_point)
    TextView mKillPointTV;
    @BindView(R.id.btn_view_participant)
    Button mViewParticipantsBTN;
    @BindView(R.id.btn_how_join)
    Button mHowToJoinBTN;
    @BindView(R.id.tv_prizepool)
    TextView mPrizePoolTV;
    @BindView(R.id.ll_prize_pool)
    LinearLayout mPrizePoolLL;
    @BindView(R.id.ll_length_level_loss)
    LinearLayout mClashRoyaleLL;
    @BindView(R.id.tv_length)
    TextView mLengthTV;
    @BindView(R.id.tv_level)
    TextView mLevelTV;
    @BindView(R.id.tv_loss)
    TextView mLossTV;

    private String mLeagueId, mEntryFee = "0", mGameUsername = "", mGameCharacterId = "", mGameId, mGameTypeId, mTeamName = "";
    private long mBonus;
    private boolean mIsCategorySolo;
    private boolean mLowBalance;
    private double mTotalWalletBal, mDepositWinWallet, mBonusBal, mBonusPayable, mWalletPayable;
    private ILeagueDetailsPresenter mPresenter;
    Handler handler = new Handler();
    private LeagueListDetails mLeagueDetails;
    private String mGameType, mGame;


    @Override
    protected int getContentView() {
        return R.layout.activity_league_details;
    }

    @Override
    protected void initViews() {
        mLeagueDetails = getIntent().getParcelableExtra(AppConstant.DATA);
        mGameType = getIntent().getStringExtra("type");
        mGame = getIntent().getStringExtra("game");
        mGameId = mLeagueDetails.getGameId();
        mGameTypeId = mLeagueDetails.getGameCategoryId();
        if (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SOLO, ""))) {
            mIsCategorySolo = true;
        }
        setAnimation();
    }

    @Override
    protected void initVariables() {
        mPresenter = new LeagueDetailsPresenter(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mMyLeagueBTN.setOnClickListener(this);
        mRuleBTN.setOnClickListener(this);
        mJoinBTN.setOnClickListener(this);
        mCreateBTN.setOnClickListener(this);
        mViewParticipantsBTN.setOnClickListener(this);
        mBonusTV.setOnClickListener(this);
        mEntryTV.setOnClickListener(this);
        mParticipatedTV.setOnClickListener(this);
        mPrizeMoneyTV.setOnClickListener(this);
        mHowToJoinBTN.setOnClickListener(this);
        mPrizePoolTV.setOnClickListener(this);
        mViewAllPrizePoolTV.setOnClickListener(this);
        if (mAppPreference.getIsProfile()) {
            setData();
        } else {
            showProgress(getString(R.string.text_waiting_progress));
            mPresenter.getProfile();
        }
    }

    private void setData() {
        mLegueNameTV.setText(mLeagueDetails.getTitle());
        mDateTV.setText(AppUtilityMethods.getConvertDateQuiz(mLeagueDetails.getStart()));
        mPrizeMoneyTV.setText(String.format(getString(R.string.display_prize), mLeagueDetails.getPrizeMoney()) + " Coins");
        mLeagueId = mLeagueDetails.getId();
        mKillPointTV.setText("You will get\n" + mLeagueDetails.getKillPoint() + " Coins per Kill");
        mMapTV.setText(getString(R.string.text_map) + "\n" + mLeagueDetails.getMap());
        mBonus = mLeagueDetails.getBonusCode();
        mBonusTV.setText("Bonus\nB" + mBonus);
        if (mIsCategorySolo) {
            mCreateBTN.setVisibility(View.GONE);
            mJoinBTN.setText(getString(R.string.text_join));
            mHowToJoinBTN.setVisibility(View.GONE);
            mParticipatedTV.setText(String.format(getString(R.string.display_participant), mLeagueDetails.getTotalParticipants()));
        } else {
            mViewParticipantsBTN.setText(getString(R.string.text_view_team));
            mCreateBTN.setVisibility(View.VISIBLE);
            mParticipatedTV.setText(String.format(getString(R.string.display_team), mLeagueDetails.getTotalParticipants()));
        }
        if (mLeagueDetails.getEntryFees() == 0) {
            mEntryTV.setText(R.string.text_entry_fee_free);
        } else {
            mEntryFee = String.valueOf(mLeagueDetails.getEntryFees());
            mEntryTV.setText(String.format(getString(R.string.display_entry_fee), mLeagueDetails.getEntryFees()) + " Coins");
        }
        if (mLeagueDetails.getPrizePoolBreakup().size() > 0) {
            LeaguePrizePoolAdapter mTrendingAdapter = new LeaguePrizePoolAdapter(this, mLeagueDetails.getPrizePoolBreakup());
            mPrizePoolBreakupRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mPrizePoolBreakupRV.setAdapter(mTrendingAdapter);
        } else {
            mPrizePoolTV.setVisibility(View.GONE);
            mPrizePoolLL.setVisibility(View.GONE);
            mPrizePoolBreakupRV.setVisibility(View.GONE);
        }
        if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, "")) || mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.bgmi_banner));
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, "")) || mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.ff_banner));
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.ff_max_banner));
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.pgbanner));
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.esbanner));
            mClashRoyaleLL.setVisibility(View.VISIBLE);
            mLengthTV.setText(getString(R.string.text_length) + ": " + mLeagueDetails.getLength());
            mLevelTV.setText(getString(R.string.text_level) + ": " + mLeagueDetails.getLevel());
            mLossTV.setText(getString(R.string.text_loss) + ": " + mLeagueDetails.getMaxloss());
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            mImageIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.pubgns_banner));
        }
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_MYLEAGUE) && mIsCategorySolo) {
            mJoinBTN.setEnabled(false);
            mJoinBTN.setText(R.string.text_joined);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_MYLEAGUE)) {
            mCreateBTN.setVisibility(View.GONE);
            mJoinBTN.setText(R.string.text_my_team_details);
            mViewParticipantsBTN.setText(R.string.text_view_team);
        }
        getWalletBalance();
    }

    private void getWalletBalance() {
        double entryFee = Double.parseDouble(mEntryFee);
        Coins coins = mAppPreference.getProfileData().getCoins();
        mTotalWalletBal = coins.getDeposit() + coins.getWinning() + coins.getBonus();
        mDepositWinWallet = coins.getDeposit() + coins.getWinning();
        mBonusBal = coins.getBonus();
        if (mBonus > 1) {
            mBonusPayable = Math.min(((entryFee * mBonus) / 100.0), mBonusBal);
            mWalletPayable = entryFee - mBonusPayable;
        } else {
            mBonusPayable = 0;
            mWalletPayable = entryFee;
        }
        if (mWalletPayable > mDepositWinWallet) {
            mLowBalance = true;
        }
        getGameCredentials();
    }

    private void getGameCredentials() {
        List<GameCredential> credentials = mAppPreference.getProfileData().getCredentials();
        if (credentials != null && credentials.size() > 0) {
            for (int i = 0; i < credentials.size(); i++) {
                String gameId = credentials.get(i).getGameId();
                if (gameId.equalsIgnoreCase(mGameId)) {
                    if (!TextUtils.isEmpty(credentials.get(i).getGameCharacterId())) {
                        mGameUsername = credentials.get(i).getGameUsername();
                        mGameCharacterId = credentials.get(i).getGameCharacterId();
                    }
                }
            }
        }
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
            case R.id.btn_join:
                joinCreateTeam(AppConstant.LEAGUE_JOIN);
                break;
            case R.id.btn_create:
                joinCreateTeam(AppConstant.LEAGUE_CREATE);
                break;
            case R.id.btn_my_league:
                openMyLeague();
                break;
            case R.id.btn_how_join:
                Intent join = new Intent(this, QuizRuleActivity.class);
                if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                    join.putExtra(AppConstant.FROM, AppConstant.PUBG_NEWSTATE_ID);
                } else {
                    join.putExtra(AppConstant.FROM, AppConstant.FROM_HOW_JOINING_PCESPORT);
                }
                startActivity(join);
                break;
            case R.id.tv_rules:
                Intent i = new Intent(this, QuizRuleActivity.class);
                if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.PUBG_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.PUBG_LITE_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_BGMI);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FREEFIRE_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FF_CLASH_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FF_MAX_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_MAX);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.PUBG_GLOBAL_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_GLOBAL);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.PREMIUM_ESPORTS_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PREMIUM_ESPORTS);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.PUBG_NEWSTATE_SOLO);
                } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "")) && !mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SOLO, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_NEWSTATE);
                }
                startActivity(i);
                break;
            case R.id.btn_view_participant:
                if (mIsCategorySolo) {
                    Intent participant = new Intent(this, ParticipantActivity.class);
                    participant.putExtra(AppConstant.FROM, AppConstant.LEAGUE);
                    participant.putExtra(AppConstant.ID, mLeagueId);
                    startActivity(participant);
                } else {
                    Intent participant = new Intent(this, TeamActivity.class);
                    participant.putExtra(AppConstant.ID, mLeagueId);
                    startActivity(participant);
                }
                break;
            case R.id.tv_bonus:
                AppUtilityMethods.showInfo(this, mBonusTV, "You can use " + mBonus + "% of your bonus coins");
                break;
            case R.id.tv_view_prizepool:
                Intent intent = new Intent(this, PrizeBreakthroughActivity.class);
                intent.putExtra(AppConstant.FROM, AppConstant.LEAGUE);
                intent.putExtra(AppConstant.DATA, mLeagueDetails);
                startActivity(intent);
                break;
        }
    }

    private void openMyLeague() {
        Intent view = new Intent(this, MyLeagueActivity.class);
        if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESTDM);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESBGMI);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFF);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFCS);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFFMAX);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEAGUESPG);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESPE);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUEPGNS);
        }
        startActivity(view);
    }

    private void joinCreateTeam(int joiningType) {
        if (joiningType == AppConstant.LEAGUE_JOIN) {
            if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.LEAGUE)) {
                if (new NetworkStatus(this).isInternetOn()) {
                    selectGameType();
                } else {
                    Snackbar.make(mLegueNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
            } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_MYLEAGUE) && !(mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, "")))) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.getMyTeam(mLeagueId);
            }
        } else if (joiningType == AppConstant.LEAGUE_CREATE) {
            if (new NetworkStatus(this).isInternetOn()) {
                if (mLeagueDetails.getTotalParticipants() == mLeagueDetails.getPlayedParticipants()) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_league_full), false);
                } else {
                    AppDialog.showCreateTeamPaymentDialog(this, addCreateTeamListener, mGameId, mEntryFee, mGameUsername, mGameCharacterId);
                }
            } else {
                Snackbar.make(mLegueNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void selectGameType() {
        //solo - add Username in team_user for -PubG_Solo
        if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SOLO, ""))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_SOLO, mGameUsername, mGameCharacterId);
        }  //duo - add Username in team_user for -PubG_Duo or PubG_Squd
        else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_DUO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SOLO, ""))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_LITE_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_LITE_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_SOLO, "")))) {
            AppDialog.addCallDutyCredentialDialog(this, addCallDutyCredentialListener, AppConstant.CALL_DUTY_SOLO, mGameUsername);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_SQUAD, "")))) {
            AppDialog.addCallDutyCredentialDialog(this, addCallDutyCredentialListener, AppConstant.CALL_DUTY_DUO, mGameUsername);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FREEFIRE_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FREEFIRE_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FF_CLASH_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FF_CLASH_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FF_MAX_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.FF_MAX_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_GLOBAL_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_GLOBAL_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PREMIUM_ESPORTS_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PREMIUM_ESPORTS_DUO, mGameUsername, mGameCharacterId);
        } else if ((mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "")) && mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SOLO, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_NEWSTATE_SOLO, mGameUsername, mGameCharacterId);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "")) && (mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_DUO, "")) || mGameTypeId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_SQUAD, "")))) {
            AppDialog.addGameCredentialDialog(this, addGameCredentialListener, AppConstant.PUBG_NEWSTATE_DUO, mGameUsername, mGameCharacterId);
        }
    }

    @Override
    public void onStartLeagueComplete(StartQuizResponse responseModel) {
        mAppPreference.setIsProfile(false);
        hideProgress();
        if (responseModel.isStatus()) {
            if (mIsCategorySolo) {
                AppUtilityMethods.showJoinMsg(this, getString(R.string.text_solo_league_joined), false, joinLeagueListener, AppConstant.SOLO);
              //Apps Flyer
                Map<String, Object> eventParameters2 = new HashMap<>();
                eventParameters2.put(AFInAppEventParameterName.REVENUE, mEntryFee); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
                eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
                eventParameters2.put(AppConstant.GAME, AppConstant.LEAGUE);
                eventParameters2.put(AppConstant.LEAGUETYPE, AppConstant.SOLO);
                eventParameters2.put(AppConstant.LEAGUEGAME, mGame);
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
               // MO Engage
                 Properties mProperties = new Properties();
                mProperties.addAttribute(AppConstant.LEAGUE_GAME, mGame);
                mProperties.addAttribute(AppConstant.LEAGUE_TYPE,AppConstant.SOLO);
                mProperties.addAttribute(AppConstant.PLAYERTYPE, "Joined");
                mProperties.addAttribute(AppConstant.ClickedDate,new Date());
                mProperties.addAttribute("Winning",responseModel.getWalletObj().getWinning());
                mProperties.addAttribute("Deposit",responseModel.getWalletObj().getDeposit());
                mProperties.addAttribute("Bonus",responseModel.getWalletObj().getBonus());
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.LEAGUE, mProperties);
            } else {
                AppUtilityMethods.showJoinMsg(this, getString(R.string.text_squd_league_joined), false, joinLeagueListener, AppConstant.DUO);
                //Apps Flyers
                Map<String, Object> eventParameters2 = new HashMap<>();
                eventParameters2.put(AFInAppEventParameterName.REVENUE, mEntryFee); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
                eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
                eventParameters2.put(AppConstant.GAME, AppConstant.LEAGUE);
                eventParameters2.put(AppConstant.LEAGUETYPE,mGameType);
                eventParameters2.put(AppConstant.LEAGUEGAME, mGame);
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
                //Mo Engage
                 Properties mProperties = new Properties();
                mProperties.addAttribute(AppConstant.LEAGUE_GAME, mGame);
                mProperties.addAttribute(AppConstant.LEAGUE_TYPE,mGameType);
                mProperties.addAttribute(AppConstant.PLAYERTYPE, "Joined");
                mProperties.addAttribute(AppConstant.ClickedDate,new Date());
                mProperties.addAttribute("Winning",responseModel.getWalletObj().getWinning());
                mProperties.addAttribute("Deposit",responseModel.getWalletObj().getDeposit());
                mProperties.addAttribute("Bonus",responseModel.getWalletObj().getBonus());
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.LEAGUE, mProperties);
            }
        } else if (responseModel.getMessage().equalsIgnoreCase(getString(R.string.text_recharge_first))) {
            AppUtilityMethods.showRechargeMsg(this, responseModel.getMessage());
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onStartLeagueFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onCreateTeamComplete(CreateTeamResponse responseModel) {
        mAppPreference.setIsProfile(false);
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showPaymentConfirm(this, responseModel.getResponse().getTeam().getCode(), joinLeagueListener);
           //Apps Flyers
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mEntryFee); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, "LEAGUE");
            eventParameters2.put(AppConstant.PLAYERTYPE, "CREATETEAM");
            eventParameters2.put(AppConstant.LEAGUETYPE, mGameType);
            eventParameters2.put(AppConstant.LEAGUEGAME, mGame);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties mProperties = new Properties();
            mProperties.addAttribute(AppConstant.LEAGUE_GAME, mGame);
            mProperties.addAttribute(AppConstant.LEAGUE_TYPE,mGameType);
            mProperties.addAttribute(AppConstant.ClickedDate,new Date());
            mProperties.addAttribute(AppConstant.PLAYERTYPE, AppConstant.CreateTeam);
            mProperties.addAttribute("Winning",responseModel.getWalletObj().getWinning());
            mProperties.addAttribute("Deposit",responseModel.getWalletObj().getDeposit());
            mProperties.addAttribute("Bonus",responseModel.getWalletObj().getBonus());
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.LEAGUE, mProperties);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onCreateTeamFailure(ApiError error) {
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
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getResponse());
        mAppPreference.setIsProfile(true);
        setData();
        hideProgress();
    }

    @Override
    public void onProfileFailure(ApiError error) {
        hideProgress();
    }

    IOnAddGameCredentialListener addGameCredentialListener = new IOnAddGameCredentialListener() {
        @Override
        public void onCredentialAdd(String userId, String characterId, String teamCode) {
            if (TextUtils.isEmpty(teamCode)) {
                mTeamName = mAppPreference.getName();
            }
            if (mIsCategorySolo) {
                showWalletDialog(userId, characterId, teamCode, 1);
            } else {
                getDataFromServer(userId, characterId, teamCode, 1);
            }
        }
    };

    IOnAddCallDutyCredentialListener addCallDutyCredentialListener = new IOnAddCallDutyCredentialListener() {
        @Override
        public void onCallDutyCredentialAdd(String userId, String teamId) {
            if (TextUtils.isEmpty(teamId)) {
                mTeamName = mAppPreference.getName();
            }
            if (mIsCategorySolo) {
                showWalletDialog(userId, "", teamId, 2);
            } else {
                getDataFromServer(userId, "", teamId, 2);
            }
        }
    };

    IOnCreateTeamPaymentListener addCreateTeamListener = (userId, characterId, teamName) -> showWalletDialog(userId, characterId, teamName, 3);

    IOnLeagueJoinListener joinLeagueListener = (shareTeamCode, teamCode, from) -> {
        if (shareTeamCode) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Hi, I have created a team. Use " + teamCode + " code to join");
            startActivity(Intent.createChooser(share, "Team Code"));
        }
    };

    public void showWalletDialog(String userId, String characterId, String teamName, int from) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_wallet_league_quiz);
        final TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        mEntryFeeTV.setText(mEntryFee);
        final TextView mTotalWalletTV = dialog.findViewById(R.id.tv_total_wallet);
        mTotalWalletTV.setText(getString(R.string.text_total_wallet_bal) + " (" + new DecimalFormat("##.##").format(mTotalWalletBal) + ")");
        final TextView mTotalWalletEntryTV = dialog.findViewById(R.id.tv_total_wallet_entry);
        mTotalWalletEntryTV.setText(mEntryFee);
        final TextView mDepositWinWalletTV = dialog.findViewById(R.id.tv_wallet);
        mDepositWinWalletTV.setText(getString(R.string.text_deposit_winning) + " (" + new DecimalFormat("##.##").format(mDepositWinWallet) + ")");
        final TextView mDepositWinEntryTV = dialog.findViewById(R.id.tv_wallet_entry);
        mDepositWinEntryTV.setText(new DecimalFormat("##.##").format(mWalletPayable));
        final TextView mBonusBalanceTV = dialog.findViewById(R.id.tv_bonus);
        mBonusBalanceTV.setText(getString(R.string.text_bonus) + " (" + new DecimalFormat("##.##").format(mBonusBal) + ")");
        final TextView mBonusEntryTV = dialog.findViewById(R.id.tv_bonus_entry);
        mBonusEntryTV.setText(new DecimalFormat("##.##").format(mBonusPayable));
        final TextView mTotalTV = dialog.findViewById(R.id.tv_total);
        mTotalTV.setText(getString(R.string.text_total_coins));
        final TextView mTotalEntryTV = dialog.findViewById(R.id.tv_total_entry);
        mTotalEntryTV.setText(mEntryFee);
        final TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        Button okBTN = dialog.findViewById(R.id.btn_send);
        if (mLowBalance) {
            mRechargeTV.setVisibility(View.VISIBLE);
            okBTN.setText(R.string.text_recharge);
        }
        mCancelBTN.setOnClickListener(view -> dialog.dismiss());
        okBTN.setOnClickListener(view -> {
            dialog.dismiss();
            if (mLowBalance) {
                startActivity(new Intent(LeagueDetailsActivity.this, WalletCashbackActivity.class));
            } else {
                getDataFromServer(userId, characterId, teamName, from);
            }
        });
        dialog.show();
    }

    private void getDataFromServer(String userId, String characterId, String teamName, int from) {
        showProgress(getString(R.string.txt_progress_authentication));
        mAppPreference.setIsProfile(false);
        if (from == 1) {
            AddCredential addCredential = new AddCredential(userId, characterId, teamName, mTeamName);
            mPresenter.startLeague(addCredential, mLeagueId);
        } else if (from == 2) {
            AddCredential addCredential = new AddCredential(userId, "", teamName, mTeamName);
            mPresenter.startLeague(addCredential, mLeagueId);
        } else if (from == 3) {
            AddCredential addCredential = new AddCredential(userId, characterId, "", teamName);
            mPresenter.createTeam(addCredential, mLeagueId);
        }
    }

    private void removeHandlerCallback() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void setAnimation() {
        ColorDrawable[] color = {new ColorDrawable(Color.MAGENTA), new ColorDrawable(Color.YELLOW)};
        TransitionDrawable trans = new TransitionDrawable(color);
        mHowToJoinBTN.setBackground(trans);
        trans.startTransition(7000); // duration 7 seconds
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onPause() {
        removeHandlerCallback();
        super.onPause();
    }

    Runnable runnable = this::showVideoHelp;

    private void showVideoHelp() {
        //show popup
        if (!mAppPreference.getIsVideoSeen()) {
            if (!mIsCategorySolo) {
                AppUtilityMethods.showVideoMsg(this, false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}