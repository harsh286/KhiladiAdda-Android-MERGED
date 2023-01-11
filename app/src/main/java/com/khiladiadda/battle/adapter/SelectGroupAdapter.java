package com.khiladiadda.battle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.battle.model.GroupDetails;
import com.khiladiadda.battle.model.Player;
import com.khiladiadda.interfaces.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectGroupAdapter extends RecyclerView.Adapter<SelectGroupAdapter.PersonViewHolder> {

    private List<GroupDetails> mGroupList;
    private IOnItemClickListener mOnItemClickListener;
    private int mSelectedIndex = -1;

    public SelectGroupAdapter(List<GroupDetails> mGroupList) {
        this.mGroupList = mGroupList;
    }

    public void setSelectedIndex(int index) {
        this.mSelectedIndex = index;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private GroupDetails getItemAt(int index) {
        return mGroupList.get(index);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_group, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        GroupDetails details = getItemAt(position);
        personViewHolder.mGroupFL.setSelected(position == mSelectedIndex || details.isPlayed());
        for (int playerIndex = 0; playerIndex < details.getPlayers().size(); playerIndex++) {
            Player player = details.getPlayers().get(playerIndex);
            personViewHolder.setPlayerVisibility(playerIndex, View.VISIBLE);
            personViewHolder.setPlayerName(player, playerIndex);
            personViewHolder.setPlayerImage(player, playerIndex);
        }
        for (int playerIndex = details.getPlayers().size(); playerIndex < 4; playerIndex++) {
            personViewHolder.setPlayerVisibility(playerIndex, View.GONE);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fl_group)
        FrameLayout mGroupFL;
        @BindView(R.id.ll_item_group_1)
        LinearLayout mGroupOneLL;
        @BindView(R.id.ll_item_group_2)
        LinearLayout mGroupTwoLL;
        @BindView(R.id.ll_item_group_3)
        LinearLayout mGroupThreeLL;
        @BindView(R.id.ll_item_group_4)
        LinearLayout mGroupFourLL;
        LinearLayout[] mIds;

        private IOnItemClickListener mOnItemClickListener;

        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            mIds = new LinearLayout[]{mGroupOneLL, mGroupTwoLL, mGroupThreeLL, mGroupFourLL};
        }

        void setPlayerVisibility(int position, int visibility) {
            mIds[position].setVisibility(visibility);
        }

        void setPlayerName(Player player, int position) {
            ((TextView) mIds[position].findViewById(R.id.tv_player)).setText(player.getTitle());
        }

        void setPlayerImage(Player player, int position) {
            String playerImage = player.getImg();
            ImageView playerIV = mIds[position].findViewById(R.id.iv_image);
            if (!TextUtils.isEmpty(playerImage) && playerImage.startsWith("https")) {
                Glide.with(playerIV.getContext()).load(playerImage).placeholder(R.drawable.profile).into(playerIV);
            } else {
                Glide.with(playerIV.getContext()).clear(playerIV);
                playerIV.setImageResource(R.mipmap.ic_launcher);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, position, 0);
            }
        }
    }

}