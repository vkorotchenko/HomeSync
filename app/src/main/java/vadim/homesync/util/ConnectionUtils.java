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

    private static final String UNKNOWN_SSID = "";

    public static Pair<CharSequence, CharSequence> getConnection(Context context) {
        if (isConnectedToHomeWiFi(context)) {
            return new Pair<>(SettingsManager.getAddress(context), SettingsManager.getPort(context));
        }
        return new Pair<>(SettingsManager.getExternalIp(context), SettingsManager.getExternalPort(context));
    }

    public static Boolean hasWiFi(Context context) {
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
        String home_network = SettingsManager.getHomeSsid(context).toString();
        return home_network.equals(getCurrentSsid(context));
    }

    public static String getCurrentSsid(Context context) {
        if (hasWiFi(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            if (wifiInfo.getSSID().equals(UNKNOWN_SSID)) {
                return null;
            }

            return wifiInfo.getSSID().replaceAll("\"", "");
        }
        return null;
    }
}