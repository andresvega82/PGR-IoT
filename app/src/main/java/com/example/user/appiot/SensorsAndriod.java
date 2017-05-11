package com.example.user.appiot;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.Display;
import android.view.WindowManager;


/**
 * Created by ASUS on 20/03/2017.
 */

public class SensorsAndriod implements SensorEventListener{

    @Override
    public void onSensorChanged(SensorEvent event) {
        String txt = "\n\nSensor: ";

        // Cada sensor puede lanzar un thread que pase por aqui
        // Para asegurarnos ante los accesos simult‡neos sincronizamos esto

        synchronized (this) {

            switch (event.sensor.getType()) {

                case Sensor.TYPE_ACCELEROMETER:

                    txt += "acelerometro\n";
                    txt += "\n x: " + event.values[0] + " m/s";
                    txt += "\n y: " + event.values[1] + " m/s";
                    txt += "\n z: " + event.values[2] + " m/s";
                    //System.out.println(txt);


                    break;

                case Sensor.TYPE_ROTATION_VECTOR:
                    txt += "rotation vector\n";
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];

                    System.out.println(txt);

                    break;

                case Sensor.TYPE_GRAVITY:
                    txt += "Gravedad\n";
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];

                    //System.out.println(txt);

                    break;
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
