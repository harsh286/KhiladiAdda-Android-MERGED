package com.khiladiadda.wordsearch.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.WordSearchCategoryQuizResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingQuizResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.wordsearch.activity.WordSearchDetailsActivity;
import com.khiladiadda.wordsearch.listener.IOnSubClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchSubMainAdapter extends RecyclerView.Adapter<WordSearchSubMainAdapter.ViewHolder> {
    private IOnSubClickListener mIOnGamesClickListener;
    private List<WordSearchTrendingQuizResponse> mTrendingQuizList;
    private WordSearchCategoryQuizResponse mCategoryList;

    public WordSearchSubMainAdapter(IOnSubClickListener mIOnGamesClickListener, List<WordSearchTrendingQuizResponse> mTrendingQuizList, WordSearchCategoryQuizResponse mCategoryList) {
        this.mIOnGamesClickListener = mIOnGamesClickListener;
        this.mTrendingQuizList = mTrendingQuizList;
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_sub_main_wordsearch, parent, false);
        return new ViewHolder(view, mIOnGamesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrendingQuizList != null && !mTrendingQuizList.isEmpty()) {
            WordSearchTrendingQuizResponse trendingQuizResponse = mTrendingQuizList.get(position);
            holder.mJoinedPb.setProgress(trendingQuizResponse.getPlayedparticipants());
            holder.mJoinedPb.setMax(trendingQuizResponse.getTotalparticipants());
            holder.mCategoriesTv.setText("Category: " + trendingQuizResponse.getCategory_name());
            holder.mTotalParticipants.setText("" + trendingQuizResponse.getPlayedparticipants() + "/" + trendingQuizResponse.getTotalparticipants());
            setupUiData(holder, trendingQuizResponse.getImage(), trendingQuizResponse.getName(), trendingQuizResponse.getPrizemoney(), trendingQuizResponse.getEntryFees());
            holder.mQuizzesMCV.setOnClickListener(view -> {
                onItemsClick(holder, position, true);
            });
            holder.mPLayBtn.setOnClickListener(view -> {
                onItemsClick(holder, position, true);
            });
            if (trendingQuizResponse.getAttemptedQuiz() != 0) {
//                holder.mAttemptMCV.setVisibility(View.VISIBLE);
                holder.mAttemptTV.setText("Attempts: " + trendingQuizResponse.getAttemptedQuiz() + "/3");
            } else {
                holder.mAttemptTV.setVisibility(View.GONE);
            }
        }
    }

    private void onItemsClick(ViewHolder holder, int position, boolean isTrending) {
        Intent intent = new Intent(holder.itemView.getContext(), WordSearchDetailsActivity.class);
        if (isTrending) {
            intent.putExtra(AppConstant.WORD_SEARCH_TYPE, 1);
            intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME, "Trending");
            intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES, mTrendingQuizList.get(position));
        } else {
            intent.putExtra(AppConstant.WORD_SEARCH_TYPE, 2);
            intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME, mCategoryList.getName());
            intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES, mCategoryList.getQuizzes().get(position));
        }
        holder.itemView.getContext().startActivity(intent);
    }

    private void setupUiData(ViewHolder holder, String image_url, String name, int winAmount, int entryAmount) {
        Glide.with(holder.itemView.getContext()).load(image_url).placeholder(R.drawable.wordsearch_placeholder).into(holder.mQuizIV);
        holder.mNameTV.setText(name);
        holder.mWinTV.setText("" + winAmount + " Coins");
        holder.mEntryFeeTV.setText("Entry: " + entryAmount + " Coins");

    }

    @Override
    public int getItemCount() {
        if (mTrendingQuizList != null) return mTrendingQuizList.size();
        return mCategoryList.getQuizzes().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IOnSubClickListener mOnItemClickListener;
        @BindView(R.id.tv_attempt)
        TextView mAttemptTV;
        @BindView(R.id.btn_play)
        AppCompatButton mPLayBtn;
        @BindView(R.id.mcv_quizzes)
        MaterialCardView mQuizzesMCV;
        @BindView(R.id.tv_entry)
        TextView mEntryFeeTV;
        @BindView(R.id.tv_win)
        TextView mWinTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.iv_quiz_image)
        ImageView mQuizIV;
        @BindView(R.id.tv_total_participants)
        TextView mTotalParticipants;
        @BindView(R.id.pb_joined)
        ProgressBar mJoinedPb;
        @BindView(R.id.tv_categories)
        TextView mCategoriesTv;

        public ViewHolder(View view, IOnSubClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            mQuizzesMCV.setOnClickListener(this);
            mPLayBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onSubItemClick(position);
            }
        }
    }
}
