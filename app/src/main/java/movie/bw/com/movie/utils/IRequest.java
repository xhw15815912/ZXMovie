package movie.bw.com.movie.utils;

import java.util.List;

import io.reactivex.Observable;
import movie.bw.com.movie.bean.Cinema;
import movie.bw.com.movie.bean.HotMovie;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.Recommend;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserInfo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zxk
 * on 2019/1/22 19:25
 * QQ:666666
 * Describe:网络请求接口类
 */
public interface IRequest {
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<UserInfo>> LOGIN(@Field("phone") String phone, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<Result> registerUser(
            @Field("nickName") String nickName,
            @Field("phone") String phone,
            @Field("pwd") String pwd,
            @Field("pwd2") String pwd2,
            @Field("sex") int sex,
            @Field("birthday") String birthday,
            @Field("imei") String imei,
            @Field("ua") String ua,
            @Field("screenSize") String screenSize,
            @Field("os") String os,
            @Field("email") String email
    );

    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<HotMovie>>> FINDHOT(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count
    );

    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<HotMovie>>> NOW(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count
    );

    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<HotMovie>>> Soon(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count
    );

    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<Recommend>>> findRecommendCinemas(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("page") int page,
                                                             @Query("count") int count);

    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<Recommend>>> findNearbyCinemas(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("longitude") String longitude,
                                                          @Query("latitude") String latitude,
                                                          @Query("page") int page,
                                                          @Query("count") int count);

    //查询影院
    @GET("cinema/v1/findAllCinemas")
    Observable<Result<Cinema>> findAllCinemas(
            @Query("page") int page,
            @Query("count") int count,
            @Query("cinemaName") String cinemaName
    );

    @GET("movie/v1/findMoviesDetail")
    Observable<Result<ParticularsBean>> Particulars(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("movieId") int movieId
    );

    //我的信息
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<MeBean>> getUserInfoByUserId(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId);
}
