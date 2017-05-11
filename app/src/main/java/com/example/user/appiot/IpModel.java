package com.example.user.appiot;

import android.annotation.TargetApi;
import android.os.Build;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import android.annotation.TargetApi;
import android.os.Build;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 * Created by User on 07/02/2017.
 */
public class IpModel {

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public InetAddress getIp() throws Exception{
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni;
            while (nis.hasMoreElements()) {
                ni = nis.nextElement();
                if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                    for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                        //filter for ipv4/ipv6
                        if (ia.getAddress().getAddress().length == 4) {
                            //4 for ipv4, 16 for ipv6
                            return ia.getAddress();
                        }
                    }
                }
            }
            return null;
        }
    }

