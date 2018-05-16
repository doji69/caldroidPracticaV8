package com.fenixbcn.caldroidpracticav8;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javiercabregarcia on 19/12/17.
 */

public class TiradaAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Tirada> mListaTiradas = new ArrayList<>();

    public TiradaAdapter(@NonNull Context context, @NonNull ArrayList<Tirada> listaTiradas) {
        super(context, 0, listaTiradas);

        mContext = context;
        mListaTiradas = listaTiradas;

    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        View vista = convertView;

        if(vista == null) {
            vista = LayoutInflater.from(mContext).inflate(com.fenixbcn.caldroidpracticav8.R.layout.tirada, parent, false);
        }

        Tirada tiradaActual = mListaTiradas.get(position);

        TextView nomTirada = (TextView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.tvNombre);
        nomTirada.setText(tiradaActual.getNombre());

        TextView fechaHoraTirada = (TextView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.tvFechaHora);
        fechaHoraTirada.setText(tiradaActual.getFecha_hora());

        TextView clubTirada = (TextView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.tvClub);
        clubTirada.setText(tiradaActual.getNombreClub(tiradaActual.getClub()));

        TextView rutaTirada = (TextView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.tvRuta);
        rutaTirada.setText(tiradaActual.getRuta());

        ImageView logoclubTirada = (ImageView) vista.findViewById(R.id.ivIcon);
        logoclubTirada.setImageResource(tiradaActual.getIconClub(tiradaActual.getLogoClub()));

        vista.setBackgroundColor(Color.parseColor(tiradaActual.getClub()));

        return vista;
    }
}
