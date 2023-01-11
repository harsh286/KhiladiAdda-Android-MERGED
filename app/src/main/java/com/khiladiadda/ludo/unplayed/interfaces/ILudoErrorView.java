package com.khiladiadda.ludo.unplayed.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;

public interface ILudoErrorView {

    void onUploadImageComplete(UploadImageResponse response);

    void onUploadImageFailure(ApiError error);

    void onLudoResultSuccess(BaseResponse response);

    void onLudoResultFailure(ApiError error);

    void onLudoErrorSuccess(BaseResponse response);

    void onLudoErrorFailure(ApiError error);
}
