package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.gameleague.TrendingTournamentActivity;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.response.droid_doresponse.GameList;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class GamesMainAdapter extends RecyclerView.Adapter<GamesMainAdapter.ViewHolder> {
    private List<GameList> itemGamesMainList;
    private IOnGamesClickListener mIOnGamesClickListener;
    private Context mContext;

    public GamesMainAdapter(List<GameList> mAllGamesList, IOnGamesClickListener mIOnGamesClickListener, Context mContext) {
        this.itemGamesMainList = mAllGamesList;
        this.mIOnGamesClickListener = mIOnGamesClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mainGamesData = layoutInflater.inflate(R.layout.item_recyclerview_games_main, parent, false);
        return new ViewHolder(mainGamesData, mIOnGamesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameList gifGamesItem = itemGamesMainList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(gifGamesItem.getImage())
                .into(holder.gifImageView);
        holder.materialCardView.setOnClickListener(view -> {
            if (mIOnGamesClickListener != null) {
                mIOnGamesClickListener.onItemClick(position);
            }
            Intent intent = new Intent(mContext, TrendingTournamentActivity.class);
            intent.putExtra("id", gifGamesItem.id);
            intent.putExtra("name", gifGamesItem.name);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemGamesMainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MaterialCardView materialCardView;
        public ProgressBar mProgressbar;
        public GifImageView gifImageView;

        public ViewHolder(@NonNull View itemView, IOnGamesClickListener iOnGamesClickListener) {
            super(itemView);
            materialCardView = itemView.findViewById(R.id.materialCardView);
            mProgressbar = itemView.findViewById(R.id.pb_onGamesGif);
            gifImageView = itemView.findViewById(R.id.iv_games_collection);
            itemView.setOnClickListener(view -> iOnGamesClickListener.onItemClick(getBindingAdapterPosition()));
        }
    }
}
