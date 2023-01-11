package com.khiladiadda.leaderboard.past.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.LeaderboardTeamMate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MateLeaderBoardRVAdapter extends RecyclerView.Adapter<MateLeaderBoardRVAdapter.EventHolder> {

    private Context mContext;
    private List<LeaderboardTeamMate> mList;

    public MateLeaderBoardRVAdapter(Context context, List<LeaderboardTeamMate> mateList) {
        this.mContext = context;
        this.mList = mateList;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new EventHolder(itemView);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        LeaderboardTeamMate details = mList.get(position);
        holder.mNameTV.setText(String.valueOf(details.getName()));
        holder.mRankTV.setText("Won: " + String.valueOf(details.getWon()) + " Coins");
        holder.mRankTV.setTextColor(ContextCompat.getColor(mContext, R.color.color_green));
        holder.mScoreTV.setText(details.getGameUsername() + " | " + details.getGameCharacterId());
        holder.mScoreTV.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
        holder.mTimeTV.setText("Kill: " + String.valueOf(details.getKilled()));
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(mContext).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override public int getItemCount() {
        return mList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_score) TextView mScoreTV;
        @BindView(R.id.tv_time) TextView mTimeTV;
        @BindView(R.id.tv_rank) TextView mRankTV;
        @BindView(R.id.tv_username) TextView mUsernameTV;
        @BindView(R.id.iv_profile) ImageView mProfileIV;

        public EventHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}