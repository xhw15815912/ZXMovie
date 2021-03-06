package movie.bw.com.movie.frag;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
import movie.bw.com.movie.utils.CacheManager;
import recycler.coverflow.CoverFlowLayoutManger;
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
    Unbinder unbinder;
    @BindView(R.id.rad1)
    RadioButton rad1;
    @BindView(R.id.rad2)
    RadioButton rad2;
    @BindView(R.id.rad3)
    RadioButton rad3;
    @BindView(R.id.rad4)
    RadioButton rad4;
    @BindView(R.id.rad5)
    RadioButton rad5;
    @BindView(R.id.rad6)
    RadioButton rad6;
    @BindView(R.id.redgroup)
    RadioGroup redgroup;
    Unbinder unbinder1;

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
    private CacheManager cacheManager;

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
        if (list != null && list.size() > 0) {
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }
        cacheManager = new CacheManager();
        initFlow();
        initHotMove();
        orientation();
        flow.setAdapter(flowAdapter);
        hotMove.setAdapter(hotMovieAdapter);
        nowMove.setAdapter(nowAdapter);
        soonMove.setAdapter(soonAdapter);
        boolean connection = isConnection();
        if (!connection) {
            Toast.makeText(getActivity(), "没网啦！！！", Toast.LENGTH_LONG).show();
            String s = cacheManager.loadDataFromFile(getContext(), "hot");
            String s1 = cacheManager.loadDataFromFile(getContext(), "now");
            String s2 = cacheManager.loadDataFromFile(getContext(), "soon");
            Gson gson = new Gson();
            Type type = new TypeToken<List<movie.bw.com.movie.bean.HotMovie>>() {
            }.getType();
            Type type1 = new TypeToken<List<movie.bw.com.movie.bean.HotMovie>>() {
            }.getType();
            Type type2 = new TypeToken<List<movie.bw.com.movie.bean.HotMovie>>() {
            }.getType();
            List<movie.bw.com.movie.bean.HotMovie> o = gson.fromJson(s, type);
            List<movie.bw.com.movie.bean.HotMovie> o1 = gson.fromJson(s1, type1);
            List<movie.bw.com.movie.bean.HotMovie> o2 = gson.fromJson(s2, type2);
            hotMovieAdapter.setData(o);
            hotMovieAdapter.notifyDataSetChanged();
            soonAdapter.setData(o1);
            soonAdapter.notifyDataSetChanged();
            nowAdapter.setData(o2);
            nowAdapter.notifyDataSetChanged();
            flowAdapter.setData(o);
            flowAdapter.notifyDataSetChanged();
        }
        redgroup.check(redgroup.getChildAt(0).getId());
        flow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                redgroup.check(redgroup.getChildAt(position).getId());
            }
        });
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
        hotMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nowMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        soonMove.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hotMovieAdapter = new HotMovieAdapter(getActivity());

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


    @OnClick({R.id.recommend_cinem_search_image, R.id.recommend_cinema_textName, R.id.image_location, R.id.next, R.id.next1, R.id.next2})
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
            case R.id.image_location:
                if (mLocationClient == null) {
                    orientation();
                }
                break;
            case R.id.next:
                Intent intent = new Intent(getActivity(), MoreMovie.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
            case R.id.next1:
                Intent intent1 = new Intent(getActivity(), MoreMovie.class);
                intent1.putExtra("id", 2);
                startActivity(intent1);
                break;
            case R.id.next2:
                Intent intent2 = new Intent(getActivity(), MoreMovie.class);
                intent2.putExtra("id", 3);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
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

//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if (!addr.equals("")){
                textPositioningq.setText(addr + "");
            }else{
                textPositioningq.setText("定位中...");
            }

        }
    }

    private class HotMovie implements DataCall<Result<List<movie.bw.com.movie.bean.HotMovie>>> {
        @Override
        public void success(Result<List<movie.bw.com.movie.bean.HotMovie>> data) {
            Log.e("qwer", data.getMessage() + "===");

            //FileUtils.writeFile()
            if (data.getStatus().equals("0000")) {
                List<movie.bw.com.movie.bean.HotMovie> result = data.getResult();
                Gson gson = new Gson();
                String s = gson.toJson(result);
                cacheManager.saveDataToFile(getContext(), s, "hot");
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
                List<movie.bw.com.movie.bean.HotMovie> result = data.getResult();
                Gson gson = new Gson();
                String s = gson.toJson(result);
                cacheManager.saveDataToFile(getContext(), s, "now");
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
                List<movie.bw.com.movie.bean.HotMovie> result = data.getResult();
                Gson gson = new Gson();
                String s = gson.toJson(result);
                cacheManager.saveDataToFile(getContext(), s, "soon");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
