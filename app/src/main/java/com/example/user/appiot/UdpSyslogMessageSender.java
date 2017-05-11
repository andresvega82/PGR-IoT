package com.example.user.appiot;

import com.cloudbees.syslog.SyslogMessage;
import com.cloudbees.syslog.util.CachingReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Created by ASUS on 5/03/2017.
 */

@ThreadSafe
public class UdpSyslogMessageSender extends AbstractSyslogMessageSender {
    /**
     * {@link java.net.InetAddress InetAddress} of the remote Syslog Server.
     *
     * The {@code InetAddress} is refreshed regularly to handle DNS changes (default {@link #DEFAULT_INET_ADDRESS_TTL_IN_MILLIS})
     *
     * Default value: {@link #DEFAULT_SYSLOG_HOST}
     */
    protected CachingReference<InetAddress> syslogServerHostnameReference;
    /**
     * Listen port of the remote Syslog server.
     *
     * Default: {@link #DEFAULT_SYSLOG_PORT}
     */
    protected int syslogServerPort = DEFAULT_SYSLOG_PORT;

    private DatagramSocket datagramSocket;

    public UdpSyslogMessageSender() {
        try {
            setSyslogServerHostname(DEFAULT_SYSLOG_HOST);
            datagramSocket = new DatagramSocket();
        } catch (IOException e) {
            throw new IllegalStateException("Exception initializing datagramSocket", e);
        }
    }

    /**
     * Send the given {@link com.cloudbees.syslog.SyslogMessage} over UDP.
     *
     * @param message the message to send
     * @throws IOException
     */
    @Override
    public void sendMessage(SyslogMessage message) throws IOException {
        sendCounter.incrementAndGet();
        long nanosBefore = System.nanoTime();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(baos, UTF_8);
            message.toSyslogMessage(messageFormat, out);
            out.flush();
            Date d = new Date();
            message.setTimestamp(d);
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("Send syslog message " + new String(baos.toByteArray(), UTF_8));
            }
            byte[] bytes = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, syslogServerHostnameReference.get(), syslogServerPort);
            datagramSocket.send(packet);
        } catch (IOException e) {
            sendErrorCounter.incrementAndGet();
            throw e;
        } catch (RuntimeException e) {
            sendErrorCounter.incrementAndGet();
            throw e;
        } finally {
            sendDurationInNanosCounter.addAndGet(System.nanoTime() - nanosBefore);
        }
    }


    public void setSyslogServerHostname(final String syslogServerHostname) {
        this.syslogServerHostnameReference = new CachingReference<InetAddress>(DEFAULT_INET_ADDRESS_TTL_IN_NANOS) {
            @Nullable
            @Override
            protected InetAddress newObject() {
                try {
                    return InetAddress.getByName(syslogServerHostname);
                } catch (UnknownHostException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    public void setSyslogServerPort(int syslogServerPort) {
        this.syslogServerPort = syslogServerPort;
    }

    @Nullable
    public String getSyslogServerHostname() {
        InetAddress inetAddress = syslogServerHostnameReference.get();
        return inetAddress == null ? null : inetAddress.getHostName();
    }

    public int getSyslogServerPort() {
        return syslogServerPort;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "syslogServerHostname='" + this.getSyslogServerHostname() + '\'' +
                ", syslogServerPort='" + this.getSyslogServerPort() + '\'' +
                ", defaultAppName='" + defaultAppName + '\'' +
                ", defaultFacility=" + defaultFacility +
                ", defaultMessageHostname='" + defaultMessageHostname + '\'' +
                ", defaultSeverity=" + defaultSeverity +
                ", messageFormat=" + messageFormat +
                ", sendCounter=" + sendCounter +
                ", sendDurationInNanosCounter=" + sendDurationInNanosCounter +
                ", sendErrorCounter=" + sendErrorCounter +
                '}';
    }
}
