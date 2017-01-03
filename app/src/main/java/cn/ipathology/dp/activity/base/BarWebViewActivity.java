package cn.ipathology.dp.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

import cn.ipathology.dp.R;
import cn.ipathology.dp.util.WebViewUtil;

public class BarWebViewActivity extends BaseWebViewActivity implements View.OnClickListener {
    public View view;
    public RelativeLayout barBackground;
    public LinearLayout linearBack, linearRightImgOne, linearRightImgTwo;
    public TextView leftTitle, centerTitle, rightTitle;
    public ImageView backImg, rightImgOne, rightImgTwo;
    public int black = 0xFF000000 ;
    public WebViewUtil webViewUtil = new WebViewUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

    }

    //设置导航栏效果
    public void initActionBar(String leftTitleString, String centerTitleString, String rightTitleString) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_back_center_right_bar, null);


        barBackground = (RelativeLayout) view.findViewById(R.id.bar_relative_bg);
        linearBack = (LinearLayout) view.findViewById(R.id.bar_linear_left_back);
        linearRightImgOne = (LinearLayout) view.findViewById(R.id.bar_linear_right_one);
        linearRightImgTwo = (LinearLayout) view.findViewById(R.id.bar_linear_right_two);


        leftTitle = (TextView) view.findViewById(R.id.bar_txt_left_title);
        rightTitle = (TextView) view.findViewById(R.id.bar_txt_right_one);
        centerTitle = (TextView) view.findViewById(R.id.bar_txt_center_title);

        leftTitle.setText(leftTitleString);
        centerTitle.setText(centerTitleString);
        rightTitle.setText(rightTitleString);

        rightImgOne = (ImageView) view.findViewById(R.id.bar_img_right_one);
        rightImgTwo = (ImageView) view.findViewById(R.id.bar_img_right_two);
        backImg = (ImageView) view.findViewById(R.id.bar_img_left_back);

        linearBack.setOnClickListener(this);
        linearRightImgOne.setOnClickListener(this);
        linearRightImgTwo.setOnClickListener(this);
        rightTitle.setOnClickListener(this);

        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);

        actionBar.setCustomView(view);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    //设置AcitonBar
    public void initActionBar() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            initActionBar("返回", title, "");
        }
        setWebViewUrl(url);
    }

    //在Base中统一设置所有的WebView
    public void setWebViewUrl(String url) {

        webViewUtil.setWebView(url, autoSwipeRefreshLayout, bridgeWebView);
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
                bbsFinish();
                break;

        }
    }
}
