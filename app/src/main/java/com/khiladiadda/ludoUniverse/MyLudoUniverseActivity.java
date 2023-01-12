package com.khiladiadda.ludoUniverse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.ludoUniverse.adapter.MyAllLudoUniAdapter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniversePresenter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniverseView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
//import com.unity3d.player.UnityLauncherActivity;
//import com.unity3d.player.UnityLauncherActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyLudoUniverseActivity extends BaseActivity implements ILudoUniverseView, IOnItemClickListener, MyAllLudoUniAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.rv_ludo_contest)
    RecyclerView mLudoContestRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    private Intent launchIntent;
    private ILudoUniversePresenter mPresenter;
    private List<LudoContest> mLudoContestList;
    private MyAllLudoUniAdapter mAllChallengeAdapter;
    private String pos;
    private double Amount;
    private boolean isDownload;
    private String mRandomName;
    private double mWinAmount;
    private String mRandomDp;
    private String mContestCode;
    private boolean isSuccessfulGameOpen;
    private boolean mIsRequestingAppInstallPermission;
    private String mFilePath;
    private String mVersion;
    private Dialog mVersionDialog;
    private String mCurrentVersion;
    private String mLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoNotificationReceiver, new IntentFilter("com.khiladiadda.LUDO_UNI_NOTIFY"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_ludo_universe;
    }

    @Override
    protected void initViews() {
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.ludo_uni_actionbar));
        mActivityNameTV.setText(R.string.text_ludo_adda);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mAppPreference.setBoolean("MyLudoDownload", false);
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        mCurrentVersion = mAppPreference.getString("LudoVersion", mCurrentVersion);
        mLink = mAppPreference.getString("mLudoLink", mLink);

    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoUniversePresenter(this);
        mLudoContestList = new ArrayList<>();
        mAllChallengeAdapter = new MyAllLudoUniAdapter(this, mLudoContestList);
        GridLayoutManager mLudoLayoutManager = new GridLayoutManager(this, 2);
        mLudoContestRV.setLayoutManager(mLudoLayoutManager);
        mLudoContestRV.setAdapter(mAllChallengeAdapter);
        mAllChallengeAdapter.setOnItemClickListener(this);
        mAllChallengeAdapter.setOnItemChildClickListener(this);
    }

    private void getData() {
        AppUtilityMethods.deleteCache(this);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getAllContestList(0, AppConstant.PAGE_SIZE);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
        }
    }

    @Override
    public void onGetContestSuccess(LudoContestResponse responseModel) {
        hideProgress();
        if (responseModel.getResponse().size() > 0) {
            mLudoContestList.clear();
            mLudoContestList.addAll(responseModel.getResponse());
            mAllChallengeAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void addChallengeSuccess(BaseResponse response) {
    }

    @Override
    public void addChallengeFailure(ApiError error) {

    }

    @Override
    public void acceptContestSuccess(BaseResponse response) {
        hideProgress();
    }

    @Override
    public void acceptContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void cancelContestSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsg(getString(R.string.text_ludo_challenge_cancel));
            getData();
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void cancelContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void JoinedContestStatusSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            launchUnityWithData(pos, Amount, mContestCode, mWinAmount, mRandomName, mRandomDp);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), true);
        }
    }

    @Override
    public void JoinedContestStatusFailure(ApiError error) {
        hideProgress();
    }

    private BroadcastReceiver mLudoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getData();
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLudoNotificationReceiver);
        mPresenter.destroy();
        super.onDestroy();
    }

    private void openResultScreen(int position) {
        //launchUnityWithData(mLudoContestList.get(position).getId());
    }

    @Override
    public void onCancelClick(int position) {
        checkChallengeStatus(position);
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        checkChallengeStatus(position);
    }

    private void checkChallengeStatus(int position) {
        if (new NetworkStatus(this).isInternetOn()) {
            OpponentLudoRequest request = new OpponentLudoRequest("");
            if (mLudoContestList.get(position).getContestStatus() == 0) {
                acceptAlert(this, 0, mLudoContestList.get(position).getId(), request, AppConstant.FROM_CANCEL, null, 0, null, null);
            } else if (mLudoContestList.get(position).getContestStatus() == 1) {
                if (mLudoContestList.get(position).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
                    acceptAlert(this, mLudoContestList.get(position).getEntryFees(), mLudoContestList.get(position).getId(), request, AppConstant.FROM_PLAY, mLudoContestList.get(position).getContestCode(), mLudoContestList.get(position).getWinningAmount(), mLudoContestList.get(position).getOpponent().getLudoName(), mLudoContestList.get(position).getOpponent().getLudoDp());
                } else {
                    acceptAlert(this, mLudoContestList.get(position).getEntryFees(), mLudoContestList.get(position).getId(), request, AppConstant.FROM_PLAY, mLudoContestList.get(position).getContestCode(), mLudoContestList.get(position).getWinningAmount(), mLudoContestList.get(position).getCaptain().getLudoName(), mLudoContestList.get(position).getCaptain().getLudoDp());
                }
            }
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void launchUnityWithData(String cId, double Amount, String mContestCode, double mWinAmount, String mRandomName, String mRandomDp) {
        String mAmount = String.valueOf(Amount);
        String mWAmount = String.valueOf(mWinAmount);
        if (launchIntent != null) {
            launchIntent.putExtra("userToken", mAppPreference.getSessionToken());
            launchIntent.putExtra("contestId", cId);
            launchIntent.putExtra("ka_version", AppUtilityMethods.getVersion());
            launchIntent.putExtra("playerId", mAppPreference.getProfileData().getId());
            launchIntent.putExtra("amount", mAmount);
            launchIntent.putExtra("contestCode", mContestCode);
            launchIntent.putExtra("winAmount", mWAmount);
            launchIntent.putExtra("randomName", mRandomName);
            launchIntent.putExtra("randomPhoto", mRandomDp);
            isSuccessfulGameOpen = true;
            startActivity(launchIntent);

        }

    }

    public void acceptAlert(final Activity activity, double entryFee, String ludoId, OpponentLudoRequest request, int from, String ContestCode, double Amt, String Rname, String RandomDp) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_ludo_uni);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        if (from == AppConstant.FROM_ADD) {
            tv_msg.setText(entryFee + getString(R.string.text_accept_confirm_ludo));
        } else if (from == AppConstant.FROM_CANCEL) {
            tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
        } else if (from == AppConstant.FROM_PLAY) {
            tv_msg.setText(getString(R.string.text_play_now_confirmation));
        }
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            showProgress(getString(R.string.txt_progress_authentication));
            if (from == AppConstant.FROM_ADD) {
                mPresenter.acceptContest(ludoId, request);
            } else if (from == AppConstant.FROM_CANCEL) {
                mPresenter.cancelContest(ludoId);
            } else if (from == AppConstant.FROM_PLAY) {
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        pos = ludoId;
                        Amount = entryFee;
                        mContestCode = ContestCode;
                        mWinAmount = Amt;
                        mRandomName = Rname;
                        mRandomDp = RandomDp;
                        mPresenter.getStatus(pos);
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
            }
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    public void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.ludo_uni_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        tv_help.setText(getString(R.string.text_help_captain));
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

//        if (AppUtilityMethods.isDeviceRooted()) {
//            AppUtilityMethods.showMsgCancel(this, "This is a rooted device and we do not allow users to play on rooted device as it might not be a good experience. Please play safely", false);
//        } else {
//            if (AppUtilityMethods.isDevMode()) {
//                dialog();
//            } else {
//               // showDialogMsg("", 2);
//            }
//        }

        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }

        if (mAppPreference.getBoolean("MyLudoDownload", false)) {
            try {
                Intent launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        finish();
                        startActivity(new Intent(this, LudoUniverseActivity.class));
                    } else {
                        finish();
                        startActivity(new Intent(this, LudoUniverseActivity.class));
                    }
                }
            } catch (Exception e) {
                finish();
                startActivity(new Intent(this, LudoUniverseActivity.class));


            }
        }

    }


    private final IOnVesrionDownloadListener mOnVersionListener = new IOnVesrionDownloadListener() {
        @Override
        public void onDownloadVersion() {
            if (mLink != null && !mLink.isEmpty()) {
                new DownloadApk(mOnFileDownloadedListener).execute(mLink);
            }
            else{
                new DownloadApk(mOnFileDownloadedListener).execute(AppSharedPreference.getInstance().getVersion().getVersion().getLudoApkLink());
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
                    mAppPreference.setBoolean("MyLudoDownload", true);
                } catch (Exception e) {
                    Toast.makeText(MyLudoUniverseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        if (Build.VERSION.SDK_INT >= 26 && !MyLudoUniverseActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(MyLudoUniverseActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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

}