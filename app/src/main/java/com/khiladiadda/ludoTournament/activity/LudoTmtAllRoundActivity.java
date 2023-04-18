package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.ludoTournament.adapter.LudoTmtPastAllRoundAdapter;
import com.khiladiadda.ludoTournament.adapter.LudoTmtRoundAdapter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtPastRoundsView;
import com.khiladiadda.ludoTournament.ip.ILudoTmtRoundsView;
import com.khiladiadda.ludoTournament.ip.LudoTmtPastRoundsPresenter;
import com.khiladiadda.ludoTournament.ip.LudoTmtRoundsPresenter;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllPastRoundsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import butterknife.BindView;

public class LudoTmtAllRoundActivity extends BaseActivity implements ILudoTmtPastRoundsView, IOnClickListener {

    @BindView(R.id.iv_back_arrow)
    ImageView backIv;
    @BindView(R.id.rv_all_round)
    RecyclerView allPastRoundRv;
    @BindView(R.id.tv_no_data)
    TextView noDataTv;
    private LudoTmtPastRoundsPresenter mPresenter;
    private LudoTmtAllTournamentResponse matchDetailsResponse;
    private LudoTmtMyMatchResponse ludoTmtMyMatchResponse;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_all_round;
    }

    @Override
    protected void initViews() {
        mPresenter = new LudoTmtPastRoundsPresenter(this);

        allPastRoundRv.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getParcelableExtra("AllLudoTournaments") != null) {
            matchDetailsResponse = getIntent().getParcelableExtra("AllLudoTournaments");
        } else {
            ludoTmtMyMatchResponse = getIntent().getParcelableExtra("MyLudoTournaments");
        }
    }

    @Override
    protected void initVariables() {
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back_arrow) {
            finish();
        }
    }

    private void callApi() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (matchDetailsResponse != null)
                mPresenter.getTournamentPastRounds(matchDetailsResponse.getId());
            else
                mPresenter.getTournamentPastRounds(ludoTmtMyMatchResponse.getId());

        } else {
            Snackbar.make(backIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPastRoundsTournamentComplete(LudoTmtAllPastRoundsMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
            if (response.getResponse().size() != 0) {
                noDataTv.setVisibility(View.GONE);
                if (matchDetailsResponse != null)
                    allPastRoundRv.setAdapter(new LudoTmtPastAllRoundAdapter(this, this, response.getResponse(), false, matchDetailsResponse.getStartDate()));
                else
                    allPastRoundRv.setAdapter(new LudoTmtPastAllRoundAdapter(this, this, response.getResponse(), false, ludoTmtMyMatchResponse.getStartDate()));
            } else {
                noDataTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetPastRoundsTournamentFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }

    @Override
    public void onItemClick(int pos) {

    }

    @Override
    public void onInProgressClick() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}