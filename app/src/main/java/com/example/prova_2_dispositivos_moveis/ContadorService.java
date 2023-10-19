package com.example.prova_2_dispositivos_moveis;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import java.util.Random;

public class ContadorService extends IntentService {

    public ContadorService() {
        super("ContadorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int qtde = intent.getIntExtra("QUANTIDADE",0);
            Random rand = new Random(System.currentTimeMillis());
            int nums[] = new int[qtde];
            for (int i = 0; i < qtde; i++) {
                int val = rand.nextInt(100000);
                Intent it = new Intent("NUMERO_GERADO");
                it.putExtra("novo_numero", val);
                sendBroadcast(it);
                nums[i] = val;
                try { Thread.sleep(1000); } catch (Exception ex) { }
            }
            Intent app = new Intent(getApplicationContext(), MainActivity.class);
            app.putExtra("numeros", nums);
            PendingIntent pendente = PendingIntent.getActivity(getApplicationContext(),
                    123, app,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder bld = new Notification.Builder(getApplicationContext());
            bld.setSmallIcon(android.R.drawable.btn_star);
            bld.setContentTitle("Geração terminada.");
            bld.setAutoCancel(true);
            bld.setContentIntent( pendente );
            bld.setContentText("Foram gerados "+qtde+" número aleatórios.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bld.setChannelId("CANAL_NOTAS");
            }
            NotificationManager mngr = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            mngr.notify(1234, bld.build());
        }
    }
}