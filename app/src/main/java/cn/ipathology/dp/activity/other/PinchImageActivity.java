package cn.ipathology.dp.activity.other;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.ipathology.dp.R;
import cn.ipathology.dp.activity.base.BarWebViewActivity;
import cn.ipathology.dp.util.ImageLoaderUtil;
import cn.ipathology.dp.util.PinchImageView;
import cn.ipathology.dp.util.PinchImageViewPager;


public class PinchImageActivity extends BarWebViewActivity implements View.OnClickListener {
    private String[] mImages = null;
    private String number;
    private TextView title;
    private String[] titles = null;
    private LinearLayout back;
    private TextView actionBar;
    private PinchImageViewPager pager;
    private int index = 0;
    ReleaseBitmap m_rll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinch_image);

        hintActionBar();
        getFragmentBar();

        actionBar = (TextView) findViewById(R.id.news_pinchImage_linearLayout_title);
        title = (TextView) findViewById(R.id.news_pinchImage_title);
        back = (LinearLayout) findViewById(R.id.news_pinchImage_linearLayout_back);
        back.setOnClickListener(this);
        title.getBackground().setAlpha(100);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        if (data.equals("dp")) {
            index = intent.getIntExtra("index", 0);
            mImages = intent.getStringArrayExtra("urls");
            titles = null;
        }

        final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();
        pager = (PinchImageViewPager) findViewById(R.id.pager);
        pager.setAdapter(index, new PagerAdapter() {

            @Override
            public int getCount() {
                return mImages.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PinchImageView piv;
                if (viewCache.size() > 0) {
                    piv = viewCache.remove();
                    piv.reset();
                } else {
                    piv = new PinchImageView(PinchImageActivity.this);
                }
                if (position < mImages.length) {
                    ImageLoaderUtil.displayBigPhoto(mImages[position], piv);
                }
                container.addView(piv);
                return piv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                container.removeView(piv);
                viewCache.add(piv);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                number = String.valueOf(position + 1) + " of " + String.valueOf(mImages.length);
                actionBar.setText(number);

                if (null != titles) {
                    title.setText(titles[position]);
                }

                pager.setMainPinchImageView((PinchImageView) object);
            }
        });

        m_rll = new ReleaseBitmap();
        ImageLoader.getInstance().setDefaultLoadingListener(m_rll);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_pinchImage_linearLayout_back:
                bbsFinish();
                break;
            default:
                break;
        }
    }

    public void getFragmentBar() {
        TypedArray actionbarSizeTypedArray = getBaseContext().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int h = (int) actionbarSizeTypedArray.getDimension(0, 0);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h - 20);
        findViewById(R.id.news_pinchImage_linearLayout_bar).setLayoutParams(lp);
    }

    //处理浏览PPT几百张图片时的 内存溢出问题
    public class ReleaseBitmap implements ImageLoadingListener {
        private List<Bitmap> mBitmaps;

        public ReleaseBitmap() {
            mBitmaps = new ArrayList<Bitmap>();
        }

        public void cleanBitmapList() {
            if (mBitmaps.size() > 0) {
                Iterator<Bitmap> stuIter = mBitmaps.iterator();
                while (stuIter.hasNext()) {
                    Bitmap b = stuIter.next();
                    if (b != null && !b.isRecycled()) {
                        b.recycle();
                    }
                }
            }

        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

        }

        /**
         * Is called when image loading task was cancelled because View for image was reused in newer task
         *
         * @param imageUri Loading image URI
         * @param view     View for image. Can be <b>null</b>.
         */
        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            System.gc();
            setContentView(R.layout.nothing);
            bbsFinish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        pager = null;
        m_rll.cleanBitmapList();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
        System.gc();
        setContentView(R.layout.nothing);
        super.onDestroy();

    }
}