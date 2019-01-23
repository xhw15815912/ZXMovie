package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.R;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.bean.UserInfo;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.RegiterPresenter;

public class RegiterActivity extends BaseActivity {


    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.image_name)
    ImageView imageName;
    @BindView(R.id.edit_sex)
    EditText editSex;
    @BindView(R.id.image_sex)
    ImageView imageSex;
    @BindView(R.id.edit_dateofbirth)
    EditText editDateofbirth;
    @BindView(R.id.image_dateofbirth)
    ImageView imageDateofbirth;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.image_phone)
    ImageView imagePhone;
    @BindView(R.id.edit_postbox)
    EditText editPostbox;
    @BindView(R.id.image_postbox)
    ImageView imagePostbox;
    @BindView(R.id.edit_loginpassword)
    EditText editLoginpassword;
    @BindView(R.id.image_loginpassword)
    ImageView imageLoginpassword;
    @BindView(R.id.btn_regiter)
    Button btnRegiter;
    private RegiterPresenter regiterPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regiter;
    }

    @Override
    protected void initView() {
        regiterPresenter = new RegiterPresenter(new DataRegiter());
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_regiter)
    public void onViewClicked() {
        String reg_nickname = nickname.getText().toString();
        String reg_sex = editSex.getText().toString();
        String reg_loginpassword = editLoginpassword.getText().toString();
        String reg__phone = editPhone.getText().toString();
        String reg_birth = editDateofbirth.getText().toString();
        String reg_box = editPostbox.getText().toString();
        regiterPresenter.request("123",reg_nickname, reg_sex, reg_birth, reg__phone, reg_box, reg_loginpassword);

    }

    private class DataRegiter implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
         if (data.getStatus().equals("0000")){
             Toast.makeText(RegiterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(RegiterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
         }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
