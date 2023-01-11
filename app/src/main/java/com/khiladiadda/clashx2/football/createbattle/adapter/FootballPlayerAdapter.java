package com.khiladiadda.clashx2.football.createbattle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.clashx2.football.createbattle.FootballKaPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballPlayerAdapter extends RecyclerView.Adapter<FootballPlayerAdapter.EventHolder> {

    private List<FootballKaPlayer> mBatmaslist;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public FootballPlayerAdapter(List<FootballKaPlayer> kaplayerList) {
        this.mBatmaslist = kaplayerList;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_battle_player_selection, parent, false);
        return new EventHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        FootballKaPlayer details = mBatmaslist.get(position);
        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.drawable.splash_logo);
        }
        holder.mNameTV.setText(details.getName());
        if (details.isSelected()) {
            holder.mPlayBTN.setSelected(true);
        } else {
            holder.mPlayBTN.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mBatmaslist.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_team_name)
        TextView mTeamNameTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.btn_play)
        Button mPlayBTN;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View itemView,  IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            mPlayBTN.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == R.id.btn_play) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onAddClicked(position);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onAddClicked(int position);
    }

}