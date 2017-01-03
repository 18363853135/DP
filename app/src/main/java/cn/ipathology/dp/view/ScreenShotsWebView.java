package cn.ipathology.dp.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;

import cn.ipathology.dp.zxing.camera.CameraManager;

/**
 * Created by wdb on 2016/12/5.
 */

public class ScreenShotsWebView extends BridgeWebView {
    private int blue = 0xFF25a0ff;   //0091FF
    private CameraManager cameraManager;
    TextView threeTop ,threeBottom;
    private Paint paint;

    public ScreenShotsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenShotsWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScreenShotsWebView(Context context) {
        super(context);
    }



}
