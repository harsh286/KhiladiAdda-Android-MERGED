package com.khiladiadda.clashx2.main.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CxBannerMainResponse;

public interface ICxBannerView {

    void onCxBannerComplete(CxBannerMainResponse responseModel);

    void onCxBannerFailure(ApiError error);
}
