package movie.bw.com.movie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import movie.bw.com.movie.activity.DetailsofcinemaActivity;
import movie.bw.com.movie.bean.Recommend;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.bean.UserInfo;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.AttentiontocinemaPresenter;

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
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        final Recommend recommend = list.get(i);
        vh.item_title.setText(recommend.getName());
        vh.item_image.setImageURI(recommend.getLogo());
        vh.item_content.setText(recommend.getAddress());
        vh.item_km.setText(recommend.getDistance()+"m");

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,DetailsofcinemaActivity.class);
                intent.putExtra("image",recommend.getLogo());
                intent.putExtra("name",(recommend.getName()));
                intent.putExtra("address",recommend.getAddress());
                intent.putExtra("yid",recommend.getId());

                context.startActivity(intent);
            }
        });
        vh.item_mind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 onItemClickListener.onItemClick(recommend.getId(),recommend.getFollowCinema());
            }
        });
        if (recommend.getFollowCinema()==1){
            vh.item_mind.setImageResource(R.mipmap.com_icon_collection);
        }else if (recommend.getFollowCinema()==2){
            vh.item_mind.setImageResource(R.mipmap.weiguanzhu);
        }
    }

    @Override
    public int getItemCount() {
        return list.    size();
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


    private class ZAN implements DataCall<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int cinemaId, int isFollow);
    }

    //方法名
    private OnItemClickListener onItemClickListener;

    //方法      设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
