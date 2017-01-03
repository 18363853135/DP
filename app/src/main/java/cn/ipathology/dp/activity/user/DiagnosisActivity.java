package cn.ipathology.dp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.MainActivity;
import cn.ipathology.dp.activity.base.BaseActivity;
import cn.ipathology.dp.util.MyPopupWindow;


public class DiagnosisActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout doctorRelativeLayout, patientRelativeLayout, adminRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        whiteActionBar();

        setContentView(R.layout.user_activity_diagnosis);

        hintActionBar();
        initView();
    }

    private void initView() {
        doctorRelativeLayout = (RelativeLayout) findViewById(R.id.lay_diagnosis_doctor);
        patientRelativeLayout = (RelativeLayout) findViewById(R.id.lay_diagnosis_patient);
        adminRelativeLayout = (RelativeLayout) findViewById(R.id.lay_diagnosis_admin);

        doctorRelativeLayout.setOnClickListener(this);
        patientRelativeLayout.setOnClickListener(this);
        adminRelativeLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DiagnosisActivity.this, LoginActivity.class);
        Intent intentPersonal = new Intent(DiagnosisActivity.this,MainActivity.class);
        switch (v.getId()) {
            case R.id.lay_diagnosis_doctor:
                startActivity(intentPersonal);
                break;
            case R.id.lay_diagnosis_patient:
                startActivity(intent);
                break;
            case R.id.lay_diagnosis_admin:
                MyPopupWindow popupWindow = new MyPopupWindow();
                popupWindow.setMyPopupWindow(this,this.getWindow().getDecorView(),"");
                break;
            default:
                break;
        }
    }



}
