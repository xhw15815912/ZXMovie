package movie.bw.com.movie;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

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
    }
}
