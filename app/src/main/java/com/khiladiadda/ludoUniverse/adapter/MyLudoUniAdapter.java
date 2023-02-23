package com.khiladiadda.ludoUniverse.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLudoUniAdapter extends RecyclerView.Adapter<MyLudoUniAdapter.LudoContestHolder> {

    private Context mContext;
    private List<LudoContest> mLudoChallengeList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public MyLudoUniAdapter(Context context, List<LudoContest> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_ludo_uni, parent, false);
        return new LudoContestHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        LudoContest ludoContestBean = mLudoChallengeList.get(position);
        String userId = AppSharedPreference.getInstance().getString(AppConstant.USER_ID, "");
        holder.mWinningAmountTV.setText(String.format("Winning: %s Coins", ludoContestBean.getWinningAmount()));
        holder.mContestNameTV.setText(ludoContestBean.getContestCode());
        if (ludoContestBean.getContestStatus() == 3) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                if (ludoContestBean.getContestStatus() == 1) {
                    setOpponentData(ludoContestBean, holder);
                } else {
                    holder.mTitleTV.setText(String.format("You have challenged for\n%s Coins", ludoContestBean.getEntryFees()));
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                holder.mTitleTV.setText(String.format("You accepted challenge for \n%s Coins", ludoContestBean.getEntryFees()));
            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setText(R.string.txt_canceled);
            holder.mCancelTV.setBackgroundColor(Color.parseColor("#DA0000"));
            holder.mCancelTV.setEnabled(false);
        } else if (ludoContestBean.getContestStatus() == 8) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                if (ludoContestBean.getContestStatus() == 1) {
                    setOpponentData(ludoContestBean, holder);
                } else {
                    holder.mTitleTV.setText(String.format("You have challenged for\n%s Coins", ludoContestBean.getEntryFees()));
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                holder.mTitleTV.setText(String.format("You accepted challenge for \n%s Coins", ludoContestBean.getEntryFees()));
            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setText("Draw");
            holder.mCancelTV.setBackgroundColor(Color.parseColor("#A2A2A2"));
            holder.mCancelTV.setEnabled(false);
        } else if (ludoContestBean.getContestStatus() == 2) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                setOpponentData(ludoContestBean, holder);
                if (ludoContestBean.getCaptainResult().isIsWon()) {
                    holder.mCancelTV.setText(R.string.text_you_won);
                    holder.mCancelTV.setBackgroundColor(Color.parseColor("#4CAF50"));
                } else {
                    holder.mCancelTV.setText(R.string.text_you_lost);
                    holder.mCancelTV.setBackgroundColor(Color.parseColor("#A2A2A2"));
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                if (ludoContestBean.getOpponentResult().isIsWon()) {
                    holder.mCancelTV.setText(R.string.text_you_won);
                    holder.mCancelTV.setBackgroundColor(Color.parseColor("#4CAF50"));
                } else {
                    holder.mCancelTV.setText(R.string.text_you_lost);
                    holder.mCancelTV.setBackgroundColor(Color.parseColor("#A2A2A2"));
                }
            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setEnabled(false);
        } else if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
            if (ludoContestBean.getContestStatus() == 1) {
                setOpponentData(ludoContestBean, holder);
                holder.mCancelTV.setText(R.string.text_play_now);
                holder.mCancelTV.setBackgroundColor(Color.parseColor("#6C56EF"));
            } else {
                holder.mTitleTV.setText(String.format("You have challenged for\n%s Coins", ludoContestBean.getEntryFees()));
                holder.mPlayerNameTV.setText(R.string.text_waiting_dot);
                holder.mCancelTV.setText(R.string.text_cancel);
                holder.mCancelTV.setBackgroundColor(Color.parseColor("#DA0000"));

            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setEnabled(true);
        } else if (userId.equalsIgnoreCase(ludoContestBean.getOpponentId())) {
            setCaptainData(ludoContestBean, holder);
            if (ludoContestBean.getContestStatus() == 1) {
                holder.mCancelTV.setText(R.string.text_play_now);
                holder.mCancelTV.setBackgroundColor(Color.parseColor("#6C56EF"));

            } else {
                holder.mCancelTV.setText(R.string.text_cancel);
                holder.mCancelTV.setBackgroundColor(Color.parseColor("#DA0000"));
            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setEnabled(true);
        }
    }

    private void setCaptainData(LudoContest ludoContestBean, LudoContestHolder holder) {
        holder.mTitleTV.setText("You accepted challenge for \n" + ludoContestBean.getEntryFees() + " Coins");
        if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getLudoDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getLudoDp()).placeholder(R.drawable.profile).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mPlayerNameTV.setText(ludoContestBean.getCaptain().getLudoName());
    }

    private void setOpponentData(LudoContest ludoContestBean, LudoContestHolder holder) {
        holder.mTitleTV.setText("Your challenge has been accepted\n" + ludoContestBean.getEntryFees() + " Coins");
        if (ludoContestBean.getOpponent() != null && ludoContestBean.getOpponent().getLudoDp() != null && !TextUtils.isEmpty(ludoContestBean.getOpponent().getLudoDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getLudoDp()).placeholder(R.drawable.profile).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getLudoName());
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public interface IOnItemChildClickListener {
        void onCancelClick(int position);
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_contest_code)
        TextView mContestNameTV;
        @BindView(R.id.tv_cancel)
        TextView mCancelTV;
        @BindView(R.id.iv_profile_player)
        ImageView mProfileAccepterIV;
        @BindView(R.id.tv_player_name)
        TextView mPlayerNameTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public LudoContestHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mCancelTV.setOnClickListener(this);
            mModeTV.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_cancel) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onCancelClick(getBindingAdapterPosition());
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                }
            }
        }
    }

}