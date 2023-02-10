package com.khiladiadda.callbreak;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.callbreak.adapter.CallBreakAdapter;
import com.khiladiadda.callbreak.interfaces.ICallBreakPresenter;
import com.khiladiadda.callbreak.interfaces.ICallBreakView;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.CallBreakDialog;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.ludoUniverse.LudoUniverseActivity;
import com.khiladiadda.ludoUniverse.ModeActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CallBreakActivity extends BaseActivity implements ICallBreakView, IOnItemClickListener, CallBreakDialog.IOnConfirmClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.rv_callbreak)
    RecyclerView mCallBreakRV;
    @BindView(R.id.tv_download)
    TextView mDownloadTV;
    @BindView(R.id.iv_announcement)
    ImageView mAnnouncementIV;
    private CallBreakAdapter mAdapter;
    private List<CallBreakDetails> mList;
    private ICallBreakPresenter mPresenter;
    private Dialog mVersionDialog;
    private CallBreakResponse mainResponse;
    private String mVersion, mLink, mFilePath, mCurrentVersion;
    private boolean mIsRequestingAppInstallPermission;
    private int position = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_callbreak;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText("Call Break");
        mBackIV.setOnClickListener(this);
        mDownloadTV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new CallBreakPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new CallBreakAdapter(this, mList);
        mCallBreakRV.setLayoutManager(new LinearLayoutManager(this));
        mCallBreakRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        getData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (view.getId() == R.id.tv_download) {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getCallBreak();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getJoinData(int pos) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getCallBreakJoin(mainResponse.getResponse().get(pos).getId());
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestSuccess(CallBreakResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mainResponse = responseModel;
            mList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
            mCurrentVersion = responseModel.getApkVersion();
            mLink = responseModel.getCallbreakApkLink();
//            apkCheck();
            if (responseModel.getBanner().size() > 0)
                mAnnouncementIV.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(responseModel.getBanner().get(0).getImg())) {
                Glide.with(mAnnouncementIV.getContext()).load(responseModel.getBanner().get(0).getImg()).transform(new CenterCrop(), new RoundedCorners(20)).into(mAnnouncementIV);
            } else {
                mAnnouncementIV.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetContestJoinSuccess(CallBreakJoinMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            launchUnityWithData(mainResponse.getResponse().get(position).getId(), mainResponse.getResponse().get(position).getEntryFees(), mainResponse.getResponse().get(position).getWinningAmount(), responseModel.getResponse().getRandomName(), responseModel.getResponse().getRandomDp(), mainResponse.getResponse().get(position).prizePoolBreakup);
        } else {
            Toast.makeText(this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestJoinFailure(ApiError error) {
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
            tv.setText("Hey You have Successfully downloaded the CallBreak game, Now please click on install button to continue.");
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText("Install Now");
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("CallBreakDownload", true);
                } catch (Exception e) {
                    Toast.makeText(CallBreakActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        if (Build.VERSION.SDK_INT >= 26 && !CallBreakActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(CallBreakActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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

    private void apkCheck() {
        if (mLink != null) {
            mAppPreference.setString("CBVersion", mCurrentVersion);
            mAppPreference.setString("mCBLink", mLink);
            Properties properties = new Properties();
            properties.addAttribute("CallBreakVersion", mCurrentVersion);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "CallBreak", properties);
        }
        if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
            mDownloadTV.setVisibility(View.GONE);
            mAppPreference.setBoolean("CallBreakDownload", true);
        } else {
            mDownloadTV.setVisibility(View.VISIBLE);
            mDownloadTV.setText("Update");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }
        if (mAppPreference.getBoolean("CallBreakDownload", false)) {
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

    @Override
    public void onItemClick(View view, int position, int tag) {
        openBottomDialog(position);
    }

    private void openBottomDialog(int position) {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        double mDepWinAmount = mCoins.getDeposit() + mCoins.getWinning();
        CallBreakDialog addExpenseDialog = new CallBreakDialog(this, String.valueOf(mList.get(position).getEntryFees()), String.valueOf(mTotalWalletBal), String.valueOf(mDepWinAmount), mList.get(position).getPrizePoolBreakup(), mList.get(position), this, position);
        addExpenseDialog.setCancelable(false);
        addExpenseDialog.setCanceledOnTouchOutside(false);
        addExpenseDialog.show();
    }

    private void launchUnityWithData(String cId, double Amount, double mWinAmount, String mRandomName, String mRandomDp, List<PrizePoolBreakthrough> prizePoolBreakUp) {
        String mAmount = String.valueOf(Amount);
        String mWAmount = String.valueOf(mWinAmount);
        AppSharedPreference.initialize(this);
        JsonArray myCustomArray = new GsonBuilder().create().toJsonTree(prizePoolBreakUp).getAsJsonArray();
        AppSharedPreference mAppPreference = AppSharedPreference.getInstance();

        Intent launchGameIntent =
                getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.CallBreakPackageName);
        if (launchGameIntent != null) {
            if (mAppPreference.getProfileData().getId() != null || !mAppPreference.getProfileData().getId().isEmpty()) {
                launchGameIntent.putExtra("userToken", mAppPreference.getSessionToken().toString());
                launchGameIntent.putExtra("contestId", cId);
                launchGameIntent.putExtra("playerId", mAppPreference.getProfileData().getId());
                launchGameIntent.putExtra("amount", mAmount);
//            launchGameIntent.putExtra("winAmount", mWAmount);
                launchGameIntent.putExtra("randomName", mRandomName);
                launchGameIntent.putExtra("randomPhoto", mRandomDp);
                launchGameIntent.putExtra("prizePoolBreakUp", String.valueOf(myCustomArray));
            }else {
                Toast.makeText(this, "Something went wrong!! Please restart your app", Toast.LENGTH_LONG).show();
            }


//            launchGameIntent.putExtra("prizePoolBreakUp", new JSONArray(prizePoolBreakUp));
//            launchGameIntent.putParcelableArrayListExtra("prizePoolBreakUp", (ArrayList<? extends Parcelable>) prizePoolBreakUp);
//            Snackbar.make(mWalletBalanceTV, +mFrom, Snackbar.LENGTH_LONG).show();
            startActivity(launchGameIntent);
            finishAffinity();
        } else {
            Toast.makeText(this, "Apk is not installed yet", Toast.LENGTH_SHORT).show();
        }
//        isSuccessfulGameOpen = true;
    }


    @Override
    public void onConfirmClick(int pos) {
        position = pos;
        getJoinData(pos);
    }
}