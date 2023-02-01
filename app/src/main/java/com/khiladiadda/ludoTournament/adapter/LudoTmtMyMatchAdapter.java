package com.khiladiadda.ludoTournament.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchResponse;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoTmtMyMatchAdapter extends RecyclerView.Adapter<LudoTmtMyMatchAdapter.ViewHolder> {
    private Context mContext;
    private IOnClickListener mIOnClickListener;
    private List<LudoTmtMyMatchResponse> myMatchLists;


    public LudoTmtMyMatchAdapter(Context mContext, IOnClickListener mIOnClickListener, List<LudoTmtMyMatchResponse> myMatchLists) {
        this.mContext = mContext;
        this.mIOnClickListener = mIOnClickListener;
        this.myMatchLists = myMatchLists;
    }

    @NonNull
    @Override
    public LudoTmtMyMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View quizHeaderData = layoutInflater.inflate(R.layout.items_of_alltournament_ludotmt, parent, false);
        return new LudoTmtMyMatchAdapter.ViewHolder(quizHeaderData, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LudoTmtMyMatchAdapter.ViewHolder holder, int position) {
        LudoTmtMyMatchResponse item = myMatchLists.get(position);
        if (item.gettStatus() == 1) {
            holder.joinBtn.setText("Match Ended");
            holder.joinBtn.setBackgroundColor(Color.parseColor("#6C56EF"));
        } else if (item.gettStatus() == 2) {
            holder.joinBtn.setText("You Won!");
            holder.joinBtn.setBackgroundColor(Color.parseColor("#29A93C"));
        } else if (item.gettStatus() == 3) {
            holder.joinBtn.setText("Opponent Won");
            holder.joinBtn.setBackgroundColor(Color.parseColor("#909090"));
        } else if (item.gettStatus() == 4) {
            holder.joinBtn.setText("Match Cancelled");
            holder.joinBtn.setBackgroundColor(Color.parseColor("#C54444"));
        } else {
            holder.joinBtn.setText("View Tournaments");
            holder.joinBtn.setBackgroundColor(Color.parseColor("#6C56EF"));
        }
        holder.tournamentTv.setText(item.getName());
        holder.entryTv.setText(String.format("%s Coins", Double.parseDouble(item.getEntryFees().toString())));
        holder.priceTv.setText(String.format("%s Coins", Double.parseDouble(item.getPrize().toString())));
        holder.roundTv.setText("" + item.getTtLevel());
        holder.startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(item.getStartDate()));
        holder.totalParticipantsTv.setText("" + item.getnParticipants());
        holder.totalParticipantsNew.setText(item.getnParticipated() + "/" + item.getnParticipants());
        holder.joinedPb.setProgress(item.getnParticipated());
        holder.joinedPb.setMax(item.getnParticipants());
    }


    @Override
    public int getItemCount() {
        return myMatchLists.size();
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
