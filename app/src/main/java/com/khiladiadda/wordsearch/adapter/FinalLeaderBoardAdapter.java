package com.khiladiadda.wordsearch.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardlbResponse;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalLeaderBoardAdapter extends RecyclerView.Adapter<FinalLeaderBoardAdapter.ViewHolder> {
    private List<WordSearchLiveLeaderBoardlbResponse> mLeaderBoardMainResponsesList;
    private boolean isPlayed;

    public FinalLeaderBoardAdapter(List<WordSearchLiveLeaderBoardlbResponse> mLeaderBoardMainResponsesList) {
        this.mLeaderBoardMainResponsesList = mLeaderBoardMainResponsesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_leaderboard_wordsearch, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordSearchLiveLeaderBoardlbResponse wordSearchLiveLeaderBoardlbResponse = mLeaderBoardMainResponsesList.get(position);
        holder.mWonTV.setTextColor(Color.parseColor("#848484"));
        int millis = wordSearchLiveLeaderBoardlbResponse.getTimeTaken();
        if (position == 0) {
            holder.cardView.setBackgroundResource(R.color.lightbluecolor);
        } else {
            holder.cardView.setBackgroundResource(0);
        }
        setupUi(holder, position, wordSearchLiveLeaderBoardlbResponse.getUser().getName(),
                "Best Time: " + String.format("%d.%02ds",
                        TimeUnit.MILLISECONDS.toSeconds(millis),
                        millis -
                                (TimeUnit.MILLISECONDS.toSeconds(millis) * 1000)),
                "Word count: " + wordSearchLiveLeaderBoardlbResponse.getRightAnswers(),
                "Won: "+wordSearchLiveLeaderBoardlbResponse.getWinningAmount()+" coins",
                wordSearchLiveLeaderBoardlbResponse.getUser().getDp());
        holder.mRankTV.setVisibility(View.VISIBLE);
        holder.mRankTV.setText("#" + wordSearchLiveLeaderBoardlbResponse.getRank());
    }

    private void setupUi(@NonNull ViewHolder holder, int position, String name, String played, String won, String wons, String dp) {
        holder.mNameTV.setText(name);
        holder.mPlayedTV.setText(played);
        holder.mWonTV.setText(won);
        holder.mWonsTV.setText(wons);
//        Glide.with(holder.itemView.getContext()).load(dp).placeholder(R.drawable.profile).into(holder.mParticipantsIV);
        if (!TextUtils.isEmpty(dp)) {
            Glide.with(holder.mParticipantsIV.getContext()).load(dp).placeholder(R.mipmap.ic_launcher).into(holder.mParticipantsIV);
        } else {
            Glide.with(holder.mParticipantsIV.getContext()).clear(holder.mParticipantsIV);
            holder.mParticipantsIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mLeaderBoardMainResponsesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private IOnClickListener mOnItemClickListener;
        @BindView(R.id.tv_won)
        TextView mWonTV;
        @BindView(R.id.tv_wons)
        TextView mWonsTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_played)
        TextView mPlayedTV;
        @BindView(R.id.tv_rank)
        TextView mRankTV;
        @BindView(R.id.iv_participants)
        ImageView mParticipantsIV;
        @BindView(R.id.cl)
        ConstraintLayout cardView;
        public ViewHolder(View  view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}

