package com.khiladiadda.ludo.unplayed.interfaces;

import android.content.ContentResolver;
import android.net.Uri;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoErrorRequest;
import com.khiladiadda.network.model.request.LudoResultRequest;

import java.io.File;

public interface ILudoErrorPresenter extends IBasePresenter {

    void uploadImage(Uri organizerImageUri, File organizerImageFile, ContentResolver contentResolver);

    void updateLudoResult(String contestId, LudoResultRequest request);

    void errorPlay(String contestId, LudoErrorRequest request);

}