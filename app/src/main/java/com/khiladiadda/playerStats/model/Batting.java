package com.khiladiadda.playerStats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batting {
    @SerializedName("listA")
    @Expose
    private ListA_ listA;
    @SerializedName("firstClass")
    @Expose
    private FirstClass_ firstClass;
    @SerializedName("T20Is")
    @Expose
    private T20Is_ t20Is;
    @SerializedName("ODIs")
    @Expose
    private ODIs_ oDIs;
    @SerializedName("tests")
    @Expose
    private Tests_ tests;

    public ListA_ getListA() {
        return listA;
    }

    public void setListA(ListA_ listA) {
        this.listA = listA;
    }

    public FirstClass_ getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(FirstClass_ firstClass) {
        this.firstClass = firstClass;
    }

    public T20Is_ getT20Is() {
        return t20Is;
    }

    public void setT20Is(T20Is_ t20Is) {
        this.t20Is = t20Is;
    }

    public ODIs_ getODIs() {
        return oDIs;
    }

    public void setODIs(ODIs_ oDIs) {
        this.oDIs = oDIs;
    }

    public Tests_ getTests() {
        return tests;
    }

    public void setTests(Tests_ tests) {
        this.tests = tests;
    }

}
