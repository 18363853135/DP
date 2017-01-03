package cn.ipathology.dp.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.util.TokenUtil;


public class FeedBackActivity extends BarWebViewActivity {

    private EditText content;
    private Button feedBackSubmit;
    private LinearLayout linearLayout;
    private int type = 5;            //意见反馈参数 这里默认为5 ;
    private int phone_type = 2;      //意见反馈 1 iOS，2 Android ;
    public static AsyncHttpClient client = new AsyncHttpClient();
//    private static ResponseData loginHandler = new ResponseData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_feed_back);

        initActionBar("返回","意见反馈","");
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);

        initFeedBackData();

    }

    public void initFeedBackData() {
        //登陆状态phone 可不填
        content = (EditText) findViewById(R.id.feedBack_position);
        feedBackSubmit = (Button) findViewById(R.id.feedBack_sunmit);
        linearLayout = (LinearLayout) findViewById(R.id.feedback_linearLayout);
        edit(content);
        feedBackSubmit.setOnClickListener(new onclick());
    }


    public void execteFeedBackJudge(String position){

        if (position.toString().length() >= 5 ) {
            linearLayout.setVisibility(View.VISIBLE);
            RequestParams requestParams = new RequestParams();
            requestParams.put("token",new TokenUtil().getToken());
            requestParams.put("type",type);
            requestParams.put("content",position);
            requestParams.put("phone_type",phone_type);

            //手机的型号 系统 和当前程序的版本
//            requestParams.put("phone_version","IHC  "+android.os.Build.MODEL+"  "+android.os.Build.VERSION.RELEASE+"  "+new TokenUtil().getApk());
//            client.post(Router.getFeedBackUrl(), requestParams, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                    linearLayout.setVisibility(View.GONE);
//                    showToast("信息已收到，感谢您的反馈");
//                }
//                @Override
//                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                    linearLayout.setVisibility(View.GONE);
//                    showToast("信息已收到，感谢您的反馈");
//                }
//            });
        } else {
            showToast("内容不能小于５个字符");
        }
    }

    //edit 自动换行
    public void edit(final EditText feedBackPosition) {
        feedBackPosition.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        feedBackPosition.setGravity(Gravity.TOP);
        feedBackPosition.setSingleLine(false);
        feedBackPosition.setHorizontallyScrolling(false);

        feedBackPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(feedBackPosition.getText())){
                    feedBackSubmit.setBackgroundResource(R.drawable.login_round_all_blue_press);
                }else{
                    feedBackSubmit.setBackgroundResource(R.drawable.login_round_all_blue_normal);
                }
            }
        });
    }

    public class onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.feedBack_sunmit:
                    checkToken();
                    //提交反馈信息
                    break;
                default:
                    break;
            }
        }
    }

    public void checkToken(){
        //请求接口 测试Token是不是有效 有效：传给接口 ？ 无效 请求登录
        boolean flag = true;
//        loginHandler.executeUserDetailWeb(flag, new CustomResponseData() {
//            @Override
//            public void onSuccess(List list) {
//                execFeedBackJudge(content.getText().toString());
//            }
//
//            @Override
//            public void onFailure(HttpError errorString)
//            {
//                showToast(errorString.getMessage());
//            }
//        });
    }

}
