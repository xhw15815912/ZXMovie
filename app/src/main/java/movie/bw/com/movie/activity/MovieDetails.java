package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.ParticularsPresenter;

public class MovieDetails extends BaseActivity {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.photo)
    Button photo;
    @BindView(R.id.comment)
    Button comment;
    @BindView(R.id.back)
    ImageView back;
    private int id;
    private ParticularsPresenter particularsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String sessionId = USER.getSessionId();
        int userId = USER.getUserId();
        id = intent.getIntExtra("id", 0);
        particularsPresenter = new ParticularsPresenter(new CallBack());
        if (id != 0) {
            particularsPresenter.request(userId, sessionId, id);
        }
    }

    @Override
    protected void destoryData() {

    }
    @OnClick(R.id.back)
    public void Bk(){
        finish();
    }

    private class CallBack implements DataCall<Result<ParticularsBean>> {
        @Override
        public void success(Result<ParticularsBean> data) {
            Log.e("错误",data.getMessage()+"");
            if (data.getStatus().equals("0000")){
                ParticularsBean result = data.getResult();
                name.setText(result.getName());
                image.setImageURI(result.getImageUrl());

            }
        }

        @Override
        public void fail(ApiException e) {
            Log.e("错误",e+"");
        }
    }
}
