package movie.bw.com.movie.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import movie.bw.com.movie.adapter.JiaoZiAdapter;
import movie.bw.com.movie.adapter.MovieReviewAdapter;
import movie.bw.com.movie.adapter.PhotoAdapter;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.MoviewCommentBean;
import movie.bw.com.movie.bean.ParticularsBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.frag.InputDialog;
import movie.bw.com.movie.frag.MovieInpuDialog;
import movie.bw.com.movie.p.FilmPresenter;
import movie.bw.com.movie.p.MovieReview_Presenter;
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
    @BindView(R.id.pay)
    Button pay;
    private int id;
    private ParticularsPresenter particularsPresenter;
    private ParticularsBean result;
    private Dialog dialog;
    private JiaoZiAdapter jiaoZiAdapter;
    private PhotoAdapter photoAdapter;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieReview_Presenter movieReview_presenter;
    private String sessionId;
    private int userId;
    private MovieInpuDialog inputDialog;
    private FilmPresenter filmPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initView() {
        filmPresenter = new FilmPresenter(new FILE());
        dialog = new Dialog(this,R.style.DialogTheme);
        jiaoZiAdapter = new JiaoZiAdapter(this);
        photoAdapter = new PhotoAdapter(this);
        movieReviewAdapter = new MovieReviewAdapter(this);
        movieReview_presenter = new MovieReview_Presenter(new Review());
        movieReviewAdapter.setOnItemClickListener(new MovieReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int commentId) {
                filmPresenter.request(userId,sessionId,commentId);
            }
        });

        Intent intent = getIntent();
        if (list!=null&&list.size()>0){
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }

        id = intent.getIntExtra("id", 0);
        particularsPresenter = new ParticularsPresenter(new CallBack());
        if (id != 0) {
            particularsPresenter.request(userId, sessionId, id);
        }
        movieReview_presenter.request(userId,sessionId,id);
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
        dialogWindow.setWindowAnimations(R.style.main_menu_animStyle);
        String imageUrl = result.getImageUrl();
        SimpleDraweeView image = inflate.findViewById(R.id.image);
        image.setImageURI(imageUrl);
        ImageView bak = inflate.findViewById(R.id.bak);
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
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @OnClick(R.id.show)
    public void Show(){
        View inflate = View.inflate(this, R.layout.dia_foreshow, null);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.main_menu_animStyle);
        ImageView bak = inflate.findViewById(R.id.bak);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                JZVideoPlayer.releaseAllVideos();
            }
        });
        RecyclerView recy = inflate.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        jiaoZiAdapter.setData(result.getShortFilmList());
        recy.setAdapter(jiaoZiAdapter);
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                JZVideoPlayer.releaseAllVideos();
            }
        });
    }
    @OnClick(R.id.photo)
    public void Photo(){
        View inflate = View.inflate(this, R.layout.dia_photo, null);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.main_menu_animStyle);
        ImageView bak = inflate.findViewById(R.id.bak);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView recy = inflate.findViewById(R.id.recy);
        recy.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        photoAdapter.setData(result.getPosterList());
        recy.setAdapter(photoAdapter);
        dialog.show();


    }
    @OnClick(R.id.comment)
    public void Cooment(){

        View inflate = View.inflate(this, R.layout.dia_movie_review, null);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getScreenHeight(MovieDetails.this) / 2));
        dialog.setContentView(inflate);
        ImageView write=inflate.findViewById(R.id.write);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.main_menu_animStyle);
        ImageView bak = inflate.findViewById(R.id.bak);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView recy = inflate.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
         ImageView back=inflate.findViewById(R.id.back);
        //movieReviewAdapter.setData(result.getPosterList());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(String.valueOf(id));
                inputDialog = new MovieInpuDialog(MovieDetails.this);
                Window window = inputDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                //设置软键盘通常是可见的
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                inputDialog.show();
                inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        movieReview_presenter.request(userId,sessionId,id);
                    }
                });
            }
        });

        recy.setAdapter(movieReviewAdapter);
        dialog.show();


    }@OnClick(R.id.pay)
    public void Pay(){
        Intent intent = new Intent(this, Pay_Chose_Film.class);
        intent.putExtra("id",id);
        intent.putExtra("name",result.getName());
        startActivity(intent);
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

    private class Review implements DataCall<Result<List<MoviewCommentBean>>> {
        @Override
        public void success(Result<List<MoviewCommentBean>> data) {
             if (data.getStatus().equals("0000")){
                 movieReviewAdapter.setData(data.getResult());
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    private class FILE implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MovieDetails.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                movieReview_presenter.request(userId,sessionId,id);
            }else {
                Toast.makeText(MovieDetails.this, data.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
