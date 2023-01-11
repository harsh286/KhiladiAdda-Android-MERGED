package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.gameleague.GamesLeaderBoardActivity;
import com.khiladiadda.gameleague.TournamenetDetailActivity;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseDataMyTournament;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTournamentGameAdapter extends RecyclerView.Adapter<MyTournamentGameAdapter.ViewHolder> {
    private Context mContext;
    private IOnGamesClickListener iOnGamesClickListener;
    private List<ResponseDataMyTournament> gameMoreTournamentList;
    private long lastItemClicked = 0;
    private int defaultInterval = 0;

    public MyTournamentGameAdapter(Context mContext, IOnGamesClickListener iOnGamesClickListener, List<ResponseDataMyTournament> tournamentTrendingListList) {
        this.mContext = mContext;
        this.gameMoreTournamentList = tournamentTrendingListList;
        this.iOnGamesClickListener = iOnGamesClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View quizHeaderData = layoutInflater.inflate(R.layout.new_item_rv_my_tournament, parent, false);
        return new ViewHolder(quizHeaderData);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResponseDataMyTournament tournamentTrendingData = gameMoreTournamentList.get(position);
        holder.tvWinPrize.setText(mContext.getString(R.string.Win) + " " + tournamentTrendingData.getWinPrize().toString());
        holder.tvEntryFee.setText(mContext.getString(R.string.text_entry_fee_droido) + tournamentTrendingData.getEntryFees());
        holder.tvFiltersTournament.setText(tournamentTrendingData.getTournaments_name());
        holder.acbTotalParticipants.setText(mContext.getString(R.string.attempts) + tournamentTrendingData.getN_attempts() + "/" + 3);
        holder.tvParticipants.setText(tournamentTrendingData.playedparticipants + "/" + tournamentTrendingData.totalparticipants);
        holder.pbDroido.setProgress(tournamentTrendingData.playedparticipants);
        holder.pbDroido.setMax(tournamentTrendingData.totalparticipants);//sets the maximum value 100
        Glide.with(mContext)
                .load(tournamentTrendingData.getTournaments_image())
                .placeholder(R.drawable.droido_defautl)
                .into(holder.imgProfile);
        if (tournamentTrendingData.getTournament_status() == 0) {
            holder.acbPlay.setVisibility(View.VISIBLE);
            holder.acbPlay.setText(mContext.getString(R.string.play));
            holder.tvEnded.setText(mContext.getString(R.string.attempts) + tournamentTrendingData.getN_attempts() + "/" + 3);
            if (tournamentTrendingData.getTournament_status() == 0 && tournamentTrendingData.getN_attempts() == 3) {
                holder.acbPlay.setVisibility(View.VISIBLE);
                holder.acbPlay.setText(mContext.getString(R.string.view));
                holder.tvEnded.setText(mContext.getString(R.string.attempts) + tournamentTrendingData.getN_attempts() + "/" + 3);
            }
        } else if (tournamentTrendingData.getTournament_status() == 1) {
            holder.acbPlay.setVisibility(View.VISIBLE);
            holder.acbPlay.setText(mContext.getString(R.string.view));
            holder.tvEnded.setText(mContext.getString(R.string.completed));
            if (tournamentTrendingData.getWinPrize() != 0) {
                holder.tvEnded.setText(mContext.getString(R.string.won) + ":  ₹ " + tournamentTrendingData.getWinPrize());
                holder.itemView.setClickable(false);
                holder.itemView.setEnabled(false);
            }
        } else {
            holder.acbPlay.setVisibility(View.GONE);
            holder.tvEnded.setText(mContext.getString(R.string.cancelled));
            holder.itemView.setClickable(false);
            holder.itemView.setEnabled(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.acbPlay.getText().equals(mContext.getString(R.string.play))) {
                    Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
                    intent.putExtra(AppConstant.DROIDO_NAME, tournamentTrendingData.getTournaments_name());
                    intent.putExtra(AppConstant.DROIDO_IMAGE, tournamentTrendingData.getTournaments_image());
                    intent.putExtra(AppConstant.DROIDO_ENTRY_FEE, tournamentTrendingData.getEntryFees().toString());
                    intent.putExtra(AppConstant.DROIDO_WIN_PRIZE, tournamentTrendingData.getWinPrize().toString());
                    intent.putExtra(AppConstant.DROIDO_N_ATTEMPTS, String.valueOf(tournamentTrendingData.getN_attempts()));
                    intent.putExtra(AppConstant.DROIDO_TOTAL_PARTICIPANTS, String.valueOf(tournamentTrendingData.totalparticipants));
                    intent.putExtra(AppConstant.DROIDO_PLAYED_PARTICIPANTS, String.valueOf(tournamentTrendingData.playedparticipants));
                    intent.putExtra("id", tournamentTrendingData.getTournament_id());
                    intent.putExtra(AppConstant.DROIDO_ENDS_IN, tournamentTrendingData.endIn);
                    intent.putExtra(AppConstant.DROIDO_PRIZEPOOL, tournamentTrendingData.getPrizePools());
///                    intent.putExtra("tournament_id", tournamentTrendingData.getTournament_id());
                    mContext.startActivity(intent);
                } else {
                    if (tournamentTrendingData.getN_attempts() != 3) {
                        doIntent(tournamentTrendingData);
                    }
                }
            }
        });
        holder.acbPlay.setOnClickListener(view -> {
            if (holder.acbPlay.getText().equals(mContext.getString(R.string.view))) {
                Intent intent = new Intent(mContext, GamesLeaderBoardActivity.class);
                intent.putExtra("tournament_id", tournamentTrendingData.getTournament_id());
                mContext.startActivity(intent);
            } else {
                if (tournamentTrendingData.getN_attempts() != 3) {
                    doIntent(tournamentTrendingData);
                }
            }
        });
    }

    private void doIntent(ResponseDataMyTournament tournamentTrendingList) {
        Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
        intent.putExtra(AppConstant.DROIDO_NAME, tournamentTrendingList.getTournaments_name());
        intent.putExtra(AppConstant.DROIDO_IMAGE, tournamentTrendingList.getTournaments_image());
        intent.putExtra(AppConstant.DROIDO_ENTRY_FEE, tournamentTrendingList.getEntryFees().toString());
        intent.putExtra(AppConstant.DROIDO_WIN_PRIZE, tournamentTrendingList.getWinPrize().toString());
        intent.putExtra(AppConstant.DROIDO_N_ATTEMPTS, String.valueOf(tournamentTrendingList.getN_attempts()));
        intent.putExtra(AppConstant.DROIDO_TOTAL_PARTICIPANTS, String.valueOf(tournamentTrendingList.totalparticipants));
        intent.putExtra(AppConstant.DROIDO_PLAYED_PARTICIPANTS, String.valueOf(tournamentTrendingList.playedparticipants));
        intent.putExtra("id", tournamentTrendingList.getTournament_id());
        intent.putExtra(AppConstant.DROIDO_ENDS_IN, tournamentTrendingList.endIn);
        intent.putExtra(AppConstant.DROIDO_PRIZEPOOL, tournamentTrendingList.getPrizePools());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return gameMoreTournamentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public IOnGamesClickListener mOnItemClickListener;
        @BindView(R.id.image_tournament)
        public ImageView circularImageView;
        @BindView(R.id.acb_total_participants)
        public TextView acbTotalParticipants;
        @BindView(R.id.tv_trend_win_prize)
        public TextView tvWinPrize;
        @BindView(R.id.tv_trend_entry_fee)
        public TextView tvEntryFee;
        @BindView(R.id.btn_trnd_play)
        public AppCompatButton acbPlay;
        @BindView(R.id.profile_images)
        public ImageView imgProfile;
        @BindView(R.id.pb_joined)
        public ProgressBar pbDroido;
        @BindView(R.id.tv_total_participants_new)
        public TextView tvParticipants;
        @BindView(R.id.tv_filter_my_tournament)
        public TextView tvFiltersTournament;
        @BindView(R.id.tv_ended_my_tournament)
        public TextView tvEnded;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }

    }

}