package com.khiladiadda.clashx2.main.activity;

import static android.view.View.GONE;

import com.khiladiadda.clashx2.main.adapter.ClashXDashBoardTabsAdapter;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.clashx2.main.interfaces.ICxBannerPresenter;
import com.khiladiadda.clashx2.main.interfaces.ICxBannerView;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.clashx2.main.CxBannerPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.main.fragment.BannerFragment;
import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CxBannerMainResponse;
import com.khiladiadda.utility.NetworkStatus;

import androidx.viewpager.widget.ViewPager;

import com.khiladiadda.help.HelpActivity;

import com.khiladiadda.base.BaseActivity;

import androidx.fragment.app.Fragment;

import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

import android.os.Handler;
import android.view.View;

import com.khiladiadda.R;

import java.util.List;

import butterknife.BindView;

public class ClashXDashBoardActivity extends BaseActivity implements ICxBannerView, View.OnClickListener {
    @BindView(R.id.viewpager_banner)
    ViewPager mBannerPager;
    @BindView(R.id.viewPager_dashboard_games)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_dashboard)
    TabLayout tabLayout;
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_league)
    TextView mMyMatcher;
    @BindView(R.id.tv_help)
    TextView mTVHelp;
    @BindView(R.id.iv_notification_clashx)
    ImageView ivNotificatioCx2;
    private ClashXDashBoardTabsAdapter clashXDashBoardAdapter;
    private ICxBannerPresenter mPresenter;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;


    @Override
    protected int getContentView() {
        return R.layout.activity_games_dash_board;
    }

    @Override
    protected void initViews() {
        mPresenter = new CxBannerPresenter(this);
        mBackIV.setOnClickListener(this);
        mMyMatcher.setOnClickListener(this);
        mTVHelp.setOnClickListener(this);
        ivNotificatioCx2.setOnClickListener(this);
        getData();
    }

    private void setUpAdvertisementViewPager(List<BannerDetails> advertisementDetails) {
        mAdvertisementsList.clear();
        mAdvertisementsList.addAll(advertisementDetails);
        List<Fragment> mFragmentList = new ArrayList<>();
        for (BannerDetails advertisement : advertisementDetails) {
            mFragmentList.add(BannerFragment.getInstance(advertisement));
        }
        BannerPagerAdapter adapter = new BannerPagerAdapter(this.getSupportFragmentManager(), mFragmentList);
        mBannerPager.setAdapter(adapter);
        mBannerPager.setOffscreenPageLimit(3);
        if (mHandler == null) {
            mHandler = new Handler();
            moveToNextAd(0);
        }
    }

    private void moveToNextAd(int i) {
        mBannerPager.setCurrentItem(i, true);
        mHandler.postDelayed(() -> {
            int currentItem = mBannerPager.getCurrentItem();
            moveToNextAd((currentItem + 1) % mAdvertisementsList.size() == 0 ? 0 : currentItem + 1);
        }, 10000);
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            mPresenter.getBannerResponse();
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initVariables() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cricket)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.football)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.kabaadi)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        clashXDashBoardAdapter = new ClashXDashBoardTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(clashXDashBoardAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_league:
                Intent intent = new Intent(this, MyFanLeagueActivityHTH.class);
                startActivity(intent);
                break;
            case R.id.tv_help:
                Intent intent1 = new Intent(this, HelpActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_notification_clashx:
                Intent intent2 = new Intent(this, NotificationActivity.class);
                startActivity(intent2);
                break;
        }

    }

    @Override
    public void onCxBannerComplete(CxBannerMainResponse responseModel) {
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().size() > 0) {
                setUpAdvertisementViewPager(responseModel.getResponse());
            } else {
                mBannerPager.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onCxBannerFailure(ApiError error) {

    }


}