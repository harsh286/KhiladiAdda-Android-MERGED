package com.khiladiadda.transaction.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.PaymentHistoryDetails;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.EventHolder> {

    private List<PaymentHistoryDetails> mFDList;
    private Context mContext;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public PaymentAdapter(Context context, List<PaymentHistoryDetails> exhibitorList) {
        this.mContext = context;
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transact, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        PaymentHistoryDetails details = mFDList.get(position);
        if (!TextUtils.isEmpty(details.getAmount())) {
            holder.mAmountTV.setText(details.getAmount());
        } else {
            holder.mAmountTV.setText(R.string.text_zero);
        }
        holder.mWalletTV.setText("OrderId: " + details.getOrderId());
        if (!TextUtils.isEmpty(details.getCreatedAt())) {
            holder.mDateTV.setText("Transaction Date: " + AppUtilityMethods.getConvertDateParkIn(details.getCreatedAt()));
        }
        if (!TextUtils.isEmpty(details.getStatus())) {
            holder.mTypeTV.setVisibility(View.VISIBLE);
            if (details.getStatus().equalsIgnoreCase("PROCESSING")) {
                holder.mTypeTV.setText(details.getStatus());
                holder.mStatusTV.setVisibility(View.GONE);
                holder.mTypeTV.setTextColor(mContext.getResources().getColor(R.color.colorLivePlayed));
                holder.mTypeTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info, 0);
            } else if (details.getStatus().equalsIgnoreCase("FAILURE")) {
                holder.mTypeTV.setText(details.getStatus());
                holder.mStatusTV.setVisibility(View.GONE);
                holder.mTypeTV.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.mTypeTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else if (details.getStatus().equalsIgnoreCase("SUCCESS")) {
                holder.mTypeTV.setText(details.getStatus());
                holder.mStatusTV.setVisibility(View.GONE);
                holder.mTypeTV.setTextColor(mContext.getResources().getColor(R.color.color_green));
                holder.mTypeTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }else {
                holder.mTypeTV.setText(details.getStatus());
                holder.mStatusTV.setVisibility(View.VISIBLE);
                holder.mTypeTV.setTextColor(mContext.getResources().getColor(R.color.orange_dark));
                holder.mTypeTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }else{
            holder.mTypeTV.setVisibility(View.GONE);
            holder.mStatusTV.setVisibility(View.GONE);
        }
        holder.mMessageTV.setText(details.getType().toUpperCase());
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_type) TextView mTypeTV;
        @BindView(R.id.tv_amount) TextView mAmountTV;
        @BindView(R.id.tv_wallet) TextView mWalletTV;
        @BindView(R.id.tv_date) TextView mDateTV;
        @BindView(R.id.tv_message) TextView mMessageTV;
        @BindView(R.id.tv_status) TextView mStatusTV;
        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            mStatusTV.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (v.getId() == R.id.tv_status) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onStatusClicked(position);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position, 0);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onStatusClicked(int position);
    }

}