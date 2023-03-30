package com.khiladiadda.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.gameleague.GameWebActivity;
import com.khiladiadda.gameleague.GamesFinalResultActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.rummy.RummyGameWebActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class RummyDialog extends BottomSheetDialog implements View.OnClickListener {

    private Button mPlayBTN;
    private TextView mEntryFeeTV, mTotalBalanceTV, mDepWinTV, mTwoPlayer, mMorePlayer;
    private MaterialCardView mCancelBtn;
    private Context mContext;
    private String mEntryFee, mTotalBal, mDepWinAmount, cardId, token, refreshToken;
    private int numPlayer;
    private OnPlayClick mOnPlayClicked;

    public RummyDialog(@NonNull Context context, String entryFee, String totalBal, String depWinAmount, String cardId, String token, String refreshToken, int theme, int numPlayer, OnPlayClick mOnPlayClicked) {
        super(context);
        this.mContext = context;
        this.mEntryFee = entryFee;
        this.mTotalBal = totalBal;
        this.mDepWinAmount = depWinAmount;
        this.cardId = cardId;
        this.token = token;
        this.refreshToken = refreshToken;
        this.numPlayer = numPlayer;
        this.mOnPlayClicked = mOnPlayClicked;
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
        mPlayBTN = findViewById(R.id.btn_play);
        mEntryFeeTV = findViewById(R.id.tv_entry_fee);
        mTotalBalanceTV = findViewById(R.id.tv_total_wallet_balance);
        mDepWinTV = findViewById(R.id.tv_deposit);
        mCancelBtn = findViewById(R.id.mcv_cancel);
        mTwoPlayer = findViewById(R.id.tv_two_players);
        mMorePlayer = findViewById(R.id.tv_more_players);
        if (numPlayer == 2){
            mTwoPlayer.setTextColor(Color.parseColor("#ffffff"));
            mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
            mMorePlayer.setTextColor(Color.parseColor("#000000"));
            mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
        }else {
            mTwoPlayer.setTextColor(Color.parseColor("#000000"));
            mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
            mMorePlayer.setTextColor(Color.parseColor("#ffffff"));
            mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
        }
    }

    private void initVariables() {
        final DecimalFormat decfor = new DecimalFormat("0.00");

        mPlayBTN.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
//        mTwoPlayer.setOnClickListener(this);
//        mMorePlayer.setOnClickListener(this);
        mEntryFeeTV.setText("₹" + decfor.format(Float.parseFloat(mEntryFee)/100));
        mTotalBalanceTV.setText("₹" + mTotalBal);
        mDepWinTV.setText("₹" + mDepWinAmount);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mcv_cancel:
                dismiss();
                break;
            case R.id.btn_play:
                Intent intLeaderboard = new Intent(mContext, RummyGameWebActivity.class);
                try {
                    intLeaderboard.putExtra("info", convertToBase64());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, Object> eventParameters2 = new HashMap<>();
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
                dismiss();
                break;
            case R.id.tv_two_players:
                mTwoPlayer.setTextColor(Color.parseColor("#ffffff"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                mMorePlayer.setTextColor(Color.parseColor("#000000"));
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                break;
            case R.id.tv_more_players:
                mTwoPlayer.setTextColor(Color.parseColor("#000000"));
                mTwoPlayer.setBackground(mContext.getDrawable(R.drawable.button_background_notselected));
                mMorePlayer.setTextColor(Color.parseColor("#ffffff"));
                mMorePlayer.setBackground(mContext.getDrawable(R.drawable.button_background_selected));
                break;
        }
    }

    private String convertToBase64() throws UnsupportedEncodingException {
        String req = "{ \"accessToken\": \"" + token + "\", \"refreshToken\": \"" + refreshToken + "\", \"stakeId\": \"" + cardId + "\", \"app_version\": \"" + AppSharedPreference.getInstance().getMasterData().getResponse().getVersion().getAppVersion() + "\", \"type\": 1}";
        byte[] data = req.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public interface OnPlayClick{
        public void onPlayClicked();
    }
}