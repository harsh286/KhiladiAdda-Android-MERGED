package com.khiladiadda.ludoUniverse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
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
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;

import com.airbnb.lottie.L;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnAddLudoUniverseListener;
import com.khiladiadda.dialogs.interfaces.IOnFilterChallengeListener;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.ludo.LudoFilterDialog;
import com.khiladiadda.ludo.buddy.BuddyActivity;
import com.khiladiadda.ludoUniverse.adapter.LudoUniAdapter;
import com.khiladiadda.ludoUniverse.adapter.MyLudoUniAdapter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniversePresenter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniverseView;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.preference.AppSharedPreference;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LudoUniverseActivity extends BaseActivity implements ILudoUniverseView, IOnAddLudoUniverseListener, LudoUniAdapter.IOnItemChildClickListener, IOnItemClickListener, MyLudoUniAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.tv_view_all_challenge)
    TextView mViewAllChallengesTV;
    @BindView(R.id.rv_my_contest)
    RecyclerView mMyContestRV;
    @BindView(R.id.rv_ludo_contest)
    RecyclerView mLudoContestRV;
    @BindView(R.id.fl_ludo_tutorial)
    RelativeLayout mTutorialRL;
    @BindView(R.id.tv_buddy_list)
    TextView mBuddyListTV;
    @BindView(R.id.tv_refresh)
    TextView mRefreshTV;
    @BindView(R.id.tv_add_challenge)
    TextView mAddChallengeTV;
    @BindView(R.id.iv_announcement)
    ImageView mAnnouncementIV;
    @BindView(R.id.tv_filters)
    TextView mFiltersTV;
    @BindView(R.id.tv_view_video)
    TextView mViewVideoTV;
    @BindView(R.id.rl_wallet)
    LinearLayout mWalletLL;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;
    @BindView(R.id.ll_download)
    LinearLayout mDownloadLL;
    @BindView(R.id.tv_download)
    TextView mDownloadTV;

    private LudoUniAdapter mAllChallengeAdapter;
    private MyLudoUniAdapter mMyChallengeAdapter;
    private ILudoUniversePresenter mPresenter;
    private List<LudoContest> mLudoContestList, mMyContestList;
    private Dialog mAddChallengeDialog;
    private boolean mGetData, mSyncProfile;
    private int mContestType, mFilters = 0;
    private String pos, mAmount;
    private Coins mCoins;
    private double mTotalWalletBal;
    private Intent launchIntent;
    private boolean isSuccessfulGameOpen;
    private boolean mIsRequestingAppInstallPermission;
    private String mFilePath;
    private double Amount;
    private Dialog mVersionDialog;
    private String mLink;
    private String mContestCode;
    private String mCurrentVersion;
    private String mVersion;
    private boolean isDownload;
    private String mRandomName;
    private double mWinAmount;
    private String mRandomDp;
    @BindView(R.id.nudge)
    NudgeView mNV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoNotificationReceiver, new IntentFilter("com.khiladiadda.LUDO_UNI_NOTIFY"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_universe;
    }

    @Override
    protected void initViews() {
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect != null)
                if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                    mAppPreference.setIsDeepLinking(true);
                }
        }
        mActivityNameTV.setText(R.string.text_ludo_adda);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.ludo_uni_actionbar));
        mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mViewAllChallengesTV.setOnClickListener(this);
        mRefreshTV.setOnClickListener(this);
        mAddChallengeTV.setOnClickListener(this);
        mTutorialRL.setOnClickListener(this);
        mBuddyListTV.setOnClickListener(this);
        mFiltersTV.setOnClickListener(this);
        mViewVideoTV.setOnClickListener(this);
        mWalletLL.setOnClickListener(this);
        mDownloadLL.setOnClickListener(this);
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        mAppPreference.setBoolean("LudoDownload", false);
        if (launchIntent != null) getVersion();


    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoUniversePresenter(this);
        mLudoContestList = new ArrayList<>();
        mAllChallengeAdapter = new LudoUniAdapter(this, mLudoContestList);
        mLudoContestRV.setLayoutManager(new LinearLayoutManager(this));
        mLudoContestRV.setAdapter(mAllChallengeAdapter);
        mAllChallengeAdapter.setOnItemChildClickListener(this);
        mMyContestList = new ArrayList<>();
        mMyChallengeAdapter = new MyLudoUniAdapter(this, mMyContestList);
        mMyContestRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mMyContestRV.setAdapter(mMyChallengeAdapter);
        mMyChallengeAdapter.setOnItemClickListener(this);
        mMyChallengeAdapter.setOnItemChildClickListener(this);
        String mDialog = getIntent().getStringExtra(AppConstant.GAMEID);
        if (mContestType == AppConstant.FROM_CREATE_LUDO) {
            createChallenge();
        }
        if (mDialog != null && !mDialog.isEmpty()) {
            createChallenge();
        }
        getLudoContest(true, mSyncProfile = true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_view_video:
                AppUtilityMethods.openLudoVideo(LudoUniverseActivity.this);
                break;
            case R.id.tv_add_challenge:
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        createChallenge();
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);

                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
                break;
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
            case R.id.tv_view_all_challenge:
                Intent i = new Intent(this, MyLudoUniverseActivity.class);
                i.putExtra(AppConstant.CONTEST_TYPE, mContestType);
                ludoResultActivityResultLauncher.launch(i);
                break;
            case R.id.tv_refresh:
                getLudoContest(false, mSyncProfile);
                break;
            case R.id.tv_buddy_list:
                Intent buddy = new Intent(this, BuddyActivity.class);
                startActivity(buddy);
                break;
            case R.id.tv_filters:
                new LudoFilterDialog(this, onFilterChallengeListener, AppConstant.FROM_LOST);
                break;
            case R.id.rl_wallet:
                Intent profile = new Intent(this, WalletActivity.class);
                startActivity(profile);
                break;
            case R.id.ll_download:
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                break;
        }
    }

    private void createChallenge() {
        mAddChallengeDialog = AppDialog.addLudoUniverseDialog(this, this);
    }

    private void getLudoContest(boolean banner, boolean profile) {
        AppUtilityMethods.deleteCache(this);
        mGetData = banner || profile;
        String date = AppUtilityMethods.getLudoCurrentDate();
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getContestList(date, String.valueOf(mContestType), banner, String.valueOf(mContestType + 1), profile, 1, mFilters);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestSuccess(LudoContestResponse responseModel) {
        mSyncProfile = false;
        mLudoContestList.clear();
        if (responseModel.isLudoEnabled()) {
            mLink = responseModel.getLudoApkLink();
            mCurrentVersion = responseModel.getApk_version();
            mLudoContestList.addAll(responseModel.getResponse());
            mAllChallengeAdapter.notifyDataSetChanged();
            mMyContestList.clear();
            mMyContestList.addAll(responseModel.getMyContests());
            mMyChallengeAdapter.notifyDataSetChanged();
            if (mMyContestList.size() >= 1) {
                mNoDataTV.setVisibility(View.GONE);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
            }
            if (mGetData) {
                setData(responseModel);
            }
        } else {
            mViewAllChallengesTV.setEnabled(false);
            mAddChallengeTV.setEnabled(false);

        }
        hideProgress();
        if (launchIntent != null) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                mWalletLL.setVisibility(View.VISIBLE);
                mDownloadLL.setVisibility(View.GONE);
            } else {
                mDownloadTV.setText("Update");
                mDownloadLL.setVisibility(View.VISIBLE);
                mWalletLL.setVisibility(View.GONE);
            }
        } else {
            mWalletLL.setVisibility(View.GONE);
            mDownloadLL.setVisibility(View.VISIBLE);

        }
        if (mLink != null) {
            mAppPreference.setString("LudoVersion", mCurrentVersion);
            mAppPreference.setString("mLudoLink", mLink);
            Properties properties = new Properties();
            properties.addAttribute("LudoADDAVersion", mCurrentVersion);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "LudoADDA", properties);
        }

    }

    private void setData(LudoContestResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getProfile());
        mCoins = mAppPreference.getProfileData().getCoins();
        if (mCoins != null) {
            mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }
        if (responseModel.isStatus()) {
            if (responseModel.getBanner().size() > 0) mAnnouncementIV.setVisibility(View.VISIBLE);
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
    public void addChallengeSuccess(BaseResponse response) {
        hideProgress();
        if (mAddChallengeDialog != null && mAddChallengeDialog.isShowing()) {
            mAddChallengeDialog.dismiss();
        }
        if (response.isStatus()) {
            showDialogMsg(response.getMessage(), 1);
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, "LUDOADDA CREATE");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties properties = new Properties();
            properties
                    // tracking integer
                    .addAttribute("game", "LudoAdda")
                    // tracking Date
                    .addAttribute("gamecreatedDate", new Date())
                    // tracking double
                    .addAttribute("amount", mAmount).addAttribute("LudoAdda", "Waiting For Opponent");

            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameCreated", properties);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void addChallengeFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void acceptContestSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showDialogMsg(response.getMessage(), 1);
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, "LUDOADDA ACCEPT");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            Properties properties = new Properties();
            properties
                    // tracking integer
                    .addAttribute("game", "LudoAdda")
                    // tracking Date
                    .addAttribute("gamecreatedDate", new Date())
                    // tracking double
                    .addAttribute("amount", mAmount);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameAccepted", properties);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void acceptContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void cancelContestSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showDialogMsg(getString(R.string.text_ludo_challenge_cancel), 1);
            Properties properties = new Properties();
            properties.addAttribute("game", "LudoAdda");
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameCanceled", properties);
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

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLudoNotificationReceiver);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onAcceptClicked(int position) {
        if (new NetworkStatus(this).isInternetOn()) {
            if (launchIntent != null) {
                if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                    if (mLudoContestList.get(position).getEntryFees() <= mTotalWalletBal) {
                        OpponentLudoRequest request = new OpponentLudoRequest("");
                        acceptAlert(this, mLudoContestList.get(position).getEntryFees(), mLudoContestList.get(position).getId(), request, AppConstant.FROM_ADD, null, 0, null, null);
                    } else {
                        AppUtilityMethods.showRechargeMsg(this, AppConstant.INSUFFICIENT_WALLET_RECHARGE);
                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }


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
            if (mMyContestList.get(position).getContestStatus() == 0) {
                acceptAlert(this, 0.0, mMyContestList.get(position).getId(), request, AppConstant.FROM_CANCEL, null, 0, null, null);
            } else if (mMyContestList.get(position).getContestStatus() == 1) {
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        if (mMyContestList.get(position).getCaptainId().equalsIgnoreCase(mAppPreference.getProfileData().getId())) {
                            acceptAlert(this, mMyContestList.get(position).getEntryFees(), mMyContestList.get(position).getId(), request, AppConstant.FROM_PLAY, mMyContestList.get(position).getContestCode(), mMyContestList.get(position).getWinningAmount(), mMyContestList.get(position).getOpponent().getLudoName(), mMyContestList.get(position).getOpponent().getLudoDp());
                        } else {
                            acceptAlert(this, mMyContestList.get(position).getEntryFees(), mMyContestList.get(position).getId(), request, AppConstant.FROM_PLAY, mMyContestList.get(position).getContestCode(), mMyContestList.get(position).getWinningAmount(), mMyContestList.get(position).getCaptain().getLudoName(), mMyContestList.get(position).getCaptain().getLudoDp());
                        }
                    } else {
                        mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                    }
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }


            }
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
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

    public void showDialogMsg(String msg, int from) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.ludo_uni_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (from == 1) {
                getLudoContest(false, mSyncProfile = true);
            } else {
                getLudoContest(true, true);
            }
        });
        dialog.show();
    }

    private final BroadcastReceiver mLudoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getLudoContest(false, mSyncProfile = true);
        }
    };


    ActivityResultLauncher<Intent> ludoResultActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (mAppPreference.getBoolean(AppConstant.IS_DATA_CHANGE, false)) {
            getLudoContest(false, mSyncProfile = true);
        }
    });

    private final IOnFilterChallengeListener onFilterChallengeListener = filter -> {
        mFilters = filter;
        getLudoContest(false, false);
    };

    @Override
    public void onAddLuodUniverseListener(String amount) {
        mAmount = amount;
        LudoContestRequest request = new LudoContestRequest(Long.parseLong(amount));
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.addChallenge(request);
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
            startActivity(launchIntent);
            isSuccessfulGameOpen = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (AppUtilityMethods.isDeviceRooted()) {
//            AppUtilityMethods.showMsgCancel(this, "This is a rooted device and we do not allow users to play on rooted device as it might not be a good experience. Please play safely", false);
//        } else {
//            if (AppUtilityMethods.isDevMode()) {
//                dialog();
//            } else {
//                showDialogMsg("", 2);
//            }
//        }
        if (isSuccessfulGameOpen) {
            getLudoContest(false, mSyncProfile = true);
        }
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }

        if (mAppPreference.getBoolean("LudoDownload", false)) {
            try {
                Intent launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
                if (launchIntent != null) {
                    finish();
                    startActivity(new Intent(this, LudoUniverseActivity.class));
                } else {
                    mWalletLL.setVisibility(View.GONE);
                    mDownloadLL.setVisibility(View.VISIBLE);

                }
            } catch (Exception e) {
                finish();
                startActivity(new Intent(this, LudoUniverseActivity.class));


            }
        }

    }


//    private void dialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setTitle("Application Blocked");
//        builder1.setMessage("Hey Khiladi ! \nTo use this application and play your favourite games, disable Developer option from Settings. \n\nSettings->Search(Developer Option)->Toggle On to Off");
//        builder1.setCancelable(false);
//        builder1.setPositiveButton("Setting", (dialog, which) -> startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)));
//        builder1.create();
//        builder1.show();
//    }

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
            }else {
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
                    mAppPreference.setBoolean("LudoDownload", true);
                } catch (Exception e) {
                    Toast.makeText(LudoUniverseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        if (Build.VERSION.SDK_INT >= 26 && !LudoUniverseActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(LudoUniverseActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            AppUtilityMethods.deleteCache(this);
            finish();
        }
    }
}