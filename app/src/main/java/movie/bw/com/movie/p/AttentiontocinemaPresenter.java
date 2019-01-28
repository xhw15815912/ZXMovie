package movie.bw.com.movie.p;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;

/**
 * 作者：夏洪武
 * 时间：2019/1/22.
 * 邮箱：
 * 说明：
 */
public class AttentiontocinemaPresenter extends BasePresenter {
    public AttentiontocinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return iRequest.followCinema((int)args[0],(String)args[1],(int)args[2]);
    }
}
