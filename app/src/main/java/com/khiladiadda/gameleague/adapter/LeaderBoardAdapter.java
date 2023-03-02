package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseLeaderBoardSub;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ResponseLeaderBoardSub> mLeaderBoardResponse;

    public LeaderBoardAdapter(Context mContext, ArrayList<ResponseLeaderBoardSub> mLeaderBoardResponse) {
        this.mContext = mContext;
        this.mLeaderBoardResponse = mLeaderBoardResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View leaderboard = layoutInflater.inflate(R.layout.games_leaderboard_items, parent, false);
        return new ViewHolder(leaderboard);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseLeaderBoardSub leaderBoardDroidoResponse = mLeaderBoardResponse.get(position);
        holder.tvParticipantsName.setText(leaderBoardDroidoResponse.getUserInfo().getName());
        String newRank = "#" + leaderBoardDroidoResponse.getRank();
        holder.tvParticipantsRank.setText(newRank);
        holder.tvTotalTime.setText("" + leaderBoardDroidoResponse.getTimeTaken() * 1000);
        setTimeWithSecond(holder, leaderBoardDroidoResponse.getTimeTaken() * 1000);
        holder.tvTotalScore.setText(leaderBoardDroidoResponse.getScore() + "");

    }

    private void setTimeWithSecond(@NonNull ViewHolder holder, int timeTaken) {
        holder.tvTotalTime.setText(String.format("%02d : %02d", timeTaken / (60 * 1000), (timeTaken / 1000) % 60));
    }

    @Override
    public int getItemCount() {
        return mLeaderBoardResponse.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvParticipantsRank;
        TextView tvParticipantsName;
        TextView tvTotalTime;
        TextView tvTotalScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvParticipantsRank = itemView.findViewById(R.id.tv_number_of_participant_leaderboard);
            tvParticipantsName = itemView.findViewById(R.id.tv_participant_name_leaderboard);
            tvTotalTime = itemView.findViewById(R.id.tv_total_time_leaderboard);
            tvTotalScore = itemView.findViewById(R.id.tv_total_score_leaderboard);
        }
    }
}
