package com.khiladiadda.clashx2.main.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.main.adapter.ClashXDashBoardAdapter;
import com.khiladiadda.clashx2.interfaces.IOnItemGamesClickListener;
import com.khiladiadda.clashx2.main.activity.HTHBattlesActivity;
import com.khiladiadda.clashx2.main.HTHPresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FootballDashBoardFragment extends BaseFragment implements IOnItemGamesClickListener, IHTHBattleView {
    @BindView(R.id.rv_football_games)
    RecyclerView rvFootballGames;
    @BindView(R.id.srl_matches)
    SwipeRefreshLayout mMatchesSRL;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTv;
    private ClashXDashBoardAdapter gamesDashBoardAdapter;
    private ArrayList<HTHResponseDetails> gamesDataArrayList = new ArrayList<>();
    private IHTHBattlePresenter mPresenter;
    private List<HTHResponseDetails> mMatchList;


    @Override
    protected int getContentView() {
        return R.layout.fragment_football_dash_board;
    }

    @Override
    protected void initViews(View view) {
        mPresenter = new HTHPresenter(this);
        mMatchesSRL.setOnRefreshListener(this::getData);
        initRecyclerViewGames();
    }

    private void initRecyclerViewGames() {
//        gamesDataArrayList = getData();
        gamesDashBoardAdapter = new ClashXDashBoardAdapter(gamesDataArrayList);
        rvFootballGames.setHasFixedSize(true);
        rvFootballGames.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvFootballGames.setAdapter(gamesDashBoardAdapter);

//        getData();
    }

    @Override
    protected void initBundle(Bundle bundle) {

    }

    private void getData() {
        if (new NetworkStatus(getContext()).isInternetOn()) {
            showProgress("");
            mPresenter.getHTHMatchList("", 2);
        } else {
//            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            Toast.makeText(mActivity, R.string.error_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initVariables() {
        gamesDashBoardAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onGamesItemClick(View view, int position, int tag) {
        if (mMatchList.get(position).isLive()) {
            Intent i = new Intent(getContext(), HTHBattlesActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.FROM_FOOTBALL);
            i.putExtra(AppConstant.BATTLE_DATA, mMatchList.get(position));
            startActivity(i);
        } else {
//            Snackbar.make(mLeagueTV, getString(R.string.text_match_open_soon), Snackbar.LENGTH_LONG).show();
            Toast.makeText(mActivity, getString(R.string.text_match_open_soon), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetHTHMatchListComplete(HTHMainResponse responseModel) {
        hideProgress();
        mMatchesSRL.setRefreshing(false);
        gamesDataArrayList.clear();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().size() != 0) {
                mMatchList = responseModel.getResponse();
                mNoDataTv.setVisibility(View.GONE);
                gamesDataArrayList.addAll(responseModel.getResponse());
                gamesDashBoardAdapter.notifyDataSetChanged();
            }else {
                gamesDataArrayList.clear();
                gamesDashBoardAdapter.notifyDataSetChanged();
                mNoDataTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetHTHMatchListFailure(ApiError error) {
        hideProgress();
        mMatchesSRL.setRefreshing(false);

    }

    @Override
    public void onCancelBattle(HTHCancelResponse responseModel) {
        mMatchesSRL.setRefreshing(false);
    }

    @Override
    public void onCancelBattleFailure(ApiError error) {
        hideProgress();
        mMatchesSRL.setRefreshing(false);
    }

    @Override
    public void onGetResultBattle(Result responseModel) {
        mMatchesSRL.setRefreshing(false);
    }

    @Override
    public void onResultBattleFailure(ApiError error) {
        hideProgress();
        mMatchesSRL.setRefreshing(false);
    }

    @Override
    public void onMatchStatus(HTHMainResponse responseModel) {
        mMatchesSRL.setRefreshing(false);

    }

    @Override
    public void onMatchStatusError(ApiError error) {
        hideProgress();
        mMatchesSRL.setRefreshing(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}