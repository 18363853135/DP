package cn.ipathology.dp.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import cn.ipathology.dp.R;

public class BaseHomeActionBarActivity extends BaseWebViewActivity {
    public View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_action_bar);
        initActionBar("");

    }

    //设置导航栏效果
    public void initActionBar(String centerTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_home_action_bar, null);
        actionBar.setCustomView(view);
        Toolbar parent =(Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }
}
