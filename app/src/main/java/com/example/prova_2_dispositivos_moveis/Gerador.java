package com.example.prova_2_dispositivos_moveis;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class Gerador extends Service {

    public Gerador() {  }

    class MeuBinder extends Binder {
        public Gerador getService() {
            return Gerador.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MeuBinder();
    }

    boolean ativo = false;

    public void iniciar() {
        if (ativo) return;
        new Thread() {
            public void run() {
                Random rand = new Random();
                ativo = true;
                while(ativo) {
                    int val = rand.nextInt(10000);
                    Intent it = new Intent("NUMERO_GERADO");
                    it.putExtra("novo_numero", val);
                    sendBroadcast(it);
                    try { Thread.sleep(1000); } catch(Throwable t) { }
                }
            }
        }.start();
    }

    public void parar() {
        ativo = false;
    }
}
