package com.dam.ex1aev1prec.vistamodelo;

import com.dam.ex1aev1prec.modelo.Equipo;

public class LogicaEquipo {

    /* Métodos ************************************************************************************/

    public static Equipo getEquipo(String id) {
        for (Equipo equipo : Datos.getInstance().getmListaEquipos()) {
            if (equipo.getId().equals(id)) {
                return equipo;
            }
        }
        return null;
    }

    public static boolean existeEquipo(Equipo e) {
        for (Equipo equipo : Datos.getInstance().getmListaEquipos()) {
            if (equipo.getId().equals(e.getId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean altaEquipo(Equipo e) {
        if (existeEquipo(e)) {
            return false;
        }
        return Datos.getInstance().getmListaEquipos().add(e);
    }

    public static boolean bajaEquipo(Equipo e) {
        if (!existeEquipo(e)) {
            return false;
        }
        for (Equipo equipo : Datos.getInstance().getmListaEquipos()) {
            if (equipo.getId().equals(e.getId())) {
                return Datos.getInstance().getmListaEquipos().remove(equipo);
            }
        }
        return false;
    }

}
