package com.khiladiadda.main.facts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.main.facts.adapter.FactsTopRVAdapter;
import com.khiladiadda.main.facts.adapter.FactsTrendingRVAdapter;
import com.khiladiadda.main.facts.interfaces.IFactsPresenter;
import com.khiladiadda.main.facts.interfaces.IFactsView;
import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.main.fragment.HomeFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class FactsFragment extends BaseFragment implements HomeFragment.IOnPageLoaded, IFactsView, FactsTrendingRVAdapter.IOnItemChildClickListener, IOnItemClickListener {

    @BindView(R.id.tv_top)
    TextView mTopTV;
    @BindView(R.id.tv_trending)
    TextView mTrendingTV;
    @BindView(R.id.rv_trending)
    RecyclerView mTrendingRV;
    @BindView(R.id.rv_top_story)
    RecyclerView mTopRV;

    private FactsTrendingRVAdapter mTrendingAdapter;
    private FactsTopRVAdapter mTopAdapter;
    private List<FactsList> mTrendingList = null;
    private List<FactsList> mTopList = null;
    private IFactsPresenter mFactsPresenter;
    private int mClickedPosition = -1;
    Handler handler = new Handler();

    public FactsFragment() {// Required empty public constructor
    }

    public static Fragment getInstance() {
        return new FactsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_facts;
    }

    @Override
    protected void initViews(View view) {
        SpannableString category = new SpannableString(mTopTV.getText().toString());
        category.setSpan(new UnderlineSpan(), 0, category.length(), 0);
        mTopTV.setText(category);
        SpannableString trending = new SpannableString(mTrendingTV.getText().toString());
        trending.setSpan(new UnderlineSpan(), 0, trending.length(), 0);
        mTrendingTV.setText(trending);
    }

    @Override
    protected void initBundle(Bundle bundle) { }

    @Override
    protected void initVariables() {
        mFactsPresenter = new FactsPresenter(this);
        mTrendingList = new ArrayList<>();
        mTrendingAdapter = new FactsTrendingRVAdapter(getActivity(), mTrendingList);
        mTrendingRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTrendingRV.setAdapter(mTrendingAdapter);
        mTrendingAdapter.setOnItemClickListener(this);
        mTrendingAdapter.setOnItemChildClickListener(this);
        mTopList = new ArrayList<>();
        mTopAdapter = new FactsTopRVAdapter(getActivity(), mTopList);
        mTopRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTopRV.setAdapter(mTopAdapter);
        mTopAdapter.setOnItemClickListener(this);
        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onClick(View v) { }

    @Override
    public void onTrendingFactsComplete(FactsResponse responseModel) {
        mTrendingList.clear();
        if (responseModel.getResponse().size() > 0) {
            mTrendingList.addAll(responseModel.getResponse());
            mTrendingAdapter.notifyDataSetChanged();
        }
        mFactsPresenter.getFacts();
    }

    @Override
    public void onTrendingFactsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFactsComplete(FactsResponse responseModel) {
        hideProgress();
        mTopList.clear();
        if (responseModel.getResponse().size() > 0) {
            mTopList.addAll(responseModel.getResponse());
            mTopAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFactsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFactDetailsComplete(FactDetailsResponse responseModel) { hideProgress();}

    @Override
    public void onFactDetailsFailure(ApiError error) {  hideProgress();  }

    @Override
    public void onLikeFactComplete(LikeFactResponse responseModel) {
        hideProgress();
        if (mClickedPosition > -1){
            FactsList trendingFact = mTrendingList.get(mClickedPosition);
            trendingFact.setLiked(!trendingFact.isLiked());
            mTrendingAdapter.notifyItemChanged(mClickedPosition);
        }
        mClickedPosition = -1;
    }

    @Override
    public void onLikeFactFailure(ApiError error) {
        hideProgress();
        mClickedPosition = -1;
    }

    @Override
    public void onBookmarkFactComplete(LikeFactResponse responseModel) {
        hideProgress();
        if (mClickedPosition > -1){
            FactsList trendingFact = mTrendingList.get(mClickedPosition);
            trendingFact.setBookmarked(!trendingFact.isBookmarked());
            mTrendingAdapter.notifyItemChanged(mClickedPosition);
        }
        mClickedPosition = -1;
    }

    @Override
    public void onBookmarkFactFailure(ApiError error) {
        hideProgress();
        mClickedPosition = -1;
    }

    @Override
    public void onFactsFetchedFromDB(List<FactsList> eventList) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (eventList.size() > 0) {
                    showData(eventList);
                }
            }
        });
    }

    private void showData(List<FactsList> mList) {
        mTrendingList.clear();
        mTrendingList.addAll(mList);
        mTrendingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWishlistClicked(int position) {
        showProgress(getString(R.string.txt_progress_authentication));
        mClickedPosition = position;
        mFactsPresenter.likeFact(mTrendingList.get(position).getId());
    }

    @Override
    public void onBookmarkClicked(int position) {
        showProgress(getString(R.string.txt_progress_authentication));
        mClickedPosition = position;
        mFactsPresenter.bookmarkFact(mTrendingList.get(position).getId());
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent eventDetails = new Intent(getActivity(), FactsActivity.class);
        switch (view.getId()){
            case R.id.cv_top:
                eventDetails.putExtra(AppConstant.DATA, mTopList.get(position));
                break;
            case R.id.cv_trending:
                eventDetails.putExtra(AppConstant.DATA, mTrendingList.get(position));
                break;
        }
        startActivity(eventDetails);
    }

    @Override
    public void onDestroy() {
        mFactsPresenter.destroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showVideoHelp();
        }
    };

    private void showVideoHelp() {
        if (!mPreference.getIsTutorialSeen()) {
                AppUtilityMethods.showTutorialMsg(getActivity(), false);
        }
    }

}