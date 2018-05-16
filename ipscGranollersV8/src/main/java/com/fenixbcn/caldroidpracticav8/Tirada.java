package com.fenixbcn.caldroidpracticav8;

/**
 * Created by javiercabregarcia on 19/12/17.
 */

public class Tirada {

    private String nombre,fecha_hora,ruta,club;
    private String logoClub;

    // constructor que crea la instancia del objeto tirada
    public Tirada(String nombre_tirada, String fecha_hora_tirada, String club_tirada, String ruta_tirada, String logo_club ) {
        this.nombre = nombre_tirada;
        this.fecha_hora = fecha_hora_tirada;
        this.ruta = ruta_tirada;
        this.club = club_tirada;
        this.logoClub =logo_club;
    }

    public String getNombre () {

        return nombre;
    }

    public String getFecha_hora () {

        return fecha_hora;
    }

    public String getRuta () {

        return ruta;
    }

    public String getClub () {

        return club;
    }

    public String getLogoClub() {

        return logoClub;
    }

    public String getNombreClub (String clubColor) {

        String nombreClub = "";

        switch (clubColor) {

            case "#7fdc44":
                nombreClub = "Granollers";
                break;
            case "#bac760":
                nombreClub = "Mataro";
                break;
            case "41d1df":
                nombreClub = "Barcelona";
                break;
            case "#e02a07":
                nombreClub = "Lleida";
                break;
            case "#f5e778":
                nombreClub = "Osona";
                break;
            case "#cf5daa":
                nombreClub = "Terrassa";
                break;
            case "#db9409":
                nombreClub = "Montsia";
                break;
            case "#16da84":
                nombreClub = "Sabadell";
                break;
        }

        return nombreClub;
    }

    public int getIconClub (String clubColor) {

        int iconClub=0;

        switch (clubColor) {

            case "#7fdc44":
                iconClub = R.mipmap.club_granollers_ico;
                break;
            default:
                iconClub = R.mipmap.ic_launcher;
        }


        return iconClub;
    }

}
