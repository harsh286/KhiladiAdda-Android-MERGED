package com.khiladiadda.socialverify.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.request.FBRegisterRequest;

public interface ISocialVerifyPresenter extends IBasePresenter {

    void validateData();

    void doGmailRegister(GmailRegisterRequest request);

    void doFbRegister(FBRegisterRequest request);

    void verifyOtp(String mobile, String otp);

    void resendOtp(String mobile);

    void getMasterData();

}