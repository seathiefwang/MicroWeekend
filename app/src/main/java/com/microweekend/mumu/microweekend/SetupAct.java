package com.microweekend.mumu.microweekend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.entry.User;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.UserEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.Myspf;
import com.microweekend.mumu.microweekend.util.ParseJson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by mumu on 2016/10/7.
 */
public class SetupAct extends WeekendAct implements View.OnClickListener {

    private User userinfo;
    private TextView tv_setnickname;
    private TextView tv_gender;
    private CircleImageView iv_displaypic;
    private ImageView iv_back;
    private Button bt_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_setup);
        initView();
    }

    public void initView() {
        tv_setnickname = (TextView)findViewById(R.id.textView);
        tv_gender = (TextView)findViewById(R.id.textView1);
        iv_displaypic = (CircleImageView)findViewById(R.id.circleImageView);
        iv_back = (ImageView)findViewById(R.id.back);
        bt_logout = (Button)findViewById(R.id.button);

        iv_displaypic.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        bt_logout.setOnClickListener(this);

        userinfo = ParseJson.parseuser(new MkHelper(context).getUser());
        setUserinfo();
        initDialog();
    }

    public void initDialog() {
        tv_setnickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(context);
                et.setPadding(10, 10, 10, 10);
                et.setText(tv_setnickname.getText());
                new AlertDialog.Builder(context)
                        .setTitle("设置昵称")
                        .setView(et)
                        .setPositiveButton("完成",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String nick = et.getText().toString();
                                        if (nick.equals("")) {
                                            ToastInfo("昵称不能为空");
                                        } else {
                                            UserEvent e = new UserEvent();
                                            e.type = UserEvent.TYPE_SETNICKNAME;
                                            e.setString(nick);
                                            EventBus.getDefault().post(e);
                                        }
                                    }
                                }).setNegativeButton("取消", null)
                        .setCancelable(false).show();
            }
        });
    }

    private void setUserinfo() {
        String string = userinfo.getNickname();
        if (string == null || string.equals("")) {
            tv_setnickname.setText("");
        } else {
            tv_setnickname.setText(string);
        }
        string = userinfo.getUserGender();
        if (string == null || string.equals("null"))
            tv_gender.setText("未知");
        else {
            tv_gender.setText(string);
        }
        string = userinfo.getDisplayPic();
        if (!TextUtils.isEmpty(string)) {
            MkConstants.ansyLoad.setBitmapOfImageView(string, iv_displaypic);
        }
    }

    @Override
    public void onEvent(ResultEvent e) {
        switch (e.type) {
            case UserEvent.TYPE_SETNICKNAME:
            case UserEvent.TYPE_SETGENDER:
            case UserEvent.TYPE_SETDISPLAYPIC:
                if (e.getCode() == 1) {
                    ToastInfo("修改资料成功");
                    UserEvent ue = new UserEvent();
                    ue.type = UserEvent.TYPE_GETUSERINFO;
                    EventBus.getDefault().post(ue);
                } else
                    ToastInfo((String)e.getMsg());
                break;
            case UserEvent.TYPE_GETUSERINFO:
                if (e.getCode() == 1) {
                    userinfo = ParseJson.parseuser(e.getJson());
                    setUserinfo();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleImageView:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.button:                   //退出登录，跳转到登录界面
                Myspf.saveLoginFlag(this, false);
                new MkHelper(this).delete();//清空数据库
                Intent intent = new Intent(this, LoginAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);  //Intent.FLAG_ACTIVITY_CLEAR_TOP，这样开启B时将会清除该进程空间的所有Activity
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
                cropImageUri(Uri.fromFile(new File(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0))), 200, 200);
                Log.d("Setup", "选择的头像文件：" + data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0));
            } else if (requestCode == 239 && data != null) {
                Log.d("Setup", "文件路径：" + Uri.parse("file:///sdcard/microweekend/temp.jpg").getEncodedPath());
                UserEvent e = new UserEvent();
                e.type = UserEvent.TYPE_SETDISPLAYPIC;
                e.setPic("/sdcard/microweekend/temp.jpg");
                EventBus.getDefault().post(e);
            }
    }

    private void cropImageUri(Uri uri, int outputX, int outputY){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///sdcard/microweekend/temp.jpg"));

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, 239);

    }
}
