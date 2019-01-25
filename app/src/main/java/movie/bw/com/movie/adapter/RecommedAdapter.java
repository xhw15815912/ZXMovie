package movie.bw.com.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.Recommend;

/**
 * Created by zxk
 * on 2019/1/24 15:16
 * QQ:666666
 * Describe:
 */
public class RecommedAdapter extends RecyclerView.Adapter<RecommedAdapter.VH> {
    private List<Recommend> list = new ArrayList<>();
    private Context context;

    public RecommedAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<Recommend> recommends) {
        if (recommends != null) {
            list.clear();
            list.addAll(recommends);
        }
    }public void deleteItem(List<Recommend> recommends) {
        if (recommends != null) {
            recommends.clear();
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Recommend recommend = list.get(i);
        vh.item_title.setText(recommend.getName());
        vh.item_image.setImageURI(recommend.getLogo());
        vh.item_content.setText(recommend.getAddress());
        vh.item_km.setText(recommend.getDistance()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView item_image;
        private final TextView item_content;
        private final TextView item_title;
        private final TextView item_km;
        private final ImageView item_mind;

        public VH(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_content = itemView.findViewById(R.id.item_content);
            item_title = itemView.findViewById(R.id.item_title);
            item_km = itemView.findViewById(R.id.item_km);
            item_mind = itemView.findViewById(R.id.item_mind);
        }
    }
}
