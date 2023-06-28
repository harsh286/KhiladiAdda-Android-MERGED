package com.khiladiadda.transaction.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.TransactionDetails;
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.EventHolder> {

    private List<TransactionDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public TransactionAdapter(List<TransactionDetails> exhibitorList) {
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transact, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }
    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        TransactionDetails details = mFDList.get(position);
        final DecimalFormat decfor = new DecimalFormat("0.00");
        holder.mAmountTV.setText("₹" + details.getAmount());
        holder.mWalletTV.setText("Deposit: " + decfor.format(details.getWallet().getDeposit()) + ", Winning: " + decfor.format(details.getWallet().getWinning()) + ", Bonus: " + decfor.format(details.getWallet().getBonus()));
        if (!TextUtils.isEmpty(details.getCreatedAt())) {
            holder.mDateTV.setText(AppUtilityMethods.getConvertDateParkIn(details.getCreatedAt()));
        }
        if (details.isIsDeducted()) {
            holder.mTypeTV.setText("DEBITED");
            holder.mTypeTV.setTextColor(ContextCompat.getColor(holder.mTypeTV.getContext(), R.color.colorPrimary));
        } else {
            holder.mTypeTV.setText("CREDITED");
            holder.mTypeTV.setTextColor(ContextCompat.getColor(holder.mTypeTV.getContext(), R.color.color_green));
        }
        if (!TextUtils.isEmpty(details.getMessage())) {
            holder.mMessageTV.setText(details.getMessage());
        } else {
            holder.mMessageTV.setVisibility(View.GONE);
        }
        if (details.isIsDeducted() && details.isGst()) {
            holder.mInvoiceBTN.setVisibility(View.VISIBLE);
        } else {
            holder.mInvoiceBTN.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_type)
        TextView mTypeTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.tv_wallet)
        TextView mWalletTV;
        @BindView(R.id.tv_date)
        TextView mDateTV;
        @BindView(R.id.tv_message)
        TextView mMessageTV;
        @BindView(R.id.btn_invoice)
        TextView mInvoiceBTN;

        private final IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener,IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            mInvoiceBTN.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (v.getId() == R.id.btn_invoice) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onInvoiceClicked(position);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onInvoiceClicked(int position);
    }

}