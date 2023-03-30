package com.khiladiadda.rummy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.RummyHistoryMainResponse;
import com.khiladiadda.rummy.adapter.RummyHistoryAdapter;
import com.khiladiadda.rummy.interfaces.IRummyHistoryPresenter;
import com.khiladiadda.rummy.interfaces.IRummyHistoryView;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import butterknife.BindView;

public class RummyHistoryActivity extends BaseActivity implements IRummyHistoryView, IOnItemClickListener {
    @BindView(R.id.tv_history)
    TextView mHistoryIv;
    @BindView(R.id.rv_rummy_history)
    RecyclerView mRummyHistoryRv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_error)
    TextView mErrorTv;
    @BindView(R.id.tv_activity_name)
    TextView mActivityName;

    private IRummyHistoryPresenter mPresenter;
    private RummyHistoryAdapter rummyHistoryAdapter;
    private RummyHistoryMainResponse dataResponse;


    @Override
    protected int getContentView() {
        return R.layout.activity_rummy_history;
    }

    @Override
    protected void initViews() {
        mPresenter = new RummyHistoryPresenter(this);
        mHistoryIv.setVisibility(View.GONE);
        mBackIv.setOnClickListener(this);
        mActivityName.setText("Transactions");
        mRummyHistoryRv.setLayoutManager(new LinearLayoutManager(this));
//        rummyHistoryAdapter = new RummyHistoryAdapter(this, rv_rummy_history)
    }

    @Override
    protected void initVariables() {
        getData();
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back){
            finish();
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRummyHistoryStatus();
        } else {
            Snackbar.make(mActivityName, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetRummyHistorySuccess(RummyHistoryMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()){
            dataResponse = responseModel;
            if (responseModel.getResponse().isEmpty()){
                mErrorTv.setVisibility(View.VISIBLE);
            }else {
                mErrorTv.setVisibility(View.GONE);
                mRummyHistoryRv.setAdapter(new RummyHistoryAdapter(this, responseModel.getResponse(), this));
            }
        }
    }

    @Override
    public void onGetRummyHistoryFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent intent = new Intent(this, RummyLogsWebViewActivity.class);
        intent.putExtra("mid", dataResponse.getResponse().get(position).getTxnID());
        startActivity(intent);
    }
}