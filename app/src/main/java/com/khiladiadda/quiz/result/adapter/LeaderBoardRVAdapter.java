package com.khiladiadda.quiz.result.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.LeaderBoard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderBoardRVAdapter extends RecyclerView.Adapter<LeaderBoardRVAdapter.EventHolder> {

    private final Context mContext;
    private final List<LeaderBoard> mFDList;
    private final boolean result;

    public LeaderBoardRVAdapter(Context context, List<LeaderBoard> exhibitorList, boolean result) {
        this.mContext = context;
        this.mFDList = exhibitorList;
        this.result = result;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_leaderboard, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        LeaderBoard details = mFDList.get(position);
        if (position == 0) {
            holder.mLeaderboardRL.setBackgroundResource(R.color.light_red_white);
        } else {
            holder.mLeaderboardRL.setBackgroundResource(0);
        }
        holder.mNameTV.setText(String.valueOf(details.getName()));

        if (result) {
            if (details.getWinAmount() > 0) {
                holder.mScoreTV.setText("Won Coins\n" + details.getWinAmount());
            } else {
                holder.mScoreTV.setText("Won Coins\n0");
            }
        } else {
            holder.mScoreTV.setText("");
        }

        holder.mPointTV.setText("Points\n" + details.getPoint());
        holder.mRankTV.setText("#" + details.getRank());

        double time = (double) details.getTimeTaken() / 60000;
        if (time >= 1) {
            String doubleAsString = String.valueOf(time);
            int indexOfDecimal = doubleAsString.indexOf(".");
            String min = doubleAsString.substring(0, indexOfDecimal);
            double times = ((double) details.getTimeTaken() / 1000) % 60;
            String das = String.valueOf(times);
            int iod = das.indexOf(".");
            String sec = das.substring(0, iod);
            String milSec = das.substring(iod);
            if (milSec.length() > 3) {
                milSec = milSec.substring(0, 3);
            }
            holder.mTimeTV.setText("Time Taken\n" + min + "m" + sec + milSec + "s");
        } else {
            double times = (double) details.getTimeTaken() / 1000;
            String doubleAsString = String.valueOf(times);
            int indexOfDecimal = doubleAsString.indexOf(".");
            String sec = doubleAsString.substring(0, indexOfDecimal);
            String milSec = doubleAsString.substring(indexOfDecimal);
            holder.mTimeTV.setText("Time Taken\n" + sec + milSec + "s");
        }
        if (details.getDp() != null && !details.getDp().isEmpty()) {
            Glide.with(mContext).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_score)
        TextView mScoreTV;
        @BindView(R.id.tv_rank)
        TextView mRankTV;
        @BindView(R.id.tv_time)
        TextView mTimeTV;
        @BindView(R.id.tv_point)
        TextView mPointTV;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.rl_leaderboard)
        RelativeLayout mLeaderboardRL;

        public EventHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}