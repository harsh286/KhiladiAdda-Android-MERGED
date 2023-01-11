package com.khiladiadda.withdrawcoins.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class NewBeneficiaryAdapter extends RecyclerView.Adapter<NewBeneficiaryAdapter.ViewHolder> {

    private final List<BeneficiaryDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context mContext;

    public NewBeneficiaryAdapter(Context context, List<BeneficiaryDetails> beneficiaryList) {
        this.mContext = context;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item_beneficiary, parent, false);
        return new ViewHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeneficiaryDetails details = mFDList.get(position);
        if (details.getTransferMode().equalsIgnoreCase(AppConstant.BANK_TRANSFER) || details.getTransferMode().equalsIgnoreCase(AppConstant.BANK_ACCOUNT)) {
            holder.mNumberTV.setText(details.getBankAccount() + "\n" + details.getIfsc());
            holder.mModeTV.setBackgroundResource(R.drawable.ic_bank);
        } else if (details.getTransferType() == 1) { //paytm wallet
            holder.mModeTV.setBackgroundResource(R.drawable.ic_paytm);
            String s = details.getVPA();
            String[] arrayString = s.split("\\.");
            String number = arrayString[0];
            String title = arrayString[1];
            holder.mNumberTV.setText(number);
        } else if (details.getTransferType() == 2) { //paytm upi
            holder.mNumberTV.setText(details.getVPA());
            holder.mModeTV.setBackgroundResource(R.drawable.ic_paytm_upi);
        } else if (details.getTransferMode().equalsIgnoreCase(AppConstant.UPI) || details.getTransferMode().equalsIgnoreCase(AppConstant.VPA)) {
            holder.mModeTV.setBackgroundResource(R.drawable.ic_upi);
            holder.mNumberTV.setText(details.getVPA());
        }
        holder.layout.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_mode)
        ImageView mModeTV;
        @BindView(R.id.tv_number)
        TextView mNumberTV;
        @BindView(R.id.iv_delete)
        ImageButton mDeleteIV;
        @BindView(R.id.rl_layout)
        RelativeLayout layout;
        @BindView(R.id.tv_status)
        TextView mStatusTV;
        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public ViewHolder(@NonNull View itemView, IOnItemClickListener mOnItemClickListener, IOnItemChildClickListener mOnItemChildClickListener) {
            super(itemView);
            this.mOnItemClickListener = mOnItemClickListener;
            this.mOnItemChildClickListener = mOnItemChildClickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mDeleteIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (v.getId() == R.id.iv_delete) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onDeleteClicked(position);
                }
            }
            else {
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