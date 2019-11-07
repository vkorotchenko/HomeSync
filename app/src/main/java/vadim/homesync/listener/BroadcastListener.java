package vadim.homesync.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import vadim.homesync.common.Message;
import vadim.homesync.util.ConnectionUtils;
import vadim.homesync.util.HttpUtils;
import vadim.homesync.util.RestUtils;


public class BroadcastListener extends BroadcastReceiver {

    private final static String TAG = "BroadcastListener";

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

        private Task(PendingResult pendingResult, Intent intent, Context context) {
            this.pendingResult = pendingResult;
            this.intent = intent;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            if (ConnectionUtils.isConnectedToHomeWiFi(context)) {
                HttpUtils.setExternal(context);

                this.onArriveHome();
            } else {
                this.onDepartHome();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n");
            String log = sb.toString();
            Log.d(TAG, log);
            return log;
        }

        private void onDepartHome() {
            RestUtils.sentElectronicsMsg(context, Message.BLINDS_DOWN);
        }

        private void onArriveHome() {
            RestUtils.sentElectronicsMsg(context, Message.LIVING_ROOM_LIGHTS);
            RestUtils.sentElectronicsMsg(context, Message.KITCHEN_LIGHTS);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pendingResult.finish();
        }
    }
}
