package business.entities;

/**
 * The {@code Songs enum} declares all the Songs that can be played on the game
 */
public enum Songs {
    MENU("menu.mp3"),
    MAIN_MENU("mainMenu.mp3"),
    BATTLE("battle.mp3");

    private final String path;

    Songs(String path) {
        this.path = path;
    }

    /**
     * Returns a {@code String} representing the filename of the Song in the system
     * @return A {@code String} representing the filename of the Song in the system
     */
    public String getFileName(){
        return path;
    }
}
