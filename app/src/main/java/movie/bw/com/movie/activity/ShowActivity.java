package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movie.bw.com.movie.R;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.frag.CinemaFragment;
import movie.bw.com.movie.frag.MovieFrag;
import movie.bw.com.movie.frag.MyFrag;

public class ShowActivity extends BaseActivity {


    @BindView(R.id.page)
    ViewPager page;
    @BindView(R.id.wdMovie)
    RadioButton wdMovie;
    @BindView(R.id.wdCinema)
    RadioButton wdCinema;
    @BindView(R.id.wdMy)
    RadioButton wdMy;
    @BindView(R.id.wdGroup)
    RadioGroup wdGroup;
    private List<Fragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    protected void initView() {
           list=new ArrayList<>();
           list.add(new MovieFrag());
           list.add(new CinemaFragment());
           list.add(new MyFrag());

           page.setOffscreenPageLimit(list.size());

           page.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

           wdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, int checkedId) {
                   switch (checkedId){
                       case R.id.wdMovie:
                           page.setCurrentItem(0);
                           wdMovie.setBackgroundResource(R.mipmap.com_icon_film_selected);
                           wdCinema.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                           wdMy.setBackgroundResource(R.mipmap.com_icon_my_default);
                           break;
                       case R.id.wdCinema:
                           page.setCurrentItem(1);
                           wdMovie.setBackgroundResource(R.mipmap.com_icon_film_fault);
                           wdCinema.setBackgroundResource(R.mipmap.com_icon_cinema_selected);
                           wdMy.setBackgroundResource(R.mipmap.com_icon_my_default);
                           break;
                       case R.id.wdMy:
                           page.setCurrentItem(2);
                           wdMovie.setBackgroundResource(R.mipmap.com_icon_film_fault);
                           wdCinema.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                           wdMy.setBackgroundResource(R.mipmap.com_icon_my_selected);
                           break;
                   }
               }
           });

           page.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               @Override
               public void onPageScrolled(int i, float v, int i1) {

               }

               @Override
               public void onPageSelected(int i) {
                   wdGroup.check(wdGroup.getChildAt(i).getId());
               }

               @Override
               public void onPageScrollStateChanged(int i) {

               }
           });
    }

    @Override
    protected void destoryData() {

    }

    //适配器
    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }


    }


}
