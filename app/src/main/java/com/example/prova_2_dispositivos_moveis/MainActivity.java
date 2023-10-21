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

//    public class MyReceiver extends BroadcastReceiver {
//        public ServicoMarca myServiceBinder;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("NUMERO_GERADO")) {
//                myServiceBinder.getMarca(lista, marcas, adapter);
//            }
//        }
//    }
//    public ServiceConnection myConnection = new ServiceConnection() {
//        public void onServiceConnected(ComponentName className, IBinder binder) {
//            myReceiver.myServiceBinder = ((ServicoMarca.ServicoBinder) binder).getService();
//            Log.d("ServiceConnection", "connected");
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            Log.d("ServiceConnection", "disconnected");
//            myReceiver.myServiceBinder = null;
//        }
//    };
//    MyReceiver myReceiver = new MyReceiver();
    ArrayAdapter<Marca> adapter;
    LinkedList<Marca> marcas;
    ListView lista;
    final static int CONSULTA_VEICULO_SCREEN = 1;
    final static int CADASTRO_VEICULO_SCREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        registerReceiver(myReceiver,
//                new IntentFilter("NUMERO_GERADO"));
//        this.doBindService();

        marcas = new LinkedList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                marcas);
        lista = findViewById(R.id.lista);
        lista.setAdapter( adapter );
    }

//    public void doBindService() {
//        Intent intent = new Intent(this, ServicoMarca.class);
//        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
//    }

    @Override
    public void onActivityResult(int code,
                                 int result, Intent data) {
        super.onActivityResult(code, result, data);

//        if (code == EDIT_ADD_SCREEN && result == RESULT_OK) {
//            Vehicle vehicle = (Vehicle) data.getSerializableExtra("vehicle");
//            if (positionEdit >= 0) {
//                vehicle_list.set(positionEdit, vehicle);
//                positionEdit = -1;
//            } else {
//                vehicle_list.add(vehicle);
//                backup_vehicle_list.add(vehicle);
//            }
//            customAdapter.notifyDataSetChanged();
//        }
    }

    public void iniciar(View v) {
        Intent intent = new Intent("NUMERO_GERADO");
        sendBroadcast(intent);
    }

    public void goToConsultaVeiculo(View v) {
        Intent intent = new Intent(this, ConsultaVeiculo.class);
        startActivityForResult(intent, CONSULTA_VEICULO_SCREEN);
    }

    public void goToCadastroVeiculo(View v) {
        Intent intent = new Intent(this, CadastroVeiculo.class);
        startActivityForResult(intent, CADASTRO_VEICULO_SCREEN);
    }
}