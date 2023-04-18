package com.khiladiadda.battle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.adapter.BattleGroupAdapter;
import com.khiladiadda.battle.adapter.LiveBattleGroupAdapter;
import com.khiladiadda.battle.adapter.PastBattleGroupAdapter;
import com.khiladiadda.battle.interfaces.IBattlePresenter;
import com.khiladiadda.battle.interfaces.IBattleView;
import com.khiladiadda.battle.model.BannerResponse;
import com.khiladiadda.battle.model.BattleDetails;
import com.khiladiadda.battle.model.BattleGroupResponse;
import com.khiladiadda.battle.model.BattleResponse;
import com.khiladiadda.battle.model.GroupDetails;
import com.khiladiadda.battle.model.JoinGroupRequest;
import com.khiladiadda.dialogs.interfaces.IOnJoinBattleListener;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.league.participant.ParticipantActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class BattleGroupActivity extends BaseActivity implements IBattleView, IOnItemClickListener, BattleGroupAdapter.IOnItemChildClickListener, LiveBattleGroupAdapter.IOnItemChildClickListener, PastBattleGroupAdapter.IOnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.rv_group)
    RecyclerView mGroupRV;
    @BindView(R.id.tv_question)
    TextView mQuestionTV;
    @BindView(R.id.tv_prize)
    TextView mPrizeTV;
    @BindView(R.id.tv_participant)
    TextView mParticipantTV;
    @BindView(R.id.tv_select_combo)
    TextView mSelectComboTV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_live_battle)
    TextView mLiveBattleTV;
    @BindView(R.id.tv_how_play)
    TextView mHowPlayTV;
    @BindView(R.id.tv_calculate)
    TextView mCalculateTV;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshL;

    private BattleGroupAdapter mBattleGroupAdapter;
    private LiveBattleGroupAdapter mLiveBattleGroupAdapter;
    private PastBattleGroupAdapter mPastBattleGroupAdapter;
    private ArrayList<GroupDetails> mGroupList = new ArrayList<>();
    private IBattlePresenter mPresenter;
    private String mGroupId, mId;
    private BattleDetails mBattleDetails;
    private boolean mInReview, mProfile, mPoints, mReverse;
    private List<String> mPlayedGroup;
    private int mFrom;
    private long mGroupJoined, mBonus;
    private double mBattleInvestment;
    private boolean mIsFreeBattle;
    private double mFreeBattlePrizePool;
    private int mHowtoPlay;
    private Double mAmount;
    private String mMatchName;

    @Override
    protected int getContentView() {
        return R.layout.activity_battle_group;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_battles);
        mBackIV.setOnClickListener(this);
        mLiveBattleTV.setOnClickListener(this);
        mHowPlayTV.setOnClickListener(this);
        mCalculateTV.setOnClickListener(this);
        mSwipeRefreshL.setOnRefreshListener(this);
        mParticipantTV.setOnClickListener(this);
        Intent intent = getIntent();
        mFrom = intent.getIntExtra(AppConstant.FROM, 0);
        mBattleDetails = intent.getParcelableExtra(AppConstant.DATA);
        mInReview = intent.getBooleanExtra(AppConstant.REVIEW, false);
        mMatchName = intent.getStringExtra(AppConstant.MatchName);

        if (mBattleDetails != null) {
            setBattleData();
            mAppPreference.setIsDeepLinking(false);
            if (mBattleDetails.isReverse()) {
                mReverse = true;
            } else {
                mReverse = false;
            }
        }
        mId = intent.getStringExtra(AppConstant.GAMEID);
    }

    @Override
    protected void initVariables() {
        mPresenter = new BattlePresenter(this);
        mBattleGroupAdapter = new BattleGroupAdapter(mGroupList, mBattleInvestment, mGroupJoined, mIsFreeBattle, mFreeBattlePrizePool);
        mGroupRV.setLayoutManager(new LinearLayoutManager(this));
        mGroupRV.setAdapter(mBattleGroupAdapter);
        mBattleGroupAdapter.setOnItemChildClickListener(this);
        mLiveBattleGroupAdapter = new LiveBattleGroupAdapter(mGroupList, mBattleInvestment, mGroupJoined, mFreeBattlePrizePool, mIsFreeBattle);
        mGroupRV.setLayoutManager(new LinearLayoutManager(this));
        mLiveBattleGroupAdapter.setOnItemChildClickListener(this);
        mPastBattleGroupAdapter = new PastBattleGroupAdapter(mGroupList);
        mGroupRV.setLayoutManager(new LinearLayoutManager(this));
        mPastBattleGroupAdapter.setOnItemChildClickListener(this);
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
            case R.id.tv_how_play:
                Intent howToPlay = new Intent(this, BattleHelpActivity.class);
                howToPlay.putExtra(AppConstant.FROM, mHowtoPlay);
                startActivity(howToPlay);
                break;
            case R.id.tv_calculate:
                startActivity(new Intent(this, BattlePointsActivity.class));
                break;
            case R.id.tv_live_battle:
                if (mInReview) {
                    AppUtilityMethods.showInfo(this, mLiveBattleTV, getString(R.string.text_info_in_review));
                } else {
                    AppUtilityMethods.showInfo(this, mLiveBattleTV, getString(R.string.text_text_info_starting));
                }
                break;
            case R.id.tv_participant:
                if (mBattleDetails.getNParticipants() > 0) {
                    Intent i = new Intent(this, ParticipantActivity.class);
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_FB_BATTLE);
                    i.putExtra(AppConstant.ID, mBattleDetails.getId());
                    startActivity(i);
                }
                break;
        }
    }

    private void getGroup() {
        if (new NetworkStatus(this).isInternetOn()) {
            mSwipeRefreshL.setRefreshing(true);
            if (mBattleDetails != null) {
                mPresenter.getGroupList(mBattleDetails.getId(), mReverse, mProfile);
            } else {
                mPresenter.getGroupList(mId, false, mProfile);
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setBattleData() {
        mGroupJoined = mBattleDetails.getnGroupJoined();
        mBattleInvestment = mBattleDetails.getInvestedAmount();
        mPlayedGroup = mBattleDetails.getnGroupsPlayed();
        mIsFreeBattle = mBattleDetails.isFree();
        mBonus = mBattleDetails.getBonusCode();
        mFreeBattlePrizePool = mBattleDetails.getPrize();
        if (mBattleDetails.isReverse()) {
            mHowtoPlay = 2;
            mHowPlayTV.setText(R.string.text_reverse_play_how);
        } else {
            mHowtoPlay = 1;
            mHowPlayTV.setText(R.string.text_clasic_play_how);
        }
    }

    private void setData() {
        mSwipeRefreshL.setRefreshing(false);
        if (mBattleDetails.getNParticipants() > 0) {
            mParticipantTV.setText(String.format("%d %s", mBattleDetails.getNParticipants(), getString(R.string.text_player_participated_already)));
        } else {
            mParticipantTV.setText(getString(R.string.text_first_to_participate));
        }
        if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
            mActivityNameTV.setText(getString(R.string.text_live_battle));
            mSelectComboTV.setVisibility(View.GONE);
            mParticipantTV.setText(String.format("%d %s", mBattleDetails.getNParticipants(), getString(R.string.text_player_participated)));
            if (mIsFreeBattle) {
                mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(mBattleDetails.getPrize())));
            } else {
                mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(mBattleDetails.getLivePrizePool())));
            }
            if (mInReview) {
                mLiveBattleTV.setVisibility(View.VISIBLE);
                mLiveBattleTV.setText(getString(R.string.text_in_review));
            } else if (mPoints) {
                mLiveBattleTV.setVisibility(View.GONE);
            } else {
                mLiveBattleTV.setVisibility(View.VISIBLE);
            }
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
            if (mIsFreeBattle) {
                mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(mBattleDetails.getPrize())));
            } else {
                mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(mBattleDetails.getLivePrizePool())));
            }
            mSelectComboTV.setVisibility(View.GONE);
            mActivityNameTV.setText(getString(R.string.text_joined_battle));
        }
        if (!TextUtils.isEmpty(mBattleDetails.getQuestion())) {
            mQuestionTV.setText(String.format("Q. %s", mBattleDetails.getQuestion()));
        } else {
            mQuestionTV.setVisibility(View.GONE);
        }
        setBattleData();
    }

    @Override
    public void onGetBattleListComplete(BattleResponse responseModel) {
    }

    @Override
    public void onGetBattleListFailure(ApiError error) {
    }

    @Override
    public void onGetGroupListComplete(BattleGroupResponse responseModel) {
        mGroupList.clear();
        if (responseModel.isStatus()) {
            List<GroupDetails> detailsList = responseModel.getResponse();
            mGroupList.addAll(detailsList);
            mBattleDetails = responseModel.getBattleDetails();
            setBattleData();
            if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
                for (GroupDetails groupDetails : detailsList) {
                    if (groupDetails.getPoints() > 0) {
                        mPoints = true;
                        break;
                    }
                }
                mLiveBattleGroupAdapter.setTopScore();
                mGroupRV.setAdapter(mLiveBattleGroupAdapter);
            } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
                mGroupRV.setAdapter(mPastBattleGroupAdapter);
            } else {
                if (mProfile) {
                    mAppPreference.setProfileData(responseModel.getProfileDetails());
                }
                mBattleGroupAdapter.updateData(mBattleInvestment, mGroupJoined);
                mBattleGroupAdapter.notifyDataSetChanged();
                double prizePool = 0;
                if (mGroupJoined > 1) {
                    prizePool = (mBattleInvestment - (mBattleInvestment / 10));
                } else {
                    for (GroupDetails groupDetails : detailsList) {
                        if (groupDetails.getNParticipants() == 1 && groupDetails.getPlayed() != null) {
                            prizePool = prizePool + (groupDetails.getPlayed().getAmount() * 1.2);
                        } else if (groupDetails.getNParticipants() >= 1) {
                            prizePool = prizePool + (groupDetails.getInvestedAmount() * 1.2);
                        }
                    }
                }
                if (mBattleDetails.isFree()) {
                    mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(mBattleDetails.getPrize())));
                } else {
                    mPrizeTV.setText(String.format("Prize Pool\n₹%s", new DecimalFormat("##.##").format(prizePool)));
                }
            }
        }
        setData();
    }

    @Override
    public void onGetGroupListFailure(ApiError error) {
        mSwipeRefreshL.setRefreshing(false);
    }

    @Override
    public void onJoinComplete(BaseResponse responseModel) {
        hideProgress();
        mProfile = true;
        mAppPreference.setBoolean(AppConstant.GROUP_JOINED, true);
        if (responseModel.isStatus()) {
            showMsg(getString(R.string.text_group_joined_success), true);
            //Apps Flyer
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, AppConstant.FAN_BATTLE);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties properties = new Properties();
            properties.addAttribute("invested in combo Amount", mAmount)
                    .addAttribute(AppConstant.MatchName, mMatchName)
                    .addAttribute(AppConstant.BattleName, mBattleDetails.getTitle())
                    .addAttribute("Winning", responseModel.getWalletObj().getWinning())
                    .addAttribute("Deposit", responseModel.getWalletObj().getDeposit())
                    .addAttribute("Bonus", responseModel.getWalletObj().getBonus())
                    .addAttribute("invested in combo Date", new Date());
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.FAN_BATTLE, properties);
        } else {
            showMsg(responseModel.getMessage(), false);
        }
    }

    @Override
    public void onJoinFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetCalculationBannerComplete(BannerResponse responseModel) {

    }

    @Override
    public void onGetCalculationBannerFailure(ApiError error) {

    }

    @Override
    public void onCancelComplete(BaseResponse responseModel) {
        hideProgress();
        mProfile = true;
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsg(this, "You have successfully un-joined this group.\nThe amount has been refunded to your wallet.\nJoin another group to earn more.", false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onCancelFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onJoinSubstituteComplete(BaseResponse responseModel) {

    }

    @Override
    public void onJoinSubstituteFailure(ApiError error) {

    }

    @Override
    public void onItemClick(View view, int position, int tag) {
    }

    private IOnJoinBattleListener onJoinGroupListener = new IOnJoinBattleListener() {
        @Override
        public void onJoinBattleListener(double amount) {
            joinGroup(amount);
        }

        @Override
        public void onCancelBattleListener(int position) {
            mBattleGroupAdapter.setSelectedIndex(-1);
            mBattleGroupAdapter.notifyDataSetChanged();
            mGroupJoined = mGroupJoined - 1;
        }
    };

    private void joinGroup(double amount) {
        JoinGroupRequest request = new JoinGroupRequest();
        request.setAmount(amount);
        mAmount = amount;
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.joinBattleGroup(request, mGroupId);
    }

    @Override
    public void onPointsClicked(int position) {
        Intent i = new Intent(this, PlayerPointsActivity.class);
        i.putParcelableArrayListExtra(AppConstant.DATA, mGroupList.get(position).getPlayers());
        startActivity(i);
    }

    @Override
    public void onInvestedAmountClicked(int position) {
        Snackbar.make(mLiveBattleTV, R.string.text_invested_amount, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onEstimatedProfitClicked(int position) {
        if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
            Snackbar.make(mLiveBattleTV, getString(R.string.text_winning_amount), Snackbar.LENGTH_LONG).show();
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
            Snackbar.make(mLiveBattleTV, getString(R.string.text_estimated_winning_won), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar snackbar = Snackbar.make(mLiveBattleTV, getString(R.string.text_estimated_winning_vary), Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView snackTextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            snackTextView.setMaxLines(3);
            snackbar.show();
        }
    }

    @Override
    public void onParticipantClicked(int position) {
        Intent i = new Intent(this, ParticipantActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.FROM_FB_GROUP);
        i.putExtra(AppConstant.ID, mGroupList.get(position).getId());
        startActivity(i);
    }

    @Override
    public void onInvestClicked(int position) {
        mBattleGroupAdapter.setSelectedIndex(position);
        mBattleGroupAdapter.notifyDataSetChanged();
        mGroupId = mGroupList.get(position).getId();
        setBattleData();
        if (mGroupJoined == 1) {
            if (mPlayedGroup.size() > 0) {
                if (!mPlayedGroup.contains(mGroupList.get(position).getId())) {
                    mGroupJoined = mGroupJoined + 1;
                }
            }
        }
        if (mIsFreeBattle) {
            confirmFreeInvest();
        } else {
            new JoinBattleDialog(this, onJoinGroupListener, position, mBattleInvestment, mGroupList.get(position).getInvestedAmount(), mGroupJoined, 0, 1, mBonus, mIsFreeBattle);
        }
    }

    @Override
    public void onPlayerProfileClicked(String playerId) {
    }

    @Override
    public void onCancelClicked(int position) {
        showConfirmationMsg(position);
    }

    public void showConfirmationMsg(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.logout);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(R.string.text_substitute_help);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setText(R.string.cancel);
        Button noBTN = dialog.findViewById(R.id.btn_no);
        noBTN.setText(R.string.btn_continue);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        noBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            Intent i = new Intent(this, SelectGroupActivity.class);
            i.putExtra(AppConstant.DATA, mBattleDetails);
            ArrayList<GroupDetails> groupList = new ArrayList<>(mGroupList);
            groupList.remove(position);
            i.putParcelableArrayListExtra(AppConstant.DATA_QUIZ, groupList);
            i.putExtra(AppConstant.TXN_AMOUNT, mGroupList.get(position).getPlayed().getAmount());
            i.putExtra(AppConstant.ID, mGroupList.get(position).getId());
            i.putExtra(AppConstant.BattleName,mBattleDetails.getTitle());
            startActivity(i);
        });
        dialog.show();
    }

    public void showMsg(String msg, boolean check) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup);
        LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
        ImageView waitimage = dialog.findViewById(R.id.waiting);
        if (check) {
            animationView.setVisibility(View.VISIBLE);
        } else {
            waitimage.setVisibility(View.VISIBLE);
        }
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            getGroup();
        });
        dialog.show();
    }

    public void confirmFreeInvest() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.logout);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText("1. This is a free group.\n2. You can invest in this group but no amount will get deducted.\n3. You can only invest on one group in a free battle.\n\nAre you sure want to invest in this group?");
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            joinGroup(0);
            dialog.dismiss();
        });
        Button noBTN = dialog.findViewById(R.id.btn_no);
        noBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        getGroup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroup();
    }

    @Override
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}