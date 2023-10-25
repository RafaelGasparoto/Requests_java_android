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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.prova_2_dispositivos_moveis.MainActivity;
import com.example.prova_2_dispositivos_moveis.R;

import java.util.LinkedList;

public class ConsultaLocacao extends AppCompatActivity {
    public class MyReceiverLocacao extends BroadcastReceiver {
        public ServicoLocacao myServiceBinder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GET_LOCACAO")) {
                myServiceBinder.getLocacoes(lista, locacoes, adapter);
            } else if(intent.getAction().equals("GET_LOCACAO_ID")) {
                String id = intent.getStringExtra("ID");
                if(!id.isEmpty()) {
                    myServiceBinder.getLocacaoById(lista, locacoes, adapter, id);
                }
            }
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
    ConsultaLocacao.MyReceiverLocacao myReceiver = new ConsultaLocacao.MyReceiverLocacao();
    ArrayAdapter<Locacao> adapter;
    LinkedList<Locacao> locacoes;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_locacao);
        registerReceivers();
        doBindService();
        makeListView();
        listenSelectLocacao();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    private void registerReceivers() {
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCACAO"));
        registerReceiver(myReceiver,
                new IntentFilter("GET_LOCACAO_ID"));
    }

    private void listenSelectLocacao() {
        lista.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Intent it = new Intent(ConsultaLocacao.this, CadastroLocacao.class);
            it.putExtra("LOCACAO_TO_EDIT", locacoes.get(pos));
            startActivityForResult(it, MainActivity.CADASTRO_LOCACAO_SCREEN);
            return true;
        });
    }

    public void makeListView() {
        locacoes = new LinkedList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                locacoes);
        lista = findViewById(R.id.lista_locacao);
        lista.setAdapter(adapter);
    }


    public void doBindService() {
        Intent intent = new Intent(this, ServicoLocacao.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    public void getLocacoes(View v) {
        Intent intent = new Intent("GET_LOCACAO");
        sendBroadcast(intent);
    }

    public void getLocacaoById(View v) {
        EditText editTextCpf = findViewById(R.id.id_consulta);
        Intent intent = new Intent("GET_LOCACAO_ID");
        intent.putExtra("ID", editTextCpf.getText().toString());
        sendBroadcast(intent);
    }
}