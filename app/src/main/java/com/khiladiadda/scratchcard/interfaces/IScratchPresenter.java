package com.khiladiadda.scratchcard.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IScratchPresenter extends IBasePresenter {
    void getScratchCard(int type);

    void applyScratchCard(String scartchid);
}
