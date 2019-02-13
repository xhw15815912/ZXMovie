package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.BuyMovieTicketPresenter;
import movie.bw.com.movie.p.WxPay_Presenter;

public class Choose_Seat extends BaseActivity {


    @BindView(R.id.FilmName)
    TextView FilmName;
    @BindView(R.id.zifu)
    TextView zifu;
    @BindView(R.id.FilmPlace)
    TextView FilmPlace;
    @BindView(R.id.MovieName)
    TextView MovieName;
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.seatView)
    SeatTable seatTableView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.w)
    LinearLayout w;
    @BindView(R.id.di)
    LinearLayout di;
    @BindView(R.id.s)
    ImageView s;
    @BindView(R.id.e)
    LinearLayout e;
    @BindView(R.id.zong)
    TextView zong;
    @BindView(R.id.finishssss)
    ImageView finishssss;
    @BindView(R.id.weixin_radio_button)
    RadioButton weixinRadioButton;
    @BindView(R.id.zhifubao_radio_button)
    RadioButton zhifubaoRadioButton;
    @BindView(R.id.cenggong)
    RelativeLayout cenggong;
    private double price;
    private double price1;
    private int num = 0;
    private int id;
    private int userId;
    private String sessionId;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;
    private String sss;
    private WxPay_Presenter wxPay_presenter;
    private String orderId;
    IWXAPI api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_seat;
    }

    @Override
    protected void initView() {
        wxPay_presenter = new WxPay_Presenter(new Pay());
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        w.setBackgroundColor(0x77ffffff);
        seatTableView = (SeatTable) findViewById(R.id.seatView);

        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String end = intent.getStringExtra("end");
        String hall = intent.getStringExtra("hall");
        String FimlAddress = intent.getStringExtra("FimlAddress");
        String FimlName = intent.getStringExtra("FimlName");
        String MovieNmae = intent.getStringExtra("MovieNmae");
        id = intent.getIntExtra("id", 0);
        Log.d("排期表id——————————————", "initView: " + id);
        price = intent.getDoubleExtra("price", 1);
        FilmName.setText(FimlName);
        FilmPlace.setText(FimlAddress);
        MovieName.setText(MovieNmae);
        time.setText(start + "-" + end);
        type.setText(hall);
        seatTableView.setScreenName(hall);//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中
        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new Buy());

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                Log.e("www", row + "=000=" + column + "=000=");
                back.setVisibility(View.GONE);
                di.setVisibility(View.VISIBLE);
                num++;
                price1 = num * price;
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(2);
                zong.setText(nf.format(price1));
                zifu.setText("微信支付" +nf.format(price1) + "元");
            }

            @Override
            public void unCheck(int row, int column) {
                num--;

                if (num == 0) {
                    back.setVisibility(View.VISIBLE);
                    di.setVisibility(View.GONE);
                }

                price1 = num * price;
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(2);
                zong.setText(nf.format(price1));
                zifu.setText("微信支付" +nf.format(price1) + "元");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
    }

    /**
     * MD5加密
     *
     * @param
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @OnClick(R.id.back)
    public void Back() {
        finish();
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.affirm, R.id.cancel, R.id.weixin_radio_button,R.id.finishssss, R.id.zhifubao_radio_button, R.id.zifu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.affirm:
                if (USER == null) {
                    Intent intent = new Intent(Choose_Seat.this, MainActivity.class);
                    intent.putExtra("id", 1);
                    startActivity(intent);
                }
                String sourceStr = userId + "" + id + "" + num + "movie";
                sss = MD5(sourceStr);
                buyMovieTicketPresenter.request(userId, sessionId, id, num, sss);
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.weixin_radio_button:
                zifu.setText("微信支付" + price1 + "元");
                break;
            case R.id.zhifubao_radio_button:
                zifu.setText("支付宝支付" + price1 + "元");
                break;
            case R.id.finishssss:
                cenggong.setVisibility(View.GONE);
                di.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                break;
            case R.id.zifu:
                if (weixinRadioButton.isChecked()) {
                    wxPay_presenter.request(userId, sessionId, 1, orderId);
                } else if (zhifubaoRadioButton.isChecked()) {
                    Toast.makeText(this, "现在不支持支付宝，建议使用微信", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "至少要选择一个支付方式", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private class Buy implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Log.e("qwer2", 111 + "====");
                Toast.makeText(Choose_Seat.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                cenggong.setVisibility(View.VISIBLE);
                di.setVisibility(View.GONE);
                orderId = data.getOrderId();
                Log.e("qwer1", orderId + "====");
            } else {
                Toast.makeText(Choose_Seat.this, data.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Pay implements DataCall<Result> {
        @Override
        public void success(Result data) {

            final IWXAPI msgApi = WXAPIFactory.createWXAPI(Choose_Seat.this, null);
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
