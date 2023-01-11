package com.khiladiadda.myfacts;

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
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.main.facts.FactsActivity;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.myfacts.adapter.FactsRVAdapter;
import com.khiladiadda.myfacts.interfaces.IMyFactsPresenter;
import com.khiladiadda.myfacts.interfaces.IMyFactsView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFacts extends BaseActivity implements IMyFactsView, IOnItemClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_league) TextView mLeagueTV;
    @BindView(R.id.tv_home) TextView mHomeTV;
    @BindView(R.id.tv_help) TextView mHelpTV;
    @BindView(R.id.btn_liked) Button mLikedBTN;
    @BindView(R.id.btn_bookmarked) Button mBookmarkedBTN;
    @BindView(R.id.rv_facts) RecyclerView mFactsRV;

    private FactsRVAdapter mAdapter;
    private List<FactsList> mList = null;
    private IMyFactsPresenter mPresenter;
    private int mFrom = 1;

    @Override protected int getContentView() {
        return R.layout.activity_my_facts;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.my_facts);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHomeTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mLikedBTN.setSelected(true);
        mLikedBTN.setOnClickListener(this);
        mBookmarkedBTN.setOnClickListener(this);

    }

    @Override protected void initVariables() {
        mPresenter = new MyFactsPresenter(this);

        mList = new ArrayList<>();
        mAdapter = new FactsRVAdapter(mList);
        mFactsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFactsRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        fetchData();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_league:
                startActivity(new Intent(this, MyLeagueActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.btn_liked:
                mBookmarkedBTN.setSelected(false);
                mLikedBTN.setSelected(true);
                mFrom = 1;
                fetchData();
                break;
            case R.id.btn_bookmarked:
                mLikedBTN.setSelected(false);
                mBookmarkedBTN.setSelected(true);
                mFrom = 2;
                fetchData();
                break;
        }
    }

    private void fetchData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom == 1) {
                mPresenter.getLikedFacts();
            } else {
                mPresenter.getBookmarkedFacts();
            }
        } else {
            Snackbar.make(mBookmarkedBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onLikedComplete(FactsResponse responseModel) {
        hideProgress();
        mList.clear();
        mList.addAll(responseModel.getResponse());
        mAdapter.notifyDataSetChanged();
    }

    @Override public void onLikedFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onBookmarkedComplete(FactsResponse responseModel) {
        hideProgress();
        mList.clear();
        mList.addAll(responseModel.getResponse());
        mAdapter.notifyDataSetChanged();
    }

    @Override public void onBookmarkedFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onItemClick(View view, int position, int tag) {
        Intent eventDetails = new Intent(this, FactsActivity.class);
        eventDetails.putExtra(AppConstant.DATA, mList.get(position));
        startActivity(eventDetails);
    }

    @Override protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}