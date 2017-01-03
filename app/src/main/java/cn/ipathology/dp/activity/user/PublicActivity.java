package cn.ipathology.dp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;


public class PublicActivity extends BarWebViewActivity {
    private EditText name,txtIntroduction;
    private LinearLayout nameLinear,introductionLinear;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_public);


        name = (EditText) findViewById(R.id.ture_name);
        txtIntroduction = (EditText) findViewById(R.id.introduction);
        nameLinear = (LinearLayout) findViewById(R.id.true_linearLayout);
        introductionLinear = (LinearLayout) findViewById(R.id.true_introduction);

        //获得修改前的值
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        name.setText(intent.getStringExtra("toUpdate"));     //
        txtIntroduction.setText(intent.getStringExtra("toUpdate"));
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        txtIntroduction.setInputType(InputType.TYPE_CLASS_TEXT);



        if (null != intent.getStringExtra("toUpdate")) {
            name.setSelection(intent.getStringExtra("toUpdate").length());   //EditText光标得位置
            txtIntroduction.setSelection(intent.getStringExtra("toUpdate").length());   //EditText光标得位置
        }

        switch (data) {
            case "trueUnit":
                name.setHint("请填写工作单位");
                introductionLinear.setVisibility(View.GONE);
                initActionBar("返回","单位","完成");
                break;

            case "trueIdentity":
                name.setHint("请填写头衔");
                initActionBar("返回","头衔","完成");
                introductionLinear.setVisibility(View.GONE);

                break;
            case "trueOffice":
                name.setHint("请填写职务");
                initActionBar("返回","职务","完成");
                introductionLinear.setVisibility(View.GONE);
                break;
            case "trueIntroduction":
                txtIntroduction.setHint("请填写个人信息");
                initActionBar("返回","个人简介","完成");
                nameLinear.setVisibility(View.GONE);
                break;
        }

        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String nameString = name.getText().toString();
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bar_txt_right_one:
                if (!nameString.isEmpty()) {
                    switch (data) {
                        case "trueAddress":
                            intent.putExtra("name", nameString);
                            intent.putExtra("back", "trueAddress");
                            setResult(RESULT_OK, intent);
                            bbsFinish();
                            break;
                        case "trueUnit":
                            intent.putExtra("name", nameString);
                            intent.putExtra("back", "trueUnit");
                            setResult(RESULT_OK, intent);
                            bbsFinish();
                            break;
                        case "trueIdentity":
                            intent.putExtra("name", nameString);
                            intent.putExtra("back", "trueIdentity");
                            setResult(RESULT_OK, intent);
                            bbsFinish();
                            break;
                        case "trueOffice":
                            intent.putExtra("name", nameString);
                            intent.putExtra("back", "trueOffice");
                            setResult(RESULT_OK, intent);
                            bbsFinish();
                            break;
                        case "trueIntroduction":
                            intent.putExtra("name", nameString);
                            intent.putExtra("back", "trueIntroduction");
                            setResult(RESULT_OK, intent);
                            bbsFinish();
                            break;
                    }
                } else {
                    showToast("信息不能为空");
                }
                break;
        }
    }
}
