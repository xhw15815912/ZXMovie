package movie.bw.com.movie.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.DaoSession;
import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.bean.UserBean;


/**
 * 作者：夏洪武
 * 时间：2019/1/22.
 * 邮箱：
 * 说明：
 */
public abstract class BaseFragment  extends Fragment implements CustomAdapt {
    public Gson mGson = new Gson();


    private Unbinder unbinder;
    //public UserInfo LOGIN_USER;
    public UserBean USER;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AutoSizeConfig.getInstance().setCustomFragment(true);




        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserBeanDao.TABLENAME);
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        List<UserBean> list = userBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Register.eq(1))
                .build()
                .list();

        if (list!=null&&list.size()>0){
            USER=list.get(0);
        }

        // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        long time = System.currentTimeMillis();
        View view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        //LogUtils.e(this.toString()+"页面加载使用："+(System.currentTimeMillis()-time));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }


    /**
     * 设置页面名字 用于友盟统计
     */
    public abstract String getPageName();
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

}
