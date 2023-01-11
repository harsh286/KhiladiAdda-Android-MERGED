package com.khiladiadda.clashx2.cricket.createbattle.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.cricket.createbattle.adapter.ReviewAdapter;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReviewFragment extends BaseFragment {

    private List<KaPlayerHTH> mReviewList;
    @BindView(R.id.rv_review)
    RecyclerView mReviewRV;
    private ReviewAdapter mReviewAdpater;

    @Override
    protected int getContentView() {
        return R.layout.fragment_review;
    }

    public ReviewFragment() {// Required empty public constructor
    }

    public static Fragment getInstance() {
        return new ReviewFragment();
    }

    @Override
    protected void initViews(View view) {
        mReviewList = new ArrayList<>();
    }

    @Override
    protected void initBundle(Bundle bundle) {

    }

    @Override
    protected void initVariables() {
        mReviewAdpater = new ReviewAdapter(mReviewList);
        mReviewRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReviewRV.setAdapter(mReviewAdpater);
    }

    @Override
    public void onClick(View v) {

    }

    public void updatePlayers(List<KaPlayerHTH> kaPlayerList){
        mReviewList.clear();
        mReviewList.addAll(kaPlayerList);
        mReviewAdpater.notifyDataSetChanged();
    }


    public List<KaPlayerHTH> getSelectedPlayers(){
        return mReviewList;
    }
}