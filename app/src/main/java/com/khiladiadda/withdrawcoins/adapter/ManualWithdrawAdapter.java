package com.khiladiadda.withdrawcoins.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.ManualWithdrawDetails;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManualWithdrawAdapter extends RecyclerView.Adapter<ManualWithdrawAdapter.EventHolder> {

    private Context mContext;
    private List<ManualWithdrawDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public ManualWithdrawAdapter(Context context, List<ManualWithdrawDetails> withdrawList) {
        this.mContext = context;
        this.mFDList = withdrawList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manual_withdraw, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        ManualWithdrawDetails details = mFDList.get(position);
        holder.mNameTV.setText((position + 1) + ".  " + AppUtilityMethods.getConvertDateQuiz(details.getCreatedAt()) + "      Request No: " + details.getRequestNo());
        holder.mAddressTV.setText(details.getPaymentAddress());
        holder.mModeTV.setText(String.valueOf(details.getPaymentMode()));
        holder.mCoinsTV.setText("Coins: " + String.valueOf(details.getWithdrawCoins()));
        if (details.getStatus() == 0) {
            holder.mProcessingTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
            holder.mApproveTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
        } else if (details.getStatus() == 1) {
            holder.mPendingTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
            holder.mApproveTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
        } else if (details.getStatus() == 2) {
            holder.mProcessingTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
            holder.mPendingTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
        } else if (details.getStatus() == -1) {
            holder.mPendingTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
            holder.mApproveTV.setTextColor(ContextCompat.getColor(mContext, R.color.lighter_gray));
            holder.mProcessingTV.setText("Rejected");
            holder.mProcessingTV.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_mode) TextView mModeTV;
        @BindView(R.id.tv_address) TextView mAddressTV;
        @BindView(R.id.tv_coins) TextView mCoinsTV;
        @BindView(R.id.tv_pending) TextView mPendingTV;
        @BindView(R.id.tv_processing) TextView mProcessingTV;
        @BindView(R.id.tv_approve) TextView mApproveTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }
}