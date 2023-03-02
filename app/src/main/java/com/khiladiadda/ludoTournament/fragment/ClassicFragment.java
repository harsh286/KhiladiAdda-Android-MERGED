package com.khiladiadda.ludoTournament.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.ludoTournament.activity.LudoTmtTounamentActivity;
import com.khiladiadda.ludoTournament.adapter.LudoTmtDashboardAdapter;
import com.khiladiadda.ludoTournament.adapter.LudoTmtMyMatchAdapter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtMyMatchView;
import com.khiladiadda.ludoTournament.ip.ILudoTmtView;
import com.khiladiadda.ludoTournament.ip.LudoTmtPresenter;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchResponse;
import com.khiladiadda.utility.NetworkStatus;

import java.util.List;

import butterknife.BindView;


public class ClassicFragment extends BaseFragment implements IOnClickListener, ILudoTmtView {


    @BindView(R.id.rv_live_tournament)
    RecyclerView liveTmtRv;
    @BindView(R.id.tv_no_data)
    TextView noDataTv;

    private LudoTmtPresenter mPresenter;
    private List<LudoTmtAllTournamentResponse> ludoTmtAllTournamentResponses;


    @Override
    protected int getContentView() {
        return R.layout.fragment_classic;
    }

    @Override
    protected void initViews(View view) {
        mPresenter = new LudoTmtPresenter(this);
        setupRv();
    }

    private void setupRv() {
        liveTmtRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initBundle(Bundle bundle) {

    }


    @Override
    protected void initVariables() {
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getContext(), LudoTmtTounamentActivity.class);
        intent.putExtra("AllLudoTournaments", ludoTmtAllTournamentResponses.get(pos));
        startActivity(intent);
    }

    @Override
    public void onInProgressClick() {
        AppDialog.showAlertDialog(getActivity(), "Match is in-progress");
    }

    @Override
    public void onRefresh() {

    }

    private void callLiveTournamentApi() {
        if (new NetworkStatus(getContext()).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getAllTournament(true, 1, false,"", true);
        } else {
            Snackbar.make(liveTmtRv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        callLiveTournamentApi();
    }

    @Override
    public void onGetAllTournamentComplete(LudoTmtAllTournamentMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
            ludoTmtAllTournamentResponses = response.getResponse();
            if (response.getResponse().size() > 0) {
                noDataTv.setVisibility(View.GONE);
                liveTmtRv.setAdapter(new LudoTmtDashboardAdapter(getContext(), this, response.getResponse()));
            } else {
                liveTmtRv.setAdapter(new LudoTmtDashboardAdapter(getContext(), this, response.getResponse()));
                noDataTv.setVisibility(View.VISIBLE);
                noDataTv.setText(response.getMessage());
            }
        }
    }

    @Override
    public void onGetAllTournamentFailure(ApiError errorMsg) {
        hideProgress();

    }
}