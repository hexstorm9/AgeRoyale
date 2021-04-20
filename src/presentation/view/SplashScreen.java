package presentation.view;

import presentation.view.customcomponents.RoyaleProgressBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class SplashScreen extends JPanel {

    private Image imageLogo;
    private RoyaleProgressBar royaleProgressBar;

    private final static String LOGO_IMAGE_PATH = "./resources/sprites/logo.png";


    public SplashScreen(){
        try{
            imageLogo = ImageIO.read(new File(LOGO_IMAGE_PATH));
        }catch(IOException e){}
        royaleProgressBar = new RoyaleProgressBar();

        JLabel logoLabel = new JLabel(new ImageIcon(imageLogo));
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


    public void setProgressBarValue(int progress){
        royaleProgressBar.setValue(progress);
    }

}
