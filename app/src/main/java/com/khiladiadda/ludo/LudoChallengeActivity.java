package com.khiladiadda.ludo;

import static android.view.View.GONE;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnAddChallengeListener;
import com.khiladiadda.dialogs.interfaces.IOnFilterChallengeListener;
import com.khiladiadda.dialogs.interfaces.IOnUpdateGameUsernameListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.ludo.adapter.LudoChallengeAdapter;
import com.khiladiadda.ludo.adapter.MyChallengeAdapter;
import com.khiladiadda.ludo.buddy.BuddyActivity;
import com.khiladiadda.ludo.interfaces.ILudoChallengePresenter;
import com.khiladiadda.ludo.interfaces.ILudoChallengeView;
import com.khiladiadda.ludo.result.LudoResultActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.GameCredential;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageActivity;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.WalletActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class LudoChallengeActivity extends BaseActivity implements ILudoChallengeView, IOnAddChallengeListener, IOnUpdateGameUsernameListener, LudoChallengeAdapter.IOnItemChildClickListener, IOnItemClickListener, MyChallengeAdapter.IOnItemChildClickListener {

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
    @BindView(R.id.tv_view_video)
    TextView mViewVideoTV;
    @BindView(R.id.tv_buddy_list)
    TextView mBuddyListTV;
    @BindView(R.id.tv_refresh)
    TextView mRefreshTV;
    @BindView(R.id.tv_add_challenge)
    TextView mAddChallengeTV;
    @BindView(R.id.btn_classic)
    Button mClassicBTN;
    @BindView(R.id.btn_popular)
    Button mPopularBTN;
    @BindView(R.id.tv_filters)
    TextView mFiltersTV;
    @BindView(R.id.ll_mode)
    LinearLayout mLLMode;
    @BindView(R.id.rl_wallet)
    LinearLayout mLLWallet;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mWalletBalanceTV;

    private LudoChallengeAdapter mAllChallengeAdapter;
    private MyChallengeAdapter mMyChallengeAdapter;
    private ILudoChallengePresenter mPresenter;
    private List<LudoContest> mLudoContestList, mMyContestList;
    private Dialog mAddChallengeDialog;
    private String mUserId, mGameId, mAmount;
    private int mContestType;
    private boolean mGetData, mSyncProfile;
    private String mGameCharacterId = "";
    private Dialog mUpdateGameUsernameDialog;
    private int mMode = 1, mFilters = 0;
    private Coins mCoins;

    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoNotificationReceiver, new IntentFilter("com.khiladiadda.LUDO_NOTIFY"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_challenge;
    }

    @Override
    protected void initViews() {
        Bundle intent=getIntent().getExtras();
        if (intent!=null){
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect!=null)
            if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                mAppPreference.setIsDeepLinking(true);
            }
        }
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mViewAllChallengesTV.setOnClickListener(this);
        mRefreshTV.setOnClickListener(this);
        mAddChallengeTV.setOnClickListener(this);
        mViewVideoTV.setOnClickListener(this);
        mTutorialRL.setOnClickListener(this);
        mBuddyListTV.setOnClickListener(this);
        mClassicBTN.setOnClickListener(this);
        mPopularBTN.setOnClickListener(this);
        mFiltersTV.setOnClickListener(this);
        mLLWallet.setOnClickListener(this);
        mLLWallet.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoChallengePresenter(this);
        mLudoContestList = new ArrayList<>();
        mAllChallengeAdapter = new LudoChallengeAdapter(this, mLudoContestList);
        mLudoContestRV.setLayoutManager(new LinearLayoutManager(this));
        mLudoContestRV.setAdapter(mAllChallengeAdapter);
        mAllChallengeAdapter.setOnItemChildClickListener(this);
        mMyContestList = new ArrayList<>();
        mMyChallengeAdapter = new MyChallengeAdapter(this, mMyContestList);
        mMyContestRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mMyContestRV.setAdapter(mMyChallengeAdapter);
        mMyChallengeAdapter.setOnItemClickListener(this);
        mMyChallengeAdapter.setOnItemChildClickListener(this);
        mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        mUserId = mAppPreference.getString(AppConstant.USER_ID, "");
        if (mContestType == AppConstant.TYPE_LUDO || mContestType == AppConstant.FROM_CREATE_LUDO) {
            mGameId = mAppPreference.getString(AppConstant.LUDO_ID, "");
            mActivityNameTV.setText(R.string.ludo_king);
        }
        if (!mAppPreference.getBoolean(AppConstant.LUDO_SECURE_MSG, false)) {
            showMsg(this, "Ludo Players Kripaya Dhyan De !!!", false, 100, 100);
        }
        String gameId = getIntent().getStringExtra(AppConstant.GAMEID);
        if ((mContestType == AppConstant.FROM_CREATE_LUDO) || (gameId != null && !gameId.isEmpty())) {
            createChallenge();
        }
        if (!mAppPreference.getBoolean(AppConstant.LUDO_VIDEO_SEEN, false)) {
            AppUtilityMethods.setLudoAnimation(this, mViewVideoTV);
            mTutorialRL.setVisibility(View.VISIBLE);
            mRefreshTV.setEnabled(false);
            mAddChallengeTV.setEnabled(false);
        }
        setMode(mMode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_challenge:
                createChallenge();
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
                Intent i = new Intent(this, MyAllChallengesActivity.class);
                i.putExtra(AppConstant.CONTEST_TYPE, mContestType);
                ludoResultActivityResultLauncher.launch(i);
                break;
            case R.id.tv_refresh:
                getLudoContest(true, mSyncProfile);
                break;
            case R.id.tv_buddy_list:
                Intent buddy = new Intent();
                if (mAppPreference.getBoolean(AppConstant.LUDO_BUDDY, false)) {
                    buddy = new Intent(this, BuddyActivity.class);
                } else {
                    buddy = new Intent(this, ImageActivity.class);
                    buddy.putExtra(AppConstant.FROM, AppConstant.LUDO_BUDDY);
                }
                buddy.putExtra(AppConstant.CONTEST_TYPE, mContestType);
                startActivity(buddy);
                break;
            case R.id.tv_view_video:
                showVideoMsg();
                break;
            case R.id.btn_classic:
                setMode(1);
                break;
            case R.id.btn_popular:
                setMode(2);
                break;
            case R.id.tv_filters:
                new LudoFilterDialog(this, onFilterChallengeListener, AppConstant.FROM_WON);
                break;
            case R.id.rl_wallet:
                Intent profile = new Intent(this, WalletActivity.class);
                startActivity(profile);
                break;
        }
    }

    private void setMode(int mode) {
        mMode = mode;
        mClassicBTN.setSelected(false);
        mPopularBTN.setSelected(false);
        if (mode == 1) {
            mClassicBTN.setSelected(true);
        } else {
            mPopularBTN.setSelected(true);
        }
        getLudoContest(true, mSyncProfile = true);
    }

    private void createChallenge() {
        if (getCredential()) {
            mAddChallengeDialog = AppDialog.addChallengeDialog(this, this, mGameCharacterId, mContestType, mMode);
        } else {
            mAddChallengeDialog = AppDialog.addChallengeDialog(this, this, "", mContestType, mMode);
        }
    }

    private void getLudoContest(boolean banner, boolean profile) {
        mGetData = banner || profile;
        String date = AppUtilityMethods.getLudoCurrentDate();
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getContestList(date, String.valueOf(mContestType), banner, String.valueOf(mContestType), profile, mMode, mFilters);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestSuccess(LudoContestResponse responseModel) {
        mSyncProfile = false;
        mLudoContestList.clear();
        mLudoContestList.addAll(responseModel.getResponse());
        mAllChallengeAdapter.notifyDataSetChanged();
        mMyContestList.clear();
        mMyContestList.addAll(responseModel.getMyContests());
        mMyChallengeAdapter.notifyDataSetChanged();

        List<BannerDetails> bannerData = responseModel.getBanner();
        if (bannerData != null && bannerData.size() > 0) {
            mBannerVP.setVisibility(View.VISIBLE);
            setUpAdvertisementViewPager(bannerData);
        } else {
            mBannerVP.setVisibility(GONE);
        }

        if (mMyContestList.size() >= 1) {
            mNoDataTV.setVisibility(View.GONE);
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        if (mGetData) {
            setData(responseModel);
        }
        hideProgress();
    }

    private void setData(LudoContestResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getProfile());
        mCoins = responseModel.getProfile().getCoins();
        if (mCoins != null) {
            double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
            mWalletBalanceTV.setText("₹" + AppUtilityMethods.roundUpNumber(mTotalWalletBal));
        }

        if (responseModel.isIs_popular_enabled()) {
            mLLMode.setVisibility(View.VISIBLE);
        } else {
            mLLMode.setVisibility(View.GONE);
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
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, AppConstant.LUDO_CREATE);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties properties = new Properties();
            properties
                    // tracking integer
                    .addAttribute("game", "LudoKing")
                    // tracking string
                    .addAttribute("gametype", mMode)
                    // tracking Date
                    .addAttribute("gamecreatedDate", new Date())
                    // tracking double
                    .addAttribute("amount", mAmount)
                    .addAttribute("LudoKing", "Waiting For Opponent");

            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameCreated", properties);
            showMsg(this, getString(R.string.text_ludo_created), false, AppConstant.FROM_ADD, 0);
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
            Map<String, Object> eventParameters2 = new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.REVENUE, mAmount); // Estimated revenue from the purchase. The revenue value should not contain comma separators, currency, special characters, or text.
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, AppConstant.LUDO_ACCEPT);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AppConstant.INVEST, eventParameters2);
            Properties properties = new Properties();
            properties
                    .addAttribute("game", "LudoKing")
                    .addAttribute("gametype", mMode)
                    .addAttribute("gamecreatedDate", new Date())
                    .addAttribute("amount", mAmount);
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameAccepted", properties);
            showMsg(this, getString(R.string.text_ludo_accepted), false, AppConstant.FROM_UPDATE, 0);
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
            showMsg(this, getString(R.string.text_ludo_challenge_cancel), false, AppConstant.FROM_CANCEL, 0);
            Properties properties = new Properties();
            properties.addAttribute("game", "LudoKing");
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
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLudoNotificationReceiver);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onAcceptClicked(int position) {
        if (new NetworkStatus(this).isInternetOn()) {
            if (getCredential()) {
                acceptChallenge(position);
            } else {
                mUpdateGameUsernameDialog = AppDialog.updateGameUsernameDialog(this, this, position);
            }
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void acceptChallenge(int position) {
        double walletBalance = mCoins.getBonus() + mCoins.getDeposit() + mCoins.getWinning();
        if (mLudoContestList.get(position).getEntryFees() <= walletBalance) {
            OpponentLudoRequest request = new OpponentLudoRequest(mGameCharacterId);
            acceptAlert(this, mLudoContestList.get(position).getEntryFees(), mLudoContestList.get(position).getId(), request, AppConstant.FROM_ADD);
        } else {
            AppUtilityMethods.showRechargeMsg(this, "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
        }
    }

    private void openResult(int position) {
        Intent intent = new Intent(this, LudoResultActivity.class);
        intent.putExtra(AppConstant.LUDO_CONTEST, mMyContestList.get(position));
        intent.putExtra(AppConstant.CONTEST_TYPE, mContestType);
        ludoResultActivityResultLauncher.launch(intent);
    }

    @Override
    public void onPlayClicked(int position) {
        openResult(position);
    }

    @Override
    public void onCancelClick(int position) {
        if (new NetworkStatus(this).isInternetOn()) {
            OpponentLudoRequest request = new OpponentLudoRequest("");
            acceptAlert(this, 0, mMyContestList.get(position).getId(), request, AppConstant.FROM_CANCEL);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReviewClick(int position) {
        checkChallengeStatus(position);
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        checkChallengeStatus(position);
    }

    private void checkChallengeStatus(int position) {
        boolean mIsCaptain = mUserId.equals(mMyContestList.get(position).getCaptainId());
        if (!mMyContestList.get(position).isResultDeclared() && !mMyContestList.get(position).isCancelled()) {
            if (mMyContestList.get(position).getAdminStatus() == 1) {
                AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
            } else if (mMyContestList.get(position).getAdminStatus() == 2) {
                if (((mIsCaptain && ((mMyContestList.get(position).getCaptainError() != null && !TextUtils.isEmpty(mMyContestList.get(position).getCaptainError().getImg())) || mMyContestList.get(position).isCaptainDeclared())))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
                } else if (((!mIsCaptain && ((mMyContestList.get(position).getOpponentError() != null && !TextUtils.isEmpty(mMyContestList.get(position).getOpponentError().getImg())) || mMyContestList.get(position).isOpponentDeclared())))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
                } else {
                    openResult(position);
                }
            } else if ((mIsCaptain && mMyContestList.get(position).isCaptainDeclared())) {
                showMsg(this, getString(R.string.text_waiting_opponent_result), false, AppConstant.FROM_LUDO_RESULT, position);
            } else if ((!mIsCaptain && mMyContestList.get(position).isOpponentDeclared())) {
                showMsg(this, getString(R.string.text_waiting_opponent_result), false, AppConstant.FROM_LUDO_RESULT, position);
            } else {
                openResult(position);
            }
        }
    }

    public void acceptAlert(final Activity activity, double entryFee, String ludoId, OpponentLudoRequest request, int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        if (from == AppConstant.FROM_ADD) {
            tv_msg.setText(entryFee + getString(R.string.text_accept_confirm_ludo));
        } else if (from == AppConstant.FROM_CANCEL) {
            tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
        }
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (from == AppConstant.FROM_ADD) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.acceptContest(ludoId, request);
            } else if (from == AppConstant.FROM_CANCEL) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.cancelContest(ludoId);
            }
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    public void showMsg(final Context activity, String msg, boolean isCancel, int from, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.challenge_add_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        if (from == AppConstant.FROM_ADD) {
            tv_help.setText(getString(R.string.text_help_captain));
        } else if (from == AppConstant.FROM_UPDATE) {
            tv_help.setText(getString(R.string.text_help_opponent));
        } else if (from == AppConstant.FROM_LUDO_RESULT) {
            tv_msg.setText(getString(R.string.ludo_result));
            tv_help.setText(msg);
        } else if (from == 100) {
            mAppPreference.setBoolean(AppConstant.LUDO_SECURE_MSG, true);
            tv_help.setText("Ab se apko opponent ka naam random dikhega aur uska real name nahi dikhega. Isse KhiladiAdda platform and apke challenges aur safe and secure ho jayenge...\n!!!Dhanyawad!!!\n\nFrom now onwards opponent name will not appear and random name be displayed for more security of your challenges.\n!!!ThankYou!!!");
        } else {
            tv_help.setVisibility(View.GONE);
        }
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (from == AppConstant.FROM_LUDO_RESULT) {
                openResult(position);
            } else if (from != 100) {
                getLudoContest(true, mSyncProfile = true);
            }
        });
        dialog.show();
    }

    private BroadcastReceiver mLudoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getLudoContest(true, mSyncProfile = true);
        }
    };

    @Override
    public void onAddChallengeListener(String gameUsername, String amount, int mode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date startDate = new Date();
        String startTime = AppUtilityMethods.getConvertDateTimeForServer(dateFormat.format(startDate));
        Date endDate = new Date();
        endDate.setTime(startDate.getTime() + 3600000);
        String endTime = AppUtilityMethods.getConvertDateTimeForServer(dateFormat.format(endDate));
        LudoContestRequest request = new LudoContestRequest(endTime, Long.parseLong(amount), mGameId, gameUsername, mContestType, startTime, mode, 0);
        if (new NetworkStatus(this).isInternetOn()) {
            mAmount = amount;
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.addChallenge(request);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showVideoMsg() {
        mTutorialRL.setVisibility(View.GONE);
        mViewVideoTV.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mRefreshTV.setEnabled(true);
        mAddChallengeTV.setEnabled(true);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.video_help);
        Button videoBTN = dialog.findViewById(R.id.btn_video);
        AppUtilityMethods.setAnimation(this, videoBTN);
        videoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            AppUtilityMethods.openLudoVideo(LudoChallengeActivity.this);
        });
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(R.string.text_ludo_help_video);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> dialog.dismiss());
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    private boolean getCredential() {
        ProfileDetails mProfileDetails = mAppPreference.getProfileData();
        if (mProfileDetails != null && mProfileDetails.getCredentials() != null) {
            List<GameCredential> credentialList = mProfileDetails.getCredentials();
            if (credentialList != null && credentialList.size() > 0) {
                for (int i = 0; i < credentialList.size(); i++) {
                    if (credentialList.get(i).getGameId() != null && credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.LUDO_ID, ""))) {
                        mGameCharacterId = credentialList.get(i).getGameCharacterId();
                    }
                }
            }
            return !TextUtils.isEmpty(mGameCharacterId);
        } else {
            return TextUtils.isEmpty(mGameCharacterId);
        }
    }

    @Override
    public void onUpdateGameUsername(String username, int position) {
        mGameCharacterId = username;
        mSyncProfile = true;
        acceptChallenge(position);
        if (mUpdateGameUsernameDialog != null && mUpdateGameUsernameDialog.isShowing()) {
            mUpdateGameUsernameDialog.dismiss();
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

    ActivityResultLauncher<Intent> ludoResultActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (mAppPreference.getBoolean(AppConstant.IS_DATA_CHANGE, false)) {
                    getLudoContest(true, mSyncProfile = true);
                }
            });

    private final IOnFilterChallengeListener onFilterChallengeListener = filter -> {
        mFilters = filter;
        getLudoContest(true, false);
    };

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