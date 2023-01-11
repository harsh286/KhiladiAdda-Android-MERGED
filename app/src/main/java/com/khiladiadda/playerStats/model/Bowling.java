package com.khiladiadda.playerStats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bowling {
    @SerializedName("listA")
    @Expose
    private ListA listA;
    @SerializedName("firstClass")
    @Expose
    private FirstClass firstClass;
    @SerializedName("T20Is")
    @Expose
    private T20Is t20Is;
    @SerializedName("ODIs")
    @Expose
    private ODIs oDIs;
    @SerializedName("tests")
    @Expose
    private Tests tests;

    public ListA getListA() {
        return listA;
    }

    public void setListA(ListA listA) {
        this.listA = listA;
    }

    public FirstClass getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(FirstClass firstClass) {
        this.firstClass = firstClass;
    }

    public T20Is getT20Is() {
        return t20Is;
    }

    public void setT20Is(T20Is t20Is) {
        this.t20Is = t20Is;
    }

    public ODIs getODIs() {
        return oDIs;
    }

    public void setODIs(ODIs oDIs) {
        this.oDIs = oDIs;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

}
