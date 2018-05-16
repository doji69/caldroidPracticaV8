package com.fenixbcn.caldroidpracticav8;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by javiercabregarcia on 4/1/18.
 */

public class Club {

    private String nombreClub;
    private int logoClub;

    // constructor que crea la instancia del objeto club
    public Club(String nombre_club, int logo_club) {
        this.nombreClub = nombre_club;
        this.logoClub = logo_club;
    }

    public String getNombreClub () {

        return nombreClub;
    }

    public int getLogoClub() {

        return logoClub;
    }
}
