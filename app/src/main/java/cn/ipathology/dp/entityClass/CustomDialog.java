package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/10/31.
 * 用于单选框 传值的实体类
 *
 */

public class CustomDialog {
    private String name;
    private int count;

    public  CustomDialog(){}

    public CustomDialog(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
