package com.khiladiadda.ludoTournament.activity;

import static android.view.View.GONE;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.gameleague.adapter.NewDroidoAdapterViewPager;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.ludoTournament.adapter.LudoTmtDashboardAdapter;
import com.khiladiadda.ludoTournament.adapter.ModeTournamentViewPagerAdapter;
import com.khiladiadda.ludoTournament.adapter.MyTournamentViewPagerAdapter;
import com.khiladiadda.ludoTournament.ip.LudoTmtPresenter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtView;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.ludoUniverse.LudoUniverseActivity;
import com.khiladiadda.ludoUniverse.ModeActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.ludoTournament.LudoTournamentFetchRequest;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.khiladiadda.wallet.WalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.util.ArrayList;
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
    TextView rulesImg;
    @BindView(R.id.iv_back_arroww)
    ImageView backArrowIv;
    @BindView(R.id.btn_classic)
    AppCompatButton ClassicBtn;
    @BindView(R.id.btn_series)
    AppCompatButton SeriesBtn;
    @BindView(R.id.btn_timer)
    AppCompatButton TimerBtn;
    @BindView(R.id.cl_mode)
    ConstraintLayout modeCl;
    @BindView(R.id.vv_classic)
    View classicVvl;
    @BindView(R.id.vv_series)
    View seriesVv;
    @BindView(R.id.vv_timer)
    View timerVv;
    @BindView(R.id.tv_download)
    TextView mDownloadTV;
    @BindView(R.id.rl_wallet)
    LinearLayout mWalletLL;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;
    @BindView(R.id.tv_title_name)
    TextView mTitleTv;
    @BindView(R.id.tv_error)
    TextView mMsgTv;
    private MyTournamentViewPagerAdapter mMyTournamentViewPagerAdapter;
    private ModeTournamentViewPagerAdapter mModeTournamentViewPagerAdapter;
    private LudoTmtPresenter mPresenter;
    private int gameMode = 1;
    private Coins mCoins;
    private List<LudoTmtAllTournamentResponse> responses;
    private Dialog mVersionDialog;
    private String mVersion, mLink, mFilePath, mCurrentVersion;
    private boolean mIsRequestingAppInstallPermission;
    private Intent launchIntent;
    private double mTotalWalletBal;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    @BindView(R.id.rl_image)
    RelativeLayout mImageRL;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    private int mDownUp = 1, mTournamentType;
    private long mLastClickTime = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_dashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtNotificationMatchLiveReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));
    }

    @Override
    protected void initViews() {
        mPresenter = new LudoTmtPresenter(this);
        mTitleTv.setText("Classic");
        allTournamentRv.setLayoutManager(new LinearLayoutManager(this));
        TabLayout.Tab firstTab = ludoTmtTL.newTab();
        firstTab.setText(R.string.all_tournament);
        ludoTmtTL.addTab(firstTab);
        TabLayout.Tab secondTab = ludoTmtTL.newTab();
        secondTab.setText(R.string.my_tournaments);
        ludoTmtTL.addTab(secondTab);
        setupTablayout();
        setSubMyTmttabData();
        setupMyTournamentViewPager();
    }

    @Override
    protected void initVariables() {
        rulesImg.setOnClickListener(this);
        backArrowIv.setOnClickListener(this);
        ClassicBtn.setOnClickListener(this);
        SeriesBtn.setOnClickListener(this);
        TimerBtn.setOnClickListener(this);
        mDownloadTV.setOnClickListener(this);
        mWalletLL.setOnClickListener(this);
//        try {
//            mCurrentVersion = mAppPreference.getVersion().getResponse().getLudoAddaVersion();
//        } catch (Exception e) {
//        }
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.img_rules) {
            AppUtilityMethods.showTooltip(this, rulesImg, getString(R.string.english_rules), gameMode);
        } else if (p0.getId() == R.id.img_rules) {
            finish();
        } else if (p0.getId() == R.id.btn_classic) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            gameMode = 1;
            mTitleTv.setText("Classic");
            classicVvl.setVisibility(View.VISIBLE);
            seriesVv.setVisibility(View.GONE);
            timerVv.setVisibility(View.GONE);
            callAllTournamentApi(1);
        } else if (p0.getId() == R.id.btn_series) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            gameMode = 3;
            mTitleTv.setText("Series");
            classicVvl.setVisibility(View.GONE);
            seriesVv.setVisibility(View.VISIBLE);
            timerVv.setVisibility(View.GONE);
            callAllTournamentApi(3);
        } else if (p0.getId() == R.id.btn_timer) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            gameMode = 2;
            mTitleTv.setText("Timer");
            classicVvl.setVisibility(View.GONE);
            seriesVv.setVisibility(View.GONE);
            timerVv.setVisibility(View.VISIBLE);
            callAllTournamentApi(2);
        } else if (p0.getId() == R.id.tv_download) {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        } else if (p0.getId() == R.id.rl_wallet) {
            Intent profile = new Intent(this, WalletActivity.class);
            startActivity(profile);
        } else {
            finish();
        }
    }

    private void callAllTournamentApi(int type) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            String bannerType = "";
            if (type == 1) {
                bannerType = "31";
            } else if (type == 2) {
                bannerType = "33";
            } else if (type == 3) {
                bannerType = "32";
            }
            mPresenter.getAllTournament(true, type, true, bannerType, true);
        } else {
            Snackbar.make(backArrowIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
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
        mTournamentType = 1;
        mMsgTv.setVisibility(GONE);
        allTournamentRv.setVisibility(View.VISIBLE);
        modeCl.setVisibility(View.VISIBLE);
        subMyTmtTl.setVisibility(View.GONE);
        ludotmtVP.setVisibility(View.GONE);
        classicVvl.setVisibility(GONE);
        seriesVv.setVisibility(View.GONE);
        timerVv.setVisibility(View.GONE);
        if (gameMode == 2) {
            mTitleTv.setText("Timer");
            timerVv.setVisibility(View.VISIBLE);
            callAllTournamentApi(2);
        } else if (gameMode == 3) {
            mTitleTv.setText("Series");
            seriesVv.setVisibility(View.VISIBLE);
            callAllTournamentApi(3);
        } else if (gameMode == 1) {
            classicVvl.setVisibility(View.VISIBLE);
            callAllTournamentApi(1);
        } else {
            classicVvl.setVisibility(View.VISIBLE);
            callAllTournamentApi(1);
        }
    }

    private void switchSecondAdapter() {
        mTournamentType = 2;
        mMsgTv.setVisibility(GONE);
        modeCl.setVisibility(View.GONE);
        allTournamentRv.setVisibility(View.GONE);
        subMyTmtTl.setVisibility(View.VISIBLE);
        subMyTmtTl.selectTab(subMyTmtTl.getTabAt(0));
        ludotmtVP.setVisibility(View.VISIBLE);
        setupMyTournamentViewPager();
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

    @Override
    public void onItemClick(int pos) {
        if (mAppPreference.getBoolean("LudoDownload", false)) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                Intent intent = new Intent(this, LudoTmtTounamentActivity.class);
                intent.putExtra("AllLudoTournaments", responses.get(pos));
                intent.putExtra("gameMode", gameMode);
                startActivity(intent);
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        }
    }

    @Override
    public void onInProgressClick() {
        AppDialog.showAlertDialog(this, "Match is in-progress");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onGetAllTournamentComplete(LudoTmtAllTournamentMainResponse response) {
        try {
            if (response.isStatus()) {
                mLink = response.getLudoApkLink();
                mCurrentVersion = response.getApkVersion();
                responses = response.getResponse();
                setData(response);
                List<BannerDetails> bannerData = response.getBanner();
                if (bannerData != null && bannerData.size() > 0) {
                    mImageRL.setVisibility(View.VISIBLE);
                    setUpAdvertisementViewPager(bannerData);
                } else {
                    mImageRL.setVisibility(GONE);
                }
                if (response.getResponse().size() > 0) {
                    mMsgTv.setVisibility(GONE);
                    allTournamentRv.setAdapter(new LudoTmtDashboardAdapter(this, this, response.getResponse()));
                } else {
                    if (mTournamentType == 1) {
                        mMsgTv.setVisibility(View.VISIBLE);
                    }
                    allTournamentRv.setAdapter(new LudoTmtDashboardAdapter(this, this, response.getResponse()));
                }
            } else {
                AppUtilityMethods.showMsg(this, response.getMessage(), false);
            }
            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        apkCheck();
    }

    @Override
    public void onGetAllTournamentFailure(ApiError errorMsg) {
        hideProgress();
    }

    private void setData(LudoTmtAllTournamentMainResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getProfile());
        mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }
    }

    //DOWNLOAD
    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ludoadda_download_popup);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_download);
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
        TextView tvMsg = dialog.findViewById(R.id.textView9);
        if (mDownUp == 2) {
            tvMsg.setText("It seem like you haven't updated our Ludo Adda game to play contests, So please click on download button to update the game.");
        }
        ivCross.setOnClickListener(view -> {
            dialog.dismiss();
        });
        iv_playstore.setOnClickListener(view -> {
            if (listener != null) {
                progressBar.setVisibility(View.VISIBLE);
                listener.onDownloadVersion();
                iv_playstore.setVisibility(View.GONE);
                ivCross.setVisibility(View.GONE);
            }
        });
        dialog.show();
        return dialog;
    }


    private final IOnVesrionDownloadListener mOnVersionListener = new IOnVesrionDownloadListener() {
        @Override
        public void onDownloadVersion() {
            if (mLink != null && !mLink.isEmpty()) {
                new DownloadApk(mOnFileDownloadedListener).execute(mLink);
            }
        }
    };

    private final IOnFileDownloadedListener mOnFileDownloadedListener = new IOnFileDownloadedListener() {
        @Override
        public void onFileDownloaded(String filePath) {
            AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
            ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
            TextView tv = mVersionDialog.findViewById(R.id.textView9);
            tv.setText("Hey You have Successfully downloaded the Ludo Adda game, Now please click on install button to continue.");
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText("Install Now");
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("LudoDownload", true);
                } catch (Exception e) {
                    Toast.makeText(LudoTmtDashboardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
                progressBar.setProgress(progress);
            }
        }
    };

    private void installApk(String filePath) {
        if (Build.VERSION.SDK_INT >= 26 && !LudoTmtDashboardActivity.this.getPackageManager().canRequestPackageInstalls()) {
            startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:com.khiladiadda")));
            mIsRequestingAppInstallPermission = true;
            mFilePath = filePath;
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(new File(filePath));
        } else {
            uri = FileProvider.getUriForFile(LudoTmtDashboardActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mFilePath = null;
        mIsRequestingAppInstallPermission = false;
    }

    private void getVersion() {
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.LudoAddaPackageName, 0);
            mVersion = pInfo.versionName;
            mAppPreference.setBoolean("LudoDownload", true);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void apkCheck() {
        if (launchIntent != null) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                mDownloadTV.setVisibility(View.GONE);
                mWalletLL.setVisibility(View.VISIBLE);
            } else {
                mWalletLL.setVisibility(View.GONE);
                mDownloadTV.setText("Update");
                mDownUp = 2;
            }
        } else {
            mDownloadTV.setVisibility(View.VISIBLE);
        }
        if (mLink != null) {
            mAppPreference.setString("LudoVersion", mCurrentVersion);
            mAppPreference.setString("mLudoLink", mLink);
            Properties properties = new Properties();
            properties.addAttribute("LudoADDAVersion", mCurrentVersion);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "LudoADDA", properties);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ludoTmtTL.selectTab(ludoTmtTL.getTabAt(0));
//        switchFirstAdapter();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        else mAppPreference.setBoolean("LudoDownload", false);
        apkCheck();
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
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

    //Notification
    private final BroadcastReceiver mLudoTmtNotificationMatchLiveReceiver = new BroadcastReceiver() { //72
        @Override
        public void onReceive(Context context, Intent intent) { // 72
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_MATCH_LIVE)) {
                showLiveDialog(context, "Tournament is Live Now");
            }
        }
    };

    //Match Live Dialog
    public void showLiveDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.error_dialog);
        TextView text = dialog.findViewById(R.id.tv_text);
        ImageView image = dialog.findViewById(R.id.iv_tick);
        TextView heading = dialog.findViewById(R.id.tv_heading);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            ludoTmtTL.selectTab(ludoTmtTL.getTabAt(1));
            subMyTmtTl.selectTab(subMyTmtTl.getTabAt(0));
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}