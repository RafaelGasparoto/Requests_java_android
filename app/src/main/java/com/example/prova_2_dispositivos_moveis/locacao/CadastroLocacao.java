package com.example.prova_2_dispositivos_moveis.locacao;

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
import com.example.prova_2_dispositivos_moveis.locador.CadastroLocador;
import com.example.prova_2_dispositivos_moveis.locador.Locador;
import com.example.prova_2_dispositivos_moveis.locador.ServicoLocador;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CadastroLocacao extends AppCompatActivity {
    Locacao locacao;

    EditText cpf, fim, inicio, id, placa, valor;

    public class MyReceiverLocacao extends BroadcastReceiver {
        public ServicoLocacao myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("POST_LOCACAO")) {
                if(myServiceBinder.postLocacao(locacao)) {
                    finish();
                }
            } else if (intent.getAction().equals("PUT_LOCACAO")) {
                if(myServiceBinder.putLocacao(locacao)) {
                    finish();
                }
            }
            locacao = null;
        }
    }

    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myReceiver.myServiceBinder = ((ServicoLocacao.LocacaoBinder) binder).getService();
            Log.d("ServiceConnection", "connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection", "disconnected");
            myReceiver.myServiceBinder = null;
        }
    };
    CadastroLocacao.MyReceiverLocacao myReceiver = new CadastroLocacao.MyReceiverLocacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locacao);
        locacao = (Locacao) getIntent().getSerializableExtra("LOCACAO_TO_EDIT");
        registerReceivers();
        listenDatePicker();
        getEditText();
        doBindService();
        if (locacao != null)
            setLocacaoToEdit();


    }

    private void listenDatePicker() {
        MaterialButton buttonPickDateInicio, buttonPickDateFim;

        buttonPickDateInicio = findViewById(R.id.pick_data_inicio);
        buttonPickDateInicio.setOnClickListener(view -> {
            showDatePicckerDialog("Inicio");
        });

        buttonPickDateFim = findViewById(R.id.pick_data_fim);
        buttonPickDateFim.setOnClickListener(view -> {
            showDatePicckerDialog("Fim");
        });
    }

    private void showDatePicckerDialog(String date) {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Data início").build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {


            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate  = format.format(calendar.getTime());
                if(date.equals("Inicio")) {
                    inicio.setText("" + formattedDate);
                } else if(date.equals("Fim")) {
                    fim.setText("" + formattedDate);
                }
            }
        });
        materialDatePicker.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    public void registerReceivers() {
        registerReceiver(myReceiver,
                new IntentFilter("POST_LOCACAO"));
        registerReceiver(myReceiver,
                new IntentFilter("PUT_LOCACAO"));
    }

    public void setLocacaoToEdit() {
        cpf.setText(locacao.getCpf());
        fim.setText(locacao.getFim());
        inicio.setText(locacao.getInicio());
        id.setText(Integer.toString(locacao.getId()));
        placa.setText(locacao.getPlaca());
        valor.setText(Double.toString(locacao.getValor()));
        Button button = findViewById(R.id.botão_cadastro_locador);
        button.setText("Atualizar cadastro");
    }

    private void getEditText() {
        cpf = findViewById(R.id.locacao_cpf);
        id = findViewById(R.id.locacao_id);
        valor = findViewById(R.id.valor);
        fim = findViewById(R.id.data_final);
        inicio = findViewById(R.id.data_inicio);
        placa = findViewById(R.id.locacao_placa);
    }

    private Locacao buildLocacao() {
        String iD = id.getText().toString();
        return new Locacao(
                iD.isEmpty() ? null : Integer.parseInt(iD),
                Double.parseDouble(valor.getText().toString()),
                cpf.getText().toString(),
                inicio.getText().toString(),
                fim.getText().toString(),
                placa.getText().toString()
        );
    }

    public void cadastrarLocacao(View v) {
        if (!checar_campo_vazio()) {
            if (locacao == null) {
                locacao = buildLocacao();
                Intent intent = new Intent("POST_LOCACAO");
                sendBroadcast(intent);
            } else {
                locacao = buildLocacao();
                Intent intent = new Intent("PUT_LOCACAO");
                sendBroadcast(intent);
            }
        } else {
            Toast toast = Toast.makeText(this, "Preencha todos os campos necessários", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean checar_campo_vazio() {
        return cpf.getText().toString().isEmpty() || fim.getText().toString().isEmpty() || inicio.getText().toString().isEmpty()
                || placa.getText().toString().isEmpty() || valor.getText().toString().isEmpty();
    }

    public void doBindService() {
        Intent intent = new Intent(this, ServicoLocacao.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }
}