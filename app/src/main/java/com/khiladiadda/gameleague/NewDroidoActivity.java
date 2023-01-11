package com.khiladiadda.gameleague;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.gameleague.adapter.MyTournamentGameAdapter;
import com.khiladiadda.gameleague.adapter.NewDroidoAdapterViewPager;
import com.khiladiadda.gameleague.adapter.TournamentMoreGameAdapter;
import com.khiladiadda.gameleague.interfaces.IDialogFilter;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentPresenter;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentView;
import com.khiladiadda.gameleague.ip.TrendingTournamentPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseDataMyTournament;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentTrendingList;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewDroidoActivity extends BaseActivity implements ITrendingTournamentView, IOnGamesClickListener, IDialogFilter {
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    @BindView(R.id.iv_back_arroww)
    ImageView mIvBack;
    @BindView(R.id.tab_layout_droido)
    TabLayout tabLayout;
    @BindView(R.id.mcv_rules)
    MaterialCardView mcvRules;
    @BindView(R.id.tv_rules_droido)
    TextView tvRules;
    @BindView(R.id.rv_more_tournaments)
    RecyclerView mRecyclerViewMoreTournament;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.mcv_filters)
    MaterialCardView mCvTvFilters;
    @BindView(R.id.tv_filters)
    TextView tvFilters;
    @BindView(R.id.tv_trending_txt)
    TextView tvTrending;
    @BindView(R.id.rv_my_tournaments)
    RecyclerView rvMyTournament;
    @BindView(R.id.tv_filter_number)
    TextView tvFilterNumbers;
    private ITrendingTournamentPresenter mPresenter;
    private TournamentMoreGameAdapter mAllTournamentGameAdapter;
    private MyTournamentGameAdapter myTournamentGameAdapter;
    private List<TournamentTrendingList> gameAllTournamentList = new ArrayList<>();
    private List<ResponseDataMyTournament> gameMyTournamentList = new ArrayList<>();
    private int size = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_new_droido;
    }

    @Override
    protected void initViews() {
        tvError.setVisibility(View.GONE);
        mIvBack.setOnClickListener(this);
        mCvTvFilters.setOnClickListener(this);
        tvFilters.setOnClickListener(this);
        tvRules.setVisibility(View.VISIBLE);
        mcvRules.setVisibility(View.VISIBLE);
        tvRules.setOnClickListener(this);
        mPresenter = new TrendingTournamentPresenter(this);
        tvFilterNumbers.setVisibility(View.GONE);
        mRecyclerViewMoreTournament.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllTournamentGameAdapter = new TournamentMoreGameAdapter(this, this, gameAllTournamentList);
        mRecyclerViewMoreTournament.setAdapter(mAllTournamentGameAdapter);
        rvMyTournament.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myTournamentGameAdapter = new MyTournamentGameAdapter(this, this, gameMyTournamentList);
        rvMyTournament.setAdapter(myTournamentGameAdapter);
        tabData();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int index = 0; index < ((ViewGroup) tab.view).getChildCount(); index++) {
                    View nextChild = ((ViewGroup) tab.view).getChildAt(index);
                    if (nextChild instanceof TextView) {
                        TextView v = (TextView) nextChild;
                        v.setTypeface(null, Typeface.BOLD);
                    }
                }
                switch (tab.getPosition()) {
                    case 0:
                        tvFilters.setVisibility(View.VISIBLE);
                        tvTrending.setVisibility(View.VISIBLE);
                        mCvTvFilters.setVisibility(View.VISIBLE);
                        switchFirstAdapter();
                        break;
                    case 1:
                        tvFilters.setVisibility(View.INVISIBLE);
                        tvTrending.setVisibility(View.GONE);
                        tvFilterNumbers.setVisibility(View.GONE);
                        mCvTvFilters.setVisibility(View.INVISIBLE);
                        switchSecondAdapter();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                for (int index = 0; index < ((ViewGroup) tab.view).getChildCount(); index++) {
                    View nextChild = ((ViewGroup) tab.view).getChildAt(index);
                    if (nextChild instanceof TextView) {
                        TextView v = (TextView) nextChild;
                        v.setTypeface(null, Typeface.NORMAL);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                getTrendingTournamentList();
                switch (tab.getPosition()) {
                    case 0:
                        tvFilters.setVisibility(View.VISIBLE);
                        tvTrending.setVisibility(View.VISIBLE);
                        mCvTvFilters.setVisibility(View.VISIBLE);
                        switchFirstAdapter();
                        break;
                    case 1:
                        tvFilters.setVisibility(View.INVISIBLE);
                        tvTrending.setVisibility(View.GONE);
                        tvFilterNumbers.setVisibility(View.GONE);
                        mCvTvFilters.setVisibility(View.INVISIBLE);
                        switchSecondAdapter();
                        break;
                }
            }
        });
        viewPagerData();
    }

    private void viewPagerData() {
        ViewPager viewPager = findViewById(R.id.vp_advertisement);
        NewDroidoAdapterViewPager newDroidoAdapterViewPager = new NewDroidoAdapterViewPager(this);
        viewPager.setAdapter(newDroidoAdapterViewPager);
        viewPager.setCurrentItem(newDroidoAdapterViewPager.getCount() - 1);

    }

    private void tabData() {
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText(R.string.all_tournament);
        tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText(R.string.my_tournaments);
        tabLayout.addTab(secondTab);
    }

    private void switchFirstAdapter() {
        mRecyclerViewMoreTournament.setVisibility(View.VISIBLE);
        tvTrending.setText(getString(R.string.tournament));
        rvMyTournament.setVisibility(View.GONE);
//        tvFilterNumbers.setVisibility(View.GONE);
        gameAllTournamentList.clear();
        getTrendingTournamentList();
    }

    private void switchSecondAdapter() {
        mRecyclerViewMoreTournament.setVisibility(View.GONE);
        rvMyTournament.setVisibility(View.VISIBLE);
//        tvFilterNumbers.setVisibility(View.GONE);
        gameMyTournamentList.clear();
        getMyTournament();
    }

    private void getMyTournament() {
        myTournamentAPI();
    }

    private void myTournamentAPI() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getMyTournamentData();
        } else {
            Snackbar.make(rvMyTournament, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initVariables() {
        getTrendingTournamentList();
    }

    private void getTrendingTournamentList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getTrendingTournamentData();
        } else {
            Snackbar.make(mRecyclerViewMoreTournament, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arroww:
                onBackPressed();
                finish();
                break;
            case R.id.mcv_filters:
            case R.id.tv_filters:
                AppDialog.droidoDialog(this, this);
                break;
            case R.id.tv_rules_droido:
                startActivity(new Intent(this, RulesActivity.class));
                break;
            case R.id.btn_apply:
                break;
        }
    }

    @Override
    public void onGameTrendingTournamentSuccess(TrendingTournamentResponse response) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 500);
        if (response.getResponseData().getTrendingList().size() != 0) {
            size = response.getResponseData().getTournamentList().size();

            tvFilterNumbers.setVisibility(View.VISIBLE);

            tvFilterNumbers.setText(String.valueOf("(" + size + ")"));
            tvError.setVisibility(View.GONE);
            gameAllTournamentList.clear();
            gameAllTournamentList.addAll(response.getResponseData().getTournamentList());
            mAllTournamentGameAdapter.notifyDataSetChanged();
        } else {
            gameAllTournamentList.clear();
            mAllTournamentGameAdapter.notifyDataSetChanged();
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
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 500);
        if (!response.getTournamentList().isEmpty()) {
            tvError.setVisibility(View.GONE);

            tvFilterNumbers.setVisibility(View.GONE);

            gameMyTournamentList.clear();
            gameMyTournamentList.addAll(response.getTournamentList());
            myTournamentGameAdapter.notifyDataSetChanged();
        } else {
            tvError.setVisibility(View.VISIBLE);
            hideProgress();
        }
    }

    @Override
    public void onMyTournamentFailure(ApiError error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
        hideProgress();
    }


    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void dialogFilters(int type) {
        if (type == R.integer.int_1) {
            getFilterApi(R.integer.int_1);
            tvTrending.setText(getString(R.string.trending));
        } else if (type == 2) {
            tvTrending.setText(getString(R.string.text_classic));
            getFilterApi(2);
        } else if (type == 3) {
            tvTrending.setText(getString(R.string.head_to_head));
            gameAllTournamentList.clear();
            getFilterApi(3);
        } else if (type == 4) {
            tvTrending.setText(getString(R.string.low_to_high));
            getFilterApi(4);
        } else if (type == 5) {
            tvTrending.setText(getString(R.string.high_to_low));
            getFilterApi(5);
        }
    }

    private void getFilterApi(int v) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getDataFilter(v);
        } else {
            Snackbar.make(mRecyclerViewMoreTournament, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getFiltersTournamentSuccess(TrendingTournamentResponse response) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 500);
        if (response.getResponseData().getTrendingList().size() != 0) {
            tvError.setVisibility(View.GONE);
            size = response.getResponseData().getTournamentList().size();
//            tvFilterNumbers.setVisibility(View.VISIBLE);
            tvFilterNumbers.setText(String.valueOf("(" + size + ")"));
            gameAllTournamentList.clear();
            gameAllTournamentList.addAll(response.getResponseData().getTournamentList());
            mAllTournamentGameAdapter.notifyDataSetChanged();
        } else {
            gameAllTournamentList.clear();
            mAllTournamentGameAdapter.notifyDataSetChanged();
            tvFilterNumbers.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            hideProgress();
        }
    }

    @Override
    public void getFiltersTournamentFailed(ApiError error) {
        hideProgress();
    }

}
