package cn.ipathology.dp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.activity.user.PersonalActivity;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.network.Router;
import cn.ipathology.dp.util.FileUtil;
import cn.ipathology.dp.util.ImageLoaderUtil;
import cn.ipathology.dp.util.WebViewUtil;
import cn.ipathology.dp.zxing.android.CaptureActivity;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BarWebViewActivity {

    public ResponseData responseData = new ResponseData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make sure activity will not in background if user is logged into another device or removed
//        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
//            Helper.getInstance().logout(false,null);
//            finish();
//            showToast("帐号被移除");
//            startActivity(new Intent(this, LoginActivity.class));
//            return;
//        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
//            finish();
//            showToast("帐号在其他设备登录");
//            startActivity(new Intent(this, LoginActivity.class));
//            return;
//        }


//        setContentView(R.layout.activity_home);

        initActionBar("", "华夏病理云诊断平台", "");

        rightTitle.setVisibility(View.GONE);
        linearBack.setVisibility(View.VISIBLE);
        backImg.setImageResource(R.mipmap.ic_scan_case_normal);
        rightImgOne.setImageResource(R.mipmap.ic_my_information_normal);
        rightImgTwo.setImageResource(R.mipmap.ic_news_notice_normal);
        linearRightImgOne.setVisibility(View.VISIBLE);
        linearRightImgTwo.setVisibility(View.GONE);

        setWebViewUrl();

    }
    //添加url
    public void setWebViewUrl(){
        new WebViewUtil().setWebView(Router.homeUrl, autoSwipeRefreshLayout, bridgeWebView);

        bridgeWebView.setWebViewClient(new BridgeWebViewClient(bridgeWebView) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                autoSwipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_linear_left_back:
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
                break;
            case R.id.bar_linear_right_one:
                Intent personal = new Intent(MainActivity.this, PersonalActivity.class);
                startActivity(personal);
                break;
            case R.id.bar_linear_right_two:

//                Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
//                startActivity(chatIntent);
                break;
            default:
                break;
        }
    }


    private boolean is2CallBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!is2CallBack) {
                is2CallBack = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        is2CallBack = false;
                    }
                }, 2500);
            } else {
                bbsFinish();
                FileUtil fileUtil = new FileUtil();
                fileUtil.deleteFile(ImageLoaderUtil.apkFile());
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtil fileUtil = new FileUtil();
        fileUtil.deleteFile(ImageLoaderUtil.apkFile());
        ShareSDK.stopSDK(this);
    }


}
