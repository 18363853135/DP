package cn.ipathology.dp.activity.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import java.util.List;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.adapter.AcdSlideBarAdapter;
import cn.ipathology.dp.entityClass.Slide;
import cn.ipathology.dp.network.CustomResponseData;
import cn.ipathology.dp.network.HttpError;
import cn.ipathology.dp.network.ResponseData;
import cn.ipathology.dp.network.Router;
import cn.ipathology.dp.util.DensityUtil;
import cn.ipathology.dp.util.StringFormat;
import cn.ipathology.dp.util.TokenUtil;
import cn.ipathology.dp.util.WebViewUtil;
import cn.ipathology.dp.view.AutoSwipeRefreshLayout;

import static cn.ipathology.dp.R.id.bar_linear_right_one;


public class AcdSeeActivity extends BarWebViewActivity  {
    private BridgeWebView bridgeWebView;
    TextView oneTop, oneBottom, twoTop, twoBottom;
    TextView threeTop, threeBottom, fourTop, fourBottom;
    TextView frameTop, frameBottom;
    public ListView listView;
    public DrawerLayout drawerLayout ;
    private AutoSwipeRefreshLayout refreshLayout;
    //手机屏幕的宽高  高度为除去状态栏和actionBar的高度
    public int screenWidth, screenHeight;
    private int actionBarHeight,frameWidth;//框的大小
    public View view;      //根视图
    public int screenColor = 0xFF000000;      //遮罩的颜色值
    public ResponseData responseData = new ResponseData();
    int slideId , caseId;    //病理的ID 用于查询切片列表 切片唯一的ID 用于定位当前选中项
    public String showDrawerLayout ,slideToken ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_acdsee);


        screenWidth = DensityUtil.getDisplayWidth(this);
        screenHeight = DensityUtil.getDisplayHeight(this);
        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, this.getResources().getDisplayMetrics());
        }
        frameWidth = screenWidth * 3 / 4;

        initView();
        setScreenWebViewUrl();


    }


    public void initView() {
        oneTop = (TextView) findViewById(R.id.one_top);
        oneBottom = (TextView) findViewById(R.id.one_bottom);
        twoTop = (TextView) findViewById(R.id.two_top);
        twoBottom = (TextView) findViewById(R.id.two_bottom);
        threeTop = (TextView) findViewById(R.id.three_top);
        threeBottom = (TextView) findViewById(R.id.three_bottom);
        fourTop = (TextView) findViewById(R.id.four_top);
        fourBottom = (TextView) findViewById(R.id.four_bottom);
        frameTop = (TextView) findViewById(R.id.acd_frame_top);
        frameBottom = (TextView) findViewById(R.id.acd_frame_bottom);
        listView = (ListView) findViewById(R.id.acd_slide_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.web_drawer_layout);
        drawerLayout.setScrimColor(0x00ffffff);



        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        listView.setSelection(0);
        listView.getBackground().setAlpha(230);


        frameTop.setOnClickListener(null);
        frameBottom.setOnClickListener(null);

        bridgeWebView = (BridgeWebView) findViewById(R.id.acd_see_webView);
        refreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.acd_see_refresh);

        setLayout(oneTop, 0, DensityUtil.dip2px(this, 75));
        setLayout(oneBottom, 0, DensityUtil.dip2px(this, 75));

        setLayout(twoTop, screenWidth - DensityUtil.dip2px(this, 12), DensityUtil.dip2px(this, 75));
        setLayout(twoBottom, screenWidth - DensityUtil.dip2px(this, 3), DensityUtil.dip2px(this, 75));

        setLayout(threeTop, 0, DensityUtil.dip2px(this, 75) + frameWidth);
        setLayout(threeBottom, 0, DensityUtil.dip2px(this, 75) + frameWidth - DensityUtil.dip2px(this, 9));

        setLayout(fourTop, screenWidth - DensityUtil.dip2px(this, 12), DensityUtil.dip2px(this, 75) + frameWidth);
        setLayout(fourBottom, screenWidth - DensityUtil.dip2px(this, 3), DensityUtil.dip2px(this, 75) + frameWidth - DensityUtil.dip2px(this, 9));

        //top
        setLayout(frameTop, 0, 0);
        setLayout(frameBottom, 0, DensityUtil.dip2px(this, 75 + 3) + frameWidth);


    }

    //设置bridgeWebView参数
    public void setScreenWebViewUrl() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        slideId = intent.getIntExtra("slideId",-1);
        caseId = intent.getIntExtra("caseId",-1);
        showDrawerLayout = intent.getStringExtra("view");
        slideToken = intent.getStringExtra("token");
        requestCaseSlide(String.valueOf(caseId));
        setActionBar(title);
        new WebViewUtil().setWebView( url, refreshLayout, bridgeWebView);
        bridgeWebView.setWebViewClient(new BridgeWebViewClient(bridgeWebView) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                hintHorn();
                //显示关闭 截图遮层
                bridgeWebView.registerHandler("appBridgeShowScreenShot", new BridgeHandler() {
                    @Override
                    public void handler(String data, CallBackFunction function) {
                        screenShot(data);

                    }
                });
            }
        });
       //禁止长按复制粘贴
        bridgeWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        setCloseRefersh(refreshLayout);
    }

    //显示请求完的 侧滑栏数据
    private void initDrawer(final List<Slide> list) {
        //数组定义在xml 文件中

        final AcdSlideBarAdapter acdSlideBarAdapter = new AcdSlideBarAdapter(this,list);
        listView.setAdapter(acdSlideBarAdapter);
        listView.setSelection(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                unFocus(list,position);
                acdSlideBarAdapter.notifyDataSetChanged();
                Slide slide = list.get(position);
                setActionBar(slide.getSlide_text());
                String url = Router.baseSlideImgUrl+list.get(position).getSlide_url()+"&case_id="+caseId+"&slide_id="+slide+"&view="+showDrawerLayout+"&token="+slideToken+"&f=app";
                bridgeWebView.loadUrl(url);

            }
        });

    } //点击时先把所有的dialog都显示为未选中状态
    public void unFocus(List<Slide> list, int position){
        for (int i =0;i<list.size();i++){
            if (i == position){
                list.get(i).setCount(1);
            }else{
                list.get(i).setCount(0);
            }
        }

    }

    //请求切片浏览的接口
    public void requestCaseSlide(final String caseIdStr){
        responseData.responseListCaseSlide(caseIdStr, new CustomResponseData() {
            @Override
            public void onSuccess(List list) {
                List<Slide> listSlide = list;
                for (int i =0;i<listSlide.size();i++){
                    if (listSlide.get(i).getSlide_id() == slideId){
                        listSlide.get(i).setCount(1);
                    }
                }
                initDrawer(listSlide);
            }

            @Override
            public void onFailure(HttpError httpError) {
                showToast(httpError.getMessage());

            }
        });
    }


    //设置当前的ActionBar样式
    public void setActionBar(String title){
        initActionBar("返回", title, "");
        barBackground.setBackgroundColor(black);
        linearRightImgOne.setVisibility(View.VISIBLE);
        rightImgOne.setImageResource(R.mipmap.ic_open_lst_normal);
        linearRightImgOne.setOnClickListener(this);
    }
    //遮罩效果判断
    public void screenShot(String data) {
        if (TextUtils.isEmpty(StringFormat.jsonObjectToString(data)))
            return;
        //1 遮罩效果  0
        if (StringFormat.jsonObjectToString(data).equals("1")) {
            showHorn();
            showScreenShot();
        } else {
            hintScreenShot();
        }

    }

    //显示遮罩
    public void showScreenShot() {

        frameTop.setVisibility(View.VISIBLE);
        frameTop.setBackgroundColor(screenColor);
        frameTop.getBackground().setAlpha(150);

        frameBottom.setVisibility(View.VISIBLE);
        frameBottom.setBackgroundColor(screenColor);
        frameBottom.getBackground().setAlpha(150);

        // top ,bottom下面屏幕的高度= 手机高度-上面遮罩的高度-截图框的高度- webView中截图工具栏的高度
        setRelativeLayoutWidthHight(frameTop, screenWidth, DensityUtil.dip2px(this, 75));
        int bottomHeight = screenHeight - DensityUtil.getStatusBarHeight() - actionBarHeight - DensityUtil.dip2px(this, 75 + 2) - frameWidth - DensityUtil.dip2px(this, 55);
        setRelativeLayoutWidthHight(frameBottom, screenWidth, bottomHeight);
    }

    //隐藏遮罩
    public void hintScreenShot() {
        frameTop.setVisibility(View.GONE);
        frameBottom.setVisibility(View.GONE);
        setRelativeLayoutWidthHight(frameBottom, 0, 0);
    }

    //截图的轨角隐藏
    public void showHorn(){
        if (new TokenUtil().getAccoutEnum() == 202){
            oneTop.setVisibility(View.VISIBLE);
            oneBottom.setVisibility(View.VISIBLE);
            twoTop.setVisibility(View.VISIBLE);
            twoBottom.setVisibility(View.VISIBLE);
            threeTop.setVisibility(View.VISIBLE);
            threeBottom.setVisibility(View.VISIBLE);
            fourTop.setVisibility(View.VISIBLE);
            fourBottom.setVisibility(View.VISIBLE);
        }

    }
    //截图的轨角显示
    public void hintHorn(){
        oneTop.setVisibility(View.GONE);
        oneBottom.setVisibility(View.GONE);
        twoTop.setVisibility(View.GONE);
        twoBottom.setVisibility(View.GONE);
        threeTop.setVisibility(View.GONE);
        threeBottom.setVisibility(View.GONE);
        fourTop.setVisibility(View.GONE);
        fourBottom.setVisibility(View.GONE);
    }


    /**
     * 设置控件所在的位置YY，并且不改变宽高，
     * XY为绝对位置

     */
    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }


    //代码设置空间的宽高
    public void setRelativeLayoutWidthHight(View view, int width, int height) {
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        linearParams.width = width;// 控件的宽强制设成30
        linearParams.height = height;// 控件的宽强制设成30
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }




    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case bar_linear_right_one:
                boolean flag = drawerLayout.isDrawerOpen(listView);
                if (flag){
                    drawerLayout.closeDrawer(listView);
                    return;
                }else{
                    drawerLayout.openDrawer(listView);
                }
                break;
            default:
                break;
        }
    }

}
