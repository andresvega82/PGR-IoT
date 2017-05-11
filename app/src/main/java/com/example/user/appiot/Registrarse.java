package com.example.user.appiot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.net.InetAddress.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Registrarse extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private RadioButton fenemino;
    private RadioButton masculino;
    private EditText correo;
    private EditText contraseña;
    private EditText documento;
    private Button registrarme;

    private String name;
    private String lastName;
    private String fen;
    private String mas;
    private String email;
    private String password;
    private ImageView home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = (EditText) findViewById(R.id.editText);
        apellido = (EditText) findViewById(R.id.editText2);
        fenemino = (RadioButton) findViewById(R.id.radioButton2);
        masculino = (RadioButton) findViewById(R.id.radioButton);
        correo = (EditText) findViewById(R.id.editText3);
        contraseña = (EditText) findViewById(R.id.editText4);
        registrarme = (Button) findViewById(R.id.button5);

        registrarme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = nombre.getText().toString();
                lastName = apellido.getText().toString();
                email = correo.getText().toString();
                password = contraseña.getText().toString();
                String fem = "femenino";

                new DoPost().execute(email + ",EA4,UnexpectedQuantityofCharactersinUsername");



                Registrados.getInstance().adicionarRegistro(new DatosDeRegistro(name, lastName, fem, email, password));
                System.out.println("Agregar");
                System.out.println(Registrados.getInstance());
                Intent i = new Intent(getApplicationContext(), loginAppIoT.class);
                startActivity(i);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private class DoPost extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... voids) {
            String[] params = voids[0].split(",");

            try {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                String latitud = "";
                String longitud = "";

                SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                SensorsAndriod sa = new SensorsAndriod();

                mSensorManager.registerListener(sa,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);

                mSensorManager.registerListener(sa,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                        SensorManager.SENSOR_DELAY_NORMAL);

                mSensorManager.registerListener(sa,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                        SensorManager.SENSOR_DELAY_NORMAL);

                if (ActivityCompat.checkSelfPermission(Registrarse.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registrarse.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    latitud = Double.toString(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
                    longitud = Double.toString(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
                }

                String mac = Utils.getMACAddress("wlan0");
                String ipAddress = Utils.getIPAddress(true);

                SyslogSenderOssim oss = new SyslogSenderOssim();
                if(Float.parseFloat(longitud)!=0){
                    oss.sendMyMessage(params[0],"GE1","GeoFencingException",ipAddress,mac,longitud,latitud);
                }
                oss.sendMyMessage(params[0],params[2],params[1],ipAddress,mac,longitud,latitud);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}



