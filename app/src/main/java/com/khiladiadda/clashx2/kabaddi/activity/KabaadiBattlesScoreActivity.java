package com.khiladiadda.clashx2.kabaddi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.activity.KabaadiPlayerStatus;
import com.khiladiadda.clashx2.main.activity.MyFanLeagueActivityHTH;
import com.khiladiadda.clashx2.main.activity.PlayerStatus;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;
import com.khiladiadda.network.model.response.hth.CaptainResultHTH;
import com.khiladiadda.network.model.response.hth.CaptainTeamHTH;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.network.model.response.hth.ResultList;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

public class KabaadiBattlesScoreActivity extends BaseActivity implements IHTHBattleView, SwipeRefreshLayout.OnRefreshListener {

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
    ImageView mTPlayerOneEditIV;
    @BindView(R.id.iv_player2edit)
    ImageView mTPlayerTwoEditIV;
    @BindView(R.id.iv_player3edit)
    ImageView mTPlayerThreeEditIV;
    @BindView(R.id.iv_player4edit)
    ImageView mTPlayerFourEditIV;
    @BindView(R.id.iv_player5edit)
    ImageView mTPlayerFiveEditIV;
    @BindView(R.id.iv_player6edit)
    ImageView mTPlayerSixEditIV;

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

//    @BindView(R.id.tv_player4edit)
//    TextView mTPlayerFourEditTV;

    @BindView(R.id.iv_player1)
    ImageView mTPlayerOneIV;
    @BindView(R.id.iv_player2)
    ImageView mTPlayerTwoIV;
    @BindView(R.id.iv_player3)
    ImageView mTPlayerThreeIV;
    @BindView(R.id.iv_player4)
    ImageView mTPlayerFourIV;
    @BindView(R.id.iv_player5)
    ImageView mTPlayerFiveIV;
    @BindView(R.id.iv_player6)
    ImageView mTPlayerSixIV;
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

//    @BindView(R.id.tv_player4)
//    TextView mTPlayerFourTV;

    @BindView(R.id.tv_points_oppents)
    TextView mPointsLosingTV;
    @BindView(R.id.tv_points)
    TextView mPointsWinningTV;
    @BindView(R.id.iv_team_imagemy)
    ImageView mWinningIV;
    @BindView(R.id.iv_team_imageopp)
    ImageView mLosingIV;
    @BindView(R.id.gif_image)
    GifImageView mGIFIV;
    @BindView(R.id.tv_status)
    TextView mStatus;
    @BindView(R.id.tv_text_name)
    TextView mNameTV;
    @BindView(R.id.tv_text_nameopp)
    TextView mOppNameTV;
    @BindView(R.id.oppcard)
    CardView mOppentCV;
    @BindView(R.id.cardwin)
    CardView mWinningCV;
    @BindView(R.id.tv_battleid)
    TextView mContestId;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshL;
    @BindView(R.id.tv_substitute_msg)
    TextView mSubstituteTV;

    private HTHResponseDetails mMatchDetail;
    private BattlesDeatilsHTH mBattleList;
    private List<ResultList> mResultList = new ArrayList<>();
    private IHTHBattlePresenter mPresenter;
    private boolean mIsCaptain;
    private boolean mYouWin;
    private int mMatch_Type;

    @Override
    protected int getContentView() {
        return R.layout.kabaadi_battles_score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverPastMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_PAST_NOTIFY"));
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mSwipeRefreshL.setOnRefreshListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new HTHPresenter(this);
        mMatchDetail = getIntent().getParcelableExtra(AppConstant.DATA);
        mBattleList = getIntent().getParcelableExtra(AppConstant.BATTLE_DATA);
        mMatch_Type = getIntent().getIntExtra(AppConstant.MATCH_TYPE, 0);
        mContestId.setText("Battle ID : " + mBattleList.getContestId());
        if (mBattleList.getBattle_status() == AppConstant.COMPLETED) {
            mStatus.setText("Completed");
            mGIFIV.setImageResource(R.drawable.ic_ready_circle);
        } else if (mBattleList.getBattle_status() == AppConstant.LIVEMATCH) {
            mStatus.setText("LIVE");
            mGIFIV.setImageResource(R.drawable.live_gif);
        } else if (mBattleList.getBattle_status() == AppConstant.REVIEWPending) {
            mStatus.setText("Pending");
            mGIFIV.setImageResource(R.drawable.ic_alert_circle);
        } else {
            mStatus.setText("Draw");
            mGIFIV.setVisibility(View.GONE);
        }

        TeamHTH homeTeam = mMatchDetail.getPlayers().getHomeTeam().getTeam();
        TeamHTH awayTeam = mMatchDetail.getPlayers().getAwayTeam().getTeam();
        mTeamOneTV.setText(homeTeam.getName());
        Glide.with(mTeamOneIV).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamOneIV);
        mTeamTwoTV.setText(awayTeam.getName());
        Glide.with(mTeamTwoIV).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(mTeamTwoIV);
        substituteData(mIsCaptain, mBattleList);
        getData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    private void getData() {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getBattleResult(mBattleList.getId());
    }

