package com.khiladiadda.battle;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.adapter.BattleAdapter;
import com.khiladiadda.battle.adapter.BattleCategoryAdapter;
import com.khiladiadda.battle.interfaces.IBattlePresenter;
import com.khiladiadda.battle.interfaces.IBattleView;
import com.khiladiadda.battle.model.BannerResponse;
import com.khiladiadda.battle.model.BattleDetails;
import com.khiladiadda.battle.model.BattleGroupResponse;
import com.khiladiadda.battle.model.BattleResponse;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.GameDetails;
import com.khiladiadda.network.model.response.MatchDetails;
import com.khiladiadda.network.model.response.FBTeamDetails;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.WalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class BattleActivity extends BaseActivity implements IBattleView, BattleAdapter.IOnItemChildClickListener, BattleCategoryAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
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
    @BindView(R.id.tv_name)
    TextView mNameTV;
    @BindView(R.id.tv_time_left)
    TextView mTimeLeftTV;
    @BindView(R.id.tv_how_play)
    TextView mHowPlayTV;
    @BindView(R.id.tv_calculate)
    TextView mCalculateTV;
    @BindView(R.id.tv_set)
    TextView mPrizePoolTV;
    @BindView(R.id.rv_battle)
    RecyclerView mBattleRV;
    @BindView(R.id.rv_battle_category)
    RecyclerView mBattleCategoryRV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityName;

    private BattleAdapter mBattleAdapter;
    private ArrayList<BattleDetails> mBattleList = new ArrayList<>();
    private ArrayList<BattleDetails> mBattleShowList = new ArrayList<>();
    private IBattlePresenter mPresenter;
    private int mFrom, mHowtoPlay;
    private MatchDetails mMatchDetail;
    private BattleCategoryAdapter mBattleCategoryAdapter;
    private GameDetails mBattleCategory;
    private String mCategoryId;

    @Override
    protected int getContentView() {
        return R.layout.activity_battle;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mHowPlayTV.setOnClickListener(this);
        mCalculateTV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mFrom = getIntent().getIntExtra(AppConstant.FROM, 0);
        mMatchDetail = getIntent().getParcelableExtra(AppConstant.DATA);
        mBattleCategory = getIntent().getParcelableExtra(AppConstant.BATTLE_CATEGORY);
        if (mBattleCategory != null) {
            mCategoryId = mBattleCategory.getCategories().get(0).getId();
        }
        setData();
    }

    @Override
    protected void initVariables() {
        mPresenter = new BattlePresenter(this);
        if (mBattleCategory != null) {
            mBattleCategoryAdapter = new BattleCategoryAdapter(mBattleCategory.getCategories());
            mBattleCategoryRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            mBattleCategoryRV.setAdapter(mBattleCategoryAdapter);
            mBattleCategoryAdapter.setOnItemChildClickListener(this);
            mBattleCategory.getCategories().get(0).setSelected(true);
        }
        mBattleAdapter = new BattleAdapter(mBattleShowList, mFrom, mMatchDetail.isInReview());
        mBattleRV.setLayoutManager(new LinearLayoutManager(this));
        mBattleRV.setAdapter(mBattleAdapter);
        mBattleAdapter.setOnItemChildClickListener(this);
        getBattle();
    }

    private void setData() {
        mHowtoPlay = 1;
        mHowPlayTV.setText(R.string.text_clasic_play_how);
        mDateTV.setText(AppUtilityMethods.getConvertDateMatch(mMatchDetail.getStartDateTime()));
        mNameTV.setText(mMatchDetail.getSeries().getName());
        FBTeamDetails homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeamDetails();
        FBTeamDetails awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeamDetails();
        mTeamOneTV.setText(homeTeam.getName());
        Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamOneIV);
        mTeamTwoTV.setText(awayTeam.getName());
        Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamTwoIV);
        mActivityName.setText(homeTeam.getName()+" vs "+awayTeam.getName());
        if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
            mTimeLeftTV.setTextColor(mTimeLeftTV.getResources().getColor(R.color.color_green));
            if (mMatchDetail.isInReview()) {
                mTimeLeftTV.setText(R.string.text_in_review);
                mTimeLeftTV.setTextColor(mTimeLeftTV.getResources().getColor(R.color.battle_red));
            } else {
                mTimeLeftTV.setText(getString(R.string.text__live));
            }
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
            mTimeLeftTV.setVisibility(View.GONE);
        } else {
            mTimeLeftTV.setText(String.format("Starts In: %s", AppUtilityMethods.getBattleRemainingTime(mMatchDetail.getStartDateTime())));
        }
        setCoin();
    }

    private void setCoin() {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }
    }

    private void getBattle() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
                mPresenter.getBattleList(mMatchDetail.getId(), true, true, false);
            } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
                mPresenter.getBattleList(mMatchDetail.getId(), true, false, true);
            } else if (mFrom == AppConstant.FROM_FANBATTLE_UPCOMING) {
                mPresenter.getBattleList(mMatchDetail.getId(), true, false, false);
            } else {
                mPresenter.getBattleList(mMatchDetail.getId(), false, false, false);
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
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
            case R.id.tv_how_play:
                Intent i = new Intent(this, BattleHelpActivity.class);
                i.putExtra(AppConstant.FROM, mHowtoPlay);
                startActivity(i);
                break;
            case R.id.tv_calculate:
                startActivity(new Intent(this, BattlePointsActivity.class));
                break;
            case R.id.rl_wallet:
                Intent profile = new Intent(this, WalletActivity.class);
                startActivity(profile);
                break;
        }
    }

    @Override
    public void onGetBattleListComplete(BattleResponse responseModel) {
        mBattleList.clear();
        mBattleList.addAll(responseModel.getResponse());
        if (responseModel.isStatus()) {
            fetchBattleOnCategory();
        }
    }

    private void fetchBattleOnCategory() {
        if ((mFrom == AppConstant.FROM_FANBATTLE_LIVE) || (mFrom == AppConstant.FROM_FANBATTLE_PAST) || (mFrom == AppConstant.FROM_FANBATTLE_UPCOMING)) {
            mBattleShowList.clear();
            mBattleShowList.addAll(mBattleList);
        } else {
            if (mBattleCategory.getCategories().size() > 0) {
                for (int k = 0; k < mBattleCategory.getCategories().size(); k++) {
                    int mSize = 0;
                    for (int j = 0; j < mBattleList.size(); j++) {
                        if (mBattleCategory.getCategories().get(k).getId().equalsIgnoreCase(mBattleList.get(j).getCategoryId())) {
                            mSize = mSize + 1;
                            mBattleCategory.getCategories().get(k).setmBattleSize(mSize);
                        }
                    }
                }
            }
            mBattleCategoryAdapter.notifyDataSetChanged();
            List<BattleDetails> battleList = new ArrayList<>();
            for (int i = 0; i < mBattleList.size(); i++) {
                if (mCategoryId.equalsIgnoreCase(mBattleList.get(i).getCategoryId())) {
                    BattleDetails battleDetails = new BattleDetails();
                    battleDetails.setId(mBattleList.get(i).getId());
                    battleDetails.setLivePrizePool(mBattleList.get(i).getLivePrizePool());
                    battleDetails.setNParticipants(mBattleList.get(i).getNParticipants());
                    battleDetails.setResultDeclared(mBattleList.get(i).isResultDeclared());
                    battleDetails.setInvestedAmount(mBattleList.get(i).getInvestedAmount());
                    battleDetails.setActive(mBattleList.get(i).isActive());
                    battleDetails.setDeleted(mBattleList.get(i).isDeleted());
                    battleDetails.setMatchId(mBattleList.get(i).getMatchId());
                    battleDetails.setTitle(mBattleList.get(i).getTitle());
                    battleDetails.setDesc(mBattleList.get(i).getDesc());
                    battleDetails.setNGroups(mBattleList.get(i).getNGroups());
                    battleDetails.setImg(mBattleList.get(i).getImg());
                    battleDetails.setQuestion(mBattleList.get(i).getQuestion());
                    battleDetails.setPlayed(mBattleList.get(i).isPlayed());
                    battleDetails.setnGroupJoined(mBattleList.get(i).getnGroupJoined());
                    battleDetails.setnGroupsPlayed(mBattleList.get(i).getnGroupsPlayed());
                    battleDetails.setCategoryId(mBattleList.get(i).getCategoryId());
                    battleDetails.setFree(mBattleList.get(i).isFree());
                    battleDetails.setTotalParticipants(mBattleList.get(i).getTotalParticipants());
                    battleDetails.setPrize(mBattleList.get(i).getPrize());
                    battleDetails.setBonusCode(mBattleList.get(i).getBonusCode());
                    battleDetails.setCaptainId(mBattleList.get(i).getCaptainId());
                    battleDetails.setContest(mBattleList.get(i).isContest());
                    battleList.add(battleDetails);
                }
            }
            mBattleShowList.clear();
            mBattleShowList.addAll(battleList);
        }
        mBattleAdapter.notifyDataSetChanged();
        double prizePool = 0;
        double freePrizePool = 0;
        for (BattleDetails battleDetails : mBattleShowList) {
            if (battleDetails.isFree()) {
                freePrizePool = freePrizePool + battleDetails.getPrize();
            }
            if (battleDetails.getnGroupJoined() > 1) {
                prizePool = prizePool + battleDetails.getLivePrizePool();
            } else {
                if (battleDetails.getInvestedAmount() > 0) {
                    prizePool = prizePool + (battleDetails.getInvestedAmount() * 1.2);
                } else {
                    prizePool = prizePool + battleDetails.getInvestedAmount();
                }
            }
        }
        mPrizePoolTV.setText("Total Prize Pool \u20B9" + new DecimalFormat("##.##").format(prizePool + freePrizePool));
        hideProgress();
    }

    @Override
    public void onGetBattleListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetGroupListComplete(BattleGroupResponse responseModel) {

    }

    @Override
    public void onGetGroupListFailure(ApiError error) {

    }

    @Override
    public void onJoinComplete(BaseResponse responseModel) {

    }

    @Override
    public void onJoinFailure(ApiError error) {

    }

    @Override
    public void onGetCalculationBannerComplete(BannerResponse responseModel) {

    }

    @Override
    public void onGetCalculationBannerFailure(ApiError error) {

    }

    @Override
    public void onCancelComplete(BaseResponse responseModel) {

    }

    @Override
    public void onCancelFailure(ApiError error) {

    }

    @Override
    public void onJoinSubstituteComplete(BaseResponse responseModel) {

    }

    @Override
    public void onJoinSubstituteFailure(ApiError error) {

    }

    @Override
    public void onPlayClicked(int position) {
        Intent i = new Intent(this, BattleGroupActivity.class);
        i.putExtra(AppConstant.FROM, getIntent().getIntExtra(AppConstant.FROM, 0));
        i.putExtra(AppConstant.REVIEW, mMatchDetail.isInReview());
        i.putExtra(AppConstant.MatchName, mMatchDetail.getSeries().getName());
        i.putExtra(AppConstant.DATA, mBattleShowList.get(position));
        startActivity(i);
        //Mo Engage
        Properties properties = new Properties();
        properties
                // tracking integer
                .addAttribute(AppConstant.MatchName, mMatchDetail.getSeries().getName())
                .addAttribute(AppConstant.BattleName, mBattleList.get(position).getTitle())
                // tracking Date
                .addAttribute(AppConstant.ClickedDate, new Date());
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.FAN_BATTLE, properties);
    }

    @Override
    public void onCategoryClicked(int position) {
        if (mBattleCategory.getCategories().get(position).getTitle().equalsIgnoreCase(AppConstant.FB_CLASSIC) && mAppPreference.getBoolean(AppConstant.FB_CLASSIC_VIEWED, false)) {
            mHowtoPlay = 1;
            mHowPlayTV.setText(R.string.text_clasic_play_how);
            onBattleCategory(position);
        } else if (mBattleCategory.getCategories().get(position).getTitle().equalsIgnoreCase(AppConstant.FB_RUMBLE) && mAppPreference.getBoolean(AppConstant.FB_RUMBLE_VIEWED, false)) {
            mHowtoPlay = 2;
            mHowPlayTV.setText(R.string.text_reverse_play_how);
            onBattleCategory(position);
        } else if (mBattleCategory.getCategories().get(position).getTitle().equalsIgnoreCase(AppConstant.FB_CLASSIC) && !mAppPreference.getBoolean(AppConstant.FB_CLASSIC_VIEWED, false)) {
            mHowtoPlay = 1;
            mHowPlayTV.setText(R.string.text_clasic_play_how);
            mAppPreference.setBoolean(AppConstant.FB_CLASSIC_VIEWED, true);
            AppUtilityMethods.showMsg(this, "You can invest on any combo in which you want but only one combo (top point score) will win the battle", false);
        } else if (mBattleCategory.getCategories().get(position).getTitle().equalsIgnoreCase(AppConstant.FB_RUMBLE) && !mAppPreference.getBoolean(AppConstant.FB_RUMBLE_VIEWED, false)) {
            mHowtoPlay = 2;
            mHowPlayTV.setText(R.string.text_reverse_play_how);
            mAppPreference.setBoolean(AppConstant.FB_RUMBLE_VIEWED, true);
            AppUtilityMethods.showMsg(this, "Same as classic! You can also invest here in any combo as your desire but only one combo (lowest point score) will win the battle", false);
        }
    }

    private void onBattleCategory(int position) {
        mCategoryId = mBattleCategory.getCategories().get(position).getId();
        for (int i = 0; i < mBattleCategory.getCategories().size(); i++) {
            mBattleCategory.getCategories().get(i).setSelected(false);
        }
        mBattleCategory.getCategories().get(position).setSelected(true);
        mBattleCategoryAdapter.notifyDataSetChanged();
        fetchBattleOnCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAppPreference.getBoolean(AppConstant.GROUP_JOINED, false)) {
            mAppPreference.setBoolean(AppConstant.GROUP_JOINED, false);
            getBattle();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}