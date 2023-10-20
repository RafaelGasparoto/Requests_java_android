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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    public class MyReceiver extends BroadcastReceiver {
        public ServicoMarca myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("NUMERO_GERADO")) {
                myServiceBinder.getMarca(lista, marcas, adapter);
            }
        }
    }
    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myReceiver.myServiceBinder = ((ServicoMarca.MeuBinder) binder).getService();
            Log.d("ServiceConnection", "connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection", "disconnected");
            myReceiver.myServiceBinder = null;
        }
    };

    MyReceiver myReceiver = new MyReceiver();
    ArrayAdapter<Marca> adapter;
    LinkedList<Marca> marcas;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        registerReceiver(myReceiver,
                new IntentFilter("NUMERO_GERADO"));
        this.doBindService();

        marcas = new LinkedList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                marcas);
        lista = findViewById(R.id.lista);
        lista.setAdapter( adapter );
    }

    public void doBindService() {
        Intent intent = new Intent(this, ServicoMarca.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    public void iniciar(View v) {
        Intent intent = new Intent("NUMERO_GERADO");
        intent.putExtra("QUANTIDADE", 10);
        sendBroadcast(intent);
    }
}