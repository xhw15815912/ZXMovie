package movie.bw.com.movie.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import movie.bw.com.movie.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CinemaFragment extends BaseFragment {


    @BindView(R.id.page)
    ViewPager page;
    @BindView(R.id.recommend)
    RadioButton recommend;
    @BindView(R.id.nearby)
    RadioButton nearby;
    @BindView(R.id.rad)
    RadioGroup rad;
    Unbinder unbinder;


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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
