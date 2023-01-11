package com.khiladiadda.forgotpassword.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IForgetPasswordPresenter extends IBasePresenter {

    void validateData();

    void sendOtp(String mobile);

    void verifyOtp(String mobile, String otp);

    void resetPassword(String mobile, String otp, String password);

    void sendOtpVerify(String mobile);

    void verifyMobile(String mobile, String otp);
}
