package com.khiladiadda.network.model.request;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class NotificationModel extends RealmObject {

    @PrimaryKey private int mId;
    private String mTitle;
    private String mMessage;
    private String mDateTime;
    private int mStatus;
    @Ignore private boolean isSelected;

    public NotificationModel() {
    }

    public NotificationModel(int mId, String mTitle, String mMessage, String mDateTime, int mStatus) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        this.mDateTime = mDateTime;
        this.mStatus = mStatus;
    }

    public NotificationModel(int id) {
        mId = id;
    }

    public NotificationModel(NotificationModel notificationModel) {
        mId = notificationModel.getId();
        mTitle = notificationModel.getTitle();
        mMessage = notificationModel.getMessage();
        mDateTime = notificationModel.getDateTime();
        mStatus = notificationModel.getmStatus();
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String mDateTime) {
        this.mDateTime = mDateTime;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationModel model = (NotificationModel) o;

        return mId == model.mId;
    }

    @Override public int hashCode() {
        return mId;
    }

}