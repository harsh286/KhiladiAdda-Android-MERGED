package com.khiladiadda.league.team.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.TeamDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.EventHolder> {

    private Context mContext;
    private List<TeamDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public MyTeamAdapter(Context context, List<TeamDetails> exhibitorList) {
        this.mContext = context;
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        TeamDetails details = mFDList.get(position);
        holder.mGameUsernameTV.setVisibility(View.GONE);
        holder.mUsernameTV.setVisibility(View.GONE);
        holder.mNameTV.setText(String.valueOf(details.getName()));
        if (!TextUtils.isEmpty(details.getDp())) {
            Glide.with(mContext).load(details.getDp()).thumbnail(Glide.with(mContext).load(details.getDp())).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.mProfileIV);
        }
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }


    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_user_name)
        TextView mUsernameTV;
        @BindView(R.id.tv_game_username)
        TextView mGameUsernameTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
            }
        }
    }

}