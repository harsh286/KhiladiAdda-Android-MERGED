package com.khiladiadda.clashx2.main.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.BattlesDeatilsHTH;
import com.khiladiadda.utility.AppConstant;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class MyBattlesAdpater extends RecyclerView.Adapter<MyBattlesAdpater.PersonViewHolder> {

    private List<BattlesDeatilsHTH> mBattleList;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private String id;
    private boolean islineup;
    private int mFrom;

    public MyBattlesAdpater(List<BattlesDeatilsHTH> mEventList, String id, boolean islineup, int mFrom) {
        this.mBattleList = mEventList;
        this.id = id;
        this.islineup = islineup;
        this.mFrom = mFrom;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_battle_hth_noswipe, parent, false);
        return new PersonViewHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        BattlesDeatilsHTH details = mBattleList.get(position);
        personViewHolder.mIDTV.setText(details.getContestId());
        personViewHolder.mEntryFeesTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getInvestedAmount()) + " coins");
        personViewHolder.mAmountTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getPrize()) + " coins");
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
        if (details.getBattle_status() == AppConstant.LIVEMATCH) {
            personViewHolder.mWaitingTV.setText("LIVE");
            personViewHolder.mTV.setVisibility(View.INVISIBLE);
            personViewHolder.mWaitingTV.setTextColor(Color.parseColor("#BF0020"));
            personViewHolder.mGifIV.setVisibility(View.VISIBLE);
            personViewHolder.mTV.setText("");
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            personViewHolder.mTV.setClickable(false);
        } else if (details.getBattle_status() == AppConstant.REVIEWPending) {
            personViewHolder.mWaitingTV.setText(R.string.text_in_review);
            personViewHolder.mTV.setVisibility(View.INVISIBLE);
            personViewHolder.mWaitingTV.setTextColor(Color.parseColor("#D26B0B"));
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert_circle, 0, 0, 0);
            personViewHolder.mGifIV.setVisibility(View.GONE);
            personViewHolder.mTV.setText("");
            personViewHolder.mTV.setClickable(false);
        } else if (details.getBattle_status() == AppConstant.COMPLETED) {
            personViewHolder.mWaitingTV.setText("Ended");
            personViewHolder.mTV.setVisibility(View.INVISIBLE);
            personViewHolder.mWaitingTV.setTextColor(Color.parseColor("#0A522F"));
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_ended, 0, 0, 0);
            personViewHolder.mGifIV.setVisibility(View.GONE);
            personViewHolder.mTV.setText("");
            personViewHolder.mTV.setClickable(false);
        } else if (details.getBattle_status() == 2) {
            personViewHolder.mTV.setVisibility(View.INVISIBLE);
            personViewHolder.mWaitingTV.setText("Accepted");
            personViewHolder.mWaitingTV.setTextColor(Color.parseColor("#5599FF"));
            personViewHolder.mGifIV.setVisibility(View.GONE);
            personViewHolder.mTV.setText("");
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group_joined, 0, 0, 0);
            personViewHolder.mTV.setClickable(false);
        } else if (details.getBattle_status() == 1) {
            personViewHolder.mTV.setVisibility(View.VISIBLE);
            personViewHolder.mWaitingTV.setText(R.string.waiting);
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
            personViewHolder.mGifIV.setVisibility(View.GONE);
            personViewHolder.mWaitingTV.setTextColor(Color.parseColor("#868686"));
            personViewHolder.mTV.setText("Cancel");
            personViewHolder.mTV.setBackgroundColor(Color.parseColor("#C10017"));
            personViewHolder.mTV.setOnClickListener(v -> mOnItemChildClickListener.onCancelCombo(position));
            personViewHolder.mTV.setClickable(true);
        }
        if (mFrom == AppConstant.FROM_FANBATTLE_PAST && !details.isIs_cancelled()) {
            if (id.equalsIgnoreCase(details.getCaptainId())) {
                if (details.getCaptain().isIs_won()) {
                    personViewHolder.mwinLL.setVisibility(View.GONE);
                    personViewHolder.mWon.setVisibility(View.VISIBLE);
                    personViewHolder.mWon.setText("You’ve won " + details.getPrize());
                    personViewHolder.mWon.setBackgroundResource(R.drawable.green_background);
                } else {
                    personViewHolder.mwinLL.setVisibility(View.GONE);
                    personViewHolder.mWon.setVisibility(View.VISIBLE);
                    personViewHolder.mWon.setText("Opponent won");
                    personViewHolder.mWon.setBackgroundResource(R.drawable.graycolor);
                }
            } else if (details.getOpponent().isIs_won()) {
                personViewHolder.mwinLL.setVisibility(View.GONE);
                personViewHolder.mWon.setVisibility(View.VISIBLE);
                personViewHolder.mWon.setText("You’ve won " + details.getPrize());
                personViewHolder.mWon.setBackgroundResource(R.drawable.green_background);
            } else {
                personViewHolder.mwinLL.setVisibility(View.GONE);
                personViewHolder.mWon.setVisibility(View.VISIBLE);
                personViewHolder.mWon.setText("Opponent won");
                personViewHolder.mWon.setBackgroundResource(R.drawable.graycolor);
            }
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST && details.isIs_cancelled()) {
            personViewHolder.mwinLL.setVisibility(View.GONE);
            personViewHolder.mWon.setVisibility(View.VISIBLE);
            personViewHolder.mWon.setText("Draw");
            personViewHolder.mWon.setBackgroundResource(R.drawable.roundbutton_red_hth);
            personViewHolder.mWaitingTV.setText("");
            personViewHolder.mWaitingTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            personViewHolder.mTV.setVisibility(View.VISIBLE);
            personViewHolder.mTV.setText("Battle cancelled due to same points");
            personViewHolder.mTV.setBackgroundColor(Color.parseColor("#9A082B"));
            personViewHolder.mTV.setClickable(false);
        }

        if (details.getnParticipants() == 2) {
            if (islineup && (mFrom != AppConstant.FROM_FANBATTLE_PAST) && (mFrom != AppConstant.FROM_FANBATTLE_LIVE)) {
                if (id.equalsIgnoreCase(details.getCaptainId())) {
                    if (details.getCaptain().isIs_team_updated()) {
                        personViewHolder.mTV.setVisibility(View.VISIBLE);
                        personViewHolder.mTV.setText(R.string.lineup_status);
                        personViewHolder.mTV.setBackgroundColor(Color.parseColor("#276600"));
                        personViewHolder.mTV.setClickable(false);
                    } else {
                        personViewHolder.mTV.setVisibility(View.VISIBLE);
                        personViewHolder.mTV.setText(R.string.lineup_error);
                        personViewHolder.mTV.setBackgroundColor(Color.parseColor("#B78400"));
                        personViewHolder.mTV.setClickable(false);
                    }
                } else {
                    if (details.getOpponent().isIs_team_updated()) {
                        personViewHolder.mTV.setVisibility(View.VISIBLE);
                        personViewHolder.mTV.setText(R.string.lineup_status);
                        personViewHolder.mTV.setBackgroundColor(Color.parseColor("#276600"));
                        personViewHolder.mTV.setClickable(false);
                    } else {
                        personViewHolder.mTV.setVisibility(View.VISIBLE);
                        personViewHolder.mTV.setText(R.string.lineup_error);
                        personViewHolder.mTV.setBackgroundColor(Color.parseColor("#B78400"));
                        personViewHolder.mTV.setClickable(false);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mBattleList.size();
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
        @BindView(R.id.tv_id)
        TextView mIDTV;
        @BindView(R.id.tv_entry)
        TextView mEntryFeesTV;
        @BindView(R.id.tv_waiting)
        TextView mWaitingTV;
        @BindView(R.id.gif_image)
        GifImageView mGifIV;
        @BindView(R.id.rl_main)
        RelativeLayout main;
        @BindView(R.id.ll_win)
        LinearLayout mwinLL;
        @BindView(R.id.tv_lineupout)
        TextView mTV;
        @BindView(R.id.tv_won)
        TextView mWon;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(@NonNull View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            main.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemChildClickListener != null) {
                if (v.getId() == R.id.rl_main) {
                    mOnItemChildClickListener.onUpdatePlayer(getBindingAdapterPosition());
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onUpdatePlayer(int position);

        void onCancelCombo(int position);
    }

}