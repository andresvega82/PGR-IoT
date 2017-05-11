package com.example.user.appiot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.appiot.DoPost;

import java.util.ArrayList;

public class loginAppIoT extends AppCompatActivity {

    private TextView registrate;
    private Button ingresar;
    private ImageView home;
    private TextView email;
    private TextView contraseña;
    private String correo;
    private String password;
    private TextView error;
    private ArrayList<DatosDeRegistro> listaRegistrados ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app_io_t);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registrate = (TextView)findViewById(R.id.textView14);
        ingresar = (Button)findViewById(R.id.button8);
        email = (TextView)findViewById(R.id.editText6);
        contraseña = (TextView)findViewById(R.id.editText7);
        listaRegistrados = Registrados.getInstance().getListaRegistrados();
        error = (TextView)findViewById(R.id.error);
        home = (ImageView)findViewById(R.id.imageView3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = email.getText().toString();
                password = contraseña.getText().toString();
                String e ;
                String c;
                if(correo.contains("<script>")||password.contains("<script>")){
                    DoPost dopost = new DoPost();
                    dopost.execute(correo + ",AE3,CommandInjectionException");
                }else {
                    for (int i = 0; i < listaRegistrados.size(); i++) {
                        System.out.println("Lista");
                        error.setText("");
                        e = listaRegistrados.get(i).getEmail();
                        c = listaRegistrados.get(i).getContraseña();
                        if (correo.equals(e) && password.equals(c)) {
                            Intent l = new Intent(getApplicationContext(), Articulos.class);
                            startActivity(l);


                        } else {
                            error.setText("El usuario no existe");
                            System.out.println("Error");
                            DoPost dopost = new DoPost();
                            dopost.execute(correo + ",EA1,AuthenticationException");
                        }
                    }
                }
                System.out.println("LISTA DE REGISTRADOS");
                System.out.println(Registrados.getInstance());
            }
        });

        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Registrarse.class);
                startActivity(i);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });
    }

}
