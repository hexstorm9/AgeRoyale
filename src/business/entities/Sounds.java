package business.entities;


/**
 * The {@code Sounds enum} declares all the Sounds that can be reproduced on the game
 */
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


    /**
     * Returns a {@code String} representing the filename of the Sound in the system
     * @return A {@code String} representing the filename of the Sound in the system
     */
    public String getFileName(){
        return path;
    }
}


