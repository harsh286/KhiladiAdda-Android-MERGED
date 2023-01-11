package com.khiladiadda.scratchcard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.ScratchCardResponseDettails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScratchCardAdapter extends RecyclerView.Adapter<ScratchCardAdapter.ViewHolder> {
    private final List<ScratchCardResponseDettails> mCardsList;
    private IOnItemClickListener mOnItemClickListener;

    public ScratchCardAdapter(List<ScratchCardResponseDettails> mCardsList) {
        this.mCardsList = mCardsList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scratch, parent, false);
        return new ViewHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScratchCardResponseDettails response = mCardsList.get(position);
        if (response.getIsUsed() == 0) {
            holder.mUnScratchIV.setVisibility(View.VISIBLE);
            holder.mScratchedIV.setVisibility(View.GONE);
            holder.mAmountTV.setVisibility(View.GONE);
        } else {
            holder.mUnScratchIV.setVisibility(View.GONE);
            holder.mScratchedIV.setVisibility(View.VISIBLE);
            holder.mAmountTV.setVisibility(View.VISIBLE);
            holder.mAmountTV.setText("Won\n" + String.format("%.2f", response.getAmount()) +"\nCoins");
        }
    }

    @Override
    public int getItemCount() {
        return mCardsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IOnItemClickListener mOnItemClickListener;
        @BindView(R.id.iv_unscratch)
        ImageView mUnScratchIV;
        @BindView(R.id.iv_scracthed)
        ImageView mScratchedIV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;

        public ViewHolder(@NonNull View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}