package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IWalletCashbackPresenter extends IBasePresenter {

    void validateData();

    void getProfile();

    void getVersionDetails();

    void getInvoice(String id);

    void applyCoupon(String couponCode);

}