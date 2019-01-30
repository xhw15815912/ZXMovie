package movie.bw.com.movie.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.common.file.FileUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movie.bw.com.movie.base.BaseActivity;
import movie.bw.com.movie.bean.MeBean;
import movie.bw.com.movie.bean.Result;
import movie.bw.com.movie.core.DataCall;
import movie.bw.com.movie.core.exception.ApiException;
import movie.bw.com.movie.p.Change_UserHead_Presenter;
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
    private File file;
    private Change_UserHead_Presenter change_userHead_presenter;
    private PopupWindow popupWindow;

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
        change_userHead_presenter = new Change_UserHead_Presenter(new Change());
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
                popupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(view1);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
                Button photo_album = view1.findViewById(R.id.photo_album);

                //相机的点击事件

                //相册的点击事件
                photo_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyMessageActivity.this,"111",Toast.LENGTH_LONG).show();
                        //调用相册
                        /*Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 2);
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }*/

                        if (ContextCompat.checkSelfPermission(MyMessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //权限还没有授予，需要在这里写申请权限的代码
                            ActivityCompat.requestPermissions(MyMessageActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        } else {
                            Intent openAlbumIntent = new Intent(
                                    Intent.ACTION_PICK);
                            openAlbumIntent.setType("image/*");
                            //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                            startActivityForResult(openAlbumIntent, 2);
                        }
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
                //Toast.makeText(MyMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //用户从图库选择图片后会返回所选图片的Uri
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = getContentResolver().query(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            change_userHead_presenter.request(userId,sessionId,img_path);
        }
       //调用相机后返回
        if (requestCode == 1 && resultCode == RESULT_OK) {

            //版本号大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //通过FileProvider创建一个content类型的Uri
                Uri contentUri = FileProvider.getUriForFile(MyMessageActivity.this, "com.bw.movie", file);
                cropPhoto(contentUri);
                String filePath = getFilePath(null, requestCode, data);
                change_userHead_presenter.request(userId,sessionId,filePath);
            } else {
                cropPhoto(Uri.fromFile(file));
            }
            cropPhoto(Uri.fromFile(file));
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap image = bundle.getParcelable("data");
                //设置到ImageView上
                userAvatar.setImageBitmap(image);

                //也可以进行一些保存、压缩等操作后上传
                String path = saveImage("crop", image);

                File filea = getFile(image);

                HashMap<String, String> map = new HashMap<>();
                map.put("image", path + "");
                String filePath = getFilePath(null, requestCode, data);
                change_userHead_presenter.request(userId,sessionId,filePath);
                //change_userHead_presenter.request(userId,sessionId,map);

            }
        }
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto (Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3);
    }
    public String saveImage (String name, Bitmap bmp){
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public File getFile (Bitmap bmp){
        String defaultPath = getApplicationContext().getFilesDir()
                .getAbsolutePath() + "/defaultGoodInfo";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/messageImg.jpg";
        file = new File(defaultImgPath);
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private class Change implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MyMessageActivity.this,data.getMessage(),Toast.LENGTH_LONG).show();
                mePresenter.request(userId, sessionId);
                popupWindow.dismiss();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
