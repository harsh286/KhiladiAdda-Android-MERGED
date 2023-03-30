package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet implements Parcelable {

    @SerializedName("winning") @Expose private double winning;
    @SerializedName("bonus") @Expose private double bonus;
    @SerializedName("deposit") @Expose private double deposit;

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.winning);
        dest.writeDouble(this.bonus);
        dest.writeDouble(this.deposit);
    }

    public void readFromParcel(Parcel source) {
        this.winning = source.readDouble();
        this.bonus = source.readDouble();
        this.deposit = source.readDouble();
    }

    public Wallet() {
    }

    protected Wallet(Parcel in) {
        this.winning = in.readDouble();
        this.bonus = in.readDouble();
        this.deposit = in.readDouble();
    }

    public static final Parcelable.Creator<Wallet> CREATOR = new Parcelable.Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
}
