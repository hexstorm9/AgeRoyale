package persistence;

import business.entities.Language;
import business.entities.Sentences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class LanguageDAO {

    private static final String FILE_PATH = "./resources/languages.json";

    public void updatePreferredLanguage(Language newLanguage) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        Gson gson = new Gson();

        LanguageFile lf = gson.fromJson(br, LanguageFile.class);

        for(Language l: Language.values()){
            if(l == newLanguage) lf.language = l.toString();
        }

        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        String json = gsonBuilder.toJson(lf);
        FileWriter fw = new FileWriter(FILE_PATH);
        fw.write(json);
        fw.close();
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
        Language currentLanguage = null;
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        Gson gson = new Gson();

        LanguageFile lf = gson.fromJson(br, LanguageFile.class);
        HashMap<Sentences, String> hashMap = new HashMap<>();

        for(Language l: Language.values()){
            if(l.toString().equals(lf.language)) currentLanguage = l;
        }
        //If the current language is still null, it means that the language in the file is not a language we have
        //on the enum, so let's use the default value: english
        if(currentLanguage == null) currentLanguage = Language.ENGLISH;

        int i = 0;
        for(Sentences s: Sentences.values()){
            hashMap.put(s, lf.sentences[i].values[currentLanguage.toInt()]);
            i++;
        }
        return hashMap;
    }
}
