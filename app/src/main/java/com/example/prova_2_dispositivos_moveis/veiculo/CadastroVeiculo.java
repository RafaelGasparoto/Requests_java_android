package com.example.prova_2_dispositivos_moveis.veiculo;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prova_2_dispositivos_moveis.R;

public class CadastroVeiculo extends AppCompatActivity {
    public class MyReceiverVeiculos extends BroadcastReceiver {
        public ServicoVeiculo myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("POST_VEICULO")) {
                if (myServiceBinder.postVeiculo(veiculo)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Veículo cadastrado com sucesso", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
                veiculo = null;
            } else if (intent.getAction().equals("PUT_VEICULO")) {
                if (myServiceBinder.putVeiculo(veiculo)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Veículo atualizado com sucesso", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
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
    CadastroVeiculo.MyReceiverVeiculos myReceiver = new CadastroVeiculo.MyReceiverVeiculos();
    Veiculo veiculo;
    EditText idModelo, ano, cor, placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        veiculo = (Veiculo) getIntent().getSerializableExtra("VEICULO_TO_EDIT");
        setContentView(R.layout.activity_cadastro_veiculo);
        registerReceivers();
        doBindService();
        getEditText();
        if (veiculo != null) {
            setVeiculoToEdit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void registerReceivers() {
        registerReceiver(myReceiver,
                new IntentFilter("POST_VEICULO"));
        registerReceiver(myReceiver,
                new IntentFilter("PUT_VEICULO"));
    }

    public void setVeiculoToEdit() {
        idModelo.setText(String.valueOf(veiculo.getIdModelo()));
        ano.setText(String.valueOf(veiculo.getAno()));
        cor.setText(veiculo.getCor());
        placa.setText(veiculo.getPlaca());
        placa.setEnabled(false);
        Button button = findViewById(R.id.botão_cadastro);
        button.setText("Atualizar cadastro");
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
        return placa.getText().toString().isEmpty() || ano.getText().toString().isEmpty() || cor.getText().toString().isEmpty() || idModelo.getText().toString().isEmpty();
    }

    public void cadastrarVeiculo(View v) {
        if (!checar_campo_vazio()) {
            if (veiculo == null) {
                veiculo = buildVeiculo();
                Intent intent = new Intent("POST_VEICULO");
                sendBroadcast(intent);
            } else {
                veiculo = buildVeiculo();
                Intent intent = new Intent("PUT_VEICULO");
                sendBroadcast(intent);
            }
        }
    }

    public void doBindService() {
        Intent intent = new Intent(this, ServicoVeiculo.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }
}