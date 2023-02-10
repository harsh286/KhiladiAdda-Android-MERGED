package com.khiladiadda.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.gameleague.GameWebActivity;
import com.khiladiadda.gameleague.GamesFinalResultActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.rummy.RummyGameWebActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.io.UnsupportedEncodingException;

public class RummyDialog extends BottomSheetDialog implements View.OnClickListener {

    private Button mPlayBTN;
    private TextView mEntryFeeTV, mTotalBalanceTV, mDepWinTV;
    private MaterialCardView mCancelBtn;
    private Context mContext;
    private String mEntryFee, mTotalBal, mDepWinAmount, cardId, token, refreshToken;

    public RummyDialog(@NonNull Context context, String entryFee, String totalBal, String depWinAmount, String cardId, String token, String refreshToken) {
        super(context);
        this.mContext = context;
        this.mEntryFee = entryFee;
        this.mTotalBal = totalBal;
        this.mDepWinAmount = depWinAmount;
        this.cardId = cardId;
        this.token = token;
        this.refreshToken = refreshToken;
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
    }

    private void initVariables() {
        mPlayBTN.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mEntryFeeTV.setText("₹" + mEntryFee);
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
                mContext.startActivity(intLeaderboard);
                dismiss();
                break;
        }
    }

    private String convertToBase64() throws UnsupportedEncodingException {
        String req = "{ \"accessToken\": \"" + token + "\", \"refreshToken\": \"" + refreshToken + "\", \"stakeId\": \"" + cardId + "\", \"app_version\": \"" + AppSharedPreference.getInstance().getMasterData().getResponse().getVersion().getAppVersion() + "\", \"type\": 1 }";
        byte[] data = req.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

}