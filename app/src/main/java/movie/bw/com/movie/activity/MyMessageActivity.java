package movie.bw.com.movie.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.MePresenter;

public class MyMessageActivity extends BaseActivity {


    @BindView(R.id.user_avatar)
    SimpleDraweeView userAvatar;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.user_sex)
    TextView userSex;
    @BindView(R.id.date_of_birth)
    TextView dateOfBirth;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.relative_1)
    RelativeLayout relative1;
    @BindView(R.id.user_postbox)
    TextView userPostbox;
    @BindView(R.id.reset_passwords)
    ImageView resetPasswords;
    private MePresenter mePresenter;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        if (list != null && list.size() > 0) {
            userId = USER.getUserId();
            sessionId = USER.getSessionId();
        }

        mePresenter = new MePresenter(new MeData());
        mePresenter.request(userId, sessionId);

    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.back,R.id.user_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.user_avatar:
                View view1 = View.inflate(getBaseContext(), R.layout.popwind_item, null);
                final PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(view1);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
                View photo_album = view1.findViewById(R.id.photo_album);
                View viewById = view1.findViewById(R.id.camera);
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用相机拍照的方法
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent1.addCategory("android.intent.category.DEFAULT");
                        startActivityForResult(intent1, 0);
                        popupWindow.dismiss();

                    }
                });
                photo_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用获取本地图片的方法
                        Intent intent2 = new Intent(Intent.ACTION_PICK);
                        intent2.setType("image/*");
                        startActivityForResult(intent2, 1);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(view1, 1, 0, 0);
                break;
        }
    }

    @OnClick(R.id.reset_passwords)
    public void Reset() {
        startActivity(new Intent(MyMessageActivity.this, ChangePasswordActivity.class));
    }



    private class MeData implements DataCall<Result<MeBean>> {
        @Override
        public void success(Result<MeBean> result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(MyMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                MeBean meBean = result.getResult();

                userAvatar.setImageURI(meBean.getHeadPic());
                nickname.setText(meBean.getNickName());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String s = format1.format(meBean.getBirthday());
                if (meBean.getSex() == 1) {
                    userSex.setText("男");
                } else {
                    userSex.setText("女");
                }
                dateOfBirth.setText(s);
                userPhone.setText(meBean.getPhone());
                userPostbox.setText(meBean.getEmail());
            } else {
                Toast.makeText(MyMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                Bitmap bitmap = data.getParcelableExtra("data");
                userAvatar.setImageBitmap(bitmap);
                break;
            case 1:
                Uri uri = data.getData();
                Intent intent = crop(uri);
                startActivityForResult(intent, 2);
                break;
            case 2:
                Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                userAvatar.setImageBitmap(bitmap1);
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
}
