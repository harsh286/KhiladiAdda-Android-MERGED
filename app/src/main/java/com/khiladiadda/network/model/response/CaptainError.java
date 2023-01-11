package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptainError implements Parcelable {

    @SerializedName("img") @Expose private String img;
    @SerializedName("reason") @Expose private String reason;

    protected CaptainError(Parcel in) {
        img = in.readString();
        reason = in.readString();
    }

    public static final Creator<CaptainError> CREATOR = new Creator<CaptainError>() {
        @Override public CaptainError createFromParcel(Parcel in) {
            return new CaptainError(in);
        }

        @Override public CaptainError[] newArray(int size) {
            return new CaptainError[size];
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
