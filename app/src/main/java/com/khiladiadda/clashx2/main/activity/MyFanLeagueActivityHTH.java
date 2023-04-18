package com.khiladiadda.clashx2.main.activity;

import static android.view.View.GONE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashx2.main.adapter.MyFanLeagueAdapterHTH;
import com.khiladiadda.clashx2.cricket.createbattle.CreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattlePresenter;
import com.khiladiadda.clashx2.cricket.createbattle.interfaces.ICreateBattleView;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFanLeagueActivityHTH extends BaseActivity implements ICreateBattleView, MyFanLeagueAdapterHTH.IOnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.btn_live)
    Button mLiveBTN;
    @BindView(R.id.btn_past)
    Button mPastBTN;
    @BindView(R.id.btn_upcoming)
    Button mUpcomingBTN;
    @BindView(R.id.rv_match)
    RecyclerView mMyMatchRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    private ArrayList<HTHResponseDetails> mMatchList = new ArrayList<>();
    private MyFanLeagueAdapterHTH mAdapter;
    private ICreateBattlePresenter mPresenter;
    private boolean mUpcoming, mLive = true, mPast;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_fan_league_hth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHTHNotificationReceiverLiveMatches, new IntentFilter("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY"));
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mLiveBTN.setOnClickListener(this);
        mPastBTN.setOnClickListener(this);
        mUpcomingBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new CreateBattlePresenter(this);
        getType(AppConstant.FROM_FANBATTLE_LIVE);
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getLeguesBattles(mUpcoming, mPast, mLive);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
            case R.id.btn_live:
                getType(AppConstant.FROM_FANBATTLE_LIVE);
                break;
            case R.id.btn_past:
                getType(AppConstant.FROM_FANBATTLE_PAST);
                break;
            case R.id.btn_upcoming:
                getType(AppConstant.FROM_FANBATTLE_UPCOMING);
                break;
        }
    }

    @Override
    public void onJoinComplete(CreateBattleResponse responseModel) {

    }

    @Override
    public void onJoinFailure(ApiError error) {

    }

    @Override
    public void onUpdateComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdateFailure(ApiError error) {

    }

    @Override
    public void onAcceptComplete(CreateBattleResponse responseModel) {

    }

    @Override
    public void onAcceptFailure(ApiError error) {

    }

    @Override
    public void onAllBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onAllBattlesFailure(ApiError error) {

    }

    @Override
    public void onMyBattlesComplete(BattleResponseHTH responseModel) {

    }

    @Override
    public void onMyBattlesFailure(ApiError error) {

    }

    @Override
    public void onFetchLegues(HTHMainResponse responseModel) {
        hideProgress();
        mMatchList.clear();
        List<BannerDetails> bannerDetailsList = responseModel.getBanners();
        List<BannerDetails> bannerShowList = new ArrayList<>();
        if (bannerDetailsList != null && bannerDetailsList.size() > 0) {
            for (int i = 0; i < bannerDetailsList.size(); i++) {
                if (bannerDetailsList.get(i).getDesc().equalsIgnoreCase("12")) {
                    BannerDetails bannerDetails = new BannerDetails();
                    bannerDetails.setImg(bannerDetailsList.get(i).getImg());
                    bannerDetails.setTitle(bannerDetailsList.get(i).getTitle());
                    bannerDetails.setType(bannerDetailsList.get(i).getType());
                    bannerDetails.setLink(bannerDetailsList.get(i).getLink());
                    bannerShowList.add(bannerDetails);
                    setUpAdvertisementViewPager(bannerShowList);
                }
            }
        } else {
            mBannerVP.setVisibility(GONE);
        }
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mNoDataTV.setVisibility(GONE);
            mMatchList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
        } else {
            hideProgress();
            mNoDataTV.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void onFetchLegues(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdatePLayers(BaseResponse response) {

    }

    @Override
    public void onUpdatePLayerError(ApiError error) {

    }

    @Override
    public void onPlayerStatus(HTHMainResponse responseModel) {

    }

    @Override
    public void onPlayerStatusError(ApiError error) {

    }

    private void getType(int type) {
        mMatchList.clear();
        mUpcoming = false;
        mLive = false;
        mPast = false;
        mLiveBTN.setSelected(false);
        mPastBTN.setSelected(false);
        mUpcomingBTN.setSelected(false);
        if (type == AppConstant.FROM_FANBATTLE_LIVE) {
            mLive = true;
            mLiveBTN.setSelected(true);
            mNoDataTV.setText(R.string.text_no_clash_match);
        } else if (type == AppConstant.FROM_FANBATTLE_PAST) {
            mPast = true;
            mPastBTN.setSelected(true);
            mNoDataTV.setText(R.string.past_battles);
        } else if (type == AppConstant.FROM_FANBATTLE_UPCOMING) {
            mUpcoming = true;
            mUpcomingBTN.setSelected(true);
            mNoDataTV.setText(R.string.upcoming_battle);
        }
        setAdapterView();
        getData();
    }

    private void setAdapterView() {
        mAdapter = new MyFanLeagueAdapterHTH(mMatchList, mLive, mUpcoming, mPast);
        mMyMatchRV.setLayoutManager(new LinearLayoutManager(this));
        mMyMatchRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClicked(int position) {
        if (mUpcoming) {
            if (mMatchList.get(position).isLive()) {
                Intent i = new Intent(this, BattlesUpcomingActivity.class);
                i.putExtra(AppConstant.DATA, mMatchList.get(position));
                // i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_UPCOMING);
                i.putExtra(AppConstant.MATCH_TYPE, mMatchList.get(position).getMatch_type());
                startActivityForResult(i, AppConstant.REQUEST_ADD_WALLET);
            } else {
                Snackbar.make(mLiveBTN, R.string.text_contest_open_soon, Snackbar.LENGTH_LONG).show();
            }
        } else if (mLive) {
            Intent i = new Intent(this, BattlesUpcomingActivity.class);
            i.putExtra(AppConstant.DATA, mMatchList.get(position));
            i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_LIVE);
            i.putExtra(AppConstant.MATCH_TYPE, mMatchList.get(position).getMatch_type());
            i.putExtra(AppConstant.LIVEMATCHID, mMatchList.get(position).getId());
            startActivity(i);
        } else if (mPast) {
            if (mMatchList.get(position).isIs_cancelled()) {
                // Snackbar.make(mBackIV, "This Match is cancelled by admin due to some valid reason.Contact Chat Support if you have any issues.", Snackbar.LENGTH_SHORT).show();
                AppUtilityMethods.showMsg(this, "This Match is cancelled by admin due to some valid reason.Contact Chat Support if you have any issues.", true);
            } else {
                Intent i = new Intent(this, BattlesUpcomingActivity.class);
                i.putExtra(AppConstant.DATA, mMatchList.get(position));
                i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
                i.putExtra(AppConstant.MATCH_TYPE, mMatchList.get(position).getMatch_type());
                startActivity(i);
            }
        }
    }

    private BroadcastReceiver mHTHNotificationReceiverLiveMatches = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.HTHLIVEREFRSH)) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    getType(AppConstant.FROM_FANBATTLE_LIVE);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHTHNotificationReceiverLiveMatches);
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getType(AppConstant.FROM_FANBATTLE_LIVE);
        }
    }

}