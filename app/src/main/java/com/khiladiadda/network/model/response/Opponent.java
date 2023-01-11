package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Opponent implements Parcelable {
    @SerializedName("username") @Expose private String username = null;
    @SerializedName("dp") @Expose private String dp = null;
    @SerializedName("id") @Expose private String id;
    @SerializedName("mobile") @Expose private long mobile;
    @SerializedName("name") @Expose private String name = null;
    @SerializedName("firebase_chat_id") @Expose private String firebase_chat_id = null;
    @SerializedName("ludo_name") @Expose private String ludoName = null;
    @SerializedName("ludo_dp") @Expose private String ludoDp = null;

    protected Opponent(Parcel in) {
        username = in.readString();
        dp = in.readString();
        id = in.readString();
        mobile = in.readLong();
        name = in.readString();
        ludoName = in.readString();
        firebase_chat_id = in.readString();
        ludoDp = in.readString();
    }

    public static final Creator<Opponent> CREATOR = new Creator<Opponent>() {
        @Override public Opponent createFromParcel(Parcel in) {
            return new Opponent(in);
        }

        @Override public Opponent[] newArray(int size) {
            return new Opponent[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebase_chat_id() {
        return firebase_chat_id;
    }

    public void setFirebase_chat_id(String firebase_chat_id) {
        this.firebase_chat_id = firebase_chat_id;
    }

    public String getLudoName() {
        return ludoName;
    }

    public void setLudoName(String ludoName) {
        this.ludoName = ludoName;
    }

    public String getLudoDp() {
        return ludoDp;
    }

    public void setLudoDp(String ludoDp) {
        this.ludoDp = ludoDp;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(dp);
        dest.writeString(id);
        dest.writeLong(mobile);
        dest.writeString(name);
        dest.writeString(ludoName);
        dest.writeString(firebase_chat_id);
        dest.writeString(ludoDp);
    }
}
