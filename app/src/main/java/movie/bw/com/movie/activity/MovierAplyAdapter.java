package movie.bw.com.movie.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.bean.FindCommentReplyBean;

/**
 * 作者：夏洪武
 * 时间：2019/2/21.
 * 邮箱：
 * 说明：
 */
public class MovierAplyAdapter extends RecyclerView.Adapter<MovierAplyAdapter.ViewHolder> {
    private final MovieDetails activity;
    private List<FindCommentReplyBean> list;

    public MovierAplyAdapter(MovieDetails context) {
        this.activity=context;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(activity, R.layout.moviereply_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
          viewHolder.content.setText(list.get(i).getReplyContent());
          viewHolder.head.setImageURI(list.get(i).getReplyHeadPic());
          viewHolder.name.setText(list.get(i).getReplyUserName());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setList(List<FindCommentReplyBean> result) {
        if (result!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        TextView name,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            content=itemView.findViewById(R.id.content);
        }
    }
}
