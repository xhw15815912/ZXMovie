package movie.bw.com.movie.p;

import java.io.File;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * 作者：夏洪武
 * 时间：2019/1/30.
 * 邮箱：
 * 说明：
 */
public class Change_UserHead_Presenter extends BasePresenter {
    public Change_UserHead_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("headPath", (String)args[2]);
        File file = new File((String) args[2]);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"),
                        file));

        return iRequest.HeadImage((int)args[0],(String)args[1],builder.build());
    }
}
