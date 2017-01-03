package cn.ipathology.dp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;

import java.io.File;
import java.io.IOException;

import cn.ipathology.dp.R;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.util.FileUtil;
import cn.ipathology.dp.util.ImageLoaderUtil;
import cn.ipathology.dp.util.TokenUtil;
import cn.ipathology.dp.util.UnzipAssets;
import cn.ipathology.dp.util.ZipExtractorTask;


public class NavigationActivity extends Activity {

    public static AsyncHttpClient client = new AsyncHttpClient();
     FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        fileUtil = new FileUtil();
//        upDateHtml();
//        judgeActivity();

        UnzipAssets();

    }

    public void UnzipAssets() {
        try {
            UnzipAssets unzipAssets = new UnzipAssets();
            new TokenUtil().setZipName("dp.zip");
            unzipAssets.unAssetsZip("dp.zip", ImageLoaderUtil.htmlSd().getPath());
            judgeActivity();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void upDateHtml() {
//        //网络请求 检测版本是否需要更新
//        client.get(Router.getAppUpdateUrl(), new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(bytes));
//                    int code = jsonObject.getInt("resultCode");
//                    String msg = jsonObject.getString("resultMsg");
//                    String data = jsonObject.getString("data");
//                    if (code == 0) {
//                        Gson gson = new Gson();
//                        UpdateVersion updateVersion = gson.fromJson(data, UpdateVersion.class);
//                        if (null != updateVersion) {
//                            doIfDownLoadWork(updateVersion, updateVersion.getDownload_url());
//                            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
//                            // 当前应用的版本名称
//                            int versionName = info.versionCode;
//                            new TokenUtil().setApk(info.versionName);
//                            Double versionCode = Double.parseDouble(versionName + "");
//                            if (versionCode < Double.parseDouble(updateVersion.getApp_android_version()) * 1000) {
//                                new TokenUtil().setApkVersion(true);
//                                new TokenUtil().setApkUpdateUrl(updateVersion.getApp_android_url());
//                            } else {
//                                new TokenUtil().setApkVersion(false);
//                            }
//                        }
//                    } else {
//                        if (!TextUtils.isEmpty(msg)) {
//                            Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    judgeActivity();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                new TokenUtil().setApkVersion(false);
//                UnzipAssets();
//                new TokenUtil().setFirst(false);
//                Toast.makeText(MyApplication.getInstance(), "网络连接失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    //判断下载
//    private void doIfDownLoadWork(UpdateVersion updateVersion, String WebUrl) {
//        if (Double.parseDouble(new TokenUtil().getVersion()) < Double.parseDouble(updateVersion.getVersion())) {
//            new TokenUtil().setVersion(updateVersion.getVersion());
//            fileUtil.deleteFile(ImageLoaderUtil.zipHtml());
//            DownLoaderTask task = new DownLoaderTask(WebUrl, ImageLoaderUtil.zipHtml().getPath(), this,"");
//            task.execute();
//            clearWebViewCache();
//        }
//    }

    //解压
    public void doZipExtractorWork() {

        // if 判断是否解压过  先使其每次都解压
        if (new TokenUtil().getIfUnZip() == false) {
            // 解压之前删除旧的html文件
            fileUtil.deleteFile(ImageLoaderUtil.htmlSd());
            ZipExtractorTask task = new ZipExtractorTask(ImageLoaderUtil.zipHtml().getPath() + "/" + new TokenUtil().getZipName(), ImageLoaderUtil.htmlSd().getPath(), MyApplication.getInstance(), true);
            task.execute();
        }

        //如果为空  则从assets 解压  使其不为空 每次都有值 防止手机解压不到文件（一些手机上的bug）
        if (new File(ImageLoaderUtil.zipHtml().getPath() + "/" + new TokenUtil().getZipName()).length() == 0) {
            UnzipAssets();
        }

//        //自动更新后 显示解压Assets 文件
//        if (new TokenUtil().getFirst()) {
//            UnzipAssets();
//            new TokenUtil().setFirst(false);
//        }

    }

    public void judgeActivity() {

        //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        Intent intent = new Intent(NavigationActivity.this, GuideActivity.class);
        startActivity(intent);
        this.finish();
    }

}

