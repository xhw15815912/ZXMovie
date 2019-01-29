package movie.bw.com.movie.frag;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;
import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.DaoSession;
import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.Film_Comment;
import movie.bw.com.movie.p.Film_Comment_Presenter;

/**
 * 作者：夏洪武
 * 时间：2019/1/29.
 * 邮箱：
 * 说明：
 */
public class InputDialog extends Dialog implements CustomAdapt {
    private EditText et;
    private Button send;
    private Film_Comment_Presenter film_comment_presenter;
    private String s;

    public InputDialog(Context context) {
        super(context);
//        super(context, R.style.CustomDialog);
        init(context);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public Context mContext;
    public View mRootView;

    public void init(final Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        send=mRootView.findViewById(R.id.send);
        et=mRootView.findViewById(R.id.et);
        film_comment_presenter = new Film_Comment_Presenter(new CallBack());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession daoSession = DaoMaster.newDevSession(context, UserBeanDao.TABLENAME);
                UserBeanDao userBeanDao = daoSession.getUserBeanDao();
                List<UserBean> list = userBeanDao.queryBuilder()
                        .where(UserBeanDao.Properties.Register.eq(1))
                        .build()
                        .list();
                String sessionId = list.get(0).getSessionId();
                int userId = list.get(0).getUserId();
                String s1 = et.getText().toString();
                film_comment_presenter.request(userId,sessionId,s,s1);
            }
        });

        setContentView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
            if (EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
            }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void FilmNum(String s) {
        this.s=s;
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }


    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class CallBack implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getContext(),data.getMessage(),Toast.LENGTH_LONG).show();
                dismiss();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
