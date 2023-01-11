package com.khiladiadda.clashx2.cricket.createbattle.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.CaptainTeamHTH;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerSelectionAdapter extends RecyclerView.Adapter<PlayerSelectionAdapter.EventHolder> {

    private List<KaPlayerHTH> mPlayerList;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private List<CaptainTeamHTH> captainHTH;
    private List<CaptainTeamHTH> OppentHTH;

    public PlayerSelectionAdapter(List<KaPlayerHTH> kaplayerList, List<CaptainTeamHTH> captainHTH, List<CaptainTeamHTH> OppentHTH) {
        this.mPlayerList = kaplayerList;
        this.captainHTH = captainHTH;
        this.OppentHTH = OppentHTH;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_battle_player_selection, parent, false);
        return new EventHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        KaPlayerHTH details = mPlayerList.get(position);

        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mNameTV.setText(details.getTitle());
//        holder.mTeamNameTV.setText(details.getTeamName());

        if (details.getPlaying() == 1 && details.isSelected()) {
            holder.mPlayBTN.setText("Added");
            holder.mLayoutRL.setBackgroundResource(0);
            holder.mPlayBTN.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick, 0, 0, 0);
            holder.mStatusTV.setText("Selected");
            holder.mStatusTV.setTextColor(Color.parseColor("#9B9B9B"));
            holder.mPlayBTN.setSelected(true);
            holder.mPlayBTN.setClickable(true);
        } else if (details.getPlaying() == 1 && !details.isSelected()) {
            holder.mPlayBTN.setSelected(false);
            holder.mLayoutRL.setBackgroundResource(0);
            holder.mPlayBTN.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.mPlayBTN.setText("+Add");
            holder.mStatusTV.setText("Unselected");
            holder.mPlayBTN.setClickable(true);
            holder.mStatusTV.setTextColor(Color.parseColor("#9B9B9B"));
        } else {
            holder.mLayoutRL.setBackgroundColor(Color.parseColor("#EEEEEE"));
            holder.mPlayBTN.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.mPlayBTN.setText("Not Playing");
            holder.mStatusTV.setText("Unselected");
            holder.mPlayBTN.setClickable(false);
            holder.mPlayBTN.setSelected(false);
            holder.mStatusTV.setTextColor(Color.parseColor("#9B9B9B"));
        }

        if (captainHTH != null && !captainHTH.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                if (details.getId().equalsIgnoreCase(captainHTH.get(i).getPlayerId())) {
                    holder.mStatusTV.setText("Selected by opponent");
                    holder.mStatusTV.setTextColor(Color.parseColor("#ED2238"));
                }
            }
        } else if (OppentHTH != null && !OppentHTH.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                if (details.getId().equalsIgnoreCase(OppentHTH.get(i).getPlayerId())) {
                    holder.mStatusTV.setText("Selected by opponent");
                    holder.mStatusTV.setTextColor(Color.parseColor("#ED2238"));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_profile)
        CircleImageView mProfileIV;
        @BindView(R.id.tv_team_name)
        TextView mTeamNameTV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.btn_play)
        Button mPlayBTN;
        @BindView(R.id.tv_status)
        TextView mStatusTV;
        @BindView(R.id.rl_layout)
        RelativeLayout mLayoutRL;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            mPlayBTN.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (v.getId() == R.id.btn_play) {
                if (mOnItemChildClickListener != null && position >= 0) {
                    mOnItemChildClickListener.onPlayerSelected(position);

                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onPlayerSelected(int position);
    }

}