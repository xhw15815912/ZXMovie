package movie.bw.com.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.SystemInfoBean;

/**
 * Created by zxk
 * on 2019/1/26 16:05
 * QQ:666666
 * Describe:
 */
public class SystemInfoAdapter extends RecyclerView.Adapter<SystemInfoAdapter.Vh> {
    private List<SystemInfoBean> list=new ArrayList<>();
    private Context context;

    public SystemInfoAdapter(Context context) {
        this.context = context;
    }
     public void addItem(List<SystemInfoBean> beans){
         if (beans != null) {
             list.clear();
             list.addAll(beans);
         }

     }
    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sys_item, viewGroup, false);
        return new Vh(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        SystemInfoBean bean = list.get(i);
        vh.buy_piao.setText(bean.getTitle());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = format1.format(bean.getPushTime());
        vh.buy_coontent.setText(bean.getContent());
        vh.buy_time.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Vh extends RecyclerView.ViewHolder{

        private final TextView buy_piao;
        private final TextView buy_coontent;
        private final TextView buy_time;

        public Vh(@NonNull View itemView) {
            super(itemView);
            buy_piao = itemView.findViewById(R.id.buy_piao);
            buy_coontent = itemView.findViewById(R.id.buy_coontent);
            buy_time = itemView.findViewById(R.id.buy_time);
        }
    }
}
