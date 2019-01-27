package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.MePresenter;

public class MyMessageActivity extends BaseActivity {


    @BindView(R.id.user_avatar)
    SimpleDraweeView userAvatar;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.user_sex)
    TextView userSex;
    @BindView(R.id.date_of_birth)
    TextView dateOfBirth;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.relative_1)
    RelativeLayout relative1;
    @BindView(R.id.user_postbox)
    TextView userPostbox;
    @BindView(R.id.reset_passwords)
    ImageView resetPasswords;
    private MePresenter mePresenter;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        if (USER!=null&&list.size()>0){
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }

        mePresenter = new MePresenter(new MeData());
        mePresenter.request(userId, sessionId);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    private class MeData implements DataCall<Result<MeBean>> {
        @Override
        public void success(Result<MeBean> result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(MyMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                MeBean meBean = result.getResult();

                userAvatar.setImageURI(meBean.getHeadPic());
                nickname.setText(meBean.getNickName());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String s = format1.format(meBean.getBirthday());
                if (meBean.getSex() == 1) {
                    userSex.setText("男");
                } else {
                    userSex.setText("女");
                }
                dateOfBirth.setText(s);
                userPhone.setText(meBean.getPhone());
                userPostbox.setText(meBean.getEmail());
            } else {
                Toast.makeText(MyMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
