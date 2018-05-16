package com.fenixbcn.caldroidpracticav8;

import android.content.Context;
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
 * Created by javiercabregarcia on 4/1/18.
 */

public class ClubAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Club> mListaclubs = new ArrayList<>();

    public ClubAdapter(@NonNull Context context, @NonNull ArrayList<Club> listaClubs) {
        super(context, 0,listaClubs);

        mContext = context;
        mListaclubs = listaClubs;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        if(vista == null) {
            vista = LayoutInflater.from(mContext).inflate(com.fenixbcn.caldroidpracticav8.R.layout.club, parent, false);
        }

        Club clubActual = mListaclubs.get(position);

        TextView nomClub = (TextView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.nomClub);
        nomClub.setText(clubActual.getNombreClub());

        ImageView logoClub = (ImageView) vista.findViewById(com.fenixbcn.caldroidpracticav8.R.id.imLogoClub);
        logoClub.setImageResource(clubActual.getLogoClub());


        return vista;

    }
}
