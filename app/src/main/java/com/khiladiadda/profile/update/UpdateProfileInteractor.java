package com.khiladiadda.profile.update;

import com.khiladiadda.network.SurepassVerifiApiManger;
import com.khiladiadda.network.SurepassVerifiSubscriberCallback;
import com.khiladiadda.network.VerifiApiManger;
import com.khiladiadda.network.VerifiSubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.PanUpdateRequest;
import com.khiladiadda.network.model.request.SurepassGenerateOtpRequest;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.request.VerifyOtpSurepass;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.ProfileRequest;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.request.AadhaarRequest;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.request.AadharUpdateRequest;
import com.khiladiadda.network.model.request.CaptchaRequest;
import com.khiladiadda.network.model.request.CheckAadharRequest;
import com.khiladiadda.network.model.request.GetOtpRequest;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.network.model.request.VerifyOtpRequest;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.utility.AppConstant;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscription;

public class UpdateProfileInteractor {

    public Subscription updateProfile(ProfileRequest request, IApiListener<ProfileResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateProfile(request))
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription uploadImage(final IApiListener<UploadImageResponse> loginApiListener, MultipartBody.Part image, int type) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.uploadImage(image, type))
                .subscribe(new SubscriberCallback<>(loginApiListener));
    }

    public Subscription updatePAN(PanUpdateRequest request, IApiListener<ProfileResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updatePAN(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription checkAadhar(CheckAadharRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.checkAadhar(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription updateAadhar(AadharUpdateRequest request, IApiListener<ProfileResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateAadhar(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getCaptcha(CaptchaRequest request, IApiListener<AadharCaptchaResponse> listener) {
        VerifiApiManger manager = VerifiApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCaptcha(request)).subscribe(new VerifiSubscriberCallback<>(listener));
    }

    public Subscription getAadhaarOTP(GetOtpRequest request, IApiListener<VerifiBaseResponse> listener) {
        VerifiApiManger manager = VerifiApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getAadhaarOTP(request)).subscribe(new VerifiSubscriberCallback<>(listener));
    }

    public Subscription verifyAadhaarOtp(VerifyOtpRequest request, IApiListener<VerifiBaseResponse> listener) {
        VerifiApiManger manager = VerifiApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyAadhaarOTP(request)).subscribe(new VerifiSubscriberCallback<>(listener));
    }

    public Subscription getAadhaarKYCDetails(AadhaarRequest request, IApiListener<AadharDetailsResponse> listener) {
        VerifiApiManger manager = VerifiApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getKYCDetails(request)).subscribe(new VerifiSubscriberCallback<>(listener));
    }

    public Subscription updateAadhaarOnServer(AadharUpdateRequest request, IApiListener<ProfileResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateAadhaar(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getWithdrawLimit(IApiListener<WIthdrawLimitResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getWithdrawLimit()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription verifyAadhaarOtpSurepass(VerifyOtpSurepass request, IApiListener<ProfileResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyAadharOTPSurepass(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription verifyBeneficiary(IApiListener<BaseResponse> listener, String beneficiaryId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyBeneficiary(beneficiaryId)).subscribe(new SubscriberCallback<>(listener));
    }

}