package com.khiladiadda.quiz.list.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizListPastRVAdapter extends RecyclerView.Adapter<QuizListPastRVAdapter.EventHolder> {

    private List<QuizListDetails> mList;
    private IOnItemClickListener mOnItemClickListener;
    private QuizListPastRVAdapter.IOnItemChildClickListener mOnItemChildClickListener;

    public QuizListPastRVAdapter(List<QuizListDetails> exhibitorList) {
        this.mList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(QuizListPastRVAdapter.IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        QuizListDetails details = mList.get(position);
        holder.mHeadingTV.setText(details.getName());
        holder.mPrizeTV.setText(details.getPrizemoney() + "\n" + holder.mPrizeTV.getContext().getString(R.string.help_coins));
        holder.mEntryTV.setText(details.getEntryFees() + "\n" + holder.mEntryTV.getContext().getString(R.string.help_coins));
        holder.mStartTimeTV.setText(AppUtilityMethods.getConvertDateQuiz(details.getStart()));
        holder.mEndTimeTV.setText(AppUtilityMethods.getConvertDateQuiz(details.getEnd()));
        holder.mTotalParticipantTV.setText(String.valueOf(details.getTotalparticipants()));
        String imageURL = details.getImage();
        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(holder.mIV.getContext()).load(imageURL).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).dontTransform().into(holder.mIV);
        } else {
            holder.mIV.setBackground(AppCompatResources.getDrawable(holder.mIV.getContext(), R.drawable.quiz_image));
        }
        if (details.isCancelled()) {
            holder.mPlayTV.setText(R.string.text_quiz_cancelled);
            holder.mPlayTV.setEnabled(false);
        } else {
            holder.mPlayTV.setText(R.string.text_view_leaderboard);
            holder.mPlayTV.setEnabled(true);
        }
        if (details.getPlayedparticipants() == details.getTotalparticipants()) {
            holder.mParticipatedTV.setText(holder.mParticipatedTV.getContext().getString(R.string.help_filled) + details.getPlayedparticipants() + "/" + details.getTotalparticipants());
            holder.mProgressPB.setProgress(100);
        } else if (details.getPlayedparticipants() == 0) {
            holder.mParticipatedTV.setText(details.getPlayedparticipants() + "/" + details.getTotalparticipants());
            holder.mProgressPB.setProgress(1);
        } else {
            double divideResult = (double) details.getPlayedparticipants() / (double) details.getTotalparticipants();
            double participant = divideResult * 100;
            holder.mParticipatedTV.setText(holder.mParticipatedTV.getContext().getString(R.string.help_filling_fast) + details.getPlayedparticipants() + "/" + details.getTotalparticipants());
            holder.mProgressPB.setProgress((int) participant);
        }
    }

    @Override public int getItemCount() {
        return mList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_image) ImageView mIV;
        @BindView(R.id.tv_heading) TextView mHeadingTV;
        @BindView(R.id.tv_prize) TextView mPrizeTV;
        @BindView(R.id.tv_entry) TextView mEntryTV;
        @BindView(R.id.tv_start_time) TextView mStartTimeTV;
        @BindView(R.id.tv_end_time) TextView mEndTimeTV;
        @BindView(R.id.tv_total_participant) TextView mTotalParticipantTV;
        @BindView(R.id.btn_play) TextView mPlayTV;
        @BindView(R.id.tv_participated) TextView mParticipatedTV;
        @BindView(R.id.pb_quiz_progress) ProgressBar mProgressPB;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mPlayTV.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (v.getId() == R.id.btn_play) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onViewPastLeaderboardClicked(getBindingAdapterPosition());
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onViewPastLeaderboardClicked(int position);
    }

}