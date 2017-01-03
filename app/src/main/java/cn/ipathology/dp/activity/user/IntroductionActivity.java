package cn.ipathology.dp.activity.user;

import android.os.Bundle;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;

public class IntroductionActivity extends BarWebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_introduction);
    }
}
