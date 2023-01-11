package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
import com.khiladiadda.gameleague.TournamenetDetailActivity;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentTrendingList;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TournamentMoreGameAdapter extends RecyclerView.Adapter<TournamentMoreGameAdapter.ViewHolder> {
    private Context mContext;
    private IOnGamesClickListener iOnGamesClickListener;
    private List<TournamentTrendingList> gameMoreTournamentList;
    private long lastItemClicked = 0;
    private int defaultInterval = 0;

    public TournamentMoreGameAdapter(Context mContext, IOnGamesClickListener iOnGamesClickListener, List<TournamentTrendingList> tournamentTrendingListList) {
        this.mContext = mContext;
        this.gameMoreTournamentList = tournamentTrendingListList;
        this.iOnGamesClickListener = iOnGamesClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View quizHeaderData = layoutInflater.inflate(R.layout.new_item_rv_more_tournament, parent, false);
        return new ViewHolder(quizHeaderData);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TournamentTrendingList tournamentTrendingModel = gameMoreTournamentList.get(position);
        holder.tvWinPrize.setText(mContext.getString(R.string.Win) +" "+ tournamentTrendingModel.getWinPrize().toString());
        holder.tvEntryFee.setText(mContext.getString(R.string.text_entry_fee_droido) + " "+tournamentTrendingModel.getEntryFees().toString());
        holder.acbTotalParticipants.setText(mContext.getString(R.string.attempts) + " "+tournamentTrendingModel.getnAttempts() + "/" + 3);
        holder.tv1stPrize.setText(mContext.getString(R.string.first_rupee)+tournamentTrendingModel.getPrizePools().get(0).getPrizeMoney());
        holder.tvParticipants.setText(tournamentTrendingModel.playedparticipants + "/" + tournamentTrendingModel.totalParticipants);
        holder.tvTournamentName.setText(tournamentTrendingModel.getName());
        holder.pbDroido.setProgress(tournamentTrendingModel.playedparticipants);
        holder.pbDroido.setMax(tournamentTrendingModel.totalParticipants);//sets the maximum value 100
        Glide.with(mContext)
                .load(tournamentTrendingModel.getImage())
                .placeholder(R.drawable.droido_defautl)
                .into(holder.imgProfile);
        holder.itemView.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - lastItemClicked < defaultInterval) {
                return;
            }
//            AppConstant
            lastItemClicked = SystemClock.elapsedRealtime();
            Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
            intent.putExtra(AppConstant.DROIDO_ID, tournamentTrendingModel.getId());
            intent.putExtra(AppConstant.DROIDO_NAME, tournamentTrendingModel.name);
            intent.putExtra(AppConstant.DROIDO_IMAGE, tournamentTrendingModel.image);
            intent.putExtra(AppConstant.DROIDO_ENTRY_FEE, tournamentTrendingModel.getEntryFees().toString());
            intent.putExtra(AppConstant.DROIDO_WIN_PRIZE, tournamentTrendingModel.getWinPrize().toString());
            intent.putExtra(AppConstant.DROIDO_N_ATTEMPTS, String.valueOf(tournamentTrendingModel.getnAttempts()));
            intent.putExtra(AppConstant.DROIDO_TOTAL_PARTICIPANTS, String.valueOf(tournamentTrendingModel.totalParticipants));
            intent.putExtra(AppConstant.DROIDO_PLAYED_PARTICIPANTS, String.valueOf(tournamentTrendingModel.playedparticipants));
            intent.putExtra(AppConstant.DROIDO_PRIZEPOOL, tournamentTrendingModel.getPrizePools());
            intent.putExtra(AppConstant.DROIDO_ENDS_IN, tournamentTrendingModel.getEnd());
            intent.putExtra("tournamentId", tournamentTrendingModel.getGameId());
            mContext.startActivity(intent);
        });
        holder.acbPlay.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - lastItemClicked < defaultInterval) {
                return;
            }
            lastItemClicked = SystemClock.elapsedRealtime();
            Intent intent = new Intent(mContext, TournamenetDetailActivity.class);
            intent.putExtra(AppConstant.DROIDO_ID, tournamentTrendingModel.getId());
            intent.putExtra(AppConstant.DROIDO_NAME, tournamentTrendingModel.name);
            intent.putExtra(AppConstant.DROIDO_IMAGE, tournamentTrendingModel.image);
            intent.putExtra(AppConstant.DROIDO_ENTRY_FEE, tournamentTrendingModel.getEntryFees().toString());
            intent.putExtra(AppConstant.DROIDO_WIN_PRIZE, tournamentTrendingModel.getWinPrize().toString());
            intent.putExtra(AppConstant.DROIDO_N_ATTEMPTS, String.valueOf(tournamentTrendingModel.getnAttempts()));
            intent.putExtra(AppConstant.DROIDO_TOTAL_PARTICIPANTS, String.valueOf(tournamentTrendingModel.totalParticipants));
            intent.putExtra(AppConstant.DROIDO_PLAYED_PARTICIPANTS, String.valueOf(tournamentTrendingModel.playedparticipants));
            intent.putExtra(AppConstant.DROIDO_PRIZEPOOL, tournamentTrendingModel.getPrizePools());
            intent.putExtra(AppConstant.DROIDO_ENDS_IN, tournamentTrendingModel.getEnd());
            intent.putExtra("tournamentId", tournamentTrendingModel.getGameId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameMoreTournamentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private IOnClickListener mOnItemClickListener;
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
        @BindView(R.id.profile_image)
        public CircleImageView imgProfile;
        @BindView(R.id.pb_joined)
        public ProgressBar pbDroido;
        @BindView(R.id.tv_total_participants_new)
        public TextView tvParticipants;
        @BindView(R.id.tv_1st_prize)
        public TextView tv1stPrize;
        @BindView(R.id.tv_filter_tournament_name)
        public TextView tvTournamentName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}