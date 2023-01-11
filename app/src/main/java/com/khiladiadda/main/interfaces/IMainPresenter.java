package com.khiladiadda.main.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.UpdateFavouriteRequest;

public interface IMainPresenter extends IBasePresenter {

    void getData();

    void updateFavourite(UpdateFavouriteRequest request);

}