package com.khiladiadda.callbreak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.CallBreakDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallBreakAdapter extends RecyclerView.Adapter<CallBreakAdapter.LudoContestHolder> {

    private Context mContext;
    private List<CallBreakDetails> mLudoChallengeList;
    private IOnItemClickListener mOnItemClickListener;

    public CallBreakAdapter(Context context, List<CallBreakDetails> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_callbreak, parent, false);
        return new LudoContestHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        CallBreakDetails ludoContestBean = mLudoChallengeList.get(position);
        holder.mEntryFeeTV.setText("Entry Fee ₹" + String.valueOf(ludoContestBean.getEntryFees()));
        holder.mWinningAmountTV.setText(String.valueOf(ludoContestBean.getWinningAmount()));
        if (ludoContestBean.getLiveEnabled()) {
            holder.mLiveTV.setVisibility(View.VISIBLE);
            holder.mLiveIv.setVisibility(View.VISIBLE);
            holder.mOnlineTV.setVisibility(View.VISIBLE);
            holder.mIvPlayers.setVisibility(View.VISIBLE);
        } else {
            holder.mLiveTV.setVisibility(View.GONE);
            holder.mLiveIv.setVisibility(View.GONE);
            holder.mOnlineTV.setVisibility(View.GONE);
            holder.mIvPlayers.setVisibility(View.GONE);
        }
        holder.mOnlineTV.setText(String.valueOf(ludoContestBean.getParticipants()));
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public interface IOnItemChildClickListener {
        void onAcceptClicked(int position);
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_entry_fee)
        TextView mEntryFeeTV;
        @BindView(R.id.tv_players)
        TextView mPlayerTV;
        @BindView(R.id.tv_round)
        TextView mRoundTV;
        @BindView(R.id.tv_online)
        TextView mOnlineTV;
        @BindView(R.id.btn_wining_amount)
        Button mWinningAmountTV;
        @BindView(R.id.tv_live)
        TextView mLiveTV;
        @BindView(R.id.iv_live)
        ImageView mLiveIv;
        @BindView(R.id.iv_players)
        ImageView mIvPlayers;
        private IOnItemClickListener mOnItemClickListener;

        public LudoContestHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mWinningAmountTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}