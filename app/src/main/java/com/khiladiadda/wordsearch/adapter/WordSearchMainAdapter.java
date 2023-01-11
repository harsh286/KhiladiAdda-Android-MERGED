package com.khiladiadda.wordsearch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.WordSearchCategoryQuizResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingQuizResponse;
import com.khiladiadda.wordsearch.listener.IOnSubClickListener;
import com.khiladiadda.wordsearch.listener.IOnViewAllClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchMainAdapter extends RecyclerView.Adapter<WordSearchMainAdapter.ViewHolder> implements IOnSubClickListener {
    private IOnViewAllClickListener mIOnViewAllClickListener;
    private Context context;
    private List<WordSearchTrendingQuizResponse> mTrendingQuizList;
    private List<WordSearchCategoryQuizResponse> mCategoryQuizList;
    int subPos;

    public WordSearchMainAdapter(IOnViewAllClickListener mIOnViewAllClickListener, Context context, List<WordSearchTrendingQuizResponse> mTrendingQuizList, List<WordSearchCategoryQuizResponse> mCategoryQuizList) {
        this.mIOnViewAllClickListener = mIOnViewAllClickListener;
        this.context = context;
        this.mTrendingQuizList = mTrendingQuizList;
        this.mCategoryQuizList = mCategoryQuizList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_main_wordsearch, parent, false);
        return new ViewHolder(view, mIOnViewAllClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrendingQuizList != null) {
            if (!mTrendingQuizList.isEmpty()) {
                WordSearchTrendingQuizResponse trendingQuizResponse = mTrendingQuizList.get(position);
                holder.mNameTV.setText("Tournaments");
                setupRecyclerview(holder, true, position);
            }
            holder.mViewALLMVC.setVisibility(View.GONE);
        } else if (mCategoryQuizList != null) {
            WordSearchCategoryQuizResponse categoryQuizResponse = mCategoryQuizList.get(position);
            holder.mNameTV.setText(categoryQuizResponse.getName());
            holder.mALLV.setBackgroundColor(Color.WHITE);
            holder.mViewAllTV.setTextColor(Color.parseColor("#" + mCategoryQuizList.get(position).getColour()));
            Glide.with(holder.itemView.getContext()).load(categoryQuizResponse.getLogo()).into(holder.mLogoIV);
            setupRecyclerview(holder, false, position);
        }
    }

    private void showHideViewAll(@NonNull ViewHolder holder, int pos) {
        if (mCategoryQuizList.get(pos).getQuizzes().size() < 4) {
            holder.mViewALLMVC.setVisibility(View.GONE);
        } else {
            holder.mViewALLMVC.setVisibility(View.VISIBLE);
        }

    }

    private void setupRecyclerview(ViewHolder holder, boolean isTrending, int position) {
        WordSearchSubMainAdapter wordSearchSubMainAdapter;
        holder.mQuizesRV.setLayoutManager(new LinearLayoutManager(context));
        if (isTrending) {
            wordSearchSubMainAdapter = new WordSearchSubMainAdapter(this, mTrendingQuizList, null);
        } else {
            showHideViewAll(holder, position);
            wordSearchSubMainAdapter = new WordSearchSubMainAdapter(this, null, mCategoryQuizList.get(position));
        }
        holder.mQuizesRV.setAdapter(wordSearchSubMainAdapter);
    }

    @Override
    public int getItemCount() {
        if (mTrendingQuizList != null) return 1;
        return mCategoryQuizList.size();
    }

    @Override
    public void onSubItemClick(int pos) {
        subPos = pos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IOnViewAllClickListener mIOnViewAllClickListener;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.iv_logo)
        ImageView mLogoIV;
        @BindView(R.id.rv_quizes)
        RecyclerView mQuizesRV;
        @BindView(R.id.mcv_view_all)
        MaterialCardView mViewALLMVC;
        @BindView(R.id.v_view_all)
        View mALLV;
        @BindView(R.id.tv_view_all)
        TextView mViewAllTV;
        public ViewHolder(View view, IOnViewAllClickListener mViewAllClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mIOnViewAllClickListener = mViewAllClickListener;
            mViewALLMVC.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (mIOnViewAllClickListener != null) {
                mIOnViewAllClickListener.onViewAllItemClick(position);
            }
        }
    }
}
