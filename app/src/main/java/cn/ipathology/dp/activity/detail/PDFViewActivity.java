package cn.ipathology.dp.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;

import java.io.File;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.activity.base.BaseWebViewActivity;
import cn.ipathology.dp.entityClass.Options;
import cn.ipathology.dp.entityClass.Share;
import cn.ipathology.dp.util.AppManager;
import cn.ipathology.dp.util.DownLoaderTask;
import cn.ipathology.dp.util.FileUtil;
import cn.ipathology.dp.util.ImageLoaderUtil;
import cn.ipathology.dp.util.ShowShare;
import cn.ipathology.dp.util.StringFormat;

public class PDFViewActivity extends BarWebViewActivity {
    FileUtil fileUtil = new FileUtil();
    public LinearLayout layoutProgressBar;
    public RelativeLayout relativeLayout;
    private TextView sure, cancel;
    public PDFView pdfView;
    public String title, option;
    public String url, id, shares;
    public Options optiopns;
    public Share share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_pdfview);

        initView();
        hintView();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        id = intent.getStringExtra("id");
        shares = intent.getStringExtra("shares");


        if (!TextUtils.isEmpty(id) && id.equals("pdf_view")) {
            idEqualPDFView();
        }


        //设置actionBar为黑色主题
        initActionBar("返回", title, "");

        barBackground.setBackgroundColor(black);
        linearRightImgOne.setVisibility(View.VISIBLE);
        rightImgOne.setImageResource(R.mipmap.ic_open_lst_normal);
        linearRightImgOne.setOnClickListener(this);
        if (!TextUtils.isEmpty(id)) {
            pdfActionBarShare();
        }

        judgePDFUrl();
        //下载PDF 并且命名为dp.pdf 文件夹中只能存一张pdf
        DownLoaderTask task = new DownLoaderTask(url, ImageLoaderUtil.PDF().getPath(), this, "PDF");
        task.execute();

    }

    //二维码 或者 预览 显示不同的状态
    public void pdfActionBarShare(){
        //如果是预览 或者扫描二维码时 显示分享
        if (id.contains(".pdf")) {
            Gson gson = new Gson();
            share = gson.fromJson(shares, Share.class);
            rightImgOne.setImageResource(R.mipmap.icon_share);
        }
        if (id.equals("QRCode")) {
            rightImgOne.setImageResource(R.mipmap.icon_share);
            share = new Share();
            share.setShareTitle(title);
            share.setShareLink(url);
        }
    }

    /**
     * 处理PDF Url链接 并且下载前格式化文件夹
     * 转诊 签发时 url结尾中包含特殊字符  舍弃掉
     */

    public void judgePDFUrl() {

        fileUtil.deleteFile(ImageLoaderUtil.PDF());
        String[] str = url.split(".pdf");
        if (str.length > 0) {
            url = str[0] + ".pdf";
        }

    }

    //如果id 等于 pdf_view 显示按钮
    public void idEqualPDFView() {
        option = getIntent().getStringExtra("options");
        Gson gson = new Gson();
        optiopns = gson.fromJson(option, Options.class);
        sure.setText(optiopns.getRight());
        cancel.setText(optiopns.getLeft());
    }


    //初始化
    public void initView() {


        pdfView = (PDFView) findViewById(R.id.pdfView);
        cancel = (TextView) findViewById(R.id.pdf_view_cancel);
        sure = (TextView) findViewById(R.id.pdf_view_sure);
        relativeLayout = (RelativeLayout) findViewById(R.id.pdf_view_relative);
        layoutProgressBar = (LinearLayout) findViewById(R.id.pdf_view_linearLayout);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);

    }


    //下载完成 显示操作按钮
    public void showView() {

        pdfView.setVisibility(View.VISIBLE);
        layoutProgressBar.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(id) && id.equals("pdf_view")) {
            relativeLayout.setVisibility(View.VISIBLE);
        }


    }

    //刚打开 隐藏操作按钮  显示加载
    public void hintView() {
        relativeLayout.setVisibility(View.GONE);
        pdfView.setVisibility(View.GONE);
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        BaseWebViewActivity activity = (BaseWebViewActivity) AppManager.getAppManager().reciprocalSecondActivity();
        switch (v.getId()) {
            case R.id.pdf_view_sure:
                bbsFinish();
                activity.callBackFunction.onCallBack(StringFormat.booleanToJsonObject("data", true));
                break;
            case R.id.pdf_view_cancel:
                bbsFinish();
                activity.callBackFunction.onCallBack(StringFormat.booleanToJsonObject("data", false));
                break;
            case R.id.bar_linear_right_one:
                sharePdf();
                break;
            default:
                break;
        }
    }

    //分享
    public void sharePdf() {
        //弹出分享的UI
        if (null != share) {
            ShowShare.setShareTitle(share.getShareTitle(), share.getShareDesc(), share.getShareLink(), share.getShareImgUrl());
            ShowShare.showShare();
        }
    }

    //显示pdf图片
    public void showPDF() {
        File file = new File(ImageLoaderUtil.PDF().getPath() + "/" + "dp.pdf");
        pdfView.fromFile(file)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .load();
        showView();
    }

}
