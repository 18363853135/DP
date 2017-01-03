package cn.ipathology.dp.network;

/**
 * Created by wdb on 2016/9/28.
 * 封装网络请求响应的数据
 * message 网络请求的状态信息
 * code  网络请求的状态码
 */

public class HttpError {
    String message;
    int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
