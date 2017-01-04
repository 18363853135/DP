package cn.ipathology.dp.activity.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.detail.AcdSeeActivity;
import cn.ipathology.dp.activity.detail.CaseListSiteActivity;
import cn.ipathology.dp.activity.detail.DiagnoseCaseActivity;
import cn.ipathology.dp.activity.detail.PDFViewActivity;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.entityClass.Slide;
import cn.ipathology.dp.util.AppManager;
import cn.ipathology.dp.util.StringFormat;
import cn.ipathology.dp.view.AutoSwipeRefreshLayout;


public class BaseWebViewActivity extends BaseActivity {
    public AutoSwipeRefreshLayout autoSwipeRefreshLayout;
    public BridgeWebView bridgeWebView;
    public LinearLayout progressBar;
    public CallBackFunction callBackFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);

        initView();

        registerAppBridgeLoading(bridgeWebView);
        registerAppBridgeConfirmShow(bridgeWebView);
        registerAppBridgeUrlOpen(bridgeWebView);         //打开新链接
        registerAppBridgeClose(bridgeWebView);

    }


    //初始化参数
    private void initView() {
        autoSwipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.baseWebView_detail_swipeRefreshLayout);
        bridgeWebView = (BridgeWebView) findViewById(R.id.baseWebView_detail_bridgeWebView);
        progressBar = (LinearLayout) findViewById(R.id.baseWebView_detail_linearLayout);
        setOpenRefersh(autoSwipeRefreshLayout);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bridgeWebView != null) {
            bridgeWebView.destroy();
        }
    }


    //注册 上传的progressBar
    public void registerAppBridgeLoading(BridgeWebView webView) {
        webView.registerHandler("appBridgeLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject json = new JSONObject(data);
                    int progressCount = json.getInt("data");
                    if (progressCount == 1) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(data);
            }
        });
    }

    //主动调取Js的方法  在Js端注册相应的函数
    public void closeCallHandler(String funName, String  param) {
        bridgeWebView.callHandler(funName, param, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }


    //显示可取消的对话框
    public void registerAppBridgeConfirmShow(BridgeWebView webView) {
        webView.registerHandler("appBridgeConfirmPop", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    showDiagnosisDialog(message, function);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //确认取消弹出框
    public void showDiagnosisDialog(String messageStr, final CallBackFunction function) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.layout_diagnosis_dialog);
        TextView message = (TextView) window.findViewById(R.id.diagnosis_dialog_title);
        TextView ok = (TextView) window.findViewById(R.id.diagnosis_dialog_ok);
        TextView cancel = (TextView) window.findViewById(R.id.diagnosis_dialog_cancel);
        message.setText(messageStr);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果确认  返回true
                function.onCallBack(StringFormat.booleanToJsonObject("data", true));
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果取消  返回false
                function.onCallBack(StringFormat.booleanToJsonObject("data", false));
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 1.id与url同时存在 使用对应id页打开url
     * 2.仅id存在 打开原生语言写的不需要url的页
     * 3.仅url存在 使用普通浏览器页打开url
     * @param  {Object}   options  参数对象
     * @param  {Function} callback 回调
     */

    public void registerAppBridgeUrlOpen(BridgeWebView webView) {

        webView.registerHandler("appBridgeUrlOpen", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                if (null != data) {
                    openUrl(data, function);
                }

            }
        });

    }

    //JS自动调用 关闭当前的Activity 并且在上一个activity中调用callHandler方法
    public void registerAppBridgeClose( BridgeWebView webView) {

        webView.registerHandler("appBridgeClose", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                appBridgeClose(data);
            }
        });
    }


    //打开url方法
    public void openUrl(String data, CallBackFunction function) {
        try {
            System.out.println("============" + data);
            JSONObject jsonObject = new JSONObject(data);
            String id = "";
            String url = "";
            String title = "";
            String options = "";
            String shares = "";


            if (!jsonObject.isNull("id")) {
                id = jsonObject.getString("id");
            }
            if (!jsonObject.isNull("url")) {
                url = jsonObject.getString("url");
            }
            if (!jsonObject.isNull("title")) {
                title = jsonObject.getString("title");
            }
            if (!jsonObject.isNull("options")) {
                options = jsonObject.getString("options");
            }
            if (!jsonObject.isNull("shares")) {
                shares = jsonObject.getString("shares");
            }

            if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(url)) {

                Intent intent = null;
                if (id.equals("case_list_site")) {
                    intent = new Intent(MyApplication.getInstance(), CaseListSiteActivity.class);
                } else if (id.equals("diagnose_case")) {
                    intent = new Intent(MyApplication.getInstance(), DiagnoseCaseActivity.class);
                } else if (id.equals("slide_view")) {
                    intent = new Intent(MyApplication.getInstance(), AcdSeeActivity.class);
                    String slideInfo = "";

                    if (!jsonObject.isNull("slide_info")) {
                        slideInfo = jsonObject.getString("slide_info");
                        Gson gson = new Gson();
                        Slide slide = gson.fromJson(slideInfo, Slide.class);
                        intent.putExtra("caseId", slide.getCase_id());
                        intent.putExtra("slideId", slide.getSlide_id());
                        intent.putExtra("view", slide.getView());
                        intent.putExtra("token", slide.getToken());
                    }
                } else if (id.contains("pdf")) {
                    intent = new Intent(MyApplication.getInstance(), PDFViewActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("options", options);
                    intent.putExtra("shares", shares);

                } else {
                    intent = new Intent(MyApplication.getInstance(), DiagnoseCaseActivity.class);
                }

                if (id.equals("pdf_view")) {
                    callBackFunction = function;
                } else {
                function.onCallBack(data);
                }

                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getInstance().startActivity(intent);

            } else if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(url)) {

                //仅id存在 打开原生语言写的不需要url的页
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                MyApplication.getInstance().startActivity(intent);

            } else if (id.isEmpty() && !url.isEmpty()) {

                //仅url存在 使用普通浏览器页打开url
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getInstance().startActivity(intent);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //关闭当前的页面 并请求Js对上个页面进行操作
    public void appBridgeClose(String data) {
        try {
            
            JSONObject jsonObject = new JSONObject(data);
            String functionName = jsonObject.getString("fnname");
            String param = jsonObject.getString("param");
            BaseWebViewActivity activity = (BaseWebViewActivity) AppManager.getAppManager().reciprocalSecondActivity();
            AppManager.getAppManager().currentActivity().finish();
            AppManager.getAppManager().popActivity();
            if (activity != null) {
                activity.closeCallHandler(functionName, param);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
