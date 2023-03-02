package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.khiladiadda.gameleague.GamesLeaderBoardActivity;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.response.droid_doresponse.GameHistoryDroido;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

public class GameDroidoHistoryAdapter extends RecyclerView.Adapter<GameDroidoHistoryAdapter.ViewHolder> {
    private List<GameHistoryDroido> historyResponseData;
    private IOnGamesClickListener mIOnGamesClickListener;
    private Context mContext;

    public GameDroidoHistoryAdapter(List<GameHistoryDroido> historyResponseData, IOnGamesClickListener mIOnGamesClickListener, Context mContext) {
        this.historyResponseData = historyResponseData;
        this.mIOnGamesClickListener = mIOnGamesClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mainGamesData = layoutInflater.inflate(R.layout.item_rv_more_tournament, parent, false);
        return new ViewHolder(mainGamesData);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameHistoryDroido gameList = historyResponseData.get(position);
        if (gameList.getImg() != null)
            Glide.with(mContext).load(gameList.getImg()).placeholder(R.drawable.droido_defautl).into(holder.imageView);
        holder.mPLayBTN.setOnClickListener(view -> doIntent(gameList));

        if (gameList.getTournamentStatus() == 0) {
            holder.mPLayBTN.setText("Live Leaderboard");
        } else if (gameList.getTournamentStatus() == 2) {
            holder.mPLayBTN.setClickable(false);
            holder.mPLayBTN.setText("Cancelled");
        } else {
            holder.mPLayBTN.setText("View Result");
        }
        holder.tvWinPrize.setText("Won: \u20B9" + gameList.getWinningAmount().toString());
        if (gameList.getWinningAmount()!=0){
            holder.tvWinPrize.setVisibility(View.VISIBLE);
            holder.tvWinPrize.setText("Won: \u20B9" + gameList.getWinningAmount().toString());
        }else {
            holder.tvWinPrize.setVisibility(View.INVISIBLE);
        }
        holder.tvEntryFee.setText("Entry Fee: \u20B9" + gameList.getEntryFees().toString());
        holder.acbTotalParticipants.setVisibility(View.GONE);
        holder.tvGameName.setVisibility(View.VISIBLE);
        holder.tvGameName.setText(gameList.getTournamentsName());
    }

    private void doIntent(GameHistoryDroido gameList) {
        Intent intent = new Intent(mContext, GamesLeaderBoardActivity.class);
        intent.putExtra("tournament_id", gameList.getTournamentId());
        intent.putExtra("tournamentStatus", gameList.getTournamentStatus());
        intent.putExtra(AppConstant.FROM, false); // true for splash and false for history
        intent.putExtra(AppConstant.FROM, GameDroidoHistoryAdapter.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return historyResponseData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGameName;
        public TextView tvViewResult;
        ImageView imageView;
        AppCompatButton mPLayBTN;
        TextView tvWinPrize;
        TextView tvEntryFee;
        public AppCompatButton acbTotalParticipants;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGameName = itemView.findViewById(R.id.tv_name);
            acbTotalParticipants = itemView.findViewById(R.id.acb_total_participants);
            imageView = itemView.findViewById(R.id.profile_image);
            mPLayBTN = itemView.findViewById(R.id.btn_trnd_play);
            tvWinPrize = itemView.findViewById(R.id.tv_trend_win_prize);
            tvEntryFee = itemView.findViewById(R.id.tv_trend_entry_fee);
        }
    }

}
