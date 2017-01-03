package cn.ipathology.dp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.umeng.analytics.MobclickAgent;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.user.LoginActivity;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.network.CustomResponseObject;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.util.TokenUtil;
import cn.ipathology.dp.zxing.android.CaptureActivity;


public class GuideActivity extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener {
    private TextView textOne, textTwo, textThree, guideGo;
    public ResponseData responseData = new ResponseData();
    public View view0ne, viewTwo, viewThree, viewFour;
    private GestureDetector gestureDetector = null;
    private ViewFlipper viewFlipper = null;
    private Activity mActivity = null;
    private boolean is2CallBack = false;
    private Button scanning, diagnosis;
    private ImageView imgFour;
    private boolean flag = true; //是否可以滑动
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mActivity = this;
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        guideGo = (TextView) findViewById(R.id.guide_go);
        guideGo.setVisibility(View.GONE);
        gestureDetector = new GestureDetector(this);                // 声明检测手势事件
        initView();

        flag = new TokenUtil().getFirstOpenApk();
        if (!flag) {
            viewFlipper.setDisplayedChild(3);
        }

        new TokenUtil().setFirstOpenApk(false);
        initFourView();
        initEvent();

    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        view0ne = inflater.inflate(R.layout.layout_guide_one, null);
        viewTwo = inflater.inflate(R.layout.layout_guide_two, null);
        viewThree = inflater.inflate(R.layout.layout_guide_three, null);
        viewFour = inflater.inflate(R.layout.layout_guide_four, null);

        textOne = (TextView) view0ne.findViewById(R.id.layout_guide_one);
        textTwo = (TextView) viewTwo.findViewById(R.id.layout_guide_two);
        textThree = (TextView) viewThree.findViewById(R.id.layout_guide_three);

        textOne.setVisibility(View.GONE);
        textTwo.setVisibility(View.GONE);
        textThree.setVisibility(View.GONE);

        viewFlipper.addView(view0ne);
        viewFlipper.addView(viewTwo);
        viewFlipper.addView(viewThree);
        viewFlipper.addView(viewFour);

    }

    //初始化viewFour  即登录页面
    public void initFourView() {
        scanning = (Button) viewFour.findViewById(R.id.scanning_or_login_scanning);
        diagnosis = (Button) viewFour.findViewById(R.id.scanning_or_login_diagnosis);
    }

    private void initEvent() {

        textOne.setOnClickListener(this);
        textTwo.setOnClickListener(this);
        textThree.setOnClickListener(this);

        scanning.setOnClickListener(this);
        diagnosis.setOnClickListener(this);


        textOne.getBackground().setAlpha(125);
        textTwo.getBackground().setAlpha(125);
        textThree.getBackground().setAlpha(125);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 将触屏事件交给手势识别类处理
        viewFlipper.stopFlipping();                // 点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        return gestureDetector.onTouchEvent(event);        // 注册手势事件

    }

    @Override
    public boolean onFling(final MotionEvent e1, final MotionEvent e2, float velocityX, float velocityY) {
        if (!flag) {
            return true;
        }
        if (e2.getX() - e1.getX() > 120) {             // 从左向右滑动（左进右出）
            if (viewFlipper.getDisplayedChild() == 0) {
                viewFlipper.stopFlipping();
                return false;
            } else {
                Animation rInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_in);    // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
                Animation rOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
                viewFlipper.setInAnimation(rInAnim);
                viewFlipper.setOutAnimation(rOutAnim);
                viewFlipper.showPrevious();
                return true;
            }
        } else if (e2.getX() - e1.getX() < -120) {         // 从右向左滑动（右进左出）
            if (viewFlipper.getDisplayedChild() == 3) {
                viewFlipper.stopFlipping();
                return false;
            } else {
                Animation lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
                Animation lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);    // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
                viewFlipper.setInAnimation(lInAnim);
                viewFlipper.setOutAnimation(lOutAnim);
                viewFlipper.showNext();
                return true;
            }

        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_guide_one:
                viewFlipper.setDisplayedChild(3);
                break;
            case R.id.layout_guide_two:
                viewFlipper.setDisplayedChild(3);
                break;
            case R.id.layout_guide_three:
                Animation lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
                Animation lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);    // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
                viewFlipper.setInAnimation(lInAnim);
                viewFlipper.setOutAnimation(lOutAnim);
                viewFlipper.setDisplayedChild(3);
                break;
            case R.id.guide_go:
                viewFlipper.setDisplayedChild(3);
                break;
            case R.id.scanning_or_login_scanning:
                Intent scacn = new Intent(GuideActivity.this, CaptureActivity.class);
//               startActivityForResult(scacn, REQUEST_CODE_SCAN);
                startActivity(scacn);
                break;
            case R.id.scanning_or_login_diagnosis:

//                //  需要使用Intent类的第2个参数指定Uri
//                Intent intent = new Intent("net.blogjava.mobile.MYACTION", Uri
//                        .parse("abc://调用其他应用程序的Activity"));
//                //  设置value属性值
//                intent.putExtra("value", "调用成功");
//                //  调用ActionActivity中的Main
//                startActivity(intent);
                https://git.oschina.net/1173964585/DP.gitｋｋｋ
                // git@git.oschina.net:1173964585/DP.gitｌｌｌｈｈ　ｈｈｈ
                              if (!is2CallBack) {
                    if (TextUtils.isEmpty(new TokenUtil().getToken())) {
                        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        judgeToken();
                    }
                    is2CallBack = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            is2CallBack = false;
                            //test　Ｇｉｔ
                        }
                    }, 500);
                }

                break;
            default:
                break;
        }

    }


    //检测token是否有效
    public void judgeToken() {
        responseData.responseAccountData(new CustomResponseObject() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(MyApplication.getInstance(), "登录成功", Toast.LENGTH_SHORT).show();
                finish();
                Intent main = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(main);
            }
            @Override
            public void onFailure(HttpError httpError) {
                Toast.makeText(MyApplication.getInstance(), httpError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}

