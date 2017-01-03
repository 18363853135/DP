package cn.ipathology.dp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.ScanningOrLoginActivity;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.util.TokenUtil;


public class SettingActivity extends BarWebViewActivity {
    private ListView listView;
    private List setList;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_setting);

        initData();              //初始化数据
        initActionBar("返回","设置","");    //设置导航栏 标题
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);

        listView = (ListView) findViewById(R.id.set_listView);
        quit = (Button) findViewById(R.id.set_quit);
        quit.setOnClickListener(new onclick());

        SimpleAdapter chat = new SimpleAdapter(getBaseContext(), setList, R.layout.layout_listview,
                new String[]{"txt", "img"}, new int[]{R.id.list_text, R.id.list_img});
        listView.setAdapter(chat);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {                      //未登陆页面得   信息反馈
                    Intent intentFeedBack = new Intent(getBaseContext(), FeedBackActivity.class);
                    startActivity(intentFeedBack);
                } else if (i == 1) {                               //未登陆页面得   关于我们
                    Intent intentAbout = new Intent(getBaseContext(), AboutUsActivity.class);
                    startActivity(intentAbout);
                }
            }
        });


    }


    //未登录页面添加数据
    public void initData() {
        setList = new ArrayList();

        Map m = new HashMap();
        m.put("txt", "意见反馈");
        m.put("img", R.mipmap.ic_btn_search_go);
        setList.add(m);

        m = new HashMap();
        m.put("txt", "关于我们");
        m.put("img", R.mipmap.ic_btn_search_go);
        setList.add(m);

    }

    public class onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.set_quit:                //退出
                    dpLogout();
            }
        }
    }

    //云诊断平台退出登录
    public void dpLogout(){

        new TokenUtil().clearToken();
        showToast("用户已退出");
        Intent intent = new Intent(SettingActivity.this, ScanningOrLoginActivity.class);
        startActivity(intent);
        //极光推送
        // JPushInterface.setAliasAndTags(getBaseContext(),"",null);

        bbsFinish();
    }


    //环信退出登录
//    void logout() {
//
//        EMClient.getInstance().logout(false, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        showToast("退出成功");
//
////                        new TokenUtil().cleckToken();
////                        showToast("用户已退出");
////
////                        JPushInterface.setAliasAndTags(getBaseContext(), "", null);
////
////                        Intent intent = new Intent();
////                        intent.setAction("unLogin");
////                        intent.putExtra("unlogin", "unlogin");
////                        getBaseContext().sendBroadcast(intent);
////
////                        startActivity(new Intent(getBaseContext(), huaxiaapp.ipathology.cn.ihc.activity.user.LoginActivity.class));
////                        bbsFinish();
//
//                    }
//                });
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//
//            }
//        });
//    }


}
