package movie.bw.com.movie.adapter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.xml.sax.helpers.LocatorImpl;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.MovieDetails;
import movie.bw.com.movie.activity.MovierAplyAdapter;
import movie.bw.com.movie.bean.FindCommentReplyBean;
import movie.bw.com.movie.bean.MoviewCommentBean;
import movie.bw.com.movie.bean.PinglunBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.frag.MovieInpuDialog;
import movie.bw.com.movie.p.FindCommentReplyPresenter;


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
    private int user;
    private String session;

    Result<List<FindCommentReplyBean>> data;
    private int id;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
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
        viewHolder.q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinglunBean pinglunBean = new PinglunBean();
                pinglunBean.setId(id);
                pinglunBean.setName(list.get(i).getCommentUserName());
                EventBus.getDefault().postSticky(pinglunBean);
                MovieInpuDialog movieInpuDialog = new MovieInpuDialog(context);
                Window window = movieInpuDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                //设置软键盘通常是可见的
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                movieInpuDialog.show();
                movieInpuDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        EventBus.getDefault().postSticky("1");
                    }
                });
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

    public void setUser(int userId, String sessionId) {
        this.user=userId;
        this.session=sessionId;
    }

    public void setid(int id) {
        this.id=id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        TextView name,message,time,num,great;
        ImageView praise,q;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
            time=itemView.findViewById(R.id.time);
            num=itemView.findViewById(R.id.num);
            praise=itemView.findViewById(R.id.praise);
            great=itemView.findViewById(R.id.great);
            q=itemView.findViewById(R.id.q);
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


    private class Comments implements DataCall<Result<List<FindCommentReplyBean>>> {
        private Result<List<FindCommentReplyBean>> data;

        @Override
        public void success(Result<List<FindCommentReplyBean>> data) {
           Toast.makeText(context,"qazxsw"+list.get(0).getCommentUserName(),Toast.LENGTH_LONG).show();
            this.data=data;
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
