package com.khiladiadda.league.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class LeagueLiveListAdapter extends RecyclerView.Adapter<LeagueLiveListAdapter.PersonViewHolder> {

    private List<LeagueListDetails> mEventList;
    private IOnItemClickListener mOnItemClickListener;
    private String mGameId;

    public LeagueLiveListAdapter(List<LeagueListDetails> mEventList,String mGameId) {
        this.mEventList = mEventList;
        this.mGameId=mGameId;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private LeagueListDetails getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live_league_match, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener);
    }
    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        LeagueListDetails details = getItemAt(position);
        holder.mTitleTV.setText(details.title);
        AppSharedPreference preference = AppSharedPreference.getInstance();
        if (preference != null && mGameId!=null){
            if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.tdm);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_LITE_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.ic_bgmi);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FREEFIRE_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.ic_ff);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FF_CLASH_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.ff_clashs);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.FF_MAX_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.freefire_max);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.pubg_gobal_lite);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.esports_per);
            } else if (mGameId.equalsIgnoreCase(preference.getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                holder.mImageLgIV.setImageResource(R.drawable.pubg_ns);
            }
        } else {
            holder.mImageLgIV.setImageResource(R.mipmap.ic_launcher);
        }
    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.img_lg_image)
        ImageView mImageLgIV;
        private IOnItemClickListener mOnItemClickListener;
        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClickItem(v, getBindingAdapterPosition(), 0);
            }
        }
    }
   public interface IOnItemClickListener {
        //method called when an item of the recycler view is clicked
        public void onClickItem(View view, int position, int tag);
    }

}
