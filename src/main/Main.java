package main;

import business.GameModel;
import presentation.controller.SplashScreenController;
import presentation.graphics.MenuGraphics;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * WarRoyale Main Class.
 * <p>As the game follows the {@code MVC Pattern}, this Main class starts the game by creating the model, the view and the first controller.
 * It all will be done on the {@code AWT Event Dispatch Thread}, as it should be the "Main Thread" of any Swing Application.
 *
 * <p>A UML Class Diagram is added to the root project folder so as to provide a better description of the whole project.
 *
 * @author david.bassols
 * @author marc.cano
 * @author biel.carpi
 * @author rafael.morera
 * @author pol.saula
 * @version 1.0
 *
 * @see GameModel
 * @see RoyaleFrame
 * @see SplashScreenController
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try{
                    MenuGraphics.getInstance().load(); //Load graphics before creating the screen
                }catch(IOException | FontFormatException e){
                    System.err.println("Can't load graphics. Please, reinstall the game.");
                    e.printStackTrace();
                }

                GameModel gameModel = new GameModel(); //Create the Model
                RoyaleFrame royaleFrame = new RoyaleFrame(); //Create the View
                SplashScreenController splashScreenController = new SplashScreenController(royaleFrame, gameModel); //Create controller and link together model, view and controller
                splashScreenController.start(false); //Start the controller and the whole project
            }
        });

    }

}
