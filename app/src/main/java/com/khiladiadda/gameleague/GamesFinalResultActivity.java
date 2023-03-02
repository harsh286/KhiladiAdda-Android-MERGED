package com.khiladiadda.gameleague;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class GamesFinalResultActivity extends BaseActivity implements ILeaderBoardDroidoView {
    @BindView(R.id.iv_title_name)
    TextView tvTitle;
    @BindView(R.id.cl_mainscreen_bg_gradient)
    ConstraintLayout constraintLayoutBgGradient;
    @BindView(R.id.tv_games_rank)
    TextView tvRank;
    @BindView(R.id.tv_highscore)
    TextView tvHighScore;
    @BindView(R.id.tv_games_score)
    TextView tvCurrentScore;
    @BindView(R.id.tv_games_timetaken)
    TextView tvTimeTaken;
    @BindView(R.id.mcv_title)
    MaterialCardView mTitleMcv;
    private ILeaderBoardDroidoPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_games_final_result;
    }

    @Override
    protected void initViews() {
        mPresenter = new LeaderBoardPresenter(this);
        String tournamentId = getIntent().getStringExtra("tournamentid");
        mTitleMcv.setVisibility(View.GONE);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mPresenter.getLeaderBoardData(tournamentId);
        } else {
            Snackbar.make(constraintLayoutBgGradient, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initVariables() {
        setBackground();
    }

    private void setBackground() {
        constraintLayoutBgGradient.setBackgroundResource(R.drawable.ic_games_droid_bg_top);
    }

    private void goToLeaderBoard(ArrayList<ResponseLeaderBoardSub> leaderBoardSubList, MyRankDroidoLeaderboard myRankDroidoLeaderboard) {
        Intent leaderBoardIntent = new Intent(this, GamesLeaderBoardActivity.class);
        leaderBoardIntent.putParcelableArrayListExtra("leaderBoardData", leaderBoardSubList);
        leaderBoardIntent.putExtra(AppConstant.FROM, true); // true for splash and false for history
        leaderBoardIntent.putExtra(AppConstant.DROIDO_MY_RANK, myRankDroidoLeaderboard);
        leaderBoardIntent.putExtra("tournament_id", myRankDroidoLeaderboard.getTournamentId());
        finish();
        startActivity(leaderBoardIntent);
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
        hideProgress();
        ArrayList<ResponseLeaderBoardSub> leaderboard = response.getResponseLeaderBoard().getLeaderboard();
        MyRankDroidoLeaderboard myRankDroidoLeaderboard = response.getResponseLeaderBoard().getMyRank();
        ResponseLeaderBoardSub myLeaderboard = null;
        for (ResponseLeaderBoardSub leaderBoardSub : leaderboard) {
            if (leaderBoardSub.getUserId().equals(getIntent().getStringExtra("id"))) {
                myLeaderboard = leaderBoardSub;
                break;
            }
        }
        if (myLeaderboard != null) {
            tvHighScore.setText(String.valueOf(response.getResponseLeaderBoard().getMyRank().getScore()));
            tvRank.setText(String.valueOf(response.getResponseLeaderBoard().getMyRank().getRank()));
            tvCurrentScore.setText(String.valueOf(response.getResponseLeaderBoard().getMyRank().getCurrentScore()));
            tvTimeTaken.setText(setTimeWithSecond(response.getResponseLeaderBoard().getMyRank().getTimeTaken() * 1000));
            tvTitle.setText(response.getResponseLeaderBoard().getMyRank().getGameName());
        }
        {

        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> goToLeaderBoard(leaderboard, myRankDroidoLeaderboard), 2000);
    }

    private String setTimeWithSecond(int timeTaken) {
        return String.format("%d.%02ds", TimeUnit.MILLISECONDS.toSeconds(timeTaken), timeTaken - (TimeUnit.MILLISECONDS.toSeconds(timeTaken) * 1000));
    }

    @Override
    public void onParticipantsFailure(ApiError error) {
        hideProgress();


    }
}