package movie.bw.com.movie.p;

import io.reactivex.Observable;
import movie.bw.com.movie.base.BasePresenter;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.utils.IRequest;
import movie.bw.com.movie.utils.NetWorkReceiver;
import movie.bw.com.movie.utils.NotWorkUtils;

/**
 * 作者：夏洪武
 * 时间：2019/2/21.
 * 邮箱：
 * 说明：
 */
public class FindCommentReplyPresenter extends BasePresenter {
    public FindCommentReplyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        return iRequest.findCommentReply((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
