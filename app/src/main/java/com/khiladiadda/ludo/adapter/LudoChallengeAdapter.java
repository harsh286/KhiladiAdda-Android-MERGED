package com.khiladiadda.ludo.adapter;

import android.content.Context;
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
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoChallengeAdapter extends RecyclerView.Adapter<LudoChallengeAdapter.LudoContestHolder> {

    private Context mContext;
    private List<LudoContest> mLudoChallengeList;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public LudoChallengeAdapter(Context context, List<LudoContest> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge, parent, false);
        return new LudoContestHolder(itemView, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        LudoContest ludoContestBean = mLudoChallengeList.get(position);
        String userId = AppSharedPreference.getInstance().getString(AppConstant.USER_ID, "");
        boolean mIsCaptain = userId.equals(ludoContestBean.getCaptainId());
        holder.mAmountTV.setText(ludoContestBean.getEntryFees() + " Coins");
        holder.mWinningAmountTV.setText("Winning: " + ludoContestBean.getWinningAmount() + " Coins");
        if (ludoContestBean.getMode() == 1) {
            holder.mModeTV.setText("CLASSIC");
        } else if (ludoContestBean.getMode() == 2) {
            holder.mModeTV.setText("POPULAR");
        }
        holder.mContestNameTV.setText(ludoContestBean.getContestCode());

        //1. Accepted (Check captain or opponent or not)
        //2. Not accepted (check captain or opponent or not)
        if (!ludoContestBean.isAccepted()) {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
            holder.mTitleTV.setText(R.string.text_challenged_for);
            holder.mPlayerNmeTV.setText(R.string.text_waiting_progress);
            if (mIsCaptain) {
                holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getName());
                holder.mAcceptTV.setText(R.string.text_waiting);
                holder.mAcceptTV.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.mAcceptTV.setEnabled(false);
                holder.mAcceptTV.setBackgroundResource(R.color.button_green);
                if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getDp())) {
                    Glide.with(mContext).load(ludoContestBean.getCaptain().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
                } else {
                    Glide.with(mContext).clear(holder.mProfileIV);
                    holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
                }
            } else {
                holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getLudoName());
                holder.mAcceptTV.setText(R.string.text_accept);
                holder.mAcceptTV.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.mAcceptTV.setEnabled(true);
                holder.mAcceptTV.setBackgroundResource(R.color.active_state_submit_button);
                if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getLudoDp())) {
                    Glide.with(mContext).load(ludoContestBean.getCaptain().getLudoDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
                } else {
                    Glide.with(mContext).clear(holder.mProfileIV);
                    holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
                }
            }
        } else {
            boolean mIsOpponent = userId.equals(ludoContestBean.getOpponentId());
            holder.mTitleTV.setText(R.string.text_challenge_accepted);
            holder.mAcceptTV.setText(R.string.text_accepted);
            holder.mAcceptTV.setEnabled(false);
            holder.mAcceptTV.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.mAcceptTV.setBackgroundResource(R.color.button_green);
            if (mIsCaptain) {
                holder.mPlayerNmeTV.setText(ludoContestBean.getCaptain().getName());
                holder.mCaptainNameTV.setText(ludoContestBean.getOpponent().getLudoName());
                setCaptainImage(ludoContestBean, holder);
                setOpponentDummyImage(ludoContestBean, holder);
            } else if (mIsOpponent) {
                holder.mPlayerNmeTV.setText(ludoContestBean.getCaptain().getLudoName());
                holder.mCaptainNameTV.setText(ludoContestBean.getOpponent().getName());
                setCaptainDummyImage(ludoContestBean, holder);
                setOpponentImage(ludoContestBean, holder);
            } else {
                holder.mPlayerNmeTV.setText(ludoContestBean.getCaptain().getLudoName());
                holder.mCaptainNameTV.setText(ludoContestBean.getOpponent().getLudoName());
                setCaptainDummyImage(ludoContestBean, holder);
                setOpponentDummyImage(ludoContestBean, holder);
            }
        }
    }

    private void setCaptainImage(LudoContest ludoContestBean, LudoContestHolder holder) {
        if (ludoContestBean.getCaptain() != null && ludoContestBean.getCaptain().getDp() != null && !TextUtils.isEmpty(ludoContestBean.getCaptain().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setCaptainDummyImage(LudoContest ludoContestBean, LudoContestHolder holder) {
        if (ludoContestBean.getCaptain() != null && ludoContestBean.getCaptain().getLudoDp() != null && !TextUtils.isEmpty(ludoContestBean.getCaptain().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getLudoDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setOpponentImage(LudoContest ludoContestBean, LudoContestHolder holder) {
        if (ludoContestBean.getOpponent() != null && ludoContestBean.getOpponent().getDp() != null && !TextUtils.isEmpty(ludoContestBean.getOpponent().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setOpponentDummyImage(LudoContest ludoContestBean, LudoContestHolder holder) {
        if (ludoContestBean.getOpponent() != null && ludoContestBean.getOpponent().getLudoDp() != null && !TextUtils.isEmpty(ludoContestBean.getOpponent().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getLudoDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public interface IOnItemChildClickListener {
        void onAcceptClicked(int position);
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_contest_code)
        TextView mContestNameTV;
        @BindView(R.id.tv_accept)
        TextView mAcceptTV;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_captain_name)
        TextView mCaptainNameTV;
        @BindView(R.id.iv_profile_player)
        ImageView mProfileAccepterIV;
        @BindView(R.id.tv_player_name)
        TextView mPlayerNmeTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;

        private IOnItemChildClickListener mOnItemChildClickListener;

        public LudoContestHolder(View view, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            mAcceptTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_accept) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onAcceptClicked(getBindingAdapterPosition());
                }
            }
        }
    }

}