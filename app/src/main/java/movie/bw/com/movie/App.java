package movie.bw.com.movie;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：夏洪武
 * 时间：2019/1/23.
 * 邮箱：
 * 说明：
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MultiDex.install(this);
        UMConfigure.init(this,  UMConfigure.DEVICE_TYPE_PHONE, null);

//        CrashReport.initCrashReport(getApplicationContext(), "5d3c07724a", false);
    }
}
