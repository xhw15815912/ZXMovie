package com.bw.movie.wxapi;

import com.bw.movie.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.jessyan.autosize.internal.CustomAdapt;
import movie.bw.com.movie.DaoMaster;
import movie.bw.com.movie.DaoSession;
import movie.bw.com.movie.MainActivity;
import movie.bw.com.movie.UserBeanDao;
import movie.bw.com.movie.activity.ShowActivity;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.bean.UserBean;
import movie.bw.com.movie.bean.UserInfo;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.Wx_Login_Presenter;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler,CustomAdapt {

	private IWXAPI api;
	private TextView textView;
	private Wx_Login_Presenter wx_login_presenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry);
		textView= (TextView) findViewById(R.id.result_tv);
		api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
//        api.registerApp("wxbd3e6bba8efbae73");
		api.handleIntent(getIntent(),this);

		Log.e("12","111111"+"===");
	}
	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent,this);
		Log.e("1","111111"+"===");
	}

	@Override
	public void onReq(BaseReq baseReq) {
		Log.e("123","111111"+"===");
	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.e("1234","111111"+"===");

		int result = 0;
		Toast.makeText(this, "baseresp.getType = " + baseResp.getType(), Toast.LENGTH_SHORT).show();

		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = R.string.errcode_success;
				final String code = ((SendAuth.Resp) baseResp).code;
				Log.e("1234",code+"===");
				wx_login_presenter = new Wx_Login_Presenter(new CallBack());
				wx_login_presenter.request(code);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = R.string.errcode_cancel;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = R.string.errcode_deny;
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				result = R.string.errcode_unsupported;
				break;
			default:
				result = R.string.errcode_unknown;
				break;
		}


		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		textView.setText("正在登陆"+result+baseResp.getType());
		//this.finish();


	}

	private class CallBack implements DataCall<Result<UserInfo>> {
		@Override
		public void success(Result<UserInfo> data) {
			if (data.getStatus().equals("0000")){
				DaoSession daoSession = DaoMaster.newDevSession(WXEntryActivity.this, UserBeanDao.TABLENAME);
				UserBeanDao userBeanDao = daoSession.getUserBeanDao();
				userBeanDao.deleteAll();

				userBeanDao.insert(new UserBean(data.getResult().getSessionId(),data.getResult().getUserId(),1));
				startActivity(new Intent(WXEntryActivity.this,ShowActivity.class));
				finish();
			}
		}

		@Override
		public void fail(ApiException e) {

		}
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