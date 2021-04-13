package business.entities;

public enum Songs {
    Menu("menu.mp3"),
    Battle("battle.mp3");

    private final String path;

    Songs(String path) {
        this.path = path;
    }

    public String getFileName(){
        return path;
    }
}
