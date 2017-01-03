package cn.ipathology.dp.activity;

import android.os.Bundle;

import com.github.lzyzsd.jsbridge.BridgeWebView;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;


public class TestActivity extends BarWebViewActivity {
    BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        webView = (BridgeWebView) findViewById(R.id.test_webView);
//        webView.loadUrl("http://192.168.1.170/index.html?p=pdf.html&f=app");

        // url (huasds ://token: sdsdfs );


    }
}
