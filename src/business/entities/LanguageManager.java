package business.entities;

import com.google.gson.JsonSyntaxException;
import persistence.LanguageDAO;

import java.io.IOException;
import java.util.HashMap;


/**
 * {@code LanguageManager} is a {@code static} class that holds all the {@link String} values for each
 * {@link Sentences} in the Game.
 *
 * <p>Before any {@code String} for a {@link Sentences} can be obtained, they must be loaded by calling
 * the method {@link #load()}
 *
 * <p>In order to obtain a {@link Sentences} String, call {@link #getSentence(Sentences)}
 *
 * @see Sentences
 * @version 1.0
 */
public class LanguageManager {

    private static HashMap<Sentences, String> sentencesLoaded;
    private static LanguageDAO languageDAO;

    private LanguageManager(){ }

    /**
     * Loads all the {@code Strings} representing each {@link Sentences} depending on the {@link Language} of the
     * Game.
     * @throws JsonSyntaxException When the file containing the information of the languages can't be correctly read
     * @throws IOException When the file containing the information of the languages can't be found
     */
    public static void load() throws JsonSyntaxException, IOException {
        languageDAO = new LanguageDAO();
        sentencesLoaded = languageDAO.readSentences();
    }


    /**
     * Changes the preferred language of the Game by writing to the file.
     * <p>In order for changes to take effect, a new call to {@link #load()} must be done.
     * @param language The new {@link Language} of the Game
     */
    public static void changePreferredLanguage(Language language){
        if(languageDAO == null) return;
        try{
            languageDAO.updatePreferredLanguage(language);
        }catch(IOException e){
            //Don't show an error to the user. Only show error in the stderr
            System.err.println("Failed to write the new language to the Languages file");
            e.printStackTrace();
        }
    }

    /**
     * Provided a {@link Sentences}, returns its {@code String} representation depending on the
     * {@link Language} of the Game.
     * @param sentence The Sentence we want its string
     * @return The {@code String} representing the Sentence provided, depending on the {@link Language} of the Game.
     */
    public static String getSentence(Sentences sentence){
        String loQueSea = sentencesLoaded.get(sentence);
        return loQueSea;
    }
}
