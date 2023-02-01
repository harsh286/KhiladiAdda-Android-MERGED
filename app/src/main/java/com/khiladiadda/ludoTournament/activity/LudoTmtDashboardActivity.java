package com.khiladiadda.ludoTournament.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.gameleague.adapter.NewDroidoAdapterViewPager;
import com.khiladiadda.ludoTournament.adapter.LudoTmtDashboardAdapter;
import com.khiladiadda.ludoTournament.adapter.MyTournamentViewPagerAdapter;
import com.khiladiadda.ludoTournament.ip.LudoTmtPresenter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtView;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.ludoTournament.LudoTournamentFetchRequest;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.List;

import butterknife.BindView;

public class LudoTmtDashboardActivity extends BaseActivity implements IOnClickListener, ILudoTmtView {
    @BindView(R.id.rv_tournament)
    RecyclerView allTournamentRv;
    @BindView(R.id.tl_ludotmt)
    TabLayout ludoTmtTL;
    @BindView(R.id.tab_sub_my_tournament)
    TabLayout subMyTmtTl;
    @BindView(R.id.vp_ludotmt)
    ViewPager ludotmtVP;
    @BindView(R.id.img_rules)
    ImageView rulesImg;
    @BindView(R.id.iv_back_arroww)
    ImageView backArrowIv;

    private MyTournamentViewPagerAdapter mMyTournamentViewPagerAdapter;
    private LudoTmtPresenter mPresenter;
    private List<LudoTmtAllTournamentResponse> responses;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_dashboard;
    }

    @Override
    protected void initViews() {
        mPresenter = new LudoTmtPresenter(this);
        setupRV();
        tabData();
        setupTablayout();
        viewPagerData();
        setSubMyTmttabData();
        setupMyTournamentViewPager();
//        swipeRefreshLayout.setRefreshing(false);
//        swipeRefreshLayout();
    }

    @Override
    protected void initVariables() {
        rulesImg.setOnClickListener(this);
        backArrowIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.img_rules) {
//            startActivity(new Intent(this, LudoTmtRulesActivity.class));
            AppUtilityMethods.showTooltip(this, rulesImg, getString(R.string.english_rules));
        } else if (p0.getId() == R.id.img_rules) {
            finish();
        }else{
            finish();
        }
    }

//    private void swipeRefreshLayout() {
//        swipeRefreshLayout.setOnRefreshListener(() ->
//                callAllTournamentApi());
//    }

    private void callAllTournamentApi() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getAllTournament(true);
        } else {
            Snackbar.make(backArrowIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setupRV() {
        allTournamentRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void tabData() {
        TabLayout.Tab firstTab = ludoTmtTL.newTab();
        firstTab.setText(R.string.all_tournament);
        ludoTmtTL.addTab(firstTab);
        TabLayout.Tab secondTab = ludoTmtTL.newTab();
        secondTab.setText(R.string.my_tournaments);
        ludoTmtTL.addTab(secondTab);
    }

    private void setSubMyTmttabData() {
        TabLayout.Tab firstTab = subMyTmtTl.newTab();
        firstTab.setText(R.string.text__live);
        subMyTmtTl.addTab(firstTab);
        TabLayout.Tab secondTab = subMyTmtTl.newTab();
        secondTab.setText(R.string.text__past);
        subMyTmtTl.addTab(secondTab);
        TabLayout.Tab thirdTab = subMyTmtTl.newTab();
        thirdTab.setText(R.string.text__upcoming);
        subMyTmtTl.addTab(thirdTab);
    }

    private void setupTablayout() {
        ludoTmtTL.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                        switchFirstAdapter();
                        break;
                    case 1:
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
                switch (tab.getPosition()) {
                    case 0:
                        switchFirstAdapter();
                        break;
                    case 1:
                        switchSecondAdapter();
                        break;
                }
            }
        });
    }

    private void switchFirstAdapter() {
//        getTrendingTournamentList();
        allTournamentRv.setVisibility(View.VISIBLE);
        subMyTmtTl.setVisibility(View.GONE);
        ludotmtVP.setVisibility(View.GONE);
        callAllTournamentApi();
    }

    private void switchSecondAdapter() {
//        getMyTournament();
        allTournamentRv.setVisibility(View.GONE);
        subMyTmtTl.setVisibility(View.VISIBLE);
        ludotmtVP.setVisibility(View.VISIBLE);
    }

    private void setupMyTournamentViewPager() {
        mMyTournamentViewPagerAdapter = new MyTournamentViewPagerAdapter(getSupportFragmentManager(), subMyTmtTl.getTabCount());
        ludotmtVP.setAdapter(mMyTournamentViewPagerAdapter);
        ludotmtVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(subMyTmtTl));
        subMyTmtTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ludotmtVP.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void viewPagerData() {
        ViewPager viewPager = findViewById(R.id.vp_advertisement);
        NewDroidoAdapterViewPager newDroidoAdapterViewPager = new NewDroidoAdapterViewPager(this);
        viewPager.setAdapter(newDroidoAdapterViewPager);
        viewPager.setCurrentItem(newDroidoAdapterViewPager.getCount() - 1);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, LudoTmtTounamentActivity.class);
        intent.putExtra("AllLudoTournaments", responses.get(pos));
        startActivity(intent);
    }

    @Override
    public void onInProgressClick() {
        AppDialog.showAlertDialog(this, "Match is in-progress");
    }

    @Override
    public void onGetAllTournamentComplete(LudoTmtAllTournamentMainResponse response) {
        hideProgress();
//        swipeRefreshLayout.setRefreshing(false);
        try {
            if (response.isStatus()) {
                responses = response.getResponse();
                allTournamentRv.setAdapter(new LudoTmtDashboardAdapter(this, this, response.getResponse()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetAllTournamentFailure(ApiError errorMsg) {
        hideProgress();
//        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        callAllTournamentApi();
    }
}