package com.khiladiadda.league.myleague.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLeagueLiveAdapter extends RecyclerView.Adapter<MyLeagueLiveAdapter.PersonViewHolder> {

    private List<LeagueListDetails> mEventList;
    private IOnItemClickListener mOnItemClickListener;

    public MyLeagueLiveAdapter(List<LeagueListDetails> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private LeagueListDetails getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live_league, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        LeagueListDetails details = getItemAt(position);
        holder.mTV.setText(details.getTitle());
        if (details.getEntryFees() > 0) {
            holder.mEntryFeeTV.setText(details.getEntryFees() + "\nCoins");
        } else {
            holder.mEntryFeeTV.setText("Free");
        }
        holder.mKillPointTV.setText(String.valueOf(details.getKillPoint()) + "\nCoins/KIll");
        holder.mEndTimeTV.setText(AppUtilityMethods.getConvertDateQuiz(details.getStart()));
        holder.mPrizeMoneyTV.setText(details.getPrizeMoney() + "\nCoins");
        holder.mTotalParticipantTV.setText(String.valueOf(details.getTotalParticipants()));
        holder.mMapTV.setText(String.valueOf(details.getMap()));
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
        if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.tdm);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_LITE_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.ic_bgmi);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.ic_ff);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.ff_clashs);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.freefire_max);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.pubg_gobal_lite);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
//            holder.mIV.setImageResource(R.drawable.esports_per);
            holder.mIV.setImageResource(R.drawable.esports_per);
        } else if (details.getGameId().equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            holder.mIV.setImageResource(R.drawable.pubg_ns);
            holder.mPlayTV.setText(R.string.text_pubgns_live);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_heading)
        TextView mTV;
        @BindView(R.id.tv_entry)
        TextView mEntryFeeTV;
        @BindView(R.id.tv_prize)
        TextView mPrizeMoneyTV;
        @BindView(R.id.tv_end_time)
        TextView mEndTimeTV;
        @BindView(R.id.tv_total_participant)
        TextView mTotalParticipantTV;
        @BindView(R.id.tv_participated)
        TextView mParticipatedTV;
        @BindView(R.id.pb_quiz_progress)
        ProgressBar mProgressPB;
        @BindView(R.id.tv_kill_point)
        TextView mKillPointTV;
        @BindView(R.id.iv_image)
        ImageView mIV;
        @BindView(R.id.tv_map)
        TextView mMapTV;
        @BindView(R.id.btn_play)
        TextView mPlayTV;

        private IOnItemClickListener mOnItemClickListener;

        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }
}