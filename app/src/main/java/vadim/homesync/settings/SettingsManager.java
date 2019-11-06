package vadim.homesync.settings;

import android.content.Context;
import android.content.SharedPreferences;

import vadim.homesync.util.Setting;

public class SettingsManager {
    public static final String CONFIG_NAME = "MyConfigFile";

	public static void saveAddress(Context context, String value) {
		saveValue(context, Setting.SERVER_IP.getKey(), value);
	}

	public static void savePort(Context context, String value) {
		saveValue(context, Setting.SERVER_PORT.getKey(), value);
	}
	public static void saveRemoteAddress(Context context, String value) {
		saveValue(context, Setting.REMOTE_IP.getKey(), value);
	}

	public static void saveMsgType(Context context, String value) {
		saveValue(context, Setting.MSG_PROTOCOL.getKey(), value);
	}

	public static void saveHomeSsid(Context context, String value) {
		saveValue(context, Setting.HOME_SSID.getKey(), value);
	}

	public static void saveExternalAddress(Context context, String value) {
		saveValue(context, Setting.EXTERNAL_IP.getKey(), value);
	}

	public static void saveExternalPort(Context context, String value) {
		saveValue(context, Setting.EXTERNAL_PORT.getKey(), value);
	}

	private static void saveValue (Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.apply();
	}


	/*
		GETTERS
	 */

	public static CharSequence getAddress(Context context) {
		return getValue(context, Setting.SERVER_IP);
//		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
//
//		if (mWifi.isConnected()) {
//			  WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//			   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//			   Log.d("wifiInfo", wifiInfo.toString());
//			   Log.d("SSID",wifiInfo.getSSID());
//
//			   String home_network =settings.getString("home_network", "KOR");
//			   Log.d("HOME NETWORK",home_network);
//			   if(wifiInfo.getSSID().replaceAll("\"", "").equals(home_network)){
//				   return settings.getString("address", "192.168.1.1");
//			   }else{
//				   return settings.getString("external_address", "test1.dyndns.org");
//
//			   }
//		}else{
//			   return settings.getString("external_address", "test1.dyndns.org");
//
//		}

	}
	public static CharSequence getPort(Context context) {
		return getValue(context, Setting.SERVER_PORT);
	}

	public static CharSequence getMsgType(Context context) {
		return getValue(context, Setting.MSG_PROTOCOL);
	}

	public static CharSequence getRemoteAddress(Context context) {
		return getValue(context, Setting.REMOTE_IP);
	}

	public static CharSequence getExternalIp(Context context) {
		return getValue(context, Setting.EXTERNAL_IP);
	}

	public static CharSequence getExternalPort(Context context) {
		return getValue(context, Setting.EXTERNAL_PORT);
	}

	public static CharSequence getHomeSsid(Context context) {
		return getValue(context, Setting.HOME_SSID);
	}


	public static CharSequence getValue(Context context, Setting setting) {
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		return settings.getString(setting.getKey(), setting.getDefaultValue());
	}


}
