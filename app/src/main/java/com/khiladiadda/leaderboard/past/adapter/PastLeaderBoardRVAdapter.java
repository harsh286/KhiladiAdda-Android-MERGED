package com.khiladiadda.leaderboard.past.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.SquadLeaderboard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastLeaderBoardRVAdapter extends RecyclerView.Adapter<PastLeaderBoardRVAdapter.EventHolder> {

    private Context mContext;
    private List<SquadLeaderboard> mTeamList;

    public PastLeaderBoardRVAdapter(Context context, List<SquadLeaderboard> teamList) {
        this.mContext = context;
        this.mTeamList = teamList;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_leaderboard, parent, false);
        return new EventHolder(itemView);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        SquadLeaderboard details = mTeamList.get(position);
        holder.mNameTV.setText(String.valueOf(details.getTeamName()));
        holder.mRankTV.setText("#" + String.valueOf(details.getRank()));
        holder.mWinTV.setText("Won: " + String.valueOf(details.getWinningAmount()) + " Coins");
        MateLeaderBoardRVAdapter mMateAdapter = new MateLeaderBoardRVAdapter(mContext, details.getUser());
        holder.mTeamMateRV.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mTeamMateRV.setAdapter(mMateAdapter);
    }

    @Override public int getItemCount() {
        return mTeamList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_rank) TextView mRankTV;
        @BindView(R.id.tv_win) TextView mWinTV;
        @BindView(R.id.rv_team_mate) RecyclerView mTeamMateRV;

        public EventHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}