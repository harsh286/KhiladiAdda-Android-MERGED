package com.khiladiadda.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.khiladiadda.base.ApiResponseEvent;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.ErrorBody;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import retrofit2.HttpException;
import rx.Subscriber;

public class SubscriberCallback<T> extends Subscriber<T> {

    private static final Logger LOGGER = Logger.getLogger(SubscriberCallback.class);

    private WeakReference<IApiListener<T>> mApiListenerWeakReference;

    /**
     * Constructor of the class
     *
     * @param apiListenerWeakReference the listener to listen to API call {@link IApiListener}
     */
    public SubscriberCallback(IApiListener<T> apiListenerWeakReference) {
        this.mApiListenerWeakReference = new WeakReference<IApiListener<T>>(apiListenerWeakReference);
    }

    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable throwable) {
        if (mApiListenerWeakReference == null) {
            return;
        }
        IApiListener<T> apiListener = mApiListenerWeakReference.get();
        if (apiListener == null) {
            return;
        }
        ApiError error;
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;

            ErrorBody errorBody = null;
            try {
                if (exception.response().errorBody() != null) {
                    errorBody = new Gson().fromJson(exception.response().errorBody().string(), ErrorBody.class);
                }
                if (errorBody == null) {
                    error = new ApiError(HttpURLConnection.HTTP_NOT_FOUND, throwable.getMessage());
                } else {
                    error = new ApiError(exception.code(), errorBody.getMessage());
                }
            } catch (JsonSyntaxException e) {
                error = new ApiError(402, e.getMessage());
            } catch (IOException e) {
                error = new ApiError(500, e.getMessage());
            }
        } else {
            error = new ApiError(HttpURLConnection.HTTP_NOT_FOUND, AppConstant.TEXT_INTERNET_ISSUE);
        }
        apiListener.onError(error);
    }

    @Override public void onNext(T response) {
        if (mApiListenerWeakReference == null) {
            return;
        }
        IApiListener<T> apiListener = mApiListenerWeakReference.get();
        if (apiListener == null) {
            return;
        }

        BaseResponse baseResponse = (BaseResponse) response;

        if (baseResponse.getSystemInfo() == AppConstant.FROM_SUCCESS) {
            apiListener.onSuccess(response);
        } else {
            EventBus.getDefault().post(new ApiResponseEvent(baseResponse.getMessage(), baseResponse.getSystemInfo()));
        }
    }
}