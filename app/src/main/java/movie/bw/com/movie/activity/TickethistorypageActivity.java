package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.OffthestocksAdapter;
import movie.bw.com.movie.adapter.PaymentonbehalfofothersAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.TheticketrecordBean;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.FindHotMovieListPresenter;
import movie.bw.com.movie.p.TheticketrecordPresenter;

public class TickethistorypageActivity extends BaseActivity {


    @BindView(R.id.recommend)
    RadioButton recommend;
    @BindView(R.id.nearby)
    RadioButton nearby;
    @BindView(R.id.theaters_xrecyclerview)
    RecyclerView theatersXrecyclerview;
    private TheticketrecordPresenter presenter;
    private PaymentonbehalfofothersAdapter adapter;
    private OffthestocksAdapter offthestocksAdapter;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tickethistorypage;
    }

    @Override
    protected void initView() {
        if (list!=null&&list.size()>0){
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        presenter = new TheticketrecordPresenter(new THET());
        presenter.request(userId,sessionId,1,5,1);
        initData();

    }

    private void initData() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatersXrecyclerview.setLayoutManager(layoutManager);
        adapter = new PaymentonbehalfofothersAdapter(this);
        theatersXrecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initData1() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatersXrecyclerview.setLayoutManager(layoutManager);
        offthestocksAdapter = new OffthestocksAdapter(this);
        theatersXrecyclerview.setAdapter(adapter);
        offthestocksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R.id.recommend, R.id.nearby,R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend:
                presenter.request(userId,sessionId,1,5,1);
                initData();
                break;
            case R.id.nearby:
                presenter.request(userId,sessionId,1,5,2);
                initData1();
                break;
            case R.id.finish:
                finish();
                break;
        }
    }

    private class THET implements DataCall<Result<List<TheticketrecordBean>>> {
        @Override
        public void success(Result<List<TheticketrecordBean>> data) {
              if (data.getStatus().equals("0000")){
                  Toast.makeText(TickethistorypageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                  List<TheticketrecordBean> result = data.getResult();
                  Log.e("wodewodewode",result.size()+",,,");
                  if (recommend.isChecked()){
                      adapter.addItem(result);
                      adapter.notifyDataSetChanged();

                  }else if (nearby.isChecked()){
                      offthestocksAdapter.addItem(result);
                      offthestocksAdapter.notifyDataSetChanged();
                  }


              }else {
                  Toast.makeText(TickethistorypageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();

              }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
