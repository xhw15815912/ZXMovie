package movie.bw.com.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.MoviewCommentBean;

/**
 * 作者：夏洪武
 * 时间：2019/1/25.
 * 邮箱：
 * 说明：
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {
    private final MovieDetails context;
    private List<MoviewCommentBean> list;

    public MovieReviewAdapter(MovieDetails movieDetails) {
        this.context=movieDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.dia_movie_review, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MoviewCommentBean> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
