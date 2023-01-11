package com.khiladiadda.fanbattle.adapter;

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
import com.khiladiadda.network.model.response.MatchDetails;
import com.khiladiadda.network.model.response.FBTeamDetails;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FanBattleAdapter extends RecyclerView.Adapter<FanBattleAdapter.PersonViewHolder> {

    private List<MatchDetails> mList;
    private IOnItemClickListener mOnItemClickListener;

    public FanBattleAdapter(List<MatchDetails> mEventList) {
        this.mList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private MatchDetails getItemAt(int index) {
        return mList.get(index);
    }

    @Override public int getItemCount() {
        return mList.size();
    }

    @NonNull
    @Override public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_match, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        MatchDetails details = getItemAt(position);
        if (details.getPlayers() != null && details.getPlayers().getHomeTeam() != null) {
            FBTeamDetails homeTeam = details.getPlayers().getHomeTeam().getTeamDetails();
            personViewHolder.mTeamOneTV.setText(homeTeam.getName());
            if (!TextUtils.isEmpty(homeTeam.getLogoUrl())) {
                Glide.with(personViewHolder.mOneIV.getContext()).load(homeTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(personViewHolder.mOneIV);
            } else {
                Glide.with(personViewHolder.mOneIV.getContext()).clear(personViewHolder.mOneIV);
                personViewHolder.mOneIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            personViewHolder.mTeamOneTV.setText("");
            personViewHolder.mOneIV.setImageResource(R.drawable.splash_logo);
        }
        if (details.getPlayers() != null && details.getPlayers().getAwayTeam() != null) {
            FBTeamDetails awayTeam = details.getPlayers().getAwayTeam().getTeamDetails();
            personViewHolder.mTeamTwoTV.setText(awayTeam.getName());
            if (!TextUtils.isEmpty(awayTeam.getLogoUrl())) {
                Glide.with(personViewHolder.mTwoIV.getContext()).load(awayTeam.getLogoUrl()).placeholder(R.drawable.splash_logo).into(personViewHolder.mTwoIV);
            } else {
                Glide.with(personViewHolder.mTwoIV.getContext()).clear(personViewHolder.mTwoIV);
                personViewHolder.mTwoIV.setImageResource(R.drawable.splash_logo);
            }
        } else {
            personViewHolder.mTeamTwoTV.setText("");
            personViewHolder.mTwoIV.setImageResource(R.drawable.splash_logo);
        }
        personViewHolder.mDateTV.setText(AppUtilityMethods.getConvertDateMatch(details.getStartDateTime()));
        personViewHolder.mNameTV.setText(details.getSeries().getName());
        personViewHolder.mTimeLeftTV.setText("Starts In: " + AppUtilityMethods.getBattleRemainingTime(details.getStartDateTime()));
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_one) ImageView mOneIV;
        @BindView(R.id.iv_two) ImageView mTwoIV;
        @BindView(R.id.tv_team_one) TextView mTeamOneTV;
        @BindView(R.id.tv_team_two) TextView mTeamTwoTV;
        @BindView(R.id.tv_date) TextView mDateTV;
        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_time_left) TextView mTimeLeftTV;

        private IOnItemClickListener mOnItemClickListener;

        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}