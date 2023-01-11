package com.khiladiadda.fanbattle;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.BattleActivity;
import com.khiladiadda.fanbattle.adapter.FanBattleAdapter;
import com.khiladiadda.fanbattle.interfaces.IFanBattlePresenter;
import com.khiladiadda.fanbattle.interfaces.IFanBattleView;
import com.khiladiadda.fanleague.MyFanLeagueActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Active;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.GameDetails;
import com.khiladiadda.network.model.response.MatchDetails;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.utility.AllHelpActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class FanBattleActivity extends BaseActivity implements IFanBattleView, IOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_league)
    TextView mLeagueTV;
    @BindView(R.id.tv_home)
    TextView mHomeTV;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.rv_match)
    RecyclerView mMatchRV;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshL;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private int mPostion;

    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    private FanBattleAdapter mMatchAdapter;
    private ArrayList<MatchDetails> mMatchList = new ArrayList<>();
    private IFanBattlePresenter mPresenter;
    private GameDetails mGameDetails;

    @Override
    protected int getContentView() {
        return R.layout.activity_fan_battle;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHomeTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mSwipeRefreshL.setOnRefreshListener(this);
        mLeagueTV.setText(R.string.text_my_match);
    }

    @Override
    protected void initVariables() {
        Bundle intent=getIntent().getExtras();
        if (intent!=null){
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                mAppPreference.setIsDeepLinking(true);
            }
        }
        mPresenter = new FanBattlePresenter(this);
        mMatchAdapter = new FanBattleAdapter(mMatchList);
        mMatchRV.setLayoutManager(new LinearLayoutManager(this));
        mMatchRV.setAdapter(mMatchAdapter);
        mMatchAdapter.setOnItemClickListener(this);
        if (!mAppPreference.getBoolean(AppConstant.FB_CHANGE_GROUP_MSG, false)) {
            showMsg(this, false);
        }
        getData();
    }

    private void getData() {
        mMatchList.clear();
        mMatchAdapter.notifyDataSetChanged();
        if (new NetworkStatus(this).isInternetOn()) {
            mSwipeRefreshL.setRefreshing(true);
            List<Active> games = mAppPreference.getMasterData().getResponse().getGames();
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getTitle().equalsIgnoreCase(AppConstant.FAN_BATTLE)) {
                    mPresenter.getMatchList(games.get(i).getId());
                }
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_home:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_league:
                startActivity(new Intent(this, MyFanLeagueActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }
    }

    @Override
    public void onGetMatchListComplete(MatchResponse responseModel) {
        mMatchList.clear();
        if (responseModel.isStatus()) {
            mMatchList.addAll(responseModel.getResponse());
            mMatchAdapter.notifyDataSetChanged();
            mGameDetails = responseModel.getGameDetails();
            if (responseModel.getBanners().size() > 0) {
                setUpAdvertisementViewPager(responseModel.getBanners());
            } else {
                mBannerVP.setVisibility(GONE);
            }
        }
        mSwipeRefreshL.setRefreshing(false);
    }

    @Override
    public void onGetMatchListFailure(ApiError error) {
        mSwipeRefreshL.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        if (mMatchList.get(position).isLive()) {
            if (mAppPreference.getBoolean(AppConstant.FB_CLASSIC_WORK, false)) {
                Intent i = new Intent(this, BattleActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE);
                i.putExtra(AppConstant.DATA, mMatchList.get(position));
                i.putExtra(AppConstant.BATTLE_CATEGORY, mGameDetails);
                startActivity(i);
                Properties properties = new Properties();
                properties
                        // tracking integer
                        .addAttribute(AppConstant.MatchName, mMatchList.get(position).getSeries().getName())
                        // tracking Date
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.FAN_BATTLE, properties);
            } else {
                mAppPreference.setBoolean(AppConstant.FB_CLASSIC_WORK, true);
                Intent i = new Intent(this, AllHelpActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE);
                i.putExtra(AppConstant.DATA, mMatchList.get(position));
                i.putExtra(AppConstant.BATTLE_CATEGORY, mGameDetails);
                startActivity(i);
            }
        } else {
            Snackbar.make(mLeagueTV, getString(R.string.text_match_open_soon), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        getData();
    }

    public void showMsg(final Context activity, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.challenge_add_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText("Change Group Option");
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        tv_help.setText("FanBattle has a big change in the game.\nIf any players from your combo is not in the Line Ups then admin substitute that player with another balance playing 11 player in Combo and you will see the change button to change the combo and your previous invested amount will get adjusted to the new combo chosen by you.\n\n!!!Thank You!!!");
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            mAppPreference.setBoolean(AppConstant.FB_CHANGE_GROUP_MSG, true);
        });
        dialog.show();
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
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }




}