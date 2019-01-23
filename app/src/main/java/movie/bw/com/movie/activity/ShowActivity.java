package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import movie.bw.com.movie.R;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.frag.FragmentFactory;

public class ShowActivity extends BaseActivity {


    @BindView(R.id.page)
    ViewPager page;
    @BindView(R.id.tab)
    TabLayout tab;
    private ImageView icon1;
    private ImageView icon2;
    private ImageView icon3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    protected void initView() {
        initTab();
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                init(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init(TabLayout.Tab tabs) {
        if (tabs == tab.getTabAt(0)){
            //tab.getTabAt(0).setCustomView(R.layout.show_bigimage);
            //icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
            /*icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
            icon2=tab.getTabAt(1).getCustomView().findViewById(R.id.img);
            icon3=tab.getTabAt(2).getCustomView().findViewById(R.id.img);*/
            icon1 .setImageResource(R.mipmap.com_icon_film_selected);
            icon2.setImageResource(R.mipmap.com_icon_cinema_default);
            icon3.setImageResource(R.mipmap.com_icon_my_default);
        }else if(tabs == tab.getTabAt(1)){
            //tab.getTabAt(1).setCustomView(R.layout.show_bigimage);
            //icon2=tab.getTabAt(1).getCustomView().findViewById(R.id.img);
           /* icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
            icon2=tab.getTabAt(1).getCustomView().findViewById(R.id.img);
            icon3=tab.getTabAt(2).getCustomView().findViewById(R.id.img);*/
            icon1 .setImageResource(R.mipmap.com_icon_film_fault);
            icon2.setImageResource(R.mipmap.com_icon_cinema_selected);
            icon3.setImageResource(R.mipmap.com_icon_my_default);
        }else if(tabs == tab.getTabAt(2)){
            //tab.getTabAt(2).setCustomView(R.layout.show_bigimage);
            //icon3=tab.getTabAt(2).getCustomView().findViewById(R.id.img);
            /*icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
            icon2=tab.getTabAt(1).getCustomView().findViewById(R.id.img);
            icon3=tab.getTabAt(2).getCustomView().findViewById(R.id.img);*/
            icon1 .setImageResource(R.mipmap.com_icon_film_fault);
            icon2.setImageResource(R.mipmap.com_icon_cinema_default);
            icon3.setImageResource(R.mipmap.com_icon_my_selected);
        }
    }

    private void initTab() {
        tab.addTab(tab.newTab());
        tab.addTab(tab.newTab());
        tab.addTab(tab.newTab());

        tab.setupWithViewPager(page,false);

        page.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Fragment fragment = FragmentFactory.getFragment(i);
                return fragment;
            }
            @Override
            public int getCount() {
                return 3;
            }
        });

        tab.getTabAt(0).setCustomView(R.layout.show_image);
        tab.getTabAt(1).setCustomView(R.layout.show_image);
        tab.getTabAt(2).setCustomView(R.layout.show_image);



        icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
        icon2=tab.getTabAt(1).getCustomView().findViewById(R.id.img);
        icon3=tab.getTabAt(2).getCustomView().findViewById(R.id.img);

        //tab.getTabAt(0).setCustomView(R.layout.show_bigimage);
        //icon1=tab.getTabAt(0).getCustomView().findViewById(R.id.img);
        icon1 .setImageResource(R.mipmap.com_icon_film_selected);
        icon2.setImageResource(R.mipmap.com_icon_cinema_default);
        icon3.setImageResource(R.mipmap.com_icon_my_default);
    }

    @Override
    protected void destoryData() {

    }


}
