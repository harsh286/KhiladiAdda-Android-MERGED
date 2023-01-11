package com.khiladiadda.clashroyale;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashroyale.adapter.ClashRoyaleFiltersAdapter;
import com.khiladiadda.clashroyale.adapter.ClashRoyaleListAdapter;
import com.khiladiadda.clashroyale.interfaces.IClashRoyalePresenter;
import com.khiladiadda.clashroyale.interfaces.IClashRoyaleView;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.league.details.LeagueDetailsActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ClashRoyaleFilterReponse;
import com.khiladiadda.network.model.response.GameCategory;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClashRoyaleActivity extends BaseActivity implements IClashRoyaleView, IOnItemClickListener, ClashRoyaleFiltersAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_league) TextView mLeagueTV;
    @BindView(R.id.tv_home) TextView mHomeTV;
    @BindView(R.id.tv_help) TextView mHelpTV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;
    @BindView(R.id.rv_filter) RecyclerView mFilterRV;
    @BindView(R.id.rv_league) RecyclerView mLeagueRV;

    private ClashRoyaleFiltersAdapter mFilterAdapter;
    private ClashRoyaleListAdapter mLeagueAdapter;
    private List<GameCategory> mFilterList = null;
    private List<LeagueListDetails> mLeagueList = null;
    private IClashRoyalePresenter mPresenter;
    private String mFilterId;
    private int mBannerType;

    @Override protected int getContentView() {
        return R.layout.activity_clash_royale;
    }

    @Override protected void initViews() {
        mHomeTV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mActivityNameTV.setText(getString(R.string.text__leagues));
    }

    @Override protected void initVariables() {
        mPresenter = new ClashRoyalePresenter(this);

        mFilterList = new ArrayList<>();
        mFilterAdapter = new ClashRoyaleFiltersAdapter(mFilterList);
        mFilterRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mFilterRV.setAdapter(mFilterAdapter);
        mFilterAdapter.setOnItemChildClickListener(this);

        mLeagueList = new ArrayList<>();
        mLeagueAdapter = new ClashRoyaleListAdapter(mLeagueList);
        mLeagueRV.setLayoutManager(new LinearLayoutManager(this));
        mLeagueRV.setAdapter(mLeagueAdapter);
        mLeagueAdapter.setOnItemClickListener(this);

        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getFilterList();
        }else{
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
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
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                break;
        }
    }

    @Override public void onFilterComplete(ClashRoyaleFilterReponse responseModel) {
        mFilterList.clear();
        if (responseModel.getResponse().getCategories().size() > 0) {
            mFilterId = responseModel.getResponse().getCategories().get(0).getId();
            mFilterList.addAll(responseModel.getResponse().getCategories());
            mFilterList.get(0).setSelected(true);
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mFilterAdapter.notifyDataSetChanged();
        getLeague();
    }

    private void getLeague() {
        mPresenter.getLeagueList(mFilterId, mBannerType);
    }

    @Override public void onFilterFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onLeagueComplete(LeagueListReponse responseModel) {
        hideProgress();
        mLeagueList.clear();
        if (responseModel.getResponse().size() > 0) {
            mNoDataTV.setVisibility(View.GONE);
            mLeagueList.addAll(responseModel.getResponse());
        }
        mLeagueAdapter.notifyDataSetChanged();
    }

    @Override public void onLeagueFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onItemClick(View view, int position, int tag) {
        if (view.getId() == R.id.cv_upcoming) {
            LeagueListDetails detail = mLeagueList.get(position);
            Intent details = new Intent(this, LeagueDetailsActivity.class);
            details.putExtra(AppConstant.FROM, AppConstant.LEAGUE);
            details.putExtra(AppConstant.DATA, detail);
            startActivity(details);
        }
    }

    @Override public void onFilterButtonClicked(int position) {
        mFilterId = mFilterList.get(position).getId();
        for (int i = 0; i < mFilterList.size(); i++) {
            mFilterList.get(i).setSelected(false);
        }
        mFilterList.get(position).setSelected(true);
        mFilterAdapter.notifyDataSetChanged();
        showProgress(getString(R.string.txt_progress_authentication));
        getLeague();
    }

}