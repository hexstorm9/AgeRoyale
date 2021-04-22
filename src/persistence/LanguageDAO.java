package persistence;

import business.entities.Language;
import business.entities.Sentences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class LanguageDAO {
    private static final String FILE_PATH = "./resources/languages.json";

    public void updatePreferredLanguage(Language l){
        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            Gson gson = new Gson();

            LanguageFile lf = gson.fromJson(br, LanguageFile.class);

            switch (l){
                case ENGLISH:
                    lf.language = "en";
                    break;
                case SPANISH:
                    lf.language = "es";
                    break;
                case CATALAN:
                    lf.language = "ca";
                    break;
            }

            Gson gs = new GsonBuilder().setPrettyPrinting().create();
            String json = gs.toJson(lf);
            FileWriter fw = new FileWriter(FILE_PATH);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        int language;
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        Gson gson = new Gson();

        LanguageFile lf = gson.fromJson(br, LanguageFile.class);
        HashMap<Sentences, String> hashMap= new HashMap<>();

        switch (lf.language){
            case "en":
                language = 0;
                break;
            case "ca":
                language = 1;
                break;
            case "es":
                language = 2;
                break;
            default:
                language = 0;
                break;
        }

        int i=0;
        for(Sentences s: Sentences.values()){
            hashMap.put(s, lf.sentences[i].values[language]);
            i++;
        }
        return hashMap;
    }
}
