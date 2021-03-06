package movie.bw.com.movie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.HotMovie;

/**
 * 作者：夏洪武
 * 时间：2019/1/24.
 * 邮箱：
 * 说明：
 */
public class FlowAdapter extends RecyclerView.Adapter<FlowAdapter.ViewHolder> {
    private final FragmentActivity context;
    private List<HotMovie> list;
    private Intent intent1;

    public FlowAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
        intent1=new Intent();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.flowmoview_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String imageUrl = list.get(i).getImageUrl();
        String name = list.get(i).getName();
        viewHolder.image.setImageURI(imageUrl);
        viewHolder.name.setText(name);
        intent1.putExtra("MovieNmae",list.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClickListener.onItemClick(list.get(i).getId());
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("id",list.get(i).getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<HotMovie> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }

    }

    public void setIntent(Intent intent1) {
        this.intent1=intent1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        TextView name,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
        }
    }
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int cinemaId);
    }

    //方法名
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
