package com.khiladiadda.main.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.main.category.adapter.QuizTrendingRVAdapter;
import com.khiladiadda.main.category.interfaces.ICategoryPresenter;
import com.khiladiadda.main.category.interfaces.ICategoryView;
import com.khiladiadda.main.fragment.HomeFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.quiz.QuizDetailsActivity;
import com.khiladiadda.quiz.list.QuizListActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryFragment extends BaseFragment implements HomeFragment.IOnPageLoaded, ICategoryView, IOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_picture) TextView mPicturesLL;
    @BindView(R.id.tv_gaming) TextView mGamingLL;
    @BindView(R.id.tv_web) TextView mWebLL;
    @BindView(R.id.tv_logo) TextView mLogoLL;
    @BindView(R.id.tv_math) TextView mMathLL;
    @BindView(R.id.tv_gk) TextView mGkLL;
    @BindView(R.id.tv_sports) TextView mSportsLL;
    @BindView(R.id.tv_technology) TextView mTechnologyLL;
    @BindView(R.id.tv_science) TextView mScienceLL;
    @BindView(R.id.tv_movie) TextView mMoviesLL;
    @BindView(R.id.tv_trending) TextView mTrendingTV;
    @BindView(R.id.rv_quiz) RecyclerView mTrendingQuizRV;
    @BindView(R.id.btn_category) Button mCategoryBTN;
    @BindView(R.id.ll_bottom_sheet_category) LinearLayout mBottomSheetCategoryLL;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshL;

    private ICategoryPresenter mPresenter;
    private QuizTrendingRVAdapter mTrendingAdapter;
    private List<QuizListDetails> mTrendingList = null;
    private BottomSheetBehavior mBottomSheetBehavior;

    public CategoryFragment() {// Required empty public constructor
    }

    public static Fragment getInstance() {
        return new CategoryFragment();
    }

    @Override protected int getContentView() {
        return R.layout.fragment_quiz;
    }

    @Override protected void initViews(View view) {
        mPresenter = new CategoryPresenter(this);

        mPicturesLL.setOnClickListener(this);
        mGamingLL.setOnClickListener(this);
        mWebLL.setOnClickListener(this);
        mLogoLL.setOnClickListener(this);
        mMathLL.setOnClickListener(this);
        mGkLL.setOnClickListener(this);
        mSportsLL.setOnClickListener(this);
        mTechnologyLL.setOnClickListener(this);
        mScienceLL.setOnClickListener(this);
        mMoviesLL.setOnClickListener(this);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetCategoryLL);
        mBottomSheetBehavior.addBottomSheetCallback(mBottomSheetCallback);
        mCategoryBTN.setOnClickListener(mCategoryToggleClickListener);
        mSwipeRefreshL.setOnRefreshListener(this);

        SpannableString trending = new SpannableString(mTrendingTV.getText().toString());
        trending.setSpan(new UnderlineSpan(), 0, trending.length(), 0);
        mTrendingTV.setText(trending);
    }

    @Override protected void initBundle(Bundle bundle) {
    }

    @Override protected void initVariables() {
        mTrendingList = new ArrayList<>();
        mTrendingAdapter = new QuizTrendingRVAdapter(getActivity(), mTrendingList);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mTrendingQuizRV.setLayoutManager(manager);
        mTrendingQuizRV.setAdapter(mTrendingAdapter);
        mTrendingAdapter.setOnItemClickListener(this);
        getData();
    }

    private void getData() {
        if (new NetworkStatus(getContext()).isInternetOn()) {
            mPresenter.getTrendingQuiz();
        } else {
            Snackbar.make(mTrendingTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onClick(View v) {
        String from = null;
        String id = null;
        switch (v.getId()) {
            case R.id.tv_picture:
                from = AppConstant.FROM_PICTURE;
                id = mPreference.getPicture();
                break;
            case R.id.tv_gaming:
                from = AppConstant.FROM_GAMING;
                id = mPreference.getGaming();
                break;
            case R.id.tv_web:
                from = AppConstant.FROM_WEB;
                id = mPreference.getWebSeries();
                break;
            case R.id.tv_logo:
                from = AppConstant.FROM_LOGO;
                id = mPreference.getLogo();
                break;
            case R.id.tv_math:
                from = AppConstant.FROM_MATH;
                id = mPreference.getMath();
                break;
            case R.id.tv_gk:
                from = AppConstant.FROM_GK;
                id = mPreference.getGK();
                break;
            case R.id.tv_sports:
                from = AppConstant.FROM_SPORTS;
                id = mPreference.getSports();
                break;
            case R.id.tv_technology:
                from = AppConstant.FROM_TECHNOLOGY;
                id = mPreference.getTechnology();
                break;
            case R.id.tv_science:
                from = AppConstant.FROM_SCIENCE;
                id = mPreference.getScience();
                break;
            case R.id.tv_movie:
                from = AppConstant.FROM_MOVIES;
                id = mPreference.getMovie();
                break;
        }
        launchQuizActivity(from, id);
    }

    @Override public void onTrendingQuizComplete(TrendinQuizResponse responseModel) {
        hideProgress();
        mPreference.setBoolean(AppConstant.IS_QUIZ_PLAYED, false);
        mSwipeRefreshL.setRefreshing(false);
        mTrendingList.clear();
        if (responseModel.getResponse().size() > 0) {
            mTrendingList.addAll(responseModel.getResponse());
            mTrendingAdapter.notifyDataSetChanged();
            mTrendingTV.setVisibility(View.VISIBLE);
        }
    }

    @Override public void onTrendingQuizFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mTrendingTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override public void onItemClick(View view, int position, int tag) {
        Intent details = new Intent(getActivity(), QuizDetailsActivity.class);
        details.putExtra(AppConstant.DATA, mTrendingList.get(position));
        details.putExtra(AppConstant.FROM, AppConstant.TRENDING);
        startActivity(details);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private void launchQuizActivity(String from, String id) {
        Intent i = new Intent(getActivity(), QuizListActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.CATEGORY);
        i.putExtra(AppConstant.CATEGORY, from);
        i.putExtra(AppConstant.ID, id);
        startActivity(i);
    }

    private View.OnClickListener mCategoryToggleClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
    };

    private void onCategoryExpanded() {
        mCategoryBTN.setBackgroundResource(R.color.colorPrimary);
        mCategoryBTN.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_drop_down_white_, 0, 0);
    }

    private void onCategoryCollapsed() {
        mCategoryBTN.setBackgroundResource(R.color.colorPrimary);
        mCategoryBTN.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_drop_up_white, 0, 0);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                onCategoryExpanded();
            } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                onCategoryCollapsed();
            }
        }

        @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override public void onRefresh() {
        mPresenter.getTrendingQuiz();
    }

    @Override public void onResume() {
        super.onResume();
        if (mPreference.getBoolean(AppConstant.IS_QUIZ_PLAYED, false)) {
            getData();
        }
    }

}