package com.khiladiadda.ludoTournament.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtTournamentDetailsResponse;
import com.khiladiadda.preference.AppSharedPreference;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoTmtRoundAdapter extends RecyclerView.Adapter<LudoTmtRoundAdapter.ViewHolder> {
    private Context mContext;
    private IOnClickListener mIOnClickListener;
    private List<LudoTmtRoundsDetailsResponse> ludoTmtRoundsDetailsResponseList;
    private LudoTmtTournamentDetailsResponse ludoTmtTournamentDetailsResponse;
    private boolean isExist, mIsPlayAvailable;


    public LudoTmtRoundAdapter(Context mContext, IOnClickListener mIOnClickListener, List<LudoTmtRoundsDetailsResponse> ludoTmtRoundsDetailsResponseList,
                               LudoTmtTournamentDetailsResponse ludoTmtTournamentDetailsResponse,
                               boolean isExist, boolean mIsPlayAvailable) {
        this.mContext = mContext;
        this.mIOnClickListener = mIOnClickListener;
        this.ludoTmtRoundsDetailsResponseList = ludoTmtRoundsDetailsResponseList;
        this.ludoTmtTournamentDetailsResponse = ludoTmtTournamentDetailsResponse;
        this.isExist = isExist;
        this.mIsPlayAvailable = mIsPlayAvailable;
    }

    @NonNull
    @Override
    public LudoTmtRoundAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_of_ludotmt_round, parent, false);
        return new LudoTmtRoundAdapter.ViewHolder(view, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LudoTmtRoundAdapter.ViewHolder holder, int position) {
        if (!isExist) {
            if ((ludoTmtRoundsDetailsResponseList.size() - 1) >= position) {
                holder.ludoTmtCl.setVisibility(View.VISIBLE);
                holder.OutOfTmtCl.setVisibility(View.GONE);
                LudoTmtRoundsDetailsResponse item = ludoTmtRoundsDetailsResponseList.get(position);
                holder.roundsTv.setText("ROUND " + ludoTmtTournamentDetailsResponse.getCurrentLevelInfo().getLevel().toString());
                holder.matchTv.setText("MATCH " + ludoTmtTournamentDetailsResponse.getTtMatch().toString());
                Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
                if (Objects.equals(item.getUserFirst(), AppSharedPreference.initialize(mContext).getMasterData().getResponse().getProfile().getId())) {
                    holder.firstPlayerTv.setText("You");
                    Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
                } else {
                    holder.firstPlayerTv.setText(item.getUserFirstInfo().getRandomName());
                    Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getRandomDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
                }
                if (Objects.equals(item.getUserFirst(), AppSharedPreference.initialize(mContext).getMasterData().getResponse().getProfile().getId())) {
                    holder.secondPlayerTv.setText("You");
                    Glide.with(holder.secondPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
                } else {
                    holder.secondPlayerTv.setText(item.getUserSecondInfo().getRandomName());
                    Glide.with(holder.secondPlayerIv.getContext()).load(item.getUserFirstInfo().getRandomDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
                }
                if (item.getRoomStatus() == 1) {
                    holder.secondPlayerTv.setText("waiting...");
                    ButtonEnable(holder, 0, 1, 0, 0);
                } else if (item.getRoomStatus() == 2) {
                    ButtonEnable(holder, 1, 0, 0, 0);
                } else if (item.getRoomStatus() == 3) {
                    ButtonEnable(holder, 0, 0, 1, 0);
                } else if (item.getRoomStatus() == 4) {
                    ButtonEnable(holder, 0, 0, 0, 1);
                }
            } else {
                holder.roundsTv.setText("Out of Tournament");
                holder.ludoTmtCl.setVisibility(View.GONE);
                holder.OutOfTmtCl.setVisibility(View.VISIBLE);
            }
        } else {
            holder.ludoTmtCl.setVisibility(View.VISIBLE);
            holder.OutOfTmtCl.setVisibility(View.GONE);
            LudoTmtRoundsDetailsResponse item = ludoTmtRoundsDetailsResponseList.get(position);
            holder.roundsTv.setText("ROUND " + ludoTmtTournamentDetailsResponse.getCurrentLevelInfo().getLevel().toString());
            holder.matchTv.setText("MATCH " + ludoTmtTournamentDetailsResponse.getTtMatch().toString());
            Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
            if (Objects.equals(item.getUserFirst(), AppSharedPreference.initialize(mContext).getMasterData().getResponse().getProfile().getId())) {
                holder.firstPlayerTv.setText("You");
                Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
            } else {
                holder.firstPlayerTv.setText(item.getUserFirstInfo().getRandomName());
                Glide.with(holder.firstPlayerIv.getContext()).load(item.getUserFirstInfo().getRandomDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
            }
            if (Objects.equals(item.getUserFirst(), AppSharedPreference.initialize(mContext).getMasterData().getResponse().getProfile().getId())) {
                holder.secondPlayerTv.setText("You");
                Glide.with(holder.secondPlayerIv.getContext()).load(item.getUserFirstInfo().getDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
            } else {
                holder.secondPlayerTv.setText(item.getUserSecondInfo().getRandomName());
                Glide.with(holder.secondPlayerIv.getContext()).load(item.getUserFirstInfo().getRandomDp()).fallback(R.drawable.profile).into(holder.firstPlayerIv);
            }
            if (mIsPlayAvailable) {
                holder.playNowBtn.setVisibility(View.VISIBLE);
            } else {
                holder.playNowBtn.setVisibility(View.GONE);
                if (item.getRoomStatus() == 1) {
                    holder.secondPlayerTv.setText("waiting...");
                    ButtonEnable(holder, 0, 1, 0, 0);
                } else if (item.getRoomStatus() == 2) {
                    ButtonEnable(holder, 1, 0, 0, 0);
                } else if (item.getRoomStatus() == 3) {
                    ButtonEnable(holder, 0, 0, 1, 0);
                } else if (item.getRoomStatus() == 4) {
                    ButtonEnable(holder, 0, 0, 0, 1);
                }
            }

        }
    }

    private void ButtonEnable(@NonNull LudoTmtRoundAdapter.ViewHolder holder, int status, int waiting, int won, int opp_won) {
        holder.statusBtn.setVisibility(View.INVISIBLE);
        holder.waitingBtn.setVisibility(View.INVISIBLE);
        holder.wonBtn.setVisibility(View.INVISIBLE);
        holder.oppWonBtn.setVisibility(View.INVISIBLE);
        holder.playNowBtn.setVisibility(View.INVISIBLE);

        if (status == 1) {
            holder.statusBtn.setVisibility(View.VISIBLE);
        } else if (waiting == 1) {
            holder.waitingBtn.setVisibility(View.VISIBLE);
        } else if (won == 1) {
            holder.wonBtn.setVisibility(View.VISIBLE);
        } else if (opp_won == 1) {
            holder.oppWonBtn.setVisibility(View.VISIBLE);
        }else {
            holder.playNowBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (!isExist)
            return (ludoTmtRoundsDetailsResponseList.size() + 1);
        else
            return ludoTmtRoundsDetailsResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_rounds)
        TextView roundsTv;
        @BindView(R.id.tv_match)
        TextView matchTv;
        @BindView(R.id.tv_first_player)
        TextView firstPlayerTv;
        @BindView(R.id.tv_second_player)
        TextView secondPlayerTv;
        @BindView(R.id.iv_first_player)
        ImageView firstPlayerIv;
        @BindView(R.id.iv_second_player)
        ImageView secondPlayerIv;
        @BindView(R.id.btn_status)
        MaterialCardView statusBtn;
        @BindView(R.id.btn_waiting)
        MaterialCardView waitingBtn;
        @BindView(R.id.btn_won)
        MaterialCardView wonBtn;
        @BindView(R.id.btn_opp_won)
        MaterialCardView oppWonBtn;
        @BindView(R.id.btn_play_now)
        MaterialCardView playNowBtn;
        @BindView(R.id.cl_ludo_tmt)
        ConstraintLayout ludoTmtCl;
        @BindView(R.id.cl_out_of_tmt)
        ConstraintLayout OutOfTmtCl;


        IOnClickListener iOnClickListener;

        public ViewHolder(View view, IOnClickListener mIOnClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            iOnClickListener = mIOnClickListener;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_play_now) {
                iOnClickListener.onItemClick(getAbsoluteAdapterPosition());
            }
        }
    }


}
