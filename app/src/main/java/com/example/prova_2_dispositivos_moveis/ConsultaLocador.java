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

public class ConsultaLocador extends AppCompatActivity {
    public class MyReceiverLocador extends BroadcastReceiver {
        public ServicoLocador myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GET_LOCADORES")) {
                myServiceBinder.getLocadores(lista, locadores, adapter);
            }
        }
    }

    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myReceiver.myServiceBinder = ((ServicoLocador.LocadorBinder) binder).getService();
            Log.d("ServiceConnection", "connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection", "disconnected");
            myReceiver.myServiceBinder = null;
        }
    };
    ConsultaLocador.MyReceiverLocador myReceiver = new ConsultaLocador.MyReceiverLocador();
    ArrayAdapter<Locador> adapter;
    LinkedList<Locador> locadores;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_locador);
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCADORES"));
        this.doBindService();
        this.makeListView();

    }

    public void makeListView() {
        locadores = new LinkedList<>();
        locadores.add(new Locador("a", "a", "a", "a", "a", "a", "s", "d", "df", "g"
        ));
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                locadores);
        lista = findViewById(R.id.lista_locadores);
        lista.setAdapter(adapter);
    }


    public void doBindService() {
        Intent intent = new Intent(this, ServicoLocador.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    public void click(View v) {
        Intent intent = new Intent("GET_LOCADORES");
        sendBroadcast(intent);
    }
}