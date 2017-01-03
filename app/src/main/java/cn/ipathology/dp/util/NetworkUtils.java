package cn.ipathology.dp.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import cn.ipathology.dp.appliaction.MyApplication;


/**
 * Created by wdb on 16-2-25.
 * 所有的网络连接状态
 */
public class NetworkUtils {

    /**
     * 网络是否可用
     *
     * @param
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 网络连接提示
     *
     * @param context
     * @return
     */
    public static void networkStateTips(Context context) {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(MyApplication.getInstance(), "网络故障，请先检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gps是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * wifi是否打开
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是3G网络
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
//	public static boolean isSSID(Context context){
//		 WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
//	        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//	        String ssid=wifiInfo.getSSID();
//	        wifiInfo.getBSSID();
//	        if ("jzyy-1111".equals(ssid)){
//	        	return true;
//	        } else  if("office".equals(ssid)){
//	        	return true;
//			}
//	        return false;
//	}
    /**
     * 运行一个外部命令，返回状态.若超过指定的超时时间，抛出TimeoutException
     * @param
     * @param
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static int executeCommand(String address) throws IOException, InterruptedException, TimeoutException {
        Runtime run = Runtime.getRuntime();
        Process process = null;
        int result=2;
        try {
            String str = "ping -c 1 -i 0.2 -W 1 "+address;
            String[] cmdStrings = new String[] {"sh", "-c", str};
            process = run.exec(cmdStrings);
            result = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            process.destroy();
        }
        return result;
    }

}
