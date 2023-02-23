package com.khiladiadda.ludoUniverse;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniversePresenter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniverseView;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ModeResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ModeActivity extends BaseActivity implements ILudoUniverseView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_download)
    TextView mDownloadTV;
    @BindView(R.id.tv_help_video)
    TextView mHelpVideoTV;
    @BindView(R.id.btn_classic_mode)
    Button mClassicModeBTN;
    @BindView(R.id.btn_series_mode)
    Button mSeriesModeBTN;
    @BindView(R.id.btn_timer_mode)
    Button mTimerModeBTN;
    @BindView(R.id.tv_play)
    TextView mPlayTV;
    @BindView(R.id.ll_mode)
    LinearLayout mModeLL;
    @BindView(R.id.ll_rules)
    LinearLayout mRuleLL;
    @BindView(R.id.tv_ludo_enable)
    TextView mLudoEnableTV;
    @BindView(R.id.tv_rules)
    TextView rulesTv;

    private Dialog mVersionDialog;
    private String mVersion, mLink, mFilePath, mCurrentVersion;
    private boolean mIsRequestingAppInstallPermission;
    private ILudoUniversePresenter mPresenter;
    private Intent launchIntent;
    private int mFrom = 1;
    private List<BannerDetails> bannerData = new ArrayList<>();


    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;

    @Override
    protected int getContentView() {
        return R.layout.activity_mode;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText("Classic");
        mBackIV.setOnClickListener(this);
        mDownloadTV.setOnClickListener(this);
        mHelpVideoTV.setOnClickListener(this);
        mClassicModeBTN.setOnClickListener(this);
        mSeriesModeBTN.setOnClickListener(this);
        mTimerModeBTN.setOnClickListener(this);
        mPlayTV.setOnClickListener(this);
        doSelection(true, false, false);
        rulesTv.setMovementMethod(new ScrollingMovementMethod());
        rulesTv.setText(AppConstant.ADDA_CLASSIC_RULES);
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        else mAppPreference.setBoolean("LudoDownload", false);
    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoUniversePresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.tv_download:
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                break;
            case R.id.tv_help_video:
                AppUtilityMethods.openLudoVideo(ModeActivity.this);
                break;
            case R.id.btn_classic_mode:
                mFrom = 1;
                mPlayTV.setText(R.string.play_classic);
                doSelection(true, false, false);
                mActivityNameTV.setText("Classic");
                rulesTv.setText(AppConstant.ADDA_CLASSIC_RULES);
                fetchBanner();
                break;
            case R.id.btn_timer_mode:
                mFrom = 2;
                mPlayTV.setText(R.string.play_timer);
                doSelection(false, true, false);
                mActivityNameTV.setText("Timer");
                rulesTv.setText(AppConstant.ADDA_TIMER_RULES);
                fetchBanner();
                break;
            case R.id.btn_series_mode:
                mFrom = 3;
                mPlayTV.setText(R.string.play_series);
                doSelection(false, false, true);
                mActivityNameTV.setText("Series");
                rulesTv.setText(AppConstant.ADDA_SERIES_RULES);
                fetchBanner();
                break;
            case R.id.tv_play:
                if (mAppPreference.getBoolean("LudoDownload", false)) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        Intent a = new Intent(this, LudoUniverseActivity.class);
                        if (mFrom == 1) {
                            a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_DAILY);
                        } else if (mFrom == 2) {
                            a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_MONTHLY);
                        } else {
                            a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_WEEKLY);
                        }
                        a.putParcelableArrayListExtra("banner", (ArrayList<? extends Parcelable>) bannerData);
                        startActivity(a);
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
                break;
        }
    }

    private void doSelection(boolean classic, boolean timer, boolean series) {
        mClassicModeBTN.setBackgroundResource(R.drawable.bg_btn_classic);
        mTimerModeBTN.setBackgroundResource(R.drawable.bg_btn_timer);
        mSeriesModeBTN.setBackgroundResource(R.drawable.bg_btn_series);
        mClassicModeBTN.setTextColor(Color.parseColor("#ffffff"));
        mTimerModeBTN.setTextColor(Color.parseColor("#ffffff"));
        mSeriesModeBTN.setTextColor(Color.parseColor("#ffffff"));
        if (classic) {
            mClassicModeBTN.setBackgroundResource(R.drawable.ic_selectable);
            mClassicModeBTN.setTextColor(Color.parseColor("#000000"));
        } else if (timer) {
            mTimerModeBTN.setBackgroundResource(R.drawable.ic_selectable);
            mTimerModeBTN.setTextColor(Color.parseColor("#000000"));
        } else if (series) {
            mSeriesModeBTN.setBackgroundResource(R.drawable.ic_selectable);
            mSeriesModeBTN.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public void onGetContestSuccess(LudoContestResponse responseModel) {

    }

    @Override
    public void onGetContestFailure(ApiError error) {

    }

    @Override
    public void addChallengeSuccess(BaseResponse response) {

    }

    @Override
    public void addChallengeFailure(ApiError error) {

    }

    @Override
    public void acceptContestSuccess(BaseResponse response) {

    }

    @Override
    public void acceptContestFailure(ApiError error) {

    }

    @Override
    public void cancelContestSuccess(BaseResponse response) {

    }

    @Override
    public void cancelContestFailure(ApiError error) {

    }

    @Override
    public void JoinedContestStatusSuccess(BaseResponse response) {

    }

    @Override
    public void JoinedContestStatusFailure(ApiError error) {

    }

    @Override
    public void onGetModeSuccess(ModeResponse responseModel) {
        hideProgress();
        if (!responseModel.getResponse().getConfig().getLudoAddaConfig().getIsEnabled()) {
            mLudoEnableTV.setVisibility(View.VISIBLE);
            mModeLL.setVisibility(View.GONE);
            mRuleLL.setVisibility(View.GONE);
            mPlayTV.setVisibility(View.GONE);
            mDownloadTV.setVisibility(View.GONE);
            mHelpVideoTV.setVisibility(View.GONE);
        } else {
            mCurrentVersion = responseModel.getMetaInfo().getApk_version();
            mLink = responseModel.getMetaInfo().getLudoApkLink();
            apkCheck();
            bannerData = responseModel.getResponse().getBanners();
            if (bannerData != null && bannerData.size() > 0) {
                mBannerVP.setVisibility(View.VISIBLE);
                setUpAdvertisementViewPager(bannerData);
            } else {
                mBannerVP.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onGetModeFailure(ApiError error) {
        hideProgress();
    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ludoadda_download_popup);
        TextView tv = dialog.findViewById(R.id.textView9);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_download);
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
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
                    Toast.makeText(ModeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        if (Build.VERSION.SDK_INT >= 26 && !ModeActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(ModeActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        else mAppPreference.setBoolean("LudoDownload", false);
        fetchBanner();
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }
    }

    private void fetchBanner() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom == 2) {
                mPresenter.getMode("23");
            } else if (mFrom == 3) {
                mPresenter.getMode("22");
            } else
                mPresenter.getMode("21");
        } else {
            Snackbar.make(mPlayTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void apkCheck() {
        if (launchIntent != null) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                mDownloadTV.setVisibility(View.GONE);
                mAppPreference.setBoolean("LudoDownload", true);
            } else {
                mDownloadTV.setText("Update");
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