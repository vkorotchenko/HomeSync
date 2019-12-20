package vadim.homesync.common;

public enum Action {
    ON_ARRIVE("ON_ARRIVE","Arriving Home","Turn on Lights?","Turn on", 29783),
    ON_DEPART("ON_DEPART","Leaving Home","Close blinds?","Close",0327652);

    private final String action;
    private final String title;
    private final String text;
    private final String button;
    private final Integer id;

    Action(String action,String title,String text,String button, Integer id) {

        this.action = action;
        this.title = title;
        this.text = text;
        this.button = button;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getButton() {
        return button;
    }

    public String getAction() {
        return action;
    }

    public Integer getId() {
        return id;
    }
}
