package movie.bw.com.movie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.activity.ShowActivity;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.bean.UserInfo;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;

import movie.bw.com.movie.p.LoginPresenter;

public class MainActivity extends BaseActivity {


    @BindView(R.id.moble)
    EditText moble;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.eye)
    ImageView eye;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.remberpwd)
    CheckBox remberpwd;
    @BindView(R.id.zd)
    CheckBox zd;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.weixin)
    ImageView weixin;
    private LoginPresenter loginPresenter;
    private String s;
    private String password;
    private boolean canSee=false;
    private SharedPreferences share;
    private SharedPreferences.Editor edit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(new CallBack());
        share = getSharedPreferences("a", MODE_PRIVATE);
        if (share.getBoolean("isCk", true)) {
            remberpwd.setChecked(true);
            String m = share.getString("moble", "123");
            String p = share.getString("password", "123");
            this.moble.setText(m + "");
            pwd.setText(p + "");
        } else {
            remberpwd.setChecked(false);
        }
        initData();
    }
    //点击登录按钮
    @OnClick(R.id.login)
    public void Login(){
        startActivity(new Intent(this,ShowActivity.class));
        /*if (submit()){
            s = moble.getText().toString();
            password = this.pwd.getText().toString();
            loginPresenter.request(s,password);
        }*/

    }
    //微信登录
    @OnClick(R.id.weixin)
    public void LoginWeixin(){

    }
    //显示隐藏密码
    @OnClick(R.id.eye)
    public void SeePwd(){
        if (canSee==false){
            //如果是不能看到密码的情况下，
            pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            canSee=true;
        }else {
            //如果是能看到密码的状态下
            pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            canSee=false;
        }
    }
    private void initData() {

    }

    @Override
    protected void destoryData() {

    }


    private class CallBack implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
            movie.bw.com.movie.DaoSession daoSession = movie.bw.com.movie.DaoMaster.newDevSession(MainActivity.this, UserBeanDao.TABLENAME);
            UserBeanDao userBeanDao = daoSession.getUserBeanDao();
            if (data.getStatus().equals("0000")){
                String sessionId = data.getResult().getSessionId();
                int userId = data.getResult().getUserId();
                UserBean userBean = new UserBean(sessionId, userId, 1);
                userBeanDao.insert(userBean);

                s = moble.getText().toString();
                password = pwd.getText().toString();
                //判断是否点击记住密码
                if (remberpwd.isChecked()){
                    edit = share.edit();
                    edit.putString("moble",s);
                    edit.putString("password",password);
                    edit.putBoolean("isCk",true);
                    edit.commit();
                }else{
                    edit = share.edit();
                    edit.putBoolean("isCk",false);
                    edit.commit();
                }
            }
            Toast.makeText(MainActivity.this,data.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    private boolean submit() {
        // validate
        String mobleString = moble.getText().toString().trim();
        if (TextUtils.isEmpty(mobleString)) {
            Toast.makeText(this, "手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        String pwdString = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdString)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        // TODO validate success, do so     mething
        return true;

    }
}
