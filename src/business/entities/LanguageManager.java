package business.entities;

import persistence.LanguageDAO;

import java.io.IOException;
import java.util.HashMap;

public class LanguageManager {
    HashMap<Sentences, String> sentencesLoaded = new HashMap<Sentences, String>();

    private LanguageManager(){ }

    public void load(){
        LanguageDAO ld = new LanguageDAO();

        try {
            sentencesLoaded = ld.readSentences();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePreferredLanguage(Language language){
        LanguageDAO ld = new LanguageDAO();

        ld.updatePreferredLanguage(language);
    }

    public String getSentence(Sentences sentence){
        String loQueSea = sentencesLoaded.get(sentence);

        return loQueSea;
    }
}
