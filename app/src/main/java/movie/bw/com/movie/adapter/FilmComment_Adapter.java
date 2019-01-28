package movie.bw.com.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.FilmCommentBean;
import movie.bw.com.movie.bean.MoviewCommentBean;

/**
 * 作者：夏洪武
 * 时间：2019/1/27.
 * 邮箱：
 * 说明：
 */
public class FilmComment_Adapter extends RecyclerView.Adapter<FilmComment_Adapter.ViewHolder> {
    private final FragmentActivity context;
    private List<FilmCommentBean> list;

    public FilmComment_Adapter(FragmentActivity activity) {
       this.context=activity;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public FilmComment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.moview_comment_item, null);
        return new FilmComment_Adapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmComment_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.head.setImageURI(list.get(i).getCommentHeadPic());

        viewHolder.message.setText(list.get(i).getCommentContent());
        viewHolder.name.setText(list.get(i).getCommentUserName());
        viewHolder.time.setText(list.get(i).getCommentTime()+"");
        viewHolder.num.setText(list.get(i).getHotComment()+"");
        viewHolder.great.setText(list.get(i).getGreatNum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<FilmCommentBean> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        TextView name,message,time,num,great;
        ImageView praise;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
            time=itemView.findViewById(R.id.time);
            num=itemView.findViewById(R.id.num);
            praise=itemView.findViewById(R.id.praise);
            great=itemView.findViewById(R.id.great);
        }
    }
}