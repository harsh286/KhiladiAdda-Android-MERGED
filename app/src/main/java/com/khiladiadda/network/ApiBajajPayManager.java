package com.khiladiadda.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.khiladiadda.utility.AppConstant;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The API manager class of the application
 */
public class ApiBajajPayManager {
    private static ApiBajajPayManager sInstance;
    /**
     * Returns instance of the ApiManager class
     *
     * @return instance of the ApiManager class
     */
    public static ApiBajajPayManager getInstance(){
        if (sInstance==null) {
            synchronized (ApiBajajPayManager.class){
                sInstance=new ApiBajajPayManager();
            }
        }
        return sInstance;
    }

    /**
     * Empty private constructor of the ApiManager class
     */
    private ApiBajajPayManager() {

    }

    /**
     * Used to create RX observable to hit the API
     *
     * @param observable the generic observable
     * @param <T>        the generic type T
     * @return the observable
     */
    public <T> Observable<T> createObservable(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    /**
     * Used to build an return the retrofit object use to call the API
     *
     * @return the retrofit object use to call the API
     */
    private Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ApiManager.UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
        return new Retrofit.Builder().baseUrl(AppConstant.BASE_BJAZ_URL).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    static class UnixEpochDateTypeAdapter extends TypeAdapter<Date> {

        private static final TypeAdapter<Date> unixEpochDateTypeAdapter = new UnixEpochDateTypeAdapter();

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

    /**
     * Creates and returns the ApiService instance to call the APIs
     *
     * @return the ApiService instance
     */
      public ApiService createService() {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(ApiService.class);
     }
    public ApiService createService(String baseUrl) {
        Retrofit retrofit = getRetrofit(baseUrl);
        return retrofit.create(ApiService.class);
    }
    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }
    /**
     * Returns the custom OKHttpClient object
     *
     * @return custom OKHttpClient object
     */
    private OkHttpClient getHttpClient(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(AppConstant.TIME_OUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(AppConstant.TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(AppConstant.TIME_OUT, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);
        return httpClient.build();
    }
}