    @Override
    public void onGetHTHMatchListComplete(HTHMainResponse responseModel) {

    }

    @Override
    public void onGetHTHMatchListFailure(ApiError error) {

    }

    @Override
    public void onCancelBattle(HTHCancelResponse responseModel) {

    }

    @Override
    public void onCancelBattleFailure(ApiError error) {

    }

    @Override
    public void onGetResultBattle(Result responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mResultList.clear();
            mResultList.add(responseModel.getResponse());
            mSwipeRefreshL.setRefreshing(false);
            playerdata();
        }
    }

    @Override
    public void onResultBattleFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onMatchStatus(HTHMainResponse responseModel) {

    }

    @Override
    public void onMatchStatusError(ApiError error) {

    }

    @Override
    public void onRefresh() {
        getData();
    }

    private BroadcastReceiver mHTHNotificationReceiverPastMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHPASTREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    Intent i = new Intent(KabaadiBattlesScoreActivity.this, MyFanLeagueActivityHTH.class);
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
                    startActivity(i);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverPastMatches);
        mPresenter.destroy();
        super.onDestroy();
    }

    private void playerdata() {
        List<CaptainTeamHTH> mBattleCaptainDetails = mBattleList.getCaptainTeam();
        List<CaptainTeamHTH> mBattleOpponentDetails = mBattleList.getOpponentTeam();
        if (mBattleList.getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
            mIsCaptain = true;
            if (mResultList.get(0).getCaptainPoints() >= mResultList.get(0).getOpponentPoints()) {
                mYouWin = true;
            } else
                mYouWin = false;
        } else {
            if (mResultList.get(0).getCaptainPoints() > mResultList.get(0).getOpponentPoints()) {
                mYouWin = false;
            } else
                mYouWin = true;
        }
        if (mResultList.get(0).getOpponentPoints() > mResultList.get(0).getCaptainPoints()) {
            if (mIsCaptain) {
                mNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
                mOppNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
            } else {
                mNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
                mOppNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
            }
            setCaptionOpponentClick();
            mPointsWinningTV.setText("Total Points " + mResultList.get(0).getOpponentPoints());
            Glide.with(this).load(mBattleList.getOpponent().getDp()).placeholder(R.drawable.splash_logo).into(mWinningIV);
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(0).getImg()) && mBattleOpponentDetails.get(0).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 1, mTPlayerOneEditIV, mTPlayerOneEditTV);
            } else {
                Glide.with(this).clear(mTPlayerOneEditIV);
                mTPlayerOneEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(1).getImg()) && mBattleOpponentDetails.get(1).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 2, mTPlayerFourEditIV, mTPlayerOneTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(2).getImg()) && mBattleOpponentDetails.get(2).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 3, mTPlayerTwoEditIV, mTPlayerTwoEditTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoEditIV);
                mTPlayerTwoEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(3).getImg()) && mBattleOpponentDetails.get(3).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 4, mTPlayerFiveEditIV, mTPlayerFourEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFiveEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(4).getImg()) && mBattleOpponentDetails.get(4).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 5, mTPlayerThreeEditIV, mTPlayerFiveEditTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeEditIV);
                mTPlayerThreeEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(5).getImg()) && mBattleOpponentDetails.get(5).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }

