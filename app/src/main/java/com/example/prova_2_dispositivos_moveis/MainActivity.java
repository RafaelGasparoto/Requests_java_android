package com.example.prova_2_dispositivos_moveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.example.prova_2_dispositivos_moveis.locacao.CadastroLocacao;
import com.example.prova_2_dispositivos_moveis.locacao.ConsultaLocacao;
import com.example.prova_2_dispositivos_moveis.locador.CadastroLocador;
import com.example.prova_2_dispositivos_moveis.locador.ConsultaLocador;
import com.example.prova_2_dispositivos_moveis.veiculo.CadastroVeiculo;
import com.example.prova_2_dispositivos_moveis.veiculo.ConsultaVeiculo;

public class MainActivity extends AppCompatActivity {
    final static int CONSULTA_VEICULO_SCREEN = 1;
    public final static int CADASTRO_VEICULO_SCREEN = 2;
    final static int CONSULTA_LOCADOR_SCREEN = 3;
    public final static int CADASTRO_LOCADOR_SCREEN = 4;
    public final static int CADASTRO_LOCACAO_SCREEN = 5;
    public final static int CONSULTA_LOCACAO_SCREEN = 6;

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

    public void goToCadastroLocacao(View v) {
        Intent intent = new Intent(this, CadastroLocacao.class);
        startActivityForResult(intent, CADASTRO_LOCACAO_SCREEN);
    }

    public void goToConsultaLocacao(View v) {
        Intent intent = new Intent(this, ConsultaLocacao.class);
        startActivityForResult(intent, CONSULTA_LOCACAO_SCREEN);
    }
}