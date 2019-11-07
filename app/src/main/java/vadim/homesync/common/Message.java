package vadim.homesync.common;

public enum Message {
    BLINDS_UP("BLINDS_UP"),
    BLINDS_STOP("BLINDS_STOP"),
    BLINDS_DOWN("BLINDS_DOWN"),
    BEDROOM_LIGHTS("BEDROOM_LIGHTS"),
    LIVING_ROOM_LIGHTS("LIVINGROOM_LIGHTS"),
    KITCHEN_LIGHTS("KITCHENK_LIGHTS"),
    DINING_LIGHTS("DINING_LIGHTS"),
    PATIO_LIGHTS("PATIO_LIGHTS");

    private final String key;

    Message(String key) {
        this.key = key;
    }


    public String getKey() {
        return key;
    }
}