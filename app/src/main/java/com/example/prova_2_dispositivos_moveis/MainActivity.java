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
    final static int CONSULTA_VEICULO_SCREEN = 1;
    final static int CADASTRO_VEICULO_SCREEN = 2;
    final static int CONSULTA_LOCADOR_SCREEN = 3;
    final static int CADASTRO_LOCADOR_SCREEN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public void onActivityResult(int code, int result, Intent data) {
        super.onActivityResult(code, result, data);
    }

    public void goToConsultaVeiculo(View v) {
        Intent intent = new Intent(this, ConsultaVeiculo.class);
        startActivityForResult(intent, CONSULTA_VEICULO_SCREEN);
    }

    public void goToCadastroVeiculo(View v) {
        Intent intent = new Intent(this, CadastroVeiculo.class);
        startActivityForResult(intent, CADASTRO_VEICULO_SCREEN);
    }

    public void goToCadastroLocador(View v) {
        Intent intent = new Intent(this, CadastroLocador.class);
        startActivityForResult(intent, CADASTRO_LOCADOR_SCREEN);
    }

    public void goToConsultaLocador(View v) {
        Intent intent = new Intent(this, ConsultaLocador.class);
        startActivityForResult(intent, CONSULTA_LOCADOR_SCREEN);
    }
}