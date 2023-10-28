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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prova_2_dispositivos_moveis.MainActivity;
import com.example.prova_2_dispositivos_moveis.R;

import java.util.ArrayList;

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

    @Override
    public void onActivityResult(int code,
                                 int result, Intent data) {
        super.onActivityResult(code, result, data);

        if (code == MainActivity.CADASTRO_VEICULO_SCREEN && result == RESULT_OK) {
            getVeiculos(new View(getApplicationContext()));
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
        setLanguage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
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

    private void setLanguage() {
        TextView consulta_veiculos = findViewById(R.id.titulo_consulta_veiculo);
        EditText placa = findViewById(R.id.placa_consulta);
        Button conulta_por_placa = findViewById(R.id.consulta_por_placa);
        Button conulta_todos = findViewById(R.id.consulta_todos);

       if(MainActivity.LANGAGUE.equals("PT")) {
           consulta_veiculos.setText(R.string.consulta_veiculo_pt);
           placa.setHint(R.string.placa_pt);
           conulta_por_placa.setText(R.string.consultar_por_placa_pt);
           conulta_todos.setText(R.string.consultar_todos_pt);
       } else {
           consulta_veiculos.setText(R.string.consulta_veiculo_en);
           placa.setHint(R.string.placa_en);
           conulta_por_placa.setText(R.string.consultar_por_placa_en);
           conulta_todos.setText(R.string.consultar_todos_en);
       }
    }
}