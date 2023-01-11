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
import com.khiladiadda.network.model.response.WordSearchMyQuizzesResponse;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchTrendingQuizAdapter extends RecyclerView.Adapter<WordSearchTrendingQuizAdapter.ViewHolder> {
    private IOnClickListener mIOnClickListener;
    private List<WordSearchMyQuizzesResponse> mTrendingQuizList;
    private String mColorName;
    private String categoryName;

    public WordSearchTrendingQuizAdapter(IOnClickListener mIOnClickListener, List<WordSearchMyQuizzesResponse> mTrendingQuizList, String mColorName, String categoryName) {
        this.mIOnClickListener = mIOnClickListener;
        this.mTrendingQuizList = mTrendingQuizList;
        this.mColorName = mColorName;
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
        WordSearchMyQuizzesResponse trendingQuizResponse = mTrendingQuizList.get(position);
        Glide.with(holder.itemView.getContext()).load(trendingQuizResponse.getQuiz().get(0).getImage()).placeholder(R.drawable.wordsearch_placeholder).into(holder.mQuizIV);
        holder.mQuizNameTV.setText(trendingQuizResponse.getQuiz().get(0).getName());
        holder.mEntryTV.setText("Entry: " + trendingQuizResponse.getEntryFees() + " Coins");
        holder.mCategoryName.setText("Category: " + categoryName);
        holder.mJoinedPb.setProgress(trendingQuizResponse.getQuiz().get(0).getPlayedparticipants());
        holder.mJoinedPb.setMax(trendingQuizResponse.getQuiz().get(0).getTotalparticipants());
        if (trendingQuizResponse.getQuiz().get(0).getQuizStatus() == 1) {
            holder.mAttemptTV.setVisibility(View.GONE);
//            holder.mAttemptMVC.setVisibility(View.GONE);
            holder.mViewBtn.setText("View");
            holder.mEntryTV.setVisibility(View.VISIBLE);
            if (trendingQuizResponse.getWinningAmount() == 0)
                holder.mEntryTV.setText("Completed");
            else
                holder.mEntryTV.setText("Won: " + trendingQuizResponse.getWinningAmount() + " Coins");
            holder.mViewBtn.setClickable(true);
            holder.itemView.setClickable(true);
        } else if (trendingQuizResponse.getQuiz().get(0).getQuizStatus() == 2) {
            holder.mAttemptTV.setVisibility(View.GONE);
//            holder.mAttemptMVC.setVisibility(View.GONE);
            holder.mViewBtn.setText("Tournament Cancelled!");
            holder.mEntryTV.setVisibility(View.GONE);
            holder.mViewBtn.setClickable(false);
            holder.itemView.setClickable(false);
        } else {
            holder.mViewBtn.setClickable(true);
            holder.itemView.setClickable(true);
            holder.mEntryTV.setVisibility(View.VISIBLE);
            holder.mAttemptTV.setText("Attempts: " + trendingQuizResponse.getQuiz().get(0).getAttemptedQuiz() + "/3");
        }
        holder.mWinTv.setText(" " + trendingQuizResponse.getQuiz().get(0).getPrizePoolBreakthrough().get(0).getPrizeMoney() + " Coins");
        holder.mTotalPoints.setText("" + trendingQuizResponse.getQuiz().get(0).getPlayedparticipants() + "/" + trendingQuizResponse.getQuiz().get(0).getTotalparticipants());

    }

    @Override
    public int getItemCount() {
        return mTrendingQuizList.size();
//        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IOnClickListener mOnItemClickListener;
        @BindView(R.id.tv_name)
        TextView mQuizNameTV;
        //        @BindView(R.id.tv_win_prize)
//        TextView mWinPrizeTV;
        @BindView(R.id.tv_entry)
        TextView mEntryTV;
        @BindView(R.id.tv_total_participants)
        TextView mTotalPoints;
        @BindView(R.id.btn_play)
        AppCompatButton mViewBtn;
        @BindView(R.id.tv_attempt)
        TextView mAttemptTV;
        @BindView(R.id.tv_win)
        TextView mWinTv;
        @BindView(R.id.tv_categories)
        TextView mCategoryName;
        //        @BindView(R.id.mcv_attempt)
//        MaterialCardView mAttemptMVC;
        @BindView(R.id.iv_quiz_image)
        ImageView mQuizIV;
        @BindView(R.id.pb_joined)
        ProgressBar mJoinedPb;


        public ViewHolder(View view, IOnClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            mViewBtn.setOnClickListener(this);
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
