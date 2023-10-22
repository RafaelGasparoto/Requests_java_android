package com.example.prova_2_dispositivos_moveis;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class ServicoLocador extends Service {
    Gson gson;

    public ServicoLocador() {
    }

    class LocadorBinder extends Binder {
        public ServicoLocador getService() {
            return ServicoLocador.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServicoLocador.LocadorBinder();
    }

    public void getLocadorByCpf(ListView lista, LinkedList<Locador> locador, ArrayAdapter<Locador> adapter, String cpf) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            String ur = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/locador/%s", cpf);
            URL url = new URL(ur);
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
                Locador value = gson.fromJson(resp, Locador.class);

                if (value != null) {
                    locador.clear();
                    locador.add(value);
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

    public void getLocadores(ListView lista, LinkedList<Locador> locador, ArrayAdapter<Locador> adapter) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/locador");
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
                Locador[] value = gson.fromJson(resp, Locador[].class);

                if (value != null) {
                    locador.clear();
                    for (Locador l : value)
                        locador.add(l);
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

    public void postLocador(Locador locador) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        String json = gson.toJson(locador);
        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/locador");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "application/json");
            PrintWriter pw = new PrintWriter(con.getOutputStream());
            pw.println(json);
            pw.flush();
            con.connect();
            Log.d("ResponseCode", "Http " + con.getResponseCode());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}