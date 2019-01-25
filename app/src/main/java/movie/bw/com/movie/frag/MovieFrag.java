package movie.bw.com.movie.frag;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import movie.bw.com.movie.activity.MoreMovie;
import movie.bw.com.movie.adapter.FlowAdapter;
import movie.bw.com.movie.adapter.HotMovieAdapter;
import movie.bw.com.movie.adapter.NowAdapter;
import movie.bw.com.movie.adapter.SoonAdapter;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.HotMovie;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.FindHotMovieListPresenter;
import movie.bw.com.movie.p.NowMovie;
import movie.bw.com.movie.p.SoonMoviewPresenter;
import recycler.coverflow.RecyclerCoverFlow;


public class MovieFrag extends BaseFragment {

    public LocationClient mLocationClient = null;
    @BindView(R.id.flow)
    RecyclerCoverFlow flow;
    @BindView(R.id.hotMove)
    RecyclerView hotMove;
    @BindView(R.id.nowMove)
    RecyclerView nowMove;
    @BindView(R.id.soonMove)
    RecyclerView soonMove;
    @BindView(R.id.recommend_cinem_search_image)
    ImageView recommendCinemSearchImage;
    @BindView(R.id.recommend_cinema_edname)
    EditText recommendCinemaEdname;
    @BindView(R.id.recommend_cinema_textName)
    TextView recommendCinemaTextName;
    @BindView(R.id.recommend_cinema_linear)
    LinearLayout recommendCinemaLinear;
    @BindView(R.id.hot)
    TextView hot;
    @BindView(R.id.now)
    TextView now;
    @BindView(R.id.soon)
    TextView soon;

    private MyLocationListener myListener = new MyLocationListener();
    @BindView(R.id.image_location)
    ImageView imageLocation;
    @BindView(R.id.text_positioningq)
    TextView textPositioningq;
    private FlowAdapter flowAdapter;
    private FindHotMovieListPresenter findHotMovieListPresenter;
    private String sessionId;
    private int userId;
    private HotMovieAdapter hotMovieAdapter;
    private NowMovie nowMovie;
    private SoonMoviewPresenter soonMoviewPresenter;
    private NowAdapter nowAdapter;
    private SoonAdapter soonAdapter;
    private ObjectAnimator animator;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show;
    }

    @Override
    protected void initView() {
        sessionId = USER.getSessionId();
        userId = USER.getUserId();
        Log.d("++++++++++++++++++++++", "initView: " + sessionId + "          " + userId);
        initFlow();
        initHotMove();
        flow.setAdapter(flowAdapter);
        hotMove.setAdapter(hotMovieAdapter);
        nowMove.setAdapter(nowAdapter);
        soonMove.setAdapter(soonAdapter);
    }

    private void initFlow() {
        nowMovie = new NowMovie(new Now());
        soonAdapter = new SoonAdapter(getActivity());
        soonMoviewPresenter = new SoonMoviewPresenter(new Soon());
        flowAdapter = new FlowAdapter(getActivity());
        nowAdapter = new NowAdapter(getActivity());
        findHotMovieListPresenter = new FindHotMovieListPresenter(new HotMovie());
        findHotMovieListPresenter.request(userId, sessionId, 1, 10);
    }

    private void initHotMove() {
        nowMovie.request(userId, sessionId, 1, 10);
        soonMoviewPresenter.request(userId, sessionId, 1, 10);
        hotMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nowMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        soonMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nowMovie.request(userId, sessionId);
        soonMoviewPresenter.request(userId, sessionId);
        hotMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nowMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        soonMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hotMovieAdapter = new HotMovieAdapter(getActivity());

    }

    @OnClick(R.id.image_location)
    public void onViewClicked() {
        if (mLocationClient==null){
            orientation();
        }

    }

    @OnClick(R.id.hot)
    public void Hot() {
        Intent intent = new Intent(getActivity(), MoreMovie.class);
        intent.putExtra("id", 1);
        startActivity(intent);
    }

    @OnClick(R.id.now)
    public void Now() {
        Intent intent = new Intent(getActivity(), MoreMovie.class);
        intent.putExtra("id", 2);
        startActivity(intent);
    }

    @OnClick(R.id.soon)
    public void Soon() {
        Intent intent = new Intent(getActivity(), MoreMovie.class);
        intent.putExtra("id", 3);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        orientation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void orientation() {
        mLocationClient = new LocationClient(getActivity());
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


    @OnClick({R.id.recommend_cinem_search_image, R.id.recommend_cinema_textName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定     位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            textPositioningq.setText(addr + "");
        }
    }

    private class HotMovie implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<movie.bw.com.movie.bean.HotMovie>> data) {
            Log.e("qwer", data.getMessage() + "===");

            if (data.getStatus().equals("0000")) {
                flowAdapter.setData(data.getResult());
                hotMovieAdapter.setData(data.getResult());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Now implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<movie.bw.com.movie.bean.HotMovie>> data) {
            if (data.getStatus().equals("0000")) {
                nowAdapter.setData(data.getResult());

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Soon implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<movie.bw.com.movie.bean.HotMovie>> data) {
            if (data.getStatus().equals("0000")) {
                soonAdapter.setData(data.getResult());

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
