package presentation.view;

import javax.swing.*;
import java.awt.*;

public class RoyaleFrame extends JFrame {

    private GraphicsDevice gd;
    private RoyaleScreen royaleScreen;

    public RoyaleFrame(){
        //Using the Java Full Screen Exclusive Mode API on the main application frame
        //More information on https://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(!gd.isFullScreenSupported()){
            setSize(0, 0);
            setVisible(true);
            JOptionPane.showMessageDialog(this, "Can't open the game");
            System.exit(0);
        }
        else{
            royaleScreen = new RoyaleScreen(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            setContentPane(royaleScreen);

            gd.setFullScreenWindow(this);
            validate();
        }
    }


    public void changeMainPane(JPanel newMainPane){
        royaleScreen.removeAll();
        newMainPane.setOpaque(false);
        royaleScreen.add(newMainPane);
        repaint();
        revalidate();
    }

    public void showCriticalErrorAndExitApplication(String errorMessage){
        System.out.println(errorMessage);
        System.exit(0);
    }

}
