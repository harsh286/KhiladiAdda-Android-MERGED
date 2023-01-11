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

        holder.tournamentTv.setText(item.getName());
        holder.entryTv.setText("" +Double.parseDouble(item.getEntryFees().toString())+" Coins");
        holder.priceTv.setText("" + Double.parseDouble(item.getPrize().toString())+" Coins");
        holder.roundTv.setText("" + item.getTtLevel());
        holder.startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(item.getStartDate()));
        holder.totalParticipantsTv.setText("" + item.getnParticipants());
        holder.totalParticipantsNew.setText(item.getnParticipated()+"/"+item.getnParticipants());
//        holder.joinedPb.setProgress(item.getnParticipated());
        holder.joinedPb.setProgress(3);
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
        IOnClickListener iOnClickListener;

        public ViewHolder(View view, IOnClickListener mIOnClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            iOnClickListener = mIOnClickListener;
            joinBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_join) {
                iOnClickListener.onItemClick(getAbsoluteAdapterPosition());
            }
        }
    }


}
