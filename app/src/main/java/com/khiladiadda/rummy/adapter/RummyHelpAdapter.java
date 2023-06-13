package com.khiladiadda.rummy.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.HelpRulesPointData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class RummyHelpAdapter extends RecyclerView.Adapter<RummyHelpAdapter.LudoContestHolder> {
    private Context mContext;
    private List<HelpRulesPointData> mHelpRulesPointData;
    public RummyHelpAdapter(Context context, List<HelpRulesPointData> mHelpRulesPointData) {
        this.mContext=context;
        this.mHelpRulesPointData=mHelpRulesPointData;
    }
    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_rummy_help, parent, false);
        return new LudoContestHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LudoContestHolder holder, int position) {
        HelpRulesPointData item = mHelpRulesPointData.get(position);
        holder.mRummyTittle.setText(item.getTitle());
        holder.mDescriptionTv.setText(item.getDesc());
        Glide.with(mContext).load(item.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.mIconIv);
    }

    @Override
    public int getItemCount() {
        return mHelpRulesPointData.size();
    }
    public static class LudoContestHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rummy_title)
        TextView mRummyTittle;
        @BindView(R.id.iv_icon)
        ImageView mIconIv;
        @BindView(R.id.tv_head_descp)
        TextView mDescriptionTv;
        public LudoContestHolder(View view) {
            super(view);
            ButterKnife.bind(this,itemView);
        }

    }

}