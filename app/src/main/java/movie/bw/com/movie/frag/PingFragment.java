package movie.bw.com.movie.frag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import movie.bw.com.movie.adapter.FilmComment_Adapter;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.FilmCommentBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.CommentPresenter;
import movie.bw.com.movie.p.Film_Comment;


public class PingFragment extends BaseFragment {

    @BindView(R.id.recy)
    RecyclerView recy;
    private Film_Comment film_comment;
    private FilmComment_Adapter filmComment_adapter;
    private String s;
    private CommentPresenter commentPresenter;
    private String sessionId;
    private int userId;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ping;
    }

    @Override
    protected void initView() {
        if (list!=null&&list.size()>0){
            sessionId = USER.getSessionId();
            userId = USER.getUserId();
        }
        commentPresenter = new CommentPresenter(new Comm());
        film_comment = new Film_Comment(new CallBack());
        if (s!=null){
            film_comment.request(s);
        }
        recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        filmComment_adapter = new FilmComment_Adapter(getActivity());
        recy.setAdapter(filmComment_adapter);
        filmComment_adapter.setOnItemClickListener(new FilmComment_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int cinemaId) {
             commentPresenter.request(userId,sessionId,cinemaId);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void FilmNum(String s) {
        this.s=s;
        if (s!=null){
            film_comment.request(s);
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
    private class CallBack implements DataCall<Result<List<FilmCommentBean>>> {
        @Override
        public void success(Result<List<FilmCommentBean>> data) {
            if (data.getStatus().equals("0000")) {
                filmComment_adapter.setData(data.getResult());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (s!=null){
            film_comment.request(s);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (s!=null){
            film_comment.request(s);
        }
    }

    private class Comm implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                if (s!=null){
                    film_comment.request(s);
                }
                filmComment_adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
