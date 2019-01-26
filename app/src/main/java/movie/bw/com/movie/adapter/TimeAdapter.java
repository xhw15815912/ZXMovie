package movie.bw.com.movie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import movie.bw.com.movie.activity.Choose_Seat;
import movie.bw.com.movie.activity.Chose_Session;
import movie.bw.com.movie.activity.StartActivity;
import movie.bw.com.movie.bean.Chose_Session_Bean;

/**
 * 作者：夏洪武
 * 时间：2019/1/26.
 * 邮箱：
 * 说明：
 */
public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private final Chose_Session context;
    private List<Chose_Session_Bean> list;

    public TimeAdapter(Chose_Session chose_session) {
        this.context=chose_session;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.time_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
          viewHolder.start.setText(list.get(i).getBeginTime()+"");
          viewHolder.end.setText(list.get(i).getEndTime()+"");
          viewHolder.name.setText(list.get(i).getScreeningHall());
        String price = String.valueOf(list.get(i).getPrice());
        SpannableString spannableString = new SpannableString(price);
        if (price.contains(".")){
            spannableString.setSpan(new RelativeSizeSpan(0.5f), price.indexOf("."), price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        viewHolder.money.setText(spannableString);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,Choose_Seat.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Chose_Session_Bean> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,start,end,money;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            start=itemView.findViewById(R.id.start);
            end=itemView.findViewById(R.id.end);
            money=itemView.findViewById(R.id.money);
        }
    }
}
