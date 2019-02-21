package movie.bw.com.movie.frag;


import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.DaoSession;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.activity.FeedbackActivity;
import movie.bw.com.movie.activity.MyInterestActivity;
import movie.bw.com.movie.activity.MyMessageActivity;
import movie.bw.com.movie.activity.SystemInfoActivity;
import movie.bw.com.movie.activity.TickethistorypageActivity;
import movie.bw.com.movie.base.BaseFragment;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.DownLoadService;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.ChangeHeadImage_Presenter;
import movie.bw.com.movie.p.FindNewVersionPresenter;
import movie.bw.com.movie.p.MePresenter;
import movie.bw.com.movie.p.MyInterestPresenter;
import movie.bw.com.movie.p.UserSingnInPresenter;
import movie.bw.com.movie.utils.ImageUtil;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFrag extends BaseFragment {

    private static String oldMsg;
    private static long time;
    @BindView(R.id.trumpet)
    ImageView trumpet;
    @BindView(R.id.headimage)
    SimpleDraweeView headimage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sign_in)
    TextView sign_in;
    @BindView(R.id.my_chat)
    RelativeLayout myChat;
    @BindView(R.id.my_attention)
    RelativeLayout myAttention;
    @BindView(R.id.my_rccord)
    RelativeLayout myRccord;
    @BindView(R.id.my_feedback)
    RelativeLayout myFeedback;
    @BindView(R.id.my_version)
    RelativeLayout myVersion;
    @BindView(R.id.rccord)
    ImageView rccord;
    @BindView(R.id.image_logout)
    ImageView imageLogout;
    private UserSingnInPresenter singn;
    private MyInterestPresenter myInterestPresenter;
    private int userId;
    private String sessionId;
    private MePresenter mePresenter;
    private ChangeHeadImage_Presenter changeHeadImage_presenter;
    private FindNewVersionPresenter findNewVersionPresenter;
    //下载的路径
    private String mDownloadUrl;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        findNewVersionPresenter = new FindNewVersionPresenter(new VerSion());
        singn = new UserSingnInPresenter(new UserSingnIn());
        if (list.size() > 0 && list != null) {
            singn.request(userId, sessionId);
        }
        mePresenter = new MePresenter(new MeData());
        mePresenter.request(userId, sessionId);
        changeHeadImage_presenter = new ChangeHeadImage_Presenter(new HeadImag());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        if (list != null && list.size() > 0) {
            mePresenter.request(userId, sessionId);
        }
        if (list.size() > 0 && list != null) {
            singn.request(userId, sessionId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }
        if (list != null && list.size() > 0) {
            mePresenter.request(userId, sessionId);
        }
    }

    @OnClick({R.id.trumpet, R.id.headimage, R.id.my_chat, R.id.my_attention, R.id.my_rccord, R.id.my_feedback, R.id.my_version, R.id.sign_in, R.id.my_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trumpet:
                startActivity(new Intent(getContext(), SystemInfoActivity.class));
                break;
            case R.id.headimage:
                if (list.size() == 0) {

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("id", 2);
                    startActivity(intent);
                } else {

                }

                break;
            case R.id.my_chat:
                Intent intent = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            //我的关注
            case R.id.my_attention:

                startActivity(new Intent(getContext(), MyInterestActivity.class));
                break;
            case R.id.my_rccord:
                startActivity(new Intent(getActivity(), TickethistorypageActivity.class));
                break;
            case R.id.my_feedback:
                startActivity(new Intent(getContext(), FeedbackActivity.class));
                break;
            case R.id.my_version:
                if (list.size()==0){
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        String versionName = getVersionName(getContext());
                        findNewVersionPresenter.request(userId, sessionId, versionName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.sign_in:
                if (list.size() > 0 && list != null) {
                    singn.request(userId, sessionId);
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.my_logout:
                if (list != null && list.size() > 0) {
                    DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserBeanDao.TABLENAME);
                    UserBeanDao userBeanDao = daoSession.getUserBeanDao();
                    userBeanDao.deleteAll();
                    headimage.setImageResource(R.mipmap.m);
                    name.setText("请登录");
                    sign_in.setText("签到");
                    list.clear();
//                    mePresenter.request(userId, sessionId);
                    Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();

                } else {
                    // 显示内容一样时，只有间隔时间大于5秒时才显示
                    if (System.currentTimeMillis() - time > 5000) {
                        Toast.makeText(getContext(), "你还未登录过！！！", Toast.LENGTH_LONG).show();
                        time = System.currentTimeMillis();
                    }

                }

                break;
        }
    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 60000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    private class UserSingnIn implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                sign_in.setText("签到");
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                sign_in.setText("已签到");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class VerSion implements DataCall<Result> {
        @Override
        public void success(final Result data) {
            if (data.getFlag() == 2) {
                Toast.makeText(getActivity(), "当前已是最新版本!", Toast.LENGTH_SHORT).show();
            } else if (data.getFlag() == 1) {
                AlertDialog.Builder builer = new AlertDialog.Builder(getContext());
                builer.setTitle("版本升级");
                builer.setMessage("发现新版本");
                //当点确定按钮时从服务器上下载 新的apk 然后安装
                builer.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), DownLoadService.class);
                        intent.putExtra("download_url", data.getDownloadUrl());
                        getActivity().startService(intent);
                    }
                });
                //当点取消按钮时进行登录
                builer.setNegativeButton("稍后下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //LoginMain();
                    }
                });
                AlertDialog dialog = builer.create();
                dialog.show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                //得到相册图片,转为file类型
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        //调用工具类将uri图片转为path
                        String path = ImageUtil.getPath(getContext(), uri);
                        if (path != null) {
                            //将图片转为file
                            File file = new File(path);
                            //调用P层
                            changeHeadImage_presenter.request(userId, sessionId, file);
                        }
                    }
                } else {
                    return;
                }
                break;
        }
    }

    //剪切
    private Intent crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("acceptX", 1);
        intent.putExtra("acceptY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("return-data", true);
        return intent;
    }

    private class MeData implements DataCall<Result<MeBean>> {
        @Override
        public void success(Result<MeBean> data) {
            if (data.getStatus().equals("0000")) {
                MeBean bean = data.getResult();
                name.setText(bean.getNickName());
                headimage.setImageURI(bean.getHeadPic());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Change implements DataCall<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class HeadImag implements DataCall<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    /**
     * 获取版本号
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }
}
