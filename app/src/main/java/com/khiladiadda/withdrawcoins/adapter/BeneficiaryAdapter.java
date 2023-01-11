package com.khiladiadda.withdrawcoins.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.BeneficiaryDetails;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.EventHolder> {

    private List<BeneficiaryDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;

    public BeneficiaryAdapter(List<BeneficiaryDetails> beneficiaryList) {
        this.mFDList = beneficiaryList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beneficiary, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        BeneficiaryDetails details = mFDList.get(position);
        if (details.getTransferMode().equalsIgnoreCase(AppConstant.BANK_TRANSFER) || details.getTransferMode().equalsIgnoreCase(AppConstant.BANK_ACCOUNT)) {
            holder.mModeTV.setText(String.valueOf(details.getTransferMode().toUpperCase()));
            holder.mNumberTV.setText(details.getBankAccount() + "\n" + details.getIfsc());
        } else if (details.getTransferMode().equalsIgnoreCase(AppConstant.UPI) || details.getTransferMode().equalsIgnoreCase(AppConstant.VPA)) {
            holder.mNumberTV.setText("" + details.getVPA());
            holder.mModeTV.setText(String.valueOf(details.getTransferMode().toUpperCase()));
        } else {
            holder.mNumberTV.setText(details.getPhone());
            holder.mModeTV.setText(String.valueOf(details.getTransferMode().toUpperCase()));
        }
        holder.mPaymentRL.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ll_payment)
        RelativeLayout mPaymentRL;
        @BindView(R.id.tv_mode)
        TextView mModeTV;
        @BindView(R.id.tv_number)
        TextView mNumberTV;
        @BindView(R.id.iv_delete)
        ImageView mDeleteIV;
        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mDeleteIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (v.getId() == R.id.iv_delete) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onDeleteClicked(position);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position, 0);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onDeleteClicked(int position);
    }

}