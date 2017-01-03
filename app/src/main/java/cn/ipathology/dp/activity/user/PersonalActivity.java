package cn.ipathology.dp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.entityClass.CustomDialog;
import cn.ipathology.dp.entityClass.Login;
import cn.ipathology.dp.network.CustomResponse;
import cn.ipathology.dp.network.CustomResponseObject;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.network.Router;
import cn.ipathology.dp.util.DialogUtil;
import cn.ipathology.dp.util.GetMenuValue;
import cn.ipathology.dp.util.ImageLoaderUtil;
import cn.ipathology.dp.util.TokenUtil;

public class PersonalActivity extends BarWebViewActivity {
    private RelativeLayout relativeLayoutAuthor, relativeLayoutPhone, relativeLayoutSex, relativeLayoutIntroduction;
    private RelativeLayout relativeLayoutUnit, relativeLayoutPositional, relativeLayoutOffice;
    private TextView txtPhone, txtSex, txtIntroduction, txtUnit, txtPositional, txtOffice;
    private LinearLayout personalLinear;
    private ImageView imgAuthor;
    private ResponseData responseData = new ResponseData();
    public static final int REQUSET = 1;      //传值的状态码
    private DialogUtil dialog = new DialogUtil();
    private GetMenuValue menuValue = new GetMenuValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_personal);

        initActionBar("返回", "我的", "设置");
        linearRightImgOne.setVisibility(View.GONE);
        linearRightImgTwo.setVisibility(View.GONE);
        rightTitle.setOnClickListener(this);

        initView();
        responseMenuData();
        responseAccountData();
    }

    private void initView() {

        relativeLayoutAuthor = (RelativeLayout) findViewById(R.id.personal_relative_author);
        relativeLayoutPhone = (RelativeLayout) findViewById(R.id.personal_relative_phone);
        relativeLayoutSex = (RelativeLayout) findViewById(R.id.personal_relative_sex);
        relativeLayoutIntroduction = (RelativeLayout) findViewById(R.id.personal_relative_introduction);
        relativeLayoutUnit = (RelativeLayout) findViewById(R.id.personal_relative_unit);
        relativeLayoutPositional = (RelativeLayout) findViewById(R.id.personal_relative_positional);
        relativeLayoutOffice = (RelativeLayout) findViewById(R.id.personal_relative_office);
        personalLinear = (LinearLayout) findViewById(R.id.personal_linear_body);

        txtPhone = (TextView) findViewById(R.id.personal_txt_phone2);
        txtSex = (TextView) findViewById(R.id.personal_txt_sex2);
        txtUnit = (TextView) findViewById(R.id.personal_txt_unit2);
        txtIntroduction = (TextView) findViewById(R.id.personal_txt_introduction2);
        txtOffice = (TextView) findViewById(R.id.personal_txt_office2);
        txtPositional = (TextView) findViewById(R.id.personal_text_positional2);

        imgAuthor = (ImageView) findViewById(R.id.personal_img_author);

        relativeLayoutAuthor.setOnClickListener(this);
        relativeLayoutPhone.setOnClickListener(this);
        relativeLayoutSex.setOnClickListener(this);
        relativeLayoutUnit.setOnClickListener(this);
        relativeLayoutOffice.setOnClickListener(this);
        relativeLayoutPositional.setOnClickListener(this);
        relativeLayoutIntroduction.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        Intent trueNameIntent = new Intent(PersonalActivity.this, PublicActivity.class);
        switch (v.getId()) {
//            case R.id.personal_relative_author:
//                break;
//            case R.id.personal_relative_phone:
//                Intent intentPhone = new Intent(PersonalActivity.this, ChangePhoneActivity.class);
//                startActivity(intentPhone);
//                break;
//            case R.id.personal_relative_sex:
//                String[] strings = new String[]{"男", "女"};
//                int index = index(strings, txtSex.getText().toString());
//                dialog.customListViewDialog(this, DialogData(strings, index), txtSex);
//                break;
//            case R.id.personal_relative_unit:
//                trueNameIntent.putExtra("data", "trueUnit");
//                trueNameIntent.putExtra("toUpdate", txtUnit.getText().toString());
//                startActivityForResult(trueNameIntent, REQUSET);
//                break;
//            case R.id.personal_relative_positional:
//                String[] stringsPositional = new String[]{"初级", "中级", "高级"};
//                int indexPositional = index(stringsPositional, txtPositional.getText().toString());
//                dialog.customListViewDialog(this, DialogData(stringsPositional, indexPositional), txtPositional);
//                break;
//            case R.id.personal_relative_office:
//                trueNameIntent.putExtra("data", "trueOffice");
//                trueNameIntent.putExtra("toUpdate", txtUnit.getText().toString());
//                startActivityForResult(trueNameIntent, REQUSET);
//                break;
//            case R.id.personal_relative_introduction:
//                trueNameIntent.putExtra("data", "trueIntroduction");
//                trueNameIntent.putExtra("toUpdate", txtIntroduction.getText().toString());
//                startActivityForResult(trueNameIntent, REQUSET);
//                break;
//
//            case R.id.bar_txt_right_one:
//                Intent intent = new Intent(PersonalActivity.this,SettingActivity.class);
//                startActivity(intent);
//                break;
            case R.id.bar_txt_right_one:
                Intent intent = new Intent(PersonalActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        String name = null, back = null;
        //requestCode表示请求的标示   resultCode表示有数据
        if (data != null) {
            name = data.getStringExtra("name");
            back = data.getStringExtra("back");
            if (requestCode == PersonalActivity.REQUSET && resultCode == RESULT_OK) {
                switch (back) {
                    case "trueIntroduction":
                        txtIntroduction.setText(name);
                        break;
                    case "trueUnit":
                        txtUnit.setText(name);
                        break;
                    case "trueOffice":
                        txtOffice.setText(name);
                        break;
                }

            }
        }

    }

    //枚举常量请求接口
    public void responseMenuData() {
        responseData.responseListEnumData(new CustomResponse() {
            @Override
            public void onSuccess(String jsonString) {
                //保存在数据库 方便下次更新
                new TokenUtil().setMenu(jsonString);
                /**
                 * 根据group判断，如果是enumAccountGroup.centerExpert，显示单位、职称、职务、简介，
                 * 否则隐藏这几个信息
                 */
                txtSex.setText(menuValue.getTextFromCode("enumSex", new TokenUtil().getAccoutSex()));

                if (menuValue.getKeyFromCode("enumAccountGroup", new TokenUtil().getAccoutEnum()).equals("centerExpert")) {
                    personalLinear.setVisibility(View.VISIBLE);
                    relativeLayoutIntroduction.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public boolean onFailure(HttpError httpError) {
                return false;
            }
        });
    }


    //用户信息请求
    public void responseAccountData() {

        responseData.responseAccountData(new CustomResponseObject() {
            @Override
            public void onSuccess(Object object) {
                Login login = (Login) object;
                //更新用户头像信息 分组信息
                new TokenUtil().setAccountheadImageUr(login.getHead_image_url());
                new TokenUtil().setAccountEnum(login.getGroup());
                new TokenUtil().setAccountSex(login.getSex());
                showAccountMessage(login);

            }

            @Override
            public void onFailure(HttpError httpError) {
                showToast(httpError.getMessage());

            }
        });

    }

    //个人信息数据的显示
    public void showAccountMessage(Login login) {

        //已知enumSex在Json对应的字段为 性别
        txtIntroduction.setText(login.getMemo());    //个人简介
        txtPositional.setText(login.getPosition());  //职务
        txtPhone.setText(login.getAccount_phone());  //手机号码
        txtUnit.setText(login.getCompany());         //单位
        txtOffice.setText(login.getPosition());

        ImageLoaderUtil.display(Router.baseHeadImgUrl+login.getHead_image_url(),imgAuthor);


    }
    /**
     * 显示当前选中的是第几个
     */
    public int index(String[] str, String title) {
        int index = 0;
        if (str.length > 0 && title != null) {
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals(title)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * 设置单选显示的数据
     */
    public List<CustomDialog> DialogData(String[] strings, int index) {
        List<CustomDialog> customDialogList = new ArrayList<>();
        CustomDialog customDialog;
        for (int i = 0; i < strings.length; i++) {
            customDialog = new CustomDialog();
            customDialog.setName(strings[i]);
            if (i == index) {
                customDialog.setCount(1);
            } else {
                customDialog.setCount(0);
            }
            customDialogList.add(customDialog);
        }
        //最后一个空的  放取消按钮的
        customDialog = new CustomDialog();
        customDialogList.add(customDialog);
        return customDialogList;
    }

}
