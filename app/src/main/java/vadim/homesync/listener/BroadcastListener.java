package vadim.homesync.listener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import vadim.homesync.R;
import vadim.homesync.common.Action;
import vadim.homesync.receivers.ActionReceiver;
import vadim.homesync.receivers.CancelReceiver;
import vadim.homesync.services.NetworkService;
import vadim.homesync.settings.SettingsManager;
import vadim.homesync.util.ConnectionUtils;

import static vadim.homesync.common.Action.ON_ARRIVE;


public class BroadcastListener extends BroadcastReceiver {

    private final static String TAG = "BroadcastListener";
    private static String lastSsid;


    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult, intent, context);
        asyncTask.execute();
    }

    private static class Task extends AsyncTask<String, Integer, String> {

        private final PendingResult pendingResult;
        private final Intent intent;
        private final Context context;
        NotificationManagerCompat notificationManager;

        private Task(PendingResult pendingResult, Intent intent, Context context) {
            this.pendingResult = pendingResult;
            this.intent = intent;
            this.context = context;

            notificationManager = NotificationManagerCompat.from(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            String currentSsid = ConnectionUtils.getCurrentSsid(context);

            if (ConnectionUtils.isConnectedToHomeWiFi(context) && lastSsid == null) {
                this.onArriveHome(context);

                lastSsid = SettingsManager.getHomeSsid(context).toString();
            } else if (!ConnectionUtils.hasWiFi(context)
                    && (SettingsManager.getHomeSsid(context)).equals(lastSsid)) {
                this.onDepartHome(context);
                lastSsid = null;
            } else if (currentSsid != null && !currentSsid.equals(lastSsid)) {
                lastSsid = currentSsid;
            }

            String log = "Action: " + intent.getAction() + "\n";
            Log.d(TAG, log);
            return log;
        }

        private void onArriveHome(Context context) {
            disaplyNotification(context, ON_ARRIVE);
        }

        private void onDepartHome(Context context) {
            disaplyNotification(context, Action.ON_DEPART);
        }

        private void disaplyNotification(Context context, Action action) {
            Intent intentAction = new Intent(context, ActionReceiver.class);
            intentAction.putExtra("action", action.getAction());
            PendingIntent pIntentAction = PendingIntent.getBroadcast(context, 0, intentAction, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent intentCancel = new Intent(context, CancelReceiver.class);
            intentCancel.putExtra("id", action.getId());
            PendingIntent pIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder builder = getNotificationBuilder(context,
                    NetworkService.CHANNEL_ID,
                    NotificationManagerCompat.IMPORTANCE_HIGH);
            builder.setContentTitle(action.getTitle())
                    .setContentText(action.getText())
                    .addAction(R.drawable.ok, action.getButton(), pIntentAction)
                    .addAction(R.drawable.cancel, "Cancel", pIntentCancel)
                    .setSmallIcon(R.drawable.come_go)
                    .setOngoing(true)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setWhen(0)
                    .setAutoCancel(true);


            notificationManager.notify(action.getId(), builder.build());

        }


        public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
            NotificationCompat.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                prepareChannel(context, channelId, importance);
                builder = new NotificationCompat.Builder(context, channelId);
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            return builder;
        }

        @TargetApi(26)
        private static void prepareChannel(Context context, String id, int importance) {
            final String appName = context.getString(R.string.app_name);
            String description = "broadcastListener";
            final NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

            if (nm != null) {
                NotificationChannel nChannel = nm.getNotificationChannel(id);

                if (nChannel == null) {
                    nChannel = new NotificationChannel(id, appName, importance);
                    nChannel.setDescription(description);
                    nm.createNotificationChannel(nChannel);
                }
            }
        }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                pendingResult.finish();
            }
        }
    }
