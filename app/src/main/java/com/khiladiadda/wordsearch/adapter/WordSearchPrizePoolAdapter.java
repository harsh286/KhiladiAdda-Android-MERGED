package com.khiladiadda.wordsearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.PrizePoolBreakthrough;
import com.khiladiadda.wordsearch.listener.IOnSubClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchPrizePoolAdapter extends RecyclerView.Adapter<WordSearchPrizePoolAdapter.ViewHolder> {
    private final IOnSubClickListener mIOnClickListener;
    private boolean showAll;
    private final List<PrizePoolBreakthrough> mPrizePoolBreakthroughList;


    public WordSearchPrizePoolAdapter(IOnSubClickListener mIOnClickListener, boolean showAll, List<PrizePoolBreakthrough> mPrizePoolBreakthroughList) {
        this.mIOnClickListener = mIOnClickListener;
        this.mPrizePoolBreakthroughList = mPrizePoolBreakthroughList;
        this.showAll = showAll;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_word_search_prize_pool, parent, false);
        return new ViewHolder(view, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrizePoolBreakthrough prizePoolBreakthrough = mPrizePoolBreakthroughList.get(position);
        holder.mStandingTV.setText("" + prizePoolBreakthrough.getFrom() + "-" + prizePoolBreakthrough.getTo());
        holder.mPrizeTV.setText("" + prizePoolBreakthrough.getPrizeMoney());
    }

    @Override
    public int getItemCount() {
        if (mPrizePoolBreakthroughList.size() > 3) {
            if (showAll)
                return mPrizePoolBreakthroughList.size();
            return 3;
        }
        return mPrizePoolBreakthroughList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IOnSubClickListener mOnItemClickListener;
        @BindView(R.id.tv_standing)
        TextView mStandingTV;
        @BindView(R.id.tv_prize)
        TextView mPrizeTV;
        public ViewHolder(View view, IOnSubClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onSubItemClick(position);
            }
        }
    }
}
