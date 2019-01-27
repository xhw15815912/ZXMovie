package movie.bw.com.movie;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.jessyan.autosize.internal.CustomAdapt;
import movie.bw.com.movie.frag.PingFragment;
import movie.bw.com.movie.frag.XiangFragment;

public class FragmentFeedCmt extends BottomSheetDialogFragment implements CustomAdapt {

    private static int mId;
    private TextView mTv_dialog_monitor_xiang;
    private TextView mTv_dialog_monitor_ping;
    private View mView_dialog_monitor_xiang;
    private View mView_dialog_monitor_ping;
    private ViewPager mVp_dialog_monitor;

    // 构造方法
    public static FragmentFeedCmt newInstance(Long feedId) {
        Bundle args = new Bundle();

        args.putLong("FEED_ID", feedId);
        FragmentFeedCmt fragment = new FragmentFeedCmt();
        fragment.setArguments(args);
        return fragment;
    }


    // show的时候调用
    @Override
    public void show(FragmentManager manager, String tag) {
        Log.e("xiaxl: ", "show");
        super.show(manager, tag);
    }



    // 创建View
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater  , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("xiaxl: ", "onCreateView");
        View inflate = inflater.inflate(R.layout.dialog_monitor_detail, container);
        initview(inflate);
        initData();
        initLinter();

        return inflate;
    }

    private void initLinter() {
        mTv_dialog_monitor_xiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVp_dialog_monitor.setCurrentItem(0);
                mView_dialog_monitor_xiang.setVisibility(View.VISIBLE);
                mView_dialog_monitor_ping.setVisibility(View.INVISIBLE);
            }
        });
        mTv_dialog_monitor_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVp_dialog_monitor.setCurrentItem(1);
                mView_dialog_monitor_xiang.setVisibility(View.INVISIBLE);
                mView_dialog_monitor_ping.setVisibility(View.VISIBLE);
            }
        });
        mVp_dialog_monitor.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        mView_dialog_monitor_xiang.setVisibility(View.VISIBLE);
                        mView_dialog_monitor_ping.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mView_dialog_monitor_xiang.setVisibility(View.INVISIBLE);
                        mView_dialog_monitor_ping.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    // create dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e("xiaxl: ", "onCreateDialog");
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        //setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.main_menu_animStyle);
        return super.onCreateDialog(savedInstanceState);
    }
    private void initview(View inflate) {
        mTv_dialog_monitor_xiang = inflate.findViewById(R.id.tv_dialog_monitor_xiang);
        mTv_dialog_monitor_ping = inflate.findViewById(R.id.tv_dialog_monitor_ping);
        mView_dialog_monitor_xiang = inflate.findViewById(R.id.view_dialog_monitor_xiang);
        mView_dialog_monitor_ping = inflate.findViewById(R.id.view_dialog_monitor_ping);
        mVp_dialog_monitor = inflate.findViewById(R.id.vp_dialog_monitor);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new XiangFragment());
        fragments.add(new PingFragment());
        mVp_dialog_monitor.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    // 加载数据
    private void initData() {
        // 数据
        Bundle args = getArguments();
        //long feedId = args.getLong("FEED_ID", -1);
    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}