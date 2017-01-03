package cn.ipathology.dp.util;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ipathology.dp.activity.other.PinchImageActivity;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.entityClass.Ajax;
import cn.ipathology.dp.entityClass.ImageBrowser;
import cn.ipathology.dp.entityClass.Share;
import cn.ipathology.dp.network.ApiHttpClient;
import cn.ipathology.dp.network.CustomResponse;
import cn.ipathology.dp.network.CustomResponseObject;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.Method;
import cn.ipathology.dp.network.PathParmas;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.view.AutoSwipeRefreshLayout;


/**
 * Created by wdb on 2016/4/22.
 */


public class WebViewUtil {
    public CustomToast customToast = new CustomToast(MyApplication.getInstance());
    private ResponseData loginHandler = new ResponseData();


    // 集体注册方法名  回调Js
    public void webViewClient( BridgeWebView webView) {

        registerShareToSocial(webView);           //分享回调
        registerAppBridgeHudShow(webView);
        registerAppBridgeAjax(webView);           //请求 AxaJ
        registerAppBridgeHudHide(webView);
        registerAppBridgeToastShow(webView);        //自定义toast显示
        registerAppBridgeToastHide(webView);        //隐藏toast
        registerAppBridgeImageDownload(webView);    //图片下载(缓存并返回path)
        registerAppBridgeImageBrowser(webView);    //图片浏览
        registerSearch(webView);                  //注册 search
        registerAppBridgeToken(webView);           //Js获取到Token
        registerAppBridgeLoadJPG(webView);        // 图片上传
//       registerAppBridgeLibraryPhotosGet(webView);

    }

    //请求 AxaJ
    public void registerAppBridgeAjax(BridgeWebView webView) {

        webView.registerHandler("appBridgeAjax", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                Gson gson = new Gson();
                final Ajax ajax = gson.fromJson(data, Ajax.class);
                PathParmas router = new PathParmas();
                if (ajax.getType().equals("post")) {
                    router.setMethod(Method.POST);
                } else {
                    router.setMethod(Method.GET);
                }
                router.setPath(ajax.getUrl());
                JSONObject jsonObject = new JSONObject();
                try {
                    String dataString = jsonObject.getString("data");
                    router.setJsonObject(new JSONObject(dataString));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ApiHttpClient.setTime(ajax.getTimeout() * 1000);
                ApiHttpClient.requestWithRouter(router, new CustomResponse() {
                    @Override
                    public void onSuccess(String jsonString) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("data", new JSONObject(jsonString));
                            jsonObject.put("code", 200);
                            jsonObject.put("msg", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        function.onCallBack(jsonObject.toString());
                    }

                    @Override
                    public boolean onFailure(HttpError errorString) {
                        showToast(errorString.getMessage());
                        return false;
                    }
                });
            }


        });
    }

    //分享回调
    public void registerShareToSocial(BridgeWebView webView) {
        webView.registerHandler("shareToSocial", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Gson gson = new Gson();
                Share share = gson.fromJson(data, Share.class);
                //弹出分享的UI
                if (null != share) {
                    ShowShare.setShareTitle(share.getShareTitle(), share.getShareDesc(), share.getShareLink(), share.getShareImgUrl());
                    ShowShare.showShare();
                }
            }
        });

    }

    public void registerAppBridgeHudShow(BridgeWebView webView) {
        webView.registerHandler("appBridgeHudShow", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(data);
            }

        });

    }

    public void registerAppBridgeHudHide(BridgeWebView webView) {
        webView.registerHandler("appBridgeHudHide", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack("appBridgeHudHide");
            }

        });

    }

    //设置toast
    public void registerAppBridgeToastShow(BridgeWebView webView) {
        webView.registerHandler("appBridgeToastShow", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Gson gson = new Gson();
                cn.ipathology.dp.entityClass.CustomToast toastColor = gson.fromJson(data, cn.ipathology.dp.entityClass.CustomToast.class);
                int duration = (int) toastColor.getDuration() * 1000;
                if (null != toastColor.getMessage() && !toastColor.getMessage().isEmpty()) {
                    customToast = cn.ipathology.dp.util.CustomToast.makeText(MyApplication.getInstance(), toastColor.getMessage(), toastColor.getMessageColor(), toastColor.getBackgroundColor(), duration);
                    customToast.show();
                }

                function.onCallBack(data);
            }
        });
    }

    //隐藏toast
    public void registerAppBridgeToastHide(BridgeWebView webView) {
        webView.registerHandler("appBridgeToastHide", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                if (null != customToast) {
                    customToast.hide();
                }
                function.onCallBack(data);
            }


        });
    }

    //
