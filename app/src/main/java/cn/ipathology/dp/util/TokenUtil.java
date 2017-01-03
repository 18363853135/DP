package cn.ipathology.dp.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ipathology.dp.appliaction.MyApplication;

/**
 * Created by wdb on 2016/9/28.
 * 数据库类 保存所有的数据信息
 * 数据库名  DpToken
 * token
 * account_phone 电话号码
 * group  用户分组  默认只要专家才可以截屏
 *
 */

public class TokenUtil {

    private SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("DpToken", Context.MODE_PRIVATE);

   public String getToken(){
       String token = sharedPreferences.getString("token","");
       return  token;
   }

    public void setToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
    /**
     * 保存枚举类型
     * */
    public String getMenu(){
       String token = sharedPreferences.getString("menu","");
       return  token;
   }

    public void setMenu(String menu){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("menu",menu);
        editor.commit();
    }
    /**
     * 保存PDF 的文件名
     * */
    public String getPDFName(){
       String token = sharedPreferences.getString("pdf","");
       return  token;
   }

    public void setPDFName(String pdf){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pdf",pdf);
        editor.commit();
    }
    /**
     * 用户号码  保存
     * */
    public String getAccountPhone(){
       String phone = sharedPreferences.getString("account_phone","");
       return  phone;
   }

    public void setAccountPhone(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account_phone",phone);
        editor.commit();
    }


    /**
     * 用户头像 保存
     * */
    public String getAccountheadImageUrl(){
       String phone = sharedPreferences.getString("head_image_url","");
       return  phone;
   }

    public void setAccountheadImageUr(String headImageUrl){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("head_image_url",headImageUrl);
        editor.commit();
    }
    /**
     * 用户分组 保存
     * */
    public int getAccoutEnum(){
       int phone = sharedPreferences.getInt("accountEnum",200);
       return  phone;
   }

    public void setAccountEnum(int enumString){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("accountEnum",enumString);
        editor.commit();
    }
    /**
     * 用户分组 性别
     * */
    public int getAccoutSex(){
       int phone = sharedPreferences.getInt("accountSex",2);
       return  phone;
   }

    public void setAccountSex(int sex){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("accountSex",sex);
        editor.commit();
    }


    //apk 是否是第一次打开  第一次的打开true ？  false
    public boolean getFirstOpenApk() {
        boolean userEmail = sharedPreferences.getBoolean("firstOpenApk",true);
        return userEmail;
    }
    public void setFirstOpenApk(boolean version) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstOpenApk", version);
        editor.commit();
    }

    /**
     * html 解压的文件名
     */
    public String getZipName() {
        String userEmail = sharedPreferences.getString("zipName", "dp.zip");
        return userEmail;
    }

    public void setZipName(String version) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("zipName", version);
        editor.commit();
    }

    /**是否已经解压
     *@return
     */
    public Boolean getIfUnZip() {
        //false 没有解压  true  已经解压
        Boolean flag = sharedPreferences.getBoolean("unZip", false);
        return flag;
    }
    public void setIfUnZip(Boolean version) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("unZip", version);
        editor.commit();
    }

    //用户退出时清空数据
    public void clearToken(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.remove("menu");
        editor.remove("head_image_url");
        editor.remove("account_phone");
        editor.remove("accountEnum");
        editor.remove("firstOpenApk");
        editor.remove("pdf");
        editor.remove("accountSex");
        editor.remove("zipName");
        editor.commit();
    }


}
