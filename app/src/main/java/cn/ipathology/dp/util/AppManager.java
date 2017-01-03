package cn.ipathology.dp.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 王冬博 on 2015/10/14.
 *
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }


    public Activity currentActivity() {
         Activity activity = null;
        if (activityStack.size()!=0){
            activity = activityStack.lastElement();
        }
        return activity;
    }


    //出站
    public void popActivity() {
        if (activityStack .size() != 0) {
            activityStack.pop();
        }
    }


    public Stack<Activity> getStackActivity() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈中
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }




    /*
    *获取倒数第二个activity
    */
    public Activity reciprocalSecondActivity() {
        Activity activity = null;
        if (activityStack.size()>=2){
            activity = activityStack.get(activityStack.size() - 2);
        }
        return activity;
    }


    /**
     * 结束Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                break;
            }
        }
//		activityStack.clear();
    }

}
