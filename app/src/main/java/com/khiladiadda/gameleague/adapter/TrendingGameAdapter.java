package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.gameleague.TournamenetDetailActivity;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentTrendingList;

import java.util.List;

public class TrendingGameAdapter extends RecyclerView.Adapter<TrendingGameAdapter.ViewHolder> {
    private Context mContext;
    private IOnGamesClickListener iOnGamesClickListener;
    private List<TournamentTrendingList> tournamentTrendingList;
    private long lastTImeClicked = 0;
    private int defaultInterval = 0;

    public TrendingGameAdapter(Context mContext, IOnGamesClickListener iOnGamesClickListener, List<TournamentTrendingList> tournamentTrendingList) {
        this.mContext = mContext;
        this.tournamentTrendingList = tournamentTrendingList;
        this.iOnGamesClickListener = iOnGamesClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View trendingData = layoutInflater.inflate(R.layout.item_rv_game_trending, parent, false);
        return new ViewHolder(trendingData, iOnGamesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TournamentTrendingList tournamentTrendingList = this.tournamentTrendingList.get(position);
        Glide.with(mContext)
                .load(tournamentTrendingList.image)
                .placeholder(R.drawable.droido_defautl)
                .into(holder.imgProfile);
        holder.tvEntryFee.setText("Entry Fee: \u20B9 " + tournamentTrendingList.getEntryFees());
        holder.tvWinPrize.setText("Prizepool: \u20B9 " + tournamentTrendingList.getWinPrize());
        holder.acbTotalParticipants.setText(tournamentTrendingList.getnAttempts() + "/" + 3);
        holder.itemView.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - lastTImeClicked < defaultInterval) {
                return;
            }
            lastTImeClicked = SystemClock.elapsedRealtime();
            Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
            intent.putExtra("id", tournamentTrendingList.getId());
            intent.putExtra("name", tournamentTrendingList.name);
            intent.putExtra("image", tournamentTrendingList.image);
            intent.putExtra("entryFee", tournamentTrendingList.getEntryFees().toString());
            intent.putExtra("winPrize", tournamentTrendingList.getWinPrize().toString());
            intent.putExtra("nAttempts", String.valueOf(tournamentTrendingList.getnAttempts()));
            intent.putExtra("totalparticipants", String.valueOf(tournamentTrendingList.totalParticipants));
            intent.putExtra("playedparticipants", String.valueOf(tournamentTrendingList.playedparticipants));
            intent.putExtra("prizepool", tournamentTrendingList.getPrizePools());
            mContext.startActivity(intent);
        });
        holder.acbPlay.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - lastTImeClicked < defaultInterval) {
                return;
            }
            lastTImeClicked = SystemClock.elapsedRealtime();
            Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
            intent.putExtra("id", tournamentTrendingList.getId());
            intent.putExtra("name", tournamentTrendingList.name);
            intent.putExtra("image", tournamentTrendingList.image);
            intent.putExtra("entryFee", tournamentTrendingList.getEntryFees().toString());
            intent.putExtra("winPrize", tournamentTrendingList.getWinPrize().toString());
            intent.putExtra("nAttempts", String.valueOf(tournamentTrendingList.getnAttempts()));
            intent.putExtra("totalparticipants", String.valueOf(tournamentTrendingList.totalParticipants));
            intent.putExtra("playedparticipants", String.valueOf(tournamentTrendingList.playedparticipants));
            intent.putExtra("prizepool", tournamentTrendingList.getPrizePools());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tournamentTrendingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView circularImageView;
        public AppCompatButton acbTotalParticipants;
        public TextView tvWinPrize;
        public TextView tvEntryFee;
        public AppCompatButton acbPlay;
        public ImageView imgProfile;

        public ViewHolder(@NonNull View itemView, IOnGamesClickListener iOnGamesClickListener) {
            super(itemView);
            acbTotalParticipants = itemView.findViewById(R.id.acb_total_participants);
            tvWinPrize = itemView.findViewById(R.id.tv_trend_win_prize_trending);
            tvEntryFee = itemView.findViewById(R.id.tv_trend_entry_fee_trending);
            acbPlay = itemView.findViewById(R.id.btn_trnd_play);
            circularImageView = itemView.findViewById(R.id.image_tournament);
            imgProfile = itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(view -> iOnGamesClickListener.onItemClick(getBindingAdapterPosition()));
        }
    }
}
