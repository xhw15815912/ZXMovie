package movie.bw.com.movie;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

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
//        CrashReport.initCrashReport(getApplicationContext(), "5d3c07724a", false);
    }
}
