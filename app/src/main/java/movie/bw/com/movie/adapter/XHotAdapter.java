package movie.bw.com.movie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MoreMovie;
import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.HotMovie;

/**
 * 作者：夏洪武
 * 时间：2019/1/24.
 * 邮箱：
 * 说明：
 */
public class XHotAdapter extends XRecyclerView.Adapter<XHotAdapter.ViewHolder> {
    private final MoreMovie context;
    private List<HotMovie> list;

    public XHotAdapter(MoreMovie moreMovie) {
        this.context=moreMovie;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.morex_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.message.setText(list.get(i).getSummary());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.image.setImageURI(list.get(i).getImageUrl());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("id",list.get(i).getId());
                context.startActivity(intent);
            }
        });;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        TextView name,message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
        }
    }
}
