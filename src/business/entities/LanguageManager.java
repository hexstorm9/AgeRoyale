package business.entities;

import persistence.LanguageDAO;

import java.io.IOException;
import java.util.HashMap;


public class LanguageManager {

    private static HashMap<Sentences, String> sentencesLoaded;
    private static LanguageDAO languageDAO;

    private LanguageManager(){ }


    public static void load() throws IOException{
        languageDAO = new LanguageDAO();
        sentencesLoaded = languageDAO.readSentences();
    }

    public static void changePreferredLanguage(Language language) throws IOException{
        if(languageDAO == null) return;
        languageDAO.updatePreferredLanguage(language);
    }

    public static String getSentence(Sentences sentence){
        String loQueSea = sentencesLoaded.get(sentence);
        return loQueSea;
    }
}
