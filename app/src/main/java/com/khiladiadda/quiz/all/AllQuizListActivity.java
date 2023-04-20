package com.khiladiadda.quiz.all;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.category.CategoryPresenter;
import com.khiladiadda.main.category.adapter.QuizTrendingRVAdapter;
import com.khiladiadda.main.category.interfaces.ICategoryPresenter;
import com.khiladiadda.main.category.interfaces.ICategoryView;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.quiz.QuizDetailsActivity;
import com.khiladiadda.quiz.list.QuizListActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.WalletActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllQuizListActivity extends BaseActivity implements ICategoryView, IOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_picture)
    TextView mPicturesLL;
    @BindView(R.id.tv_gaming)
    TextView mGamingLL;
    @BindView(R.id.tv_web)
    TextView mWebLL;
    @BindView(R.id.tv_logo)
    TextView mLogoLL;
    @BindView(R.id.tv_math)
    TextView mMathLL;
    @BindView(R.id.tv_gk)
    TextView mGkLL;
    @BindView(R.id.tv_sports)
    TextView mSportsLL;
    @BindView(R.id.tv_technology)
    TextView mTechnologyLL;
    @BindView(R.id.tv_science)
    TextView mScienceLL;
    @BindView(R.id.tv_movie)
    TextView mMoviesLL;
    @BindView(R.id.rv_quiz)
    RecyclerView mTrendingQuizRV;
    @BindView(R.id.btn_category)
    Button mCategoryBTN;
    @BindView(R.id.ll_bottom_sheet_category)
    LinearLayout mBottomSheetCategoryLL;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshL;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;

    private ICategoryPresenter mPresenter;
    private QuizTrendingRVAdapter mTrendingAdapter;
    private List<QuizListDetails> mTrendingList = null;
    private BottomSheetBehavior mBottomSheetBehavior;

    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_quiz;
    }

    @Override
    protected void initViews() {
        showProgress("");
        Bundle intent = getIntent().getExtras();
        if (intent!=null){
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                mAppPreference.setIsDeepLinking(true);
            }
        }

        mActivityNameTV.setText("Quizzes");
        mPresenter = new CategoryPresenter(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mPicturesLL.setOnClickListener(this);
        mGamingLL.setOnClickListener(this);
        mWebLL.setOnClickListener(this);
        mLogoLL.setOnClickListener(this);
        mMathLL.setOnClickListener(this);
        mGkLL.setOnClickListener(this);
        mSportsLL.setOnClickListener(this);
        mTechnologyLL.setOnClickListener(this);
        mScienceLL.setOnClickListener(this);
        mMoviesLL.setOnClickListener(this);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetCategoryLL);
        mBottomSheetBehavior.addBottomSheetCallback(mBottomSheetCallback);
        mCategoryBTN.setOnClickListener(mCategoryToggleClickListener);
        mSwipeRefreshL.setOnRefreshListener(this);

    }

    @Override
    protected void initVariables() {
        mTrendingList = new ArrayList<>();
        mTrendingAdapter = new QuizTrendingRVAdapter(this, mTrendingList);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mTrendingQuizRV.setLayoutManager(manager);
        mTrendingQuizRV.setAdapter(mTrendingAdapter);
        mTrendingAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String from;
        String id;
        switch (v.getId()) {
            case R.id.iv_back:
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
            case R.id.rl_wallet:
                Intent profile = new Intent(this, WalletActivity.class);
                startActivity(profile);
                break;
            case R.id.tv_picture:
                from = AppConstant.FROM_PICTURE;
                id = mAppPreference.getPicture();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_gaming:
                from = AppConstant.FROM_GAMING;
                id = mAppPreference.getGaming();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_web:
                from = AppConstant.FROM_WEB;
                id = mAppPreference.getWebSeries();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_logo:
                from = AppConstant.FROM_LOGO;
                id = mAppPreference.getLogo();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_math:
                from = AppConstant.FROM_MATH;
                id = mAppPreference.getMath();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_gk:
                from = AppConstant.FROM_GK;
                id = mAppPreference.getGK();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_sports:
                from = AppConstant.FROM_SPORTS;
                id = mAppPreference.getSports();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_technology:
                from = AppConstant.FROM_TECHNOLOGY;
                id = mAppPreference.getTechnology();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_science:
                from = AppConstant.FROM_SCIENCE;
                id = mAppPreference.getScience();
                launchQuizActivity(from, id);
                break;
            case R.id.tv_movie:
                from = AppConstant.FROM_MOVIES;
                id = mAppPreference.getMovie();
                launchQuizActivity(from, id);
                break;
        }
    }

    @Override
    public void onTrendingQuizComplete(TrendinQuizResponse responseModel) {
        hideProgress();
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 5000);
        mAppPreference.setBoolean(AppConstant.IS_QUIZ_PLAYED, false);
        mSwipeRefreshL.setRefreshing(false);
        mTrendingList.clear();
        if (responseModel.getResponse().size() > 0) {
            mTrendingList.addAll(responseModel.getResponse());
            mTrendingAdapter.notifyDataSetChanged();
        }
        List<BannerDetails> bannerData = responseModel.getBanner();
        if (bannerData != null && bannerData.size() > 0) {
            mBannerVP.setVisibility(View.VISIBLE);
            setUpAdvertisementViewPager(bannerData);
        } else {
            mBannerVP.setVisibility(GONE);
        }
        setData();
    }

    @Override
    public void onTrendingQuizFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent details = new Intent(this, QuizDetailsActivity.class);
        details.putExtra(AppConstant.DATA, mTrendingList.get(position));
        details.putExtra(AppConstant.FROM, AppConstant.TRENDING);
        startActivity(details);
    }

    private void launchQuizActivity(String from, String id) {
        Intent i = new Intent(this, QuizListActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.CATEGORY);
        i.putExtra(AppConstant.CATEGORY, from);
        i.putExtra(AppConstant.ID, id);
        startActivity(i);
    }

    private final View.OnClickListener mCategoryToggleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
    };

    private void onCategoryExpanded() {
        //mCategoryBTN.setBackgroundResource(R.color.colorPrimaryDark);
        mCategoryBTN.setBackgroundResource(R.color.colorPrimary);
        mCategoryBTN.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_drop_down_white_, 0, 0);
    }

    private void onCategoryCollapsed() {
        mCategoryBTN.setBackgroundResource(R.color.colorPrimary);
        mCategoryBTN.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_drop_up_white, 0, 0);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                onCategoryExpanded();
            } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                onCategoryCollapsed();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        getData();
    }

    private void setData() {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            mPresenter.getTrendingQuiz();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtilityMethods.deleteCache(this);
        getData();
    }

    @Override
    public void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
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