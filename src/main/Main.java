package main;

import business.DatabaseInfo;
import view.Splash_screen;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
        try{
            DatabaseInfo databaseInfo = DatabaseInfo.getInstance();

            System.out.println("IP --> " + databaseInfo.getIp());
            System.out.println("Port --> " + databaseInfo.getPort());
            System.out.println("User --> " + databaseInfo.getUser());
            System.out.println("Password --> " + databaseInfo.getPassword());
            System.out.println("Database Name --> " + databaseInfo.getDatabaseName());
        }
        catch(IOException e){
            System.out.println("Couldn't read the file correctly.");
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Splash_screen view = null;

                try {
                    System.out.println("run");
                    view = new Splash_screen();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                view.setVisible(true);
            }
        });
    }

}
