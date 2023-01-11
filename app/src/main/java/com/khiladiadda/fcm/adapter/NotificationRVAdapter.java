package com.khiladiadda.fcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.request.NotificationModel;
import com.khiladiadda.interfaces.IOnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.EventHolder> {
    private List<NotificationModel> mNotificationModelList;
    private IOnCheckListener mOnCheckListener;
    private IOnItemClickListener mOnItemClickListener;
    private int mCheckCount;
    private Map<Integer, NotificationModel> mSelectedMap;

    public NotificationRVAdapter(Context context, List<NotificationModel> notificationModelList) {
        this.mNotificationModelList = notificationModelList;
        mSelectedMap = new HashMap<>();
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnCheckListener(IOnCheckListener mOnCheckListener) {
        this.mOnCheckListener = mOnCheckListener;
    }

    public Map<Integer, NotificationModel> getSelectedMap() {
        return mSelectedMap;
    }

    public void checkAll() {
        for (NotificationModel model : mNotificationModelList) {
            model.setSelected(true);
            mSelectedMap.put(model.getId(), model);
        }
        notifyDataSetChanged();
    }

    public void uncheckAll() {
        for (NotificationModel model : mNotificationModelList) {
            model.setSelected(false);
        }
        mSelectedMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnCheckListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final NotificationModel notificationList = mNotificationModelList.get(position);
        holder.mNameTV.setText(notificationList.getTitle());
        holder.mMessageTV.setText(notificationList.getMessage());
        holder.mDateTV.setText(notificationList.getDateTime());
        holder.mCheckboxIV.setSelected(notificationList.isSelected());
    }

    @Override
    public int getItemCount() {
        return mNotificationModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_notification_title)
        TextView mNameTV;
        @BindView(R.id.tv_notification_message)
        TextView mMessageTV;
        @BindView(R.id.tv_notification_date_time)
        TextView mDateTV;
        @BindView(R.id.iv_checkbox)
        ImageView mCheckboxIV;

        private IOnItemClickListener mOnItemClickListener;
        private IOnCheckListener mOnCheckListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnCheckListener onCheckListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            mOnCheckListener = onCheckListener;
            itemView.setOnClickListener(this);
            mCheckboxIV.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_checkbox) {
                NotificationModel model = mNotificationModelList.get(getAdapterPosition());
                model.setSelected(!model.isSelected());
                if (model.isSelected()) {
                    mSelectedMap.put(model.getId(), model);
                } else {
                    mSelectedMap.remove(model.getId());
                }
                notifyItemChanged(getAdapterPosition());
                if (mOnCheckListener != null) {
                    mOnCheckListener.setChecked(mSelectedMap.size() == mNotificationModelList.size());
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
                }
            }
        }
    }

    public interface IOnCheckListener {
        void setChecked(boolean isChecked);
    }

}