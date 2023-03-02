package com.khiladiadda.gameleague;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.PrizePoolAdapter;
import com.khiladiadda.gameleague.interfaces.ITournamentDetailPresenter;
import com.khiladiadda.gameleague.interfaces.ITournamentDetailView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.droid_doresponse.PrizePool;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentDetailPresenter;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentDetailResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.AddWalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.ncorti.slidetoact.SlideToActView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

public class TournamenetDetailActivity extends BaseActivity implements ITournamentDetailView {
    @BindView(R.id.iv_title_name)
    TextView toolbarTitle;
    @BindView(R.id.tv_rules_droido)
    TextView tvRules;
    @BindView(R.id.iv_back_arroww)
    ImageView mIvBack;
    @BindView(R.id.tv_tournament_name_detail)
    TextView tvTournamentName;
    @BindView(R.id.image_tournament_detail)
    ImageView imageTournamentImage;
    @BindView(R.id.tv_win_prize)
    TextView tvWinPrize;
    @BindView(R.id.tv_entry_fee)
    TextView tvEntryfee;
    @BindView(R.id.tv_player_count)
    TextView tvParticipants;
    @BindView(R.id.tv_attempts)
    TextView tvAttempts;
    @BindView(R.id.rl_prize_pool)
    MaterialCardView materialCardViewPrizePool;
    @BindView(R.id.shimmer_tv)
    ShimmerTextView shimmerTextView;
    @BindView(R.id.slider_btn_play_quiz)
    SlideToActView slideToActView;
    @BindView(R.id.rv_prize_pool)
    RecyclerView mRecyclerViewPrizePool;
    @BindView(R.id.materialCardView3)
    MaterialCardView materialCardViewLeaderboard;
    @BindView(R.id.acb_view_leaderboard)
    AppCompatButton acbLeaderBoard;
    @BindView(R.id.mcv_rules)
    MaterialCardView mcvRules;
    @BindView(R.id.tv_ends_in)
    TextView tvEndsIn;
    private String mDayLeft;
    private String mEndTime;
    private PrizePoolAdapter mPrizePoolAdapter;
    private ArrayList<PrizePool> prizePoolArrayList = new ArrayList<>();
    private String id;
    private Shimmer mShimmer;
    private long mLastClickTime = 0;
    private ITournamentDetailPresenter mPresenter;
    private String userID;
    private String tournamentid;
    private String refNo;
    private String playedParticipant;
    private String totalParticipants;
    private String nAttempts;
    private String entryPrize;
    private String winPrize;
    private double mTotalWalletBal, mDepositWinWallet, mBonusBal, mBonusPayable, mWalletPayable;
    private int mType, mBonusCode = 0, mParticipants, mAttempt = 3, mQuizStatus;
    private boolean mLowBalance;

    @Override
    protected int getContentView() {
        return R.layout.new_activity_tournaments_details;
    }

    @Override
    protected void initViews() {
        mPresenter = new TournamentDetailPresenter(this);
        setBackground();
        acbLeaderBoard.setOnClickListener(this);
        tvParticipants.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mcvRules.setVisibility(View.VISIBLE);
        tvRules.setVisibility(View.VISIBLE);
        tvRules.setOnClickListener(this);
    }

    private void setBackground() {
        shimmerTextView.setReflectionColor(0xFFDCBD1D);
    }

    @Override
    protected void initVariables() {
        mShimmer = new Shimmer();
        mShimmer.start(shimmerTextView);
        setData();
        id = getIntent().getStringExtra(AppConstant.DROIDO_ID);
        slideToActView.setOnSlideCompleteListener(slideToActView -> {
            if (!Objects.equals(playedParticipant, totalParticipants) || !nAttempts.equals("0")) {
                if ((nAttempts.length() < 2)) {
                    if (nAttempts.equals("0")) {
                        showWalletDialog();
                    } else {
                        startPlay();
                    }
                } else {
                    slideToActView.resetSlider();
                    Toast.makeText(this, getString(R.string.game_limit_exceed), Toast.LENGTH_SHORT).show();
                }
            } else {
                slideToActView.resetSlider();
                Toast.makeText(this, getString(R.string.slots_are_full_please_join_another_tournament), Toast.LENGTH_SHORT).show();
            }
        });
        getWalletData();
        mPrizePoolAdapter = new PrizePoolAdapter(this, prizePoolArrayList);
        mRecyclerViewPrizePool.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewPrizePool.setAdapter(mPrizePoolAdapter);
    }

