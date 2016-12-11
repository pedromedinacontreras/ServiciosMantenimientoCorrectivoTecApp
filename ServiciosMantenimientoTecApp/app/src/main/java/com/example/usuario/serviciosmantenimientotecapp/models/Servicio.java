package com.example.usuario.serviciosmantenimientotecapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by usuario on 10/12/16.
 */

public class Servicio implements Serializable, Parcelable{

    @SerializedName("consecutivo")
    private String consecutivo;
    @SerializedName("fecha_solicitud")
    private String fecha_solicitud;
    @SerializedName("quien_reviso")
    private String quien_reviso;
    @SerializedName("describen")
    private String describen;
    @SerializedName("estatus")
    private String estatus;

    public Servicio(){}

    protected Servicio(Parcel in) {
        consecutivo = in.readString();
        fecha_solicitud = in.readString();
        quien_reviso = in.readString();
        describen = in.readString();
        estatus = in.readString();
    }

    public static final Creator<Servicio> CREATOR = new Creator<Servicio>() {
        @Override
        public Servicio createFromParcel(Parcel in) {
            return new Servicio(in);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[size];
        }
    };

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(String fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public String getQuien_reviso() {
        return quien_reviso;
    }

    public void setQuien_reviso(String quien_reviso) {
        this.quien_reviso = quien_reviso;
    }

    public String getDescriben() {
        return describen;
    }

    public void setDescriben(String describen) {
        this.describen = describen;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(consecutivo);
        dest.writeString(fecha_solicitud);
        dest.writeString(quien_reviso);
        dest.writeString(describen);
        dest.writeString(estatus);
    }
}
