package movie.bw.com.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.RegiterPresenter;
import movie.bw.com.movie.utils.EncryptUtil;


public class RegiterActivity extends BaseActivity {


    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.image_name)
    ImageView imageName;

    @BindView(R.id.image_sex)
    ImageView imageSex;
    @BindView(R.id.edit_dateofbirth)
    TextView editDateofbirth;
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
    @BindView(R.id.nan)
    RadioButton nan;
    @BindView(R.id.nv)
    RadioButton nv;
    @BindView(R.id.rag)
    RadioGroup rag;
    private int gender;
    private RegiterPresenter regiterPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_regiter;
    }

    @Override
    protected void initView() {
        regiterPresenter = new RegiterPresenter(new DataRegiter());
        initKey();
    }

    private void initKey() {
        final InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {

                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
            editDateofbirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        if (systemService != null) {
                            systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }

                }
            });

        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
        editPostbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
        editLoginpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
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


    @OnClick(R.id.btn_regiter)
    public void onViewClicked() {
        String reg_nickname = nickname.getText().toString();
        String reg_loginpassword = editLoginpassword.getText().toString();
        String reg__phone = editPhone.getText().toString();
        String reg_birth = editDateofbirth.getText().toString();
        String reg_box = editPostbox.getText().toString();
        String date = editDateofbirth.getText().toString();
        regiterPresenter.request(reg_nickname, reg__phone, EncryptUtil.encrypt(reg_loginpassword), EncryptUtil.encrypt(reg_loginpassword), gender, date, "123456", "小米", "5.0", "android", reg_box);

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    @OnClick({R.id.nan, R.id.nv, R.id.rag,R.id.edit_dateofbirth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nan:
                gender = 1;
                break;
            case R.id.nv:
                gender = 2;
                break;
            case R.id.rag:
                break;
            case R.id.edit_dateofbirth:
                TimePickerView pvTime = new TimePickerView.Builder(RegiterActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        editDateofbirth.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字

                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime.show();
                break;
        }
    }



    private class DataRegiter implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(RegiterActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegiterActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegiterActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
