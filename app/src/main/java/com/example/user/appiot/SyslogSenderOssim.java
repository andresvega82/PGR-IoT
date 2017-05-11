package com.example.user.appiot;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ASUS on 5/03/2017.
 */

public class SyslogSenderOssim {

    public void sendMessage(String evento){
        System.out.println("LLEGA AL SYSLOG: "+evento);
        UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
        messageSender.setDefaultMessageHostname(""); // some syslog cloud services may use this field to transmit a secret key
        messageSender.setDefaultAppName("IOT");
        messageSender.setDefaultFacility(Facility.USER);
        messageSender.setDefaultSeverity(Severity.ALERT);
        messageSender.setSyslogServerHostname("10.2.78.8");
        messageSender.setSyslogServerPort(514);
        messageSender.setMessageFormat(MessageFormat.RFC_3164); // optional, default is RFC 3164
        evento =  evento.substring(1,evento.length()-2);
        String[] params = evento.split(",");
        try {
            // send a Syslog message
            String email = params[1].split(":")[1].substring(1,params[1].split(":")[1].length()-2);
            String nameCatergory = params[3].split(":")[1].substring(1, params[3].split(":")[1].length()-2);
            String category = params[0].split(":")[1].substring(1, params[0].split(":")[1].length()-2);
            String ip = params[2].split(":")[1].substring(1, params[2].split(":")[1].length()-2);
            messageSender.sendMessage("|"+email+"|"+nameCatergory+"|"+category+"|"+ip);
        } catch (IOException ex) {
            Logger.getLogger(this.toString()).log(Level.SEVERE, null, ex);
        }

    }


    public void sendMyMessage(String email,String nameCategory, String category, String ip, String mac,String longitud, String latitud){
        UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
        messageSender.setDefaultMessageHostname(""); // some syslog cloud services may use this field to transmit a secret key
        messageSender.setDefaultAppName("IOT");
        messageSender.setDefaultFacility(Facility.USER);
        messageSender.setDefaultSeverity(Severity.ALERT);
        messageSender.setSyslogServerHostname("10.2.78.8");
        messageSender.setSyslogServerPort(514);
        messageSender.setMessageFormat(MessageFormat.RFC_3164);
        System.out.println("Send");
        try {
            System.out.println("|"+email+"|"+nameCategory+"|"+category+"|"+ip+"|"+mac+"|"+longitud+"|"+latitud+"|"+new Date());
            messageSender.sendMessage("|"+email+"|"+nameCategory+"|"+category+"|"+ip+"|"+mac+"|"+longitud+"|"+latitud+"|"+new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
