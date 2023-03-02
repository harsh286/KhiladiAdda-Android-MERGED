package com.khiladiadda.wordsearch.adapter;

import android.graphics.Color;
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
import com.khiladiadda.network.model.response.WordSearchLeaderBoardResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardlbResponse;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private IOnClickListener mIOnGamesClickListener;
    private List<WordSearchLeaderBoardResponse> mSearchLeaderBoardResponseList;
    private List<WordSearchLiveLeaderBoardlbResponse> mLeaderBoardMainResponsesList;
    private boolean isPlayed;

    public LeaderBoardAdapter(List<WordSearchLeaderBoardResponse> mSearchLeaderBoardResponseList, IOnClickListener mIOnGamesClickListener, List<WordSearchLiveLeaderBoardlbResponse> mLeaderBoardMainResponsesList, boolean isPlayed) {
        this.mIOnGamesClickListener = mIOnGamesClickListener;
        this.mSearchLeaderBoardResponseList = mSearchLeaderBoardResponseList;
        this.mLeaderBoardMainResponsesList = mLeaderBoardMainResponsesList;
        this.isPlayed = isPlayed;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.items_of_leaderboard_wordsearch, parent, false);
        return new ViewHolder(view, mIOnGamesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isPlayed) {
            WordSearchLiveLeaderBoardlbResponse wordSearchLiveLeaderBoardlbResponse = mLeaderBoardMainResponsesList.get(position);
            int millis = wordSearchLiveLeaderBoardlbResponse.getTimeTaken();
            if (position == 0) {
                holder.cardView.setBackgroundResource(R.color.lightbluecolor);
            } else {
                holder.cardView.setBackgroundResource(0);
            }
            holder.mWonTV.setTextColor(Color.parseColor("#848484"));
            setupUi(holder,
                    position,
                    wordSearchLiveLeaderBoardlbResponse.getUser().getName(),
                    "Best Time: " + String.format("%d.%02ds",
                            TimeUnit.MILLISECONDS.toSeconds(millis),
                            millis -
                                    (TimeUnit.MILLISECONDS.toSeconds(millis) * 1000)),
                    "Word count: " + wordSearchLiveLeaderBoardlbResponse.getRightAnswers(),
                    "Won: " + wordSearchLiveLeaderBoardlbResponse.getWinningAmount(),
                    wordSearchLiveLeaderBoardlbResponse.getUser().getDp());
            holder.mRankTV.setVisibility(View.VISIBLE);
            holder.mRankTV.setText("#"+wordSearchLiveLeaderBoardlbResponse.getRank());
        } else {
            WordSearchLeaderBoardResponse wordSearchLeaderBoardResponse = mSearchLeaderBoardResponseList.get(position);

            setupUi(holder, position, wordSearchLeaderBoardResponse.getName(),
                    "Played: " + wordSearchLeaderBoardResponse.getnQuiz().getPlayed(),
                    "Won: " + wordSearchLeaderBoardResponse.getnQuiz().getWon(),
                    "",
                    wordSearchLeaderBoardResponse.getDp());
            holder.mRankTV.setVisibility(View.GONE);
        }
    }

    private void setupUi(@NonNull ViewHolder holder, int position, String name, String played, String won, String wons, String dp) {
        holder.mNameTV.setText(name);
        holder.mPlayedTV.setText(played);
        holder.mWonTV.setText(won);
//        holder.itemRowBinding.tvWons.setText(wons);
        Glide.with(holder.itemView.getContext()).load(dp).placeholder(R.drawable.profile).into(holder.mParticipantsIV);
    }

    @Override
    public int getItemCount() {
        if (isPlayed) return mLeaderBoardMainResponsesList.size();
        return mSearchLeaderBoardResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IOnClickListener mOnItemClickListener;
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
        public ViewHolder(View view, IOnClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position);
            }
        }
    }
}
