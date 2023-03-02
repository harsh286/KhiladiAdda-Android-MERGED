package com.khiladiadda.clashx2.cricket.createbattle;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashx2.cricket.createbattle.adapter.HeadToHeadFragmentPagerAdapter;
import com.khiladiadda.clashx2.cricket.createbattle.fragment.PlayerFragment;
import com.khiladiadda.clashx2.cricket.createbattle.fragment.ReviewFragment;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattleView;
import com.khiladiadda.dialogs.interfaces.IOnCreateBattleListener;
import com.khiladiadda.clashx2.main.activity.HTHBattlesActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.PlayerId;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.request.hth.UpdateOpponentPlayers;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

public class CreateTeamActivity extends BaseActivity implements ICreateBattleView, ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.iv_one)
    ImageView mTeamOneIV;
    @BindView(R.id.iv_two)
    ImageView mTeamTwoIV;
    @BindView(R.id.tv_team_one)
    TextView mTeamOneTV;
    @BindView(R.id.tv_team_two)
    TextView mTeamTwoTV;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.btn_next)
    TextView mNextBTN;
    @BindView(R.id.rl_next)
    RelativeLayout mNextRL;
    @BindView(R.id.iv_batmanselected)
    ImageView mBatmanSelectedIV;
    @BindView(R.id.iv_bowlselected)
    ImageView mBowlSelectedIV;
    @BindView(R.id.iv_arselected)
    ImageView mArSelectedIV;
    @BindView(R.id.iv_wkselected)
    ImageView mWkSelectedIV;
    @BindView(R.id.iv_allselected)
    ImageView mALLSelectedIV;
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    private Double mAmount;

    CreateBattleRequest createBattleRequest = new CreateBattleRequest();
    UpdateOpponentPlayers updateOpponentPlayers = new UpdateOpponentPlayers();
    List<PlayerId> playerIdList = new ArrayList<>();
    private String mFromPLayer;
    private final List<KaPlayerHTH> playing = new ArrayList<>();
    private final List<KaPlayerHTH> notPlaying = new ArrayList<>();
    private List<HTHResponseDetails> mMatchDetailList = new ArrayList<>();
    private int same = 0;
    private int oppent = 0;
    private List<Fragment> mFragmentList;
    private int mFrom;
    private HTHResponseDetails mMatchDetail;
    private BattlesDeatilsHTH mBattleList;
    private ICreateBattlePresenter mPresenter;
    private int mCurrentPage;
    private String mBattleID;
    List<KaPlayerHTH> mPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverLiveMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_create_team;
    }

    @Override
    protected void initViews() {
        mFrom = getIntent().getIntExtra(AppConstant.FROM, 0);
        mMatchDetailList = getIntent().getParcelableArrayListExtra(AppConstant.MATCH_DATA);
        if (mMatchDetailList != null && !mMatchDetailList.isEmpty()) {
            mMatchDetail = mMatchDetailList.get(0);
            mBattleID = getIntent().getStringExtra(AppConstant.ID);
            TeamHTH homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeam();
            TeamHTH awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeam();
            mTeamOneTV.setText(homeTeam.getName());
            Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.mipmap.ic_launcher).into(mTeamOneIV);
            mTeamTwoTV.setText(awayTeam.getName());
            Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.mipmap.ic_launcher).into(mTeamTwoIV);
        }
        if (getIntent().getParcelableExtra(AppConstant.BATTLE_DATA) != null) {
            mBattleList = getIntent().getParcelableExtra(AppConstant.BATTLE_DATA);
        }
        if (getIntent().getStringExtra(AppConstant.FROM_PLAYER) != null) {
            mFromPLayer = getIntent().getStringExtra(AppConstant.FROM_PLAYER);
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new CreateBattlePresenter(this);
        mFragmentList = new ArrayList<>();
        setAllPlayer(mMatchDetail);
        onData();
    }

    private void onData() {
        mFragmentList.add(ReviewFragment.getInstance());
        HeadToHeadFragmentPagerAdapter mAdapter = new HeadToHeadFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);
        int mSelected = 0;
        viewPager.setCurrentItem(mSelected);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        mCurrentPage = 0;
        mCancelBTN.setOnClickListener(this);
        mNextRL.setOnClickListener(this);
        mBackIV.setOnClickListener(this);
        images();
    }

    private void setAllPlayer(HTHResponseDetails mMatchDetail) {
        for (int i = 0; i < mMatchDetail.getKaPlayers().size(); i++) {
            if (mMatchDetail.getKaPlayers().get(i).getPlaying() == 1) {
                playing.add(mMatchDetail.getKaPlayers().get(i));
            } else {
                notPlaying.add(mMatchDetail.getKaPlayers().get(i));
            }
        }
        if (notPlaying != null && !notPlaying.isEmpty()) {
            playing.addAll(notPlaying);
            mMatchDetail.setKaPlayers(playing);
        } else {
            mMatchDetail.setKaPlayers(playing);
        }
        if (mBattleList != null) {
            if (mFrom == 3) {
                opponentTeam();
            } else if (mFrom == 2) {
                if (mFromPLayer.equalsIgnoreCase(AppConstant.Captain)) {
                    CapatainTeam();
                } else if (mFromPLayer.equalsIgnoreCase(AppConstant.Opponent)) {
                    opponentTeam();
                }
            }
        } else {
            createTeam();
        }
    }

    private void createTeam() {
        mFragmentList.add(PlayerFragment.getInstanceCreateBattle(mMatchDetail.getKaPlayers(), AppConstant.PLAYER_BATSMAN));
        mFragmentList.add(PlayerFragment.getInstanceCreateBattle(mMatchDetail.getKaPlayers(), AppConstant.PLAYER_BOWLER));
        mFragmentList.add(PlayerFragment.getInstanceCreateBattle(mMatchDetail.getKaPlayers(), AppConstant.PLAYER_ALL_ROUNDER));
        mFragmentList.add(PlayerFragment.getInstanceCreateBattle(mMatchDetail.getKaPlayers(), AppConstant.PLAYER_WICKET_KEEPER));
    }

    private void opponentTeam() {
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getCaptainTeam(), AppConstant.PLAYER_BATSMAN));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getCaptainTeam(), AppConstant.PLAYER_BOWLER));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getCaptainTeam(), AppConstant.PLAYER_ALL_ROUNDER));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getCaptainTeam(), AppConstant.PLAYER_WICKET_KEEPER));
    }

    private void CapatainTeam() {
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getOpponentTeam(), AppConstant.PLAYER_BATSMAN));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getOpponentTeam(), AppConstant.PLAYER_BOWLER));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getOpponentTeam(), AppConstant.PLAYER_ALL_ROUNDER));
        mFragmentList.add(PlayerFragment.getInstance(mMatchDetail.getKaPlayers(), mBattleList.getOpponentTeam(), AppConstant.PLAYER_WICKET_KEEPER));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.rl_next:
                if (mCurrentPage == 4) {
                    try {
                        mPlayerList = ((ReviewFragment) mFragmentList.get(4)).getSelectedPlayers();
                        if (mPlayerList.size() == 4 && mFrom == 1) {
                            if (!playerIdList.isEmpty()) {
                                playerIdList.clear();
                            }
                            createBattleN(mPlayerList);
                            // showMsgSmallDialog(getString(R.string.text_combo_created_success), 1);
                            //  mNextRL.setClickable(false);
                        } else if (mPlayerList.size() == 4 && mFrom == 3) {
                            createBattle(mBattleList.getInvestedAmount(), mPlayerList);
                            // mNextRL.setClickable(false);
                        } else if (mPlayerList.size() == 4 && mFrom == 2) {
                            createBattle(mBattleList.getInvestedAmount(), mPlayerList);
                            //mNextRL.setClickable(false);
                        } else {
                            AppUtilityMethods.showMsg(this, "Please select players  from each category to create battle", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    viewPager.setCurrentItem(mCurrentPage + 1);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        images();
        if (mCurrentPage == 4) {
            mNextBTN.setText(R.string.text_finish);
            setPlayersForReview();
            setPlayers();
        } else if (mCurrentPage == 1 || mCurrentPage == 2 || mCurrentPage == 3) {
            mCancelBTN.setText(R.string.previous);
            mNextBTN.setText(R.string.text_next);
            playerIdList.clear();
            setPlayers();
        } else {
            mCancelBTN.setText(R.string.back);
            playerIdList.clear();
            setPlayers();
            mNextBTN.setText(R.string.text_next);
        }
    }

    private void setPlayersForReview() {
        List<KaPlayerHTH> reviewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PlayerFragment fragment = (PlayerFragment) mFragmentList.get(i);
            KaPlayerHTH player = fragment.getSelectedPlayer();
            if (player != null) {
                reviewList.add(player);
                if (reviewList.size() == 4) {
                    mALLSelectedIV.setImageResource(R.drawable.ic_ready_circle);
                }
            }
        }
        ((ReviewFragment) mFragmentList.get(4)).updatePlayers(reviewList);
    }

    private void setPlayers() {
        for (int i = 0; i < 4; i++) {
            PlayerFragment fragment = (PlayerFragment) mFragmentList.get(i);
            KaPlayerHTH player = fragment.getSelectedPlayer();
            if (player != null) {
                if (player.isSelected()) {
                    if (player.getRole() == AppConstant.PLAYER_BATSMAN) {
                        mBatmanSelectedIV.setImageResource(R.drawable.ic_tick_selected);
                    } else if (player.getRole() == AppConstant.PLAYER_BOWLER) {
                        mBowlSelectedIV.setImageResource(R.drawable.ic_tick_selected);
                    } else if (player.getRole() == AppConstant.PLAYER_ALL_ROUNDER) {
                        mArSelectedIV.setImageResource(R.drawable.ic_tick_selected);
                    } else if (player.getRole() == AppConstant.PLAYER_WICKET_KEEPER) {
                        mWkSelectedIV.setImageResource(R.drawable.ic_tick_selected);
                    }
                }
            }
        }
    }

    private void images() {
        if (mCurrentPage == 0) {
            mBatmanSelectedIV.setImageResource(R.drawable.ic_playerselected);
            mWkSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mArSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mBowlSelectedIV.setImageResource(R.drawable.ic_playerunselected);
        } else if (mCurrentPage == 1) {
            mBowlSelectedIV.setImageResource(R.drawable.ic_playerselected);
            mBatmanSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mWkSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mArSelectedIV.setImageResource(R.drawable.ic_playerunselected);
        } else if (mCurrentPage == 2) {
            mArSelectedIV.setImageResource(R.drawable.ic_playerselected);
            mBatmanSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mWkSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mBowlSelectedIV.setImageResource(R.drawable.ic_playerunselected);
        } else if (mCurrentPage == 3) {
            mWkSelectedIV.setImageResource(R.drawable.ic_playerselected);
            mBatmanSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mArSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mBowlSelectedIV.setImageResource(R.drawable.ic_playerunselected);
        } else {
            mBatmanSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mWkSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mArSelectedIV.setImageResource(R.drawable.ic_playerunselected);
            mBowlSelectedIV.setImageResource(R.drawable.ic_playerunselected);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private final IOnCreateBattleListener onCreateBattleListener = new IOnCreateBattleListener() {
        @Override
        public void onAddBattleListener(double amount, List<KaPlayerHTH> kaplayerList) {
            createBattle(amount, kaplayerList);
        }

        @Override
        public void onCancelBattleListener() {

        }
    };

    private void createBattleN(List<KaPlayerHTH> kaplayerList) {
        createBattleRequest.setBattleId("");
        createBattleRequest.setMatchId(mMatchDetail.getId());
        for (int i = 0; i < kaplayerList.size(); i++) {
            PlayerId playerId = new PlayerId();
            playerId.setPlayerId(kaplayerList.get(i).getId());
            playerId.setImg(kaplayerList.get(i).getImg());
            playerId.setName(kaplayerList.get(i).getTitle());
            playerId.setRole(kaplayerList.get(i).getRole());
            playerId.setTeamName(kaplayerList.get(i).getTeamName());
            playerIdList.add(playerId);
        }
        getPayerStatus();
    }

    private void createBattle(double amount, List<KaPlayerHTH> kaplayerList) {
        createBattleRequest.setAmount(amount);
        createBattleRequest.setBattleId("");
        createBattleRequest.setMatchId(mMatchDetail.getId());
        mAmount = amount;
        if (!playerIdList.isEmpty()) {
            playerIdList.clear();
        }
        for (int i = 0; i < kaplayerList.size(); i++) {
            PlayerId playerId = new PlayerId();
            playerId.setPlayerId(kaplayerList.get(i).getId());
            playerId.setImg(kaplayerList.get(i).getImg());
            playerId.setName(kaplayerList.get(i).getTitle());
            playerId.setRole(kaplayerList.get(i).getRole());
            playerId.setTeamName(kaplayerList.get(i).getTeamName());
            playerIdList.add(playerId);
        }
        if (mFrom == 3) {
            if (mBattleList != null && mBattleList.getCaptainTeam() != null) {
                for (int j = 0; j < kaplayerList.size(); j++) {
                    if (playerIdList.get(j).getPlayerId().contains(mBattleList.getCaptainTeam().get(j).getPlayerId())) {
                        same = same + 1;
                    }
                }
                if (same == 4) {
                    playerIdList.clear();
                    showMsgErrorSmallDialog(getString(R.string.error_battle), false, 10);
                    same = 0;
                } else {
                    if (mFrom == 3) {
                        updateOpponentPlayers.setBattleId(mBattleID);
                        getPayerStatus();
                    }
                }
            }
        }
//        1 = add, 2 = update, 3 = accept
        if (mFrom == 1) {
            createBattleRequest.setPlayers(playerIdList);
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.createBattle(createBattleRequest);
        } else if (mFrom == 2) {
            if (mFromPLayer.equalsIgnoreCase(AppConstant.Opponent)) {
                if (mBattleList != null && mBattleList.getCaptainTeam() != null && !mBattleList.getCaptainTeam().isEmpty()) {
                    for (int i = 0; i < kaplayerList.size(); i++) {
                        if (kaplayerList.get(i).getId().contains(mBattleList.getCaptainTeam().get(i).getPlayerId())) {
                            oppent = oppent + 1;
                        }
                    }
                    if (oppent == 4) {
                        playerIdList.clear();
                        showMsgErrorSmallDialog(getString(R.string.error_battle), false, 10);
                        oppent = 0;
                    } else {
                        if (mFrom == 2) {
                            getPayerStatus();
                        }
                    }
                } else if (mFrom == 2) {
                    getPayerStatus();
                }
            } else if (mFromPLayer.equalsIgnoreCase(AppConstant.Captain)) {
                if (mBattleList != null && mBattleList.getOpponentTeam() != null && !mBattleList.getOpponentTeam().isEmpty()) {
                    for (int i = 0; i < kaplayerList.size(); i++) {
                        if (kaplayerList.get(i).getId().contains(mBattleList.getOpponentTeam().get(i).getPlayerId())) {
                            oppent = oppent + 1;
                        }
                    }
                    if (oppent == 4) {
                        playerIdList.clear();
                        showMsgErrorSmallDialog(getString(R.string.error_battle), false, 10);
                        oppent = 0;
                    } else {
                        if (mFrom == 2) {
                            getPayerStatus();
                        }
                    }
                } else if (mFrom == 2) {
                    getPayerStatus();
                }
            }
        }
    }

    @Override
    public void onJoinComplete(CreateBattleResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            showMsgBigDialog(responseModel.getMessage());
            mAppPreference.setProfileData(responseModel.getProfile());
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, "ClashX Create");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onJoinFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), true);
    }

    @Override
    public void onUpdateComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            showMsgBigDialog(responseModel.getMessage());
            mFrom = 0;
        } else {
            if (responseModel.getType() == 10) {
                showMsgErrorSmallDialog(getString(R.string.error_battle), false, 10);
            } else {
                AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
            }
        }
    }

    @Override
    public void onUpdateFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), true);
    }

    @Override
    public void onAcceptComplete(CreateBattleResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            showMsgBigDialog(responseModel.getMessage());
            mFrom = 0;
            mAppPreference.setProfileData(responseModel.getProfile());
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onAcceptFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showCreateBattleMsg(this, error.getMessage(), 1);
        mFrom = 0;
    }

    @Override
    public void onAllBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onAllBattlesFailure(ApiError error) {

    }

    @Override
    public void onMyBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onMyBattlesFailure(ApiError error) {

    }

    @Override
    public void onFetchLegues(HTHMainResponse responseModel) {

    }


    @Override
    public void onFetchLegues(ApiError error) {

    }

    @Override
    public void onUpdatePLayers(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsgBigDialog(response.getMessage());
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), true);
        }
    }

    @Override
    public void onUpdatePLayerError(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPlayerStatus(HTHMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            showMsgSmallDialog(responseModel.getMessage(), mFrom);
        } else {
            if (responseModel.getType() == 2) {
                showMsgErrorSmallDialog(responseModel.getMessage(), false, 0);
                mMatchDetailList.clear();
                mMatchDetailList.addAll(responseModel.getResponse());
                mFragmentList.clear();
                ClearAllist();
                setAllPlayer(mMatchDetailList.get(0));
                onData();
            } else if (responseModel.getType() == 5) {
                showMsgErrorSmallDialog(getString(R.string.cancelled_match), false, 5);
            } else {
                showMsgErrorSmallDialog(responseModel.getMessage(), true, 0);
            }
        }
    }

    @Override
    public void onPlayerStatusError(ApiError error) {
        hideProgress();
    }

    public void showMsgSmallDialog(String msg, int mFrom) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.combo);
        TextView text = dialog.findViewById(R.id.tv_text);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            mNextRL.setClickable(true);
            if (mFrom == 1) {
                dialog.dismiss();
                new CreateBattleDialog(this, onCreateBattleListener, mPlayerList);
            } else if (mFrom == 3) {
                mPresenter.updateOpponentPlayer(mBattleID, updateOpponentPlayers);
                dialog.dismiss();
            } else if (mFrom == 2) {
                showProgress(getString(R.string.txt_progress_authentication));
                createBattleRequest.setPlayers(playerIdList);
                mPresenter.updateBattleGroup(createBattleRequest, getIntent().getStringExtra(AppConstant.GROUP_JOINED));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showMsgBigDialog(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.combobig);
        TextView text = dialog.findViewById(R.id.tv_text);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    public void showMsgErrorSmallDialog(String msg, boolean live, int type) {
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
                same = 0;
                if (mFrom == 1) {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else if (mFrom == 2) {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else if (mFrom == 3) {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                finish();
            } else {
                if (type == 10) {
                    dialog.dismiss();
                    finish();
                } else if (type == 5) {
                    dialog.dismiss();
                    startActivity(new Intent(this, HTHBattlesActivity.class));
                } else {
                    mNextRL.setClickable(true);
                    viewPager.setCurrentItem(0);
                    dialog.dismiss();
                    same = 0;
                }
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPage == 5) {
            viewPager.setCurrentItem(4);
        } else if (mCurrentPage == 4) {
            viewPager.setCurrentItem(3);
        } else if (mCurrentPage == 3) {
            viewPager.setCurrentItem(2);
        } else if (mCurrentPage == 2) {
            viewPager.setCurrentItem(1);
        } else if (mCurrentPage == 1) {
            viewPager.setCurrentItem(0);
        } else if (mCurrentPage == 0) {
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverLiveMatches);
        mPresenter.destroy();
        super.onDestroy();
    }

    private void ClearAllist() {
        playing.clear();
        notPlaying.clear();
        mPlayerList.clear();
        playerIdList.clear();
        mALLSelectedIV.setImageResource(R.drawable.ic_notselectedcircle);
    }

    private void getPayerStatus() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            updateOpponentPlayers.setPlayers(playerIdList);
            mPresenter.getPlayerStatus(mMatchDetail.getId(), updateOpponentPlayers);
            mNextRL.setClickable(false);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private final BroadcastReceiver mHTHNotificationReceiverLiveMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHLIVEREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    if (mFrom == 2) {
                        showMsgErrorSmallDialog("MATCH IS LIVE", true, 0);
                    } else if (mFrom == 3) {
                        showMsgErrorSmallDialog("MATCH IS  LIVE", true, 0);
                    }
                }
            }
        }
    };

}