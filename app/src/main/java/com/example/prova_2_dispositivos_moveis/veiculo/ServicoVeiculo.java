package com.example.prova_2_dispositivos_moveis.veiculo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ServicoVeiculo extends Service {
    Gson gson;

    public ServicoVeiculo() {
    }

    class VeiculoBinder extends Binder {
        public ServicoVeiculo getService() {
            return ServicoVeiculo.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServicoVeiculo.VeiculoBinder();
    }

    public void getVeiculoByPlaca(ListView lista, ArrayList<Veiculo> veiculos, ArrayAdapter<Veiculo> adapter, String placa) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            String endPoint = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/veiculo/%s", placa);
            URL url = new URL(endPoint);
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
                Veiculo value = gson.fromJson(resp, Veiculo.class);

                if (value != null) {
                    veiculos.clear();
                    veiculos.add(value);
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

    public void getVeiculos(ListView lista, ArrayList<Veiculo> veiculos, ArrayAdapter<Veiculo> adapter) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/veiculo");
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
                Veiculo[] value = gson.fromJson(resp, Veiculo[].class);

                if (value != null) {
                    veiculos.clear();
                    for (Veiculo v : value)
                        veiculos.add(v);
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

    public boolean postVeiculo(Veiculo veiculo) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        String json = gson.toJson(veiculo);
        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/veiculo");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "application/json");
            PrintWriter pw = new PrintWriter(con.getOutputStream());
            pw.println(json);
            pw.flush();
            con.connect();
            Log.d("ResponseCode", "Http " + con.getResponseCode());
            if (con.getResponseCode() != 201) {
                Toast toast = Toast.makeText(this, "Algum dado inserido está errado", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        } catch (Throwable t) {
            Toast toast = Toast.makeText(this, "Houve algum erro ao cadastrar o veículo", Toast.LENGTH_SHORT);
            toast.show();
            t.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean putVeiculo(Veiculo veiculo) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        String json = gson.toJson(veiculo);
        try {
            String endPoint = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/veiculo/%s", veiculo.placa);
            URL url = new URL(endPoint);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("content-type", "application/json");
            PrintWriter pw = new PrintWriter(con.getOutputStream());
            pw.println(json);
            pw.flush();
            con.connect();
            Log.d("ResponseCode", "Http " + con.getResponseCode());
            if(con.getResponseCode() != 200) {
                Toast toast = Toast.makeText(getApplicationContext(), "Id modelo pode estar errado", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }
}