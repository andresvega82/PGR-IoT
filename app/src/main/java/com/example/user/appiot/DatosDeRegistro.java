package com.example.user.appiot;

/**
 * Created by User on 30/01/2017.
 */
public class DatosDeRegistro {

    private String nombre;
    private String apellido;
    private String fenemino;
    private String masculino;
    private String email;
    private String contraseña;
    private int documento;


    public DatosDeRegistro(String nombre, String apellido,String fenemino , String email,String contraseña ){

        this.nombre = nombre;
        this.apellido = apellido;
        this.fenemino = fenemino;
        this.email = email;
        this.contraseña= contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFenemino() {
        return fenemino;
    }

    public void setFenemino(String fenemino) {
        this.fenemino = fenemino;
    }

    public String getMasculino() {
        return masculino;
    }

    public void setMasculino(String masculino) {
        this.masculino = masculino;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
