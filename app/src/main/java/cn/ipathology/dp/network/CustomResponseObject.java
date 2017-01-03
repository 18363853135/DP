package cn.ipathology.dp.network;

/**
 * Created by wdb on 2016/11/16.
 * 将CustomResponse封装下来的JsonString进行解析(此时接口的网络请求已经成功)
 * object 将JsonString 向下封装成实体对象
 * HttpError 将错误信息 继续向下封装
 *
 */

public interface CustomResponseObject {
    void onSuccess(Object object);
    void onFailure(HttpError httpError);
}
