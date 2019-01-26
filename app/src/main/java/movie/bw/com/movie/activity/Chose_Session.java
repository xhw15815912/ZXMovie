package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.TimeAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Chose_Session_Bean;
import movie.bw.com.movie.bean.FilmInFoBean;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.Chose_Session_Presenter;
import movie.bw.com.movie.p.FilmInfo_Presenter;
import movie.bw.com.movie.p.ParticularsPresenter;

public class Chose_Session extends BaseActivity {


    @BindView(R.id.FileName)
    TextView FileName;
    @BindView(R.id.map)
    ImageView map;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.movieName)
    TextView movieName;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.men)
    TextView men;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.Filmplace)
    TextView Filmplace;
    private Chose_Session_Presenter chose_session_presenter;
    private int filmid;
    private String movieId;
    private TimeAdapter timeAdapter;
    private ParticularsPresenter particularsPresenter;
    private FilmInfo_Presenter filmInfo_presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chose_session;
    }


    @Override
    protected void initView() {
        filmInfo_presenter = new FilmInfo_Presenter(new FilmInfo());
        Intent intent = getIntent();
        String sessionId = USER.getSessionId();
        int userId = USER.getUserId();
        particularsPresenter = new ParticularsPresenter(new Chat());
        particularsPresenter.request(userId,sessionId,Integer.valueOf(movieId));

        filmid = intent.getIntExtra("id", 1);
        Log.e("影院id",filmid+"");
        filmInfo_presenter.request(userId,sessionId,filmid);
        chose_session_presenter = new Chose_Session_Presenter(new CallBack());
        chose_session_presenter.request(filmid, movieId);
        timeAdapter = new TimeAdapter(this);
        recy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recy.setAdapter(timeAdapter);
    }
    @OnClick(R.id.back)
    public void Back(){
        finish();
    }
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Time(String id) {
        movieId = id;
    }
    @Override
    protected void destoryData() {
    }
    private class CallBack implements DataCall<Result<List<Chose_Session_Bean>>> {
        @Override
        public void success(Result<List<Chose_Session_Bean>> data) {
            if (data.getStatus().equals("0000")) {
                timeAdapter.setList(data.getResult());
            }
        }
        @Override
        public void fail(ApiException e) {
        }
    }

    private class Chat implements DataCall<Result<ParticularsBean>> {
        @Override
        public void success(Result<ParticularsBean> data) {
               if (data.getStatus().equals("0000")){
                   image.setImageURI(data.getResult().getImageUrl());
                   movieName.setText(data.getResult().getName());
                   type.setText("类型:"+data.getResult().getMovieTypes());
                   men.setText("导演:"+data.getResult().getDirector());
                   time.setText("时长:"+data.getResult().getDuration());
                   place.setText("产地:"+data.getResult().getPlaceOrigin());
               }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class FilmInfo implements DataCall<Result<FilmInFoBean>> {
        @Override
        public void success(Result<FilmInFoBean> data) {
            Log.e("影院详情",data.getMessage()+"");
            Log.e("影院详情",data.getStatus()+"");
            if (data.getStatus().equals("0000")){
                FileName.setText(data.getResult().getName());
                Filmplace.setText(data.getResult().getAddress());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
