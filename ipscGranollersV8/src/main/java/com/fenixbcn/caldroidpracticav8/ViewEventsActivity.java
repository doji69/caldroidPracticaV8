package com.fenixbcn.caldroidpracticav8;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewEventsActivity extends AppCompatActivity {

    private static final String TAG = "viewEvents";
    private ListView listaEventos;
    private TextView txtMensaje;
    private ProgressBar pbCargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fenixbcn.caldroidpracticav8.R.layout.activity_view_events);

        // creo mi propio action bar
        Toolbar actionBar = (Toolbar) findViewById(com.fenixbcn.caldroidpracticav8.R.id.actionBarView);
        actionBar.setLogo(com.fenixbcn.caldroidpracticav8.R.mipmap.ipsc_granollers_ico);
        setSupportActionBar(actionBar);

        final SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd");
        listaEventos = (ListView) findViewById(com.fenixbcn.caldroidpracticav8.R.id.listaEventos);
        txtMensaje = (TextView) findViewById(com.fenixbcn.caldroidpracticav8.R.id.txtMensaje);
        pbCargando = (ProgressBar) findViewById(com.fenixbcn.caldroidpracticav8.R.id.pbCargando);

        Date fecha = new Date();

        Bundle viewEventsActivityVars = getIntent().getExtras();
        fecha.setTime(viewEventsActivityVars.getLong("fecha",-1));

        //System.out.println(formatedDate.format(fecha));

        String new_ulr = "http://calendario.ipscgranollers.com/webserviceDayEvents.php?fecha="+ formatedDate.format(fecha);
        //Toast.makeText(ViewEventsActivity.this,"" + new_ulr, Toast.LENGTH_LONG).show();
        new jtGetDateEvents().execute(new_ulr);


    }

    public class jtGetDateEvents extends AsyncTask<String, Integer, String > {

        @Override
        protected void onPreExecute(){
            txtMensaje.setText("Cargando...");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            txtMensaje.setText(values[0]);
            pbCargando.setProgress(values[0]);

        }

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
                //List<String> cadEvents= new ArrayList<String>();

                for (int i=0; i<eventos.length(); i++) {
                    JSONObject evento = eventos.getJSONObject(i);
                    //String tirada = evento.getString("tirada");
                    JSONObject tirada = evento.getJSONObject("tirada");

                    int id = tirada.getInt("id");
                    String nombre = tirada.getString("title");
                    String inicio = tirada.getString("start");
                    String fin = tirada.getString("end");
                    String color = tirada.getString("color");
                    String urlEvent = tirada.getString("url_practiscore");

                    finalBuffereData.append(id + " -- " + nombre + " -- " + inicio + " -- " + fin+ " -- " + color + " -- " + urlEvent + "\n\n");
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
            pbCargando.setVisibility(View.GONE);
            txtMensaje.setText("");
            System.out.println("result = "+result);

            if (result != null && !result.equals("")) {
                ViewEventsActivity.this.setDatosLista(result);
            } else {
                System.out.println("ignoramos result");
                txtMensaje.setText("No hay eventos");
            }

        }
    }

    private String [] vEvento = new String[0];

    private void setDatosLista(String result) {

        // crear array adapter

        ArrayList<Tirada> alTirada = new ArrayList<>();
        TiradaAdapter tiradas;

        String [] vResult = new String[0];
        vResult = result.split("\n\n");

        // recorremos el vector para añadir cada uno de los eventos que hay en el array vResult

        for (int i=0; i<vResult.length; i++) {
            //Log.d(TAG, "el valor de la posicion " + i + " es: " + vResult[i]);
            vEvento = vResult[i].split(" -- ");
            //System.out.println("el nuevo evento es " + cadena); // muestra en el logcat el mensaje
            //System.out.println("nombre " + vEvento[1] + " dia " + vEvento[2] + " evento3 " + vEvento[3] + " club " + vEvento[4] + " ruta " + vEvento[5]); // muestra en el logcat el mensaje
            //alTirada.add(new Tirada(vEvento[1],vEvento[2],vEvento[4],vEvento[5],R.mipmap.ipsc_granollers_ico));
            String nombre = vEvento[1];
            String fecha_ini = vEvento[2]; // evento[3] es la fecha de fin pero no la uso
            String club = vEvento[4]; // evento[4] es el color que representa a cada club
            String ruta = "";
            if (vEvento.length >5)
                ruta = vEvento[5];
            alTirada.add(new Tirada(nombre,fecha_ini,club,ruta,club));

        }

        tiradas = new TiradaAdapter(this, alTirada);

        listaEventos.setAdapter(tiradas);

        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (vEvento.length>5) {

                    Intent registerActivityVars = new Intent(getApplicationContext(), RegisterActivity.class);
                    registerActivityVars.putExtra("id", position);
                    registerActivityVars.putExtra("ruta", vEvento[5]);
                    startActivity(registerActivityVars);
                } else {

                    Toast.makeText(ViewEventsActivity.this, "El enlace no esta disponible", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
