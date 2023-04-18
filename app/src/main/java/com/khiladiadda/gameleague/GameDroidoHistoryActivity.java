package com.khiladiadda.gameleague;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.GameDroidoHistoryAdapter;
import com.khiladiadda.gameleague.interfaces.IDridoHistoryView;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.DridoHistoryPresenter;
import com.khiladiadda.network.model.response.droid_doresponse.DroidoHistoryGameList;
import com.khiladiadda.network.model.response.droid_doresponse.GameHistoryDroido;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GameDroidoHistoryActivity extends BaseActivity implements IDridoHistoryView, IOnGamesClickListener {
    @BindView(R.id.tv_error_show)
    TextView tvErrorHistory;
    @BindView(R.id.iv_title_name)
    TextView tvTitleToolbar;
    @BindView(R.id.iv_cross_arrow)
    MaterialCardView imgClose;
    @BindView(R.id.rv_history)
    RecyclerView rvDroidoHistory;
    private DridoHistoryPresenter mPresenter;
    private List<GameHistoryDroido> mGameHistoryDroidoList;
    private GameDroidoHistoryAdapter mGameDroidoHistoryAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_game_history_actvivity;
    }

    @Override
    protected void initViews() {
        imgClose.setOnClickListener(this);
        tvTitleToolbar.setText(getString(R.string.history));
    }

    @Override
    protected void initVariables() {
        mPresenter = new DridoHistoryPresenter(this);
        mGameHistoryDroidoList = new ArrayList<>();
        mGameDroidoHistoryAdapter = new GameDroidoHistoryAdapter(mGameHistoryDroidoList, this, this);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this);
        rvDroidoHistory.setLayoutManager(mLayoutManger);
        rvDroidoHistory.setAdapter(mGameDroidoHistoryAdapter);
        getDroidoHistoryGameList();
    }

    private void getDroidoHistoryGameList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getDroidoHistoryData();
        } else {
            Snackbar.make(rvDroidoHistory, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_cross_arrow) {
            finish();
        }
    }

    @Override
    public void onDroidoHistoryListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onDroidoHistoryListSuccess(DroidoHistoryGameList response) {
        mGameHistoryDroidoList.clear();
        mGameHistoryDroidoList.addAll(response.getGameHistoryDroidoList());
        mGameDroidoHistoryAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}