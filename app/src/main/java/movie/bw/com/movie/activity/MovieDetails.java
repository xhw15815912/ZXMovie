package movie.bw.com.movie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.ParticularsPresenter;

public class MovieDetails extends BaseActivity {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.photo)
    Button photo;
    @BindView(R.id.comment)
    Button comment;
    @BindView(R.id.back)
    ImageView back;

    private int id;
    private ParticularsPresenter particularsPresenter;
    private ParticularsBean result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        String sessionId = USER.getSessionId();
        int userId = USER.getUserId();
        id = intent.getIntExtra("id", 0);
        particularsPresenter = new ParticularsPresenter(new CallBack());
        if (id != 0) {
            particularsPresenter.request(userId, sessionId, id);
        }
    }

    @Override
    protected void destoryData() {

    }
    @OnClick(R.id.back)
    public void Bk(){
        finish();
    }
    @OnClick(R.id.chat)
    public void Chat(){



        Dialog dialog = new Dialog(this);
        View inflate = View.inflate(this, R.layout.pop_movie_chat, null);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.BOTTOM);
        String imageUrl = result.getImageUrl();
        SimpleDraweeView image = inflate.findViewById(R.id.image);
        image.setImageURI(imageUrl);
        TextView type = inflate.findViewById(R.id.type);
        TextView men = inflate.findViewById(R.id.men);
        TextView time = inflate.findViewById(R.id.time);
        TextView place = inflate.findViewById(R.id.place);
        TextView chat = inflate.findViewById(R.id.chat);
        RecyclerView recy =inflate.findViewById(R.id.recy);
        type.setText("类型："+result.getMovieTypes());
        men.setText("导演："+result.getDirector());
        time.setText("时长："+result.getDuration());
        place.setText("产地："+result.getPlaceOrigin());
        chat.setText(result.getSummary());
        dialog.show();
    }
    private class CallBack implements DataCall<Result<ParticularsBean>> {
        @Override
        public void success(Result<ParticularsBean> data) {
            Log.e("错误",data.getMessage()+"");
            if (data.getStatus().equals("0000")){
                result = data.getResult();
                name.setText(result.getName());
                image.setImageURI(result.getImageUrl());

            }
        }

        @Override
        public void fail(ApiException e) {
            Log.e("错误",e+"");
        }
    }
}
