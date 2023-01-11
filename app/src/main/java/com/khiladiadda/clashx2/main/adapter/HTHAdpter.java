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
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HTHAdpter extends RecyclerView.Adapter<HTHAdpter.ViewHolder> {

    private List<HTHResponseDetails> mEventList;
    private IOnItemClickListener mOnItemClickListener;

    public HTHAdpter(List<HTHResponseDetails> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_hth, parent, false);
        return new ViewHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HTHResponseDetails details = mEventList.get(position);
        if (details.getPlayers() != null && details.getPlayers().getHomeTeam() != null) {
            TeamHTH homeTeam = details.getPlayers().getHomeTeam().getTeam();
            holder.mTeamOneTV.setText(homeTeam.getName());
            if (!TextUtils.isEmpty(homeTeam.getLogoUrl())) {
                Glide.with(holder.mOneIV.getContext()).load(homeTeam.getLogoUrl()).placeholder(R.mipmap.ic_launcher).into(holder.mOneIV);
            } else {
                Glide.with(holder.mOneIV.getContext()).clear(holder.mOneIV);
                holder.mOneIV.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            holder.mTeamOneTV.setText("");
            holder.mOneIV.setImageResource(R.mipmap.ic_launcher);
        }
        if (details.getPlayers() != null && details.getPlayers().getAwayTeam() != null) {
            TeamHTH awayTeam = details.getPlayers().getAwayTeam().getTeam();
            holder.mTeamTwoTV.setText(awayTeam.getName());
            if (!TextUtils.isEmpty(awayTeam.getLogoUrl())) {
                Glide.with(holder.mTwoIV.getContext()).load(awayTeam.getLogoUrl()).placeholder(R.mipmap.ic_launcher).into(holder.mTwoIV);
            } else {
                Glide.with(holder.mTwoIV.getContext()).clear(holder.mTwoIV);
                holder.mTwoIV.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            holder.mTeamTwoTV.setText("");
            holder.mTwoIV.setImageResource(R.mipmap.ic_launcher);
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
        return mEventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_one)
        ImageView mOneIV;
        @BindView(R.id.iv_two)
        ImageView mTwoIV;
        @BindView(R.id.tv_team_one)
        TextView mTeamOneTV;
        @BindView(R.id.tv_team_two)
        TextView mTeamTwoTV;
        @BindView(R.id.tv_date)
        TextView mDateTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_time_left)
        TextView mTimeLeftTV;
        @BindView(R.id.tv_time)
        TextView mTimeTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.tv_lineupout)
        TextView mLineOutTV;
        private IOnItemClickListener mOnItemClickListener;

        public ViewHolder(@NonNull View itemView, IOnItemClickListener onItemClickListener) {
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