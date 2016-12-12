package com.example.usuario.serviciosmantenimientotecapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.serviciosmantenimientotecapp.fragments.CentroComputoFragment;
import com.example.usuario.serviciosmantenimientotecapp.fragments.MantenimientoFragment;
import com.example.usuario.serviciosmantenimientotecapp.fragments.RecursosMaterialesFragment;
import com.example.usuario.serviciosmantenimientotecapp.interfaces.Api;
import com.example.usuario.serviciosmantenimientotecapp.models.Respuesta;
import com.example.usuario.serviciosmantenimientotecapp.models.Servicio;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckMantenimientos extends AppCompatActivity {

    private ArrayList<Servicio> recursosMateriales;
    private ArrayList<Servicio> centroComputo;
    private ArrayList<Servicio> mantenimiento;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private Dialog dialog;
    public static final String RECURSOS_MATERIALES = "Departamento de Recursos Materiales";
    public static final String CENTRO_COMPUTO = "Departamento de Centro de Cómputo";
    public static final String MANTENIMIENTO = "Departamento de Mantenimiento";
    private String departamentoSolicita;
    private String token;
    private ServiciosPagerAdapter adapter;
    private int mTabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mantenimientos);

        toolbar = (Toolbar) findViewById(R.id.toolbar_check_mantenimientos);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        departamentoSolicita = getIntent().getStringExtra("departamentoSolicita");
        token = getIntent().getStringExtra("token");
        recursosMateriales = getIntent().getParcelableArrayListExtra("array1");
        centroComputo = getIntent().getParcelableArrayListExtra("array2");
        mantenimiento = getIntent().getParcelableArrayListExtra("array3");
        adapter = new ServiciosPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = createSolicitarMantenimientoDialog();
                dialog.show();
            }
        });
}


    public Dialog createSolicitarMantenimientoDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(CheckMantenimientos.this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_solicitar_mantenimiento,null);
        final EditText edtUsuario = (EditText) view.findViewById(R.id.edt_usuario);
        final EditText edtDescribe = (EditText) view.findViewById(R.id.edt_describe);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_departamentos);
        builder.setView(view)
                .setTitle("Nuevo servicio")
                .setCancelable(false)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(edtUsuario.getText().toString().equals("") || edtDescribe.getText().toString().equals("")){
                            Toast.makeText(CheckMantenimientos.this,"Error con la solicitud",Toast.LENGTH_SHORT).show();
                        } else {
                            String departamento = "";
                            switch (spinner.getSelectedItem().toString()) {
                                case RECURSOS_MATERIALES:
                                    departamento = "419";
                                    break;
                                case CENTRO_COMPUTO:
                                    departamento = "420";
                                    break;
                                case MANTENIMIENTO:
                                    departamento = "421";
                                    break;
                                default:
                                    break;
                            }
                            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create()).build();
                            Api api = retrofit.create(Api.class);
                            retrofit2.Call<Respuesta> callResponse = api.solicitarMantenimiento(edtUsuario.getText().toString(), token, departamentoSolicita,departamento,edtDescribe.getText().toString());
                            callResponse.enqueue(new Callback<Respuesta>() {
                                @Override
                                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                                    Log.e("onResponse","onResponse");
                                    dialog.dismiss();
                                    if(response.body().isRespuesta()){
                                        Toast.makeText(CheckMantenimientos.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                                        Servicio servicio = new Servicio();
                                        servicio.setEstatus("0");
                                        servicio.setFecha_solicitud("");
                                        servicio.setConsecutivo((recursosMateriales.size()+1)+"");
                                        servicio.setDescriben(edtDescribe.getText().toString());
                                        servicio.setQuien_reviso("");
                                        switch (spinner.getSelectedItem().toString()) {
                                            case RECURSOS_MATERIALES:
                                                recursosMateriales.add(servicio);
                                                mTabPosition = 0;
                                                break;
                                            case CENTRO_COMPUTO:
                                                centroComputo.add(servicio);
                                                mTabPosition = 1;
                                                break;
                                            case MANTENIMIENTO:
                                                mantenimiento.add(servicio);
                                                mTabPosition = 2;
                                                break;
                                            default:
                                                break;
                                        }
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(CheckMantenimientos.this, "Solicitud incorrecta", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Respuesta> call, Throwable t) {
                                    Log.e("onFailure","onFailure");
                                    dialog.dismiss();
                                    Toast.makeText(CheckMantenimientos.this, "No se pudo enviar la solicitud", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
        return builder.create();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ServiciosPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private int totalPages = 3;

        public ServiciosPagerAdapter(Context context, FragmentManager fm){
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position){
            Fragment f = new Fragment();
            switch (position){
                case 0:
                    f = RecursosMaterialesFragment.create(recursosMateriales);
                    break;
                case 1:
                    f = CentroComputoFragment.create(centroComputo);
                    break;
                case 2:
                    f = MantenimientoFragment.create(mantenimiento);
                    break;
            }
            return f;
        }

        @Override
        public int getCount(){
            return totalPages;
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }

}
