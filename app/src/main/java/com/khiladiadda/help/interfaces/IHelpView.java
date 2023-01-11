package com.khiladiadda.help.interfaces;

import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.HelpResponse;
import com.khiladiadda.network.model.ApiError;

public interface IHelpView {

    void onGetCategoryComplete(FaqCategoryResponse responseModel);

    void onGetCategoryFailure(ApiError error);

    void onHelpComplete(HelpResponse responseModel);

    void onHelpFailure(ApiError error);

}
