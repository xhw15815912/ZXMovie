package movie.bw.com.movie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import movie.bw.com.movie.base.BaseActivity;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        BaseActivity.getForegroundActivity();
        
    }

    @Override
    protected void destoryData() {

    }


}
