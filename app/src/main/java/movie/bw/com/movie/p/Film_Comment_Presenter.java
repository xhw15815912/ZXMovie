package movie.bw.com.movie.p;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;

/**
 * 作者：夏洪武
 * 时间：2019/1/29.
 * 邮箱：
 * 说明：
 */
public class Film_Comment_Presenter extends BasePresenter {
    public Film_Comment_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return iRequest.FilmComment((int)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}
