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
import com.khiladiadda.network.model.response.OverallLeadBoardList;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FbLeadBoardAdapter extends RecyclerView.Adapter<FbLeadBoardAdapter.ViewHolder> {

    private List<OverallLeadBoardList> overallLeadBoardLists;

    public FbLeadBoardAdapter(List<OverallLeadBoardList> overallLeadBoardLists) {
        this.overallLeadBoardLists = overallLeadBoardLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OverallLeadBoardList details = overallLeadBoardLists.get(position);
        holder.mNameTV.setText(String.valueOf(details.getName()));
        if (!TextUtils.isEmpty(String.valueOf(details.getWinningAmount()))) {
            holder.mScoreTV.setText("Won: " + "\u20B9" + new DecimalFormat("##.##").format(details.getWinningAmount()));
        } else {
            holder.mScoreTV.setText("Won: \u20B9 0");
        }
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        int rank = 0;
        rank = position + 4;
        holder.mRankTV.setText("#" + rank);
    }

    @Override
    public int getItemCount() {
        return overallLeadBoardLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_score)
        TextView mScoreTV;
        @BindView(R.id.tv_username)
        TextView mUsernameTV;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_rank) TextView mRankTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}