package com.khiladiadda.leaderboard.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.AllLederBoardDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllLeaderBoardRVAdapter extends RecyclerView.Adapter<AllLeaderBoardRVAdapter.EventHolder> {

    private List<AllLederBoardDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private int mFrom;

    public AllLeaderBoardRVAdapter(List<AllLederBoardDetails> exhibitorList, int from) {
        this.mFDList = exhibitorList;
        this.mFrom = from;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        int rank = 0;
        AllLederBoardDetails details = mFDList.get(position);
        holder.mNameTV.setText(String.valueOf(details.getName()));
        if (!TextUtils.isEmpty(details.getWinningAmount())) {
            holder.mScoreTV.setText("Won: " + details.getWinningAmount() + " Coins");
        } else {
            holder.mScoreTV.setText("Won: 0 Coins");
        }
        if (mFrom == 1) {
            rank = position + 4;
        } else {
            rank = position + 1;
            holder.mUsernameTV.setText(details.getUsername() + " | " + details.getGameUsername());
        }
        holder.mRankTV.setText("#" + rank);
        if (mFrom == 1) {
            holder.mTimeTV.setVisibility(View.GONE);
        } else {
            holder.mTimeTV.setText("Kill: " + details.getKilled());
        }
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_score) TextView mScoreTV;
        @BindView(R.id.tv_time) TextView mTimeTV;
        @BindView(R.id.tv_rank) TextView mRankTV;
        @BindView(R.id.tv_username) TextView mUsernameTV;
        @BindView(R.id.iv_profile) ImageView mProfileIV;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            if (mFrom == 2) {
                mUsernameTV.setVisibility(View.VISIBLE);
            }
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_wishlist:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onWishlistClicked(getBindingAdapterPosition());
                    }
                    break;
                case R.id.iv_bookmark:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onBookmarkClicked(getBindingAdapterPosition());
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onWishlistClicked(int position);

        void onBookmarkClicked(int position);
    }

}