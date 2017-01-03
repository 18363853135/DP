package cn.ipathology.dp.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.ipathology.dp.appliaction.MyApplication;

/**
 * Created by wdb on 2016/9/28.
 */

public class FileUtil {

    //图片上传时 缩放后图片路径和名字
    public static File zoomImgSd() {
        File cache = null;
        File imgSd = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = MyApplication.getInstance().getExternalCacheDir();
            cache = new File(dir, "zoomImgSd");
            if (!cache.exists()) {
                cache.mkdir();
                cache = new File(dir, "zoomImgSd");
                imgSd = new File(cache, System.currentTimeMillis() + ".jpg");
            } else {
                imgSd = new File(cache, System.currentTimeMillis() + ".jpg");
            }
        }
        return imgSd;
    }

    //图片上传时 缩放后图片的路径
    public static File zoomImgSdPath() {
        File cache = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = MyApplication.getInstance().getExternalCacheDir();
            cache = new File(dir, "zoomImgSd");
            if (!cache.exists()) {
                cache.mkdir();
                cache = new File(dir, "zoomImgSd");
            }
        }
        return cache;
    }


    //图片上传时 把bitmap起名并保存在一定的路径下
    public static File saveThePicture(Bitmap bitmap) {

        File cache = zoomImgSd();
        if (cache != null) {
            try {
                FileOutputStream fos = new FileOutputStream(cache);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                    fos.flush();
                    fos.close();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return cache;
    }

    //imageLoader 设置图片缓存的路径
    public static File searchSd() {
        File cache = null;
        File dir = MyApplication.getInstance().getCacheDir();
        cache = new File(dir, "imageloader");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "imageloader");

        }
        return cache;
    }

    // html  Zip 解压后文件的存放路径
    public static File htmlSd() {
        File cache = null;
        File dir = MyApplication.getInstance().getCacheDir();
        cache = new File(dir, "html");
        if (!cache.exists()) {
            cache.mkdir();
            cache = new File(dir, "html");
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

    //当数据被其他软件清理掉时  使其解压assect中的数据
    public void UnzipAssets() {
        if (new File(zipHtml().getPath() + "/" + new TokenUtil().getZipName()).length() == 0) {
            try {
                UnzipAssets unzipAssets = new UnzipAssets();
                new TokenUtil().setZipName("dp.zip");
                unzipAssets.unAssetsZip("dp.zip", htmlSd().getPath());
//                new TokenUtil().setVersion("1.0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    //删除文件夹
    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();     //删除目录
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }




    //删除文件及文件夹
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }

    //判断文件夹是否为空
    public boolean ifFileNull(File file) {
        boolean flag = true;
        if (file.exists() && file.isDirectory()) {
            if (file.list().length == 0) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }


}