////////////////////////////////////////////////////////////OPPonentTeam////////////////////////////////////////////////////////////////////////////////////////////
            mPointsLosingTV.setText("Total Points " + mResultList.get(0).getCaptainPoints());
            Glide.with(this).load(mBattleList.getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mLosingIV);
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(0).getImg()) && mBattleCaptainDetails.get(0).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
            } else {
                Glide.with(mTPlayerOneIV.getContext()).clear(mTPlayerOneIV);
                mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(1).getImg()) && mBattleCaptainDetails.get(1).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 2, mTPlayerFourIV, mTPlayerThreeEditTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(2).getImg()) && mBattleCaptainDetails.get(2).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 3, mTPlayerTwoIV, mTPlayerTwoTV);
            } else {
                Glide.with(mTPlayerTwoIV.getContext()).clear(mTPlayerTwoIV);
                mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(3).getImg()) && mBattleCaptainDetails.get(3).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 4, mTPlayerFiveIV, mTPlayerFourEditTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(4).getImg()) && mBattleCaptainDetails.get(4).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 5, mTPlayerThreeIV, mTPlayerThreeTV);
            } else {
                Glide.with(mTPlayerThreeIV.getContext()).clear(mTPlayerThreeIV);
                mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(5).getImg()) && mBattleCaptainDetails.get(5).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 6, mTPlayerSixIV, mTPlayerThreeEditTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
//            substituteData(mIsCaptain, mBattleList);
        } else if (mResultList.get(0).getCaptainPoints() > mResultList.get(0).getOpponentPoints()) {
            if (mIsCaptain) {
                mNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
                mOppNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
            } else {
                mNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
                mOppNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
            }
            mPointsWinningTV.setText("Total Points " + mResultList.get(0).getCaptainPoints());
            Glide.with(this).load(mBattleList.getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mWinningIV);
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
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleCaptainDetails.get(5).getImg()) && mBattleCaptainDetails.get(5).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }


////////////////////////////////////////////////////////////OPPONENTTeam////////////////////////////////////////////////////////////////////////////////////////////
            mPointsLosingTV.setText("Total Points " + mResultList.get(0).getOpponentPoints());
            Glide.with(this).load(mBattleList.getOpponent().getDp()).placeholder(R.drawable.splash_logo).into(mLosingIV);
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(0).getImg()) && mBattleOpponentDetails.get(0).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
            } else {
                Glide.with(this).clear(mTPlayerOneIV);
                mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(1).getImg()) && mBattleOpponentDetails.get(1).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 2, mTPlayerTwoIV, mTPlayerTwoTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoIV);
                mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(2).getImg()) && mBattleOpponentDetails.get(2).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 3, mTPlayerThreeIV, mTPlayerThreeTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeIV);
                mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(3).getImg()) && mBattleOpponentDetails.get(3).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 4, mTPlayerFourIV, mTPlayerFourTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(4).getImg()) && mBattleOpponentDetails.get(4).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 5, mTPlayerFiveIV, mTPlayerFiveTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleOpponentDetails.get(5).getImg()) && mBattleOpponentDetails.get(5).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 6, mTPlayerSixIV, mTPlayerSixTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

        } else if (mResultList.get(0).getCaptainPoints() == mResultList.get(0).getOpponentPoints()) {
            playerdatawithoutpoints();
        }
    }

    private void oppentpoints(List<CaptainResultHTH> list) {
        Intent intent = new Intent(this, KabaadiPlayerStatus.class);
        intent.putParcelableArrayListExtra(AppConstant.DATA, (ArrayList<? extends Parcelable>) list);
        startActivity(intent);
    }

    private void points(List<CaptainResultHTH> list) {
        Intent intent = new Intent(this, KabaadiPlayerStatus.class);
        intent.putParcelableArrayListExtra(AppConstant.DATA, (ArrayList<? extends Parcelable>) list);
        startActivity(intent);
    }

    private void setCaptionOpponentClick() {
        if (mIsCaptain) {
            if (mYouWin) {
                mPointsWinningTV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
                mWinningCV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
                mPointsLosingTV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
                mOppentCV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
            } else {
                mPointsWinningTV.setOnClickListener(v -> points(mResultList.get(0).getOpponent()));
                mWinningCV.setOnClickListener(v -> points(mResultList.get(0).getOpponent()));
                mPointsLosingTV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getCaptain()));
                mOppentCV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getCaptain()));
            }
        } else {
            if (mYouWin) {
                mPointsWinningTV.setOnClickListener(v -> points(mResultList.get(0).getOpponent()));
                mWinningCV.setOnClickListener(v -> points(mResultList.get(0).getOpponent()));
                mPointsLosingTV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getCaptain()));
                mOppentCV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getCaptain()));
            } else {
                mPointsWinningTV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
                mWinningCV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
                mPointsLosingTV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
                mOppentCV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
            }
