package movie.bw.com.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.TheticketrecordBean;

/**
 * Created by zxk
 * on 2019/1/29 14:27
 * QQ:666666
 * Describe:
 */
public class OffthestocksAdapter extends RecyclerView.Adapter<OffthestocksAdapter.VH> {
    private List<TheticketrecordBean> list=new ArrayList<>();
    private Context context;

    public OffthestocksAdapter(Context context) {
        this.context = context;
    }
    public void addItem(List<TheticketrecordBean> beans){
        if (beans!=null){
            this.list.clear();
            list.addAll(beans);
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.complete_item, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        TheticketrecordBean bean = list.get(i);
        vh.name.setText(bean.getMovieName());
        vh.begin_time.setText(bean.getBeginTime()+"-"+bean.getEndTime());
        vh.odd_hao.setText("订单号："+bean.getOrderId());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = format1.format(bean.getCreateTime());
        vh.cinem.setText("影院："+bean.getCinemaName());
        vh.th_film_office.setText("影厅："+bean.getScreeningHall());
        vh.timea.setText("时间："+format);
        vh.number.setText("数量："+bean.getAmount()+"张");
        vh.money.setText("金额："+bean.getPrice()+"元");
//        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
//        String begin = format2.format(bean.getBeginTime());
//        String end = format2.format(bean.getEndTime());
//        long deadline = Long.parseLong(format);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        private final TextView odd_hao;
        private final TextView begin_time;
        private final TextView cinem;
        private final TextView name;
        private final TextView timea;
        private final TextView number;
        private final TextView money;
        private final TextView th_film_office;

        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            odd_hao = itemView.findViewById(R.id.odd_hao);
            cinem = itemView.findViewById(R.id.cinem);
            begin_time = itemView.findViewById(R.id.begin_time);
            timea = itemView.findViewById(R.id.timea);
            number = itemView.findViewById(R.id.number);
            money = itemView.findViewById(R.id.money);
            th_film_office = itemView.findViewById(R.id.th_film_Office);
        }
    }
}
