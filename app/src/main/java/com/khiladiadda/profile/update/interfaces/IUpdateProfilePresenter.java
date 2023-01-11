package com.khiladiadda.profile.update.interfaces;

import android.content.ContentResolver;
import android.net.Uri;

import com.khiladiadda.base.interfaces.IBasePresenter;

import java.io.File;

public interface IUpdateProfilePresenter extends IBasePresenter {

    void validateData();

    void uploadImage(Uri organizerImageUri, File organizerImageFile, ContentResolver contentResolver, int type);

    void doUpdateProfile(String image);

    void doUpdatePAN(String panURL, String panName, String panNumber);

    void checkAadhar(String aadharNumber);

    void getCaptcha(String requestId, String hashSequence);

    void getAadhaarOtp(String requestId, String uuid, String aadhaar, String captcha);

    void verifyAadhaarOtp(String requestId, String uuid, String otp, String shareCode, int from);

    void getAadhaarKYC(String requestId, String uuid, String hashSequence);

    void updateAadhaarServer(String name, String url, String urlAadhar, String address, String email, String gender, String dob, String aadhaarNo, String motherName, String fatherName, int from);

    void getWithdrawLimit();

    void verifyBeneficiary(String beneficiaryId);
    
}