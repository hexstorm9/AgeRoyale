package business.entities;

/**
 * The {@code Language enum} will contain the information about all the available languages in the game.
 */
public enum Language {
    ENGLISH("en", 0), SPANISH("es", 1), CATALAN("ca", 2);


    private String stringValue;
    private int intValue;

    Language(String stringValue, int intValue){
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    /**
     * Returns a {@code String} representation of the {@link Language}
     * @return A {@code String} representation of the {@link Language}
     */
    public String toString(){
        return stringValue;
    }

    /**
     * Returns an {@code Integer} representation of the {@link Language}
     * @return An {@code Integer} representation of the {@link Language}
     */
    public int toInt(){
        return intValue;
    }
}
