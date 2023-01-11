package com.khiladiadda.quiz.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.QuizParticipant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizParticipantAdapter extends RecyclerView.Adapter<QuizParticipantAdapter.EventHolder> {

    private List<QuizParticipant> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public QuizParticipantAdapter(List<QuizParticipant> exhibitorList) {
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_participant, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        QuizParticipant details = mFDList.get(position);
        holder.mNameTV.setText(String.valueOf(details.getName()));
        holder.mPlayedTV.setText("Played : " + String.valueOf(details.getNQuiz().getPlayed()));
        holder.mWonTV.setText("Won : " + String.valueOf(details.getNQuiz().getWon()));
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getDp()).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_playedfor) TextView mPlayedTV;
        @BindView(R.id.tv_win) TextView mWonTV;
        @BindView(R.id.iv_profile) ImageView mProfileIV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}