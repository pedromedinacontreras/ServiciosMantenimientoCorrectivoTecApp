package com.example.usuario.serviciosmantenimientotecapp.interfaces;

import com.example.usuario.serviciosmantenimientotecapp.models.Respuesta;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by usuario on 10/12/16.
 */

public interface Api {
    String BASE_URL = "http://itculiacan.edu.mx";

    @GET("/prusoft/Mantenimiento/data/api/entrada.php")
    Call<Respuesta> checkLogIn(
            @Query("usuario") String usuario,
            @Query("clave") String clave
    );

    @GET("/prusoft/Mantenimiento/data/api/vermantenimientos.php")
    Call<JsonArray> getRespuestaDepartamentos(
            @Query("usuario") String usuario,
            @Query("usuariovalida") String usuariovalida,
            @Query("deptosolicita") int deptosolicita,
            @Query("deptomantenimiento") int deptomantenimiento
    );

    @POST("/prusoft/Mantenimiento/data/api/solicita.php")
    Call<Respuesta> solicitarMantenimiento(
            @Query("usuario") String usuario,
            @Query("usuariovalida") String usuariovalida,
            @Query("deptosolicita") String deptosolicita,
            @Query("deptomantenimiento") String deptomantenimiento,
            @Query("descripcionFalla") String descripcionFalla
    );



}
