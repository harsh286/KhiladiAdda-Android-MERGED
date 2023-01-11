package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpponentError implements Parcelable {

    @SerializedName("img") @Expose private String img;
    @SerializedName("reason") @Expose private String reason;

    protected OpponentError(Parcel in) {
        img = in.readString();
        reason = in.readString();
    }

    public static final Creator<OpponentError> CREATOR = new Creator<OpponentError>() {
        @Override public OpponentError createFromParcel(Parcel in) {
            return new OpponentError(in);
        }

        @Override public OpponentError[] newArray(int size) {
            return new OpponentError[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(reason);
    }
}
