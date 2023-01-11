package com.khiladiadda.clashx2.main.adapter;

import android.graphics.Color;
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
import com.khiladiadda.clashx2.interfaces.IOnItemGamesClickListener;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClashXDashBoardAdapter extends RecyclerView.Adapter<ClashXDashBoardAdapter.GamesViewHolder> {
    private ArrayList<HTHResponseDetails> gamesDataArrayList;
    private IOnItemGamesClickListener mIOnItemGamesClickListener;

    public void setOnItemClickListener(IOnItemGamesClickListener mIOnItemGamesClickListener) {
        this.mIOnItemGamesClickListener = mIOnItemGamesClickListener;
    }

    public ClashXDashBoardAdapter(ArrayList<HTHResponseDetails> gamesDataArrayList) {
        this.gamesDataArrayList = gamesDataArrayList;
    }

    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dashboard_games, viewGroup, false);
        return new GamesViewHolder(view, mIOnItemGamesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {
        HTHResponseDetails details = gamesDataArrayList.get(position);
        if (details.getPlayers() != null && details.getPlayers().getHomeTeam() != null) {
            TeamHTH homeTeam = details.getPlayers().getHomeTeam().getTeam();
            holder.mTeamOneTV.setText(homeTeam.getName());
            if (!TextUtils.isEmpty(homeTeam.getLogoUrl())) {
                Glide.with(holder.mOneIV.getContext()).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(holder.mOneIV);
            } else {
                Glide.with(holder.mOneIV.getContext()).clear(holder.mOneIV);
                holder.mOneIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            holder.mTeamOneTV.setText("");
            holder.mOneIV.setImageResource(R.drawable.splash_logo);
        }
        if (details.getPlayers() != null && details.getPlayers().getAwayTeam() != null) {
            TeamHTH awayTeam = details.getPlayers().getAwayTeam().getTeam();
            holder.mTeamTwoTV.setText(awayTeam.getName());
            if (!TextUtils.isEmpty(awayTeam.getLogoUrl())) {
                Glide.with(holder.mTwoIV.getContext()).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(holder.mTwoIV);
            } else {
                Glide.with(holder.mTwoIV.getContext()).clear(holder.mTwoIV);
                holder.mTwoIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            holder.mTeamTwoTV.setText("");
            holder.mTwoIV.setImageResource(R.drawable.splash_logo);
        }
        holder.mDateTV.setText("Date: " + AppUtilityMethods.getConvertDateFacts(details.getStartDateTime()));
        holder.mNameTV.setText(details.getSeries().getName());
        holder.mTimeTV.setText("Time: " + AppUtilityMethods.getConvertTimeMatch(details.getStartDateTime()));
        holder.mTimeLeftTV.setText("Starts in: " + AppUtilityMethods.getBattleRemainingTime(details.getStartDateTime()));
        if (AppUtilityMethods.getBattleRemainingTime(details.getStartDateTime()).equalsIgnoreCase("30:00")) {
            holder.mTimeLeftTV.setTextColor(Color.parseColor("#E3B201"));
        } else if (AppUtilityMethods.getBattleRemainingTime(details.getStartDateTime()).equalsIgnoreCase("10:00")) {

            holder.mTimeLeftTV.setTextColor(Color.parseColor("#E30101"));
        } else {
            holder.mTimeLeftTV.setTextColor(Color.parseColor("#49C100"));
        }
//LineUp Out
        if (details.isIs_lines_up()) {
            holder.mLineOutTV.setVisibility(View.VISIBLE);
        } else {
            holder.mLineOutTV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return gamesDataArrayList.size();
    }

    public class GamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView mOneIV;
        CircleImageView mTwoIV;
        TextView mTeamOneTV;
        TextView mTeamTwoTV;
        TextView mDateTV;
        TextView mNameTV;
        TextView mTimeLeftTV;
        TextView mTimeTV;
        TextView mAmountTV;
        TextView mLineOutTV;
        private IOnItemGamesClickListener mIOnItemGamesClickListener;

        public GamesViewHolder(@NonNull View itemView, IOnItemGamesClickListener iOnItemGamesClickListener) {
            super(itemView);
            mOneIV = itemView.findViewById(R.id.iv_one);
            mTwoIV = itemView.findViewById(R.id.iv_two);
            mTeamOneTV = itemView.findViewById(R.id.tv_team_one);
            mTeamTwoTV = itemView.findViewById(R.id.tv_team_two);
            mDateTV = itemView.findViewById(R.id.tv_date);
            mNameTV = itemView.findViewById(R.id.tv_name);
            mTimeLeftTV = itemView.findViewById(R.id.tv_time_left);
            mTimeTV = itemView.findViewById(R.id.tv_time);
            mAmountTV = itemView.findViewById(R.id.tv_amount);
            mLineOutTV = itemView.findViewById(R.id.tv_lineupout);
            mIOnItemGamesClickListener = iOnItemGamesClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mIOnItemGamesClickListener != null) {
                mIOnItemGamesClickListener.onGamesItemClick(view, getBindingAdapterPosition(), 0);
            }
        }
    }
}
