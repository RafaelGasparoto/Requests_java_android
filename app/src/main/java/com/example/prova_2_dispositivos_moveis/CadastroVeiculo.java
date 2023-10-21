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
import android.widget.EditText;
import android.widget.ListView;

import java.util.LinkedList;

public class CadastroVeiculo extends AppCompatActivity {
    public class MyReceiverVeiculos extends BroadcastReceiver {
        public ServicoVeiculo myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("POST_VEICULO")) {
                myServiceBinder.postVeiculo(veiculo);
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
    CadastroVeiculo.MyReceiverVeiculos myReceiver = new CadastroVeiculo.MyReceiverVeiculos();
    Veiculo veiculo;

    EditText idModelo;
    EditText ano;
    EditText cor;
    EditText placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);
        registerReceiver(myReceiver,
                new IntentFilter("POST_VEICULO"));
        doBindService();
        getEditText();
    }

    private void getEditText() {
        idModelo = findViewById(R.id.id_modelo);
        ano = findViewById(R.id.ano);
        cor = findViewById(R.id.cor);
        placa = findViewById(R.id.placa);
    }

    private Veiculo buildVeiculo() {
         return new Veiculo(
                Integer.parseInt(String.valueOf(ano.getText())),
                Integer.parseInt(String.valueOf(idModelo.getText())),
                cor.getText().toString(),
                placa.getText().toString()
        );
    }

    private boolean checar_campo_vazio() {
        return placa.getText().toString().isEmpty() || ano.getText().toString().isEmpty() || cor.getText().toString().isEmpty()|| idModelo.getText().toString().isEmpty();
    }

    public void cadastrarVeiculo(View v) {
        if (!checar_campo_vazio()) {
            veiculo = buildVeiculo();
            Intent intent = new Intent("POST_VEICULO");
            sendBroadcast(intent);
        }
    }

    public void doBindService() {
        Intent intent = new Intent(this, ServicoVeiculo.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }
}