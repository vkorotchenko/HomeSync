package vadim.homesync.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Pair;

import java.util.Objects;

import vadim.homesync.settings.SettingsManager;

public class ConnectionUtils {

    public static Pair<CharSequence, CharSequence> getConnection(Context context) {
        if (isConnectedToHomeWiFi(context)) {
            return new Pair<>(SettingsManager.getAddress(context), SettingsManager.getPort(context));
        }
        return new Pair<>(SettingsManager.getExternalIp(context), SettingsManager.getExternalPort(context));
    }

    private static Boolean hasWiFi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = Objects.requireNonNull(connectivity).getAllNetworks();
        for (Network network : networks) {
            NetworkCapabilities capabilities = connectivity.getNetworkCapabilities(network);
            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public static boolean isConnectedToHomeWiFi(Context context) {
        if (hasWiFi(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            String home_network = SettingsManager.getHomeSsid(context).toString();
            if (wifiInfo.getSSID().replaceAll("\"", "").equals(home_network)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}