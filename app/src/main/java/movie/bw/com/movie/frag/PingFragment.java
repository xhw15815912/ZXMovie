package movie.bw.com.movie.frag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import movie.bw.com.movie.p.Film_Comment;


public class PingFragment extends BaseFragment {

    @BindView(R.id.recy)
    RecyclerView recy;
    Unbinder unbinder;
    private Film_Comment film_comment;
    private FilmComment_Adapter filmComment_adapter;
    private String s;

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
        film_comment = new Film_Comment(new CallBack());
        film_comment.request(s);
        recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        filmComment_adapter = new FilmComment_Adapter(getActivity());
        recy.setAdapter(filmComment_adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void FilmNum(String s) {
        this.s=s;
        film_comment.request(s);
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
        film_comment.request(s);
    }

    @Override
    public void onStart() {
        super.onStart();
        film_comment.request(s);
    }
}
