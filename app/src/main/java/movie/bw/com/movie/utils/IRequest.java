package movie.bw.com.movie.utils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import movie.bw.com.movie.bean.Chose_Session_Bean;
import movie.bw.com.movie.bean.Cinema;
import movie.bw.com.movie.bean.DetailsofcinemaBean;
import movie.bw.com.movie.bean.FilmCommentBean;
import movie.bw.com.movie.bean.FilmInFoBean;
import movie.bw.com.movie.bean.FindCommentReplyBean;
import movie.bw.com.movie.bean.HotMovie;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.MoviewCommentBean;
import movie.bw.com.movie.bean.Pay_Chose_Film_Bean;
import movie.bw.com.movie.bean.MyCinemaBean;
import movie.bw.com.movie.bean.MyInterestBean;
import movie.bw.com.movie.bean.Recommend;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.SystemInfoBean;
import movie.bw.com.movie.bean.TheticketrecordBean;
import movie.bw.com.movie.bean.UserInfo;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("user/v1/weChatBindingLogin")
    Observable<Result<UserInfo>> WX_LOGIN(@Field("code") String code);

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

    //查询热门影片
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<HotMovie>>> FINDHOT(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count
    );

    //查看正在上映的电影
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<HotMovie>>> NOW(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count
    );

    //查看即将上映
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

    //查询影片详情
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<ParticularsBean>> Particulars(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("movieId") int movieId
    );

    //查询影片评论
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<MoviewCommentBean>>> MoviewComment(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("movieId") int movieId,
            @Query("page") int page,
            @Query("count") int count
    );

    //我的信息
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<MeBean>> getUserInfoByUserId(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId);

    //根据影片选择影院
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<Pay_Chose_Film_Bean>>> PayCHOSEFILM(@Query("movieId") int movieId);

    //查询该影院的此影片的排片时间
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<Chose_Session_Bean>>> CHOSESESSION(@Query("cinemasId") int cinemasId, @Query("movieId") String movieId);

    //影院详情
    @GET("cinema/v1/findCinemaInfo")
    Observable<Result<FilmInFoBean>> FilmInfo(@Header("userId") int userId,
                                              @Header("sessionId") String sessionId,
                                              @Query("cinemaId") int cinemaId);

    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId);

    //
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<MyInterestBean>>> findMoviePageList(@Header("userId") int userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") int page,
                                                               @Query("count") int count
    );

    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<MyCinemaBean>>> findCinemaPageList(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId,
                                                              @Query("page") int page,
                                                              @Query("count") int count
    );

    @FormUrlEncoded
    @POST("tool/v1/verify/recordFeedBack")
    Observable<Result> recordFeedBack(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("content") String content);

    @GET("tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<SystemInfoBean>>> findAllSysMsgList(@Header("userId") int userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") int page,
                                                               @Query("count") int count);

    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<HotMovie>>> findMovieListByCinemaId(
            @Query("cinemaId") int cinemaId);

    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<Chose_Session_Bean>>> findMovieScheduleList(@Query("cinemasId") int cinemasId,
                                                                       @Query("movieId") int movieId);

    //影院详情
    @GET("cinema/v1/findCinemaInfo")
    Observable<Result<DetailsofcinemaBean>> findCinemaInfo(@Header("userId") int userId,
                                                           @Header("sessionId") String sessionId,
                                                           @Query("cinemaId") int cinemaId);

    //影院详情
    @GET("cinema/v1/findCinemaInfo")
    Observable<Result<DetailsofcinemaBean>> FilMINFO(@Query("cinemaId") String cinemaId);

    //查询影片评论
    @GET("cinema/v1/findAllCinemaComment")
    Observable<Result<List<FilmCommentBean>>> FilmComment(
            @Query("cinemaId") String cinemaId,
            @Query("page") int page,
            @Query("count") int count
    );

    //修改密码
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserPwd")
    Observable<Result> modifyUserPwd(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                     @Field("oldPwd") String oldPwd,
                                     @Field("newPwd") String newPwd,
                                     @Field("newPwd2") String newPwd2);

    //下单
    @FormUrlEncoded
    @POST("movie/v1/verify/buyMovieTicket")
    Observable<Result> buyMovieTicket(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("scheduleId") int scheduleId,
                                      @Field("amount") int amount,
                                      @Field("sign") String sign);

    //微信支付
    @FormUrlEncoded
    @POST("movie/v1/verify/pay")
    Observable<Result> WxPay(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("payType") int payType,
                             @Field("orderId") String orderId);

    //关注电影
    @GET("movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId
    );

    //取消关注电影
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId
    );

    //关注影院
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                    @Query("cinemaId") int cinemaId
    );

    //取消关注影院
    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                          @Query("cinemaId") int cinemaId
    );
//    //头像上传
//    @FormUrlEncoded
//    @POST("user/v1/verify/uploadHeadPic")
//    Observable<Result> HeadImage(@Header("userId") int userId,
//                             @Header("sessionId") String sessionId,
//                             @Field("image") File image);
    //修改用户头像

    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> HeadImage(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                 @Body MultipartBody body);

    @GET("user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<TheticketrecordBean>>> findUserBuyTicketRecordList(
            @Header("userId") int userId, @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count,
            @Query("status") int status
    );

    //影院评论
    @FormUrlEncoded
    @POST("cinema/v1/verify/cinemaComment")
    Observable<Result> FilmComment(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Field("cinemaId") String cinemaId,
                                   @Field("commentContent") String commentContent
    );

    //影院评论
    @FormUrlEncoded
    @POST("movie/v1/verify/movieComment")
    Observable<Result> MovieComment(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("movieId") String movieId,
                                    @Field("commentContent") String commentContent
    );

    //影院评论点赞
    @FormUrlEncoded
    @POST("cinema/v1/verify/cinemaCommentGreat")
    Observable<Result> cinemaCommentGreat(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Field("commentId") int commentId);

    //影片评论点赞
    @FormUrlEncoded
    @POST("movie/v1/verify/movieCommentGreat")
    Observable<Result> movieCommentGreat(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Field("commentId") int commentId
    );

    //添加用户对评论的回复
    @FormUrlEncoded
    @POST("movie/v1/verify/commentReply")
    Observable<Result> commentReply(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Field("commentId") int commentId,
            @Field("replyContent") String replyContent
    );
    //最新版本
    @GET("tool/v1/findNewVersion")
    Observable<Result> findNewVersion(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Header("ak") String ak
    );
    //影片评论回复列表
    @GET("movie/v1/findCommentReply")
    Observable<Result<List<FindCommentReplyBean>>> findCommentReply(
            @Header("userId") int userId, @Header("sessionId") String sessionId,
            @Query("commentId") int commentId,
            @Query("page") int page,
            @Query("count") int count

    );
}
