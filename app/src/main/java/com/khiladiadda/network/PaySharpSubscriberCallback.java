package com.khiladiadda.network;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.CricAPiListerner;

import java.lang.ref.WeakReference;

import rx.Subscriber;

public class PaySharpSubscriberCallback<T> extends Subscriber<T> {
    // private static final Logger LOGGER = Logger.getLogger(SubscriberCallback.class);

    private WeakReference<IApiListener<T>> mApiListenerWeakReference;

    /**
     * Constructor of the class
     *
     * @param apiListenerWeakReference the listener to listen to API call {@link CricAPiListerner}
     */
    public PaySharpSubscriberCallback(IApiListener<T> apiListenerWeakReference) {
        this.mApiListenerWeakReference = new WeakReference<>(apiListenerWeakReference);
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
        ApiError error = new ApiError(100, "");
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
        apiListener.onSuccess(response);
    }

}