package cn.ipathology.dp.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wdb on 2016/12/15.
 * 字符串转换工具
 */

public  class StringFormat {

    /**
     * 将String 转换为Json
     * */
    public static String stringToJsonObject(String stringName ,String string){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(stringName ,string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 将boolean 转换为Json
     * */
    public static String booleanToJsonObject(String stringName ,boolean flag){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(stringName ,flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 将JSON 转换为Stirng
     * */
    public static String jsonObjectToString(String jsonString) {
        JSONObject jsonObject = null;
        String string = null;
        try {
            jsonObject = new JSONObject(jsonString);
            string = jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return string;
    }


}
