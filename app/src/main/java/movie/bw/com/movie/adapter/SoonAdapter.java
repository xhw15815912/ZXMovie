package movie.bw.com.movie.adapter;

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

import movie.bw.com.movie.bean.HotMovie;

/**
 * 作者：夏洪武
 * 时间：2019/1/24.
 * 邮箱：
 * 说明：
 */
public class SoonAdapter extends RecyclerView.Adapter<SoonAdapter.ViewHolder> {
    private final FragmentActivity context;
    private List<HotMovie> list;

    public SoonAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public SoonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.hotmovie_item, null);

        return new SoonAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SoonAdapter.ViewHolder viewHolder, int i) {
        String imageUrl = list.get(i).getImageUrl();
        String name = list.get(i).getName();
        viewHolder.image.setImageURI(imageUrl);
        viewHolder.name.setText(name);

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
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
