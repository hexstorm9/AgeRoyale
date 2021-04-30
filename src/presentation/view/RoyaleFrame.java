package presentation.view;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Main View Class of the project.
 * RoyaleFrame will be the JFrame of the game.
 * <p>Whenever a screen change wants to take place, the RoyaleFrame will need to be called ({@link RoyaleFrame#changeScreen(JPanel, BackgroundStyle)} (JPanel)})
 * <p>The frame will be displayed in full screen, using the Java FullScreenExclusiveMode API.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html">FullScreenExclusiveMode API</a>
 */
public class RoyaleFrame extends JFrame {

    private GraphicsDevice gd;
    private MenuBackgroundPanel menuBackgroundPanel;
    private BattleBackgroundPanel battleBackgroundPanel;


    public enum BackgroundStyle{
        MENU,
        BATTLE
    }


    /**
     * RoyalFrame Constructor.
     * <p>Creates a Full-Screen RoyaleFrame if FullScreenExclusiveMode is allowed in the current machine.
     * Otherwise, shows an error message dialog and exits the application.
     *
     * @see <a href="https://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html">FullScreenExclusiveMode API</a>
     */
    public RoyaleFrame(){
        //Using the Java Full Screen Exclusive Mode API on the main application frame
        //More information on https://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1]; //Open frame in the second monitor
        if(!gd.isFullScreenSupported()){
            setSize(0, 0);
            setVisible(true);
            JOptionPane.showMessageDialog(this, "Can't open the game");
            System.exit(0);
        }
        else{
            menuBackgroundPanel = new MenuBackgroundPanel(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            setContentPane(menuBackgroundPanel);

            gd.setFullScreenWindow(this);
            validate();
        }

        setCursor(MenuGraphics.getInstance().getDefaultCursor());
    }


    /**
     * Provided a JPanel, this method deletes the Main Panel in the Frame and paints the new one provided.
     * The background style will also need to be provided so as to update it.
     *
     * @param newMainPanel The new main JPanel to put in the RoyaleFrame
     * @param backgroundStyle The Background style desired for this main JPanel provided
     *
     * @see BackgroundStyle
     */
    public void changeScreen(JPanel newMainPanel, BackgroundStyle backgroundStyle){
        if(newMainPanel == null) return;
        newMainPanel.setOpaque(false); //Force transparent JPanel

        //If the current content pane is the menuBackgroundPanel (meaning we're in the menu) add the panel provided to the menuBackgroundPanel
        //Else, add the panel provided to the battleBackgroundPanel (meaning we're in the battle)
        if(backgroundStyle == BackgroundStyle.MENU){
            if(getContentPane() != menuBackgroundPanel) setContentPane(menuBackgroundPanel);
            menuBackgroundPanel.removeAll();
            menuBackgroundPanel.add(newMainPanel);
        }
        else if(backgroundStyle == BackgroundStyle.BATTLE){
            if(getContentPane() != battleBackgroundPanel) setContentPane(battleBackgroundPanel);
            battleBackgroundPanel.removeAll();
            battleBackgroundPanel.add(newMainPanel);
        }

        if(getKeyListeners() != null){
            for(KeyListener kl: getKeyListeners()){ //Delete other keyListeners that could exist. We only want one
                removeKeyListener(kl);
            }
        }

        repaint(); //Repaint the frame as its contents have been updated
        revalidate(); //Recalculate again layouts, as the frame has been updated
    }



    /**
     * Sets the panel that will be on top of everything else. If you want it to be shown, {@link RoyaleFrame#setFrontPanelVisible(boolean)} has to be called.
     * <p>Caution! There can only be one single panel at a time on top of everything else
     */
    public void setFrontPanel(FrontPanel newFrontPanel){
        newFrontPanel.setVisible(false);
        setGlassPane(newFrontPanel);
    }


    /**
     * Toggles the PanelOnTop visibility.
     * @param visible Whether the PanelOnTop is visible or not
     */
    public void setFrontPanelVisible(boolean visible){
        Component glassPane = getGlassPane();
        if(glassPane != null) glassPane.setVisible(visible);
        repaint(); //Repaint the frame as its contents have been updated
        revalidate(); //Recalculate again layouts, as the frame has been updated
    }


    /**
     * Whenever a critical application error occurs and we can't continue the game because of it,
     * this method will add a {@code JPanel} to the {@code RoyaleFrame}, show the critical message error and exit the application.
     *
     * <p>From the point the critical error is shown, the user won't be able to interact with the app anymore.
     * The only interaction he will be able to carry out is to click the "OK" button of the message and exit the application.
     *
     * @param errorMessage The error message that will be displayed to the user.
     */
    public void showCriticalErrorAndExitApplication(String errorMessage){

        System.out.println(errorMessage);
        System.exit(0);
    }


}
