package movie.bw.com.movie.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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

import static android.app.PendingIntent.getActivity;

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
    @BindView(R.id.image_white)
    ImageView imageWhite;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.recommend_cinem_search_image)
    ImageView recommendCinemSearchImage;
    @BindView(R.id.recommend_cinema_edname)
    EditText recommendCinemaEdname;
    @BindView(R.id.recommend_cinema_textName)
    TextView recommendCinemaTextName;
    @BindView(R.id.recommend_cinema_linear)
    LinearLayout recommendCinemaLinear;
    private NowMovie nowMovie;
    private SoonMoviewPresenter soonMoviewPresenter;
    private FindHotMovieListPresenter findHotMovieListPresenter;
    private String sessionId;
    private int userId;
    private XHotAdapter xHotAdapter;
    private UnfollowcinemasPresenter unfollowcinemasPresenter;
    private UnfollowPresenter unfollowPresenter;
    private ObjectAnimator animator;
    public LocationClient mLocationClient = null;
    private  MyLocationListener myListener = new MyLocationListener();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_movie;
    }
    private void orientation() {
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    @Override
    public void onResume() {
        super.onResume();
        orientation();
    }

    @Override
    public void onStart() {
        super.onStart();
        orientation();
    }
    @Override
    protected void initView() {
        recommendCinemaEdname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        initP();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id == 1) {
            hot.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            hot.setTextColor(Color.WHITE);
            photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
            photo.setTextColor(Color.BLACK);
            comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
            comment.setTextColor(Color.BLACK);
            findHotMovieListPresenter.request(userId, sessionId, 1, 10);

        } else if (id == 2) {
            hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
            hot.setTextColor(Color.BLACK);
            photo.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            photo.setTextColor(Color.WHITE);
            comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
            comment.setTextColor(Color.BLACK);
            nowMovie.request(userId, sessionId, 1, 10);
        } else if (id == 3) {
            hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
            hot.setTextColor(Color.BLACK);
            photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
            photo.setTextColor(Color.BLACK);
            comment.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
            comment.setTextColor(Color.WHITE);
            soonMoviewPresenter.request(userId, sessionId, 1, 10);

        }

        xrecy.setAdapter(xHotAdapter);
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定     位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if (!location.equals("")) {
                mLocationClient.stop();
            }

            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if (addr!=null){
                address.setText(addr + "");
            }else{
                address.setText("请重新定位!");
            }

        }
    }
    private void initP() {
        unfollowcinemasPresenter = new UnfollowcinemasPresenter(new Unfo());
        unfollowPresenter = new UnfollowPresenter(new UnF());
        xrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (list != null && list.size() > 0) {
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
                Log.d("状态渍渍渍++++++++++", "onItemClick: " + isFollow);
                if (USER != null) {
                    if (hot.isClickable()) {
                        if (isFollow == 1) {
                            unfollowPresenter.request(userId, sessionId, cinemaId);
                        } else if (isFollow == 2) {
                            unfollowcinemasPresenter.request(userId, sessionId, cinemaId);

                        }
                    } else if (photo.isClickable()) {
                        if (isFollow == 1) {
                            unfollowPresenter.request(userId, sessionId, cinemaId);
                        } else if (isFollow == 2) {
                            unfollowcinemasPresenter.request(userId, sessionId, cinemaId);

                        }
                    } else if (comment.isClickable()) {
                        if (isFollow == 1) {
                            unfollowPresenter.request(userId, sessionId, cinemaId);
                        } else if (isFollow == 2) {
                            unfollowcinemasPresenter.request(userId, sessionId, cinemaId);

                        }
                    }
                }

            }
        });
    }


    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.back, R.id.hot, R.id.photo, R.id.comment,R.id.recommend_cinem_search_image, R.id.recommend_cinema_textName})
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
                findHotMovieListPresenter.request(userId, sessionId, 1, 10);
                break;
            case R.id.photo:
                hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
                hot.setTextColor(Color.BLACK);
                photo.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
                photo.setTextColor(Color.WHITE);
                comment.setBackgroundResource(R.drawable.kuang_8dp_touying);
                comment.setTextColor(Color.BLACK);
                nowMovie.request(userId, sessionId, 1, 10);
                break;
            case R.id.comment:
                hot.setBackgroundResource(R.drawable.kuang_8dp_touying);
                hot.setTextColor(Color.BLACK);
                photo.setBackgroundResource(R.drawable.kuang_8dp_touying);
                photo.setTextColor(Color.BLACK);
                comment.setBackgroundResource(R.drawable.bg_dividing_line_title_rectangle);
                comment.setTextColor(Color.WHITE);
                soonMoviewPresenter.request(userId, sessionId, 1, 10);
                break;
            case R.id.recommend_cinem_search_image:
                animator = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 30f, -500f);
                animator.setDuration(1000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                break;
            case R.id.recommend_cinema_textName:
                animator = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", -510f, 0f);
                animator.setDuration(1000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }


  private class Now implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            if (data.getStatus().equals("0000")) {
                xHotAdapter.setData(data.getResult());
                xHotAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Soon implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            if (data.getStatus().equals("0000")) {
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
            if (data.getStatus().equals("0000")) {
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
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                if (hot.isClickable()) {
                    findHotMovieListPresenter.request(userId, sessionId, 1, 10);
                } else if (photo.isClickable()) {
                    nowMovie.request(userId, sessionId, 1, 10);
                } else if (comment.isClickable()) {
                    soonMoviewPresenter.request(userId, sessionId, 1, 10);
                }
            } else {
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
            if (data.getStatus().equals("0000")) {
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                if (hot.isClickable()) {
                    findHotMovieListPresenter.request(userId, sessionId, 1, 10);
                } else if (photo.isClickable()) {
                    nowMovie.request(userId, sessionId, 1, 10);
                } else if (comment.isClickable()) {
                    soonMoviewPresenter.request(userId, sessionId, 1, 10);
                }
            } else {
                Toast.makeText(MoreMovie.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
