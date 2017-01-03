package cn.ipathology.dp.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;

public class ChangePhoneActivity extends BarWebViewActivity {
    private EditText newPhone, code;
    private TextView getCode, oldPhone;
    private Thread thread = null;                //开启倒计时
    private Handler mHandler = new Handler();     //全局handler
    private int i = 60;                            //倒计时的整个时间数
    private int type = 4;                          //后台请求得参数 4为修改 1为激活

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_activity_change_phone);

        initActionBar("返回", "手机修改", "完成");
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);
        findById();


    }

    public void findById() {
        oldPhone = (TextView) findViewById(R.id.phone_old_number);
        newPhone = (EditText) findViewById(R.id.phone_new_number);
        getCode = (TextView) findViewById(R.id.phone_getCode);
        code = (EditText) findViewById(R.id.phone_code);
        getCode.setOnClickListener(this);

    }


    //判断手机号码格式是否正确
    public void initData() {

        String newPhoneStr = newPhone.getText().toString();
        if (newPhoneStr.length() != 0) {
            if (newPhoneStr.length() == 11) {
//                getCode(newPhoneStr);
            } else {
                showToast("号码格式不正确");
            }
        } else {
            showToast("新号码不能为空");
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.phone_getCode:
                String newPhoneStr = newPhone.getText().toString();
                if (newPhoneStr.length() == 11) {
                    thread = new Thread(new ClassCut());//开启倒计时
                    thread.start();
                }
                initData();
                break;
            case R.id.bar_txt_right_one:
                showToast("手机号码修改成功");
                break;

            default:
                break;
        }
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



}
