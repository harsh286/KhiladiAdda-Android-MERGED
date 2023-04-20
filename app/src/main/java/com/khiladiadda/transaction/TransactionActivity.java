package com.khiladiadda.transaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.PaymentHistoryResponse;
import com.khiladiadda.network.model.response.TransactionDetails;
import com.khiladiadda.network.model.response.WIthdrawDetails;
import com.khiladiadda.network.model.response.WalletTransactionResponse;
import com.khiladiadda.network.model.response.WithdrawResponse;
import com.khiladiadda.transaction.adapter.TransactionAdapter;
import com.khiladiadda.transaction.adapter.WithdrawAdapter;
import com.khiladiadda.transaction.interfaces.ITransactionPresenter;
import com.khiladiadda.transaction.interfaces.ITransactionView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.withdrawcoins.ManualWithdrawActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.PAGE_SIZE;

public class TransactionActivity extends BaseActivity implements ITransactionView, TransactionAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_wining_cash)
    TextView mWinningCashTV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.rv_transaction)
    RecyclerView mTransactionRV;
    @BindView(R.id.rv_withdraw)
    RecyclerView mWithdrawRV;
    @BindView(R.id.btn_view_manual_history)
    Button mViewManualHistoryBTN;

    private LinearLayoutManager mLayoutManager, mLayoutManagerWithdraw;
    private ITransactionPresenter mPresenter;
    private TransactionAdapter mAdapter;
    private ArrayList<TransactionDetails> mList;
    private WithdrawAdapter mWithdrawAdapter;
    private ArrayList<WIthdrawDetails> mWithdrawList;
    private boolean isLoading, isLastPage;
    private int mCurrentPage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_transaction;
    }

    @Override
    protected void initViews() {
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_WALLET)) {
            mActivityNameTV.setText(R.string.wallet_transaction);
        } else {
            mActivityNameTV.setText(R.string.text_withdraw_history);
        }
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mViewManualHistoryBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new TransactionPresenter(this);

        mList = new ArrayList<>();
        mAdapter = new TransactionAdapter(mList);
        mLayoutManager = new LinearLayoutManager(this);
        mTransactionRV.setLayoutManager(mLayoutManager);
        mTransactionRV.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        mTransactionRV.addOnScrollListener(recyclerViewOnScrollListener);

        mWithdrawList = new ArrayList<>();
        mWithdrawAdapter = new WithdrawAdapter(mWithdrawList);
        mLayoutManagerWithdraw = new LinearLayoutManager(this);
        mWithdrawRV.setLayoutManager(mLayoutManagerWithdraw);
        mWithdrawRV.setAdapter(mWithdrawAdapter);
        mWithdrawRV.addOnScrollListener(withdrawRVOnScrollListener);

        double winningCoins = mAppPreference.getProfileData().getCoins().getWinning();
        if (String.valueOf(winningCoins).contains(".")) {
            mWinningCashTV.setText(getString(R.string.show_wining_coins) + "\n₹" + String.format("%.2f", winningCoins));
        } else {
            mWinningCashTV.setText(getString(R.string.show_wining_coins) + "\n₹" + winningCoins);
        }
        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_WALLET)) {
                mPresenter.getAllTransaction(mCurrentPage, PAGE_SIZE);
            } else {
                mPresenter.getWithdrawHistory(mCurrentPage, PAGE_SIZE);
            }
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_view_manual_history:
                Intent i = new Intent(this, ManualWithdrawActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_TRANSACTION);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onAllTransactionComplete(WalletTransactionResponse responseModel) {
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

    @Override
    public void onAllTransactionFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onWithdrawHistoryComplete(WithdrawResponse responseModel) {
        hideProgress();
        mTransactionRV.setVisibility(View.GONE);
        mWithdrawRV.setVisibility(View.VISIBLE);
        if (responseModel.isStatus()) {
            if (mCurrentPage == 0) {
                mWithdrawList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mWithdrawList.clear();
                mNoDataTV.setVisibility(View.VISIBLE);
            }
            if (responseModel.getResponse().size() > 0) {
                mWithdrawList.addAll(responseModel.getResponse());
                mWithdrawAdapter.notifyDataSetChanged();
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

    @Override
    public void onWithdrawHistoryFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPaymentHistoryComplete(PaymentHistoryResponse responseModel) {
    }

    @Override
    public void onPaymentHistoryFailure(ApiError error) {
    }

    @Override
    public void onPaymentStatusComplete(BaseResponse responseModel) {

    }

    @Override
    public void onPaymentStatusFailure(ApiError error) {

    }

    @Override
    public void onInvoiceComplete(InvoiceResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(responseModel.getResponse().getFileUrl()), "application/pdf");
                Intent chooser = Intent.createChooser(browserIntent, getString(R.string.chooser_title));
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                startActivity(chooser);
            } catch (ActivityNotFoundException e) {
                AppUtilityMethods.showMsg(this, "Please download an app to open", false);
            }
        }
    }

    @Override
    public void onInvoiceFailure(ApiError error) {
        hideProgress();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

    private RecyclerView.OnScrollListener withdrawRVOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManagerWithdraw.getChildCount();
            int totalItemCount = mLayoutManagerWithdraw.getItemCount();
            int firstVisibleItemPosition = mLayoutManagerWithdraw.findFirstVisibleItemPosition();
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        isLoading = true;
        getData();
    }

    @Override
    public void onInvoiceClicked(int position) {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getInvoice(mList.get(position).getId());
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }
}