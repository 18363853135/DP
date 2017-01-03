package cn.ipathology.dp.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.MainActivity;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.entityClass.Login;
import cn.ipathology.dp.network.CustomResponseObject;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.util.JudgeFormat;
import cn.ipathology.dp.util.TokenUtil;

public class LoginActivity extends BarWebViewActivity implements View.OnClickListener {
    private ImageView back;
    private Button getCode;
    private TextView go;
    public boolean is2CallBack = false;
    int i = 60;//倒计时的整个时间数
    private EditText phone, code;
    private Handler mHandler = new Handler();   //全局handler
    public JudgeFormat judgeFormat = new JudgeFormat();
    public ResponseData responseData = new ResponseData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //判断是否已经登录
//        if (EMClient.getInstance().isLoggedInBefore()) {
//            startActivity(new Intent(LoginActivity.this, ChatActivity.class));
//            return;
//        }

        setContentView(R.layout.user_activity_login);

        whiteActionBar();
        hintActionBar();
        initView();

    }

    //初始化参数
    private void initView() {
        back = (ImageView) findViewById(R.id.login_img_back);
        getCode = (Button) findViewById(R.id.login_btn_getCode);
        code = (EditText) findViewById(R.id.login_edit_code);
        phone = (EditText) findViewById(R.id.login_edit_phone);
        go = (TextView) findViewById(R.id.login_txt_diagnosis);

        getCode.setOnClickListener(this);
        back.setOnClickListener(this);
        go.setOnClickListener(this);

        phone.setText(new TokenUtil().getAccountPhone());

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(code.getText())) {
                    go.setBackgroundResource(R.drawable.login_round_all_blue_press);
                } else {
                    go.setBackgroundResource(R.drawable.login_round_all_blue_normal);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_getCode:
                judgePhone();
                break;
            case R.id.login_txt_diagnosis:

                //解决快速点击时 连续访问网络的情况 is2CallBack
                if (!is2CallBack) {
                    requestLogin(phone.getText().toString(), code.getText().toString());
                    is2CallBack = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            is2CallBack = false;
                        }
                    }, 1000);
                }

                break;
            case R.id.login_img_back:
                bbsFinish();
                break;
            default:
                break;
        }
    }


    //获取验证码 判断号码是否存在
    public void judgePhone() {
        String phoneNumber = phone.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("电话号码不能为空");
            return;
        }
        if (!judgeFormat.isPhoneNumber(phoneNumber)) {
            showToast("电话号码格式错误");
            return;
        }

        new Thread(new ClassCut()).start();//开启倒计时
        getCode(phoneNumber);
        showToast("验证码已发送");


    }


    //用户登录
    public void requestLogin(String phone, String code) {

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showToast("验证不能为空");
            return;
        }
        responseData.responseLoginData(phone, code, new CustomResponseObject() {
            @Override
            public void onSuccess(Object object) {
                Login login = (Login) object;
                //保存 token 手机号码 用户头像 用户分组 性别
                new TokenUtil().setToken(login.getToken());
                new TokenUtil().setAccountPhone(login.getAccount_phone());
                new TokenUtil().setAccountheadImageUr(login.getHead_image_url());
                new TokenUtil().setAccountEnum(login.getGroup());
                new TokenUtil().setAccountSex(login.getSex());

                System.out.println("111111111" + login.getSex());


                bbsFinish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(HttpError httpError) {
                showToast(httpError.getMessage());
                System.out.println("222222" + httpError.getMessage());

            }
        });
    }

    //获取验证码
    public void getCode(String phone) {
        responseData.responseLoginCode(phone, new CustomResponseObject() {
            @Override
            public void onSuccess(Object object) {
//                code.setText();

            }

            @Override
            public void onFailure(HttpError httpError) {
                showToast("GetCode fail");
            }
        });

    }

    class ClassCut implements Runnable {//倒计时逻辑子线程

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (i > 0) {//整个倒计时执行的循环
                i--;
                mHandler.post(new Runnable() {//通过它在UI主线程中修改显示的剩余时间
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        getCode.setText(i + "秒");//显示剩余时间
                        getCode.setTextColor(Color.GRAY);
                        getCode.setClickable(false);  //控件不可用
                    }
                });
                try {
                    Thread.sleep(1000);//线程休眠一秒钟     这个就是倒计时的间隔时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //下面是倒计时结束逻辑
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    getCode.setTextColor(Color.BLACK);
                    getCode.setClickable(true);  //控件可用
                    getCode.setText("获取验证码");

                }
            });
            i = 60;//修改倒计时剩余时间变量为60秒
        }
    }


    //    //测试看图
//    public void acdSee(){
//        Intent intent = new Intent(LoginActivity.this, AcdSeeActivity.class);
//        intent.putExtra("title","切片看图");
//        intent.putExtra("url", Router.acdseeUrl);
//        startActivity(intent);
//    }

//     public void loginHuanXin(final String userName, String password){
//         EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
//             @Override
//             public void onSuccess() {
//                 EMClient.getInstance().groupManager().loadAllGroups();
//                 EMClient.getInstance().chatManager().loadAllConversations();
//                 EMClient.getInstance().getCurrentUser();
//                 showToast("登录聊天服务器成功");
////                 Log.d("==============","登录聊天服务器成功");
//

//
//             }
//
//             @Override
//             public void onProgress(int progress, String status) {
//                 Log.d("============",status);
//             }
//
//             @Override
//             public void onError(int code, String message) {
////                 showToast("登录聊天服务器失败");
//                 Log.d("==============","登录聊天服务器失败");
//
//             }
//         });
//    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
