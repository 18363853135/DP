package cn.ipathology.dp.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.ipathology.dp.entityClass.Login;
import cn.ipathology.dp.entityClass.Slide;

/**
 * Created by wdb on 2016/11/15.
 * onFailure 中return false时禁止弹出登录页面  true 允许弹出登录框
 * 数据模块请求 向下封装成对象/list
 */

public class ResponseData {

    public void responseLoginData(String username, String password, final CustomResponseObject customResponseObject) {

        //将前面的传值 在getLoginUrl()进行封装JsonObject
        PathParmas pathParmas = new Router().getLoginUrl(username, password);
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                Gson gson = new Gson();
                Login login = gson.fromJson(jsonString, Login.class);
                customResponseObject.onSuccess(login);

            }

            @Override
            public boolean onFailure(HttpError httpError) {
                customResponseObject.onFailure(httpError);

                return true;
            }
        });

    }

    /**
     * 用户信息
     */
    public void responseAccountData(final CustomResponseObject customResponseObject) {

        //将前面的传值进行封装 成JsonObject
        PathParmas pathParmas = new Router().getAccountUrl();
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                Gson gson = new Gson();
                Login login = gson.fromJson(jsonString, Login.class);
                customResponseObject.onSuccess(login);

            }

            @Override
            public boolean onFailure(HttpError httpError) {
                customResponseObject.onFailure(httpError);

                return true;
            }
        });

    }

    /**
     * 获取验证码
     */
    public void responseLoginCode(String phone, final CustomResponseObject customResponseObject) {

        //将前面的传值进行封装 成JsonObject
        PathParmas pathParmas = new Router().getLoginCode(phone);
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                System.out.println("===============" + jsonString);
                Log.d("==========", jsonString);
//                Gson gson = new Gson();
//                Login login = gson.fromJson(jsonString, Login.class);
//                customResponseObject.onSuccess(login);

            }

            @Override
            public boolean onFailure(HttpError httpError) {
                Log.d("==========", httpError.getMessage());
                customResponseObject.onFailure(httpError);
                return true;
            }
        });

    }

    /**
     * 所有的枚举值
     */
    public void responseListEnumData(final CustomResponse customResponse) {

        //进行封装JsonObject
        PathParmas pathParmas = new Router().getListEnumData();
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                customResponse.onSuccess(jsonString);

            }

            @Override
            public boolean onFailure(HttpError httpError) {
                customResponse.onFailure(httpError);
                return false;
            }
        });

    }

    /**
     * 切片浏览数据
     */
    public void responseListCaseSlide(String caseId ,final CustomResponseData customResponse) {

        //进行封装JsonObject
        PathParmas pathParmas = new Router().getListCaseSlide(caseId);
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                Gson gson = new Gson();
                List<Slide> slideList = gson.fromJson(jsonString, new TypeToken<ArrayList<Slide>>() {
                }.getType());
                customResponse.onSuccess(slideList);
            }

            @Override
            public boolean onFailure(HttpError httpError) {
                customResponse.onFailure(httpError);
                return false;
            }
        });

    }


    /**
     * 二维码接口处理
     */
    public void responseCaseReportBySystemNo(String caseId ,final CustomResponse customResponse) {

        //进行封装JsonObject
        PathParmas pathParmas = new Router().getCaseReportBySystemNo(caseId);
        ApiHttpClient.requestWithRouter(pathParmas, new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                customResponse.onSuccess(jsonString);
            }

            @Override
            public boolean onFailure(HttpError httpError) {
                customResponse.onFailure(httpError);
                return false;
            }
        });

    }


}
