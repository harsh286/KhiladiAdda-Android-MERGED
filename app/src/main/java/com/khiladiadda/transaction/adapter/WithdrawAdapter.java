package com.khiladiadda.transaction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.network.model.response.WIthdrawDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.EventHolder> {

    private List<WIthdrawDetails> mList;
    private IOnItemClickListener mOnItemClickListener;

    public WithdrawAdapter(List<WIthdrawDetails> exhibitorList) {
        this.mList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_withdraw, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        WIthdrawDetails details = mList.get(position);
        holder.mNameTV.setText((position + 1) + ". Transfer Id: " + details.getRequestNo());
        holder.mAddressTV.setText(AppUtilityMethods.getConvertDateQuiz(details.getCreatedAt()));
        holder.mCoinsTV.setText("Coins: " + String.valueOf(details.getWithdrawAmount()));

        if (details.getPaymentMode().equalsIgnoreCase(AppConstant.PATYM) || details.getTransferAddress().contains(AppConstant.PATYMWALLTERUPI)) {
            if (details.getTransferAddress() != null && details.getTransferAddress().contains(AppConstant.PATYMWALLTERUPI)) {
                String s = details.getTransferAddress();
                String[] arrayString = s.split("\\.");
                String number = arrayString[0];
                String title = arrayString[1];
                holder.mModeTV.setText("Paytm - " + number);
            }
            else{
                holder.mModeTV.setText(String.valueOf(details.getPaymentMode()).toUpperCase() + " - " +details.getTransferAddress());
            }
        }else{
            holder.mModeTV.setText(String.valueOf(details.getPaymentMode()).toUpperCase() + " - " +details.getTransferAddress());
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;
        @BindView(R.id.tv_address)
        TextView mAddressTV;
        @BindView(R.id.tv_coins)
        TextView mCoinsTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
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