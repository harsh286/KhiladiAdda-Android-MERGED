package com.khiladiadda.rummy;
import static android.view.View.GONE;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.RummyDialog;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.interfaces.IOnItemReplayClickListener;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyDetails;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.profile.ProfilePresenter;
import com.khiladiadda.profile.interfaces.IProfilePresenter;
import com.khiladiadda.profile.interfaces.IProfileView;
import com.khiladiadda.rummy.adapter.RummyLiveTableAdpter;
import com.khiladiadda.rummy.adapter.RummyAdapter;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.LocationCheckUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
public class RummyActivity extends BaseActivity implements IRummyView,IOnItemClickListener,RummyDialog.OnPlayClick,
        IProfileView,IOnItemReplayClickListener,LocationCheckUtils.IOnAdressPassed{
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_points)
    TextView mPointsTV;
    @BindView(R.id.tv_pool)
    TextView mPoolTV;
    @BindView(R.id.tv_deal)
    TextView mDealTV;
    @BindView(R.id.tv_one)
    TextView mOneTV;
    @BindView(R.id.tv_two)
    TextView mTwoTV;
    @BindView(R.id.tv_three)
    TextView mThreeTV;
    @BindView(R.id.rv_rummy)
    RecyclerView mRummyRV;
    @BindView(R.id.ll_mode_option)
    LinearLayout mModeOptionLL;
    @BindView(R.id.tv_how_to_play)
    TextView mHowToPlayTv;
    @BindView(R.id.tv_history)
    TextView mHistoryTv;
    @BindView(R.id.rv_live_table_rummy)
    RecyclerView mRummyLiveTableRV;
    @BindView(R.id.rl_live_table_rummy)
    RelativeLayout mRummyLiveTableRL;
    private RummyAdapter mAdapter;
    private RummyLiveTableAdpter mRummyLiveTableAdpter;
    private List<RummyDetails> mList;
    private List<RummyDetails> mLiveTableList = new ArrayList<>();
    private IRummyPresenter mPresenter;
    private IProfilePresenter mProfilePresenter;
    private String mType, mRefreshToken = "";
    private int mMode = 1, pos = 0;
    private long mLastClickTime = 0;
    private boolean isPlayed = false;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails>mAdvertisementsList=new ArrayList<>();
    private Handler mHandler;
    @BindView(R.id.nudge)
    NudgeView mNV;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRummyRefreshNotificationReceiver, new IntentFilter(AppConstant.RUMMY_PACKAGE));
    }
    @Override
    protected int getContentView(){
        return R.layout.activity_rummy;
    }
    @Override
    protected void onStart(){
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }
    @Override
    protected void initViews() {
        mActivityNameTV.setText("Rummy Adda");
        mBackIV.setOnClickListener(this);
        mPointsTV.setOnClickListener(this);
        mPoolTV.setOnClickListener(this);
        mDealTV.setOnClickListener(this);
        mOneTV.setOnClickListener(this);
        mTwoTV.setOnClickListener(this);
        mThreeTV.setOnClickListener(this);
        mHowToPlayTv.setOnClickListener(this);
        mHistoryTv.setOnClickListener(this);

    }
    @Override
    protected void initVariables() {
        LocationCheckUtils.initialize(this, this, this);
        AppUtilityMethods.deleteCache(this);
        mPresenter = new RummyPresenter(this);
        mProfilePresenter = new ProfilePresenter(this);
        mList = new ArrayList<>();
        mAdapter = new RummyAdapter(this, mList, mMode);
        mRummyRV.setLayoutManager(new LinearLayoutManager(this));
        mRummyRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        /*Live Table Adapter call*/
        mRummyLiveTableAdpter = new RummyLiveTableAdpter(this, mLiveTableList, mMode);
        mRummyLiveTableRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRummyLiveTableRV.setAdapter(mRummyLiveTableAdpter);
        mRummyLiveTableAdpter.setOnItemClickListener(this);
        setMode(14);
        setModeOptionOne();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_points:
                mMode = 1;
                mModeOptionLL.setVisibility(View.VISIBLE);
                mThreeTV.setVisibility(View.GONE);
                setMode(14);
                setModeOptionOne();
                break;
            case R.id.tv_pool:
                mMode = 2;
                mModeOptionLL.setVisibility(View.VISIBLE);
                mThreeTV.setVisibility(View.VISIBLE);
                setMode(24);
                setModeOptionTwo();
                break;
            case R.id.tv_deal:
                mMode = 3;
                mModeOptionLL.setVisibility(View.GONE);
                setMode(34);
                break;
            case R.id.tv_one:
                if (mMode == 1) {
                    setMode(14);
                } else if (mMode == 2) {
                    setMode(24);
                } else
                    setMode(34);
                break;
            case R.id.tv_two:
                if (mMode == 1) {
                    setMode(15);
                } else if (mMode == 2) {
                    setMode(25);
                } else
                    setMode(34);
                break;
            case R.id.tv_three:
                if (mMode == 1) {
                    setMode(16);
                } else if (mMode == 2) {
                    setMode(26);
                } else
                    setMode(34);
                break;
            case R.id.tv_how_to_play:
                AppUtilityMethods.showRummyTooltip(this, mHowToPlayTv, AppConstant.FROM_MAIN_ACTIVITY);
                break;
            case R.id.tv_history:
                startActivity(new Intent(this, RummyHistoryActivity.class));
        }
    }

    private void setMode(int mode) {
        mPointsTV.setSelected(false);
        mPoolTV.setSelected(false);
        mDealTV.setSelected(false);
        mOneTV.setTextColor(getResources().getColor(R.color.white));
        mOneTV.setTextAppearance(this, R.style.RummyModeButton);
        mOneTV.setSelected(false);
        mTwoTV.setTextColor(getResources().getColor(R.color.white));
        mTwoTV.setTextAppearance(this, R.style.RummyModeButton);
        mTwoTV.setSelected(false);
        mThreeTV.setTextColor(getResources().getColor(R.color.white));
        mThreeTV.setTextAppearance(this, R.style.RummyModeButton);
        mThreeTV.setSelected(false);
        switch (mode) {
            case 14:
                mType = "pt_S13";
                setPoints();
                setOne();
                break;
            case 15:
                mType = "mtwc_S13";
                setPoints();
                setTwo();
                break;
            case 16:
                mType = "pool51_S13";
                setPoints();
                setThree();
                break;
            case 24:
                mType = "pool51_S13";
                setPool();
                setOne();
                break;
            case 25:
                mType = "pool101_S13";
                setPool();
                setTwo();
                break;
            case 26:
                mType = "pool201_S13";
                setPool();
                setThree();
                break;
            case 34:
                mType = "deal_S13";
                setDeal();
                setOne();
                break;
        }
    }

    private void setModeOptionOne() {
        mOneTV.setText("13 Cards");
        mTwoTV.setText("2 Jokers");
        mThreeTV.setVisibility(View.GONE);
    }

    private void setModeOptionTwo() {
        mOneTV.setText("Pool 51");
        mTwoTV.setText("Pool 101");
        mThreeTV.setVisibility(View.VISIBLE);
    }

    private void setOne() {
        mOneTV.setSelected(true);
        mOneTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }

    private void setTwo() {
        mTwoTV.setSelected(true);
        mTwoTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }

    private void setThree() {
        mThreeTV.setSelected(true);
        mThreeTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }
    private void setDeal() {
        mDealTV.setSelected(true);
        mDealTV.setTextColor(getResources().getColor(R.color.battle_red));
        mPointsTV.setTextColor(Color.parseColor("#9A9797"));
        mPoolTV.setTextColor(Color.parseColor("#9A9797"));
    }
    private void setPool(){
        mPoolTV.setSelected(true);
        mPoolTV.setTextColor(getResources().getColor(R.color.battle_red));
        mPointsTV.setTextColor(Color.parseColor("#9A9797"));
        mDealTV.setTextColor(Color.parseColor("#9A9797"));
    }
    private void setPoints() {
        mPointsTV.setSelected(true);
        mPointsTV.setTextColor(getResources().getColor(R.color.battle_red));
        mPoolTV.setTextColor(Color.parseColor("#9A9797"));
        mDealTV.setTextColor(Color.parseColor("#9A9797"));
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRummyList(mType);
        } else {
            Snackbar.make(mActivityNameTV,R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getRefreshToken() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRummyRefreshToken();
        } else {
            Snackbar.make(mActivityNameTV,R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestSuccess(RummyResponse responseModel) {
        hideProgress();
        mLiveTableList.clear();
        mList.clear();
        if (responseModel.isStatus()) {
            mList.addAll(responseModel.getResponse());
            mLiveTableList.addAll(responseModel.getmLiveTableRes());
            mAdapter.changeType(mMode);
            mRummyLiveTableAdpter.changeType(mMode);
            mAdapter.notifyDataSetChanged();
            mRummyLiveTableAdpter.notifyDataSetChanged();
            if (mLiveTableList!=null&&mLiveTableList.size()>0){
                mRummyLiveTableRL.setVisibility(View.VISIBLE);
            } else {
                mRummyLiveTableRL.setVisibility(GONE);
            }
            List<BannerDetails> bannerData = responseModel.getBanner();
            if (bannerData != null && bannerData.size() > 0) {
                mBannerVP.setVisibility(View.VISIBLE);
                setUpAdvertisementViewPager(bannerData);
            } else {
                mBannerVP.setVisibility(GONE);
            }
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }
    @Override
    public void onGetContestFailure(ApiError error) {
        hideProgress();
    }
    @Override
    public void onGetContestRefreshTokenSuccess(RummyRefreshTokenMainResponse responseModel) {
        if (responseModel.isStatus()) {
            mRefreshToken = responseModel.getResponse();
        } else {
            Toast.makeText(this, "" + responseModel.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onGetContestRefreshTokenFailure(ApiError error){

    }

    @Override
    public void onGetContestCheckGameSuccess(RummyCheckGameResponse responseModel) {

    }
    @Override
    public void onGetContestCheckGameFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        if (LocationCheckUtils.getInstance().hasLocationPermission()){
            LocationCheckUtils.getInstance().requestNewLocationData();
            pos = position;
            /*send request with card id.card id used for only balance insufficient balance validation*/
            openBottomDialog(pos);
        } else {
            AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
        }
    }
    private void openBottomDialog(int position) {
        Coins mCoins=mAppPreference.getProfileData().getCoins();
        double mTotalWalletBal=mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        double mDepWinAmount = mCoins.getDeposit() + mCoins.getWinning();
        RummyDialog addExpenseDialog;
        //ADD BONUS DYNAMIC
        addExpenseDialog=new RummyDialog(this, String.valueOf(mList.get(position).getEntryFee()), String.format("%.2f", mTotalWalletBal), String.format("%.2f", mDepWinAmount), AppSharedPreference.getInstance().getSessionToken(), mRefreshToken, R.style.CustomBottomSheetDialogTheme, this, mList.get(position).getmPlayersDetails(), position,LocationCheckUtils.getInstance().getmLatitute(),LocationCheckUtils.getInstance().getmLongitude(),mCoins.getBonus(),mList.get(position).getBonus());
        addExpenseDialog.setCancelable(true);
        addExpenseDialog.setCanceledOnTouchOutside(false);
        addExpenseDialog.show();
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
    /*When user rejoin the match then this method is call*/
    private void onReJoinRummyDirect(){
        String encodeRequest = convertToBase64(AppSharedPreference.getInstance().getSessionToken(), mRefreshToken, "active");
        Intent intLeaderboard = new Intent(this, RummyGameWebActivity.class);
        intLeaderboard.putExtra("info", encodeRequest);
        startActivity(intLeaderboard);
    }

    private String convertToBase64(String token, String refreshToken, String cardId) {
        try {
            String req = "{ \"accessToken\": \"" + token + "\", \"refreshToken\": \"" + refreshToken + "\", \"stakeId\": \"" + cardId + "\", \"app_version\": \"" + AppSharedPreference.getInstance().getMasterData().getResponse().getVersion().getAppVersion() + "\", \"type\": 1,\"requestVia\": 4}";
            byte[] data = req.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            Log.e("TAG", "convertToBase64: " + e.getLocalizedMessage());
        }
        return "";
    }

    private void moveToNextAd(int i) {
        mBannerVP.setCurrentItem(i, true);
        mHandler.postDelayed(() -> {
            int currentItem = mBannerVP.getCurrentItem();
            moveToNextAd((currentItem + 1) % mAdvertisementsList.size() == 0 ? 0 : currentItem + 1);
        }, 10000);
    }

    @Override
    public void onPlayClicked() {
        isPlayed = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayed) {
            getProfile();
        }
        getData();

    }

    private void getProfile() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mProfilePresenter.getProfile();
        } else {
            Snackbar.make(mBackIV,getString(R.string.error_internet), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpdatePasswordComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdatePasswordFailure(ApiError error) {

    }

    @Override
    public void onUpdateDOBComplete(ProfileResponse responseModel) {

    }

    @Override
    public void onUpdateDOBFailure(ApiError error) {

    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        hideProgress();
        mAppPreference.setProfileData(responseModel.getResponse());

    }

    @Override
    public void onProfileFailure(ApiError error) {

    }

    @Override
    public void onApplyVoucherComplete(BaseResponse responseModel) {

    }

    @Override
    public void onApplyVoucherFailure(ApiError error) {

    }

    @Override
    public void onSendOtpComplete(BaseResponse responseModel) {

    }

    @Override
    public void onSendOtpFailure(ApiError error) {

    }

    @Override
    public void onVerifyEmailComplete(BaseResponse responseModel) {

    }

    @Override
    public void onVerifyEmailFailure(ApiError error) {

    }

    @Override
    public void onUpdateEmailComplete(BaseResponse responseModel) {

    }

    @Override
    public void onUpdateEmailFailure(ApiError error) {

    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onReplayItemClick(View view, int position, int tag) {
        onReJoinRummyDirect();
    }

    private final BroadcastReceiver mRummyRefreshNotificationReceiver=new BroadcastReceiver() { // 77
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String mFrom=intent.getStringExtra(AppConstant.FROM);
                if (mFrom.equalsIgnoreCase(AppConstant.RUMMY_UPDATE)){
                    getData();
                    getProfile();
                }
            }catch (Exception e){
               e.printStackTrace();
            }
        }
    };
    @Override
    public void iOnAddressSuccess(){

    }
    @Override
    public void iOnAddressFailure(){

    }
}