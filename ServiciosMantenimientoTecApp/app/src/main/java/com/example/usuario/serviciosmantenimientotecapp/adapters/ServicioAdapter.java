package com.example.usuario.serviciosmantenimientotecapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario.serviciosmantenimientotecapp.R;
import com.example.usuario.serviciosmantenimientotecapp.models.Servicio;

import java.util.ArrayList;

/**
 * Created by usuario on 11/12/16.
 */

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder> {

    private ArrayList<Servicio> items;
    private Context context;
    public static final String ESTATUS_SOLICITADO = "0";
    public static final String ESTATUS_EN_PROCESO = "1";
    public static final String ESTATUS_TERMINADO = "2";
    public static final String ESTATUS_LIBERADO = "3";
    public static final String ESTATUS_RECHAZADO = "5";

    public ServicioAdapter(Context context, ArrayList<Servicio> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ServicioAdapter.ServicioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_servicio,viewGroup,false);
        return new ServicioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServicioAdapter.ServicioViewHolder holder, int i) {
        final Servicio servicio = items.get(i);

        holder.tvQuienReviso.setText(servicio.getQuien_reviso());
        holder.tvDescriben.setText(servicio.getDescriben());
        holder.tvFecha.setText(servicio.getFecha_solicitud());
        switch (servicio.getEstatus()) {
            case ESTATUS_SOLICITADO:
                holder.tvEstatus.setText("Solicitado");
                holder.tvEstatus.setTextColor(ContextCompat.getColor(context, R.color.valor0));
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.valor0));
                break;
            case ESTATUS_EN_PROCESO:
                holder.tvEstatus.setText("En proceso");
                holder.tvEstatus.setTextColor(ContextCompat.getColor(context, R.color.valor1));
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.valor1));
                break;
            case ESTATUS_TERMINADO:
                holder.tvEstatus.setText("Terminado");
                holder.tvEstatus.setTextColor(ContextCompat.getColor(context, R.color.valor2));
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.valor2));
                break;
            case ESTATUS_LIBERADO:
                holder.tvEstatus.setText("Liberado");
                holder.tvEstatus.setTextColor(ContextCompat.getColor(context, R.color.valor3));
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.valor3));
                break;
            case ESTATUS_RECHAZADO:
                holder.tvEstatus.setText("Rechazado");
                holder.tvEstatus.setTextColor(ContextCompat.getColor(context, R.color.valor5));
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.valor5));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public class ServicioViewHolder extends RecyclerView.ViewHolder{

        TextView tvQuienReviso;
        TextView tvDescriben;
        TextView tvEstatus;
        TextView tvFecha;
        LinearLayout linearLayout;

        public ServicioViewHolder(View view){
            super(view);
            tvQuienReviso = (TextView) view.findViewById(R.id.tv_quien_reviso);
            tvDescriben = (TextView) view.findViewById(R.id.tv_describen);
            tvEstatus = (TextView) view.findViewById(R.id.tv_estatus);
            tvFecha = (TextView) view.findViewById(R.id.tv_fecha);
            linearLayout = (LinearLayout) view.findViewById(R.id.layout_status);
        }
    }
}