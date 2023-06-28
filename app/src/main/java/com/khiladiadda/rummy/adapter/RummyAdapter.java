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
import com.khiladiadda.network.model.response.PlayersDetails;
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
            holder.mFeeEntryTV.setVisibility(View.VISIBLE);
            holder.mFeeEntryTV.setText("Entry Fee ₹" + decfor.format(Float.parseFloat(ludoContestBean.getEntryFee().toString()) / 100));
            holder.mPointsTv.setText("Points Value");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString()) / 80) / 100));
        } else if (mMode == 2) {
            holder.mFeeEntryTV.setVisibility(View.INVISIBLE);
            holder.mPointsTv.setText("Entry Fee");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString())) / 100));
//            holder.mEntryFeeTV.setText("" + ludoContestBean.getEntryFee() + " Pool");
        } else if (mMode == 3) {
            holder.mFeeEntryTV.setVisibility(View.INVISIBLE);
            holder.mPointsTv.setText("Entry Fee");
            holder.mEntryFeeTV.setText("₹" + decfor.format((Float.parseFloat(ludoContestBean.getEntryFee().toString())) / 100));
        }
        if (ludoContestBean.getmPlayersDetails().size() == 2) {
            holder.mPlayerTV.setVisibility(View.VISIBLE);
            holder.mPlayersMoreTv.setVisibility(View.VISIBLE);
            holder.mWinningAmountTV.setText("₹ " + AppUtilityMethods.roundUpNumber(ludoContestBean.getmPlayersDetails().get(1).getMaxWin()));
            holder.mWinningAmountTV.setText("₹ " + decfor.format(Float.parseFloat(ludoContestBean.getmPlayersDetails().get(1).getMaxWin().toString()) / 100));
        } else {
            holder.mWinningAmountTV.setText("₹ " + AppUtilityMethods.roundUpNumber(ludoContestBean.getmPlayersDetails().get(0).getMaxWin()));
            holder.mWinningAmountTV.setText("₹ " + decfor.format(Float.parseFloat(ludoContestBean.getmPlayersDetails().get(0).getMaxWin().toString()) / 100));
            holder.mPlayerTV.setVisibility(View.VISIBLE);
            holder.mPlayersMoreTv.setVisibility(View.GONE);
        }
        holder.mOnlineTV.setText("" + ludoContestBean.getOnline());
        if (ludoContestBean.getBonus() != null)
            holder.mBonusTV.setText("Use " + ludoContestBean.getBonus() + "% Bonus");
        else
            holder.mBonusTV.setVisibility(View.GONE);

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
        @BindView(R.id.tv_points)
        TextView mPointsTv;
        @BindView(R.id.tv_fee_entry)
        TextView mFeeEntryTV;
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