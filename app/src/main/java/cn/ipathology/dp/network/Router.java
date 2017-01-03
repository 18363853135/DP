package cn.ipathology.dp.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ipathology.dp.util.TokenUtil;

/**
 * Created by wdb on 2016/9/27.
 * 统一管理 所有的网络请求路径
 * 所有的网络请求参数 统一在此 封装成JsonString
 *
 */


public class Router {

    public  static String DPBaseUrl = "http://192.168.1.167:8081/api/";
    private static String baseUrl = "file:///data/data/cn.ipathology.dp/cache/DPHtml/index.html?";       //基路径
    public  static String homeUrl  = baseUrl+"p=user_port&f=app";
//    public  static String homeUrl  = "http://192.168.1.137/med100_app/index.html?p=user_port&f=app";
    public  static String baseHeadImgUrl  = "http://img.med100.cn/";
    public  static String baseSlideImgUrl  = "http://192.168.1.137/map-wap/index.html?filename=";  //切图浏览列表地址更目录

    public static String getDPBaseUrl() {
        return DPBaseUrl;
    }
    public static void setDPBaseUrl(String DPBaseUrl) {
        Router.DPBaseUrl = DPBaseUrl;
    }

    /**
     *登录请求 参数封装成JsonString
     */
    public PathParmas getLoginUrl(String username, String password) {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
            jsonObject.put("account_phone", username);
            jsonObject.put("code",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("account/loginVercode");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);

        return p;
    }
    /**
     *用户信息 参数封装成JsonString
     */
    public PathParmas getAccountUrl() {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
            Log.d("token = ",new TokenUtil().getToken().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("account/getAccount");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);
        return p;
    }
    /**
     *获取验证码
     */
    public PathParmas getLoginCode(String phone) {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
            jsonObject.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("account/getLoginVercode");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);
        return p;
    }
    /**
     *用户信息  枚举值
     */
    public PathParmas getListEnumData() {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("data/listEnumData");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);
        return p;
    }
    /**
     *切片浏览
     */
    public PathParmas getListCaseSlide(String caseId) {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
            jsonObject.put("case_id",caseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("case/listCaseSlideSimple");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);
        return p;
    }
    /**
     *二维码扫描结果处理接口
     */
    public PathParmas getCaseReportBySystemNo(String systemNo) {
        PathParmas p = new PathParmas();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",new TokenUtil().getToken());
            jsonObject.put("system_no",systemNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        p.setPath("case/getCaseReportBySystemNo");
        p.setMethod(Method.POST);
        p.setJsonObject(jsonObject);
        return p;
    }

}
