package com.khiladiadda.network;

import com.khiladiadda.network.model.ApiError;

public interface IApiListener<T> {

    void onSuccess(T response);

    void onError(ApiError error);
}