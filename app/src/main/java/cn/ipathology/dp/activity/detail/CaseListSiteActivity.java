package cn.ipathology.dp.activity.detail;

import android.os.Bundle;
import android.view.View;

import cn.ipathology.dp.activity.base.BarWebViewActivity;

public class CaseListSiteActivity extends BarWebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);

    }
}
