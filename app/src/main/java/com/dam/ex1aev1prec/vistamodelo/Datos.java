package com.dam.ex1aev1prec.vistamodelo;

import com.dam.ex1aev1prec.modelo.Equipo;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    private List<Equipo> mListaEquipos;
    private static Datos datos;

    public Datos( ) {
        this.mListaEquipos = new ArrayList<>();
    }

    public List<Equipo> getmListaEquipos() {
        return mListaEquipos;
    }

    public void setmListaEquipos(List<Equipo> mListaEquipos) {
        this.mListaEquipos = mListaEquipos;
    }

    /* Singleton **********************************************************************************/

    public static Datos getInstance(){
        if(datos ==null){
            datos = new Datos();
        }
        return datos;
    }



}
