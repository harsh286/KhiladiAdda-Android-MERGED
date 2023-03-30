package com.khiladiadda.ludoTournament.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoTmtDashboardAdapter extends RecyclerView.Adapter<LudoTmtDashboardAdapter.ViewHolder> {
    private Context mContext;
    private IOnClickListener mIOnClickListener;
    private List<LudoTmtAllTournamentResponse> mLudoTmtAllTournamentResponses;

    public LudoTmtDashboardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LudoTmtDashboardAdapter(Context mContext, IOnClickListener mIOnClickListener, List<LudoTmtAllTournamentResponse> mLudoTmtAllTournamentResponses) {
        this.mContext = mContext;
        this.mIOnClickListener = mIOnClickListener;
        this.mLudoTmtAllTournamentResponses = mLudoTmtAllTournamentResponses;
    }

    @NonNull
    @Override
    public LudoTmtDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View quizHeaderData = layoutInflater.inflate(R.layout.items_of_alltournament_ludotmt, parent, false);
        return new LudoTmtDashboardAdapter.ViewHolder(quizHeaderData, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LudoTmtDashboardAdapter.ViewHolder holder, int position) {
        LudoTmtAllTournamentResponse item = mLudoTmtAllTournamentResponses.get(position);
        holder.tournamentTv.setText(item.getName());
        holder.entryTv.setText(String.format("%s Coins",item.getEntryFees().toString()));
        holder.priceTv.setText(String.format("%s Coins", item.getPrize().toString()));
        holder.roundTv.setText("" + item.getTtLevel());
        holder.startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(item.getStartDate()));
        holder.totalParticipantsTv.setText("" + item.getnParticipants());
        holder.totalParticipantsNew.setText(item.getnParticipated()+"/"+item.getnParticipants());
        holder.joinedPb.setProgress(item.getnParticipated());
        holder.joinedPb.setMax(item.getnParticipants());
        if (item.isJoined()){
            holder.joinBtn.setText("View Tournament");
        }else {
            holder.joinBtn.setText("Join Tournament");
        }
    }

    @Override
    public int getItemCount() {
        return mLudoTmtAllTournamentResponses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.btn_join)
        AppCompatButton joinBtn;
        @BindView(R.id.tv_entry)
        TextView entryTv;
        @BindView(R.id.tv_price)
        TextView priceTv;
        @BindView(R.id.tv_rounds)
        TextView roundTv;
        @BindView(R.id.tv_starttime)
        TextView startTimeTv;
        @BindView(R.id.tv_total_participants)
        TextView totalParticipantsTv;
        @BindView(R.id.tv_name)
        TextView tournamentTv;
        @BindView(R.id.tv_total_participants_new)
        TextView totalParticipantsNew;
        @BindView(R.id.pb_joined)
        ProgressBar joinedPb;
        @BindView(R.id.mcv_tournaments)
        MaterialCardView tournamentMcv;
        IOnClickListener iOnClickListener;

        public ViewHolder(View view, IOnClickListener mIOnClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            iOnClickListener = mIOnClickListener;
            joinBtn.setOnClickListener(this);
            tournamentMcv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_join) {
                iOnClickListener.onItemClick(getAbsoluteAdapterPosition());
            }else if (view.getId() == R.id.mcv_tournaments) {
                iOnClickListener.onItemClick(getAbsoluteAdapterPosition());
            }
        }
    }

}