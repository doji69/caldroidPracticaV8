package com.fenixbcn.caldroidpracticav8;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private CaldroidFragment calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fenixbcn.caldroidpracticav8.R.layout.activity_main);

        // creo mi propio action bar
        Toolbar actionBarView = (Toolbar) findViewById(com.fenixbcn.caldroidpracticav8.R.id.actionBar);
        actionBarView.setLogo(com.fenixbcn.caldroidpracticav8.R.mipmap.ipsc_granollers_ico);
        setSupportActionBar(actionBarView);

        calendario = new CaldroidFragment(); // crea la instancia del calendario tipo Caldroid

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            calendario.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else { // If activity is created from fresh
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Monday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
            //args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            calendario.setArguments(args);
        }

        // llamada a la funcion asyntask para capturar el resultado del webservice
        new jtGetDates().execute("http://calendario.ipscgranollers.com/webserviceFechas.php");

        // Setup listener
        calendario.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                final AlertDialog.Builder desplegable = new AlertDialog.Builder(MainActivity.this);
                CharSequence []opciones = new CharSequence[2];
                opciones[0] = "Ver eventos";
                opciones[1] = "Cancelar";

                final Long fecha;

                fecha = date.getTime();

                desplegable.setTitle("Elige una tarea")
                        .setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0) {

                                    Intent viewEventsActivityVars = new Intent(getApplication(), ViewEventsActivity.class);
                                    viewEventsActivityVars.putExtra("fecha", fecha);
                                    startActivity(viewEventsActivityVars);

                                }
                            }
                        });
                AlertDialog dialog = desplegable.create();
                dialog.show();
            }
        });
    }

    private void setCustomResourceForDates(String result) { // pinta el caledario

        // hay que recuperar las fechas de inicio y fin de todos los eventos y marcar las fechas que estan en la lista.
        // si el evento dura un solo dia se marca el día, pero si dura más de un día se marca principio y final

        //System.out.println(result);

        String []vFechas;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicial= null; // fecha del sistema
        Date fechaFinal= null; // fecha que recibo del vector
        int dias=0;

        vFechas = result.split("_");

        try {
            fechaInicial = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();

        for (int i=0;i<(vFechas.length);i++) {

            try {
                fechaFinal = dateFormat.parse(vFechas[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dias = (int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

            //System.out.println("inicio "+fechaInicial);
            //System.out.println("fin "+fechaFinal);
            //System.out.println(dias);

            cal = Calendar.getInstance(); // hay que llamar al getInstance para cada fecha que se quiere colorear
            cal.add(Calendar.DATE, dias);
            //System.out.println(Calendar.DATE);

            Date blueDate = cal.getTime();
            //System.out.println("pinto "+blueDate);

            if (calendario != null) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(com.fenixbcn.caldroidpracticav8.R.color.blue));
                calendario.setBackgroundDrawableForDate(blue, blueDate);
                calendario.setTextColorForDate(com.fenixbcn.caldroidpracticav8.R.color.white, blueDate);
            }

        }

        //tvResultado.setText(Integer.toString(dias));

        // fecha a colorear
        /*String stDias = tvResultado.getText().toString();
        Integer dias=Integer.parseInt(stDias);
        dias = dias * -1;
        cal.add(Calendar.DATE, dias);
        Date blueDate = cal.getTime();

        if (calendario != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            calendario.setBackgroundDrawableForDate(blue, blueDate);
            calendario.setTextColorForDate(R.color.white, blueDate);
        }*/

        /*prueba de resta de dos fechas*/

            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicial= null;
            try {
                fechaInicial = dateFormat.parse(dateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date fechaFinal= null;
            try {
                fechaFinal = dateFormat.parse("2017-12-21");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int dias=0;
            dias = (int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

            tvResultado.setText(Integer.toString(dias));*/

            /* fin prueba*/
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (calendario != null) {
            calendario.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (calendario != null) {
            calendario.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

    public class jtGetDates extends AsyncTask<String, String, String > {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject tiradas = new JSONObject(finalJson);
                JSONArray eventos = tiradas.getJSONArray("tiradas");

                StringBuffer finalBuffereData = new StringBuffer();
                for (int i=0; i<eventos.length(); i++) {
                    JSONObject evento = eventos.getJSONObject(i);
                    //String tirada = evento.getString("tirada");
                    JSONObject tirada = evento.getJSONObject("tirada");

                    //int id = tirada.getInt("id");
                    //String nombre = tirada.getString("title");
                    String inicio = tirada.getString("start");
                    //String fin = tirada.getString("end");
                    //String color = tirada.getString("color");
                    //String urlEvent = tirada.getString("url_practiscore");

                    //finalBuffereData.append(id + " - " + nombre + " - " + inicio + " - " + fin+ " - " + color + " - " + urlEvent + "\n\n");
                    String []vInicio;
                    vInicio = inicio.split(" ");

                    finalBuffereData.append(vInicio[0] + "_");
                }
                return finalBuffereData.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            result = result.substring(0, result.length()-1); // le quita el ultimo caracter a la cadena para eliminar el ultimo "|"

            setCustomResourceForDates(result); // colorea el calendario

            // Attach to the activity. asigna el calendario a layout deseado
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(com.fenixbcn.caldroidpracticav8.R.id.lyCalendario, calendario);
            t.commit();
        }
    }

    // muestra el menu de la actionBar y sus items
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.fenixbcn.caldroidpracticav8.R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        switch (item.getItemId()) {

            case com.fenixbcn.caldroidpracticav8.R.id.clubs:
                // llamar a una actividad que muestre el color de cada club
                //Toast.makeText(MainActivity.this, "club", Toast.LENGTH_LONG).show();
                /*final AlertDialog.Builder adClubs = new AlertDialog.Builder(this);
                adClubs.setTitle("Lista de club");
                adClubs.setCancelable(true);
                adClubs.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adClubs.setMessage("Barcelona\nGranollers");
                adClubs.show();*/


                FragmentManager manager = getSupportFragmentManager();
                ClubsFragments clubsFragments = new ClubsFragments();
                clubsFragments.show(manager, "Message Dialog");

                Log.i("TAG", "Just show the dialog");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // fin muestra el menu de la actionBar y sus items

    @Override
    public void onBackPressed() { // bloquea el boton backbutton del dispositivo

    }

}
