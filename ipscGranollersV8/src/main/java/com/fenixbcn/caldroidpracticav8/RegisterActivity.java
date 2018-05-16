package com.fenixbcn.caldroidpracticav8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fenixbcn.caldroidpracticav8.R.layout.activity_register);

        int id_item_clicked=0;
        String ruta_registro;

        Bundle registerActivityVars = getIntent().getExtras();
        id_item_clicked = registerActivityVars.getInt("id",-1);
        ruta_registro = registerActivityVars.getString("ruta"," ");

        WebView wbPagina = (WebView) findViewById(com.fenixbcn.caldroidpracticav8.R.id.wbPagina);
        WebSettings webSettings = wbPagina.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbPagina.setWebViewClient(new WebViewClient());
        wbPagina.loadUrl(ruta_registro);



        Toast.makeText(RegisterActivity.this, "el item clicado es: "+ id_item_clicked, Toast.LENGTH_LONG).show();


    }
}
