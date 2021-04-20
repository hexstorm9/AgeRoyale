package main;

import business.GameModel;
import presentation.controller.SplashScreenController;
import presentation.view.RoyaleFrame;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        //Do not remove -----------------------------
        //Uncomment to see BasicPlayer logs ---------
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); //Disable Commons-Logging (the BasicPlayer library uses it)
        // ------------------------------------------

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameModel gameModel = new GameModel();
                RoyaleFrame royaleFrame = new RoyaleFrame();
                SplashScreenController splashScreenController = SplashScreenController.getInstance(royaleFrame, gameModel);
                splashScreenController.start();
            }
        });
    }

}
