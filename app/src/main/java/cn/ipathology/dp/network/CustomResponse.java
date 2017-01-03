package cn.ipathology.dp.network;

/**
 * Created by wdb on 2016/9/28.
 * String 网络请求成功  data中的数据 向下封装
 * HttpError 网络请求信息 向下 封装
 */

public interface CustomResponse {
    void onSuccess(String jsonString);
    boolean onFailure(HttpError httpError);
}
