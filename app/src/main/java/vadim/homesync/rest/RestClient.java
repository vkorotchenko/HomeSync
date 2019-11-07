package vadim.homesync.rest;

import android.content.Context;
import android.util.Pair;

import vadim.homesync.common.Message;
import vadim.homesync.settings.SettingsManager;
import vadim.homesync.util.ConnectionUtils;
import vadim.homesync.util.HttpUtils;

public class RestClient {
    private Context context;

    public RestClient(Context context) {
        this.context = context;
    }

    public void sentElectronicsMsg(Message msg) {
        try {
            String uri = getBaseURIString() + "/Electronics/" + msg.getKey() + "/" + SettingsManager.getMsgType(context);
            HttpUtils.sendMsg(uri, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBaseURIString() {
        Pair connection = ConnectionUtils.getConnection(context);
        String address = (String) connection.first;
        String port = (String) connection.second;

        return "http://" + address + ":" + port + "/api/rest/Remote";
    }
} 