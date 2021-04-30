package business.entities;

import com.google.gson.JsonSyntaxException;
import persistence.LanguageDAO;

import java.io.IOException;
import java.util.HashMap;


public class LanguageManager {

    private static HashMap<Sentences, String> sentencesLoaded;
    private static LanguageDAO languageDAO;

    private LanguageManager(){ }


    public static void load() throws JsonSyntaxException, IOException {
        languageDAO = new LanguageDAO();
        sentencesLoaded = languageDAO.readSentences();
    }

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

    public static String getSentence(Sentences sentence){
        String loQueSea = sentencesLoaded.get(sentence);
        return loQueSea;
    }
}
