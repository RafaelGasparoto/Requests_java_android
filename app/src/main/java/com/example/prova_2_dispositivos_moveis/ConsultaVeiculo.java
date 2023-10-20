package com.example.prova_2_dispositivos_moveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

public class ConsultaVeiculo extends AppCompatActivity {

    public class MyReceiverVeiculos extends BroadcastReceiver {
        public ServicoVeiculo myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GET_VEICULOS")) {
                myServiceBinder.getVeiculos(lista, veiculos, adapter);
            }
        }
    }

    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myReceiver.myServiceBinder = ((ServicoVeiculo.VeiculoBinder) binder).getService();
            Log.d("ServiceConnection", "connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection", "disconnected");
            myReceiver.myServiceBinder = null;
        }
    };
    MyReceiverVeiculos myReceiver = new MyReceiverVeiculos();
    ArrayAdapter<Veiculo> adapter;
    LinkedList<Veiculo> veiculos;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_veiculo);
        registerReceiver(myReceiver,
                new IntentFilter("GET_VEICULOS"));
        this.doBindService();
        this.makeListView();

    }

    public void makeListView() {
        veiculos = new LinkedList<>();
        veiculos.add(new Veiculo(
                10, 1, "PRETA", "AAA",
                new Modelo("d", "aaaaaa", 2, 333,
                        new Marca(
                                1, "aa"
                        ))
        ));
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                veiculos);
        lista = findViewById(R.id.lista_veiculos);
        lista.setAdapter(adapter);
    }


    public void doBindService() {
        Intent intent = new Intent(this, ServicoVeiculo.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    public void click(View v) {
        Intent intent = new Intent("GET_VEICULOS");
        sendBroadcast(intent);
    }
}