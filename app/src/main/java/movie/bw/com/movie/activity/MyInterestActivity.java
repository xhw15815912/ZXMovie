package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.MyInterestAdaper;
import movie.bw.com.movie.adapter.MyMoveAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.MyCinemaBean;
import movie.bw.com.movie.bean.MyInterestBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.MyCinemaPresenter;
import movie.bw.com.movie.p.MyInterestPresenter;

//我的关注activity
public class MyInterestActivity extends BaseActivity {
    @BindView(R.id.movie)
    RadioButton movie;
    @BindView(R.id.cinema)
    RadioButton cinema;
    @BindView(R.id.x_receycelview)
    RecyclerView xReceycelview;
    @BindView(R.id.back)
    ImageView back;
    private MyInterestPresenter myInterestPresenter;
    private int userId;
    private String sessionId;

    private MyMoveAdapter adapter;
    private MyCinemaPresenter myCinemaPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_interest;
    }

    @Override
    protected void initView() {
        userId = USER.getUserId();
        sessionId = USER.getSessionId();
        myCinemaPresenter = new MyCinemaPresenter(new CInema());
        myInterestPresenter = new MyInterestPresenter(new MyInterest());
        myInterestPresenter.request(userId, sessionId, 1, 10);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xReceycelview.setLayoutManager(layoutManager);
        adapter = new MyMoveAdapter(this);
        xReceycelview.setAdapter(adapter);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.movie, R.id.cinema,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie:
                myInterestPresenter.request(userId, sessionId, 1, 10);
                break;
            case R.id.cinema:
                myCinemaPresenter.request(userId, sessionId, 1, 10);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    private class MyInterest implements DataCall<Result<List<MyInterestBean>>> {

        @Override
        public void success(Result<List<MyInterestBean>> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MyInterestActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                List<MyInterestBean> result = data.getResult();
                LinearLayoutManager layoutManager = new LinearLayoutManager(MyInterestActivity.this);
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                xReceycelview.setLayoutManager(layoutManager);
                adapter = new MyMoveAdapter(MyInterestActivity.this);
                adapter.addItem(result);
                xReceycelview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MyInterestActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CInema implements DataCall<Result<List<MyCinemaBean>>> {
        @Override
        public void success(Result<List<MyCinemaBean>> data) {
            if (data.getStatus().equals("0000")) {
                List<MyCinemaBean> dataResult = data.getResult();
                LinearLayoutManager layoutManager = new LinearLayoutManager(MyInterestActivity.this);
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                xReceycelview.setLayoutManager(layoutManager);
                MyInterestAdaper myInterestAdaper = new MyInterestAdaper(MyInterestActivity.this);
                myInterestAdaper.addITem(dataResult);
                xReceycelview.setAdapter(myInterestAdaper);
                myInterestAdaper.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
