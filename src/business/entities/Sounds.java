package business.entities;

public enum Sounds {
    BUTTON("button.mp3"),
    SPLASH_SCREEN("splashscreen.mp3"),
    ERROR("button.mp3"),
    WON("button.mp3"),
    LOST("button.mp3");

    private final String path;



    Sounds(String path) {
        this.path = path;
    }

    public String getFileName(){
        return path;
    }
}


