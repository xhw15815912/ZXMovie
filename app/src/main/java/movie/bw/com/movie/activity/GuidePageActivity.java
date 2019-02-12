package movie.bw.com.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initView() {
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirstUse = preferences.getBoolean("isFirstUse", false);
        if (isFirstUse) {
            startActivity(new Intent(GuidePageActivity.this, ShowActivity.class));
            return;
        }
        list = new ArrayList<>();
        list.add(new Fragment_Bootpage_one());
        list.add(new Fragment_Bootpage_two());
        list.add(new Fragment_Bootpage_three());
        list.add(new Fragment_Bootpage_four());
        guideAdapter = new GuideAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(guideAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                radio.check(radio.getChildAt(i).getId());
                if (i == list.size() - 1) {

                    startActivity(new Intent(GuidePageActivity.this, ShowActivity.class));

                    finish();
                    //实例化Editor对象
                    SharedPreferences.Editor editor = preferences.edit();
                    //存入数据
                    editor.putBoolean("isFirstUse", true);
                    //提交修改
                    editor.commit();
                }
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

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
}
