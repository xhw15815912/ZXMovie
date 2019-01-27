package movie.bw.com.movie.frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.DaoSession;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.activity.FeedbackActivity;
import movie.bw.com.movie.activity.MyInterestActivity;
import movie.bw.com.movie.activity.MyMessageActivity;
import movie.bw.com.movie.activity.SystemInfoActivity;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.MyInterestBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.MePresenter;
import movie.bw.com.movie.p.MyInterestPresenter;
import movie.bw.com.movie.p.UserSingnInPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFrag extends BaseFragment {

    private static String oldMsg;
    private static long time;
    @BindView(R.id.trumpet)
    ImageView trumpet;
    @BindView(R.id.headimage)
    SimpleDraweeView headimage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.my_chat)
    RelativeLayout myChat;
    @BindView(R.id.my_attention)
    RelativeLayout myAttention;
    @BindView(R.id.my_rccord)
    RelativeLayout myRccord;
    @BindView(R.id.my_feedback)
    RelativeLayout myFeedback;
    @BindView(R.id.my_version)
    RelativeLayout myVersion;
    @BindView(R.id.rccord)
    ImageView rccord;
    @BindView(R.id.image_logout)
    ImageView imageLogout;
    private UserSingnInPresenter singn;
    private MyInterestPresenter myInterestPresenter;
    private int userId;
    private String sessionId;
    private MePresenter mePresenter;
    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        if (USER!=null&&list.size()>0){
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }

        singn = new UserSingnInPresenter(new UserSingnIn());
        mePresenter = new MePresenter(new  MeData());
        mePresenter.request(userId, sessionId);
    }

    @OnClick({R.id.trumpet, R.id.headimage, R.id.my_chat, R.id.my_attention, R.id.my_rccord, R.id.my_feedback, R.id.my_version, R.id.sign_in, R.id.my_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trumpet:
                startActivity(new Intent(getContext(), SystemInfoActivity.class));
                break;
            case R.id.headimage:
                if (USER != null && list.size() > 0) {
                    Toast.makeText(getContext(), "已有用户", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
                break;
            case R.id.my_chat:
                Intent intent = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            //我的关注
            case R.id.my_attention:

                startActivity(new Intent(getContext(),MyInterestActivity.class));
                break;
            case R.id.my_rccord:

                break;
            case R.id.my_feedback:
                startActivity(new Intent(getContext(),FeedbackActivity.class));
                break;
            case R.id.my_version:

                break;
            case R.id.sign_in:

                singn.request(userId,sessionId);
                break;
            case R.id.my_logout:
                if (USER != null && list.size() > 0) {
                    DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserBeanDao.TABLENAME);
                    UserBeanDao userBeanDao = daoSession.getUserBeanDao();
                    userBeanDao.deleteAll();
                    Intent intent1 = new Intent(getActivity(), MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent1);
                }else {

                        // 显示内容一样时，只有间隔时间大于5秒时才显示
                        if (System.currentTimeMillis() - time >5000) {
                            Toast.makeText(getContext(), "你还未登录过！！！", Toast.LENGTH_LONG).show();
                            time = System.currentTimeMillis();
                        }

                }

                break;
        }
    }
    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,60000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

    private class UserSingnIn implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class MeData implements DataCall<Result<MeBean>> {
        @Override
        public void success(Result<MeBean> data) {
            MeBean bean = data.getResult();
            name.setText(bean.getNickName());
            headimage.setImageURI(bean.getHeadPic());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
