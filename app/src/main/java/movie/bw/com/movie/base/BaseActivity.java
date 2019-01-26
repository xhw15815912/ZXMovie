package movie.bw.com.movie.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

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
public abstract class BaseActivity extends AppCompatActivity implements CustomAdapt {
    public final static int PHOTO =0;
    public final static int CAMERA = 1;
    public Dialog mLoadDialog;

    private static BaseActivity mActivity = null;
    private String success ;
    public UserBean USER;
    public List<UserBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Android4.4的沉浸式状态栏写法



        BaseActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserBeanDao.TABLENAME);
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
        initLoad();
        setContentView(getLayoutId());
        ButterKnife.bind(this);//绑定布局
        initView();
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    /**
     * @param mActivity 传送Activity的
     */
    public void intent(Class mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * @param mActivity 传送Activity的
     * @param bundle
     */
    public void intent(Class mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 初始化加载框
     */
    private void initLoad() {
        mLoadDialog = new ProgressDialog(this);// 加载框
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryData();
        if (isRegisterEventBus()){
            if (EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
            }
        }

    }

    //取消操作：请求或者其他
    public void cancelLoadDialog() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mActivity;
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            {
                actualimagecursor.close();
            }

            return img_path;
        }
        return null;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
