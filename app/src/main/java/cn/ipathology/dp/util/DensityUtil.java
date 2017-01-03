package cn.ipathology.dp.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

import cn.ipathology.dp.appliaction.MyApplication;

/**
 * Created by wdb on 2016/5/11.
 * 手机屏幕的分辨率转换
 * 手机的 宽 高值
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取手机的宽度
     */
    public static int getDisplayWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取手机的高度
     */
    public static int getDisplayHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取手机的状态栏高度
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = MyApplication.getInstance().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }


    //        final int listViewWeight = DensityUtil.dip2px(this,188);
//        final int listViewHeight = DensityUtil.dip2px(this,55);

//        ViewTreeObserver vto2 = listView.getViewTreeObserver();
//        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                //202 为平台专家  只有平台专家才有截图功能
//                if (new TokenUtil().getAccoutEnum() == 202){
//                    setWidthHight(listView,listViewWeight ,listView.getHeight()-listViewHeight);
//                }
//
//            }
//        });


}
