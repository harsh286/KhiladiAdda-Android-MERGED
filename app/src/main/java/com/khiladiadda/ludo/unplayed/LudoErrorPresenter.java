package com.khiladiadda.ludo.unplayed;

import android.content.ContentResolver;
import android.net.Uri;

import com.khiladiadda.ludo.unplayed.interfaces.ILudoErrorPresenter;
import com.khiladiadda.ludo.unplayed.interfaces.ILudoErrorView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoErrorRequest;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.response.UploadImageResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;

public class LudoErrorPresenter implements ILudoErrorPresenter {

    private ILudoErrorView mView;
    private LudoErrorInteractor mInteractor;
    private Subscription mSubscription;

    public LudoErrorPresenter(ILudoErrorView mView) {
        this.mView = mView;
        mInteractor = new LudoErrorInteractor();
    }

    @Override
    public void uploadImage(Uri organizerImageUri, File organizerImageFile, ContentResolver contentResolver) {
        RequestBody bannerImageRequestBody = RequestBody.create(MediaType.parse(contentResolver.getType(organizerImageUri)), organizerImageFile);
        MultipartBody.Part bannerImagePart = MultipartBody.Part.createFormData("file", organizerImageFile.getName(), bannerImageRequestBody);
        mSubscription = mInteractor.uploadLudoImage(mUploadImageApiListener, bannerImagePart);
    }

    @Override
    public void updateLudoResult(String contestId, LudoResultRequest resultRequest) {
        mSubscription = mInteractor.ludoResult(contestId, resultRequest, mLudoResultAPIListener);
    }

    @Override
    public void errorPlay(String contestId, LudoErrorRequest resultRequest) {
        mSubscription = mInteractor.ludoError(contestId, resultRequest, mLudoErrorAPIListener);
    }

    private IApiListener<UploadImageResponse> mUploadImageApiListener = new IApiListener<UploadImageResponse>() {
        @Override
        public void onSuccess(UploadImageResponse response) {
            mView.onUploadImageComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUploadImageFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLudoResultAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLudoResultSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLudoResultFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLudoErrorAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLudoErrorSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLudoErrorFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
