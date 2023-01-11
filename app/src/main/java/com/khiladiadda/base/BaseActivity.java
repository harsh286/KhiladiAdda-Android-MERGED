package com.khiladiadda.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.khiladiadda.R;
import com.khiladiadda.base.interfaces.IBaseVersionPresenter;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.PermissionUtils;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.core.model.AppStatus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/*
 * This class is the Base Activity class the all other Activity classes extend.
 * This class has 2 methods to initialize data and views that all other Activity classes must implement
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
    protected AppSharedPreference mAppPreference;
    protected AppUtilityMethods mUtility;
    private Dialog mDialog;
    private IBaseVersionPresenter mPresenter = new BasePresenter(this);
    private Dialog mVersionDialog;
    private boolean mIsRequestingAppInstallPermission;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        AppSharedPreference.initialize(this);
        initData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    protected abstract int getContentView();

    /*
     * This method is used to initialize the data of the class
     * @param
     * @return void
     */
    protected void initData() {
        mAppPreference = AppSharedPreference.getInstance();
        mUtility = new AppUtilityMethods(this);
        initViews();
        initVariables();
    }

    /*
     * This method is used to initialize the views of the class
     * @param
     * @return void
     */
    protected abstract void initViews();

    /*
     * This method is used to initialize the variables of the class
     * @param
     * @return void
     */
    protected abstract void initVariables();

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }
    }

    protected void showProgress(String message) {
        hideProgress();
        mDialog = AppDialog.getAppProgressDialog(this, message);
        mDialog.show();
    }

    protected void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected void showVersionDialog() {
        if (mVersionDialog == null) {
            AppUtilityMethods.deleteCache(this);
                mVersionDialog = AppDialog.showMsgVersionExit(this, mOnVersionListener);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiResponseEvent(ApiResponseEvent event) {
        switch (event.getSystemInfo()) {
            case AppConstant.FROM_MAINTENANCE:
                mAppPreference.setBoolean(AppConstant.UNDER_MAINTENANCE, true);
                AppUtilityMethods.showBlockMsg(BaseActivity.this, event.getmMessage());
                break;
            case AppConstant.FROM_UPDATE:
                mAppPreference.setIsVersionUpdated(true);
                mAppPreference.setDateSaveMaster(0);
                showProgress(getString(R.string.text_waiting_progress));
                mPresenter.getVersionDetails();
                break;
            case AppConstant.FROM_BLOCKED:
                mAppPreference.setBoolean(AppConstant.USER_BLOCKED, true);
                AppUtilityMethods.showBlockMsg(BaseActivity.this, event.getmMessage());
                break;
            case AppConstant.FROM_FORCED_LOGOUT:
                mAppPreference.setBoolean(AppConstant.USER_LOGOUT, true);
                AppUtilityMethods.showLogout(BaseActivity.this, AppConstant.FROM_FORCED_LOGOUT, event.getmMessage());
                break;
        }
    }

    void onVersionSuccess(VersionResponse response) {
        if (response.isStatus()) {
            mAppPreference.setString(AppConstant.VERSION_SIZE, String.valueOf(response.getVersion().getApkSize()));
            mAppPreference.setString(AppConstant.VERSION_DESC, response.getVersion().getUpdateDescription());
            mAppPreference.setString(AppConstant.VERSION_LINK, response.getVersion().getApkLink());
            mAppPreference.setString(AppConstant.VERSION, response.getVersion().getAppVersion());
            showVersionDialog();
        }
        hideProgress();
    }

    void onVersionFailure(ApiError error) {
        hideProgress();
    }

    private final IOnVesrionDownloadListener mOnVersionListener = new IOnVesrionDownloadListener() {
        @Override
        public void onDownloadVersion() {
            new DownloadApk(mOnFileDownloadedListener).execute(mAppPreference.getString(AppConstant.VERSION_LINK, ""));
        }
    };

    private final IOnFileDownloadedListener mOnFileDownloadedListener = new IOnFileDownloadedListener() {
        @Override
        public void onFileDownloaded(String filePath) {
            mVersionDialog.hide();
            mVersionDialog = null;
            try {
                installApk(filePath);
            } catch (Exception e) {
                Toast.makeText(BaseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                float fileSizeMB = (fileLength * 1.0f) / 1024 / 1024;
                float fileDownloadedMB = (progress / 100.0f) * fileSizeMB;
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                TextView appSize = mVersionDialog.findViewById(R.id.tv_size);
                progressBar.setProgress(progress);
                appSize.setText(getString(R.string.text_downloading_progress) + "(" + new DecimalFormat("##.##").format(fileDownloadedMB) + "mb/" + new DecimalFormat("##.##").format(fileSizeMB) + "mb)");
            }
        }
    };

    private void installApk(String filePath) {
        mAppPreference.setDateSaveMaster(0);
        if (Build.VERSION.SDK_INT >= 26 && !BaseActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(BaseActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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

}