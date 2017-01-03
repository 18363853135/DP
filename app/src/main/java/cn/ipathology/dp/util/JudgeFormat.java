package cn.ipathology.dp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wdb on 16-2-24.
 */
public class JudgeFormat {

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //判断String  是否是全数字
    public static boolean isNumber(String str) {
        boolean flag = true;
        if (str.length() == 11) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                flag = false;
            }else{
                flag = true;
            }
        }else{
            flag = false;
        }
        return flag;
    }

    //判断手机格式是否正确
    public static boolean isPhoneNumber(String mobiles) {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
    }






}
