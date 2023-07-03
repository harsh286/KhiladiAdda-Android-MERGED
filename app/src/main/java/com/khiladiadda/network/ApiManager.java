package com.khiladiadda.network;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.khiladiadda.BuildConfig;
import com.khiladiadda.KhiladiAddaApp;
import com.khiladiadda.R;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
public class ApiManager {
    private static ApiManager sInstance;

    /**
     * Returns instance of the ApiManager class
     *
     * @return instance of the ApiManager class
     */
    public static ApiManager getInstance() {
        if (sInstance == null) {
            synchronized (ApiManager.class) {
                sInstance = new ApiManager();
            }
        }
        return sInstance;
    }

    /**
     * Empty private constructor of the ApiManager class
     */
    private ApiManager() {

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
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
        return new Retrofit.Builder().baseUrl(AppUtilityMethods.getURL(AppConstant.URL)).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
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
        return new Retrofit.Builder().baseUrl(baseUrl).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    /**
     * Returns the custom OKHttpClient object
     *
     * @return custom OKHttpClient object
     */
    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        initSSL(KhiladiAddaApp.getInstance(), httpClient);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder request = null;
            try {
                request = original.newBuilder().
                        header(AppConstant.TEXT_ENCRYPTION, AppUtilityMethods.secure()).
                        header(AppConstant.TEXT_CONTENT_TYPE, AppConstant.TEXT_CONTENT_TYPE_VALUE).
                        header(AppConstant.TEXT_TOKEN, AppSharedPreference.getInstance().getSessionToken()).
                        header(AppConstant.TEXT_VERSION_NO, AppUtilityMethods.getVersion()).
                        header(AppConstant.TEXT_KEY_TYPE, String.valueOf(AppConstant.FROM_WON)).
                        header(AppConstant.TEXT_ENC_KEY, AppConstant.ENC_KEY).
                        header(AppConstant.TEXT_KEY_NEW, AppConstant.KEY_NEW).
                        method(original.method(), original.body());
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            assert request != null;
            return chain.proceed(request.build());
        }).addInterceptor(loggingInterceptor);
        httpClient.readTimeout(AppConstant.TIME_OUT,TimeUnit.SECONDS);
        httpClient.connectTimeout(AppConstant.TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(AppConstant.TIME_OUT, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);
        return httpClient.build();
    }
    private void initSSL(Context context, OkHttpClient.Builder httpClientBuilder) {
        SSLContext sslContext = null;
          try{
            if(BuildConfig.MODE){
                sslContext=createCertificate(context.getResources().openRawResource(R.raw.uat_new));
            }else{
                sslContext=createCertificate(context.getResources().openRawResource(R.raw.prod_new));
            }

        } catch (CertificateException | IOException | KeyStoreException | KeyManagementException |
                 NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), systemDefaultTrustManager());
        }
    }

    private SSLContext createCertificate(InputStream trustedCertificateIS) throws CertificateException, IOException, KeyStoreException, KeyManagementException, NoSuchAlgorithmException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try {
            ca = cf.generateCertificate(trustedCertificateIS);
        } finally {
            trustedCertificateIS.close();
        }
        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;
    }

    private X509TrustManager systemDefaultTrustManager() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }
    }

}