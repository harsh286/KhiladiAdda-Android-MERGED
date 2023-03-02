package com.khiladiadda.gameleague;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.LeaderBoardAdapter;
import com.khiladiadda.gameleague.interfaces.ILeaderBoardDroidoPresenter;
import com.khiladiadda.gameleague.interfaces.ILeaderBoardDroidoView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.LeaderBoardDroidoResponse;
import com.khiladiadda.network.model.response.droid_doresponse.LeaderBoardPresenter;
import com.khiladiadda.network.model.response.droid_doresponse.MyRankDroidoLeaderboard;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseLeaderBoardSub;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;

import butterknife.BindView;

public class GamesLeaderBoardActivity extends BaseActivity implements ILeaderBoardDroidoView {
    @BindView(R.id.tv_user_rank)
    TextView tvUserRank;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_time_taken)
    TextView tvtimeTaken;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.iv_title_name)
    TextView toolbarTitle;
    @BindView(R.id.iv_back_arroww)
    ImageView mIvBack;
    @BindView(R.id.rv_leaderboard)
    RecyclerView rvLaederBoardDroido;
    @BindView(R.id.tv_live_leaderboard)
    TextView tvLiveLeaderboard;
    @BindView(R.id.rl_live_leaderboard)
    RelativeLayout rlLiveLeaderboardHints;
    @BindView(R.id.tv_rules_droido)
    TextView tvRules;
    @BindView(R.id.mcv_rules)
    MaterialCardView mcvRules;
    private LeaderBoardAdapter mLeaderBoardAdapter;
    private ArrayList<ResponseLeaderBoardSub> mLeaderBoardList = new ArrayList<>();
    private ILeaderBoardDroidoPresenter mPresenter;
    private String tournamentid;
    private Integer tournamentStatus;
    private MyRankDroidoLeaderboard mMyRankDroidoLeaderboard;

    @Override
    protected int getContentView() {
        return R.layout.activity_games_leader_board;
    }

    @Override
    protected void initViews() {
//        toolbarTitle.setText(R.string.text_leaderboard_live);
        toolbarTitle.setText(R.string.text_leaderboard);

        mIvBack.setOnClickListener(this);
        tvRules.setOnClickListener(this);
        tvRules.setOnClickListener(this);
//        toolbarTitle.setText(getString(R.string.live_leaderboard));

        toolbarTitle.setText(R.string.text_leaderboard);

        tvRules.setVisibility(View.VISIBLE);
        mcvRules.setVisibility(View.VISIBLE);
        tvRules.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initVariables() {
        mPresenter = new LeaderBoardPresenter(this);
        tournamentid = getIntent().getStringExtra("tournament_id");
        tournamentStatus = getIntent().getIntExtra("tournamentStatus", 0);
        if (getIntent().getParcelableExtra(AppConstant.DROIDO_MY_RANK) == null)
            getLeaderBoardList();
        if (getIntent().getParcelableExtra(AppConstant.DROIDO_MY_RANK) != null)
            mMyRankDroidoLeaderboard = getIntent().getParcelableExtra(AppConstant.DROIDO_MY_RANK);
        rvLaederBoardDroido.setBackgroundResource(R.drawable.ic_games_final_droid);
        mLeaderBoardList = new ArrayList<>();
        if (getIntent().getParcelableArrayListExtra("leaderBoardData") != null)
            mLeaderBoardList.addAll(getIntent().getParcelableArrayListExtra("leaderBoardData"));
        if (mLeaderBoardList != null)
            mLeaderBoardAdapter = new LeaderBoardAdapter(this, mLeaderBoardList);
        if (mMyRankDroidoLeaderboard != null) {
            tvtimeTaken.setText(setTimeWithSecond(mMyRankDroidoLeaderboard.getTimeTaken() * 1000));
            tvScore.setText("" + mMyRankDroidoLeaderboard.getScore());
            tvUserRank.setText("#" + mMyRankDroidoLeaderboard.getRank());
        }
        rvLaederBoardDroido.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLaederBoardDroido.setAdapter(mLeaderBoardAdapter);
        if (tournamentStatus == 1) {
            toolbarTitle.setText(R.string.text_leaderboard);

        } else {
            toolbarTitle.setText(R.string.text_leaderboard);

//            toolbarTitle.setText(R.string.text_leaderboard_live);
        }
    }


    private String setTimeWithSecond(Integer timeTaken) {
        return String.format("%02d : %02d", timeTaken / (60 * 1000), (timeTaken / 1000) % 60);
    }

    private void getLeaderBoardList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getLeaderBoardData(tournamentid);
        } else {
            Snackbar.make(rvLaederBoardDroido, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arroww:
                Intent intent = new Intent(this, NewDroidoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_rules_droido:
                startActivity(new Intent(this, RulesActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NewDroidoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onParticipantsSuccess(LeaderBoardDroidoResponse response) {
        if (!response.getResponseLeaderBoard().getLeaderboard().isEmpty()) {
            hideProgress();
            mLeaderBoardList.clear();
            mMyRankDroidoLeaderboard = response.getResponseLeaderBoard().getMyRank();
            tvtimeTaken.setText(setTimeWithSecond(mMyRankDroidoLeaderboard.getTimeTaken() * 1000));
            setTimeWithSecond(mMyRankDroidoLeaderboard.getTimeTaken() * 1000);
            tvScore.setText("" + mMyRankDroidoLeaderboard.getScore());
            tvUserRank.setText("#" + mMyRankDroidoLeaderboard.getRank());
            mLeaderBoardList.addAll(response.getResponseLeaderBoard().getLeaderboard());
            mLeaderBoardAdapter.notifyDataSetChanged();
            if (mLeaderBoardList.get(0).getmTournamentStatus() == 1) {
                rlLiveLeaderboardHints.setVisibility(View.GONE);
            } else {
                rlLiveLeaderboardHints.setVisibility(View.VISIBLE);
            }
        } else {
            hideProgress();
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onParticipantsFailure(ApiError error) {
        hideProgress();
    }
}