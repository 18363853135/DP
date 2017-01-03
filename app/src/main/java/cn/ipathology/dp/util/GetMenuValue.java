package cn.ipathology.dp.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.ipathology.dp.entityClass.Menu;

/**
 * Created by wdb on 2016/11/18.
 */

public class GetMenuValue {

    List<Menu> listMenu = new ArrayList<>();
    Gson gson = new Gson();

    public GetMenuValue() {

    }

    //通过code  获取text  codeType字段用来用来快速查找 code所在的租
    public String getTextFromCode(String codeType ,int code){
        String text = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new TokenUtil().getMenu());
            this.listMenu = gson.fromJson(jsonObject.getString(codeType), new TypeToken<ArrayList<Menu>>() {
            }.getType());

            for (Menu menu : listMenu) {
                if (menu.getCode() == code) {
                    text = menu.getText();
                    break ;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return text;
    }

    //通过code  获取key  codeType字段用来用来快速查找 code所在的租
    public String getKeyFromCode(String codeType ,int code){
        String text = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new TokenUtil().getMenu());
            this.listMenu = gson.fromJson(jsonObject.getString(codeType), new TypeToken<ArrayList<Menu>>() {
            }.getType());

            for (Menu menu : listMenu) {
                if (menu.getCode() == code) {
                    text = menu.getKey();
                    break ;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return text;
    }
}
