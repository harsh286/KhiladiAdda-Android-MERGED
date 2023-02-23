package com.khiladiadda.clashx2.kabaddi.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashx2.cricket.createbattle.CreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattleView;
import com.khiladiadda.clashx2.kabaddi.createbattle.KabaddiCreateTeamActivity;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.activity.MyFanLeagueActivityHTH;
import com.khiladiadda.clashx2.main.activity.SelectedPlayers;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;
import com.khiladiadda.network.model.response.hth.CaptainTeamHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
//import com.khiladiadda.clashx2.kabaddi.createbattle.KabaddiCreateTeamActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class KabaddiSelectedPlayerActivity extends BaseActivity implements IHTHBattleView, ICreateBattleView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_one)
    ImageView mTeamOneIV;
    @BindView(R.id.iv_two)
    ImageView mTeamTwoIV;
    @BindView(R.id.tv_team_one)
    TextView mTeamOneTV;
    @BindView(R.id.tv_team_two)
    TextView mTeamTwoTV;
    @BindView(R.id.iv_player1edit)
    CircleImageView mTPlayerOneEditIV;
    @BindView(R.id.iv_player2edit)
    CircleImageView mTPlayerTwoEditIV;
    @BindView(R.id.iv_player3edit)
    CircleImageView mTPlayerThreeEditIV;
    @BindView(R.id.iv_player4edit)
    CircleImageView mTPlayerFourEditIV;
    @BindView(R.id.iv_player5edit)
    CircleImageView mTPlayerFiveEditIV;
    @BindView(R.id.iv_player6edit)
    CircleImageView mTPlayerSixEditIV;
    @BindView(R.id.tv_player1edit)
    TextView mTPlayerOneEditTV;
    @BindView(R.id.tv_player2edit)
    TextView mTPlayerTwoEditTV;
    @BindView(R.id.tv_player3edit)
    TextView mTPlayerThreeEditTV;
    @BindView(R.id.tv_player4edit)
    TextView mTPlayerFourEditTV;
    @BindView(R.id.tv_player5edit)
    TextView mTPlayerFiveEditTV;
    @BindView(R.id.tv_player6edit)
    TextView mTPlayerSixEditTV;
    @BindView(R.id.tv_player1static)
    TextView mTPlayerOneStaticTV;
    @BindView(R.id.tv_player2static)
    TextView mTPlayerTwoStaticTV;
    @BindView(R.id.tv_player3static)
    TextView mTPlayerThreeStaticTV;
    @BindView(R.id.tv_player4static)
    TextView mTPlayerFourStaticTV;
    @BindView(R.id.tv_player5static)
    TextView mTPlayerFiveStaticTV;
    @BindView(R.id.tv_player6static)
    TextView mTPlayerSixStaticTV;
    @BindView(R.id.iv_player1)
    CircleImageView mTPlayerOneIV;
    @BindView(R.id.iv_player2)
    CircleImageView mTPlayerTwoIV;
    @BindView(R.id.iv_player3)
    CircleImageView mTPlayerThreeIV;
    @BindView(R.id.iv_player4)
    CircleImageView mTPlayerFourIV;
    @BindView(R.id.iv_player5)
    CircleImageView mTPlayerFiveIV;
    @BindView(R.id.iv_player6)
    CircleImageView mTPlayerSixIV;
    @BindView(R.id.tv_player1)
    TextView mTPlayerOneTV;
    @BindView(R.id.tv_player2)
    TextView mTPlayerTwoTV;
    @BindView(R.id.tv_player3)
    TextView mTPlayerThreeTV;
    @BindView(R.id.tv_player4)
    TextView mTPlayerFourTV;
    @BindView(R.id.tv_player5)
    TextView mTPlayerFiveTV;
    @BindView(R.id.tv_player6)
    TextView mTPlayerSixTV;
    @BindView(R.id.tv_player1_opp_static)
    TextView mTPlayerOneOppTV;
    @BindView(R.id.tv_player2_opp_static)
    TextView mTPlayerTwoOppTV;
    @BindView(R.id.tv_player3_opp_static)
    TextView mTPlayerThreeOppTV;
    @BindView(R.id.tv_player4_opp_static)
    TextView mTPlayerFourOppTV;
    @BindView(R.id.tv_player5_opp_static)
    TextView mTPlayerFiveOppTV;
    @BindView(R.id.tv_player6_opp_static)
    TextView mTPlayerSixOppTV;
    @BindView(R.id.btn_edit)
    AppCompatButton mEditBTN;
    @BindView(R.id.tv_text_nameopp)
    TextView mOppTV;
    @BindView(R.id.iv_team_imageopp)
    ImageView mOPPIV;
    @BindView(R.id.iv_team_imagemy)
    ImageView mTeamCapIV;
    @BindView(R.id.btn_cancel)
    AppCompatButton mCancelBTN;
    @BindView(R.id.rl_main)
    RelativeLayout mMainRL;
    @BindView(R.id.ll_change)
    LinearLayout mChangeLL;
    @BindView(R.id.tv_some_player)
    TextView mSomePlayersTV;
    @BindView(R.id.btn_accept)
    AppCompatButton mAcceptBTN;
    @BindView(R.id.editable)
    CardView mEdaitbleCV;
    @BindView(R.id.tv_battleid)
    TextView mContestId;
    @BindView(R.id.ll_yourteam_down)
    LinearLayout mLlYourteamDown;
    @BindView(R.id.ll_opp_team)
    LinearLayout mLLOppTeam;
    private Double mAmount;

    private HTHResponseDetails mMatchDetail;
    private BattlesDeatilsHTH mBattleList;
    private String mBattleID;
    private IHTHBattlePresenter mPresenter;
    private ICreateBattlePresenter mPresenterB;
    private ArrayList<BattlesDeatilsHTH> mMyBattleList = new ArrayList<>();
    private double mDepositWinWallet;
    private double mTotalWalletBal;
    private int mCheck;

    @Override
    protected int getContentView() {
        return R.layout.activity_kabaadi_selected_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiver, new IntentFilter("com.khiladiadda.HTH_NOTIFY"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_NOTIFY"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverLiveMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY"));
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mEditBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);
        mChangeLL.setOnClickListener(this);
        mAcceptBTN.setOnClickListener(this);
        mEdaitbleCV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new HTHPresenter(this);
        mPresenterB = new CreateBattlePresenter(this);
        mMatchDetail = getIntent().getParcelableExtra(AppConstant.MATCH_DATA);
        mBattleList = getIntent().getParcelableExtra(AppConstant.BATTLE_DATA);
        mBattleID = getIntent().getStringExtra(AppConstant.ID);
        TeamHTH homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeam();
        TeamHTH awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeam();
        mTeamOneTV.setText(homeTeam.getName());
        Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamOneIV);
        mTeamTwoTV.setText(awayTeam.getName());
        Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamTwoIV);
        mContestId.setText("Battle ID : " + mBattleList.getContestId());
        Coins mCoins = AppSharedPreference.getInstance().getProfileData().getCoins();
        mDepositWinWallet = mCoins.getDeposit() + mCoins.getWinning();
        mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_edit:
            case R.id.ll_change:
                if (new NetworkStatus(this).isInternetOn()) {
                    mEditBTN.setClickable(false);
                    mPresenter.getHTHMatchList(mMatchDetail.getId(), 0);
                    mCheck = 1;
                } else {
                    Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                acceptAlert(this);
                break;
            case R.id.editable:
                if (mMyBattleList.get(0).getnParticipants() == 2) {
                    mPresenter.getHTHMatchList(mMatchDetail.getId(), 0);
                    mCheck = 2;
                } else {
                    showConfirmation(mBattleList.getInvestedAmount());
                }
                mEdaitbleCV.setClickable(false);
                break;
            case R.id.btn_accept:
                if (mMyBattleList.get(0).getnParticipants() == 2) {
                    mPresenter.getHTHMatchList(mMatchDetail.getId(), 0);
                    mCheck = 2;
                } else {
                    showConfirmation(mBattleList.getInvestedAmount());
                }
                mAcceptBTN.setClickable(false);
                break;
        }
    }

    @Override
    public void onGetHTHMatchListComplete(HTHMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (mCheck == 2) {
                Intent createBattle1 = new Intent(this, KabaddiCreateTeamActivity.class);
                createBattle1.putExtra(AppConstant.FROM, AppConstant.FROM_ACCEPT);
                createBattle1.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) responseModel.getResponse());
                createBattle1.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(0));
                createBattle1.putExtra(AppConstant.ID, mMyBattleList.get(0).getId());
                createBattle1.putExtra(AppConstant.FROM_PLAYER, AppConstant.Opponent);
                createBattle1.putExtra(AppConstant.GROUP_JOINED, getIntent().getStringExtra(AppConstant.GROUP_JOINED));
                startActivityForResult(createBattle1, AppConstant.REQUEST_GALLERY);
            } else {
                Matches(responseModel.getResponse(), mMyBattleList.get(0).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId()));
            }
        }
    }

    @Override
    public void onGetHTHMatchListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onCancelBattle(HTHCancelResponse responseModel) {
        if (responseModel.isStatus()) {
            mAppPreference.setProfileData(responseModel.getProfile());
            AppUtilityMethods.showMsg(this, "Battle Cancelled Successfully", true);
            finish();
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

    }

    @Override
    public void onMatchStatusError(ApiError error) {

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
        hideProgress();
        if (responseModel.isStatus()) {
            showMsgBigDialog(responseModel.getMessage());
            mAppPreference.setProfileData(responseModel.getProfile());
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, "ClashX Joined");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onAcceptFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onAllBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onAllBattlesFailure(ApiError error) {

    }

    @Override
    public void onMyBattlesComplete(BattleResponseHTH responseModel) {
        hideProgress();
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mMyBattleList.clear();
            mMyBattleList.addAll(responseModel.getResponse());
            data(mMyBattleList);
        }
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
    protected void onResume() {
        super.onResume();
        getData();
        mEditBTN.setClickable(true);
        mAcceptBTN.setClickable(true);
    }

    private void setData(List<BattlesDeatilsHTH> mMyBattleList) {
        List<CaptainTeamHTH> mBattleCaptainDetails = mMyBattleList.get(0).getCaptainTeam();
        List<CaptainTeamHTH> mBattleOpponentDetails = mMyBattleList.get(0).getOpponentTeam();
        if (mMyBattleList.get(0).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
            mLlYourteamDown.setVisibility(View.VISIBLE);
            mLLOppTeam.setVisibility(View.VISIBLE);
            mTPlayerOneStaticTV.setText("Raider");
            mTPlayerTwoStaticTV.setText("Raider");
            mTPlayerThreeStaticTV.setText("Defender");
            mTPlayerFourStaticTV.setText("Defender");
            mTPlayerFiveStaticTV.setText("AR");
            mTPlayerSixStaticTV.setText("AR");
            mTPlayerOneOppTV.setText("Raider");
            mTPlayerTwoOppTV.setText("Raider");
            mTPlayerThreeOppTV.setText("Defender");
            mTPlayerFourOppTV.setText("Defender");
            mTPlayerFiveOppTV.setText("AR");
            mTPlayerSixOppTV.setText("AR");
            if (!TextUtils.isEmpty(mMyBattleList.get(0).getCaptain().getDp()) && mMyBattleList.get(0).getCaptain().getDp().startsWith("https")) {
                Glide.with(this).load(mMyBattleList.get(0).getCaptain().getDp()).placeholder(R.drawable.profile).into(mTeamCapIV);
            } else {
                Glide.with(mTeamCapIV.getContext()).clear(mTeamCapIV);
                mTeamCapIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(0).getImg()) && mBattleCaptainDetails.get(0).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 1, mTPlayerOneEditIV, mTPlayerOneEditTV);
            } else {
                Glide.with(mTPlayerOneEditIV.getContext()).clear(mTPlayerOneEditIV);
                mTPlayerOneEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(1).getImg()) && mBattleCaptainDetails.get(1).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 2, mTPlayerTwoEditIV, mTPlayerTwoEditTV);
            } else {
                Glide.with(mTPlayerTwoEditIV.getContext()).clear(mTPlayerTwoEditIV);
                mTPlayerTwoEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(2).getImg()) && mBattleCaptainDetails.get(2).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 3, mTPlayerThreeEditIV, mTPlayerThreeEditTV);
            } else {
                Glide.with(mTPlayerThreeEditIV.getContext()).clear(mTPlayerThreeEditIV);
                mTPlayerThreeEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(3).getImg()) && mBattleCaptainDetails.get(3).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 4, mTPlayerFourEditIV, mTPlayerFourEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(4).getImg()) && mBattleCaptainDetails.get(4).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 5, mTPlayerFiveEditIV, mTPlayerFiveEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFiveEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(5).getImg()) && mBattleCaptainDetails.get(5).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerSixEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            ///opp

            if (mBattleOpponentDetails != null && !mBattleOpponentDetails.isEmpty()) {
                mOppTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
                Glide.with(mOPPIV.getContext()).load(mMyBattleList.get(0).getOpponent().getDp()).placeholder(R.drawable.splash_logo).into(mOPPIV);
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(0).getImg()) && mBattleOpponentDetails.get(0).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
                } else {
                    Glide.with(mTPlayerOneIV.getContext()).clear(mTPlayerOneIV);
                    mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(1).getImg()) && mBattleOpponentDetails.get(1).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 2, mTPlayerTwoIV, mTPlayerTwoTV);
                } else {
                    Glide.with(mTPlayerTwoIV.getContext()).clear(mTPlayerTwoIV);
                    mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(2).getImg()) && mBattleOpponentDetails.get(2).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 3, mTPlayerThreeIV, mTPlayerThreeTV);
                } else {
                    Glide.with(mTPlayerThreeIV.getContext()).clear(mTPlayerThreeIV);
                    mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(3).getImg()) && mBattleOpponentDetails.get(3).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 4, mTPlayerFourIV, mTPlayerFourTV);
                } else {
                    Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                    mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(4).getImg()) && mBattleOpponentDetails.get(4).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 5, mTPlayerFiveIV, mTPlayerFiveTV);
                } else {
                    Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFiveIV);
                    mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(5).getImg()) && mBattleOpponentDetails.get(5).getImg().startsWith("https")) {
                    setOpponentImage(mBattleOpponentDetails, 6, mTPlayerSixIV, mTPlayerSixTV);
                } else {
                    Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerSixIV);
                    mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
                }
            } else {
                mOppTV.setText("Opponent yet to join");
                mLLOppTeam.setVisibility(View.GONE);
                mTPlayerOneOppTV.setText("Raider");
                mTPlayerTwoOppTV.setText("Defender");
                mTPlayerThreeOppTV.setText("AR");
                mOPPIV.setImageResource(R.drawable.splash_logo);
            }
            substituteData(true, mMyBattleList);
        } else {
            mLLOppTeam.setVisibility(View.VISIBLE);
            mLlYourteamDown.setVisibility(View.VISIBLE);
            mOppTV.setText("Opponent's Combo");//change for opponent combo when players join.

            mLLOppTeam.setVisibility(View.VISIBLE);
            mTPlayerOneStaticTV.setText("Raider");
            mTPlayerTwoStaticTV.setText("Raider");
            mTPlayerThreeStaticTV.setText("Defender");
            mTPlayerFourStaticTV.setText("Defender");
            mTPlayerFiveStaticTV.setText("AR");
            mTPlayerSixStaticTV.setText("AR");
            mTPlayerOneOppTV.setText("Raider");
            mTPlayerTwoOppTV.setText("Raider");
            mTPlayerThreeOppTV.setText("Defender");
            mTPlayerFourOppTV.setText("Defender");
            mTPlayerFiveOppTV.setText("AR");
            mTPlayerSixOppTV.setText("AR");

            Glide.with(mOPPIV.getContext()).load(mMyBattleList.get(0).getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mOPPIV);
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(0).getImg()) && mBattleCaptainDetails.get(0).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
            } else {
                Glide.with(mTPlayerOneIV.getContext()).clear(mTPlayerOneIV);
                mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(1).getImg()) && mBattleCaptainDetails.get(1).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 2, mTPlayerTwoIV, mTPlayerTwoTV);
            } else {
                Glide.with(mTPlayerTwoIV.getContext()).clear(mTPlayerTwoIV);
                mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(2).getImg()) && mBattleCaptainDetails.get(2).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 3, mTPlayerThreeIV, mTPlayerThreeTV);
            } else {
                Glide.with(mTPlayerThreeIV.getContext()).clear(mTPlayerThreeIV);
                mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(3).getImg()) && mBattleCaptainDetails.get(3).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 4, mTPlayerFourIV, mTPlayerFourTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(4).getImg()) && mBattleCaptainDetails.get(4).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 5, mTPlayerFiveIV, mTPlayerFiveTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFiveIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(5).getImg()) && mBattleCaptainDetails.get(5).getImg().startsWith("https")) {
                setOpponentImage(mBattleCaptainDetails, 6, mTPlayerSixIV, mTPlayerSixTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerSixIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            ///opp
            if (mBattleOpponentDetails != null && !mBattleOpponentDetails.isEmpty()) {
                mOppTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
                if (!TextUtils.isEmpty(mMyBattleList.get(0).getOpponent().getDp()) && mMyBattleList.get(0).getOpponent().getDp().startsWith("https")) {
                    Glide.with(this).load(mMyBattleList.get(0).getOpponent().getDp()).placeholder(R.drawable.profile).into(mTeamCapIV);
                } else {
                    Glide.with(mTeamCapIV.getContext()).clear(mTeamCapIV);
                    mTeamCapIV.setImageResource(R.drawable.splash_logo);
                }
                Glide.with(mOPPIV.getContext()).load(mMyBattleList.get(0).getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mOPPIV);
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(0).getImg()) && mBattleOpponentDetails.get(0).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 1, mTPlayerOneEditIV, mTPlayerOneEditTV);
                } else {
                    Glide.with(mTPlayerOneEditIV.getContext()).clear(mTPlayerOneEditIV);
                    mTPlayerOneEditIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(1).getImg()) && mBattleOpponentDetails.get(1).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 2, mTPlayerTwoEditIV, mTPlayerTwoEditTV);
                } else {
                    Glide.with(mTPlayerTwoEditIV.getContext()).clear(mTPlayerTwoEditIV);
                    mTPlayerTwoEditIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(2).getImg()) && mBattleOpponentDetails.get(2).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 3, mTPlayerThreeEditIV, mTPlayerThreeEditTV);
                } else {
                    Glide.with(mTPlayerThreeEditIV.getContext()).clear(mTPlayerThreeEditIV);
                    mTPlayerThreeEditIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(3).getImg()) && mBattleOpponentDetails.get(3).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 4, mTPlayerFourEditIV, mTPlayerFourEditTV);
                } else {
                    Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                    mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(4).getImg()) && mBattleOpponentDetails.get(4).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 5, mTPlayerFiveEditIV, mTPlayerFiveEditTV);
                } else {
                    Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFiveEditIV);
                    mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
                }
                if (!TextUtils.isEmpty(mBattleOpponentDetails.get(5).getImg()) && mBattleOpponentDetails.get(5).getImg().startsWith("https")) {
                    setCaptainImage(mBattleOpponentDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
                } else {
                    Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerSixEditIV);
                    mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
                }
            } else {
                mTPlayerOneStaticTV.setText("Raider");
                mTPlayerTwoStaticTV.setText("Defender");
                mTPlayerThreeStaticTV.setText("AR");
                mLlYourteamDown.setVisibility(View.GONE);
            }
            substituteData(false, mMyBattleList);
        }
    }

    private void setCaptainImage(List<CaptainTeamHTH> mBattleDetails, int i, CircleImageView mTPlayerIV, TextView mTPlayerTV) {
        Glide.with(this).load(mBattleDetails.get(i - 1).getImg()).placeholder(R.drawable.profile).into(mTPlayerIV);
        mTPlayerIV.setBorderColor(getResources().getColor(R.color.redhth));
        mTPlayerIV.setBorderWidth(4);
        mTPlayerTV.setText(mBattleDetails.get(i - 1).getTitle());
    }

    private void setOpponentImage(List<CaptainTeamHTH> mBattleDetails, int i, CircleImageView mTPlayerIV, TextView mTPlayerTV) {
        Glide.with(this).load(mBattleDetails.get(i - 1).getImg()).placeholder(R.drawable.profile).into(mTPlayerIV);
        mTPlayerIV.setBorderColor(getResources().getColor(R.color.redhth));
        mTPlayerIV.setBorderWidth(4);
        mTPlayerTV.setText(mBattleDetails.get(i - 1).getTitle());
    }

    private void substituteData(boolean isCaptain, List<BattlesDeatilsHTH> mMyBattleList) {
        List<CaptainTeamHTH> battleDetails;
        if (mMyBattleList.get(0).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
            battleDetails = mMyBattleList.get(0).getCaptainTeam();
        } else {
            battleDetails = mMyBattleList.get(0).getOpponentTeam();
        }
        if (battleDetails.get(0).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        } else if (battleDetails.get(1).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        } else if (battleDetails.get(2).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        } else if (battleDetails.get(3).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        }else if (battleDetails.get(4).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        }else if (battleDetails.get(5).isSubstitute()) {
            mEditBTN.setVisibility(View.GONE);
            mChangeLL.setVisibility(View.VISIBLE);
            mSomePlayersTV.setVisibility(View.VISIBLE);
            mMainRL.setBackgroundResource(R.drawable.ic_playernotplaying);
        } else {
            mMainRL.setBackgroundResource(0);
            mChangeLL.setVisibility(View.GONE);
            mSomePlayersTV.setVisibility(View.GONE);
        }
    }

    private void data(List<BattlesDeatilsHTH> mMyBattleList) {
        if (mMyBattleList.get(0).getnParticipants() == 1) {
            if (mMyBattleList.get(0).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
                mCancelBTN.setVisibility(View.VISIBLE);
                mAcceptBTN.setVisibility(View.GONE);
                mEditBTN.setVisibility(View.VISIBLE);
                mEdaitbleCV.setClickable(false);
            } else {
                mCancelBTN.setVisibility(View.GONE);
                mAcceptBTN.setVisibility(View.VISIBLE);
                mEditBTN.setVisibility(View.GONE);
                mEdaitbleCV.setClickable(true);
            }
        } else if (mMyBattleList.get(0).getnParticipants() == 2 && mMyBattleList.get(0).getOpponentTeam().isEmpty()) {
            if (!mMyBattleList.get(0).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
                mCancelBTN.setVisibility(View.GONE);
                mAcceptBTN.setVisibility(View.VISIBLE);
                mAcceptBTN.setText("Create Combo");
                mEditBTN.setVisibility(View.GONE);
                mEdaitbleCV.setClickable(false);
            } else {
                mCancelBTN.setVisibility(View.GONE);
                mAcceptBTN.setVisibility(View.GONE);
                mEditBTN.setVisibility(View.VISIBLE);
                mEdaitbleCV.setClickable(false);
            }
        } else if (mMyBattleList.get(0).getnParticipants() == 2 && !mMyBattleList.get(0).getOpponentTeam().isEmpty()) {
            mCancelBTN.setVisibility(View.GONE);
            mAcceptBTN.setVisibility(View.GONE);
            mEditBTN.setVisibility(View.VISIBLE);
            mEdaitbleCV.setClickable(false);
        }
        setData(mMyBattleList);
    }

    public void acceptAlert(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.onCancelBattle(mBattleID);
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
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
        TextView mEstimatedProfitTV = dialog.findViewById(R.id.tv_estimated_winning);
        TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        TextView mTotalWalletBalanceTV = dialog.findViewById(R.id.tv_total_wallet_balance);
        TextView mDepositWalletTV = dialog.findViewById(R.id.tv_wallet_entry);
        TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        TextView hintTV = dialog.findViewById(R.id.tv_hint);
        mEntryFeeTV.setText(String.valueOf(amt));
        TextView heading = dialog.findViewById(R.id.tv_heading);
        heading.setText("Battle Amount");
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
                AcceptBattle(amt);
                dialog.dismiss();
            } else {
                mRechargeTV.setVisibility(View.VISIBLE);
                AppUtilityMethods.showRechargeMsghth(this, AppConstant.INSUFFICIENT_WALLET_RECHARGE);
                dialog.dismiss();
                mAcceptBTN.setClickable(true);
                mEdaitbleCV.setClickable(true);
            }
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            mAcceptBTN.setClickable(true);
            mEdaitbleCV.setClickable(true);
        });
        dialog.show();
    }

    public void showMsgBigDialog(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.combobig);
        TextView text = dialog.findViewById(R.id.tv_text);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            mAcceptBTN.setClickable(true);
            dialog.dismiss();
            getData();
        });
        dialog.show();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mPresenterB.getMyBattles(mMatchDetail.getId(), mBattleID, false, false, false, false);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void AcceptBattle(double amt) {
        showProgress(getString(R.string.txt_progress_authentication));
        CreateBattleRequest createBattleRequest = new CreateBattleRequest();
        createBattleRequest.setAmount(amt);
        createBattleRequest.setBattleId(mBattleID);
        createBattleRequest.setMatchId(mMatchDetail.getId());
        mAmount = amt;
        mPresenterB.acceptBattleGroup(createBattleRequest);
    }

    private void Matches(List<HTHResponseDetails> matches, boolean isCaptain) {
        Intent createBattle = new Intent(this, KabaddiCreateTeamActivity.class);
        createBattle.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE);
        createBattle.putParcelableArrayListExtra(AppConstant.MATCH_DATA, (ArrayList<? extends Parcelable>) matches);
        createBattle.putExtra(AppConstant.BATTLE_DATA, mMyBattleList.get(0));
        if (isCaptain) {
            createBattle.putExtra(AppConstant.FROM_PLAYER, AppConstant.Captain);
        } else {
            createBattle.putExtra(AppConstant.FROM_PLAYER, AppConstant.Opponent);
        }
        createBattle.putExtra(AppConstant.GROUP_JOINED, getIntent().getStringExtra(AppConstant.GROUP_JOINED));
        startActivityForResult(createBattle, AppConstant.REQUEST_GALLERY);
    }

    public void showMsgErrorSmallDialog(String msg, boolean live) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.error_dialog);
        TextView text = dialog.findViewById(R.id.tv_text);
        ImageView image = dialog.findViewById(R.id.iv_tick);
        TextView heading = dialog.findViewById(R.id.tv_heading);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        if (live) {
            heading.setBackgroundColor(getResources().getColor(R.color.yellowhth));
            image.setImageResource(R.drawable.ic_alert_icon_yellow);
        }
        okBTN.setOnClickListener(arg0 -> {
            if (live) {
                dialog.dismiss();
//                Intent resultIntent = new Intent();
//                setResult(RESULT_OK, resultIntent);
//                finish();
                finish();
                Intent i = new Intent(KabaddiSelectedPlayerActivity.this, MyFanLeagueActivityHTH.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_LIVE);
                startActivity(i);
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private BroadcastReceiver mHTHNotificationReceiverLiveMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHLIVEREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    mAppPreference.setBoolean(AppConstant.MATCH_LIVE, true);
                    showMsgErrorSmallDialog("LIVE MATCH", true);
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
                    getData();
                }
            }
        }
    };

    private final BroadcastReceiver mHTHNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.CLASHOFFANS)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    getData();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverMatches);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverLiveMatches);
        mPresenter.destroy();
        mPresenterB.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAppPreference.setBoolean(AppConstant.MATCH_LIVE, false);
    }
}