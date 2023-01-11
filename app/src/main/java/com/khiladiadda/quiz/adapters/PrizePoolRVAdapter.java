package com.khiladiadda.quiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrizePoolRVAdapter extends RecyclerView.Adapter<PrizePoolRVAdapter.EventHolder> {

    private List<PrizePoolBreakthrough> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public PrizePoolRVAdapter(List<PrizePoolBreakthrough> exhibitorList) {
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prize_pool, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        PrizePoolBreakthrough details = mFDList.get(position);
        holder.mFromTV.setText(details.getFrom() + "-" + details.getTo());
        holder.mAmountTV.setText(String.valueOf(details.getPrizeMoney()));
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_from) TextView mFromTV;
        @BindView(R.id.tv_amount) TextView mAmountTV;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_wishlist:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onWishlistClicked(getBindingAdapterPosition());
                    }
                    break;
                case R.id.iv_bookmark:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onBookmarkClicked(getBindingAdapterPosition());
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onWishlistClicked(int position);
        void onBookmarkClicked(int position);
    }

}