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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prova_2_dispositivos_moveis.MainActivity;
import com.example.prova_2_dispositivos_moveis.R;

import java.util.LinkedList;

public class ConsultaLocador extends AppCompatActivity {
    public class MyReceiverLocador extends BroadcastReceiver {
        public ServicoLocador myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GET_LOCADORES")) {
                myServiceBinder.getLocadores(lista, locadores, adapter);
            } else if (intent.getAction().equals("GET_LOCADOR_CPF")) {
                String cpf = intent.getStringExtra("CPF");
                if (!cpf.isEmpty()) {
                    myServiceBinder.getLocadorByCpf(lista, locadores, adapter, cpf);
                }
            }
        }
    }
    @Override
    public void onActivityResult(int code,
                                 int result, Intent data) {
        super.onActivityResult(code, result, data);

        if (code == MainActivity.CADASTRO_LOCADOR_SCREEN && result == RESULT_OK) {
            getLocadores(new View(getApplicationContext()));
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
        registerReceivers();
        doBindService();
        makeListView();
        listenSelectLocador();
        setLanguage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void registerReceivers() {
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCADORES"));
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCADOR_CPF"));
    }

    private void listenSelectLocador() {
        lista.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Intent it = new Intent(ConsultaLocador.this, CadastroLocador.class);
            it.putExtra("LOCADOR_TO_EDIT", locadores.get(pos));
            startActivityForResult(it, MainActivity.CADASTRO_LOCADOR_SCREEN);
            return true;
        });
    }

    public void makeListView() {
        locadores = new LinkedList<>();
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

    public void getLocadores(View v) {
        Intent intent = new Intent("GET_LOCADORES");
        sendBroadcast(intent);
    }

    public void getLocadorByCpf(View v) {
        EditText editTextCpf = findViewById(R.id.cpf_consulta);
        Intent intent = new Intent("GET_LOCADOR_CPF");
        intent.putExtra("CPF", editTextCpf.getText().toString());
        sendBroadcast(intent);
    }

    private void setLanguage() {
        TextView consulta_locador = findViewById(R.id.consulta_locador);
        Button consulta_por_cpf = findViewById(R.id.consulta_por_cpf);
        Button consulta_todos = findViewById(R.id.consulta_todos);
        if(MainActivity.LANGAGUE.equals("PT")) {
            consulta_locador.setText(R.string.consulta_locador_pt);
            consulta_por_cpf.setText(R.string.consultar_por_cpf_pt);
            consulta_todos.setText(R.string.consultar_todos_pt);
        } else {
            consulta_locador.setText(R.string.consulta_locador_en);
            consulta_por_cpf.setText(R.string.consultar_por_cpf_en);
            consulta_todos.setText(R.string.consultar_todos_en);
        }
    }
}