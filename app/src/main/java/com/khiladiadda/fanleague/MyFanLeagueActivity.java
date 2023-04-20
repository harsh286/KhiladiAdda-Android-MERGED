package com.khiladiadda.fanleague;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.BattleActivity;
import com.khiladiadda.fanleague.adapter.MyFanLeagueAdapter;
import com.khiladiadda.fanleague.interfaces.IMyFanLeaguePresenter;
import com.khiladiadda.fanleague.interfaces.IMyFanLeagueView;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchDetails;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;

import butterknife.BindView;

public class MyFanLeagueActivity extends BaseActivity implements IMyFanLeagueView, IOnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.btn_live)
    Button mLiveBTN;
    @BindView(R.id.btn_past)
    Button mPastBTN;
    @BindView(R.id.btn_upcoming)
    Button mUpcomingBTN;
    @BindView(R.id.rv_my_match)
    RecyclerView mMyMatchRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;

    private ArrayList<MatchDetails> mMatchList = new ArrayList<>();
    private MyFanLeagueAdapter mAdapter;
    private IMyFanLeaguePresenter mPresenter;
    private boolean mUpcoming, mLive = true, mPast;
    private int mScoreIndex = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_fan_league;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_my_match);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLiveBTN.setOnClickListener(this);
        mPastBTN.setOnClickListener(this);
        mUpcomingBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new MyFanLeaguePresenter(this);
        setAdapterView();
        if (new NetworkStatus(this).isInternetOn()) {
            getType(AppConstant.FROM_FANBATTLE_LIVE);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setAdapterView() {
        mAdapter = new MyFanLeagueAdapter(mMatchList, mLive, mUpcoming, mPast);
        mMyMatchRV.setLayoutManager(new LinearLayoutManager(this));
        mMyMatchRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getMyFanLeague(mUpcoming, mPast, mLive);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.btn_live:
                getType(AppConstant.FROM_FANBATTLE_LIVE);
                break;
            case R.id.btn_past:
                getType(AppConstant.FROM_FANBATTLE_PAST);
                break;
            case R.id.btn_upcoming:
                getType(AppConstant.FROM_FANBATTLE_UPCOMING);
                break;
        }
    }

    private void getType(int type) {
        mMatchList.clear();
        mUpcoming = false;
        mLive = false;
        mPast = false;
        mLiveBTN.setSelected(false);
        mPastBTN.setSelected(false);
        mUpcomingBTN.setSelected(false);
        mNoDataTV.setVisibility(View.GONE);
        if (type == AppConstant.FROM_FANBATTLE_LIVE) {
            mLive = true;
            mLiveBTN.setSelected(true);
        } else if (type == AppConstant.FROM_FANBATTLE_PAST) {
            mPast = true;
            mPastBTN.setSelected(true);
        } else if (type == AppConstant.FROM_FANBATTLE_UPCOMING) {
            mUpcoming = true;
            mUpcomingBTN.setSelected(true);
        }
        setAdapterView();
        getData();
    }

    @Override
    public void onMyFanLeagueComplete(MatchResponse responseModel) {
        mMatchList.clear();
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mMatchList.addAll(responseModel.getResponse());
            showData();
        } else {
            hideProgress();
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    private void showData() {
        mAdapter.notifyDataSetChanged();
        mNoDataTV.setVisibility(View.GONE);
        hideProgress();
    }

    @Override
    public void onMyFanLeagueFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLiveScoreComplete(CricScorce cricScorce) {
        if (mScoreIndex >= 0) {
            mMatchList.get(mScoreIndex).setScore(cricScorce.getScore());
            mScoreIndex++;
            if (mScoreIndex < mMatchList.size()) {
                mPresenter.getLiveScore(AppConstant.CRIC_API_KEY, mMatchList.get(mScoreIndex).getMatchId());
            } else {
                mScoreIndex = -1;
                mAdapter.notifyDataSetChanged();
                hideProgress();
            }
        }
    }

    @Override
    public void onLiveScoreFailure(CricApiError error) {
        hideProgress();
        showData();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent i = new Intent(this, BattleActivity.class);
        i.putExtra(AppConstant.DATA, mMatchList.get(position));
        if (mUpcoming) {
            if (mMatchList.get(position).isLive()) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_UPCOMING);
                startActivity(i);
            } else {
                Snackbar.make(mLiveBTN, R.string.text_contest_open_soon, Snackbar.LENGTH_LONG).show();
            }
        } else if (mLive) {
            i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_LIVE);
            startActivity(i);
        } else if (mPast) {
            i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE_PAST);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}