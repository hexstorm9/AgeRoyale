package business.entities;


/**
 * The {@code Sounds enum} declares all the Sounds that can be reproduced on the game
 */
public enum Sounds {
    /**
     * Defines the sound of a Button
     */
    BUTTON("button.mp3"),
    /**
     * Defines the sound of the SplashScreen
     */
    SPLASH_SCREEN("splashscreen.mp3"),
    /**
     * Defines the sound of an Error
     */
    ERROR("button.mp3"),
    /**
     * Defines the sound of a won battle
     */
    WON("button.mp3"),
    /**
     * Defines the sound of a lost battle
     */
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


