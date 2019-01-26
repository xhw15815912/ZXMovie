package movie.bw.com.movie.activity;

import android.os.Bundle;

import com.bw.movie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;

public class FeedActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.fan)
    public void onViewClicked() {
        finish();
    }
}
