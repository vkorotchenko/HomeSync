package vadim.homesync.util;

import android.content.Context;

import vadim.homesync.common.Message;
import vadim.homesync.rest.RestClient;

public class RestUtils {

    public static void sentElectronicsMsg(Context context, Message msg) {
        RestClient restClient = new RestClient(context);
        restClient.sentElectronicsMsg(msg);
    }
}
