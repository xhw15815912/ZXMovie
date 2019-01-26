package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.adapter.Pay_Chose_Film_Adapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.Pay_Chose_Film_Bean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.Pay_Chose_Film_Presenter;

public class Pay_Chose_Film extends BaseActivity {

    @BindView(R.id.movieName)
    TextView movieName;
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.back)
    ImageView back;
    private int id;
    private Pay_Chose_Film_Presenter pay_chose_film_presenter;
    private Pay_Chose_Film_Adapter pay_chose_film_adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_chose_film;
    }

    @Override
    protected void initView() {
        pay_chose_film_adapter = new Pay_Chose_Film_Adapter(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        EventBus.getDefault().postSticky(id+"");
        String intExtra = intent.getStringExtra("name");
        pay_chose_film_presenter = new Pay_Chose_Film_Presenter(new CallBack());
        pay_chose_film_presenter.request(id);
        movieName.setText(intExtra);
        recy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recy.setAdapter(pay_chose_film_adapter);
    }

    @OnClick(R.id.back)
    public void Back(){
        finish();
    }
    @Override
    protected void destoryData() {

    }



    private class CallBack implements DataCall<Result<List<Pay_Chose_Film_Bean>>> {
        @Override
        public void success(Result<List<Pay_Chose_Film_Bean>> data) {
            Log.e("影院",data.getMessage());
            if (data.getStatus().equals("0000")) {
                pay_chose_film_adapter.setData(data.getResult());

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
