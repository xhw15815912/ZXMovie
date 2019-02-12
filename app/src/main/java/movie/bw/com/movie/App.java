package movie.bw.com.movie;

import android.app.Application;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;
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
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, config); //不设置默认传一个参数既可

        MultiDex.install(this);
        UMConfigure.init(this,  UMConfigure.DEVICE_TYPE_PHONE, null);

        CrashReport.initCrashReport(getApplicationContext(), "5d3c07724a", false);
    }
}
