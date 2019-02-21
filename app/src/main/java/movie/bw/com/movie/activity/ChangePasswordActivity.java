package movie.bw.com.movie.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.ChangePwdPresenter;
import movie.bw.com.movie.utils.EncryptUtil;

public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.text_lpwd)
    EditText textLpwd;
    @BindView(R.id.text_xpwd)
    EditText textXpwd;
    @BindView(R.id.text_xxpwd)
    EditText textXxpwd;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.finif)
    ImageView finif;
    private ChangePwdPresenter changePwdPresenter;
    private String sessionId;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        if (list != null && list.size() > 0) {
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }
        changePwdPresenter = new ChangePwdPresenter(new Change());
        textLpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
        textXpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
        textXxpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.btn_commit, R.id.finif})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String lpwd = textLpwd.getText().toString();
                String xpwd = textXpwd.getText().toString();
                String xxpwd = textXxpwd.getText().toString();
                if (lpwd.equals(xpwd)) {
                    Toast.makeText(ChangePasswordActivity.this, "新密码与旧密码相同请更改", Toast.LENGTH_LONG).show();
                } else {
                    if (xxpwd.equals(xpwd)) {
                        changePwdPresenter.request(userId, sessionId, EncryptUtil.encrypt(lpwd), EncryptUtil.encrypt(xpwd), EncryptUtil.encrypt(xxpwd));
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "两次新密码不相同请核对", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.finif:
                finish();
                break;
        }

    }

    private class Change implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")) {
                Toast.makeText(ChangePasswordActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ChangePasswordActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
