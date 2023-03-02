package com.khiladiadda.wordsearch.adapter;

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
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesResponse;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchQuizAdapter extends RecyclerView.Adapter<WordSearchQuizAdapter.ViewHolder> {
    private IOnClickListener mIOnClickListener;
    private List<WordSearchCategoriesQuizzesResponse> mCategoriesQuizzesResponseList;
    private String categoryName;

    public WordSearchQuizAdapter(IOnClickListener mIOnClickListener, List<WordSearchCategoriesQuizzesResponse> mCategoriesQuizzesResponseList, String categoryName) {
        this.mIOnClickListener = mIOnClickListener;
        this.mCategoriesQuizzesResponseList = mCategoriesQuizzesResponseList;
        this.categoryName = categoryName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_quizs_wordsearch, parent, false);
        return new ViewHolder(view, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordSearchCategoriesQuizzesResponse categoriesQuizzesResponse = mCategoriesQuizzesResponseList.get(position);
        Glide.with(holder.itemView.getContext()).load(categoriesQuizzesResponse.getImage()).placeholder(R.drawable.wordsearch_placeholder).into(holder.mQuizIV);
        holder.mQuizNameTV.setText(categoriesQuizzesResponse.getName());
//        holder.mCategoryName.setVisibility(View.INVISIBLE);
        holder.mCategoryName.setText("Category: " + categoryName);

        if (categoriesQuizzesResponse.getAttemptedQuiz() != 0) {
//            holder.mApptemptMVC.setVisibility(View.VISIBLE);
            holder.mAttemptsTV.setText("Attempts: " + categoriesQuizzesResponse.getAttemptedQuiz() + "/3");
//            holder.view.setBackgroundColor(Color.parseColor("#33" + mColorName));
//            holder.mAttemptsTV.setTextColor(Color.parseColor("#" + mColorName));
        } else {
            holder.mAttemptsTV.setVisibility(View.GONE);
        }

        if (categoriesQuizzesResponse.getQuizStatus() == 2) {
//            holder.ViewBtn.setText("Tournament Cancelled!");
        }
        if (categoriesQuizzesResponse.getQuizStatus() == 1) {
//            holder.ViewBtn.setText("View Leaderboard");
        }
//        holder.ViewBtn.setBackgroundColor(Color.parseColor("#" + mColorName));
        holder.mWinPrizeTV.setText(" " + categoriesQuizzesResponse.getPrizePoolBreakthrough().get(0).getPrizeMoney() + " Coins");
        holder.mEntryFeeTV.setText("Entry: " + categoriesQuizzesResponse.getEntryFees() + " Coins");
        holder.mTotalParticipants.setText("" + categoriesQuizzesResponse.getPlayedparticipants() + "/" + categoriesQuizzesResponse.getTotalparticipants());
        holder.mJoinedPb.setProgress(categoriesQuizzesResponse.getPlayedparticipants());
        holder.mJoinedPb.setMax(categoriesQuizzesResponse.getTotalparticipants());
    }

    @Override
    public int getItemCount() {
        return mCategoriesQuizzesResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IOnClickListener mOnItemClickListener;
        @BindView(R.id.iv_quiz_image)
        ImageView mQuizIV;
        @BindView(R.id.tv_name)
        TextView mQuizNameTV;
        @BindView(R.id.tv_win)
        TextView mWinPrizeTV;
        @BindView(R.id.tv_entry)
        TextView mEntryFeeTV;
        @BindView(R.id.btn_play)
        AppCompatButton mPlayBtn;
        @BindView(R.id.tv_attempt)
        TextView mAttemptsTV;
        @BindView(R.id.tv_total_participants)
        TextView mTotalParticipants;
        @BindView(R.id.pb_joined)
        ProgressBar mJoinedPb;
        @BindView(R.id.tv_categories)
        TextView mCategoryName;

        public ViewHolder(View view, IOnClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            mPlayBtn.setOnClickListener(this);
//            ViewBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position);
            }
        }
    }
}
