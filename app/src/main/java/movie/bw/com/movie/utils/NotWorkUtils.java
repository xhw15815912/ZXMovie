package movie.bw.com.movie.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zxk
 * on 2019/1/22 19:14
 * QQ:666666
 * Describe:网络请求工具类
 */
public class NotWorkUtils {
    //单例模式，饿汉式
    private static NotWorkUtils notWorkUtils = new NotWorkUtils();
    private final static String BASE_URL = "http://mobile.bwstudent.com/movieApi/";
    private Retrofit retrofit;

    private NotWorkUtils() {
        init();
    }

    public static NotWorkUtils getNotWorkUtils() {
        if (notWorkUtils == null) {
            notWorkUtils = new NotWorkUtils();
        }
        return notWorkUtils;
    }

    private void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
