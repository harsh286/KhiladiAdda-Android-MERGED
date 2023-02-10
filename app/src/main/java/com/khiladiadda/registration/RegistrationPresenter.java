package com.khiladiadda.registration;

import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.RegistrationRequest;
import com.khiladiadda.registration.interfaces.IRegistrationPresenter;
import com.khiladiadda.registration.interfaces.IRegistrationView;
import com.khiladiadda.utility.AppUtilityMethods;

import rx.Subscription;

public class RegistrationPresenter implements IRegistrationPresenter {

    private IRegistrationView mView;
    private RegistrationInteractor mInteractor;
    private Subscription mSubscription;

    public RegistrationPresenter(IRegistrationView view) {
        mView = view;
        mInteractor = new RegistrationInteractor();
    }

    @Override
    public void validateData() {
        String name = mView.getName();
        String mobileNumber = mView.getMobileNumber();
        String email = mView.getEmail();
        if (TextUtils.isEmpty(name)) {
            mView.onValidationFailure("User name cannot be empty");
            return;
        }
        if (name.length() < 3) {
            mView.onValidationFailure("Please provide proper name");
            return;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            mView.onValidationFailure("Mobile number cannot be empty");
            return;
        }
        if (mobileNumber.length() != 10) {
            mView.onValidationFailure("Mobile number should be 10 digit");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mView.onValidationFailure("Please select email id");
            return;
        }
        if (!AppUtilityMethods.isMobileValidator(mobileNumber)) {
            mView.onValidationFailure("Please provide valid mobile number");
            return;
        }
        mView.onValidationComplete();
    }

    @Override
    public void doRegister(String gmailId) {
        RegistrationRequest request = new RegistrationRequest(mView.getName(), mView.getMobileNumber(), mView.getReferenceNo(), mView.getEmail(), gmailId);
        mSubscription = mInteractor.doRegister(request, mLoginApiListener);
    }

    private IApiListener<BaseResponse> mLoginApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onRegisterComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRegisterFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}