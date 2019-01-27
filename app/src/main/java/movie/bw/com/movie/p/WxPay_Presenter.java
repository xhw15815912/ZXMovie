package movie.bw.com.movie.p;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NotWorkUtils;

/**
 * Created by zxk
 * on 2019/1/27 19:52
 * QQ:666666
 * Describe:
 */
public class WxPay_Presenter extends BasePresenter {
    public WxPay_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return iRequest.WxPay((int)args[0],(String) args[1],(int)args[2],(String)args[3]);
    }
}
