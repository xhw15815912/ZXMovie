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

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.MyCinemaBean;
import movie.bw.com.movie.bean.MyInterestBean;

/**
 * Created by zxk
 * on 2019/1/26 11:44
 * QQ:666666
 * Describe:
 */
public class MyInterestAdaper extends RecyclerView.Adapter<MyInterestAdaper.VH> {
    private List<MyCinemaBean> list = new ArrayList<>();
    private Context context;


    public MyInterestAdaper(Context context) {
        this.context = context;
    }

    public void addITem(List<MyCinemaBean> beans) {
        if (beans != null) {
            list.clear();
            list.addAll(beans);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cinema_item_laypout, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        MyCinemaBean bean = list.get(i);
        vh.item_image.setImageURI(bean.getLogo());
        vh.item_titl.setText(bean.getName());
        vh.item_site.setText(bean.getAddress());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final TextView item_titl;
        private final SimpleDraweeView item_image;
        private final TextView item_site;

        public VH(@NonNull View itemView) {
            super(itemView);
            item_titl = itemView.findViewById(R.id.item_title);

            item_image = itemView.findViewById(R.id.item_image);
            item_site = itemView.findViewById(R.id.item_site);
        }
    }
}
