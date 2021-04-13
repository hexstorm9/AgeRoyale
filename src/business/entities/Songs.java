package business.entities;

public enum Songs {
    MENU("menu.mp3"),
    BATTLE("battle.mp3");

    private final String path;

    Songs(String path) {
        this.path = path;
    }

    public String getFileName(){
        return path;
    }
}