//
//            mPointsWinningTV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
//            mWinningCV.setOnClickListener(v -> points(mResultList.get(0).getCaptain()));
//            mPointsLosingTV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
//            mOppentCV.setOnClickListener(v -> oppentpoints(mResultList.get(0).getOpponent()));
        }
    }

    private void playerdatawithoutpoints() {
        List<CaptainTeamHTH> mBattleCaptainDetails = mBattleList.getCaptainTeam();
        List<CaptainTeamHTH> mBattleOpponentDetails = mBattleList.getOpponentTeam();
        setCaptionOpponentClick();
        if (!mIsCaptain) {
            mNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
            mOppNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
            mPointsWinningTV.setText("Total Points " + mResultList.get(0).getOpponentPoints());
            Glide.with(this).load(mBattleList.getOpponent().getDp()).placeholder(R.drawable.splash_logo).into(mWinningIV);
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(0).getImg()) && mBattleList.getOpponentTeam().get(0).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 1, mTPlayerOneEditIV, mTPlayerOneEditTV);
            } else {
                Glide.with(this).clear(mTPlayerOneEditIV);
                mTPlayerOneEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(1).getImg()) && mBattleList.getOpponentTeam().get(1).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 2, mTPlayerTwoEditIV, mTPlayerTwoEditTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoEditIV);
                mTPlayerTwoEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(2).getImg()) && mBattleList.getOpponentTeam().get(2).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 3, mTPlayerThreeEditIV, mTPlayerThreeEditTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeEditIV);
                mTPlayerThreeEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(3).getImg()) && mBattleList.getOpponentTeam().get(3).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 4, mTPlayerFourEditIV, mTPlayerFourEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(4).getImg()) && mBattleList.getOpponentTeam().get(4).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 5, mTPlayerFiveEditIV, mTPlayerFiveEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(5).getImg()) && mBattleList.getOpponentTeam().get(5).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
            } else {
                Glide.with(mTPlayerFourEditIV.getContext()).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }

////////////////////////////////////////////////////////////OPPonentTeam////////////////////////////////////////////////////////////////////////////////////////////
            mPointsLosingTV.setText("Total Points " + mResultList.get(0).getCaptainPoints());
            Glide.with(this).load(mBattleList.getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mLosingIV);
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(0).getImg()) && mBattleList.getCaptainTeam().get(0).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
            } else {
                Glide.with(this).clear(mTPlayerOneIV);
                mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(1).getImg()) && mBattleList.getCaptainTeam().get(1).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 2, mTPlayerTwoIV, mTPlayerTwoTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoIV);
                mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(2).getImg()) && mBattleList.getCaptainTeam().get(2).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 3, mTPlayerThreeIV, mTPlayerThreeTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeIV);
                mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(3).getImg()) && mBattleList.getCaptainTeam().get(3).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 4, mTPlayerFourIV, mTPlayerFourTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(4).getImg()) && mBattleList.getCaptainTeam().get(4).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 5, mTPlayerFiveIV, mTPlayerFiveTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(5).getImg()) && mBattleList.getCaptainTeam().get(5).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 6, mTPlayerSixIV, mTPlayerSixTV);
            } else {
                Glide.with(this).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

        } else {
            Glide.with(this).load(mBattleList.getCaptain().getDp()).placeholder(R.drawable.splash_logo).into(mWinningIV);
            mPointsWinningTV.setText("Total Points " + mResultList.get(0).getCaptainPoints());
            mNameTV.setText(AppConstant.TEXT_YOUR_COMBO);
            mOppNameTV.setText(AppConstant.TEXT_OPPONENT_COMBO);
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(0).getImg()) && mBattleList.getCaptainTeam().get(0).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 1, mTPlayerOneEditIV, mTPlayerOneEditTV);
            } else {
                Glide.with(this).clear(mTPlayerOneEditIV);
                mTPlayerOneEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(1).getImg()) && mBattleList.getCaptainTeam().get(1).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 2, mTPlayerTwoEditIV, mTPlayerTwoEditTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoEditIV);
                mTPlayerTwoEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(2).getImg()) && mBattleList.getCaptainTeam().get(2).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 3, mTPlayerThreeEditIV, mTPlayerThreeEditTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeEditIV);
                mTPlayerThreeEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(3).getImg()) && mBattleList.getCaptainTeam().get(3).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 4, mTPlayerFourEditIV, mTPlayerFourEditTV);
            } else {
                Glide.with(this).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(4).getImg()) && mBattleList.getCaptainTeam().get(4).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 5, mTPlayerFiveEditIV, mTPlayerFiveEditTV);
            } else {
                Glide.with(this).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getCaptainTeam().get(5).getImg()) && mBattleList.getCaptainTeam().get(5).getImg().startsWith("https")) {
                setCaptainImage(mBattleCaptainDetails, 6, mTPlayerSixEditIV, mTPlayerSixEditTV);
            } else {
                Glide.with(this).clear(mTPlayerFourEditIV);
                mTPlayerFourEditIV.setImageResource(R.drawable.splash_logo);
            }

