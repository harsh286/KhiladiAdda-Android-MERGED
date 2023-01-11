package com.khiladiadda.login.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.login.TrueCallerRequest;

public interface ITrueCallerPresenter extends IBasePresenter {

    void callTrueCallerApi(TrueCallerRequest request);
}
