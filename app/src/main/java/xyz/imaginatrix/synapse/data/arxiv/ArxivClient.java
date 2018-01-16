package xyz.imaginatrix.synapse.data.arxiv;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ArxivClient {

    public final static String API_BASE_URL = "http://export.arxiv.org/api/";

    // Precursors
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    // Interceptors
    private static HttpLoggingInterceptor httpLogInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC);


    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(httpLogInterceptor)) {
            httpClient.addInterceptor(httpLogInterceptor);
        }

        retrofitBuilder.client(httpClient.build());
        retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }

//    public static <S> S createApiService(Class<S> serviceClass, final String accessToken) {
//        if (!TextUtils.isEmpty(accessToken)) {
//            if (!httpClient.interceptors().contains(httpLogInterceptor)) {
//                httpClient.addInterceptor(httpLogInterceptor);
//            }
//
//            retrofitBuilder.client(httpClient.build());
//            retrofit = retrofitBuilder.build();
//        }
//
//        return retrofit.create(serviceClass);
//    }
}
