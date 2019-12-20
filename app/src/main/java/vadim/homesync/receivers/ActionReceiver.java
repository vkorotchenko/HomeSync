package vadim.homesync.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import vadim.homesync.common.Action;
import vadim.homesync.common.Message;
import vadim.homesync.util.RestUtils;

public class ActionReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getStringExtra("action");
        if (action.equals(Action.ON_DEPART.getAction())) {
            onDepartHome(context, Action.ON_DEPART);
        } else if (action.equals(Action.ON_ARRIVE.getAction())) {
            onArriveHome(context, Action.ON_ARRIVE);
        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    private void onDepartHome(Context context, Action action) {
        RestUtils.sentElectronicsMsg(context, Message.BLINDS_DOWN);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(action.getId());

    }

    private void onArriveHome(Context context, Action action) {
        RestUtils.sentElectronicsMsg(context, Message.LIVING_ROOM_LIGHTS);
        RestUtils.sentElectronicsMsg(context, Message.KITCHEN_LIGHTS);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(action.getId());
    }
}
