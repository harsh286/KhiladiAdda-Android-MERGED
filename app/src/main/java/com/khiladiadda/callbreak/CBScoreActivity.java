package com.khiladiadda.callbreak;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.callbreak.adapter.CallBreakScoreAdapter;
import com.khiladiadda.callbreak.interfaces.ICallBreakPresenter;
import com.khiladiadda.callbreak.interfaces.ICallBreakView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CallBreakHistoryPlayerResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CBScoreActivity extends BaseActivity implements ICallBreakView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_help_video)
    TextView mHelpVideoTV;
    @BindView(R.id.rv_callbreak)
    RecyclerView mCallBreakRV;
    @BindView(R.id.mcv_play)
    AppCompatButton mPlayAGainMcv;
    private List<CallBreakHistoryPlayerResponse> playersValue;
    private ICallBreakPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_cbscore;
    }

    @Override
    protected void initViews() {
        mPresenter = new CallBreakPresenter(this);
        mActivityNameTV.setText("Score");
        mBackIV.setOnClickListener(this);
        mPlayAGainMcv.setOnClickListener(this);
        mHelpVideoTV.setVisibility(View.GONE);
    }

    @Override
    protected void initVariables() {
        playersValue = new ArrayList<>();
        setData(getIntent().getStringExtra(AppConstant.ID));
    }

    private void setData(String id) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getCallBreakHistoryRank(id);
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back) {
            doIntent();
        } else if (p0.getId() == R.id.mcv_play) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doIntent();
    }

    private void doIntent() {
        startActivity(new Intent(this, CBHistoryActivity.class));
        finish();
    }

    @Override
    public void onGetContestSuccess(CallBreakResponse responseModel) {

    }

    @Override
    public void onGetContestFailure(ApiError error) {

    }

    @Override
    public void onGetContestJoinSuccess(CallBreakJoinMainResponse responseModel) {

    }

    @Override
    public void onGetContestJoinFailure(ApiError error) {

    }

    @Override
    public void onGetHistorySuccess(CallBreakResponse responseModel) {

    }

    @Override
    public void onGetHistoryFailure(ApiError error) {

    }

    @Override
    public void onGetHistoryRankSuccess(CbHistoryRankMainResponse responseModel) {
        if (responseModel.isStatus()) {
            playersValue = responseModel.getResponse().getPlayers();
            CallBreakScoreAdapter mAdapter = new CallBreakScoreAdapter(this, playersValue);
            mCallBreakRV.setLayoutManager(new LinearLayoutManager(this));
            mCallBreakRV.setAdapter(mAdapter);
        }
        hideProgress();
    }

    public static int compareThem(CallBreakHistoryPlayerResponse a, CallBreakHistoryPlayerResponse b) {
        return a.getRank().compareTo(b.getRank());
    }

    @Override
    public void onGetHistoryRankFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}