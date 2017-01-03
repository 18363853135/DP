package cn.ipathology.dp.network;

import org.json.JSONObject;

/**
 * Created by wdb on 2016/9/27.
 * 参数封装
 * path baseUrl后面的路径
 * jsonObject 以Json格式给后台传值
 * method  网络请求方式  post ? get
 */

public class PathParmas {

    private String path;
    private JSONObject jsonObject;
    private Method method = Method.POST;
    private static String baseUrl = Router.getDPBaseUrl();

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        PathParmas.baseUrl = baseUrl;
    }

    public PathParmas( ) {
        this.path = "";
        this.jsonObject = new JSONObject();
    }

    public PathParmas(String path) {
        this.path = path;
        this.jsonObject = new JSONObject();
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public PathParmas(JSONObject object) {
        this.jsonObject = object;
        this.jsonObject = object;
    }

    public PathParmas(Method method) {
        this.method = method;
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public PathParmas setJsonObject(JSONObject object) {
        this.jsonObject = object;
        return null;
    }

    public String getUrl() {
        return baseUrl+this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

