package com.example.user.appiot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;

import java.util.Arrays;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by ASUS on 3/05/2017.
 */

public class DoPost  extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... voids) {
            String[] params = voids[0].split(",");
            String mac = Utils.getMACAddress("wlan0");
            String ipAddress = Utils.getIPAddress(true);
            SyslogSenderOssim oss = new SyslogSenderOssim();
            try {
                oss.sendMyMessage(params[0],params[2],params[1],ipAddress,mac,"null","null");



                //SyslogSenderOssim oss = new SyslogSenderOssim();
                //oss.sendMyMessage(params[0],params[2],params[1],ipAddress,mac,longitud,latitud);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



