package movie.bw.com.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.ParticularsBean;

/**
 * 作者：夏洪武
 * 时间：2019/1/25.
 * 邮箱：
 * 说明：
 */
public class JiaoZiAdapter extends RecyclerView.Adapter<JiaoZiAdapter.ViewHolder> {
    private final MovieDetails context;
    private List<ParticularsBean.ShortFilmListBean> list;

    public JiaoZiAdapter(MovieDetails movieDetails) {
        this.context=movieDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.jiaozi_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String imageUrl = list.get(i).getImageUrl();
            String videoUrl = list.get(i).getVideoUrl();
            viewHolder.jz.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            Glide.with(context).load(imageUrl)
                    .into(viewHolder.jz.thumbImageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<ParticularsBean.ShortFilmListBean> shortFilmList) {
        if (this.list!=null){
            this.list.clear();
            this.list=shortFilmList;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jz;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jz=itemView.findViewById(R.id.jz);
        }
    }
}
