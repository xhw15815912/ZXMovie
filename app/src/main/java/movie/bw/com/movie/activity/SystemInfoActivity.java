package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.SystemInfoAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.SystemInfoBean;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.SysPresenter;

public class SystemInfoActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private SysPresenter sysPresenter;
    private int userId;
    private String sessionId;
    private SystemInfoAdapter systemInfoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_info;
    }

    @Override
    protected void initView() {
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }

        sysPresenter = new SysPresenter(new Sys());
        sysPresenter.request(userId,sessionId,1,10);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        systemInfoAdapter = new SystemInfoAdapter(this);
        recyclerview.setAdapter(systemInfoAdapter);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.image_return01)
    public void onViewClicked() {
        finish();
    }

    private class Sys implements DataCall<Result<List<SystemInfoBean>>> {
        @Override
        public void success(Result<List<SystemInfoBean>> data) {
            if (data.getStatus().equals("0000")){
                List<SystemInfoBean> list = data.getResult();
                systemInfoAdapter.addItem(list);
                systemInfoAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
