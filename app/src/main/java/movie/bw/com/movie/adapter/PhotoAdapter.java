package movie.bw.com.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MovieDetails;

/**
 * 作者：夏洪武
 * 时间：2019/1/25.
 * 邮箱：
 * 说明：
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private final MovieDetails context;
    private List<String> list;

    public PhotoAdapter(MovieDetails movieDetails) {
        this.context=movieDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.details_photo_item,null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i)).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<String> posterList) {

            this.list=posterList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
