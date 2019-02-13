package movie.bw.com.movie.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.DetailsofcinemaBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.FilmInfo_Presenter;
import movie.bw.com.movie.p.Film_Chat_Presenter;


public class XiangFragment extends BaseFragment {

    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.phone)
    TextView phone;
    /*@BindView(R.id.subway)
    TextView subway;
    @BindView(R.id.bus)
    TextView bus;
    @BindView(R.id.car)
    TextView car;*/
    @BindView(R.id.address)
    TextView address;
    Unbinder unbinder;
    private String sessionId;
    private int userId;
    private Film_Chat_Presenter filmInfo_presenter;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xiang;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void FilmNum(String s) {
        Log.e("s",s+"====");
        if (s!=null){
            filmInfo_presenter = new Film_Chat_Presenter(new CallBack());
            filmInfo_presenter.request(s);
        }
    }

    @Override
    protected void initView() {
    }
    private class CallBack implements DataCall<Result<DetailsofcinemaBean>> {
        @Override
        public void success(Result<DetailsofcinemaBean> data) {
            if (data.getStatus().equals("0000")) {
                place.setText(data.getResult().getAddress());
                phone.setText(data.getResult().getPhone());
                address.setText(data.getResult().getVehicleRoute());
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}
