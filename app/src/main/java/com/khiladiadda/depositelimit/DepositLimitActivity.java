package com.khiladiadda.depositelimit;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.deposite.DepositLimitRequest;
import com.khiladiadda.network.model.response.deposite.DepositLimitMainResponse;
import com.khiladiadda.network.model.response.deposite.FetchDepositLimitMainResponse;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import butterknife.BindView;

public class DepositLimitActivity extends BaseActivity implements IDepositView {

    @BindView(R.id.sb_limit)
    public SeekBar mLimitSeekBar;
    @BindView(R.id.tv_price_change)
    public TextView mPriceChangeTv;
    @BindView(R.id.tv_month_price_changed)
    public TextView mMonthPriceChangeTv;
    @BindView(R.id.iv_add)
    public ImageView mAddIv;
    @BindView(R.id.iv_minus)
    public ImageView mMinusIv;
    @BindView(R.id.iv_back)
    public ImageView mBackIv;
    @BindView(R.id.tv_terms)
    public TextView mTermsTV;
    @BindView(R.id.mcv_know_more)
    public MaterialCardView mKnowMoreMCV;
    @BindView(R.id.btn_set_limit)
    public AppCompatButton mSetLimitBtn;
    @BindView(R.id.tv_last_date)
    public TextView mLastDateTv;
    @BindView(R.id.cb_terms)
    public CheckBox mTermCB;
    @BindView(R.id.tv_limit_left)
    public TextView mLimitLeft;
    private int min = 5000, max = 50000, step = 1000;
    private IDepositeLimitPresenter mIDepositeLimitPresenter;


    @Override
    protected int getContentView() {
        return R.layout.activity_deposit_limit;
    }

    @Override
    protected void initViews() {
        mLimitSeekBar.setMax((max - min) / step);
        mIDepositeLimitPresenter = new DepositLimitPresenter(this);

        doSeekBar();

    }

    @Override
    protected void initVariables() {
        mAddIv.setOnClickListener(this);
        mMinusIv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mKnowMoreMCV.setOnClickListener(this);
        mSetLimitBtn.setOnClickListener(this);
        mTermsTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        switch (p0.getId()) {
            case R.id.iv_add:
                if (Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) < max) {
                    mPriceChangeTv.setText("₹ " + (Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) + 1000) + "/-");
                    mMonthPriceChangeTv.setText("₹ " + Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) * 20 + "/-");
                    mLimitSeekBar.setProgress((Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) - min) / 1000);
                } else {
                    mAddIv.setEnabled(false);
                    mAddIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_add_disable));
                    mMinusIv.setEnabled(true);
                    mMinusIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_minus));
                }
                break;
            case R.id.iv_minus:
                if (Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) > min) {
                    mPriceChangeTv.setText("₹ " + (Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) - 1000) + "/-");
                    mMonthPriceChangeTv.setText("₹ " + Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) * 20 + "/-");
                    mLimitSeekBar.setProgress((Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", "")) - min) / 1000);
                } else {
                    mAddIv.setEnabled(true);
                    mAddIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_add));
                    mMinusIv.setEnabled(false);
                    mMinusIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_minus_disable));
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_terms:
                Uri uri = Uri.parse("https://www.khiladiadda.com/terms-and-condition");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.mcv_know_more:
                startActivity(new Intent(this, DepositKnowMoreActivity.class));
                break;
            case R.id.btn_set_limit:
                if (mTermCB.isChecked()) {
                    addData();
                } else {
                    Snackbar.make(mSetLimitBtn, R.string.text_select_term_condition, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mIDepositeLimitPresenter.getFetchDepositPresenter();
        } else {
            Snackbar.make(mAddIv, getString(R.string.error_internet), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void addData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mIDepositeLimitPresenter.getAddDepositPresenter(new DepositLimitRequest(Integer.parseInt(mPriceChangeTv.getText().toString().replaceAll("[^0-9]", ""))));
        } else {
            Snackbar.make(mAddIv, getString(R.string.error_internet), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void doSeekBar() {
        mLimitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                mProgress = i * 1000 + 5000
                if (i == 0) {
                    mPriceChangeTv.setText("₹ " + 5000 + "/-");
                    mMonthPriceChangeTv.setText("₹ " + (5000*20) + "/-");
                    mAddIv.setEnabled(true);
                    mAddIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_add));
                    mMinusIv.setEnabled(false);
                    mMinusIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_minus_disable));
                } else if (i == 45) {
                    mPriceChangeTv.setText("₹ " + 50000 + "/-");
                    mMonthPriceChangeTv.setText("₹ " + (50000*20) + "/-");
                    mAddIv.setEnabled(false);
                    mAddIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_add_disable));
                    mMinusIv.setEnabled(true);
                    mMinusIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_minus));
                } else {
                    mMinusIv.setEnabled(true);
                    mAddIv.setEnabled(true);
                    mAddIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_add));
                    mMinusIv.setImageDrawable(getDrawable(R.drawable.ic_deposit_minus));
                    int progress_custom = min + (i * step);
//                    int temp = i * 1000 + 5000;
                    mPriceChangeTv.setText("₹ " + progress_custom + "/-");
                    mMonthPriceChangeTv.setText("₹ " + (progress_custom * 20) + "/-");

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onDepositLimitComplete(DepositLimitMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
            Snackbar.make(mSetLimitBtn, response.getMessage(), Snackbar.LENGTH_SHORT).show();
            mLastDateTv.setText("You can update your limit on " + AppUtilityMethods.getTimeLeft(response.getResponse().getLimitUpdatedAt()));
        } else {
            Snackbar.make(mSetLimitBtn, response.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDepositLimitFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onFetchDepositLimitComplete(FetchDepositLimitMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
            int limit = 0;
            limit = response.getResponse().getRemainingLimit();
            mLimitLeft.setText("₹ " + limit + "/-");
            mLimitSeekBar.setProgress((limit-min)/1000);
            mLastDateTv.setText("You can update your limit after " + AppUtilityMethods.getTimeLeft(response.getResponse().getLimitUpdatedAt()));
        } else {
            Snackbar.make(mSetLimitBtn, response.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFetchDepositLimitFailure(ApiError errorMsg) {
        hideProgress();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}