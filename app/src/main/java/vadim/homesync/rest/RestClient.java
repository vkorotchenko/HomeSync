package vadim.homesync.rest;

import android.app.Activity;
import android.content.Context;

import vadim.homesync.settings.SettingsManager;
import vadim.homesync.util.HttpUtils;

public class RestClient {
    private Context context;

    public RestClient(Context context) {
        this.context = context;
    }

    public void sentElectronicsMsg(String msg) {
        try {
            String uri = getBaseURIString() + "/Electronics/" + msg + "/" + SettingsManager.getMsgType((Activity) context);
            HttpUtils.sendMsg(uri,context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getBaseURIString() {
        String address = (String) SettingsManager.getAddress((Activity) this.context);
        String port = (String) SettingsManager.getPort((Activity) this.context);


        return "http://" + address + ":" + port + "/api/rest/Remote";
    }




} 