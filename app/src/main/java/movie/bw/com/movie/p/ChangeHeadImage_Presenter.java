package movie.bw.com.movie.p;

import java.io.File;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;
import okhttp3.MultipartBody;

/**
 * Created by zxk
 * on 2019/1/28 15:59
 * QQ:666666
 * Describe:更换头像  Presenter
 */
public class ChangeHeadImage_Presenter extends BasePresenter {
    public ChangeHeadImage_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return null;
    }
}
