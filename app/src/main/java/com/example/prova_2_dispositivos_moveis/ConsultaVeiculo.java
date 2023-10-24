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
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class ConsultaVeiculo extends AppCompatActivity {

    public class MyReceiverVeiculos extends BroadcastReceiver {
        public ServicoVeiculo myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GET_VEICULOS")) {
                myServiceBinder.getVeiculos(lista, veiculos, adapter);
            } else if (intent.getAction().equals("GET_VEICULO_PLACA")) {
                String placa = intent.getStringExtra("PLACA");
                if (!placa.isEmpty()) {
                    myServiceBinder.getVeiculoByPlaca(lista, veiculos, adapter, placa);
                }
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
    ArrayList<Veiculo> veiculos;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_veiculo);
        registerReceiver(myReceiver,
                new IntentFilter("GET_VEICULOS"));
        registerReceiver(myReceiver,
                new IntentFilter("GET_VEICULO_PLACA"));
        this.doBindService();
        this.makeListView();
        this.listenSelectVeiculo();

    }

    private void listenSelectVeiculo() {
        lista.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Intent it = new Intent(ConsultaVeiculo.this, CadastroVeiculo.class);
            it.putExtra("VEICULO_TO_EDIT", veiculos.get(pos));
            startActivityForResult(it, MainActivity.CADASTRO_VEICULO_SCREEN);
            return true;
        });
    }

    public void makeListView() {
        veiculos = new ArrayList<>();
        veiculos.add(new Veiculo(
                10, 1, "PRETA", "AAA"
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

    public void getVeiculos(View v) {
        Intent intent = new Intent("GET_VEICULOS");
        sendBroadcast(intent);
    }

    public void getVeiculoByPlaca(View v) {
        EditText editTextCpf = findViewById(R.id.placa_consulta);
        Intent intent = new Intent("GET_VEICULO_PLACA");
        intent.putExtra("PLACA", editTextCpf.getText().toString());
        sendBroadcast(intent);
    }
}