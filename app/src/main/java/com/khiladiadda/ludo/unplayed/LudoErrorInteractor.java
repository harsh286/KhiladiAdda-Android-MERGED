package com.khiladiadda.ludo.unplayed;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoErrorRequest;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.response.UploadImageResponse;

import okhttp3.MultipartBody;
import rx.Subscription;

public class LudoErrorInteractor {

    Subscription uploadLudoImage(final IApiListener<UploadImageResponse> listener, MultipartBody.Part image) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.uploadImage(image, 3)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription ludoResult(String contestId, LudoResultRequest ludoRequest, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.ludoResultResponse(contestId, ludoRequest)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription ludoError(String contestId, LudoErrorRequest ludoRequest, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.ludoError(contestId, ludoRequest)).subscribe(new SubscriberCallback<>(listener));
    }

}