////////////////////////////////////////////////////////////OPPONENTTeam////////////////////////////////////////////////////////////////////////////////////////////
            mPointsLosingTV.setText("Total Points " + mResultList.get(0).getOpponentPoints());
            Glide.with(this).load(mBattleList.getOpponent().getDp()).placeholder(R.drawable.splash_logo).into(mLosingIV);
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(0).getImg()) && mBattleList.getOpponentTeam().get(0).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 1, mTPlayerOneIV, mTPlayerOneTV);
            } else {
                Glide.with(this).clear(mTPlayerOneIV);
                mTPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(1).getImg()) && mBattleList.getOpponentTeam().get(1).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 2, mTPlayerTwoIV, mTPlayerTwoTV);
            } else {
                Glide.with(this).clear(mTPlayerTwoIV);
                mTPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(2).getImg()) && mBattleList.getOpponentTeam().get(2).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 3, mTPlayerThreeIV, mTPlayerThreeTV);
            } else {
                Glide.with(this).clear(mTPlayerThreeIV);
                mTPlayerThreeIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(3).getImg()) && mBattleList.getOpponentTeam().get(3).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 4, mTPlayerFourIV, mTPlayerFourTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(4).getImg()) && mBattleList.getOpponentTeam().get(4).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 5, mTPlayerFiveIV, mTPlayerFiveTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }
            if (!TextUtils.isEmpty(mBattleList.getOpponentTeam().get(5).getImg()) && mBattleList.getOpponentTeam().get(5).getImg().startsWith("https")) {
                setOpponentImage(mBattleOpponentDetails, 6, mTPlayerSixIV, mTPlayerSixTV);
            } else {
                Glide.with(mTPlayerFourIV.getContext()).clear(mTPlayerFourIV);
                mTPlayerFourIV.setImageResource(R.drawable.splash_logo);
            }

        }
    }

    private void setCaptainImage(List<CaptainTeamHTH> mBattleDetails, int i, ImageView mTPlayerIV, TextView mTPlayerTV) {
        Glide.with(this).load(mBattleDetails.get(i - 1).getImg()).placeholder(R.drawable.profile).into(mTPlayerIV);
        mTPlayerTV.setText(mBattleDetails.get(i - 1).getTitle());
    }

    private void setOpponentImage(List<CaptainTeamHTH> mBattleDetails, int i, ImageView mTPlayerIV, TextView mTPlayerTV) {
        Glide.with(this).load(mBattleDetails.get(i - 1).getImg()).placeholder(R.drawable.profile).into(mTPlayerIV);
        mTPlayerTV.setText(mBattleDetails.get(i - 1).getTitle());
    }

    private void substituteData(boolean isCaptain, BattlesDeatilsHTH mBattleList) {
        List<CaptainTeamHTH> battleDetails;
        if (isCaptain) {
            battleDetails = mBattleList.getCaptainTeam();
        } else {
            battleDetails = mBattleList.getOpponentTeam();
        }
        try {
            if (battleDetails.get(0).isSubstitute() || battleDetails.get(1).isSubstitute() || battleDetails.get(2).isSubstitute() || battleDetails.get(3).isSubstitute()) {
                mSubstituteTV.setVisibility(View.VISIBLE);
            } else {
                mSubstituteTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }

}