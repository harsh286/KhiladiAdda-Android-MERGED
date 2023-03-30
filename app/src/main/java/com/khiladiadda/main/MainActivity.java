package com.khiladiadda.main;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.depositelimit.DepositLimitActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnNetworkErrorListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.leaderboard.NewLeaderboardActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.main.game.GameFragment;
import com.khiladiadda.main.interfaces.IMainPresenter;
import com.khiladiadda.main.interfaces.IMainView;
import com.khiladiadda.myfacts.MyFacts;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.UpdateFavouriteRequest;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.DashboardResponse;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.profile.StatActivity;
import com.khiladiadda.quiz.list.QuizListActivity;
import com.khiladiadda.referhistory.ReferHelpActivity;
import com.khiladiadda.rewards.RewardsActivity;
import com.khiladiadda.setting.SettingActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.WalletActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    @BindView(R.id.iv_menu)
    ImageView mMenuIV;
    @BindView(R.id.rl_wallet)
    LinearLayout mWalletRV;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;
    @BindView(R.id.actionbadge)
    FrameLayout frameLayout;

    private TextView mUsername, mEmail, notificationCountTV;
    private ImageView mProfileURL;
    private LinearLayout mProfileLL;
    private int backPressCount;
    private FrameLayout notificationCircleFL;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    private IMainPresenter mPresenter;
    private Fragment mFragment;
    private boolean mIsDataSync = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mMenuIV.setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = mNavigationView.getHeaderView(0);
        mProfileLL = view.findViewById(R.id.ll_profile);
        mProfileURL = view.findViewById(R.id.iv_profile);
        mUsername = view.findViewById(R.id.tv_name);
        mEmail = view.findViewById(R.id.tv_email);
        int from = getIntent().getIntExtra(AppConstant.FROM, -1);
        if (from == AppConstant.FROM_NOTIFICATION) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.putExtra(AppConstant.FROM, AppConstant.FROM_NOTIFICATION);
