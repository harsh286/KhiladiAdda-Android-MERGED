package com.khiladiadda.network;

import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.playerStats.model.PlayerProfileRes;

import java.lang.ref.WeakReference;

import rx.Subscriber;

public class CricPlayerSubsricberCallback<T> extends Subscriber<T> {

    private WeakReference<CricAPiListerner<T>> mApiListenerWeakReference;

    /**
     * Constructor of the class
     *
     * @param apiListenerWeakReference the listener to listen to API call {@link CricAPiListerner}
     */
    public CricPlayerSubsricberCallback(CricAPiListerner<T> apiListenerWeakReference) {
        this.mApiListenerWeakReference = new WeakReference<>(apiListenerWeakReference);
    }


    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable throwable) {
        if (mApiListenerWeakReference == null) {
            return;
        }
        CricAPiListerner<T> apiListener = mApiListenerWeakReference.get();
        if (apiListener == null) {
            return;
        }
        CricApiError error = new CricApiError();
        apiListener.onError(error);
    }

    @Override public void onNext(T response) {
        if (mApiListenerWeakReference == null) {
            return;
        }
        CricAPiListerner<T> apiListener = mApiListenerWeakReference.get();
        if (apiListener == null) {
            return;
        }
        PlayerProfileRes cricScorce = (PlayerProfileRes) response;
        apiListener.onSuccess(response);
    }

}