package movie.bw.com.movie.frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import movie.bw.com.movie.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFrag extends BaseFragment {


    @BindView(R.id.trumpet)
    ImageView trumpet;
    @BindView(R.id.headimage)
    SimpleDraweeView headimage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.my_chat)
    RelativeLayout myChat;
    @BindView(R.id.my_attention)
    RelativeLayout myAttention;
    @BindView(R.id.my_rccord)
    RelativeLayout myRccord;
    @BindView(R.id.my_feedback)
    RelativeLayout myFeedback;
    @BindView(R.id.my_version)
    RelativeLayout myVersion;
    Unbinder unbinder;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }





    @OnClick({R.id.trumpet, R.id.headimage, R.id.my_chat, R.id.my_attention, R.id.my_rccord, R.id.my_feedback, R.id.my_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trumpet:
                break;
            case R.id.headimage:
                break;
            case R.id.my_chat:

                break;
            case R.id.my_attention:
                break;
            case R.id.my_rccord:
                break;
            case R.id.my_feedback:
                break;
            case R.id.my_version:
                break;
        }
    }
}
