package com.khiladiadda.gameleague;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.TournamentMoreGameAdapter;
import com.khiladiadda.gameleague.adapter.TrendingGameAdapter;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentPresenter;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentView;
import com.khiladiadda.gameleague.ip.TrendingTournamentPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentTrendingList;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TrendingTournamentActivity extends BaseActivity implements ITrendingTournamentView, IOnGamesClickListener {
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_trending_txt)
    TextView tvTrending;
    @BindView(R.id.iv_image_icon)
    ImageView imgTrendingIcon;
    @BindView(R.id.tv_more_tour_txt)
    TextView tvMoreTournament;
    @BindView(R.id.rv_trending_tournaments)
    RecyclerView mRecyclerViewTrending;
    @BindView(R.id.rv_more_tournaments)
    RecyclerView mRecyclerViewMoreTournament;
    @BindView(R.id.iv_title_name)
    TextView toolbarTitle;
    @BindView(R.id.iv_back_arrow)
    ImageView mIvBack;
    @BindView(R.id.cl_back_arrow)
    MaterialCardView mCVBack;
    @BindView(R.id.cl_cross_arrow)
    MaterialCardView mCVHistory;
    private TrendingGameAdapter mTrendingGameAdapter;
    private TournamentMoreGameAdapter mTournamentMoreGameAdapter;
    private List<TournamentTrendingList> gameTrendingTournamentList = new ArrayList<>();
    private List<TournamentTrendingList> gameMoreTournamentList = new ArrayList<>();
    private ITrendingTournamentPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_trending_tournament;
    }

    @Override
    protected void initViews() {
        toolbarTitle.setText("Droid - O");
        mPresenter = new TrendingTournamentPresenter(this);
        mTrendingGameAdapter = new TrendingGameAdapter(this, this, gameTrendingTournamentList);
        mRecyclerViewTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTrending.setAdapter(mTrendingGameAdapter);
        mRecyclerViewMoreTournament.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTournamentMoreGameAdapter = new TournamentMoreGameAdapter(this, this, gameMoreTournamentList);
        mRecyclerViewMoreTournament.setAdapter(mTournamentMoreGameAdapter);
    }

    @Override
    protected void initVariables() {
        mIvBack.setOnClickListener(this);
        mCVBack.setOnClickListener(this);
        mCVHistory.setOnClickListener(this);
        mCVHistory.setVisibility(View.VISIBLE);
        getTrendingTournamentList();

    }

    private void getTrendingTournamentList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getTrendingTournamentData();
        } else {
            Snackbar.make(mRecyclerViewTrending, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
            case R.id.cl_back_arrow:
                finish();
                break;
            case R.id.cl_cross_arrow:
                Intent intent = new Intent(this, GameDroidoHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onGameTrendingTournamentSuccess(TrendingTournamentResponse response) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 500);
        if (!response.getResponseData().getTrendingList().isEmpty()) {
            tvError.setVisibility(View.GONE);
            gameTrendingTournamentList.addAll(response.getResponseData().getTrendingList());
            gameMoreTournamentList.addAll(response.getResponseData().getTournamentList());
            mTournamentMoreGameAdapter.notifyDataSetChanged();
            mTrendingGameAdapter.notifyDataSetChanged();
        } else {
            tvError.setVisibility(View.VISIBLE);
            hideProgress();
        }
    }

    @Override
    public void onGameTrendingTournamentFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onMyTournamentSuccess(MyTournamentResponse response) {

    }

    @Override
    public void onMyTournamentFailure(ApiError error) {

    }

    @Override
    public void getFiltersTournamentSuccess(TrendingTournamentResponse response) {

    }

    @Override
    public void getFiltersTournamentFailed(ApiError error) {

    }

    @Override
    public void onItemClick(int position) {

    }

}