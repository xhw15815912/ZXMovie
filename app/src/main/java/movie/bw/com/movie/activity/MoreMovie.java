package movie.bw.com.movie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.RecommedAdapter;
import movie.bw.com.movie.adapter.XHotAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.HotMovie;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.frag.MovieFrag;
import movie.bw.com.movie.p.FindHotMovieListPresenter;
import movie.bw.com.movie.p.NowMovie;
import movie.bw.com.movie.p.SoonMoviewPresenter;
import movie.bw.com.movie.p.UnfollowPresenter;
import movie.bw.com.movie.p.UnfollowcinemasPresenter;

public class MoreMovie extends BaseActivity implements XRecyclerView.LoadingListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.hot)
    Button hot;
    @BindView(R.id.photo)
    Button photo;
    @BindView(R.id.comment)
    Button comment;
    @BindView(R.id.xrecy)
    RecyclerView xrecy;
    private NowMovie nowMovie;
    private SoonMoviewPresenter soonMoviewPresenter;
    private FindHotMovieListPresenter findHotMovieListPresenter;
    private String sessionId;
    private int userId;
    private XHotAdapter xHotAdapter;
    private UnfollowcinemasPresenter unfollowcinemasPresenter;
    private UnfollowPresenter unfollowPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_movie;
    }

    @Override
    protected void initView() {

        initP();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id==1){
            hot.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            hot.setTextColor(Color.WHITE);
            photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
            photo.setTextColor(Color.BLACK);
            comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
            comment.setTextColor(Color.BLACK);
            findHotMovieListPresenter.request(userId,sessionId,1,10);

        }else if (id==2){
            hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
            hot.setTextColor(Color.BLACK);
            photo.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            photo.setTextColor(Color.WHITE);
            comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
            comment.setTextColor(Color.BLACK);
            nowMovie.request(userId,sessionId,1,10);
        }else if (id==3){
            hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
            hot.setTextColor(Color.BLACK);
            photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
            photo.setTextColor(Color.BLACK);
            comment.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            comment.setTextColor(Color.WHITE);
            soonMoviewPresenter.request(userId,sessionId,1,10);

        }

        xrecy.setAdapter(xHotAdapter);
    }

    private void initP() {
        unfollowcinemasPresenter = new UnfollowcinemasPresenter(new Unfo());
        unfollowPresenter = new UnfollowPresenter(new UnF());
        xrecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        if (list!=null&&list.size()>0){
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }

        nowMovie = new NowMovie(new Now());
        soonMoviewPresenter = new SoonMoviewPresenter(new Soon());
        findHotMovieListPresenter = new FindHotMovieListPresenter(new Hot());
        xHotAdapter = new XHotAdapter(this);
        xHotAdapter.setOnItemClickListener(new RecommedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int cinemaId, int isFollow) {
                Log.d("状态渍渍渍++++++++++", "onItemClick: "+isFollow);
                if (hot.isClickable()){
                   if (isFollow==1){
                       unfollowPresenter.request(userId,sessionId,cinemaId);
                   }else if (isFollow==2){
                       unfollowcinemasPresenter.request(userId,sessionId,cinemaId);

                   }
                } else if (photo.isClickable()) {
                    if (isFollow==1){
                        unfollowPresenter.request(userId,sessionId,cinemaId);
                    }else if (isFollow==2){
                        unfollowcinemasPresenter.request(userId,sessionId,cinemaId);

                    }
                } else if (comment.isClickable()) {
                    if (isFollow==1){
                        unfollowPresenter.request(userId,sessionId,cinemaId);
                    }else if (isFollow==2){
                        unfollowcinemasPresenter.request(userId,sessionId,cinemaId);

                    }
                }
            }
        });
    }

    @Override
    protected void destoryData() {

    }
    @OnClick({R.id.back, R.id.hot, R.id.photo, R.id.comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.hot:
                    hot.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
                    hot.setTextColor(Color.WHITE);
                    photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
                    photo.setTextColor(Color.BLACK);
                    comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
                    comment.setTextColor(Color.BLACK);
                    findHotMovieListPresenter.request(userId,sessionId,1,10);
                break;
            case R.id.photo:
                hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
                hot.setTextColor(Color.BLACK);
                photo.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
                photo.setTextColor(Color.WHITE);
                comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
                comment.setTextColor(Color.BLACK);
                nowMovie.request(userId,sessionId,1,10);
                break;
            case R.id.comment:
                hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
                hot.setTextColor(Color.BLACK);
                photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
                photo.setTextColor(Color.BLACK);
                comment.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
                comment.setTextColor(Color.WHITE);
                soonMoviewPresenter.request(userId,sessionId,1,10);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    private class Now implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
               if (data.getStatus().equals("0000")){
                   xHotAdapter.setData(data.getResult());
                   xHotAdapter.notifyDataSetChanged();
               }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Soon implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            if (data.getStatus().equals("0000")){
                xHotAdapter.setData(data.getResult());
                xHotAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Hot implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            if (data.getStatus().equals("0000")){
                  xHotAdapter.setData(data.getResult());
                xHotAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Unfo implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                if (hot.isClickable()){
                    findHotMovieListPresenter.request(userId,sessionId,1,10);
                }else if (photo.isClickable()){
                    nowMovie.request(userId,sessionId,1,10);
                }else if(comment.isClickable()){
                    soonMoviewPresenter.request(userId,sessionId,1,10);
                }
            }else {
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class UnF implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                if (hot.isClickable()){
                    findHotMovieListPresenter.request(userId,sessionId,1,10);
                }else if (photo.isClickable()){
                    nowMovie.request(userId,sessionId,1,10);
                }else if(comment.isClickable()){
                    soonMoviewPresenter.request(userId,sessionId,1,10);
                }
            }else {
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
