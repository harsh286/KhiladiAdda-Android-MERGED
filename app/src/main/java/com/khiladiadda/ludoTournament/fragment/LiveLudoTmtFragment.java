package com.khiladiadda.ludoTournament.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.interfaces.IOnItemGamesClickListener;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.activity.HTHBattlesActivity;
import com.khiladiadda.clashx2.main.adapter.ClashXDashBoardAdapter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.ludoTournament.adapter.LudoTmtDashboardAdapter;
import com.khiladiadda.ludoTournament.adapter.LudoTmtMyMatchAdapter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtMyMatchView;
import com.khiladiadda.ludoTournament.ip.ILudoTmtView;
import com.khiladiadda.ludoTournament.ip.LudoTmtPresenter;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveLudoTmtFragment extends BaseFragment implements IOnClickListener, ILudoTmtMyMatchView {


    @BindView(R.id.rv_live_tournament)
    RecyclerView liveTmtRv;
    @BindView(R.id.tv_no_data)
    TextView noDataTv;

    private LudoTmtPresenter mPresenter;


    @Override
    protected int getContentView() {
        return R.layout.fragment_live_ludo_tmt;
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

    }

    private void callLiveTournamentApi() {
        if (new NetworkStatus(getContext()).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getLiveTournament();
        } else {
            Snackbar.make(liveTmtRv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetMyMatchTournamentComplete(LudoTmtMyMatchMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
            if (response.getResponse().size() > 0) {
                noDataTv.setVisibility(View.GONE);
                liveTmtRv.setAdapter(new LudoTmtMyMatchAdapter(getContext(), this, response.getResponse()));
            } else {
                noDataTv.setVisibility(View.VISIBLE);
                noDataTv.setText(response.getMessage());
            }
        }
    }

    @Override
    public void onGetMyMatchTournamentFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        callLiveTournamentApi();
    }
}