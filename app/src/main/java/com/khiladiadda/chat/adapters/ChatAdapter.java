package com.khiladiadda.chat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.chat.model.ChatMessage;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageViewHolder> {

    private static final int TYPE_USER_CURRENT = 1;
    private static final int TYPE_USER_OTHER = 2;
    private String mUserId;
    private List<ChatMessage> mChatMessageList;

    public ChatAdapter(List<ChatMessage> chatMessageList, String userId) {
        this.mChatMessageList = chatMessageList;
        this.mUserId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatMessageList.get(position).getId().equals(mUserId)) {
            return TYPE_USER_CURRENT;
        } else {
            return TYPE_USER_OTHER;
        }
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_USER_CURRENT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sender, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_receiver, parent, false);
        }
        return new ChatMessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder viewHolder, int position) {
        ChatMessage chatMessage = mChatMessageList.get(position);
        if (chatMessage.getPhotoUrl() == null) {
            viewHolder.mChatUserIV.setImageResource(R.drawable.profile);
        } else {
            Glide.with(viewHolder.mChatUserIV.getContext()).load(chatMessage.getPhotoUrl()).fallback(R.drawable.profile).into(viewHolder.mChatUserIV);
        }

        viewHolder.mChatMessageTV.setText(chatMessage.getText());
    }

    @Override
    public int getItemCount() {
        return mChatMessageList.size();
    }

    static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_chat_message)
        TextView mChatMessageTV;
        @BindView(R.id.iv_chat_user)
        ImageView mChatUserIV;

        ChatMessageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}