//
//    /**
//     * @param webView 相册 多选图片
//     */
//    public static void registerAppBridgeLibraryPhotosGet(BridgeWebView webView) {
//        webView.registerHandler("appBridgeLibraryPhotosGet", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
////                Intent intent = new Intent(MyApplication.getInstance(), AlbumChoseActivity.class);
////                AppManager.getAppManager().currentActivity().startActivityForResult(intent, DetailActivity.BBSALBUMCHOSE);
////                MyApplication.getInstance().setCallBackFunction(function);
//            }
//        });
//    }
//

    //图片下载(缓存并返回path)
    public void registerAppBridgeImageDownload(final BridgeWebView webView) {
        /**
         * 图片下载(缓存并返回path)
         * @param  {Object}   options  参数对象
         * @param  {Function} callback 回调
         */
        webView.registerHandler("appBridgeImageDownload", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                //图片下载
                imageDown(data);
                //图片缓存
//                imageCache(data);
                function.onCallBack(data);
            }

        });

    }

    //注册 search
    public void registerSearch(BridgeWebView webView) {
        webView.registerHandler("search", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(data);
            }

        });
    }

    //图片浏览
    public void registerAppBridgeImageBrowser(BridgeWebView webView) {
        /**
         * @param  {Object}   options  参数对象
         * @param  {Function} callback 回调
         */
        webView.registerHandler("appBridgeImageBrowser", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                imageBrowser(data);
                function.onCallBack(data);
            }
        });

    }

    /**
     * @param webView 图片上传
     */
    public void registerAppBridgeLoadJPG(BridgeWebView webView) {
        /**
         * @param  {Object}   options  参数对象
         * @param  {Function} callback 回调
         */
        webView.registerHandler("appBridgeUpLoadJPG", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                imageUpLoad(data);


            }

        });

    }


    //得到Token
    public void registerAppBridgeToken(final BridgeWebView webView) {
        webView.registerHandler("appBridgeToken", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {

                loginHandler.responseAccountData(new CustomResponseObject() {
                    @Override
                    public void onSuccess(Object object) {
                        function.onCallBack(new Gson().toJson(new TokenUtil().getToken()).toString());
                    }

                    @Override
                    public void onFailure(HttpError httpError) {
                        showToast(httpError.getMessage());
                    }
                });

            }
        });
    }

    //图片浏览的参数
    public void imageBrowser(String data) {
        Gson gson = new Gson();
        ImageBrowser imageBrowser = gson.fromJson(data, ImageBrowser.class);
        Intent intent = new Intent(MyApplication.getInstance(), PinchImageActivity.class);
        intent.putExtra("data", "dp");
        intent.putExtra("index", imageBrowser.getIndex());
        intent.putExtra("urls", imageBrowser.getUrls());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        MyApplication.getInstance().startActivity(intent);

    }

    //下载图片方法
    public void imageDown(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String url = jsonObject.getString("url");
            if (null != url && !url.isEmpty()) {
//                ImageDown imageDown = new ImageDown();
//                imageDown.saveNetWorkImage(url);
            } else {
                showToast("图片下载路径错误");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


   //图片缓存方法
//    public static void imageCache(String data) {
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String url = jsonObject.getString("url");
//            ImageView imageView = new ImageView(MyApplication.getInstance());
//            ImageLoaderUtil.display(url, imageView);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
    //图片上传的方法
    public void imageUpLoad(String data) {
    }



    //设置bridgeWebView可以刷新
    public void setRefershBridge(SwipeRefreshLayout swipeRefreshLayout, final BridgeWebView bridgeWebView) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bridgeWebView.loadUrl(bridgeWebView.getUrl());
            }
        });
    }



    //分享接口回调
    public static void callShareToSocial(BridgeWebView webView) {
        webView.callHandler("shareToSocial", new Gson().toJson(""), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                Gson gson = new Gson();
                Share share = gson.fromJson(data, Share.class);
                //弹出分享的UI
                if (null != share) {
                    ShowShare.setShareTitle(share.getShareTitle(), share.getShareDesc(), share.getShareLink(), share.getShareImgUrl());
                    ShowShare.showShare();
                }
            }
        });
    }

    //Toast 工具
    public static void showToast(String str) {
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(MyApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
        }
    }


    //WebView  参数设置
    public void setWebView( String url, AutoSwipeRefreshLayout swipeRefreshLayout, BridgeWebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setAppCacheEnabled(true);

        setting.setDomStorageEnabled(true);
        setting.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = MyApplication.getInstance().getCacheDir().getAbsolutePath() + "/webViewCache";
        setting.setAppCachePath(appCachePath);
        setting.setAllowFileAccess(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.requestFocus();
        webView.setDefaultHandler(new DefaultHandler());
        webView.loadUrl(url);
        //集体注册方法
        new WebViewUtil().webViewClient(webView);
        setRefershBridge(swipeRefreshLayout, webView);

    }


}