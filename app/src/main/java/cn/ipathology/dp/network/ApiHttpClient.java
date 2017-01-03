package cn.ipathology.dp.network;

import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.ipathology.dp.activity.user.LoginActivity;
import cn.ipathology.dp.appliaction.MyApplication;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by wdb on 2016/9/28.
 * requestWithRouter() 统一管理所有的网络请求
 * PathParmas 所有网络请求参数的封装类
 * RequestCode 网络请求状态码类
 *
 *
 */

public class ApiHttpClient {
    private static final String BASE_URL = Router.getDPBaseUrl();
    public static AsyncHttpClient client = new AsyncHttpClient();


    public static void cancelAsyHttpClient() {
        client.cancelAllRequests(true);
    }

    public static void setTime(int time) {
        client.setTimeout(time);
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteUrl(partUrl), handler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(MyApplication instance, String s, ByteArrayEntity entity, String url, AsyncHttpResponseHandler responseHandler) {

        client.post(getAbsoluteUrl(url), responseHandler);
    }

    //网络请求方式选择
    public static void requestWithRouter(PathParmas p, final CustomResponse response) {

        if (p.getMethod() == Method.GET) {
            //TODO get();
        } else {
            post(p,response);
        }

    }

    //post网络请求 使用JSONString的形式传递参数
    private static void post(PathParmas p, final CustomResponse responsed) {
        final String url = p.getUrl();
        final HttpError httpError = new HttpError();
//        ByteArrayEntity entity = null;
//        try {
//            entity = new ByteArrayEntity(p.getJsonObject().toString().getBytes(""));
//            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(p.getJsonObject().toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        client.post(MyApplication.getInstance(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                    try {
                        int code = response.getInt("resultCode");
                        String msg = response.getString("resultMsg");
                        String data = "" ;
                        if (!response.isNull("data")){
                            data = response.getString("data");
                        }

                        httpError.setMessage(msg);
                        httpError.setCode(code);
                        if (code == RequestCode.requestSuccess) {
                            //接口请求成功
                            responsed.onSuccess(data);
                        } else if (code == RequestCode.needLogin ||code ==RequestCode.tokenError) {
                            // 发送一个需要登录的广播
                            boolean isLogin = responsed.onFailure(httpError);
                            if (null == responsed || isLogin) {
                                Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MyApplication.getInstance().startActivity(intent);
                            }
                        } else {
                            responsed.onFailure(httpError);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MyApplication.getInstance(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                httpError.setMessage(throwable.getMessage());
                httpError.setCode(statusCode);

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });



    }


}
