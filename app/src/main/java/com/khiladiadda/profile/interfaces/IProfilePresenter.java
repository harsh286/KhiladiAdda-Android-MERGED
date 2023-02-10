package com.khiladiadda.profile.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IProfilePresenter extends IBasePresenter {

    void doUpdatePassword(String mobile, String password, String oldPassword);

    void doUpdateDOB(String dob);

    void getProfile();

    void applyVocher(String code, String pin);

    void doSendOTP(String email);

    void doVerifyEmail(String email, String otp);

    void updateEmail(String email);
}