package com.khiladiadda.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.PlayersDetails;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.rummy.RummyGameWebActivity;
import com.khiladiadda.rummy.RummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RummyDialog extends BottomSheetDialog implements View.OnClickListener, IRummyView {
    private Button mPlayBTN;
    private TextView mEntryFeeTV, mTotalBalanceTV, mDepWinTV, mTwoPlayer, mMorePlayer, mWinningAmountTV;
    private MaterialCardView mCancelBtn;
    private Context mContext;
    private String mEntryFee, mWinningAmount, mTotalBal, mDepWinAmount, cardId, token, refreshToken;
    private OnPlayClick mOnPlayClicked;
    private String mEntryAmount;
    private List<PlayersDetails> mPlayerDetails;
    private DecimalFormat decfor;
    private IRummyPresenter mPresenter;
    private long mLastClickTime = 0;
    private int itemPosition, mNumberOfPlayersPosition;
    private Dialog mDialog;

    private String mLatitute, mLongitude;


    public RummyDialog(@NonNull Context context, String mEntryFee, String totalBal, String mDepWinAmount, String token, String refreshToken, int theme, OnPlayClick mOnPlayClicked, List<PlayersDetails> mPlayerDetails, int itemPosition, String mLatitute, String mLongitude) {
        super(context);
        this.mContext = context;
        this.mEntryFee = mEntryFee;
        this.mTotalBal = totalBal;
        this.mDepWinAmount = mDepWinAmount;
        this.token = token;
        this.refreshToken = refreshToken;
        this.mOnPlayClicked = mOnPlayClicked;
        this.mPlayerDetails = mPlayerDetails;
        this.itemPosition = itemPosition;
        this.mLatitute = mLatitute;
        this.mLongitude = mLongitude;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
        initVariables();
    }

    protected int getContentView() {
        return R.layout.dialog_rummy;
    }

    protected void initViews() {
        mPresenter = new RummyPresenter(this);
        mPlayBTN = findViewById(R.id.btn_play);
        mEntryFeeTV = findViewById(R.id.tv_entry_fee);
        mTotalBalanceTV = findViewById(R.id.tv_total_wallet_balance);
        mDepWinTV = findViewById(R.id.tv_deposit);
        mCancelBtn = findViewById(R.id.mcv_cancel);
        mTwoPlayer = findViewById(R.id.tv_two_players);
        mMorePlayer = findViewById(R.id.tv_more_players);
        mWinningAmountTV = findViewById(R.id.tv_winning_amount);
        if (mPlayerDetails.size() != 2) {
            if (mPlayerDetails.get(0).getnPlayers() == 2) {
                mTwoPlayer.setTextColor(Color.parseColor("#ffffff"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                mMorePlayer.setTextColor(Color.parseColor("#000000"));
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                mMorePlayer.setClickable(false);
            } else {
                mTwoPlayer.setTextColor(Color.parseColor("#000000"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                mMorePlayer.setTextColor(Color.parseColor("#ffffff"));
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                mTwoPlayer.setClickable(false);
            }
        } else {
            mTwoPlayer.setClickable(true);
            mMorePlayer.setClickable(true);
        }
    }

    private void initVariables() {
        decfor = new DecimalFormat("0.00");
        mPlayBTN.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mTwoPlayer.setOnClickListener(this);
        mMorePlayer.setOnClickListener(this);
        mWinningAmount = String.valueOf(mPlayerDetails.get(0).getMaxWin());
        mNumberOfPlayersPosition = 0;
        cardId = mPlayerDetails.get(0).getCardId();
        modeSwitch();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mcv_cancel:
                dismiss();
                break;
            case R.id.btn_play:
                if (new NetworkStatus(mContext).isInternetOn()) {
                    showProgress(mContext.getString(R.string.txt_progress_authentication));
                    mPresenter.getCheckGameStatus(mPlayerDetails.get(mNumberOfPlayersPosition).get_id(),mLatitute,mLongitude);
                } else {
                    Snackbar.make(mPlayBTN, mContext.getString(R.string.error_internet), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_two_players:
                mTwoPlayer.setTextColor(Color.parseColor("#ffffff"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                mMorePlayer.setTextColor(Color.parseColor("#000000"));
                mWinningAmount = String.valueOf(mPlayerDetails.get(0).getMaxWin());
                mNumberOfPlayersPosition = 0;
                cardId = mPlayerDetails.get(0).getCardId();
                modeSwitch();
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                break;
            case R.id.tv_more_players:
                mTwoPlayer.setTextColor(Color.parseColor("#000000"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                mMorePlayer.setTextColor(Color.parseColor("#ffffff"));
                mWinningAmount = String.valueOf(mPlayerDetails.get(1).getMaxWin());
                mNumberOfPlayersPosition = 1;
                cardId = mPlayerDetails.get(1).getCardId();
                modeSwitch();
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                break;
        }
    }
    private void modeSwitch(){
        mWinningAmountTV.setText("₹" + decfor.format(Float.parseFloat(mWinningAmount) / 100));
        mEntryFeeTV.setText("₹" + decfor.format(Float.parseFloat(mEntryFee) / 100));
        mEntryAmount = decfor.format(Float.parseFloat(mEntryFee) / 100);
        mTotalBalanceTV.setText("₹" + mTotalBal);
        mDepWinTV.setText("₹" + mDepWinAmount);
    }
    private String convertToBase64(String cardId) throws UnsupportedEncodingException {
        String req = "{ \"accessToken\": \"" + token + "\", \"refreshToken\": \"" + refreshToken + "\", \"stakeId\": \"" + cardId + "\", \"app_version\": \"" + AppSharedPreference.getInstance().getMasterData().getResponse().getVersion().getAppVersion() + "\", \"type\": 1,\"requestVia\": 4}";
        byte[] data = req.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    @Override
    public void onGetContestSuccess(RummyResponse responseModel) {

    }

    @Override
    public void onGetContestFailure(ApiError error) {

    }

    @Override
    public void onGetContestRefreshTokenSuccess(RummyRefreshTokenMainResponse responseModel) {

    }

    @Override
    public void onGetContestRefreshTokenFailure(ApiError error) {
    }

    @Override
    public void onGetContestCheckGameSuccess(RummyCheckGameResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            callRummyWebView("active");

        } else {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            callRummyWebView(cardId);

        }
    }

    private void callRummyWebView(String cardId) {
        Intent intLeaderboard = new Intent(mContext, RummyGameWebActivity.class);
        /*Below code for check user wallet balance if user balance is less then from entry fee the show wallet popup*/
        if (Double.parseDouble(mTotalBal) >= Double.parseDouble(mEntryAmount)) {
            try {
                intLeaderboard.putExtra("info",convertToBase64(cardId));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, Object> eventParameters2=new HashMap<>();
            eventParameters2.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR); // Currency code
            eventParameters2.put(AppConstant.GAME, AppConstant.RUMMY);
            eventParameters2.put(AppConstant.EntryFee, mEntryFee);
            AppsFlyerLib.getInstance().logEvent(mContext, AppConstant.INVEST, eventParameters2);
            //Mo Engage
            Properties mProperties = new Properties();
            mProperties.addAttribute(AppConstant.GAMETYPE, AppConstant.RUMMY);
            mProperties.addAttribute("EnrtyFee", mEntryFee);
            MoEAnalyticsHelper.INSTANCE.trackEvent(mContext, AppConstant.RUMMY, mProperties);
            mContext.startActivity(intLeaderboard);
            mOnPlayClicked.onPlayClicked();
        } else {
            AppDialog.showInsufficientRummyDialog((Activity) mContext);
        }
        dismiss();
    }

    @Override
    public void onGetContestCheckGameFailure(ApiError error) {

    }

    public interface OnPlayClick {
        public void onPlayClicked();
    }

    private void showProgress(String message) {
        hideProgress();
        mDialog = AppDialog.getAppProgressDialog(mContext, message);
        mDialog.show();
    }

    private void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}