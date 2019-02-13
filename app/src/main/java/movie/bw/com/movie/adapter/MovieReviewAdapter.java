package movie.bw.com.movie.adapter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.xml.sax.helpers.LocatorImpl;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.bean.MoviewCommentBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.frag.MovieInpuDialog;


/**
 * 作者：夏洪武
 * 时间：2019/1/25.
 * 邮箱：
 * 说明：
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {
    private final MovieDetails context;
    private List<MoviewCommentBean> list;
    private boolean isFow;
    public MovieReviewAdapter(MovieDetails movieDetails) {
        this.context=movieDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.moview_comment_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.head.setImageURI(list.get(i).getCommentHeadPic());
        viewHolder.message.setText(list.get(i).getCommentContent());
        final MoviewCommentBean bean = list.get(i);
        final int commentId = bean.getCommentId();
        viewHolder.name.setText(list.get(i).getCommentUserName());
        viewHolder.time.setText(list.get(i).getCommentTime()+"");
        viewHolder.num.setText(list.get(i).getReplyNum()+"");
        viewHolder.great.setText(list.get(i).getGreatNum()+"");
        viewHolder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(commentId);
            }
        });


        if (isFow){
            isFow=true;
            viewHolder.praise.setImageResource(R.mipmap.com_icon_praise_selected);

        }else {
            isFow=true;
            viewHolder.praise.setImageResource(R.mipmap.com_icon_praise_default);

        }
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
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int commentId);
    }

    //方法名
    private  OnItemClickListener onItemClickListener;

    //方法      设置点击方法
    public void setOnItemClickListener( OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