//            startActivityForResult(intent, AppConstant.REQUEST_NOTIFICATION);
            notificationActivityResultLauncher.launch(intent);
        } else {
            setViewPagerPage();
        }
        mWalletRV.setOnClickListener(this);
        frameLayout.setOnClickListener(this);
        notification();
    }

    @Override
    protected void initVariables() {
        mPresenter = new MainPresenter(this);
        mProfileLL.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backStackEntryCount > 0) {
            String title = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName();
            getSupportActionBar().setTitle(getString(R.string.my_dashboard));
            getSupportFragmentManager().popBackStackImmediate(new Fragment().getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (backPressCount == 0) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.press_again_exit), Snackbar.LENGTH_LONG).show();
            backPressCount++;
        } else {
            mAppPreference.setIsDeepLinking(false);
            super.onBackPressed();
        }
    }

    private void updateNotification(int count) {
        notificationCircleFL.setVisibility((count > 0) ? VISIBLE : GONE);
        if (count > 0) {
            notificationCountTV.setText(String.valueOf(count));
        } else {
            notificationCountTV.setText("");
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        backPressCount = 0;
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                setViewPagerPage();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(this, StatActivity.class));
                break;
            case R.id.nav_wallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;
            case R.id.nav_leaderboard:
                Intent leaderboard = new Intent(this, NewLeaderboardActivity.class);
                leaderboard.putExtra(AppConstant.FROM, AppConstant.FROM_MAIN);
                startActivity(leaderboard);
                break;
            case R.id.nav_earn_free_coins:
                Intent quiz = new Intent(this, QuizListActivity.class);
                quiz.putExtra(AppConstant.FROM, AppConstant.FROM_MAIN);
                startActivity(quiz);
                break;
            case R.id.nav_rewards:
                startActivity(new Intent(this, RewardsActivity.class));
                break;
            case R.id.nav_referrals:
                startActivity(new Intent(this, ReferHelpActivity.class));
                break;
            case R.id.nav_facts:
                startActivity(new Intent(this, MyFacts.class));
                break;
            case R.id.nav_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.nav_deposit:
                Intent deposit = new Intent(this, DepositLimitActivity.class);
                startActivity(deposit);
                break;
            case R.id.nav_setting:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                break;
            case R.id.nav_responsible_gaming:
                Intent responsibleGaming = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.khiladiadda.com/responsible-gaming/"));
                startActivity(responsibleGaming);
                break;
            case R.id.nav_tutorial:
                AppUtilityMethods.openTutorial(this);
                break;
            case R.id.nav_telegram:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/s/Khiladiadda")));
                break;
            case R.id.nav_instagram:
                AppUtilityMethods.openInstagram(this);
                break;
            case R.id.nav_youtube:
                AppUtilityMethods.openYoutube(this);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_profile) {
            backPressCount = 0;
            Intent profile = new Intent(this, ProfileActivity.class);
            startActivity(profile);
        } else if (v.getId() == R.id.iv_menu) {
            mDrawer.openDrawer(GravityCompat.START);
        } else if (v.getId() == R.id.rl_wallet) {
            Intent profile = new Intent(this, WalletActivity.class);
            startActivity(profile);
        } else if (v.getId() == R.id.actionbadge) {
            mAppPreference.setNotificationCount(0);
            updateNotification(0);
            notificationActivityResultLauncher.launch(new Intent(MainActivity.this, NotificationActivity.class));
        }
    }

    private void notification() {
        notificationCircleFL = frameLayout.findViewById(R.id.view_alert_red_circle);
        notificationCountTV = frameLayout.findViewById(R.id.tv_count_notification);
        updateNotification(mAppPreference.getNotificationCount());
    }

    private void setProfileData() {
        if (mAppPreference.getIsVersionUpdated()) {
            showVersionDialog();
        } else {
            if (!TextUtils.isEmpty(mAppPreference.getEmail())) {
                mEmail.setText(mAppPreference.getEmail());
            }
            if (!TextUtils.isEmpty(mAppPreference.getName())) {
                mUsername.setText(mAppPreference.getName());
            }
            if (!TextUtils.isEmpty(mAppPreference.getUrl())) {
                Glide.with(this).load(mAppPreference.getUrl()).thumbnail(Glide.with(this).load(mAppPreference.getUrl())).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mProfileURL);
            }
            setCoins();
//            if (!mAppPreference.getBoolean(AppConstant.IS_FAVOURITE, false)) {
//                mIsDataSync = false;
//                favouriteActivityResultLauncher.launch(new Intent(this, FavouriteActivity.class));
//            }
            getData();
        }
    }

    private void setCoins() {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }
    }

    private void getData() {
        showProgress(getString(R.string.txt_progress_authentication));
        if (new NetworkStatus(this).isInternetOn()) {
            mPresenter.getData();
        } else {
            AppDialog.showNetworkErrorDialog(this, onNetworkErrorListener);
        }
    }

    private IOnNetworkErrorListener onNetworkErrorListener = this::getData;

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
    protected void onResume() {
        super.onResume();
        setCoins();
        if (mIsDataSync) {
            setProfileData();
        }
        if (mAppPreference.getBoolean(AppConstant.IS_QUIZ_PLAYED, false)) {
            getData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setViewPagerPage() {
        if (mFragment == null) {
            mFragment = GameFragment.getInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }

    @Override
    public void onTrendingQuizComplete(DashboardResponse responseModel) {
        mAppPreference.setDashboardData(responseModel.getResponse());
        if (mFragment != null && ((mAppPreference.getDashboardData().getTopUsers().size() > 0) || mAppPreference.getDashboardData().getTrendingQuiz().size() > 0)) {
            ((GameFragment) mFragment).setUpAdvertisement();
        }
        List<BannerDetails> bannerData = responseModel.getResponse().getBanners();
        if (bannerData != null && bannerData.size() > 0) {
            setUpAdvertisementViewPager(bannerData);
        } else {
            mBannerVP.setVisibility(GONE);
        }
        mIsDataSync = false;
        mAppPreference.setBoolean(AppConstant.IS_QUIZ_PLAYED, false);
        if ((mAppPreference.getDashboardData().getDialogueBanner() != null) && (!mAppPreference.getDashboardData().getDialogueBanner().isDeleted())) {
            AppDialog.showTrendingDialog(this);
        }
        hideProgress();
    }

    @Override
    public void onTrendingQuizFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdateFavouriteComplete(BaseResponse responseModel) {
        hideProgress();
        if ((mAppPreference.getDashboardData().getDialogueBanner() != null) && (!mAppPreference.getDashboardData().getDialogueBanner().isDeleted())) {
            AppDialog.showTrendingDialog(this);
        }
    }

    @Override
    public void onUpdateFavouriteFailure(ApiError error) {
        hideProgress();

    }

    ActivityResultLauncher<Intent> notificationActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                setViewPagerPage();
            });

    ActivityResultLauncher<Intent> favouriteActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppConstant.FROM_FAVOURITE) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    List<Integer> favourites = new ArrayList<>();
                    assert result.getData() != null;
                    boolean league = result.getData().getBooleanExtra("league", false);
                    boolean quiz = result.getData().getBooleanExtra("quiz", false);
                    boolean cricket = result.getData().getBooleanExtra("cricket", false);
                    boolean sports = result.getData().getBooleanExtra("sports", false);
                    if (sports) {
                        favourites.add(1);
                    }
                    if (cricket) {
                        favourites.add(2);
                    }
                    if (league) {
                        favourites.add(3);
                    }
                    if (quiz) {
                        favourites.add(4);
                    }
                    UpdateFavouriteRequest request = new UpdateFavouriteRequest();
                    request.setFavourites(favourites);
                    mAppPreference.setBoolean(AppConstant.IS_FAVOURITE, true);
                    mPresenter.updateFavourite(request);
                }
            });
}