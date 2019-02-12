package movie.bw.com.movie.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * 作者：夏洪武
 * 时间：2019/2/12.
 * 邮箱：
 * 说明：
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService (CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null ){
            //表示可以连接网络
            Toast.makeText(context,"重新获取数据中",Toast.LENGTH_LONG).show();
        }else{
            //则不能
           Toast.makeText(context,"没有网络",Toast.LENGTH_LONG).show();
        }
    }
}
