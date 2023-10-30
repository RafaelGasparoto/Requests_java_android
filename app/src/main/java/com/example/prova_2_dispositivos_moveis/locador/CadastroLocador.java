package com.example.prova_2_dispositivos_moveis.locador;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.prova_2_dispositivos_moveis.MainActivity;
import com.example.prova_2_dispositivos_moveis.R;
import com.example.prova_2_dispositivos_moveis.locacao.CadastroLocacao;

public class CadastroLocador extends AppCompatActivity {
    Locador locador;

    Cep cepinfo;
    EditText nome, cpf, telefone, email, cep, cidade, logradouro, numero, complemento, uf;

    public class MyReceiverLocador extends BroadcastReceiver {
        public ServicoLocador myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("POST_LOCADOR")) {
                if (myServiceBinder.postLocador(locador)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Locador cadastrado com sucesso", Toast.LENGTH_SHORT);
                    toast.show();
                    CadastroLocador.this.setResult(RESULT_OK);
                    finish();
                }
                locador = null;
            } else if (intent.getAction().equals("PUT_LOCADOR")) {
                if (myServiceBinder.putLocador(locador)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Locador atualizado com sucesso", Toast.LENGTH_SHORT);
                    toast.show();
                    CadastroLocador.this.setResult(RESULT_OK);
                    finish();
                }
            } else if (intent.getAction().equals("GET_LOCADOR_CEP")) {
                cepinfo = myServiceBinder.getLocadorCep(cep.getText().toString());
                if (cepinfo != null) {
                    cidade.setText(cepinfo.localidade);
                    logradouro.setText(cepinfo.logradouro);
                    complemento.setText(cepinfo.complemento);
                    uf.setText(cepinfo.uf);
                    cepinfo = null;
                }
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
        registerReceivers();
        getEditText();
        doBindService();
        if (locador != null)
            setLocadorToEdit();

        setLanguage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    public void registerReceivers() {
        registerReceiver(myReceiver,
                new IntentFilter("POST_LOCADOR"));
        registerReceiver(myReceiver,
                new IntentFilter("PUT_LOCADOR"));
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCADOR_CEP"));
    }

    public void setLocadorToEdit() {
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
        } else {
            Toast toast = Toast.makeText(this, "Preencha todos os campos necessários", Toast.LENGTH_SHORT);
            toast.show();
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

    public void searchCEP(View v) {
        Intent intent = new Intent("GET_LOCADOR_CEP");
        sendBroadcast(intent);
    }

    private void setLanguage() {
        Button cadastrar = findViewById(R.id.cadastrar);
        Button buscar_cep = findViewById(R.id.buscar_cep);
        TextView cadastro_de_locador = findViewById(R.id.cadastro_de_locador);

        if (MainActivity.LANGAGUE.equals("PT")) {
            nome.setHint(R.string.nome_pt);
            telefone.setHint(R.string.telefone_pt);
            cidade.setHint(R.string.cidade_pt);
            logradouro.setHint(R.string.logradouro_pt);
            numero.setHint(R.string.numero_pt);
            complemento.setHint(R.string.complemento_pt);
            if (locador != null) {
                cadastrar.setText(R.string.atualizar_pt);
            } else {
                cadastrar.setText(R.string.cadastrar_pt);
            }            buscar_cep.setText(R.string.buscar_cep_pt);
            cadastro_de_locador.setText(R.string.cadastro_locador_pt);
        } else {
            nome.setHint(R.string.nome_en);
            telefone.setHint(R.string.telefone_en);
            cidade.setHint(R.string.cidade_en);
            logradouro.setHint(R.string.logradouro_en);
            numero.setHint(R.string.numero_en);
            complemento.setHint(R.string.complemento_en);
            if (locador != null) {
                cadastrar.setText(R.string.atualizar_en);
            } else {
                cadastrar.setText(R.string.cadastrar_en);
            }
            buscar_cep.setText(R.string.buscar_cep_en);
            cadastro_de_locador.setText(R.string.cadastro_locador_en);
        }
    }
}