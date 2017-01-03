package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/11/18.
 * 解析保存的枚举类型并且返回相应的String
 */

public class Menu {
    /**
     * text: "其他固定"
     * code: 3
     * key: "other"
     */
    public String text ;
    public int code ;
    public String key ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
