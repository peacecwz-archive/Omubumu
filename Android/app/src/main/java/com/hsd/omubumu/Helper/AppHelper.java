package com.hsd.omubumu.Helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

/**
 * Created by APALYazilim on 25.7.2015.
 */
public class AppHelper extends Activity {
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String GetDeviceInfo(){
        String deviceInfo = "Device={0};Hardware={1};ID={2};Model={3};Product={4};MANUFACTURER={5};User={6};";
        return String.format(deviceInfo,Build.DEVICE,Build.HARDWARE,Build.ID,Build.MODEL,Build.PRODUCT,Build.MANUFACTURER,Build.USER);
    }

}
