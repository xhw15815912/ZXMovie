package movie.bw.com.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;

public class Choose_Seat extends BaseActivity {


    @BindView(R.id.FilmName)
    TextView FilmName;
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
    private double price;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_seat;
    }

    @Override
    protected void initView() {
        w.setBackgroundColor(0x77ffffff);
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中
        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String end = intent.getStringExtra("end");
        String hall = intent.getStringExtra("hall");
        String FimlAddress = intent.getStringExtra("FimlAddress");
        String FimlName = intent.getStringExtra("FimlName");
        String MovieNmae = intent.getStringExtra("MovieNmae");
        price = intent.getDoubleExtra("price",1);
        FilmName.setText(FimlName);
        FilmPlace.setText(FimlAddress);
        MovieName.setText(MovieNmae);
        time.setText(start+"-"+end);
        type.setText(hall);
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                Log.e("www",row+"=000="+column+"=000=");
            }

            @Override
            public void unCheck(int row, int column) {
     
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);
    }

    @OnClick(R.id.back)
    public void Back(){
        finish();
    }
    @Override
    protected void destoryData() {

    }

}
