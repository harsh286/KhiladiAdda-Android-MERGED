package com.khiladiadda.network;

import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SurepassVerifiApiManger {

    private static SurepassVerifiApiManger sInstance;

    public static SurepassVerifiApiManger getInstance() {
        if (sInstance == null) {
            synchronized (SurepassVerifiApiManger.class) {
                sInstance = new SurepassVerifiApiManger();
            }
        }
        return sInstance;
    }

    private SurepassVerifiApiManger() {

    }

    public <T> Observable<T> createObservable(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    private Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ApiManager.UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
        return new Retrofit.Builder().baseUrl(AppConstant.SUREPASS_VERIFI_AADHAR_URL).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    static class UnixEpochDateTypeAdapter extends TypeAdapter<Date> {

        private static final TypeAdapter<Date> unixEpochDateTypeAdapter = new SurepassVerifiApiManger.UnixEpochDateTypeAdapter();

        private UnixEpochDateTypeAdapter() {
        }

        static TypeAdapter<Date> getUnixEpochDateTypeAdapter() {
            return unixEpochDateTypeAdapter;
        }

        @Override
        public Date read(final JsonReader in) throws IOException {
            // this is where the conversion is performed
            return new Date(in.nextLong());
        }

        @Override
        @SuppressWarnings("resource")
        public void write(final JsonWriter out, final Date value) throws IOException {
            // write back if necessary or throw UnsupportedOperationException
            out.value(value.getTime());
        }

    }

    public ApiService createService() {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(ApiService.class);
    }

    public ApiService createService(String baseUrl) {
        Retrofit retrofit = getRetrofit(baseUrl);
        return retrofit.create(ApiService.class);
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder request = original.newBuilder().
                    addHeader(AppConstant.SUREPASS_TOKEN_NAME, AppConstant.SUREPASS_TOKEN_NAME_VALUE + AppConstant.SUREPASS_TOKEN_VALUE).
                    method(original.method(), original.body());
            return chain.proceed(request.build());
        }).addInterceptor(loggingInterceptor);
        httpClient.readTimeout(AppConstant.SUREPASS_TIME_OUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(AppConstant.SUREPASS_TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(AppConstant.SUREPASS_TIME_OUT, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);
        return httpClient.build();
    }

}