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
import com.khiladiadda.network.model.response.RummyDetails;
import com.khiladiadda.network.model.response.RummyPayload;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RummyAdapter extends RecyclerView.Adapter<RummyAdapter.LudoContestHolder> {

    private Context mContext;
    private List<RummyDetails> mLudoChallengeList;
    private IOnItemClickListener mOnItemClickListener;

    public RummyAdapter(Context context, List<RummyDetails> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rummy, parent, false);
        return new LudoContestHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        RummyDetails ludoContestBean = mLudoChallengeList.get(position);
        holder.mEntryFeeTV.setText(ludoContestBean.getEntryFee() + " Coins");
        holder.mWinningAmountTV.setText("Winning: " + ludoContestBean.getMaxWin() + " Coins");
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_entry_fee)
        TextView mEntryFeeTV;
        @BindView(R.id.tv_players)
        TextView mPlayerTV;
        @BindView(R.id.tv_online)
        TextView mOnlineTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_bonus)
        TextView mBonusTV;
        private IOnItemClickListener mOnItemClickListener;

        public LudoContestHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}