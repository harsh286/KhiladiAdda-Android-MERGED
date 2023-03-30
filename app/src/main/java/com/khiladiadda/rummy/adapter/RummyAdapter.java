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
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RummyAdapter extends RecyclerView.Adapter<RummyAdapter.LudoContestHolder> {

    private Context mContext;
    private List<RummyDetails> mLudoChallengeList;
    private IOnItemClickListener mOnItemClickListener;
    private int mMode;

    public RummyAdapter(Context context, List<RummyDetails> ludoChallengeList, int mMode) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
        this.mMode = mMode;
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
        final DecimalFormat decfor = new DecimalFormat("0.00");
        RummyDetails ludoContestBean = mLudoChallengeList.get(position);
        if (mMode == 1) {
            holder.mPointsTv.setText("Points Value");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString()) / 80) / 100));
        } else if (mMode == 2) {
            holder.mPointsTv.setText("Entry Fee");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString())) / 100));
//            holder.mEntryFeeTV.setText("" + ludoContestBean.getEntryFee() + " Pool");
        } else if (mMode == 3) {
            holder.mPointsTv.setText("Entry Fee");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString())) / 100));
        }
        if (ludoContestBean.getNumPlayers() == 2) {
            holder.mPlayerTV.setVisibility(View.VISIBLE);
            holder.mPlayersMoreTv.setVisibility(View.GONE);
        } else {
            holder.mPlayerTV.setVisibility(View.GONE);
            holder.mPlayersMoreTv.setVisibility(View.VISIBLE);
        }
        holder.mOnlineTV.setText("" + ludoContestBean.getOnline());
//        holder.mEntryFeeTV.setText("₹"+ludoContestBean.getEntryFee());
        holder.mWinningAmountTV.setText("₹ " + AppUtilityMethods.roundUpNumber(ludoContestBean.getMaxWin()));
        holder.mWinningAmountTV.setText("₹ " + decfor.format(Float.parseFloat(ludoContestBean.getMaxWin().toString()) / 100));
    }

    public void changeType(int mode) {
        mMode = mode;
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
        @BindView(R.id.tv_players_more)
        TextView mPlayersMoreTv;
        @BindView(R.id.tv_online)
        TextView mOnlineTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_bonus)
        TextView mBonusTV;
        @BindView(R.id.tv_points)
        TextView mPointsTv;
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