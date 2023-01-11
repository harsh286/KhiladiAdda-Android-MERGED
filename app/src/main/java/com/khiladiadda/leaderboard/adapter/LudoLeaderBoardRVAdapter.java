package com.khiladiadda.leaderboard.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.LudoLeaderboardDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoLeaderBoardRVAdapter extends RecyclerView.Adapter<LudoLeaderBoardRVAdapter.EventHolder> {

    private List<LudoLeaderboardDetails> mLudoList;

    public LudoLeaderBoardRVAdapter(List<LudoLeaderboardDetails> ludoList, int from) {
        this.mLudoList = ludoList;
    }

    @NonNull
    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new EventHolder(itemView);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        int rank = 0;
        LudoLeaderboardDetails details = mLudoList.get(position);
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mNameTV.setText(String.valueOf(details.getName()));
        rank = position + 4;
        holder.mRankTV.setText("#" + rank);
        if (!TextUtils.isEmpty(String.valueOf(details.getTotal()))) {
            holder.mScoreTV.setText("Won: " + details.getTotal() + " Coins");
        } else {
            holder.mScoreTV.setText("Won: 0 Coins");
        }
        if (details.getnLudo() != null) {
            holder.mUsernameTV.setText("Won: " + details.getnLudo().getWon() + " Challenges");
        }
    }

    @Override public int getItemCount() {
        return mLudoList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_score) TextView mScoreTV;
        @BindView(R.id.tv_rank) TextView mRankTV;
        @BindView(R.id.tv_username) TextView mUsernameTV;
        @BindView(R.id.iv_profile) ImageView mProfileIV;

        public EventHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mUsernameTV.setVisibility(View.VISIBLE);
        }

        @Override public void onClick(View v) {
            v.getId();
        }
    }
}