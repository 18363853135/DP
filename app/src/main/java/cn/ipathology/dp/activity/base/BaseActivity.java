package cn.ipathology.dp.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import cn.ipathology.dp.R;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.util.DensityUtil;
import cn.ipathology.dp.util.MyPopupWindow;
import cn.sharesdk.framework.ShareSDK;

import static cn.ipathology.dp.util.AppManager.getAppManager;


public class BaseActivity extends ActionBarActivity {
    private ActionBar actionBar;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getAppManager().addActivity(this);
        ShareSDK.initSDK(this);
        initActionBar("");

    }


    public void whiteActionBar(){
        //设置状态栏白底黑色   android6.0以上才生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }



    //设置导航栏效果
    public void initActionBar(String title) {
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }




    //设置导航栏效果
    public void hintActionBar() {
        actionBar = this.getSupportActionBar();
        actionBar.hide();
    }

    public void showToast(String message) {
        if (!TextUtils.isEmpty(message))
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    //回退键 事件处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            bbsFinish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //设置打开就刷
    public void setOpenRefersh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(MyApplication.getInstance(), 24));
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }
    //禁止刷新
    public void setCloseRefersh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(MyApplication.getInstance(), 24));
        swipeRefreshLayout.setEnabled(false);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
//                  Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
                MyPopupWindow popupWindow = new MyPopupWindow();
                popupWindow.setMyPopupWindow(this, this.getWindow().getDecorView(), content);

            }
        }
    }


    //方便自己维栈 在始终保持在当前的activity
    public void bbsFinish(){
        finish();
        getAppManager().popActivity();
    }








}
