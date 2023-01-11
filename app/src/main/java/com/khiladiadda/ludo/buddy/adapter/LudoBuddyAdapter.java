package com.khiladiadda.ludo.buddy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.BuddyDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoBuddyAdapter extends RecyclerView.Adapter<LudoBuddyAdapter.LudoContestHolder> {

    private Context mContext;
    private List<BuddyDetails> mLudoChallengeList;
    private LudoBuddyAdapter.IOnItemChildClickListener mOnItemChildClickListener;

    public LudoBuddyAdapter(Context context, List<BuddyDetails> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemChildClickListener(LudoBuddyAdapter.IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buddy, parent, false);
        return new LudoContestHolder(itemView, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(LudoContestHolder holder, int position) {
        BuddyDetails ludoContestBean = mLudoChallengeList.get(position);
        if(!TextUtils.isEmpty(ludoContestBean.getName())) {
            holder.mNameTV.setText(ludoContestBean.getName());
        }
        if (!TextUtils.isEmpty(ludoContestBean.getDp())) {
            Glide.with(mContext).load(ludoContestBean.getDp()).placeholder(R.drawable.profile).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public interface IOnItemChildClickListener {
        void onRequestClicked(int position);
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_profile) ImageView mProfileIV;
        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.btn_request) Button mRequestBTN;

        private LudoBuddyAdapter.IOnItemChildClickListener mOnItemChildClickListener;

        public LudoContestHolder(View view, LudoBuddyAdapter.IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            mRequestBTN.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (v.getId() == R.id.btn_request) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onRequestClicked(getAdapterPosition());
                }
            }
        }
    }

}