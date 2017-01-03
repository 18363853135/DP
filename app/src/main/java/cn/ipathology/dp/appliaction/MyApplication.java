package cn.ipathology.dp.appliaction;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.analytics.MobclickAgent;

import cn.ipathology.dp.util.ImageLoaderUtil;


/**
 * Created by wdb on 2016/9/27.
 * 初始化数据
 * 全局变量
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {

        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }


    public static void setInstance(MyApplication instance)
    {
        MyApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //环信
//        Helper helper = new Helper();
//        helper.init(instance);

        //友盟
        MobclickAgent.setDebugMode(true);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(new ImageLoaderUtil().searchSd()))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 60 * 1000, 60 * 1000))
                .build();

        //设置参数
        ImageLoader.getInstance().init(config);

    }

}
