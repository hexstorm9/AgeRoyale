package business.entities;

public enum Sounds {
    Button("button.mp3"),
    SplashScreen("splashscreen.mp3");

    private final String path;



    Sounds(String path) {
        this.path = path;
    }

    public String getFileName(){
        return path;
    }
}


