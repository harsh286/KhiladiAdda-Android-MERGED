package com.khiladiadda.clashx2.football.createbattle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballAllBattlesAdapter extends RecyclerView.Adapter<FootballAllBattlesAdapter.PersonViewHolder> {

    private List<BattlesDeatilsHTH> mBattleList;
    private String mid;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private final int COUNT = 30;
    private final int[] itemsOffset = new int[COUNT];
    private int pos = -1;

    public FootballAllBattlesAdapter(List<BattlesDeatilsHTH> mEventList, String mid) {
        this.mBattleList = mEventList;
        this.mid = mid;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private BattlesDeatilsHTH getItemAt(int index) {
        return mBattleList.get(index);
    }

    @Override
    public int getItemCount() {
        return mBattleList.size();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_battle_hth, viewGroup, false);
        return new PersonViewHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        BattlesDeatilsHTH details = mBattleList.get(position);
        personViewHolder.mIDTV.setText(details.getContestId());
        personViewHolder.mEntryFeesTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getInvestedAmount()) + " coins");
        personViewHolder.mAmountTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getPrize()));
        if (details.getnParticipants() == 1 && details.getCaptainId().equalsIgnoreCase(mid)) {
            personViewHolder.mVsTV.setText(R.string.captain_battle);
            personViewHolder.mCancelTV.setVisibility(View.VISIBLE);
            personViewHolder.mAcceptTV.setVisibility(View.GONE);
        } else if (details.getnParticipants() == 1 && !details.getCaptainId().equalsIgnoreCase(mid)) {
            personViewHolder.mVsTV.setText(R.string.opponent_battle);
            personViewHolder.mAcceptTV.setVisibility(View.VISIBLE);
            personViewHolder.mCancelTV.setVisibility(View.GONE);
        } else if (details.getnParticipants() == 2 && details.getCaptainId().equalsIgnoreCase(mid)) {
            personViewHolder.mVsTV.setText(R.string.text_headtohead_participate);
            personViewHolder.mAcceptTV.setVisibility(View.GONE);
            personViewHolder.mCancelTV.setVisibility(View.GONE);
        } else if (details.getnParticipants() == 2 && details.getOpponentId().equalsIgnoreCase(mid)) {
            personViewHolder.mVsTV.setText(R.string.text_headtohead_participate);
            personViewHolder.mAcceptTV.setVisibility(View.GONE);
            personViewHolder.mCancelTV.setVisibility(View.GONE);
        } else {
            personViewHolder.mVsTV.setText(R.string.text_headtohead_participate);
            personViewHolder.mAcceptTV.setVisibility(View.GONE);
            personViewHolder.mCancelTV.setVisibility(View.GONE);
        }
        if (details.getCaptain() != null) {
            personViewHolder.mPlayerOneTV.setText(details.getCaptain().getName());
            Glide.with(personViewHolder.mPlayerOneIV.getContext()).load(details.getCaptain().getDp()).placeholder(R.drawable.profile).into(personViewHolder.mPlayerOneIV);
        } else {
            Glide.with(personViewHolder.mPlayerOneIV.getContext()).clear(personViewHolder.mPlayerOneIV);
            personViewHolder.mPlayerOneIV.setImageResource(R.drawable.splash_logo);
            personViewHolder.mPlayerOneTV.setText("");
        }
        if (details.getOpponent() != null) {
            personViewHolder.mPlayerTwoTV.setText(details.getOpponent().getName());
            Glide.with(personViewHolder.mPlayerTwoIV.getContext()).load(details.getOpponent().getDp()).placeholder(R.drawable.profile).into(personViewHolder.mPlayerTwoIV);
        } else {
            Glide.with(personViewHolder.mPlayerTwoIV.getContext()).clear(personViewHolder.mPlayerTwoIV);
            personViewHolder.mPlayerTwoIV.setImageResource(R.drawable.splash_logo);
            personViewHolder.mPlayerTwoTV.setText("waiting...");
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_one)
        ImageView mPlayerOneIV;
        @BindView(R.id.tv_team_one)
        TextView mPlayerOneTV;
        @BindView(R.id.iv_two)
        ImageView mPlayerTwoIV;
        @BindView(R.id.tv_team_two)
        TextView mPlayerTwoTV;
        @BindView(R.id.tv_win)
        TextView mAmountTV;
        @BindView(R.id.vs)
        TextView mVsTV;
        @BindView(R.id.tv_id)
        TextView mIDTV;
        @BindView(R.id.tv_entry)
        TextView mEntryFeesTV;
        @BindView(R.id.ll_one)
        LinearLayout mOneLL;
        @BindView(R.id.tv_accept)
        TextView mAcceptTV;
        @BindView(R.id.tv_cancel)
        TextView mCancelTV;

        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            mOneLL.setOnClickListener(this);
            mAcceptTV.setOnClickListener(this);
            mCancelTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemChildClickListener != null) {
                if (v.getId() == R.id.swipe_container) {
                    mOnItemChildClickListener.onUpdateClicked(getBindingAdapterPosition());
                } else if (v.getId() == R.id.tv_cancel) {
                    mOnItemChildClickListener.onCanceledClicked(getBindingAdapterPosition());
                } else if (v.getId() == R.id.tv_accept) {
                    mOnItemChildClickListener.onUpdateClicked(getBindingAdapterPosition());
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onUpdateClicked(int position);

        void onJoinedCLicked(int postion);

        void onCanceledClicked(int position);
    }

}