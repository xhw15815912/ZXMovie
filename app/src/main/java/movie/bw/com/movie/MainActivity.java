package movie.bw.com.movie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.activity.RegiterActivity;
import movie.bw.com.movie.activity.ShowActivity;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.bean.UserInfo;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;

import movie.bw.com.movie.p.LoginPresenter;
import movie.bw.com.movie.utils.EncryptUtil;

import static java.security.AccessController.getContext;


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
        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
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

        eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        pwd.setSelection(pwd.getText().length());
                        break;
                    case MotionEvent.ACTION_UP:
                        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        pwd.setSelection(pwd.getText().length());

                        break;
                }
                return true;
            }
        });
        initKey();
    }

    private void initKey() {
        moble.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null){
                        systemService.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                          }
                    }
            }
        });
        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null){
                        systemService.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
    }

    //点击登录按钮
    @OnClick(R.id.login)
    public void Login(){
        //startActivity(new Intent(this,ShowActivity.class));

         if (submit()){
            s = moble.getText().toString();
            password = pwd.getText().toString();

            loginPresenter.request(s,EncryptUtil.encrypt(password));
        }
    }
    //微信登录
    @OnClick(R.id.weixin)
    public void LoginWeixin(){

    }
    @OnClick(R.id.regist)
    public void Regirst(){
         startActivity(new Intent(this,RegiterActivity.class));

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
            Toast.makeText(MainActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")){
                String sessionId = data.getResult().getSessionId();
                int userId = data.getResult().getUserId();
               // EventBus.getDefault().postSticky(new UserBean(sessionId,userId,1));
                List<UserBean> userBeans = userBeanDao.loadAll();
                if (userBeans.size()>0){
                    userBeanDao.deleteAll();
                }
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


                startActivity(new Intent(MainActivity.this,ShowActivity.class));
                finish();
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
