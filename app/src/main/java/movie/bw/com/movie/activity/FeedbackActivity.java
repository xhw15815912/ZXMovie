package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.FeedPresenter;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.ed_feedback)
    EditText edFeedback;
    private FeedPresenter feedPresenter;
    private int userId;
    private String sessionId;


    @OnClick({R.id.text_commit, R.id.fan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_commit:
                String s = edFeedback.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();

                }else {
                    feedPresenter.request(userId, sessionId,edFeedback.getText().toString());

                }
                break;
            case R.id.fan:
                finish();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        feedPresenter = new FeedPresenter(new Feed());
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }


    }

    @Override
    protected void destoryData() {

    }

    private class Feed implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(FeedbackActivity.this,data.getMessage() , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FeedbackActivity.this, FeedActivity.class));
                finish();
            }else {
                Toast.makeText(FeedbackActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
