package movie.bw.com.movie.adapter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.Chose_Session;
import movie.bw.com.movie.activity.Pay_Chose_Film;
import movie.bw.com.movie.activity.StartActivity;
import movie.bw.com.movie.bean.Pay_Chose_Film_Bean;

/**
 * 作者：夏洪武
 * 时间：2019/1/26.
 * 邮箱：
 * 说明：
 */
public class Pay_Chose_Film_Adapter extends RecyclerView.Adapter<Pay_Chose_Film_Adapter.ViewHolder> {
    private final Pay_Chose_Film context;
    private List<Pay_Chose_Film_Bean> list;

    public Pay_Chose_Film_Adapter(Pay_Chose_Film pay_chose_film) {
        this.context=pay_chose_film;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.pay_chose_film_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         viewHolder.place.setText(list.get(i).getAddress());
         viewHolder.name.setText(list.get(i).getName());
         viewHolder.image.setImageURI(list.get(i).getLogo());
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //传的影院id
                 Intent intent = new Intent(context, Chose_Session.class);
                 intent.putExtra("id",list.get(i).getId());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Pay_Chose_Film_Bean> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        TextView name,place,far;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            place=itemView.findViewById(R.id.place);
            far=itemView.findViewById(R.id.far);
        }
    }
}
