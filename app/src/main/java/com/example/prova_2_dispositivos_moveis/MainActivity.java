package com.example.prova_2_dispositivos_moveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Gson gson;
    class Ouvinte extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("NUMERO_GERADO")) {
                try {
                    URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/marca");
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    if (con.getResponseCode() == 200) {
                        String resp = "";
                        String linha;
                        BufferedReader buf = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        do {
                            linha = buf.readLine();
                            if (linha != null) {
                                resp += linha;
                            }
                        } while (linha != null);
                        Marca[] sets = gson.fromJson(resp, Marca[].class);

//                        if (sets != null) {
//                            setores.clear();
//                            for (Marca s : sets)
//                                setores.add(s);
//                        }
//                        lista.post( new Runnable() {
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
                    }
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        registerReceiver(new Ouvinte(),
                new IntentFilter("NUMERO_GERADO"));
//        Intent servico = new Intent(getApplicationContext(), Gerador.class);
//        bindService(servico, new Conector(), BIND_AUTO_CREATE);
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
    }

    public void iniciar(View v) {
//        Intent it = new Intent(getApplicationContext(), ContadorService.class);
//        it.putExtra("QUANTIDADE", 10);
//        startService( it );

        Intent intent = new Intent("NUMERO_GERADO");
        intent.putExtra("QUANTIDADE", 10);
        sendBroadcast(intent);
    }
}