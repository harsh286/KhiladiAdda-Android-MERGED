package com.khiladiadda.league.adapter;

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
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeagueListAdapter extends RecyclerView.Adapter<LeagueListAdapter.PersonViewHolder> {

    private List<LeagueListDetails> mEventList;
    private IOnItemClickListener mOnItemClickListener;
    private String mGameId;

    public LeagueListAdapter(List<LeagueListDetails> mEventList, String mFrom) {
        this.mEventList = mEventList;
        this.mGameId = mFrom;
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

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_solo_league, viewGroup, false);
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
        holder.mKillPointTV.setText(String.valueOf(details.getKillPoint()) + "\nCoins/Kill");
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
        AppSharedPreference preference = AppSharedPreference.getInstance();
        if (preference != null && mGameId != null) {
            if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.tdm);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_LITE_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.ic_bgmi);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FREEFIRE_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.ic_ff);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FF_CLASH_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.ff_clashs);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FF_MAX_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.freefire_max);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.pubg_gobal_lite);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.esports_per);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                holder.mIV.setImageResource(R.drawable.pubg_ns);
            }
        } else {
            holder.mIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_image)
        ImageView mIV;
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
        @BindView(R.id.tv_kill_point)
        TextView mKillPointTV;
        @BindView(R.id.tv_map)
        TextView mMapTV;
        @BindView(R.id.pb_quiz_progress)
        ProgressBar mProgressPB;

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