package persistence;

import business.entities.Language;
import business.entities.Sentences;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class LanguageDAO {
    private static final String FILE_PATH = "./resources/languages.json";

    public void updatePreferredLanguage(Language l){

    }
    class Sentence{
        String key;
        String[] values;
    }

    class LanguageFile{
        String language;
        Sentence[] sentences;
    }

    public HashMap<Sentences, String> readSentences() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        Gson gson = new Gson();

        LanguageFile lf = gson.fromJson(br, LanguageFile.class);
        HashMap<Sentences, String> hashMap= new HashMap<>();
        Language l;

        //Locale.getDefault().getLanguage();
        switch (lf.language){
            case "en":
                l = Language.ENGLISH;
                break;
            case "ca":
                l = Language.CATALAN;
                break;
            case "es":
                l = Language.SPANISH;
                break;
            default:

        }

        System.out.println(lf.sentences[4].values[1]);
        int i=0;
        for(Sentences s: Sentences.values()){
            hashMap.put(s, lf.sentences[i].values[0]);
            i++;
        }
        return hashMap;
    }
}
