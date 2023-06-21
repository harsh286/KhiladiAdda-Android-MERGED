package com.khiladiadda.dialogs;

public interface IOnPineLabsListener {

    public void iOnOtpSuccess();

    public void iOnResendOtp();

    /**
     * 1 -> Basic details save
     * 2 -> Pan Card*/
    public void iOnSuccessOkayClicked(int type);
}
