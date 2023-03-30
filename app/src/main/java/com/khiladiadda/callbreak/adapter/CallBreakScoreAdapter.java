package com.khiladiadda.callbreak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.CallBreakHistoryPlayerResponse;
import com.khiladiadda.preference.AppSharedPreference;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CallBreakScoreAdapter extends RecyclerView.Adapter<CallBreakScoreAdapter.LudoContestHolder> {

    private Context mContext;
    private List<CallBreakHistoryPlayerResponse> mLudoChallengeList;

    public CallBreakScoreAdapter(Context context, List<CallBreakHistoryPlayerResponse> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }


    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_cb_history, parent, false);
        return new LudoContestHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        CallBreakHistoryPlayerResponse mPlayerResponse = mLudoChallengeList.get(position);
        if (Objects.equals(mPlayerResponse.getUserId(), AppSharedPreference.getInstance().getMasterData().getResponse().getProfile().getId())) {
            holder.cbHistoryMcv.setBackground(mContext.getDrawable(R.drawable.cb_history_bg));
        }else {
            holder.cbHistoryMcv.setBackgroundResource(0);
        }
        holder.mNumberingTv.setText("#" + mPlayerResponse.getRank());
        holder.mNameTv.setText(mPlayerResponse.getName());
        holder.mPointsTv.setText("" + mPlayerResponse.getScore());
        holder.mWonTv.setText("" + mPlayerResponse.getWinningAmount());
        Glide.with(mContext).load(mPlayerResponse.getDp()).placeholder(R.drawable.splash_logo).into(holder.mProfileCiv);

    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }


    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_numbering)
        TextView mNumberingTv;
        @BindView(R.id.civ_profile)
        CircleImageView mProfileCiv;
        @BindView(R.id.tv_name)
        TextView mNameTv;
        @BindView(R.id.tv_points)
        TextView mPointsTv;
        @BindView(R.id.tv_won)
        TextView mWonTv;
        @BindView(R.id.mcv_cb_history)
        MaterialCardView cbHistoryMcv;

        public LudoContestHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}