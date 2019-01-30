package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;

import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.base.BaseActivity;

public class StartActivity extends BaseActivity {
    private int time = 3;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;

            if (time == 0) {
                if (USER != null && list.size() > 0) {
                    startActivity(new Intent(StartActivity.this, GuidePageActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(StartActivity.this, GuidePageActivity.class));
                    finish();
                }
            } else {
                mhandler.sendEmptyMessageDelayed(1, 1000);
            }
        }

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {

        mhandler.sendEmptyMessageDelayed(0, 1000);

    }

    @Override
    protected void destoryData() {

    }
}
