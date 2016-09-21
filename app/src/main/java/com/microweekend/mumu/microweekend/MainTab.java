package com.microweekend.mumu.microweekend;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.microweekend.mumu.microweekend.event.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class MainTab extends Activity {
    private BottomNavigationBar bottomNavigationBar;
	private FragmentManager fmanager;
    private HomeTimeLine fra_home;
    private DiscoverFra fra_discover;
    private MeFra fra_me;
    private MessageFra fra_message;
    private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
        context = this;
		initView();
	}

	/**
	 *
	 */
	public void initView() {
        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_home,"首页")).setActiveColor("#ffffff").setInActiveColor("#c0c0c0").setBarBackgroundColor("#303030")
                            .addItem(new BottomNavigationItem(R.drawable.icon_square, "发现"))
                            .addItem(new BottomNavigationItem(R.drawable.compose_more_add, "发布"))
                            .addItem(new BottomNavigationItem(R.drawable.icon_meassage, "消息"))
                            .addItem(new BottomNavigationItem(R.drawable.icon_more, "我"))
                            .initialise();
        setDefaultFragment();
        onClick();
	}

	public void setDefaultFragment() {
		fmanager = getFragmentManager();
        FragmentTransaction ft = fmanager.beginTransaction();
        fra_home = new HomeTimeLine();
        ft.replace(R.id.fl_content, fra_home);
        ft.commit();
	}

    public void onClick() {
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction ft = fmanager.beginTransaction();
                switch (position) {
                    case 0:
                        if (fra_home == null) {
                            fra_home = new HomeTimeLine();
                        }
                        ft.replace(R.id.fl_content, fra_home);
                        break;
                    case 1:
                        if (fra_discover == null) {
                            fra_discover = new DiscoverFra();
                        }
                        ft.replace(R.id.fl_content, fra_discover);
                        break;
                    case 2:
                        PhotoPicker.builder()
                                .setPhotoCount(4)
                                .setShowCamera(false)
                                .setShowGif(false)
                                .setPreviewEnabled(true)
                                .start(MainTab.this, PhotoPicker.REQUEST_CODE);
                        break;
                    case 3:
                        if (fra_message == null) {
                            fra_message = new MessageFra();
                        }
                        ft.replace(R.id.fl_content, fra_message);
                        break;
                    case 4:
                        if (fra_me == null) {
                            fra_me = new MeFra();
                        }
                        ft.replace(R.id.fl_content, fra_me);
                        break;
                    default:
                        break;
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                startActivity(new Intent(MainTab.this,MkSendTile.class));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new TestEvent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TestEvent te) {

    }

    /**http://blog.csdn.net/jdsjlzx/article/details/41643587
     * 设置设备顶栏颜色
     */
    public void setStatusColor() {
        Window window = this.getWindow();
//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏颜色
//        window.setStatusBarColor(getResources().getColor(R.color.headcolor));

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }
}
