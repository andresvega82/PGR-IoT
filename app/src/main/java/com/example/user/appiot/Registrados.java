package com.example.user.appiot;

import java.util.ArrayList;


/**
 * Created by User on 30/01/2017.
 */
public class Registrados {
        private static Registrados ourInstance = new Registrados();
        private ArrayList<DatosDeRegistro> listaRegistrados;

        public static Registrados getInstance() {
            return ourInstance;
        }

        private Registrados() {
            listaRegistrados = new ArrayList();
        }

        public ArrayList<DatosDeRegistro> getListaRegistrados() {
            return listaRegistrados;
        }

        public void adicionarRegistro(DatosDeRegistro e){
            listaRegistrados.add(e);
        }

        public String toString(){
            String ang = "";
            for (int i=0; i< listaRegistrados.size(); i++){
                ang += listaRegistrados.get(i).getEmail() + " " + listaRegistrados.get(i).getContraseña();
                ang+="/n";
            }
            return ang;
        }


}
