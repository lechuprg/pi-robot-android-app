package com.robot.pi.cache;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Lechu on 26.11.2016.
 */
public class CacheProperty {

    public static final String PI_ROBOT_IP_ADDRESS = "pi.robot.ip.address";
    public static final String HTTP = "http://";
    public static final String IP_ADDRESS = "000.000.0.1";

    SharedPreferences robotPiAddress;
    SharedPreferences.Editor prefsEditor;
    public CacheProperty(Activity activity) {
        robotPiAddress = activity.getSharedPreferences("robotPiAddress", 0);

        prefsEditor = robotPiAddress.edit();
    }

    public String getProperty(String name) {
        return robotPiAddress.getString(name, IP_ADDRESS);

    }

    public String getIpAddress() {
        return HTTP + robotPiAddress.getString(PI_ROBOT_IP_ADDRESS, IP_ADDRESS);
    }

    public String getJustIpAddress() {
        return robotPiAddress.getString(PI_ROBOT_IP_ADDRESS, IP_ADDRESS);
    }

    public void setIpAddress(String value) {
        prefsEditor.putString(PI_ROBOT_IP_ADDRESS, value).commit();
        prefsEditor.apply();
    }
}
