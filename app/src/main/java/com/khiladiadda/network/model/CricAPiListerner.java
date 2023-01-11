package com.khiladiadda.network.model;

public interface CricAPiListerner<T> {
    void onSuccess(T response);

    void onError(CricApiError error);

}