package persistence;

import business.entities.Language;
import business.entities.Sentences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;

/**
 * Data Access Object for retrieving/updating information from the {@code languages.json} file.
 */
public class LanguageDAO {

    private static final String FILE_PATH = "./resources/languages.json";

    /**
     * Updates the preferred language in the {@code languages.json}
     * @param newLanguage New preferred language
     * @throws JsonSyntaxException When the file has possibly been modified from outside the program and can't be read/modified
     * @throws IOException When the file can't be found
     */
    public void updatePreferredLanguage(Language newLanguage) throws JsonSyntaxException, IOException{
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


    /**
     * Inner class matching the whole {@code languages.json} so as to read with Gson
     */
    private class LanguageFile{
        String language;
        Sentence[] sentences;
    }

    /**
     * Inner class matching each Sentence on the json so as to read with Gson
     */
    private class Sentence{
        String key;
        String[] values;
    }


    /**
     * Reads all the Sentences from {@code languages.json} and returns a {@code HashMap}, where each {@link Sentence} maps its
     * correct {@code String} depending on the language of the user.
     * @return {@code HashMap} where each {@link Sentence} maps a {@code String}
     * @throws JsonSyntaxException
     * @throws IOException
     */
    public HashMap<Sentences, String> readSentences() throws JsonSyntaxException, IOException {
        Language currentLanguage = null;
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        Gson gson = new Gson();

        LanguageFile lf = gson.fromJson(br, LanguageFile.class);
        HashMap<Sentences, String> hashMap = new HashMap<>();


        for(Language l: Language.values()){
            if(l.toString().equals(lf.language)) currentLanguage = l;
        }
        //If the current language is still null, it means that the language written in the file is not a language we have
        //on the enum. The default value of the language written is not a language of the enum, so we'll always
        //do this step -->
        //Let's check the language of the OS and choose a language accordingly
        if(currentLanguage == null){
            for(Language l: Language.values()){
                Locale locale = new Locale(l.toString());
                if(locale.equals(Locale.getDefault())) currentLanguage = l;
            }
            //If currentLanguage is still null (meaning the locale language of the OS is not supported)
            //Let's assign the default value to english
            if(currentLanguage == null) currentLanguage = Language.ENGLISH;
        }


        //Let's loop through every sentence of the file
        for(int i = 0; i < lf.sentences.length; i++){
            boolean sentenceFound = false;

            //With every sentence of the file, let's check what sentence it is, and save it to the hashMap
            for(Sentences s: Sentences.values()){
                if(s.toString().equals(lf.sentences[i].key)){
                    sentenceFound = true;
                    hashMap.put(s, lf.sentences[i].values[currentLanguage.toInt()]);
                }
            }

            if(sentenceFound == false) System.err.println("A sentence has not been properly loaded --> " + lf.sentences[i].values[0]);
        }

        return hashMap;
    }
}
