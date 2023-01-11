package com.khiladiadda.league.team;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.league.participant.interfaces.IParticipantPresenter;
import com.khiladiadda.league.participant.interfaces.IParticipantView;
import com.khiladiadda.league.participant.ParticipantPresenter;
import com.khiladiadda.league.team.adapter.TeamAdapter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.QuizParticipantResponse;
import com.khiladiadda.network.model.response.TeamName;
import com.khiladiadda.network.model.response.TeamResponse;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TeamActivity extends BaseActivity implements IParticipantView {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;
    @BindView(R.id.rv_team) RecyclerView mRV;

    private IParticipantPresenter mPresenter;
    private TeamAdapter mLeagueAdapter;
    private List<TeamName> mLeagueList;

    @Override protected int getContentView() {
        return R.layout.activity_team;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_view_team);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        mPresenter = new ParticipantPresenter(this);

        mLeagueList = new ArrayList<>();
        mLeagueAdapter = new TeamAdapter(mLeagueList);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(mLeagueAdapter);

        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getTeam(getIntent().getStringExtra(AppConstant.ID));
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
        }
    }

    @Override public void onParticipantComplete(ParticipantResponse responseModel) {

    }

    @Override public void onParticipantFailure(ApiError error) {

    }

    @Override public void onTeamComplete(TeamResponse responseModel) {
        hideProgress();
        if (responseModel.getResponse().size() > 0) {
            mLeagueList.clear();
            mLeagueList.addAll(responseModel.getResponse());
            mLeagueAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override public void onTeamFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onQuizParticipantComplete(QuizParticipantResponse responseModel) {

    }

    @Override public void onQuizParticipantFailure(ApiError error) {

    }

    @Override public void onFBGroupParticipantComplete(FBParticipantResponse responseModel) {

    }

    @Override public void onFBGroupParticipantFailure(ApiError error) {

    }

    @Override public void onFBBattleParticipantComplete(FBParticipantResponse responseModel) {

    }

    @Override public void onFBBattleParticipantFailure(ApiError error) {

    }

    @Override
    public void onFBMatchParticipantComplete(FBParticipantResponse responseModel) {

    }

    @Override
    public void onFBMatchParticipantFailure(ApiError error) {

    }

    @Override protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

}