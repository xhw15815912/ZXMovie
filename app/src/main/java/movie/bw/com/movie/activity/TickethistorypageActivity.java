package movie.bw.com.movie.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
import movie.bw.com.movie.p.TheticketrecordPresenter;
import movie.bw.com.movie.p.WxPay_Presenter;

public class TickethistorypageActivity extends BaseActivity {


    @BindView(R.id.recommend)
    RadioButton recommend;
    @BindView(R.id.nearby)
    RadioButton nearby;
    @BindView(R.id.theaters_xrecyclerview)
    RecyclerView theatersXrecyclerview;
    @BindView(R.id.weixin_radio_button)
    RadioButton weixinRadioButton;
    @BindView(R.id.zhifubao_radio_button)
    RadioButton zhifubaoRadioButton;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.zifu)
    TextView zifu;
    @BindView(R.id.cenggong)
    RelativeLayout cenggong;
    private TheticketrecordPresenter presenter;
    private PaymentonbehalfofothersAdapter adapter;
    private OffthestocksAdapter offthestocksAdapter;
    private int userId;
    private String sessionId;
    private WxPay_Presenter wxPay_presenter;
    private int payType = 1;
    private String dingdan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tickethistorypage;
    }

    @Override
    protected void initView() {
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        presenter = new TheticketrecordPresenter(new THET());
        presenter.request(userId, sessionId, 1, 5, 1);
        initData();
        adapter.setOnItemClickListener(new PaymentonbehalfofothersAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(String id, double price) {
                wxPay_presenter = new WxPay_Presenter(new Pay());
                cenggong.setVisibility(View.VISIBLE);
                dingdan = id;
                zifu.setText("支付" + price + "元");
            }
        });
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatersXrecyclerview.setLayoutManager(layoutManager);
        adapter = new PaymentonbehalfofothersAdapter(this);
        theatersXrecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData1() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatersXrecyclerview.setLayoutManager(layoutManager);
        offthestocksAdapter = new OffthestocksAdapter(this);
        theatersXrecyclerview.setAdapter(offthestocksAdapter);
        offthestocksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.recommend, R.id.finishssss, R.id.nearby, R.id.finish, R.id.zifu, R.id.radiogroup, R.id.weixin_radio_button, R.id.zhifubao_radio_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend:
                presenter.request(userId, sessionId, 1, 5, 1);
                initData();
                break;
            case R.id.nearby:
                presenter.request(userId, sessionId, 1, 5, 2);
                initData1();
                break;
            case R.id.finish:
                finish();
                break;
            case R.id.weixin_radio_button:
                if (weixinRadioButton.isChecked()) {
                    payType = 1;
                }
                break;
            case R.id.zifu:
                if (weixinRadioButton.isChecked()) {
                    wxPay_presenter.request(userId, sessionId, payType, dingdan);

                } else if (zhifubaoRadioButton.isChecked()) {
                    Toast.makeText(TickethistorypageActivity.this, "暂不支持支付宝，请用微信支付", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.zhifubao_radio_button:
                if (zhifubaoRadioButton.isChecked()) {
                    payType = 2;
                }
                break;
            case R.id.finishssss:
                cenggong.setVisibility(View.GONE);
                break;
        }
    }


    private class THET implements DataCall<Result<List<TheticketrecordBean>>> {
        @Override
        public void success(Result<List<TheticketrecordBean>> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(TickethistorypageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                List<TheticketrecordBean> result = data.getResult();
                Log.e("wodewodewode", result.size() + ",,,");
                if (recommend.isChecked()) {
                    adapter.addItem(result);
                    adapter.notifyDataSetChanged();
                } else if (nearby.isChecked()) {
                    offthestocksAdapter.addItem(result);
                    offthestocksAdapter.notifyDataSetChanged();
                }


            } else {
                Toast.makeText(TickethistorypageActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
        }
    }

    private class Pay implements DataCall<Result> {
        @Override
        public void success(Result data) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(TickethistorypageActivity.this, null);
            // 将该app注册到微信
            Log.e("qwer", data.getAppId() + "====");
            msgApi.registerApp("wxb3852e6a6b7d9516");
            PayReq request = new PayReq();
            request.appId = data.getAppId();
            request.partnerId = data.getPartnerId();
            request.prepayId = data.getPrepayId();
            request.packageValue = data.getPackageValue();
            request.nonceStr = data.getNonceStr();
            request.timeStamp = data.getTimeStamp();
            request.sign = data.getSign();
            msgApi.sendReq(request);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}