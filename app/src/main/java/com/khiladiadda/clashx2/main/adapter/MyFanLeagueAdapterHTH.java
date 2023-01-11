package com.khiladiadda.clashx2.main.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.HTHResponseDetails;
import com.khiladiadda.network.model.response.hth.TeamHTH;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFanLeagueAdapterHTH extends RecyclerView.Adapter<MyFanLeagueAdapterHTH.ViewHolder> {

    private List<HTHResponseDetails> mBattleList;
    private IOnItemClickListener mOnItemClickListener;
    private boolean mLive, mUpcoming, past;

    public MyFanLeagueAdapterHTH(List<HTHResponseDetails> mBattleList, boolean live, boolean upcoming, boolean past) {
        this.mBattleList = mBattleList;
        this.mLive = live;
        this.mUpcoming = upcoming;
        this.past = past;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private HTHResponseDetails getItemAt(int index) {
        return mBattleList.get(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_hth, parent, false);
        return new ViewHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder personViewHolder, int position) {
        HTHResponseDetails details = mBattleList.get(position);
        personViewHolder.mLLAmount.setVisibility(View.GONE);
        if (details.getPlayers() != null && details.getPlayers().getHomeTeam() != null) {
            TeamHTH homeTeam = details.getPlayers().getHomeTeam().getTeam();
            personViewHolder.mPlayerOneTV.setText(homeTeam.getName());
            if (!TextUtils.isEmpty(homeTeam.getLogoUrl())) {
                Glide.with(personViewHolder.mPlayerTwoIV.getContext()).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(personViewHolder.mPlayerOneIV);
            } else {
                Glide.with(personViewHolder.mPlayerOneIV.getContext()).clear(personViewHolder.mPlayerOneIV);
                personViewHolder.mPlayerOneIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            personViewHolder.mPlayerOneTV.setText("");
            personViewHolder.mPlayerTwoIV.setImageResource(R.drawable.splash_logo);
        }
        if (details.getPlayers() != null && details.getPlayers().getAwayTeam() != null) {
            TeamHTH awayTeam = details.getPlayers().getAwayTeam().getTeam();
            personViewHolder.mPlayerTwoTV.setText(awayTeam.getName());
            if (!TextUtils.isEmpty(awayTeam.getLogoUrl())) {
                Glide.with(personViewHolder.mPlayerTwoIV.getContext()).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(personViewHolder.mPlayerTwoIV);
            } else {
                Glide.with(personViewHolder.mPlayerTwoIV.getContext()).clear(personViewHolder.mPlayerTwoIV);
                personViewHolder.mPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            personViewHolder.mPlayerTwoTV.setText("");
            personViewHolder.mPlayerTwoIV.setImageResource(R.drawable.splash_logo);
        }
        personViewHolder.mDateTV.setText("Date: " + AppUtilityMethods.getConvertDateFacts(details.getStartDateTime()));
        personViewHolder.mNameTV.setText(details.getSeries().getName());
        personViewHolder.mTimeLeftTV.setText("Time: " + AppUtilityMethods.getConvertTimeMatch(details.getStartDateTime()));
        if (details.isIs_lines_up() && mUpcoming) {
            personViewHolder.mLineOutTV.setVisibility(View.VISIBLE);
            personViewHolder.mLineOutTV.setText(R.string.lineup_out);
            personViewHolder.mLineOutTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lineup, 0, 0, 0);
        } else if (mLive) {
            personViewHolder.mLineOutTV.setVisibility(View.GONE);
        } else if(past){
            personViewHolder.mLineOutTV.setVisibility(View.VISIBLE);
            personViewHolder.mLineupRL.setBackgroundColor(Color.parseColor("#0A4900"));
            personViewHolder.mLineOutTV.setText(R.string.match_ended);
            if (details.isIs_cancelled()){
                personViewHolder.mLineupRL.setBackgroundColor(Color.parseColor("#C10000"));
                personViewHolder.mLineOutTV.setText(R.string.cancelled_match);
            }
        } else {
            personViewHolder.mLineOutTV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mBattleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_one)
        ImageView mPlayerOneIV;
        @BindView(R.id.tv_team_one)
        TextView mPlayerOneTV;
        @BindView(R.id.iv_two)
        ImageView mPlayerTwoIV;
        @BindView(R.id.tv_team_two)
        TextView mPlayerTwoTV;
        @BindView(R.id.ll_amount)
        LinearLayout mLLAmount;
        @BindView(R.id.tv_date)
        TextView mDateTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_time)
        TextView mTimeLeftTV;
        @BindView(R.id.tv_lineupout)
        TextView mLineOutTV;
        @BindView(R.id.rl_lineup)
        RelativeLayout mLineupRL;
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
                mOnItemClickListener.onClicked(getBindingAdapterPosition());
            }
        }
    }

    public interface IOnItemClickListener {
        void onClicked(int position);
    }
}
