package com.khiladiadda.base.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.VersionResponse;

public interface IBaseView {

    void onVersionSuccess(VersionResponse response);

    void onVersionFailure(ApiError error);

}