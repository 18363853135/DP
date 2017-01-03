package cn.ipathology.dp.activity.user;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;

public class AboutUsActivity extends BarWebViewActivity {

    private ImageView imageView;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_about_us);

        version = (TextView) findViewById(R.id.about_version);
        initActionBar("返回","关于我们","");
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);
        versionNumber();

        imageView = (ImageView) findViewById(R.id.aboutus_imageView);
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(false).displayer(new RoundedBitmapDisplayer(30)).build();
//        ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ihc_2x, imageView, options);

    }


    public void versionNumber() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);

            // 当前应用的版本名称
            String versionName = info.versionName;

            version.setText("云诊断平台Android版 v" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
