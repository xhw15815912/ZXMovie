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

import static com.umeng.analytics.pro.k.a.b;
import static com.umeng.analytics.pro.k.a.i;
import static com.umeng.analytics.pro.k.a.s;
import static com.umeng.analytics.pro.k.a.v;

/**
 * Created by zxk
 * on 2019/1/29 14:27
 * QQ:666666
 * Describe:
 */
public class PaymentonbehalfofothersAdapter extends RecyclerView.Adapter<PaymentonbehalfofothersAdapter.VH> {
    private List<TheticketrecordBean> list = new ArrayList<>();
    private Context context;

    public PaymentonbehalfofothersAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<TheticketrecordBean> beans) {
        if (beans != null) {
            list.clear();
            list.addAll(beans);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_item, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        final TheticketrecordBean bean = list.get(i);
        vh.name.setText(bean.getMovieName());
        vh.odd_hao.setText("订单号：" + bean.getOrderId());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = format1.format(bean.getCreateTime());
        vh.cinem.setText("影院：" + bean.getCinemaName());
        vh.th_film_office.setText("影厅：" + bean.getScreeningHall());
        vh.timea.setText("时间：" + format);
        vh.number.setText("数量：" + bean.getAmount() + "张");
        vh.money.setText("金额：" + bean.getPrice() + "元");
        vh.go_buymoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(bean.getOrderId(),bean.getPrice());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final Button go_buymoney;
        private final TextView odd_hao;
        private final TextView cinem;
        private final TextView name;
        private final TextView timea;
        private final TextView number;
        private final TextView money;
        private final TextView th_film_office;

        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            go_buymoney = itemView.findViewById(R.id.go_buymoney);
            odd_hao = itemView.findViewById(R.id.odd_hao);
            cinem = itemView.findViewById(R.id.cinem);
            timea = itemView.findViewById(R.id.timea);
            number = itemView.findViewById(R.id.number);
            money = itemView.findViewById(R.id.money);
            th_film_office = itemView.findViewById(R.id.th_film_Office);
        }
    }

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(String id,double price);
    }

    //方法名
    private OnItemClickListener onItemClickListener;

    //方法      设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
