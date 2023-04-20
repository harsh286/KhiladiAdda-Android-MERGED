package com.khiladiadda.quiz.list;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;
import com.khiladiadda.quiz.QuizDetailsActivity;
import com.khiladiadda.quiz.list.adapter.QuizListLiveRVAdapter;
import com.khiladiadda.quiz.list.adapter.QuizListPastRVAdapter;
import com.khiladiadda.quiz.list.adapter.QuizListUpcomingRVAdapter;
import com.khiladiadda.quiz.list.interfaces.IQuizListPresenter;
import com.khiladiadda.quiz.list.interfaces.IQuizListView;
import com.khiladiadda.quiz.result.QuizResultActivity;
import com.khiladiadda.quiz.splash.QuizStartSplashActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuizListActivity extends BaseActivity implements IQuizListView, QuizListUpcomingRVAdapter.IOnItemChildClickListener, IOnItemClickListener, QuizListLiveRVAdapter.IOnItemChildClickListener, QuizListPastRVAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.rv_quiz) RecyclerView mRV;
    @BindView(R.id.btn_past) TextView mPastBTN;
    @BindView(R.id.btn_live) TextView mLiveBTN;
    @BindView(R.id.btn_upcoming) TextView mUpcomingBTN;
    @BindView(R.id.tv_category_name) TextView mCategoryNameTV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;
    @BindView(R.id.ll_first) LinearLayout mTopLL;

    private QuizListUpcomingRVAdapter mUpcomingAdapter;
    private QuizListLiveRVAdapter mLiveAdapter;
    private QuizListPastRVAdapter mPastAdapter;
    private List<QuizListDetails> mList = null;
    private IQuizListPresenter mPresenter;
    private String mCategoryId, mFrom = AppConstant.LIVE;
    private int mPosition;
    private boolean mUpcoming, mLive, mPast;

    @Override protected int getContentView() {
        return R.layout.activity_quiz_list;
    }

    @Override protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLiveBTN.setSelected(true);
        mBackIV.setOnClickListener(this);
        mPastBTN.setOnClickListener(this);
        mLiveBTN.setOnClickListener(this);
        mUpcomingBTN.setOnClickListener(this);

        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.CATEGORY)) {
            mActivityNameTV.setText(R.string.txt_choose_quiz);
            mCategoryNameTV.setText(getIntent().getStringExtra(AppConstant.CATEGORY));
            mCategoryId = getIntent().getStringExtra(AppConstant.ID);
            mAppPreference.setCategoryId(mCategoryId);
            mLive = true;
        } else {
            mActivityNameTV.setText(R.string.text_my_quiz);
            mTopLL.setVisibility(View.GONE);
            mCategoryNameTV.setVisibility(View.GONE);
            mPast = true;
            mFrom = AppConstant.PAST;
            mCategoryId = "all";
        }
    }

    @Override protected void initVariables() {
        mPresenter = new QuizListPresenter(this);

        mList = new ArrayList<>();
        mUpcomingAdapter = new QuizListUpcomingRVAdapter(mList);
        mLiveAdapter = new QuizListLiveRVAdapter(mList);
        mPastAdapter = new QuizListPastRVAdapter(mList);
        mRV.setAdapter(mLiveAdapter);
        mRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mUpcomingAdapter.setOnItemClickListener(this);
        mUpcomingAdapter.setOnItemChildClickListener(this);
        mLiveAdapter.setOnItemClickListener(this);
        mLiveAdapter.setOnItemChildClickListener(this);
        mPastAdapter.setOnItemClickListener(this);
        mPastAdapter.setOnItemChildClickListener(this);

        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getQuizList(mCategoryId, mUpcoming, mPast, mLive);
        } else {
            Snackbar.make(mLiveBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onClick(View v) {
        mPastBTN.setSelected(false);
        mLiveBTN.setSelected(false);
        mUpcomingBTN.setSelected(false);
        mPast = false;
        mUpcoming = false;
        mLive = false;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_past:
                mPastBTN.setSelected(true);
                mFrom = AppConstant.PAST;
                mPast = true;
                getData();
                break;
            case R.id.btn_live:
                mLiveBTN.setSelected(true);
                mFrom = AppConstant.LIVE;
                mLive = true;
                getData();
                break;
            case R.id.btn_upcoming:
                mUpcomingBTN.setSelected(true);
                mFrom = AppConstant.UPCOMING;
                mUpcoming = true;
                getData();
                break;
        }
    }

    @Override public void onStartQuizComplete(StartQuizResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().getNAttempts() < 4) {
                Intent details = new Intent(this, QuizStartSplashActivity.class);
                details.putExtra(AppConstant.DATA, mList.get(mPosition));
                startActivity(details);
            } else {
                AppUtilityMethods.showMsg(this, getString(R.string.text_played_max_limit), false);
            }
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override public void onStartQuizFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onQuizListComplete(QuizListResponse response) {
        mNoDataTV.setVisibility(View.GONE);
        mList.clear();
        if (mLive) {
            if (response.getResponse().getLive().size() > 0) {
                mList.addAll(response.getResponse().getLive());
                mRV.setAdapter(mLiveAdapter);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
                mNoDataTV.setText(getString(R.string.text_no_quiz_list));
            }
        } else if (mPast) {
            if (response.getResponse().getPast().size() > 0) {
                mList.addAll(response.getResponse().getPast());
                mRV.setAdapter(mPastAdapter);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
                mNoDataTV.setText(getString(R.string.text_no_quiz_played));
            }
        } else if (mUpcoming) {
            if (response.getResponse().getUpcoming().size() > 0) {
                mList.addAll(response.getResponse().getUpcoming());
                mRV.setAdapter(mUpcomingAdapter);
            } else {
                mNoDataTV.setVisibility(View.VISIBLE);
                mNoDataTV.setText(getString(R.string.text_no_quiz_list));
            }
        }
        mUpcomingAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override public void onQuizListFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onUserPlayedQuizComplete(UserQuizPlayedResponse responseModel) {
    }

    @Override public void onUserPlayedQuizFailure(ApiError error) {

    }

    @Override public void onItemClick(View view, int position, int tag) {
        QuizListDetails detail = mList.get(position);
        if (mList.get(position).isCancelled()) {
            AppUtilityMethods.showMsg(this, "This quiz was cancelled and entry fee has been reverted to your wallet.", false);
        } else {
            Intent details = new Intent(this, QuizDetailsActivity.class);
            details.putExtra(AppConstant.DATA, detail);
            details.putExtra(AppConstant.FROM, mFrom);
            startActivity(details);
        }
    }

    @Override public void onPlayClicked(int position) {
        QuizListDetails detail = mList.get(position);
        Intent details = new Intent(this, QuizDetailsActivity.class);
        details.putExtra(AppConstant.DATA, detail);
        details.putExtra(AppConstant.FROM, mFrom);
        startActivity(details);
    }

    @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override public void onViewPastLeaderboardClicked(int position) {
        mPosition = position;
        Intent i = new Intent(this, QuizResultActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.QUIZ_DETAILS);
        i.putExtra(AppConstant.DATA_QUIZ, mList.get(mPosition));
        startActivity(i);
    }

}