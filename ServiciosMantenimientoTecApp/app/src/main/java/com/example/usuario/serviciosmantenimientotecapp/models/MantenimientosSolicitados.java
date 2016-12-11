package com.example.usuario.serviciosmantenimientotecapp.models;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by usuario on 11/12/16.
 */

public class MantenimientosSolicitados {
    private ArrayList<Servicio> recursosMateriales;
    private ArrayList<Servicio> centroComputo;
    private ArrayList<Servicio> mantenimiento;

    public ArrayList<Servicio> getRecursosMateriales() {
        return recursosMateriales;
    }

    public void setRecursosMateriales(ArrayList<Servicio> recursosMateriales) {
            this.recursosMateriales = recursosMateriales;
    }

    public ArrayList<Servicio> getCentroComputo() {
        return centroComputo;
    }

    public void setCentroComputo(ArrayList<Servicio> centroComputo) {
        this.centroComputo = centroComputo;
    }

    public ArrayList<Servicio> getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(ArrayList<Servicio> mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

}
