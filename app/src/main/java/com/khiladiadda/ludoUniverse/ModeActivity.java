package com.khiladiadda.ludoUniverse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;

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
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
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
    @BindView(R.id.iv_announcement)
    ImageView mAnnouncementIV;
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

    private Dialog mVersionDialog;
    private String mVersion, mLink, mFilePath, mCurrentVersion;
    private boolean mIsRequestingAppInstallPermission;
    private ILudoUniversePresenter mPresenter;
    private Intent launchIntent;
    private int mFrom = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_mode;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_ludo_adda);
        mBackIV.setOnClickListener(this);
        mDownloadTV.setOnClickListener(this);
        mHelpVideoTV.setOnClickListener(this);
        mClassicModeBTN.setOnClickListener(this);
        mSeriesModeBTN.setOnClickListener(this);
        mTimerModeBTN.setOnClickListener(this);
        mPlayTV.setOnClickListener(this);
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        mAppPreference.setBoolean("LudoDownload", false);
        if (launchIntent != null) getVersion();
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
                break;
            case R.id.btn_timer_mode:
                mFrom = 2;
                mPlayTV.setText(R.string.play_timer);
                break;
            case R.id.btn_series_mode:
                mFrom = 3;
                mPlayTV.setText(R.string.play_series);
                break;
            case R.id.tv_play:
                Intent a = new Intent(this, LudoUniverseActivity.class);
                if (mFrom == 1) {
                    a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_DAILY);
                } else if (mFrom == 2) {
                    a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_MONTHLY);
                } else {
                    a.putExtra(AppConstant.FROM, AppConstant.LEADERBOARD_LISTING_WEEKLY);
                }
                startActivity(a);
                break;
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
            mAnnouncementIV.setVisibility(View.GONE);
        } else {
            mCurrentVersion = responseModel.getMetaInfo().getApk_version();
            mLink = responseModel.getMetaInfo().getLudoApkLink();
            apkCheck();
            if (responseModel.isStatus()) {
                if (responseModel.getResponse().getBanners().size() > 0)
                    mAnnouncementIV.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(responseModel.getResponse().getBanners().get(0).getImg())) {
                    Glide.with(mAnnouncementIV.getContext()).load(responseModel.getResponse().getBanners().get(0).getImg()).transform(new CenterCrop(), new RoundedCorners(20)).into(mAnnouncementIV);
                } else {
                    mAnnouncementIV.setVisibility(View.GONE);
                }
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
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getMode("1");
        } else {
            Snackbar.make(mPlayTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }
        if (mAppPreference.getBoolean("LudoDownload", false)) {
            try {
                Intent launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
                if (launchIntent != null) {
                    finish();
                    startActivity(new Intent(this, ModeActivity.class));
                } else {
                    mDownloadTV.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                finish();
                startActivity(new Intent(this, LudoUniverseActivity.class));
            }
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

}