package com.khiladiadda.clashx2.main.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.CaptainResultHTH;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KabaadiPlayerStatusAdapter extends RecyclerView.Adapter<KabaadiPlayerStatusAdapter.PersonViewHolder> {

    private List<CaptainResultHTH> mPlayerList;

    public KabaadiPlayerStatusAdapter(List<CaptainResultHTH> playerList) {
        this.mPlayerList = playerList;
    }

    private CaptainResultHTH getItemAt(int index) {
        return mPlayerList.get(index);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kabaadi_player_points, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        CaptainResultHTH details = mPlayerList.get(position);
        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(personViewHolder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(personViewHolder.mProfileIV);
        } else {
            Glide.with(personViewHolder.mProfileIV.getContext()).clear(personViewHolder.mProfileIV);
            personViewHolder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        personViewHolder.mNameTV.setText(details.getTitle());
        personViewHolder.mTvSuccessRaid.setText(String.valueOf(details.getKbScore().getSuccessRaid()));
        personViewHolder.mTvSuccessTackle.setText(String.valueOf(details.getKbScore().getSuccessTackle()));
        personViewHolder.mTvRaidBonus.setText(String.valueOf(details.getKbScore().getRaidBonus()));
        personViewHolder.mTvSuccessTackle.setText(String.valueOf(details.getKbScore().getSuccessTackle()));
        personViewHolder.mTvSuperRaid.setText(String.valueOf(details.getKbScore().getSuperRaid()));
        personViewHolder.mTvSuperTackle.setText(String.valueOf(details.getKbScore().getSuperTackle()));
        personViewHolder.mTvSuperTenRaid.setText(String.valueOf(details.getKbScore().getSuperTenRaid()));
        personViewHolder.mTvHighFiveDefends.setText(String.valueOf(details.getKbScore().getHighFiveDefends()));
        personViewHolder.mTv_PlayingSeven.setText(String.valueOf(details.getKbScore().getPlayingSeven()));
        personViewHolder.mTvSubstitue.setText(String.valueOf(details.getKbScore().getSubstitue()));
        personViewHolder.mTvUnsuccessRaid.setText(String.valueOf(details.getKbScore().getUnsuccessRaid()));
        personViewHolder.mTvUnsuccessTackle.setText(String.valueOf(details.getKbScore().getUnsuccessTackle()));
        personViewHolder.mTvOppositionAllOut.setText(String.valueOf(details.getKbScore().getOppositionAllOut()));
        personViewHolder.mTvGreenCard.setText(String.valueOf(details.getKbScore().getGreenCard()));
        personViewHolder.mTvRedCard.setText(String.valueOf(details.getKbScore().getRedCard()));
        personViewHolder.mTvYellowCard.setText(String.valueOf(details.getKbScore().getYellowCard()));

        personViewHolder.mTvSuccessRaidPoints.setText(String.valueOf(details.getKbPoints().getSuccessRaid()));
        personViewHolder.mTvSuccessTacklePoints.setText(String.valueOf(details.getKbPoints().getSuccessTackle()));
        personViewHolder.mTvRaidBonusPoints.setText(String.valueOf(details.getKbPoints().getRaidBonus()));
        personViewHolder.mTvSuperTacklePoints.setText(String.valueOf(details.getKbPoints().getSuperTackle()));
        personViewHolder.mTvSuccessTacklePoints.setText(String.valueOf(details.getKbPoints().getSuccessTackle()));
        personViewHolder.mTvSuperRaidPoints.setText(String.valueOf(details.getKbPoints().getSuperRaid()));
        personViewHolder.mTvSuperTenRaidPoints.setText(String.valueOf(details.getKbPoints().getSuperTenRaid()));
        personViewHolder.mTvHighFiveDefendsPoints.setText(String.valueOf(details.getKbPoints().getHighFiveDefends()));
        personViewHolder.mTvPlayingSevenPoints.setText(String.valueOf(details.getKbPoints().getPlayingSeven()));
        personViewHolder.mTvSubstituePoints.setText(String.valueOf(details.getKbPoints().getSubstitue()));
        personViewHolder.mTvUnsuccessRaidPoints.setText(String.valueOf(details.getKbPoints().getUnsuccessRaid()));
        personViewHolder.mTvUnsuccessTacklePoints.setText(String.valueOf(details.getKbPoints().getUnsuccessTackle()));
        personViewHolder.mTvOppositionAllOutPoints.setText(String.valueOf(details.getKbPoints().getOppositionAllOut()));
        personViewHolder.mTvGreenCardPoints.setText(String.valueOf(details.getKbPoints().getGreenCard()));
        personViewHolder.mTvRedCardPoints.setText(String.valueOf(details.getKbPoints().getRedCard()));
        personViewHolder.mTvYellowCardPoints.setText(String.valueOf(details.getKbPoints().getYellowCard()));
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_success_raid)
        TextView mTvSuccessRaid;
        @BindView(R.id.tv_success_tackle)
        TextView mTvSuccessTackle;
        @BindView(R.id.tv_raid_bonus)
        TextView mTvRaidBonus;
        @BindView(R.id.tv_super_tackle)
        TextView mTvSuperTackle;
        @BindView(R.id.tv_super_raid)
        TextView mTvSuperRaid;
        @BindView(R.id.tv_super_ten_raid)
        TextView mTvSuperTenRaid;
        @BindView(R.id.tv_high_five_defends)
        TextView mTvHighFiveDefends;
        @BindView(R.id.tv_playing_seven)
        TextView mTv_PlayingSeven;
        @BindView(R.id.tv_substitue)
        TextView mTvSubstitue;
        @BindView(R.id.tv_unsuccess_raid)
        TextView mTvUnsuccessRaid;
        @BindView(R.id.tv_unsuccess_tackle)
        TextView mTvUnsuccessTackle;
        @BindView(R.id.tv_opposition_all_out)
        TextView mTvOppositionAllOut;
        @BindView(R.id.tv_green_card)
        TextView mTvGreenCard;
        @BindView(R.id.tv_yellow_card)
        TextView mTvYellowCard;
        @BindView(R.id.tv_red_card)
        TextView mTvRedCard;


        @BindView(R.id.tv_success_raid_points)
        TextView mTvSuccessRaidPoints;
        @BindView(R.id.tv_success_tackle_points)
        TextView mTvSuccessTacklePoints;
        @BindView(R.id.tv_raid_bonus_points)
        TextView mTvRaidBonusPoints;
        @BindView(R.id.tv_super_tackle_points)
        TextView mTvSuperTacklePoints;
        @BindView(R.id.tv_super_raid_points)
        TextView mTvSuperRaidPoints;
        @BindView(R.id.tv_super_ten_raid_points)
        TextView mTvSuperTenRaidPoints;
        @BindView(R.id.tv_high_five_defends_points)
        TextView mTvHighFiveDefendsPoints;
        @BindView(R.id.tv_playing_seven_points)
        TextView mTvPlayingSevenPoints;
        @BindView(R.id.tv_substitue_points)
        TextView mTvSubstituePoints;
        @BindView(R.id.tv_unsuccess_raid_points)
        TextView mTvUnsuccessRaidPoints;
        @BindView(R.id.tv_unsuccess_tackle_points)
        TextView mTvUnsuccessTacklePoints;
        @BindView(R.id.tv_opposition_all_out_points)
        TextView mTvOppositionAllOutPoints;
        @BindView(R.id.tv_green_card_points)
        TextView mTvGreenCardPoints;
        @BindView(R.id.tv_yellow_card_points)
        TextView mTvYellowCardPoints;
        @BindView(R.id.tv_red_card_points)
        TextView mTvRedCardPoints;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}