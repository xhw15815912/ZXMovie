package movie.bw.com.movie.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RadioGroup;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.adapter.GuideAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.frag.guidefragment.Fragment_Bootpage_four;
import movie.bw.com.movie.frag.guidefragment.Fragment_Bootpage_one;
import movie.bw.com.movie.frag.guidefragment.Fragment_Bootpage_three;
import movie.bw.com.movie.frag.guidefragment.Fragment_Bootpage_two;

public class GuidePageActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.radio)
    RadioGroup radio;
    private GuideAdapter guideAdapter;
    private List<Fragment> list;
    private boolean isFirstUse;
    private SharedPreferences preferences;
    private int currentItem = 0;
    private int flaggingWidth;
    private GestureDetector mGestureDetector;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initView() {

        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;

        if (ContextCompat.checkSelfPermission(GuidePageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(GuidePageActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        }

        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirstUse = preferences.getBoolean("isFirstUse", false);
        if (isFirstUse) {
            startActivity(new Intent(GuidePageActivity.this, ShowActivity.class));
            finish();
            return;
        }
        list = new ArrayList<>();
        list.add(new Fragment_Bootpage_one());
        list.add(new Fragment_Bootpage_two());
        list.add(new Fragment_Bootpage_three());
        list.add(new Fragment_Bootpage_four());
        guideAdapter = new GuideAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(guideAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                radio.check(radio.getChildAt(i).getId());

            }

            @Override
            public void onPageSelected(int i) {
                currentItem = i;
                //实例化Editor对象
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putBoolean("isFirstUse", true);
                //提交修改
                editor.commit();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        slipToMain();

    }

    private void slipToMain() {
        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (currentItem == 3) {
                            if ((e1.getRawX() - e2.getRawX()) >= flaggingWidth) {
                                Intent intent = new Intent(GuidePageActivity.this,  ShowActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                            }
                        }
                        return false;
                    }

                });
    }

    @Override
    protected void destoryData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mGestureDetector.onTouchEvent(ev))
        {
            ev.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(ev);
    }

}
