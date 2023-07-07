package com.khiladiadda.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.quiz.adapters.PrizePoolRVAdapter;
import com.khiladiadda.utility.AppConstant;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CallBreakDialog extends BottomSheetDialog implements View.OnClickListener {

    private Button mCancelBTN, mConfirmBTN;
    private TextView mEntryFeeTV, mTotalBalanceTV, mDepWinTV;
    private RecyclerView mPrizePoolRV;
    private Context mContext;
    private int pos;
    private String mEntryFee, mTotalBal, mDepWinAmount;
    private List<PrizePoolBreakthrough> mPrizePoolList;
    private CallBreakDetails mCallBreakDetails;
    private IOnConfirmClickListener mIOnConfirmClickListeener;

    public CallBreakDialog(@NonNull Context context) {
        super(context);
    }

    public CallBreakDialog(@NonNull Context context, String entryFee, String totalBal, String depWinAmount, List<PrizePoolBreakthrough> prizePoolBreakthroughList, CallBreakDetails mCallBreakDetails
            , IOnConfirmClickListener mIOnConfirmClickListeener, int pos) {
        super(context);
        this.mContext = context;
        this.mEntryFee = entryFee;
        this.mTotalBal = totalBal;
        this.mDepWinAmount = depWinAmount;
        this.mPrizePoolList = prizePoolBreakthroughList;
        this.mCallBreakDetails = mCallBreakDetails;
        this.mIOnConfirmClickListeener = mIOnConfirmClickListeener;
        this.pos = pos;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
        initVariables();
    }

    protected int getContentView() {
        return R.layout.dialog_call_break;
    }

    protected void initViews() {
        mCancelBTN = findViewById(R.id.btn_cancel);
        mConfirmBTN = findViewById(R.id.btn_confirm);
        mEntryFeeTV = findViewById(R.id.tv_entry_fee);
        mTotalBalanceTV = findViewById(R.id.tv_total_wallet_balance);
        mDepWinTV = findViewById(R.id.tv_deposit);
        mPrizePoolRV = findViewById(R.id.rv_prize_pool_breakup);
    }

    private void initVariables() {
        mCancelBTN.setOnClickListener(this);
        mConfirmBTN.setOnClickListener(this);
        mEntryFeeTV.setText("Entry Fee   ₹" + mEntryFee);
        mTotalBalanceTV.setText("Total Wallet Balance   ₹" + mTotalBal);
        mDepWinTV.setText("Deposit + Winning   ₹" + mDepWinAmount);

        if (mPrizePoolList.size() > 0) {
            PrizePoolRVAdapter mTrendingAdapter = new PrizePoolRVAdapter(mPrizePoolList);
            mPrizePoolRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mPrizePoolRV.setAdapter(mTrendingAdapter);
        }
    }


    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                mIOnConfirmClickListeener.onConfirmClick(pos);
                break;
        }
    }
    public interface IOnConfirmClickListener {
        public void onConfirmClick(int pos);
    }

}
