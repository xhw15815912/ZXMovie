package movie.bw.com.movie.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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

    public Dialog mLoadDialog;
    private Unbinder unbinder;
    //public UserInfo LOGIN_USER;
    public UserBean USER;
    public List<UserBean> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AutoSizeConfig.getInstance().setCustomFragment(true);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserBeanDao.TABLENAME);
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        list = userBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Register.eq(1))
                .build()
                .list();

        if (list !=null&& list.size()>0){
            USER= list.get(0);
        }
        if (isRegisterEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this);
            }
        }
        // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        long time = System.currentTimeMillis();
        View view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        //LogUtils.e(this.toString()+"页面加载使用："+(System.currentTimeMillis()-time));
        return view;
    }
    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (isRegisterEventBus()){
            if (EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
            }
        }

    }

    private void initLoad() {
        mLoadDialog = new ProgressDialog(getContext());// 加载框
        mLoadDialog.setCanceledOnTouchOutside(false);
        mLoadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (mLoadDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
                    cancelLoadDialog();//加载消失的同时
                    mLoadDialog.cancel();
                }
                return false;
            }
        });
    }
    //取消操作：请求或者其他
    public void cancelLoadDialog() {

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
