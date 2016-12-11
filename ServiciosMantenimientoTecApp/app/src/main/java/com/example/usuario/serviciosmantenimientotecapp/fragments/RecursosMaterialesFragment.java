package com.example.usuario.serviciosmantenimientotecapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.serviciosmantenimientotecapp.R;
import com.example.usuario.serviciosmantenimientotecapp.adapters.ServicioAdapter;
import com.example.usuario.serviciosmantenimientotecapp.models.Servicio;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecursosMaterialesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lmanager;
    private ServicioAdapter adapter;
    private ArrayList<Servicio> servicios;


    public RecursosMaterialesFragment() {
        // Required empty public constructor
    }

    public static RecursosMaterialesFragment create(ArrayList<Servicio> servicios) {
        RecursosMaterialesFragment fragment = new RecursosMaterialesFragment();
        fragment.servicios = servicios;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recursos_materiales, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        asignaValores();
    }

    private void asignaValores(){
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_recursos_materiales);
        lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lmanager);
        adapter = new ServicioAdapter(getContext(),servicios);
        recyclerView.setAdapter(adapter);
    }

}
