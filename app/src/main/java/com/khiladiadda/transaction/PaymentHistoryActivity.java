package com.khiladiadda.transaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnDateSetListener;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.PaymentHistoryRequest;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.PaymentHistoryDetails;
import com.khiladiadda.network.model.response.PaymentHistoryResponse;
import com.khiladiadda.network.model.response.WalletTransactionResponse;
import com.khiladiadda.network.model.response.WithdrawResponse;
import com.khiladiadda.transaction.adapter.PaymentAdapter;
import com.khiladiadda.transaction.interfaces.ITransactionPresenter;
import com.khiladiadda.transaction.interfaces.ITransactionView;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.PAGE_SIZE;

public class PaymentHistoryActivity extends BaseActivity implements ITransactionView, IOnItemClickListener, PaymentAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_wining_cash) TextView mWinningCashTV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;
    @BindView(R.id.rv_transaction) RecyclerView mTransactionRV;
    @BindView(R.id.tv_from_date) TextView mFromDateTV;
    @BindView(R.id.tv_to_date) TextView mToDateTV;
    @BindView(R.id.btn_success) Button mSuccessBTN;
    @BindView(R.id.btn_pending) Button mPendingBTN;
    @BindView(R.id.btn_failed) Button mFailedBTN;
    @BindView(R.id.btn_done) Button mDoneBTN;
    @BindView(R.id.btn_all) Button mAllBTN;

    private LinearLayoutManager mLayoutManager;
    private ITransactionPresenter mPresenter;
    private PaymentAdapter mAdapter;
    private ArrayList<PaymentHistoryDetails> mList;
    private boolean isLoading, isLastPage;
    private int mCurrentPage = 0;
    private String mFromDate, mToDate;
    private int mStatus = 0;
    private PaymentHistoryRequest request = new PaymentHistoryRequest();

    @Override protected int getContentView() {
        return R.layout.activity_payment_history;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_payment_history);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mToDateTV.setOnClickListener(this);
        mFromDateTV.setOnClickListener(this);
        mSuccessBTN.setOnClickListener(this);
        mPendingBTN.setOnClickListener(this);
        mFailedBTN.setOnClickListener(this);
        mDoneBTN.setOnClickListener(this);
        mAllBTN.setOnClickListener(this);
        mAllBTN.setSelected(true);
    }

    @Override protected void initVariables() {
        mPresenter = new TransactionPresenter(this);

        mList = new ArrayList<>();
        mAdapter = new PaymentAdapter(this, mList);
        mLayoutManager = new LinearLayoutManager(this);
        mTransactionRV.setLayoutManager(mLayoutManager);
        mTransactionRV.setAdapter(mAdapter);
        mTransactionRV.addOnScrollListener(recyclerViewOnScrollListener);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

        double winningCoins = mAppPreference.getProfileData().getCoins().getWinning();
        if (String.valueOf(winningCoins).contains(".")) {
            mWinningCashTV.setText(getString(R.string.show_wining_coins) + "\n₹" + String.format("%.2f", winningCoins));
        } else {
            mWinningCashTV.setText(getString(R.string.show_wining_coins) + "\n₹" + winningCoins);
        }
        setRequestForPaymentHistory();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_from_date:
                AppUtilityMethods.openToDatePickerDialog(this, mFromDateSetListener);
                break;
            case R.id.tv_to_date:
                AppUtilityMethods.openToDatePickerDialog(this, mToDateSetListener);
                break;
            case R.id.btn_done:
                if (!TextUtils.isEmpty(mFromDateTV.getText().toString().trim()) && !TextUtils.isEmpty(mToDateTV.getText().toString().trim())) {
                    setBackground();
                    if (AppUtilityMethods.checkDates(mFromDateTV.getText().toString().trim(), mToDateTV.getText().toString().trim())) {
                        AppUtilityMethods.showMsg(this, "Please provide From-Date and To-Date properly", false);
                    }
                } else {
                    AppUtilityMethods.showMsg(this, "Please provide From-Date and To-Date properly", false);
                }
                break;
            case R.id.btn_all:
                mStatus = 0;
                setBackground();
                break;
            case R.id.btn_success:
                mStatus = 1;
                setBackground();
                break;
            case R.id.btn_pending:
                mStatus = 2;
                setBackground();
                break;
            case R.id.btn_failed:
                mStatus = 3;
                setBackground();
                break;
        }
    }

    private void setBackground() {
        mCurrentPage = 0;
        isLastPage = false;
        mAllBTN.setSelected(false);
        mSuccessBTN.setSelected(false);
        mPendingBTN.setSelected(false);
        mFailedBTN.setSelected(false);
        mList.clear();
        mAdapter.notifyDataSetChanged();
        if (mStatus == 0) {
            mAllBTN.setSelected(true);
        } else if (mStatus == 1) {
            mSuccessBTN.setSelected(true);
        } else if (mStatus == 2) {
            mPendingBTN.setSelected(true);
        } else if (mStatus == 3) {
            mFailedBTN.setSelected(true);
        }
        setRequestForPaymentHistory();
    }

    private void setRequestForPaymentHistory() {
        request.setFromDate(mFromDate);
        request.setToDate(mToDate);
        request.setPage(mCurrentPage);
        request.setLimit(PAGE_SIZE);
        request.setStatus(mStatus);
        getData();
    }

    @Override public void onAllTransactionComplete(WalletTransactionResponse responseModel) {

    }

    @Override public void onAllTransactionFailure(ApiError error) {

    }

    @Override public void onWithdrawHistoryComplete(WithdrawResponse responseModel) {

    }

    @Override public void onWithdrawHistoryFailure(ApiError error) {

    }

    @Override public void onPaymentHistoryComplete(PaymentHistoryResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (mCurrentPage == 0) {
                mList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mList.clear();
                mNoDataTV.setVisibility(View.VISIBLE);
            }
            if (responseModel.getResponse().size() > 0) {
                mList.addAll(responseModel.getResponse());
                mAdapter.notifyDataSetChanged();
                mTransactionRV.setVisibility(View.VISIBLE);
            }
            isLoading = false;
            mCurrentPage++;
            if (responseModel.getResponse().size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override public void onPaymentHistoryFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onPaymentStatusComplete(BaseResponse responseModel) {
        hideProgress();
        showMsg(responseModel.getMessage());
    }

    @Override public void onPaymentStatusFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onInvoiceComplete(InvoiceResponse responseModel) {

    }

    @Override
    public void onInvoiceFailure(ApiError error) {

    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getPaymentHistory(request);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        isLoading = true;
        setRequestForPaymentHistory();
    }

    private IOnDateSetListener mFromDateSetListener = new IOnDateSetListener() {
        @Override public void onDateSet(int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            Date fromDate = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            mFromDateTV.setText(format.format(fromDate));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(fromDate);
            mFromDate = formattedDate + "T00:00:00.000Z";
        }
    };

    private IOnDateSetListener mToDateSetListener = new IOnDateSetListener() {
        @Override public void onDateSet(int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            Date toDate = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            mToDateTV.setText(format.format(toDate));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(toDate);
            mToDate = formattedDate + "T23:59:59.000Z";
        }
    };

    @Override protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override public void onStatusClicked(int position) {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getPaymentStatus(mList.get(position).getId());
    }

    @Override public void onItemClick(View view, int position, int tag) {
        if (mList.get(position).getStatus().equalsIgnoreCase("PROCESSING")) {
            //            AppUtilityMethods.showMsg(this, "Your payment is under processing.\nClick on Check Status to view latest payment status.\nIt will take around 2-4hours to update the payment.\nConnect with contact support for any help.", false);
            AppUtilityMethods.showMsg(this, "Your transaction is pending from your end. To recharged your wallet Go to My wallet->Add coins.\nIf any amount deducted from your wallet and not added in Khiladi Adda wallet then contact support on whatsapp(7428879142)", false);
        }
    }

    public void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            setBackground();
            dialog.dismiss();
        });
        dialog.show();
    }

}