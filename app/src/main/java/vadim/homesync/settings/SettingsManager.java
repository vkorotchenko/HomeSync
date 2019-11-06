package vadim.homesync.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class SettingsManager {
    public static final String CONFIG_NAME = "MyConfigFile";

	public static void saveAddress(Activity context, String address) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("address", address);

		editor.commit();
		
	}

	public static void savePort(Activity context, String port) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("port", port);

		editor.commit();
		
	}
	public static void saveRemoteAddress(Activity context, String address) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("remote_address", address);

		editor.commit();
		
	}

	public static void saveMsgType(Activity context, String type) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("msg_type", type);

		editor.commit();
		
	}
	


	public static void saveHomeNetwork(Activity context,
                                       String network) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("home_network", network);

		editor.commit();
		
	}

	public static void saveExternalAddress(Activity context,
                                           String external) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("external_address", external);

		editor.commit();
		
	}
	

	public static CharSequence getAddress(Activity context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
	    
		if (mWifi.isConnected()) {
			  WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			   Log.d("wifiInfo", wifiInfo.toString());
			   Log.d("SSID",wifiInfo.getSSID());
			   
			   String home_network =settings.getString("home_network", "KOR");
			   Log.d("HOME NETWORK",home_network);
			   if(wifiInfo.getSSID().replaceAll("\"", "").equals(home_network)){
				   return settings.getString("address", "192.168.1.1");
			   }else{
				   return settings.getString("external_address", "test1.dyndns.org");
				   
			   }
		}else{
			   return settings.getString("external_address", "test1.dyndns.org");
			
		}
	    
	}

	public static CharSequence getPort(Activity context) {
		
	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
	    String port = settings.getString("port", "8080");
	    
	    return port;
	}

	public static CharSequence getMsgType(Activity context) {
		
	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
	    String msg_type = settings.getString("msg_type", "UART");
	    
	    return msg_type;
	}

	public static CharSequence getRemoteAddress(Activity context) {
		
	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
	    String address = settings.getString("remote_address", "192.168.1.1");
	    
	    return address;
	}
	
	public static CharSequence getExternal(Activity context) {
		
	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
	    String ext = settings.getString("external_address", "test1.dyndns.org");
	    
	    return ext;
	    
	}public static CharSequence getHomeNetwork(Activity context) {
		
	    SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, 0);
		String home_network =settings.getString("home_network", "KOR");
	    
	    return home_network;
	}
	
	

}
