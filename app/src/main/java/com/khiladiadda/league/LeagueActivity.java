package com.khiladiadda.league;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.league.adapter.LeagueListAdapter;
import com.khiladiadda.league.details.LeagueDetailsActivity;
import com.khiladiadda.league.interfaces.ILeagueListPresenter;
import com.khiladiadda.league.interfaces.ILeagueListView;
import com.khiladiadda.league.leaguehelp.LeagueHelpActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Active;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.GameCategory;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LeagueActivity extends BaseActivity implements ILeagueListView, IOnItemClickListener {

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
    @BindView(R.id.rv_league)
    RecyclerView mRV;
    @BindView(R.id.btn_solo)
    TextView mSoloBTN;
    @BindView(R.id.btn_duo)
    TextView mDuoBTN;
    @BindView(R.id.btn_squad)
    TextView mSquadBTN;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;

    private ILeagueListPresenter mPresenter;
    private LeagueListAdapter mLeagueAdapter;
    private List<LeagueListDetails> mLeagueList;
    private String mGameId;
    private boolean mIsCategorySolo = true;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    Handler handler = new Handler();
    private String mType, mGame;
    private int mBannerType;
    @BindView(R.id.nudge)
    NudgeView mNV;

    @Override
    protected int getContentView() {
        return R.layout.activity_league;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        mHomeTV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mSoloBTN.setOnClickListener(this);
        mDuoBTN.setOnClickListener(this);
        mSquadBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new LeagueListPresenter(this);
        setData();
        mLeagueList = new ArrayList<>();
        mLeagueAdapter = new LeagueListAdapter(mLeagueList, mGameId);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(mLeagueAdapter);
        mLeagueAdapter.setOnItemClickListener(this);
        String mCategoryType = getIntent().getStringExtra(AppConstant.CATEGORY);
        if (mCategoryType != null) {
            if (mCategoryType.equalsIgnoreCase(AppConstant.SOLO)) {
                getCategory(AppConstant.SOLO);
            } else if (mCategoryType.equalsIgnoreCase(AppConstant.DUO)) {
                getCategory(AppConstant.DUO);
            } else if (mCategoryType.equalsIgnoreCase(AppConstant.SQUAD)) {
                getCategory(AppConstant.SQUAD);
            }
        } else {
            getCategory(AppConstant.SOLO);
        }
    }

    private void setData() {
        String mFromGame = getIntent().getStringExtra(AppConstant.FROM);
        if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_FREEFIRE)) {
            mGameId = mAppPreference.getString(AppConstant.FREEFIRE_ID, "");
            mGame = AppConstant.FROM_VIEW_FREEFIRE;
            mBannerType = 11;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_FF_MAX)) {
            mGameId = mAppPreference.getString(AppConstant.FF_MAX_ID, "");
            mGame = AppConstant.FROM_VIEW_FF_MAX;
            mBannerType = 12;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_FF_CLASH)) {
            mGameId = mAppPreference.getString(AppConstant.FF_CLASH_ID, "");
            mGame = AppConstant.FROM_VIEW_FF_CLASH;
            mBannerType = 13;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_TDM)) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_ID, "");
            mGame = AppConstant.FROM_VIEW_TDM;
            mBannerType = 14;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_BGMI)) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_LITE_ID, "");
            mGame = AppConstant.FROM_VIEW_BGMI;
            mBannerType = 15;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_GLOBAL)) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, "");
            mGame = AppConstant.FROM_VIEW_PUBG_GLOBAL;
            mBannerType = 16;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_PREMIUM_ESPORTS)) { // Valorant
            mGameId = mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, "");
            mGame = AppConstant.FROM_VIEW_PREMIUM_ESPORTS;
            mBannerType = 17;
        } else if (mFromGame.equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_NEWSTATE)) {
            mGameId = mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, "");
            mGame = AppConstant.FROM_VIEW_PUBG_NEWSTATE;
            mBannerType = 18;
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
                openMyLeague();
                break;
            case R.id.tv_help:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                break;
            case R.id.btn_solo:
                getCategory(AppConstant.SOLO);
                break;
            case R.id.btn_duo:
                getCategory(AppConstant.DUO);
                break;
            case R.id.btn_squad:
                getCategory(AppConstant.SQUAD);
                break;
        }
    }

    private void getData(String mCategoryId) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getGameDetails(mCategoryId, mBannerType);
        } else {
            Snackbar.make(mDuoBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getCategory(String mFromCategory) {
        mSoloBTN.setSelected(false);
        mDuoBTN.setSelected(false);
        mSquadBTN.setSelected(false);
        mIsCategorySolo = false;
        if (mFromCategory.equalsIgnoreCase(AppConstant.SOLO)) {
            mIsCategorySolo = true;
            mSoloBTN.setSelected(true);
            mType = AppConstant.SOLO;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.DUO)) {
            mDuoBTN.setSelected(true);
            mType = AppConstant.DUO;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.SQUAD)) {
            mSquadBTN.setSelected(true);
            mType = AppConstant.SQUAD;
        }
        String categoryId = "";
        List<Active> gameList = mAppPreference.getMasterData().getResponse().getGames();
        for (Active game : gameList) {
            if (mGameId.equalsIgnoreCase(game.getId())) {
                for (GameCategory category : game.getCategories()) {
                    if (mFromCategory.equalsIgnoreCase(category.getTitle())) {
                        categoryId = category.getId();
                        break;
                    }
                }
            }
        }
        getData(categoryId);
    }

    @Override
    public void onGameComplete(LeagueListReponse responseModel) {
        mLeagueList.clear();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().size() > 0) {
                mLeagueList.addAll(responseModel.getResponse());
                mNoDataTV.setVisibility(View.GONE);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
            }
            List<BannerDetails> banners = responseModel.getBanners();
            if (banners != null && banners.size() > 0) {
                setUpAdvertisementViewPager(banners);
            } else {
                mBannerVP.setVisibility(GONE);
            }
            mLeagueAdapter.notifyDataSetChanged();
            hideProgress();
        } else {
            hideProgress();
            Snackbar.make(mDuoBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        if (view.getId() == R.id.cv_upcoming) {
            LeagueListDetails detail = mLeagueList.get(position);
            Intent details;
            if (!mIsCategorySolo && !mAppPreference.getBoolean(AppConstant.LEAGUE_CREATE_JOIN_HELP, false)) {
                details = new Intent(this, LeagueHelpActivity.class);
            } else {
                details = new Intent(this, LeagueDetailsActivity.class);
            }
            details.putExtra(AppConstant.FROM, AppConstant.LEAGUE);
            details.putExtra(AppConstant.DATA, detail);
            details.putExtra("type", mType);
            details.putExtra("game", mGame);
            startActivity(details);
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

    private void openMyLeague() {
        Intent view = new Intent(this, MyLeagueActivity.class);
        if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESTDM);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESBGMI);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFF);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFCS);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESFFMAX);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEAGUESPG);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUESPE);
        } else if (mGameId.equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            view.putExtra(AppConstant.FROM, AppConstant.FROM_APPFLYER_FORMYLEGUEPGNS);
        }
        startActivity(view);
    }

    Runnable runnable = this::showVideoHelp;

    private void showVideoHelp() {
        AppUtilityMethods.showCODVideoMsg(this, false);
    }

    @Override
    public void onBackPressed() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        AppUtilityMethods.deleteCache(this);
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}