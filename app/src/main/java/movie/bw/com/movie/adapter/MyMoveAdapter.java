package movie.bw.com.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.MyInterestBean;
import movie.bw.com.movie.bean.Result;

/**
 * Created by zxk
 * on 2019/1/26 11:58
 * QQ:666666
 * Describe:
 */
public class MyMoveAdapter extends RecyclerView.Adapter<MyMoveAdapter.Vh> {
    private List<MyInterestBean> list = new ArrayList<>();
    private Context context;

    public MyMoveAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<MyInterestBean> myInterestBeans) {
        if (myInterestBeans != null) {
            list.clear();
            list.addAll(myInterestBeans);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mivoe_layout, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        MyInterestBean myInterestBean = list.get(i);
        vh.item_content.setText(myInterestBean.getSummary());
        vh.item_image_move.setImageURI(myInterestBean.getImageUrl());
        vh.item_name.setText(myInterestBean.getName());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = format1.format(myInterestBean.getReleaseTime());
        vh.item_time.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        private final SimpleDraweeView item_image_move;
        private final TextView item_name;
        private final TextView item_time;
        private final TextView item_content;

        public Vh(@NonNull View itemView) {
            super(itemView);
            item_image_move = itemView.findViewById(R.id.item_image_move);
            item_name = itemView.findViewById(R.id.item_name);
            item_time = itemView.findViewById(R.id.item_time);
            item_content = itemView.findViewById(R.id.item_content);
        }
    }
}
