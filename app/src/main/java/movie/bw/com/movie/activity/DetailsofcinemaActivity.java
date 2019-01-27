package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detailsofcinema;
    }

    @Override
    protected void initView() {

        adapter = new FlowAdapter(this);

        flow.setAdapter(adapter);
        presenter = new FindMoviePresenter(new FindMovie());
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        image_sim.setImageURI(image);
        String name = intent.getStringExtra("name");
        title.setText(name);
        String address = intent.getStringExtra("address");
        content.setText(address);
        final int yid = intent.getIntExtra("yid", 0);
        presenter.request(yid);

        flow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

                dtPresenter.request(yid,position);
            }
        });
        dtPresenter = new DTPresenter(new DT());

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xreceycelview.setLayoutManager(layoutManager);
        adapters = new TimesAdapter(this);
        xreceycelview.setAdapter(adapters);
    }

    @Override
    protected void destoryData() {

    }



    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
    }


    private class FindMovie implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            List<HotMovie> result = data.getResult();
            adapter.setData(result);

            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class DT implements DataCall<Result<List<Chose_Session_Bean>>> {
        @Override
        public void success(Result<List<Chose_Session_Bean>> data) {
            List<Chose_Session_Bean> result = data.getResult();
            adapters.setList(result);
            adapters.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}