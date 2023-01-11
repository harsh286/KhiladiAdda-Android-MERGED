package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesList implements Parcelable {
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    private boolean isSelected;
    private int mBattleSize;

    protected CategoriesList(Parcel in) {
        desc = in.readString();
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<CategoriesList> CREATOR = new Creator<CategoriesList>() {
        @Override
        public CategoriesList createFromParcel(Parcel in) {
            return new CategoriesList(in);
        }

        @Override
        public CategoriesList[] newArray(int size) {
            return new CategoriesList[size];
        }
    };

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(id);
        dest.writeString(title);
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getmBattleSize() {
        return mBattleSize;
    }

    public void setmBattleSize(int mBattleSize) {
        this.mBattleSize = mBattleSize;
    }
}