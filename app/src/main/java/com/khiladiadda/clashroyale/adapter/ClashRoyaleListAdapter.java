package com.khiladiadda.clashroyale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClashRoyaleListAdapter extends RecyclerView.Adapter<ClashRoyaleListAdapter.PersonViewHolder> {

    private List<LeagueListDetails> mEventList;
    private IOnItemClickListener mOnItemClickListener;

    public ClashRoyaleListAdapter(List<LeagueListDetails> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private LeagueListDetails getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override public int getItemCount() {
        return mEventList.size();
    }

    @Override public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_league, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(PersonViewHolder holder, int position) {
        LeagueListDetails details = getItemAt(position);
        holder.mTV.setText(details.getTitle());
        holder.mKillPointTV.setText(details.getKillPoint() + " Coins/Kill");
        holder.mEndTimeTV.setText(AppUtilityMethods.getConvertDateQuiz(details.getStart()));
        holder.mPrizeMoneyTV.setText(details.getPrizeMoney() + " Coins");
        holder.mTotalParticipantTV.setText(String.valueOf(details.getTotalParticipants()));
        holder.mMapTV.setText(details.getMap());
        if (details.getEntryFees() > 0) {
            holder.mEntryFeeTV.setText(details.getEntryFees() + " Coins");
        } else {
            holder.mEntryFeeTV.setText("Free");
        }
        if (details.getPlayedParticipants() == details.getTotalParticipants()) {
            holder.mParticipatedTV.setText("Filled " + details.getPlayedParticipants() + "/" + details.getTotalParticipants());
            holder.mProgressPB.setProgress(100);
        } else if (details.getPlayedParticipants() == 0) {
            holder.mParticipatedTV.setText(details.getPlayedParticipants() + "/" + details.getTotalParticipants());
            holder.mProgressPB.setProgress(1);
        } else {
            double divideResult = (double) details.getPlayedParticipants() / (double) details.getTotalParticipants();
            double participant = divideResult * 100;
            holder.mParticipatedTV.setText("Filling Fast " + details.getPlayedParticipants() + "/" + details.getTotalParticipants());
            holder.mProgressPB.setProgress((int) participant);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_image) ImageView mIV;
        @BindView(R.id.tv_heading) TextView mTV;
        @BindView(R.id.tv_entry) TextView mEntryFeeTV;
        @BindView(R.id.tv_prize) TextView mPrizeMoneyTV;
        @BindView(R.id.tv_map) TextView mMapTV;
        @BindView(R.id.tv_end_time) TextView mEndTimeTV;
        @BindView(R.id.tv_total_participant) TextView mTotalParticipantTV;
        @BindView(R.id.tv_participated) TextView mParticipatedTV;
        @BindView(R.id.tv_kill_point) TextView mKillPointTV;
        @BindView(R.id.pb_quiz_progress) ProgressBar mProgressPB;

        private IOnItemClickListener mOnItemClickListener;

        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
            }
        }
    }
}