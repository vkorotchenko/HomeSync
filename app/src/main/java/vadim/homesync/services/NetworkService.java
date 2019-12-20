package vadim.homesync.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import vadim.homesync.MainActivity;
import vadim.homesync.R;
import vadim.homesync.listener.BroadcastListener;
import vadim.homesync.settings.SettingsManager;

public class NetworkService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    BroadcastListener broadcastListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (SettingsManager.getExperimentalAi(this)) {
            this.initBroadcastLIstener();
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("HomeSync Network Listener")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_network_check_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);



        if (SettingsManager.getExperimentalAi(this)) {
            this.initBroadcastLIstener();
        }


        return START_NOT_STICKY;
    }

    private void initBroadcastLIstener() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        broadcastListener = new BroadcastListener();
        this.registerReceiver(broadcastListener, filter);

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastListener);
        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
