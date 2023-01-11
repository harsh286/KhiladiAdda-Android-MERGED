package com.khiladiadda.referhistory.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.ReferDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReferAdapter extends RecyclerView.Adapter<ReferAdapter.EventHolder> {

    private List<ReferDetail> mDetails;
    private IOnItemClickListener mOnItemClickListener;

    public ReferAdapter(List<ReferDetail> exhibitorList) {
        this.mDetails = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        ReferDetail details = mDetails.get(position);
        holder.mNameTV.setText(details.getName());
        if (!TextUtils.isEmpty(details.getUsername())) {
            holder.mUsernameTV.setText(details.getUsername());
        } else {
            holder.mUsernameTV.setText("");
        }
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(holder.mProfileIV.getContext()).load(details.getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(holder.mProfileIV.getContext()).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override public int getItemCount() {
        return mDetails.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_profile) ImageView mProfileIV;
        @BindView(R.id.tv_name) TextView mNameTV;
        @BindView(R.id.tv_user_name) TextView mUsernameTV;
        @BindView(R.id.tv_game_username) TextView mGameUsernameTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }
}