package com.khiladiadda.splash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.VersionResponse;

public interface ISplashView {

    void onMasterComplete(MasterResponse responseModel);

    void onMasterFailure(ApiError error);

    void onVersionSuccess(VersionResponse response);

    void onVersionFailure(ApiError error);

}