package com.khiladiadda.rummy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.RummyDetails;
import com.khiladiadda.network.model.response.RummyHistoryResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RummyHistoryAdapter extends RecyclerView.Adapter<RummyHistoryAdapter.LudoContestHolder> {

    private Context mContext;
    private List<RummyHistoryResponse> mRummyHistoryResponseList;
    private IOnItemClickListener mOnItemClickListener;

    public RummyHistoryAdapter(Context context, List<RummyHistoryResponse> mRummyHistoryResponseList, IOnItemClickListener mOnItemClickListener) {
        this.mContext = context;
        this.mRummyHistoryResponseList = mRummyHistoryResponseList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public RummyHistoryAdapter.LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_rummy_history, parent, false);
        return new RummyHistoryAdapter.LudoContestHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RummyHistoryAdapter.LudoContestHolder holder, int position) {
        RummyHistoryResponse item = mRummyHistoryResponseList.get(position);
        holder.mDateTv.setText(AppUtilityMethods.getConvertDateParkIn(item.getCreatedAt()));
        holder.mTransactionId.setText(item.getTxnID());
        holder.mPriceTv.setText("Entry ₹"+item.getAmount());
        if (item.getWinningAmount() >= 0){
            holder.mWinningPriceTv.setText("Won ₹"+String.format("%.2f", Double.parseDouble(item.getWinningAmount().toString().replaceAll("[+-]", ""))));
            holder.mWinningPriceTv.setTextColor(Color.parseColor("#00910E"));
            holder.mDetailsCl.setVisibility(View.VISIBLE);
        }else {
            holder.mWinningPriceTv.setText("Lose ₹"+String.format("%.2f", Double.parseDouble(item.getWinningAmount().toString().replaceAll("[+-]", ""))));
            holder.mWinningPriceTv.setTextColor(Color.parseColor("#CA2236"));
            holder.mArrowIv.setImageResource(R.drawable.up_arrow_show);
            holder.mWinningPriceTv.setVisibility(View.GONE);
            holder.mDetailsCl.setVisibility(View.GONE);

        }
//        holder.mWinningPriceTv.setText("Entry ₹"+item.getWinningAmount());
        holder.mHistoryMcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mDetailsCl.isShown()){
                    holder.mDetailsCl.setVisibility(View.GONE);
                    holder.mArrowIv.setImageResource(R.drawable.up_arrow_show);
                    if (item.getWinningAmount() < 0){
                        holder.mWinningPriceTv.setVisibility(View.GONE);
                    }else
                        holder.mWinningPriceTv.setVisibility(View.VISIBLE);
                }else {
                    holder.mDetailsCl.setVisibility(View.VISIBLE);
                    holder.mArrowIv.setImageResource(R.drawable.down_arrow_hide);
                    if (item.getWinningAmount() < 0){
                        holder.mWinningPriceTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRummyHistoryResponseList.size();
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.mcv_history)
        MaterialCardView mHistoryMcv;
        @BindView(R.id.tv_wining_amount)
        TextView mWiningAmountTv;
        @BindView(R.id.cl_details)
        ConstraintLayout mDetailsCl;
        @BindView(R.id.tv_date)
        TextView mDateTv;
        @BindView(R.id.tv_transaction_id)
        TextView mTransactionId;
        @BindView(R.id.tv_winningprice)
        TextView mWinningPriceTv;
        @BindView(R.id.tv_price)
        TextView mPriceTv;
        @BindView(R.id.iv_arrow)
        ImageView mArrowIv;

        private IOnItemClickListener mOnItemClickListener;

        public LudoContestHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
//            view.setOnClickListener(this);
            mWiningAmountTv.setOnClickListener(this);
//            mHistoryMcv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}