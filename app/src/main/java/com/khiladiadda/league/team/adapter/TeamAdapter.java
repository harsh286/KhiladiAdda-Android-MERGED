package com.khiladiadda.league.team.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.TeamName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.EventHolder> {

    private List<TeamName> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public TeamAdapter(List<TeamName> exhibitorList) {
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        TeamName details = mFDList.get(position);
        holder.mNameTV.setText("SLOT: " + String.valueOf(details.getTeamSlot()) + "-" + String.valueOf(details.getTeamName()));
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView mNameTV;

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