package vadim.homesync.util;

public enum Setting {
    SERVER_IP( "server_ip", "192.168.0.111"),
    SERVER_PORT("server_port", "9999"),
    REMOTE_IP("remote_ip", "192.168.0.107"),
    HOME_SSID("home_ssid", "KOR"),
    MSG_PROTOCOL("msg_type", "UART"),
    EXTERNAL_PORT("external_port", ""),
    EXTERNAL_IP("external_ip", "");


    private final String defaultValue;
    private final String key;

    Setting(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }
}
