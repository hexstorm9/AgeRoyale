package main;

import persistence.LanguageDAO;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        LanguageDAO lan = new LanguageDAO();

        try {
            lan.readSentences();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
