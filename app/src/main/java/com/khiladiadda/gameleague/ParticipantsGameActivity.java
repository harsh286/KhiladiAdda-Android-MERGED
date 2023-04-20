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
import com.khiladiadda.gameleague.adapter.GamesParticipantsAdapter;
import com.khiladiadda.gameleague.interfaces.IParticipantsPresenter;
import com.khiladiadda.gameleague.interfaces.IParticipantsView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.GameParticipantsDataResponse;
import com.khiladiadda.network.model.response.droid_doresponse.ParticipantsPresenter;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseItem;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ParticipantsGameActivity extends BaseActivity implements IParticipantsView {
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.iv_back_arroww)
    ImageView ivBack;
    @BindView(R.id.iv_title_name)
    TextView tvTitleToolbar;
    @BindView(R.id.rl_main)
    RelativeLayout relativeLayoutMain;
    @BindView(R.id.rl_live_leaderboard)
    RelativeLayout relativeLayoutLeaderBoard;
    @BindView(R.id.rv_participant)
    RecyclerView rvParticipants;
    @BindView(R.id.mcv_rules)
    MaterialCardView mcvRules;
    @BindView(R.id.tv_rules_droido)
    TextView tvRules;
    private GamesParticipantsAdapter mGamesParticipantsAdapter;
    private List<ResponseItem> gameParticipantDataList;
    private String id;
    private IParticipantsPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_participants_game;
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tvTitleToolbar.setText(getString(R.string.participant));
        ivBack.setOnClickListener(this);
        tvRules.setOnClickListener(this);
        mcvRules.setVisibility(View.VISIBLE);
        tvRules.setVisibility(View.VISIBLE);
        tvRules.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        relativeLayoutLeaderBoard.setBackgroundResource(R.drawable.ic_games_final_droid);
        gameParticipantDataList = new ArrayList<>();
        mGamesParticipantsAdapter = new GamesParticipantsAdapter(gameParticipantDataList, this);
        rvParticipants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvParticipants.setAdapter(mGamesParticipantsAdapter);
        mPresenter = new ParticipantsPresenter(this);
        getParticipantsList();
    }

    private void getParticipantsList() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getParticipantsData(id);
        } else {
            Snackbar.make(rvParticipants, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arroww:
                finish();
                break;
            case R.id.tv_rules_droido:
                startActivity(new Intent(this, RulesActivity.class));
                break;
        }
    }

    @Override
    public void onParticipantsSuccess(GameParticipantsDataResponse response) {
        if (!response.getResponse().isEmpty()) {
            hideProgress();
            gameParticipantDataList.clear();
            gameParticipantDataList.addAll(response.getResponse());
            mGamesParticipantsAdapter.notifyDataSetChanged();
        } else {
            hideProgress();
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onParticipantsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }
}