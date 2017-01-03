package cn.ipathology.dp.util;

import android.os.Handler;

import cn.ipathology.dp.R;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wdb on 2016/6/13.
 */
public class ShowShare {
    public static OnekeyShare oks;
    public static boolean is2CallBack = false;
    public static void setShareTitle(String title, final String content, final String titleUrl,String imgUrl) {

        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //网络图片的url：所有平台
//        if (!TextUtils.isEmpty(imgUrl)){
//            oks.setImageUrl(imgUrl);
//        }else{
            oks.setImageUrl("http://oss.aliyuncs.com/ipathology/app/IHC_60@3x.png");
//        }
        //网络图片rul
        //url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(titleUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setSite(MyApplication.getInstance().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(titleUrl);
        //微信token失效 分享失败时的操作
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//            @Override
//            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                if (SinaWeibo.NAME.equals(platform.getName())) {
//                    paramsToShare.setText(content);
//                    paramsToShare.setUrl(titleUrl);
//                    paramsToShare.setImageUrl("http://oss.aliyuncs.com/ipathology/sites/default/uploads/wangdongbo/logo1024px.png");
//                    if (platform.isAuthValid()) {
//                        platform.removeAccount(true);
//                        ShareSDK.removeCookieOnAuthorize(true);
//                    }
//                }
//            }
//        });

// 启动分享GUI
    }

    public static void showShare() {
        if (!is2CallBack) {
            oks.show(MyApplication.getInstance());
            is2CallBack = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    is2CallBack = false;
                }
            }, 1000);
        }

    }
}