    private void startPlay() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getTournamentData(id);
            slideToActView.resetSlider();
        } else {
            slideToActView.resetSlider();
            Snackbar.make(mIvBack, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        Intent intent = getIntent();
        prizePoolArrayList = (ArrayList<PrizePool>) getIntent().getSerializableExtra(AppConstant.DROIDO_PRIZEPOOL);
        String name = intent.getStringExtra(AppConstant.DROIDO_NAME);
        String image = intent.getStringExtra(AppConstant.DROIDO_IMAGE);
        entryPrize = intent.getStringExtra(AppConstant.DROIDO_ENTRY_FEE);
        winPrize = intent.getStringExtra(AppConstant.DROIDO_WIN_PRIZE);
        nAttempts = intent.getStringExtra(AppConstant.DROIDO_N_ATTEMPTS);
        if (nAttempts.equals("0")) {
            materialCardViewLeaderboard.setVisibility(View.GONE);
        }
        String endsIn = intent.getStringExtra(AppConstant.DROIDO_ENDS_IN);
        if (endsIn != null) {
            mDayLeft = AppUtilityMethods.getDayLeft(endsIn);
        }
        tvEndsIn.setText(endsIn);
        if (!AppUtilityMethods.getTimeDiff(mEndTime, Calendar.getInstance().getTime()))
            tvEndsIn.setText(mDayLeft);
        playedParticipant = intent.getStringExtra(AppConstant.DROIDO_PLAYED_PARTICIPANTS);
        totalParticipants = intent.getStringExtra(AppConstant.DROIDO_TOTAL_PARTICIPANTS);
        toolbarTitle.setText(name);
        tvTournamentName.setText(name);
        tvWinPrize.setText(getString(R.string.Win) + " " + winPrize);
        tvEntryfee.setText(getString(R.string.text_entry_fee_droido) + " " + entryPrize);
        tvParticipants.setText(getString(R.string.participants) + playedParticipant + "/" + totalParticipants);
        tvParticipants.setPaintFlags(tvParticipants.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvAttempts.setText(getString(R.string.attempts) + " " + nAttempts + "/" + mAttempt);
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.droido_defautl)
                .into(imageTournamentImage);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arroww:
                finish();
                break;
            case R.id.tv_player_count:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(this, ParticipantsGameActivity.class);
                intent.putExtra(AppConstant.DROIDO_ID, id);
                startActivity(intent);
                break;
            case R.id.tv_rules_droido:
                startActivity(new Intent(this, RulesActivity.class));
                break;
            case R.id.acb_view_leaderboard:
                Intent intent1 = new Intent(this, GamesLeaderBoardActivity.class);
                intent1.putExtra("tournament_id", id);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onTournamentDetailSuccess(TournamentDetailResponse response) {
        hideProgress();
        loadGamelinks(AppConstant.GAMEURLDROIDO, response);
        //Apps Flyer
        Map<String, Object> eventParameters2 = new HashMap<>();
        eventParameters2.put(AFInAppEventParameterName.REVENUE, entryPrize); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
        eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
        eventParameters2.put(AppConstant.GAME, "Droid_DO");
        eventParameters2.put(AppConstant.EntryFee, entryPrize);
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
        //Mo Engage
        Properties mProperties = new Properties();
        mProperties.addAttribute(AppConstant.GAMETYPE, "Droid_DO");
        mProperties.addAttribute("Winning", response.getWalletObj().getWinning());
        mProperties.addAttribute("Deposit", response.getWalletObj().getDeposit());
        mProperties.addAttribute("Bonus", response.getWalletObj().getBonus());
        mProperties.addAttribute("EntryFee", entryPrize);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Droid_DO", mProperties);

    }

    private void loadGamelinks(String gameurl, TournamentDetailResponse response) {
        if (response.isStatus()) {
            Intent intent1 = new Intent(this, GameWebActivity.class);
            intent1.putExtra("gameurl", AppConstant.GAMEURLDROIDO);
            intent1.putExtra("tournamentid", response.getResponse().getTournamentId());
            intent1.putExtra(AppConstant.DROIDO_ID, response.getResponse().getUserId());
            intent1.putExtra("refNo", response.getResponse().getRefNo());
            startActivity(intent1);
            finish();
        } else {
            Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getWalletData() {
        Coins coins = mAppPreference.getProfileData().getCoins();
        mTotalWalletBal = coins.getDeposit() + coins.getWinning() + coins.getBonus();
        mDepositWinWallet = coins.getDeposit() + coins.getWinning();
        mBonusBal = coins.getBonus();
        if (mBonusCode > 1) {
            mBonusPayable = Math.min(((Integer.parseInt(entryPrize) * mBonusCode) / 100.0), mBonusBal);
            mWalletPayable = Double.parseDouble(entryPrize + " ") - mBonusPayable;
        } else {
            mBonusPayable = 0;
            if (entryPrize != null) {
                mWalletPayable = Double.parseDouble(entryPrize.toString() + " ");
            } else {
                entryPrize = "" + 0;
            }
        }
        if (mWalletPayable > mDepositWinWallet) {
            mLowBalance = true;
        }
    }

    /**
     * Wallet Scrren
     **/
    public void showWalletDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_wallet_league_quiz);
        final TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        final TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        mEntryFeeTV.setText(String.valueOf(new DecimalFormat("##.##").format(Integer.parseInt(entryPrize))));
        final TextView mTotalWalletTV = dialog.findViewById(R.id.tv_total_wallet);
        mTotalWalletTV.setText(getString(R.string.text_total_wallet_bal) + " (" + String.valueOf(new DecimalFormat("##.##").format(mTotalWalletBal)) + ")");
        final TextView mTotalWalletEntryTV = dialog.findViewById(R.id.tv_total_wallet_entry);
        mTotalWalletEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(Integer.parseInt(entryPrize))));
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
        mTotalEntryTV.setText(String.valueOf(new DecimalFormat("##.##").format(Integer.parseInt(entryPrize))));
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        Button okBTN = dialog.findViewById(R.id.btn_send);
        if (mLowBalance) {
            mRechargeTV.setVisibility(View.VISIBLE);
            okBTN.setText(R.string.text_recharge);
        }
        mCancelBTN.setOnClickListener(v -> {
            dialog.dismiss();
            slideToActView.resetSlider();
        });
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (mLowBalance) {
                startActivity(new Intent(TournamenetDetailActivity.this, AddWalletActivity.class));
                slideToActView.resetSlider();
            } else {
                startPlay();
                slideToActView.resetSlider();
            }
        });
        dialog.show();
    }

    @Override
    public void onTournamentDetailFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        slideToActView.resetSlider();

    }
}
