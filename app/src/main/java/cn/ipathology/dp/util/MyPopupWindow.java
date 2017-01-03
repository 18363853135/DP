package cn.ipathology.dp.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.ipathology.dp.R;
import cn.ipathology.dp.appliaction.MyApplication;
import cn.ipathology.dp.zxing.android.CaptureActivity;

/**
 * Created by wdb on 2016/11/8.
 * 自定义popupWindow 显示快递信息
 */

public class MyPopupWindow extends PopupWindow {
    public View mView;
    public TextView cancel, sure;
    public ImageView scanningImg;
    public EditText scanningCode;
    public MyPopupWindow myPopupWindow;
    public  int REQUEST_CODE_SCAN = 0x0000;



    public void setMyPopupWindow(final Activity context, View view, final String scanning) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_popupwindow_message, null);

        initView();
        if (myPopupWindow == null) {
            myPopupWindow = new MyPopupWindow();
        }

        ColorDrawable dw = new ColorDrawable(0x00000000);
        myPopupWindow.setBackgroundDrawable(dw);
        int widthDip = DensityUtil.getDisplayWidth(context) - DensityUtil.dip2px(context, 80);
        myPopupWindow.setWidth(widthDip);
        myPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        myPopupWindow.setContentView(mView);
        myPopupWindow.setFocusable(true);
        myPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        myPopupWindow.showAtLocation(view, Gravity.CENTER, 0, -100);


        //扫描完的结果  传值过来
        if (myPopupWindow.isShowing()) {
            if (!TextUtils.isEmpty(scanning)) {
                scanningCode.setText(scanning);
            }
        }

        //背景变透明
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.4f;
        context.getWindow().setAttributes(lp);

        myPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                //背景恢复
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPopupWindow.isShowing()) {
                    myPopupWindow.dismiss();
                }
            }
        });

        //确定操作
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(scanningCode.getText().toString())){
                    //TODO
                }else{
                    Toast.makeText(MyApplication.getInstance(),"快递单号不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });


       //扫描二维码
        scanningImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getInstance(), CaptureActivity.class);
                context.startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

    }

    //初始化控件
    private void initView() {
        TextView address = (TextView) mView.findViewById(R.id.message_address_detail);
        TextView addressee = (TextView) mView.findViewById(R.id.message_addressee_details);
        TextView phone = (TextView) mView.findViewById(R.id.message_phone_details);

        //这种方法设置的下划线之后  字体显虚
//        address.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        addressee.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

        //用html的方法设置下划线
        address.setText(Html.fromHtml("<u>"+"浙江省杭州市滨江区滨安路688号5栋401"+"</u>"));
        addressee.setText(Html.fromHtml("<u>"+"城北"+"</u>"));
        phone.setText(Html.fromHtml("<u>"+"18659758632"+"</u>"));

        scanningCode = (EditText) mView.findViewById(R.id.message_scanning_detail);
        scanningImg = (ImageView) mView.findViewById(R.id.message_scanning_img);
        cancel = (TextView) mView.findViewById(R.id.message_cancel);
        sure = (TextView) mView.findViewById(R.id.message_sure);

    }


}
