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
import android.widget.Button;
import android.widget.EditText;

public class CadastroLocador extends AppCompatActivity {
    Locador locador;
    EditText nome, cpf, telefone, email, cep, cidade, logradouro, numero, complemento, uf;

    public class MyReceiverLocador extends BroadcastReceiver {
        public ServicoLocador myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("POST_LOCADOR")) {
                myServiceBinder.postLocador(locador);
            } else if (intent.getAction().equals("PUT_LOCADOR")) {
                myServiceBinder.putLocador(locador);
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
    CadastroLocador.MyReceiverLocador myReceiver = new CadastroLocador.MyReceiverLocador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locador);
        locador = (Locador) getIntent().getSerializableExtra("LOCADOR_TO_EDIT");
        registerReceiver(myReceiver,
                new IntentFilter("POST_LOCADOR"));
        registerReceiver(myReceiver,
                new IntentFilter("PUT_LOCADOR"));

        getEditText();
        doBindService();
        if (locador != null)
            setVeiculoToEdit();
    }
    public void setVeiculoToEdit() {
        nome.setText(locador.getNome());
        cpf.setText(locador.getCpf());
        telefone.setText(locador.getTelefone());
        email.setText(locador.getEmail());
        cep.setText(locador.getCep());
        cidade.setText(locador.getLocalidade());
        logradouro.setText(locador.getLogradouro());
        numero.setText(locador.getNumero());
        complemento.setText(locador.getComplemento());
        uf.setText(locador.getUf());
        cpf.setEnabled(false);
        Button button = findViewById(R.id.botão_cadastro_locador);
        button.setText("Atualizar cadastro");
    }
    private void getEditText() {
        nome = findViewById(R.id.nome);
        cpf = findViewById(R.id.cpf);
        telefone = findViewById(R.id.telefone);
        email = findViewById(R.id.email);
        cep = findViewById(R.id.cep);
        cidade = findViewById(R.id.cidade);
        logradouro = findViewById(R.id.logradouro);
        numero = findViewById(R.id.numero);
        complemento = findViewById(R.id.complemento);
        uf = findViewById(R.id.uf);
    }

    private Locador buildLocador() {
        return new Locador(
                nome.getText().toString(),
                cep.getText().toString(),
                complemento.getText().toString(),
                cpf.getText().toString(),
                email.getText().toString(),
                cidade.getText().toString(),
                logradouro.getText().toString(),
                numero.getText().toString(),
                telefone.getText().toString(),
                uf.getText().toString()
        );
    }

    public void cadastrarLocador(View v) {
        if (!checar_campo_vazio()) {
            if (locador == null) {
                locador = buildLocador();
                Intent intent = new Intent("POST_LOCADOR");
                sendBroadcast(intent);
            } else {
                locador = buildLocador();
                Intent intent = new Intent("PUT_LOCADOR");
                sendBroadcast(intent);
            }
        }
    }

    private boolean checar_campo_vazio() {
        return nome.getText().toString().isEmpty() || cpf.getText().toString().isEmpty() || telefone.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || cep.getText().toString().isEmpty() || cidade.getText().toString().isEmpty() || logradouro.getText().toString().isEmpty() || numero.getText().toString().isEmpty()
                || complemento.getText().toString().isEmpty() || uf.getText().toString().isEmpty();
    }

    public void doBindService() {
        Intent intent = new Intent(this, ServicoLocador.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }
}