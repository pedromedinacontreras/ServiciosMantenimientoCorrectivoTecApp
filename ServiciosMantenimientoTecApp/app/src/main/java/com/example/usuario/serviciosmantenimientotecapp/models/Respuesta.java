package com.example.usuario.serviciosmantenimientotecapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by usuario on 10/12/16.
 */

//public class Respuesta extends RealmObject implements Serializable {
public class Respuesta implements Serializable {
    @SerializedName("respuesta")
    private boolean respuesta;
    @SerializedName("usuariovalida")
    private String usuariovalida;
    @SerializedName("deptosolicita")
    private String deptosolicita;

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public String getUsuariovalida() {
        return usuariovalida;
    }

    public void setUsuariovalida(String usuariovalida) {
        this.usuariovalida = usuariovalida;
    }

    public String getDeptosolicita() {
        return deptosolicita;
    }

    public void setDeptosolicita(String deptosolicita) {
        this.deptosolicita = deptosolicita;
    }

//    public static Respuesta getRespuesta(Context context){
//        Respuesta respuesta = null;
//        Realm.init(context);
//        Realm realm = Realm.getDefaultInstance();
//        respuesta = realm.where(Respuesta.class).findFirst();
//        return respuesta;
//    }
}
