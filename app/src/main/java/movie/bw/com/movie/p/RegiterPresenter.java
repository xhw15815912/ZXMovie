package movie.bw.com.movie.p;

import android.util.Log;

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
public class RegiterPresenter extends BasePresenter {
    public RegiterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NotWorkUtils.getNotWorkUtils().create(IRequest.class);
        Log.e("waqe", (String) args[0] + "===");
        return iRequest.registerUser((String) args[0], (String) args[1], (String) args[2], (String) args[3], (int) args[4], (String) args[5], (String) args[6], (String) args[7], (String) args[8], (String) args[9], (String) args[10]);
    }
}
