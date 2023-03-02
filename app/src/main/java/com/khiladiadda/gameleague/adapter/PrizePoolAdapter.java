package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.droid_doresponse.PrizePool;

import java.util.ArrayList;

public class PrizePoolAdapter extends RecyclerView.Adapter<PrizePoolAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PrizePool> itemPrizePoolList;

    public PrizePoolAdapter(Context context, ArrayList<PrizePool> itemPrizePoolList) {
        this.context = context;
        this.itemPrizePoolList = itemPrizePoolList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View prizePool = layoutInflater.inflate(R.layout.item_rv_prize_pool, parent, false);
        ViewHolder viewHolder = new ViewHolder(prizePool);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PrizePool itemPrizePool = itemPrizePoolList.get(position);
        holder.tvRank.setText("" + itemPrizePool.getFromRank() + "-" + itemPrizePool.getToRank());
        holder.tvPrize.setText(" \u20B9 " + itemPrizePool.getPrizeMoney());
    }

    @Override
    public int getItemCount() {
        return itemPrizePoolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRank;
        public TextView tvPrize;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tv_rank_prize_pool);
            tvPrize = itemView.findViewById(R.id.tv_prize_prize_pool);
        }
    }
}
