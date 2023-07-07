package com.khiladiadda.callbreak;
import static android.view.View.GONE;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.splash.SplashActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.khiladiadda.wallet.WalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.rl_wallet)
    LinearLayout mWalletLL;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;
    private CallBreakAdapter mAdapter;
    private List<CallBreakDetails> mList;
    private ICallBreakPresenter mPresenter;
    private Dialog mVersionDialog;
    private CallBreakResponse mainResponse;
    private String mVersion, mLink, mFilePath, mCurrentVersion, mEntryFee;
    private boolean mIsRequestingAppInstallPermission;
    private int position = 0, mFromUnity = 0, mDownUp = 1;
    private Intent launchIntent;
    private double mTotalWalletBal, mDepWinAmount;
    private CallBreakDialog callBreakDialog;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    @BindView(R.id.tv_history)
    TextView mHistoryTV;
    @BindView(R.id.tv_help_video)
    TextView mHelpVideoTV;
    private long mLastClickTime = 0;
    @BindView(R.id.nudge)
    NudgeView mNV;

    @Override
    protected int getContentView() {
        return R.layout.activity_callbreak;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_court_piece_pro);
    }

    @Override
    protected void initVariables() {
        mPresenter = new CallBreakPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new CallBreakAdapter(this, mList);
        mCallBreakRV.setLayoutManager(new LinearLayoutManager(this));
        mCallBreakRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mWalletLL.setOnClickListener(this);
        mBackIV.setOnClickListener(this);
        mDownloadTV.setOnClickListener(this);
        mHelpVideoTV.setOnClickListener(this);
        mHistoryTV.setOnClickListener(this);
        callBreakDialog = new CallBreakDialog(this);
        getData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (view.getId() == R.id.tv_download) {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        } else if (view.getId() == R.id.rl_wallet) {
            Intent profile = new Intent(this, WalletActivity.class);
            startActivity(profile);
        } else if (view.getId() == R.id.tv_history) {
            startActivity(new Intent(this, CBHistoryActivity.class));
        } else if (view.getId() == R.id.tv_help_video) {
            AppUtilityMethods.openYoutubeCallbreak(this, "www.youtube.com/watch?v=BQXaInDe4m4", "https://www.youtube.com/watch?v=BQXaInDe4m4");
        }
    }
    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            getCoins();
            mPresenter.getCallBreak();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }
    private void getCoins() {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mDepWinAmount = mCoins.getDeposit() + mCoins.getWinning();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
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
        if (responseModel.isStatus()) {
            mainResponse = responseModel;
            mList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
            mCurrentVersion = responseModel.getApkVersion();
            mLink = responseModel.getCallbreakApkLink();
            List<BannerDetails> bannerData = responseModel.getBanner();
            if (bannerData != null && bannerData.size() > 0) {
                mBannerVP.setVisibility(View.VISIBLE);
                setUpAdvertisementViewPager(bannerData);
            } else {
                mBannerVP.setVisibility(GONE);
            }
            hideProgress();
            apkCheck();
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
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
            if (responseModel.getStatusCode() == 200 || responseModel.getStatusCode() == 202) { // okay
                //AppFlyer
                //AppUtilityMethods.appFlyersGameInvestEvent(this,responseModel.getResponse().getWinningAmount().toString(), AppConstant.WORD_SEARCH);
                //Mo Engage
                Properties mProperties=new Properties();
                mProperties.addAttribute(AppConstant.GAMETYPE,AppConstant.CALL_BREAK_JOIN);
                mProperties.addAttribute("EnrtyFee", mEntryFee);
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, AppConstant.CALL_BREAK_JOIN, mProperties);
                launchUnityWithData(mainResponse.getResponse().get(position).getId(), mainResponse.getResponse().get(position).getEntryFees(), mainResponse.getResponse().get(position).getWinningAmount(), responseModel.getResponse().getRandomName(), responseModel.getResponse().getRandomDp(), mainResponse.getResponse().get(position).prizePoolBreakup);
            } else if (responseModel.getStatusCode() == 201) {
                AppUtilityMethods.showMsg(this, "You have already joined other match", false);
            } else if (responseModel.getStatusCode() == 203) {
                AppUtilityMethods.showRechargeMsg(this, "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
            } else {
                AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            }
        } else {
            if (responseModel.getStatusCode() == 203) {
                AppUtilityMethods.showRechargeMsg(this, "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
            } else {
                Toast.makeText(this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onGetContestJoinFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetHistorySuccess(CallBreakResponse responseModel) {

    }

    @Override
    public void onGetHistoryFailure(ApiError error) {

    }

    @Override
    public void onGetHistoryRankSuccess(CbHistoryRankMainResponse responseModel) {

    }

    @Override
    public void onGetHistoryRankFailure(ApiError error) {

    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ludoadda_download_popup);
        TextView tv = dialog.findViewById(R.id.textView9);
        tv.setText("It seem like you haven't downloaded our CallBreak game to play contests, So please click on download button to download the game.");
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_download);
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
        TextView tvMsg = dialog.findViewById(R.id.textView9);
        if (mDownUp == 2) {
            tvMsg.setText("It seem like you haven't update our CallBreak game to play contests, So please click on download button to download the game.");
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
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.CallBreakPackageName, 0);
            mVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void apkCheck() {
        if (launchIntent != null) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                mDownloadTV.setVisibility(View.GONE);
                mWalletLL.setVisibility(View.VISIBLE);
                mAppPreference.setBoolean("CallBreakDownload", true);
            } else {
                mDownloadTV.setVisibility(View.VISIBLE);
                mDownloadTV.setText("Update");
                mDownUp = 2;
                mWalletLL.setVisibility(GONE);
            }
        } else {
            mDownloadTV.setVisibility(View.VISIBLE);
            mWalletLL.setVisibility(GONE);
        }
        if (mLink != null) {
            mAppPreference.setString("CBVersion", mCurrentVersion);
            mAppPreference.setString("CBLink", mLink);
            Properties properties = new Properties();
            properties.addAttribute("CBVersion", mCurrentVersion);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "CallBreak", properties);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mFromUnity == 0) {
            launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.CallBreakPackageName);
            if (launchIntent != null) getVersion();
            else mAppPreference.setBoolean("CallBreakDownload", false);
            if (mIsRequestingAppInstallPermission) {
                installApk(mFilePath);
            }
            apkCheck();
        } else {
            AppDialog.showRestartDialog(this, "We are restarting");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentClear = new Intent(CallBreakActivity.this, SplashActivity.class);
                    intentClear.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentClear);
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        if (mAppPreference.getBoolean("CallBreakDownload", false)) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                openBottomDialog(position);

            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else {
            mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
        }
    }

    private void openBottomDialog(int position) {
        getCoins();
        mEntryFee = String.valueOf(mList.get(position).getEntryFees());
        callBreakDialog = new CallBreakDialog(this, mEntryFee, String.format("%.2f", mTotalWalletBal), String.format("%.2f", mDepWinAmount), mList.get(position).getPrizePoolBreakup(), mList.get(position), this, position);
        callBreakDialog.setCancelable(true);
        callBreakDialog.setCanceledOnTouchOutside(false);
        callBreakDialog.show();
    }

    private void launchUnityWithData(String cId, double Amount, double mWinAmount, String mRandomName, String mRandomDp, List<PrizePoolBreakthrough> prizePoolBreakUp) {
        String mAmount = String.valueOf(Amount);
        AppSharedPreference.initialize(this);
        JsonArray myCustomArray = new GsonBuilder().create().toJsonTree(prizePoolBreakUp).getAsJsonArray();
        AppSharedPreference mAppPreference = AppSharedPreference.getInstance();
        Intent launchGameIntent =
                getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.CallBreakPackageName);
        if (launchGameIntent != null) {
            if (mAppPreference.getProfileData().getId() != null || !mAppPreference.getProfileData().getId().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName(AppConstant.CallBreakPackageName, "com.unity3d.player.UnityPlayerActivity"));
                intent.putExtra("userToken", mAppPreference.getSessionToken());
                intent.putExtra("playerId", mAppPreference.getProfileData().getId());
                intent.putExtra("amount", mAmount);
                intent.putExtra("prizePoolBreakUp", String.valueOf(myCustomArray));
                mFromUnity = 1;
                startActivity(intent);
            } else {
                Toast.makeText(this, "Something went wrong!! Please restart your app", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Apk is not installed yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        callBreakDialog.cancel();
    }

    @Override
    public void onConfirmClick(int pos) {
        position = pos;
        getJoinData(pos);
        callBreakDialog.dismiss();
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
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}