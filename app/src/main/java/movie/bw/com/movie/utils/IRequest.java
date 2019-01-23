package movie.bw.com.movie.utils;

import io.reactivex.Observable;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserInfo;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zxk
 * on 2019/1/22 19:25
 * QQ:666666
 * Describe:网络请求接口类
 */
public interface IRequest {

    @POST("user/v1/login")
    Observable<Result<UserInfo>> LOGIN(@Query("phone")String phone, @Query("pwd")String pwd);

}
