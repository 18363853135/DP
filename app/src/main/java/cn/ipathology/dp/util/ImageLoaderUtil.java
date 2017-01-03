package cn.ipathology.dp.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

import cn.ipathology.dp.R;
import cn.ipathology.dp.appliaction.MyApplication;

public class ImageLoaderUtil {


    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.no_image_200)     //加载图片时的图片
            .showImageOnFail(R.mipmap.no_image_200)       //没有图片资源时的默认图片
            .showImageForEmptyUri(R.mipmap.no_image_200)   //加载失败时的图片
            .cacheInMemory(true)                           //启用内存缓存
            .cacheOnDisk(true)                              //启用外存缓存
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .displayer(new FadeInBitmapDisplayer(200))
//                .extraForDownloader(new HashMap<String, Str)
            .build();
    private static DisplayImageOptions optionsBig = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.icon_loading)
            .showImageOnFail(R.mipmap.no_image_800)
            .showImageForEmptyUri(R.mipmap.no_image_800)
            .cacheInMemory(true).cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .displayer(new FadeInBitmapDisplayer(200)).build();
    private static DisplayImageOptions headerOptioins = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.default_head)
            .showImageOnFail(R.mipmap.default_head)
            .showImageForEmptyUri(R.mipmap.default_head)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .displayer(new FadeInBitmapDisplayer(200))
            .build();

    public static void display(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayBigPhoto(String uri, ImageView imageView) {

        ImageLoader.getInstance().displayImage(uri, imageView, optionsBig);
    }


    public static void displayImg(String uri) {
        String imgUri = null;
        ImageLoader.getInstance().loadImage(uri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);

            }
        });
    }

    //imageLoader 设置图片缓存的路径
    public static File searchSd() {
        File cache = null;
//        File dir = MyApplication.getInstance().getCacheDir();
        File dir = Environment.getExternalStorageDirectory();
        cache = new File(dir, "imageloader");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "imageloader");

        }
        return cache;
    }
    //pdf 下载地址
    public static File PDF() {
        File cache = null;
        File dir = MyApplication.getInstance().getCacheDir();
        cache = new File(dir, "PDF");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "PDF");
        }
        return cache;
    }

    // html  Zip 解压后的路径
    public static File htmlSd() {
        File cache = null;
        File dir = MyApplication.getInstance().getCacheDir();

        cache = new File(dir, "DPHtml");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "DPHtml");
        }
        return cache;
    }

    // html  Zip 存储的的路径   ROM
    public static File zipHtml() {

        File cache = null;
        File dir = MyApplication.getInstance().getCacheDir();
        cache = new File(dir, "ZipHtml");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "ZipHtml");
        }
        return cache;
    }

    // apk  存储的的路径
    public static File apkFile() {
        File cache = null;
        File dir = MyApplication.getInstance().getExternalCacheDir();
        cache = new File(dir, "apk");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "apk");
        }
        return cache;
    }

}
