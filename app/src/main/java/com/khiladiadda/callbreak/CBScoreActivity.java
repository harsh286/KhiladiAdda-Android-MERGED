package com.khiladiadda.callbreak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.callbreak.adapter.CallBreakHistoryAdapter;
import com.khiladiadda.callbreak.adapter.CallBreakScoreAdapter;
import com.khiladiadda.callbreak.interfaces.ICallBreakPresenter;
import com.khiladiadda.callbreak.interfaces.ICallBreakView;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.CallBreakHistoryPlayerResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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
    MaterialCardView mPlayAGainMcv;
    private CallBreakScoreAdapter mAdapter;
    private List<CallBreakDetails> mList;
    private List<CallBreakHistoryPlayerResponse> playersValue;
    private List<CallBreakHistoryPlayerResponse> mNewPlayersValue;
    private ICallBreakPresenter mPresenter;
    private String id;

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
//        playersValue = getIntent().getParcelableArrayListExtra("player_list");
        id = getIntent().getStringExtra(AppConstant.ID);
//        playersValue.sort();
        setData(id);

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
        hideProgress();
        if (responseModel.isStatus()) {
            playersValue = responseModel.getResponse().getPlayers();
//            for (int i = 0; i < responseModel.getResponse().getPlayers().size(); i++) {
//                if (Objects.equals(AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId(),
//                        responseModel.getResponse().getPlayers().get(i).getUserId())) {
//                    mNumberingTv.setText("#" + responseModel.getResponse().getPlayers().get(i).getRank());
//                    mNameTv.setText(responseModel.getResponse().getPlayers().get(i).getName());
//                    mPointsTv.setText("" + responseModel.getResponse().getPlayers().get(i).getScore());
//                    mWonTv.setText("" + responseModel.getResponse().getPlayers().get(i).getWinningAmount());
//                    Glide.with(this).load(responseModel.getResponse().getPlayers().get(i).getDp()).placeholder(R.drawable.splash_logo).into(mProfileCiv);
//                    mNewPlayersValue.remove(i);
//                }
//            }
            mAdapter = new CallBreakScoreAdapter(this, playersValue);
            mCallBreakRV.setLayoutManager(new LinearLayoutManager(this));
            mCallBreakRV.setAdapter(mAdapter);
        }
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
}