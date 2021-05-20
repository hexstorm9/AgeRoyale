package presentation.view;

import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleProgressBar;

import javax.swing.*;
import java.awt.*;


/**
 * The LoadingBattleScreen class will be a simple {@link Screen} showing the logo and a ProgressBar ({@link RoyaleProgressBar})
 * controlled through its controller ({@link presentation.controller.LoadingBattleScreenController})
 * <p>It is shown whenever the program loads the resources before entering a Battle.
 *
 * @see presentation.controller.LoadingBattleScreenController
 * @see RoyaleProgressBar
 * @version 1.0
 */
public class LoadingBattleScreen extends Screen {

    private Image imageLogo;
    private RoyaleProgressBar royaleProgressBar;

    /**
     * LoadingBattleScreen Constructor.
     * <p>It will create the whole Panel and set the ProgressBar to 0.
     * @param screenHeight Height of the Screen so as to scale the Panel accordingly
     *
     * @see RoyaleProgressBar
     */
    public LoadingBattleScreen(int screenHeight){
        super(screenHeight);

        imageLogo = MenuGraphics.getLogo();
        royaleProgressBar = new RoyaleProgressBar();

        RoyaleLabel logoLabel = new RoyaleLabel(new ImageIcon(imageLogo));
        logoLabel.setAlignmentX(CENTER_ALIGNMENT);
        royaleProgressBar.setAlignmentX(CENTER_ALIGNMENT);

        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));
        centeredPanel.add(logoLabel);
        centeredPanel.add(Box.createRigidArea(new Dimension(50, 50))); //Add a little space between the logo and progress bar
        centeredPanel.add(royaleProgressBar);
        centeredPanel.setOpaque(false);

        setLayout(new GridBagLayout());
        add(centeredPanel, new GridBagConstraints());
    }


    /**
     * Visually update the ProgressBar value
     * @param progress Current progress
     */
    public void setProgressBarValue(int progress){
        royaleProgressBar.setValue(progress);
    }

}


