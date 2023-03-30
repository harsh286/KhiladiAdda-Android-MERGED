package com.khiladiadda.gameleague;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
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
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseData;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseDataMyTournament;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewDroidoActivity extends BaseActivity implements ITrendingTournamentView, IOnGamesClickListener, IDialogFilter {

    @BindView(R.id.iv_back_arroww)
    ImageView mIvBack;
    @BindView(R.id.tab_layout_droido)
    TabLayout tabLayout;
    @BindView(R.id.mcv_rules)
    MaterialCardView rulesMCV;
    @BindView(R.id.tv_rules_droido)
    TextView rulesTV;
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
    RecyclerView myTournamentRv;
    @BindView(R.id.tv_filter_number)
    TextView filterNumbersTV;
    private ITrendingTournamentPresenter mPresenter;
    private TournamentMoreGameAdapter mAllTournamentGameAdapter;
    private MyTournamentGameAdapter myTournamentGameAdapter;
    private List<ResponseData> gameAllTournamentList = new ArrayList<>();
    private List<ResponseDataMyTournament> gameMyTournamentList = new ArrayList<>();
    private int size = 0;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    @BindView(R.id.rl_image)
    RelativeLayout mImageRL;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;

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
        rulesTV.setVisibility(View.VISIBLE);
        rulesMCV.setVisibility(View.VISIBLE);
        rulesTV.setOnClickListener(this);
        mPresenter = new TrendingTournamentPresenter(this);
        filterNumbersTV.setVisibility(View.GONE);
        mRecyclerViewMoreTournament.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllTournamentGameAdapter = new TournamentMoreGameAdapter(this, this, gameAllTournamentList);
        mRecyclerViewMoreTournament.setAdapter(mAllTournamentGameAdapter);
        myTournamentRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myTournamentGameAdapter = new MyTournamentGameAdapter(this, this, gameMyTournamentList);
        myTournamentRv.setAdapter(myTournamentGameAdapter);
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
                        filterNumbersTV.setVisibility(View.GONE);
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
                        filterNumbersTV.setVisibility(View.GONE);
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
        myTournamentRv.setVisibility(View.GONE);
        gameAllTournamentList.clear();
        getTrendingTournamentList();
    }

    private void switchSecondAdapter() {
        mRecyclerViewMoreTournament.setVisibility(View.GONE);
        myTournamentRv.setVisibility(View.VISIBLE);
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
            Snackbar.make(myTournamentRv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
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
        if (response.isStatus()) {
            List<BannerDetails> bannerData = response.getBanner();
            if (bannerData != null && bannerData.size() > 0) {
                mImageRL.setVisibility(View.VISIBLE);
                setUpAdvertisementViewPager(bannerData);
            } else {
                mImageRL.setVisibility(View.GONE);
            }
            if (!response.getResponse().isEmpty()) {
                size = response.getResponse().size();
                filterNumbersTV.setVisibility(View.VISIBLE);
                filterNumbersTV.setText(String.valueOf("(" + size + ")"));
                tvError.setVisibility(View.GONE);
                gameAllTournamentList.clear();
                gameAllTournamentList.addAll(response.getResponse());
                mAllTournamentGameAdapter.notifyDataSetChanged();
            } else {
                filterNumbersTV.setVisibility(View.GONE);
                gameAllTournamentList.clear();
                mAllTournamentGameAdapter.notifyDataSetChanged();
                tvError.setVisibility(View.VISIBLE);
                hideProgress();
            }
        } else{
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
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
            filterNumbersTV.setVisibility(View.GONE);
            gameMyTournamentList.clear();
            gameMyTournamentList.addAll(response.getTournamentList());
            myTournamentGameAdapter.notifyDataSetChanged();
        }else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMyTournamentFailure(ApiError error) {
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
        if (!response.getResponse().isEmpty()) {
            tvError.setVisibility(View.GONE);
            size = response.getResponse().size();
            filterNumbersTV.setText(String.valueOf("(" + size + ")"));
            gameAllTournamentList.clear();
            gameAllTournamentList.addAll(response.getResponse());
            mAllTournamentGameAdapter.notifyDataSetChanged();
        } else {
            gameAllTournamentList.clear();
            mAllTournamentGameAdapter.notifyDataSetChanged();
            filterNumbersTV.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            hideProgress();
        }
    }

    @Override
    public void getFiltersTournamentFailed(ApiError error) {
        hideProgress();
    }

    private void setUpAdvertisementViewPager(List<BannerDetails> advertisementDetails) {
        mAdvertisementsList.clear();
        mAdvertisementsList.addAll(advertisementDetails);
        List<Fragment> mFragmentList = new ArrayList<>();
        for (BannerDetails advertisement : advertisementDetails) {
            mFragmentList.add(BannerFragment.getInstance(advertisement));
        }
        BannerPagerAdapter adapter = new BannerPagerAdapter(this.getSupportFragmentManager(), mFragmentList);
        mBannerVP.setAdapter(adapter);
        mBannerVP.setOffscreenPageLimit(3);
        if (mHandler == null) {
            mHandler = new Handler();
            moveToNextAd(0);
        }
    }

    private void moveToNextAd(int i) {
        mBannerVP.setCurrentItem(i, true);
        mHandler.postDelayed(() -> {
            int currentItem = mBannerVP.getCurrentItem();
            moveToNextAd((currentItem + 1) % mAdvertisementsList.size() == 0 ? 0 : currentItem + 1);
        }, 10000);
    }

}
