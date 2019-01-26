package movie.bw.com.movie.p;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;

/**
 * 作者：夏洪武
 * 时间：2019/1/26.
 * 邮箱：
 * 说明：
 */
public class Chose_Session_Presenter extends BasePresenter {
    public Chose_Session_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return iRequest.CHOSESESSION((int)args[0],(String)args[1]);
    }
}
