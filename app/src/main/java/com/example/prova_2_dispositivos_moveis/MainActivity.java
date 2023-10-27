package com.example.prova_2_dispositivos_moveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    public static String LANGAGUE = "PT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setLanguage();
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

    private void setLanguage() {
        TextView veiculos = findViewById(R.id.veiculos);
        TextView locador = findViewById(R.id.locador);
        TextView locacao = findViewById(R.id.locacao);
        Button cadastro_veiculo = findViewById(R.id.cadastro_veiculo);
        Button consulta_veiculo = findViewById(R.id.consulta_veiculo);
        Button cadastro_locador = findViewById(R.id.cadastro_locador);
        Button consulta_locador = findViewById(R.id.consulta_locador);
        Button cadastro_locacao = findViewById(R.id.cadastro_locacao);
        Button consulta_locacao = findViewById(R.id.consulta_locacao);
        Button idioma = findViewById(R.id.idioma);
        if (LANGAGUE.equals("PT")) {
            veiculos.setText(R.string.veiculo_pt);
            locador.setText(R.string.locador_pt);
            locacao.setText(R.string.locacao_pt);
            cadastro_veiculo.setText(R.string.cadastro_veiculo_pt);
            consulta_veiculo.setText(R.string.consulta_veiculo_pt);
            cadastro_locador.setText(R.string.cadastro_locador_pt);
            consulta_locador.setText(R.string.consulta_locador_pt);
            consulta_locacao.setText(R.string.consulta_locacao_pt);
            cadastro_locacao.setText(R.string.cadastro_locacao_pt);
            idioma.setText(R.string.idioma_pt);
        } else {
            veiculos.setText(R.string.veiculo_en);
            locador.setText(R.string.locador_en);
            locacao.setText(R.string.locacao_en);
            cadastro_veiculo.setText(R.string.cadastro_veiculo_en);
            consulta_veiculo.setText(R.string.consulta_veiculo_en);
            cadastro_locador.setText(R.string.cadastro_locador_en);
            consulta_locador.setText(R.string.consulta_locador_en);
            consulta_locacao.setText(R.string.consulta_locacao_en);
            cadastro_locacao.setText(R.string.cadastro_locacao_en);
            idioma.setText(R.string.idioma_en);
        }
    }

    public void changeLanguage(View v) {
        if (LANGAGUE.equals("PT")) {
            LANGAGUE = "EN";
        } else {
            LANGAGUE = "PT";
        }
        setLanguage();
    }
}