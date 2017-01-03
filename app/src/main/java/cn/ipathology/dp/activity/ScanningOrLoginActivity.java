package cn.ipathology.dp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.user.LoginActivity;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.network.CustomResponseObject;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.util.TokenUtil;
import cn.ipathology.dp.zxing.android.CaptureActivity;

public class ScanningOrLoginActivity extends Activity implements View.OnClickListener{
    private Button scanning,diagnosis;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    public ResponseData responseData = new ResponseData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_scanning_or_login);

        initView();

    }


    private void initView() {

        scanning = (Button) findViewById(R.id.scanning_or_login_scanning);
        diagnosis = (Button) findViewById(R.id.scanning_or_login_diagnosis);

        scanning.setOnClickListener(this);
        diagnosis.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scanning_or_login_scanning:
                Intent scacn = new Intent(ScanningOrLoginActivity.this,
                        CaptureActivity.class);
//                startActivityForResult(scacn, REQUEST_CODE_SCAN);
                startActivity(scacn);

                break;

            case R.id.scanning_or_login_diagnosis:
                if (TextUtils.isEmpty(new TokenUtil().getToken())){
                    Intent intent = new Intent(ScanningOrLoginActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    judgeToken();
                }
                break;
        }

    }


    //检测token是否有效
    public void judgeToken(){
        responseData.responseAccountData(new CustomResponseObject() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(MyApplication.getInstance(),"登录成功",Toast.LENGTH_SHORT).show();
                finish();
                Intent main = new Intent(ScanningOrLoginActivity.this, MainActivity.class);
                startActivity(main);
            }

            @Override
            public void onFailure(HttpError httpError) {
                Toast.makeText(MyApplication.getInstance(),httpError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 扫描二维码/条码回传
//        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
//            if (data != null) {
//
//                String content = data.getStringExtra(DECODED_CONTENT_KEY);
////                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
//                Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
