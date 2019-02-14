package movie.bw.com.movie.frag;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import movie.bw.com.movie.adapter.RecommedAdapter;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.Recommend;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.AttentiontocinemaPresenter;
import movie.bw.com.movie.p.FocusonFilmPresenter;
import movie.bw.com.movie.p.NearcinemadPresenter;
import movie.bw.com.movie.p.RecommendPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener {


    @BindView(R.id.recommend)
    RadioButton recommend;
    @BindView(R.id.nearby)
    RadioButton nearby;
    @BindView(R.id.rad)
    RadioGroup rad;
    @BindView(R.id.theaters_xrecyclerview)
    XRecyclerView theatersXrecyclerview;
    @BindView(R.id.image_white)
    ImageView imageWhite;
    @BindView(R.id.recommend_cinem_search_image)
    ImageView recommendCinemSearchImage;
    @BindView(R.id.recommend_cinema_edname)
    EditText recommendCinemaEdname;
    @BindView(R.id.recommend_cinema_textName)
    TextView recommendCinemaTextName;
    @BindView(R.id.recommend_cinema_linear)
    LinearLayout recommendCinemaLinear;
    @BindView(R.id.address)
    TextView address;
    Unbinder unbinder;
    private RecommendPresenter presenter;
    private RecommedAdapter adapter;
    private String sessionId;
    private int userId;
    private NearcinemadPresenter nearcinemadPresenter;
    private int page = 1;
    private AttentiontocinemaPresenter attentiontocinemaPresenter;
    private FocusonFilmPresenter focusonFilmPresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema;
    }

    @Override
    protected void initView() {
        orientation();
        presenter = new RecommendPresenter(new Recomm());
        nearcinemadPresenter = new NearcinemadPresenter(new Nearcinima());
        if (list != null && list.size() > 0) {
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }

        presenter.request(userId, sessionId, page, 5);
        initData();
        theatersXrecyclerview.setLoadingListener(this);
        theatersXrecyclerview.setLoadingMoreEnabled(true);
        theatersXrecyclerview.setPullRefreshEnabled(true);
        onRefresh();
    }


    /*设置伸展状态时的布局*/
    @SuppressLint("ClickableViewAccessibility")
    private void initExpand() {
        recommendCinemaEdname.setHint("CGV影城");
        recommendCinemaEdname.requestFocus();
        recommendCinemaEdname.setHintTextColor(Color.WHITE);
        recommendCinemaTextName.setText("搜索");
        recommendCinemaTextName.setVisibility(View.VISIBLE);
        recommendCinemaEdname.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) recommendCinemaLinear.getLayoutParams();
        LayoutParams.width = dip2px(240);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        recommendCinemaLinear.setLayoutParams(LayoutParams);

        recommendCinemaEdname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recommendCinemaEdname.setFocusable(true);
                recommendCinemaEdname.setFocusableInTouchMode(true);
                return false;
            }
        });
        //开始动画
        beginDelayedTransition(recommendCinemaLinear);
    }

    /*设置收缩状态时的布局*/
    private void initReduce() {
        recommendCinemaEdname.setCursorVisible(false);
        recommendCinemaEdname.setVisibility(View.GONE);
        recommendCinemaTextName.setVisibility(View.GONE);
        LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) recommendCinemaLinear.getLayoutParams();
        LayoutParams.width = dip2px(50);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        recommendCinemaLinear.setLayoutParams(LayoutParams);

        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow()
                .getDecorView().getWindowToken(), 0);


        recommendCinemaEdname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendCinemaEdname.setCursorVisible(true);
            }
        });
        //开始动画
        beginDelayedTransition(recommendCinemaLinear);
    }


    private void beginDelayedTransition(ViewGroup view) {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(1000);
        TransitionManager.beginDelayedTransition(view, transition);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
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

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定     位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            address.setText(addr + "");
        }
    }

    private void initData() {
        focusonFilmPresenter = new FocusonFilmPresenter(new FOCU());
        attentiontocinemaPresenter = new AttentiontocinemaPresenter(new ZAn());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatersXrecyclerview.setLayoutManager(layoutManager);
        adapter = new RecommedAdapter(getContext());
        adapter.setOnItemClickListener(new RecommedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int cinemaId, int isFollow) {
                Log.d("状态值++++++++++++++++", "onItemClick: " + isFollow);
                if (USER != null && list.size() > 0) {
                    if (recommend.isChecked()) {
                        if (isFollow == 1) {
                            focusonFilmPresenter.request(userId, sessionId, cinemaId);

                        } else if (isFollow == 2) {
                            attentiontocinemaPresenter.request(userId, sessionId, cinemaId);

                        }
                    } else if (nearby.isChecked()) {
                        if (isFollow == 1) {
                            focusonFilmPresenter.request(userId, sessionId, cinemaId);

                        } else if (isFollow == 2) {
                            attentiontocinemaPresenter.request(userId, sessionId, cinemaId);

                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
        theatersXrecyclerview.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        orientation();
        if (recommend.isChecked()) {
            presenter.request(userId, sessionId, page, 10);

        }else {
            nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 10);

        }

    }

    @OnClick({R.id.recommend, R.id.nearby, R.id.rad, R.id.recommend_cinem_search_image, R.id.recommend_cinema_textName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend:

                presenter.request(userId, sessionId, page, 5);
                break;
            case R.id.nearby:

                nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 5);
                break;
            case R.id.recommend_cinem_search_image:
                initExpand();
                break;
            case R.id.recommend_cinema_textName:
                initReduce();
                break;
            case R.id.rad:
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (recommend.isChecked()) {
            presenter.request(userId, sessionId, page, 5);
        } else if (nearby.isChecked()) {
            nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 5);
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        if (recommend.isChecked()) {
            presenter.request(userId, sessionId, page, 5);
        } else if (nearby.isChecked()) {
            nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 5);
        }
    }


    private class Recomm implements DataCall<Result<List<Recommend>>> {


        @Override
        public void success(Result<List<Recommend>> data) {
            List<Recommend> list = data.getResult();
            adapter.addItem(list);
            adapter.notifyDataSetChanged();
            theatersXrecyclerview.loadMoreComplete();
            theatersXrecyclerview.refreshComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Nearcinima implements DataCall<Result<List<Recommend>>> {


        @Override
        public void success(Result<List<Recommend>> data) {
            List<Recommend> list = data.getResult();
            adapter.addItem(list);
            adapter.notifyDataSetChanged();
            theatersXrecyclerview.loadMoreComplete();
            theatersXrecyclerview.refreshComplete();

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class ZAn implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                if(recommend.isChecked()){
                    presenter.request(userId, sessionId, page, 5);

                }else if (nearby.isChecked()){
                    nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 5);
                }
                adapter.notifyDataSetChanged();

            }else {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class FOCU implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                if(recommend.isChecked()){
                    presenter.request(userId, sessionId, page, 5);

                }else if (nearby.isChecked()){
                    nearcinemadPresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", page, 5);
                }
                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
