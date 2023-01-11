package com.khiladiadda.gameleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.droid_doresponse.ResponseItem;

import java.util.List;

public class GamesParticipantsAdapter extends RecyclerView.Adapter<GamesParticipantsAdapter.ViewHolder> {
    private List<ResponseItem> gameParticipantDataList;
    private Context mContext;

    public GamesParticipantsAdapter(List<ResponseItem> gameParticipantDataResponses, Context mContext) {
        this.gameParticipantDataList = gameParticipantDataResponses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mainGamesData = layoutInflater.inflate(R.layout.games_participant_items, parent, false);
        return new ViewHolder(mainGamesData);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseItem gameParticipantDataResponse = gameParticipantDataList.get(position);
        holder.tvName.setText("" + (position + 1)+". " + gameParticipantDataResponse.getParticipants().name);

    }

    @Override
    public int getItemCount() {
        return gameParticipantDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_participant_name);
        }
    }
}
