package com.khiladiadda.rummy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.interfaces.IOnItemReplayClickListener;
import com.khiladiadda.network.model.response.RummyDetails;
import com.khiladiadda.utility.AppConstant;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RummyLiveTableAdpter extends RecyclerView.Adapter<RummyLiveTableAdpter.LudoContestHolder> {
    private Context mContext;
    private List<RummyDetails> mLudoChallengeList;
    private IOnItemReplayClickListener mOnItemClickListener;

    private int mMode;

    public RummyLiveTableAdpter(Context context, List<RummyDetails> ludoChallengeList, int mMode) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
        this.mMode = mMode;
    }

    public void setOnItemClickListener(IOnItemReplayClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_table_rummy, parent, false);
        return new LudoContestHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        final DecimalFormat decfor = new DecimalFormat("0.00");
        holder.mJoinNowTV.setText("Rejoin now");
        RummyDetails ludoContestBean = mLudoChallengeList.get(position);
        if (ludoContestBean.getNumPlayers() ==2) {
            holder.mPlayerTV.setVisibility(View.VISIBLE);
            holder.mPlayersMoreTv.setVisibility(View.GONE);
        } else {
            holder.mPlayerTV.setVisibility(View.GONE);
            holder.mPlayersMoreTv.setVisibility(View.VISIBLE);
        }

        //Game Mode
        if (ludoContestBean.getGameMode() == AppConstant.POOL_51) {
            holder.mGameModeTv.setText("Pool51");
        } else if (ludoContestBean.getGameMode() == AppConstant.POOL_101) {
            holder.mGameModeTv.setText("Pool101");
        } else if (ludoContestBean.getGameMode() == AppConstant.POOL_201) {
            holder.mGameModeTv.setText("Pool201");
        } else if (ludoContestBean.getGameMode() == AppConstant.POINT_13) {
            holder.mGameModeTv.setText("Point(13 cards)");
        } else if (ludoContestBean.getGameMode() == AppConstant.JOKER) {
            holder.mGameModeTv.setText("Point(2 Jokes)");
        } else if (ludoContestBean.getGameMode() == AppConstant.DEAL) {
            holder.mGameModeTv.setText("Deal");
        }
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public void changeType(int mode) {
        mMode = mode;
    }


    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_players)
        TextView mPlayerTV;
        @BindView(R.id.tv_players_more)
        TextView mPlayersMoreTv;
        @BindView(R.id.tv_join_now)
        TextView mJoinNowTV;
        @BindView(R.id.tv_game_mode)
        TextView mGameModeTv;

        private IOnItemReplayClickListener mOnItemClickListener;

        public LudoContestHolder(View view, IOnItemReplayClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onReplayItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}