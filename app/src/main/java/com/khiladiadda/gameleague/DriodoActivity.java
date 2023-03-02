package com.khiladiadda.gameleague;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.GamesMainAdapter;
import com.khiladiadda.gameleague.interfaces.IDridoPresenter;
import com.khiladiadda.gameleague.interfaces.IDridoView;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.gameleague.ip.DridoPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.GameList;
import com.khiladiadda.network.model.response.droid_doresponse.ItemGamesMainResponse;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DriodoActivity {
//    @BindView(R.id.iv_back_arrow)
//    ImageView mIvBack;
//    @BindView(R.id.cl_back_arrow)
//    MaterialCardView mCVBack;
//    @BindView(R.id.tv_error)
//    TextView tvErrorGames;
//    @BindView(R.id.rv_games)
//    RecyclerView mGamesRV;
//    @BindView(R.id.iv_title_name)
//    TextView toolbarTitle;
//    private GamesMainAdapter mGamesmainAdapter;
//    private IDridoPresenter mPresenter;
//    private List<GameList> mGameLists;
//
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_driodo;
//    }
//
//    @Override
//    protected void initViews() {
//        toolbarTitle.setText(R.string.games);
//
//    }
//
//    @Override
//    protected void initVariables() {
//        mPresenter = new DridoPresenter(this);
//        mGameLists = new ArrayList<>();
//        mGamesmainAdapter = new GamesMainAdapter(mGameLists, this, this);
//        mGamesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mGamesRV.setAdapter(mGamesmainAdapter);
//        getGameList();
//    }
//
//    private void getGameList() {
//        if (new NetworkStatus(this).isInternetOn()) {
//            showProgress(getString(R.string.txt_progress_authentication));
//            mPresenter.getGameData();
//        } else {
//            Snackbar.make(mGamesRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//
//    @Override
//    public void onGameListFailure(ApiError error) {
//        hideProgress();
//    }
//
//    @Override
//    public void onGameListSuccess(ItemGamesMainResponse response) {
//        if (!response.getGameListList().isEmpty()) {
//            hideProgress();
//            mGameLists.clear();
//            mGameLists.addAll(response.getGameListList());
//            mGamesmainAdapter.notifyDataSetChanged();
//        } else {
//            tvErrorGames.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onItemClick(int position) {
//    }
}