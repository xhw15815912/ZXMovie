package movie.bw.com.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.FragmentFeedCmt;
import movie.bw.com.movie.adapter.FlowAdapter;
import movie.bw.com.movie.adapter.TimesAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Chose_Session_Bean;
import movie.bw.com.movie.bean.HotMovie;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.DTPresenter;
import movie.bw.com.movie.p.FindMoviePresenter;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class DetailsofcinemaActivity extends BaseActivity {


    @BindView(R.id.image)
    SimpleDraweeView image_sim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.flow)
    RecyclerCoverFlow flow;
    @BindView(R.id.x_receycelview)
    XRecyclerView xreceycelview;
    private FlowAdapter adapter;
    private FindMoviePresenter presenter;
    private DTPresenter dtPresenter;
    private TimesAdapter adapters;
    private FragmentFeedCmt fragmentFeedCmt;
    private int yid;
    private Intent intent;
    private Intent intent1;
    private List<HotMovie> result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detailsofcinema;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        adapter = new FlowAdapter(this);
        intent1 = new Intent(this, Choose_Seat.class);
        flow.setAdapter(adapter);
        presenter = new FindMoviePresenter(new FindMovie());
        yid = intent.getIntExtra("yid", 0);
        presenter.request(yid);
        String image = intent.getStringExtra("image");
        image_sim.setImageURI(image);
        String name = intent.getStringExtra("name");
        title.setText(name);
        String address = intent.getStringExtra("address");
        content.setText(address);
        intent1.putExtra("FimlName", name);
        intent1.putExtra("FimlAddress", address);
        dtPresenter = new DTPresenter(new DT());

        flow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                //请求当前影院当前电影的排场
                dtPresenter.request(yid, result.get(position).getId());
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xreceycelview.setLayoutManager(layoutManager);
        adapters = new TimesAdapter(this);
        xreceycelview.setAdapter(adapters);
    }

    @Override
    protected void destoryData() {

    }
    @OnClick(R.id.content)
    public void Content() {
        fragmentFeedCmt = new FragmentFeedCmt();
        String s = String.valueOf(yid);
        Log.e("zasx", s);
        EventBus.getDefault().postSticky(s);
        if (fragmentFeedCmt == null) {
            fragmentFeedCmt = FragmentFeedCmt.newInstance(123L);
        }
        fragmentFeedCmt.show(getSupportFragmentManager(), "Dialog");
    }


    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
    }




    private class FindMovie implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            result = data.getResult();
            dtPresenter.request(yid, result.get(0).getId());
            adapter.setData(result);
            adapter.setIntent(intent1);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class DT implements DataCall<Result<List<Chose_Session_Bean>>> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void success(Result<List<Chose_Session_Bean>> data) {
            List<Chose_Session_Bean> result = data.getResult();
            intent = new Intent(DetailsofcinemaActivity.this, Choose_Seat.class);
            adapters.setList(result);
            adapters.setIntent(intent1);

            adapters.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
