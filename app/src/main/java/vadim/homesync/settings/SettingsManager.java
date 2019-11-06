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
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.apply();
	}


	/*
		GETTERS
	 */

	public static CharSequence getAddress(Context context) {
		return getValue(context, Setting.SERVER_IP);
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
		SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
		return settings.getString(setting.getKey(), setting.getDefaultValue());
	}


}
