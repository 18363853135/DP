package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/5/9.
 * 自定义Toast
 * message  显示信息
 * messageColor 文字颜色
 * backgroundColor 背景颜色
 * duration; toast 显示时间
 */
public class CustomToast {
    String message;
    String messageColor;
    String backgroundColor;
    double duration;

    public String getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(String messageColor) {
        this.messageColor = messageColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
