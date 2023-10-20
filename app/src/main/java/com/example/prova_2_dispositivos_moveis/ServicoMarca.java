package com.example.prova_2_dispositivos_moveis;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class ServicoMarca extends Service {
    Gson gson;

    class MeuBinder extends Binder {
        public ServicoMarca getService() {
            return ServicoMarca.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MeuBinder();
    }

    public void getMarca(ListView lista, LinkedList<Marca> marcas, ArrayAdapter<Marca> adapter) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/marca");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            if (con.getResponseCode() == 200) {
                String resp = "";
                String linha;
                BufferedReader buf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                do {
                    linha = buf.readLine();
                    if (linha != null) {
                        resp += linha;
                    }
                } while (linha != null);
                Marca[] value = gson.fromJson(resp, Marca[].class);

                if (value != null) {
                    marcas.clear();
                    for (Marca m : value)
                        marcas.add(m);
                }
                lista.post(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}