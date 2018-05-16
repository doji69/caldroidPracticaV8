package com.fenixbcn.caldroidpracticav8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by javiercabregarcia on 3/1/18.
 */

public class ClubsFragments extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // crear la vista
        View v = LayoutInflater.from (getActivity())
                .inflate(com.fenixbcn.caldroidpracticav8.R.layout.lista_clubs, null);

        ListView listaClubs = (ListView) v.findViewById(com.fenixbcn.caldroidpracticav8.R.id.listaClubs);

        // creamos el array list y el adapter
        ArrayList<Club> alClubs = new ArrayList<>();
        ClubAdapter clubs;

        alClubs.add(new Club("Club Precision Granollers", com.fenixbcn.caldroidpracticav8.R.mipmap.club_granollers_ico));
        //alClubs.add(new Club("Club Tir Barcelona", com.fenixbcn.caldroidpracticav8.R.mipmap.ipsc_granollers_ico));

        clubs = new ClubAdapter(getContext(), alClubs);

        listaClubs.setAdapter(clubs);

        // crear el boton llistener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.i("TAG", "you click dialog button");

            }
        };

        // construir el alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Lista de Clubs de tiro")
                .setView(v)
                .setPositiveButton(com.fenixbcn.caldroidpracticav8.R.string.clubs_dialog_close, listener)
                .create();
    }
}
