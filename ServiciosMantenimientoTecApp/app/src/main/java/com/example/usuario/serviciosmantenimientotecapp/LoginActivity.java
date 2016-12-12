package com.example.usuario.serviciosmantenimientotecapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.serviciosmantenimientotecapp.interfaces.Api;
import com.example.usuario.serviciosmantenimientotecapp.models.MantenimientosSolicitados;
import com.example.usuario.serviciosmantenimientotecapp.models.Respuesta;
import com.example.usuario.serviciosmantenimientotecapp.models.Servicio;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsuario;
    private EditText edtContraseña;
    private Button btnEntrar;
    private ArrayList<Servicio> recursosMateriales;
    private ArrayList<Servicio> centroComputo;
    private ArrayList<Servicio> mantenimiento;
    private String token;
    private String usuario;
    private String departamentoSolicita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = (EditText) findViewById(R.id.edt_usuario);
        edtContraseña = (EditText) findViewById(R.id.edt_contraseña);
        btnEntrar = (Button) findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsuario.getText().toString().equals("") || edtContraseña.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"Campos vacíos",Toast.LENGTH_SHORT).show();
                } else {
                    btnEntrar.setEnabled(false);
                    usuario = edtUsuario.getText().toString();
                    checkLogIn(usuario, edtContraseña.getText().toString());
                }
            }
        });
    }

    private void checkLogIn(String nombre, String contraseña) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        retrofit2.Call<Respuesta> callResponse = api.checkLogIn(nombre, contraseña);
        callResponse.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(retrofit2.Call<Respuesta> call, Response<Respuesta> response) {
                Respuesta respuesta = response.body();
                token = respuesta.getUsuariovalida();
                departamentoSolicita = respuesta.getDeptosolicita();
                if(respuesta.isRespuesta()) {
                    Log.e("checkLogIn", "Good");
                    loadMantenimientosRecursosMateriales();
                } else if (!respuesta.isRespuesta()) {
                    Log.e("checkLogIn", "Fail");
                    Toast.makeText(LoginActivity.this,"Usuario y/o contraseña incorrecto(s)",Toast.LENGTH_SHORT).show();
                    btnEntrar.setEnabled(true);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Respuesta> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Error con la solicitud",Toast.LENGTH_SHORT).show();
                Dialog dialog = createErrorDialog(t.toString());
                dialog.show();
            }
        });
    }

    private void loadMantenimientosRecursosMateriales(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        retrofit2.Call<JsonArray> callResponse = api.getRespuestaDepartamentos(usuario,token, Integer.valueOf(departamentoSolicita),419);
        callResponse.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(retrofit2.Call<JsonArray> call, Response<JsonArray> response) {
                Log.e("onResponse","onResponse");
                ArrayList<Servicio> servicioArrayList = new ArrayList<Servicio>();
                int cantidad = 0;
                JsonArray jsonArray = response.body();

                //Cachar los elementos y guardarlos en un arraylist
                JsonElement jsonElementLista = jsonArray.get(1);
                try {
                    JSONArray jsonArrayLista = new JSONArray(jsonElementLista.toString());
                    for(int i = 0; i < jsonArrayLista.length(); i++) {
                        JSONObject jsonServicio = jsonArrayLista.getJSONObject(i);
                        Servicio servicio = new Servicio();
                        servicio.setQuien_reviso(jsonServicio.getString("quien_reviso"));
                        servicio.setEstatus(jsonServicio.getString("estatus"));
                        servicio.setDescriben(jsonServicio.getString("describen"));
                        servicio.setConsecutivo(jsonServicio.getString("consecutivo"));
                        servicio.setFecha_solicitud(jsonServicio.getString("fecha_solicitud"));
                        servicioArrayList.add(servicio);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Cachar la cantidad
                JsonElement jsonElement = jsonArray.get(0);
                try {
                    JSONObject jsonCantidad = new JSONObject(jsonElement.toString());
                    cantidad = jsonCantidad.getInt("cantidad");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("cantidad", String.valueOf(cantidad));
                recursosMateriales = servicioArrayList;
                loadMantenimientosCentroComputo();
            }

            @Override
            public void onFailure(retrofit2.Call<JsonArray> call, Throwable t) {
                Dialog dialog = createErrorDialog(t.toString());
                dialog.show();
            }
        });
    }

    private void loadMantenimientosCentroComputo(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        retrofit2.Call<JsonArray> callResponse = api.getRespuestaDepartamentos(usuario,token, Integer.valueOf(departamentoSolicita),420);
        callResponse.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(retrofit2.Call<JsonArray> call, Response<JsonArray> response) {
                Log.e("onResponse","onResponse");
                ArrayList<Servicio> servicioArrayList = new ArrayList<Servicio>();
                int cantidad = 0;
                JsonArray jsonArray = response.body();

                //Cachar los elementos y guardarlos en un arraylist
                JsonElement jsonElementLista = jsonArray.get(1);
                try {
                    JSONArray jsonArrayLista = new JSONArray(jsonElementLista.toString());
                    for(int i = 0; i < jsonArrayLista.length(); i++) {
                        JSONObject jsonServicio = jsonArrayLista.getJSONObject(i);
                        Servicio servicio = new Servicio();
                        servicio.setQuien_reviso(jsonServicio.getString("quien_reviso"));
                        servicio.setEstatus(jsonServicio.getString("estatus"));
                        servicio.setDescriben(jsonServicio.getString("describen"));
                        servicio.setConsecutivo(jsonServicio.getString("consecutivo"));
                        servicio.setFecha_solicitud(jsonServicio.getString("fecha_solicitud"));
                        servicioArrayList.add(servicio);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Cachar la cantidad
                JsonElement jsonElement = jsonArray.get(0);
                try {
                    JSONObject jsonCantidad = new JSONObject(jsonElement.toString());
                    cantidad = jsonCantidad.getInt("cantidad");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("cantidad", String.valueOf(cantidad));
                centroComputo = servicioArrayList;
                loadMantenimientosMantenimiento();
            }

            @Override
            public void onFailure(retrofit2.Call<JsonArray> call, Throwable t) {
                Dialog dialog = createErrorDialog(t.toString());
                dialog.show();
            }
        });
    }

    private void loadMantenimientosMantenimiento(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        retrofit2.Call<JsonArray> callResponse = api.getRespuestaDepartamentos(usuario,token, Integer.valueOf(departamentoSolicita),421);
        callResponse.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(retrofit2.Call<JsonArray> call, Response<JsonArray> response) {
                Log.e("onResponse","onResponse");
                ArrayList<Servicio> servicioArrayList = new ArrayList<Servicio>();
                int cantidad = 0;
                JsonArray jsonArray = response.body();

                //Cachar los elementos y guardarlos en un arraylist
                JsonElement jsonElementLista = jsonArray.get(1);
                try {
                    JSONArray jsonArrayLista = new JSONArray(jsonElementLista.toString());
                    for(int i = 0; i < jsonArrayLista.length(); i++) {
                        JSONObject jsonServicio = jsonArrayLista.getJSONObject(i);
                        Servicio servicio = new Servicio();
                        servicio.setQuien_reviso(jsonServicio.getString("quien_reviso"));
                        servicio.setEstatus(jsonServicio.getString("estatus"));
                        servicio.setDescriben(jsonServicio.getString("describen"));
                        servicio.setConsecutivo(jsonServicio.getString("consecutivo"));
                        servicio.setFecha_solicitud(jsonServicio.getString("fecha_solicitud"));
                        servicioArrayList.add(servicio);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Cachar la cantidad
                JsonElement jsonElement = jsonArray.get(0);
                try {
                    JSONObject jsonCantidad = new JSONObject(jsonElement.toString());
                    cantidad = jsonCantidad.getInt("cantidad");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("cantidad", String.valueOf(cantidad));
                mantenimiento = servicioArrayList;

                Intent intent = new Intent(LoginActivity.this, CheckMantenimientos.class);
                intent.putExtra("array1", recursosMateriales);
                intent.putExtra("array2", centroComputo);
                intent.putExtra("array3", mantenimiento);
                intent.putExtra("token",token);
                intent.putExtra("departamentoSolicita", departamentoSolicita);
                btnEntrar.setEnabled(true);
                startActivity(intent);
            }

            @Override
            public void onFailure(retrofit2.Call<JsonArray> call, Throwable t) {
                Dialog dialog = createErrorDialog(t.toString());
                dialog.show();
            }
        });
    }

    public Dialog createErrorDialog(String error){
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Error")
                .setMessage(error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        btnEntrar.setEnabled(true);
        return builder.create();
    }

    @Override
    public void onResume() {
        btnEntrar.setEnabled(true);
        super.onResume();
    }
}
