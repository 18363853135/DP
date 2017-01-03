package cn.ipathology.dp.network;

import java.util.List;

/**
 * Created by wdb on 2016/9/28.
 * 将CustomResponse封装下来的JsonString进行解析(此时接口的网络请求已经成功)
 * List 将JsonString 向下封装成list(解析结果可能包含多个object时)
 * HttpError 将错误信息 继续向下封装
 */

public interface CustomResponseData {
    void onSuccess(List list);
    void onFailure(HttpError httpError);
}
