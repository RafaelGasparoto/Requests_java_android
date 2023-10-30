package com.example.prova_2_dispositivos_moveis.locacao;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prova_2_dispositivos_moveis.locador.Cep;
import com.example.prova_2_dispositivos_moveis.locador.Locador;
import com.example.prova_2_dispositivos_moveis.locador.ServicoLocador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class ServicoLocacao extends Service {
    Gson gson;

    public ServicoLocacao() {
    }

    class LocacaoBinder extends Binder {
        public ServicoLocacao getService() {
            return ServicoLocacao.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServicoLocacao.LocacaoBinder();
    }

    public void getLocacaoById(ListView lista, LinkedList<Locacao> locacao, ArrayAdapter<Locacao> adapter, String id) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            String endPoint = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/locacao/%s", id);
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
                Locacao value = gson.fromJson(resp, Locacao.class);

                if (value != null) {
                    locacao.clear();
                    locacao.add(value);
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

    public void getLocacoes(ListView lista, LinkedList<Locacao> locacoes, ArrayAdapter<Locacao> adapter) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();

        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/locacao");
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
                Locacao[] value = gson.fromJson(resp, Locacao[].class);

                if (value != null) {
                    locacoes.clear();
                    for (Locacao l : value)
                        locacoes.add(l);
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

    public boolean postLocacao(Locacao locacao) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        String json = gson.toJson(locacao);
        try {
            URL url = new URL("https://argo.td.utfpr.edu.br/locadora-war/ws/locacao");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "application/json");
            PrintWriter pw = new PrintWriter(con.getOutputStream());
            pw.println(json);
            pw.flush();
            con.connect();
            Log.d("ResponseCode", "Http " + con.getResponseCode());
            if(con.getResponseCode() != 201) {
                Toast toast = Toast.makeText(this, "Algum dado inserido está errado", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        } catch (Throwable t) {
            Toast toast = Toast.makeText(this, "Erro ao registrar cadastro", Toast.LENGTH_SHORT);
            toast.show();
            t.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean putLocacao(Locacao locacao) {
        GsonBuilder bld = new GsonBuilder();
        gson = bld.create();
        String json = gson.toJson(locacao);
        try {
            String endPoint = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/locacao/%s", locacao.getId());
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
                Toast toast = Toast.makeText(this, "Algum dado inserido está errado", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        } catch (Throwable t) {
            Toast toast = Toast.makeText(this, "Erro ao atualizar cadastro", Toast.LENGTH_SHORT);
            toast.show();
            t.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteLocacao(int id) {
        try {
            String endPoint = String.format("https://argo.td.utfpr.edu.br/locadora-war/ws/locacao/%s", id);
            URL url = new URL(endPoint);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("content-type", "application/json");
            con.connect();
            Log.d("ResponseCode", "Http " + con.getResponseCode());
            if(con.getResponseCode() != 204) {
                Toast toast = Toast.makeText(this, "Houve algum erro ao deletar", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        } catch (Throwable t) {
            Toast toast = Toast.makeText(this, "Houve algum erro ao deletar", Toast.LENGTH_SHORT);
            toast.show();
            t.printStackTrace();
            return false;
        }
        return true;
    }

}