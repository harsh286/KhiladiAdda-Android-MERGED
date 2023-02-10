package com.khiladiadda.registration.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IRegistrationPresenter extends IBasePresenter {

    void validateData();

    void doRegister(String gmailId);

}