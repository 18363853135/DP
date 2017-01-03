package cn.ipathology.dp.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by wdb on 2015/12/30.
 *
 * 图片裁剪工具 压缩
 *
 */
public class ImageTailorUtil {

    //按比例裁剪图片
    public static String imageSmall(String uri, String size) {

        if (uri == null) {
            return "";
        }
        uri.trim();

        if (uri.contains("?")) {
            return uri + "&size=" + size + "&c=1";
        } else {
            return uri + "?size=" + size + "&c=1";
        }
    }


    public static String jPushImgUrl(String uri) {

        if (uri == null) {
            return "";
        }
        uri.trim();

        if (uri.contains("?")) {
            return uri + "&token=" + new TokenUtil().getToken() ;
        } else {
            return uri + "?token=" + new TokenUtil().getToken();
        }
    }


    //使用Bitmap加Matrix来缩放  如果图片的宽度小于degree不缩放  否则 按宽高比缩放
    public static Bitmap resizeImage(Bitmap bitmap, int degree) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth, newHeight;
        //宽>高  按宽裁剪
        if (width > height) {
            newWidth = degree;
            newHeight = (degree * height) / width;

        } else {
            newHeight = degree;
            newWidth = (degree * width) / height;
        }
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        if (width >= degree || height >= degree) {
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        } else {
            return bitmap;
        }
    }

}
