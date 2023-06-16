package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.BajajPayRemainingRequest;
import com.khiladiadda.network.model.request.LinkBajajWalletRequest;

public interface IWalletCashbackPresenter extends IBasePresenter {

    void validateData();

    void getProfile();

    void  getRemainingLimit();


    void getVersionDetails();

    void getInvoice(String id);

    void applyCoupon(String couponCode);

}