package presentation.view;

import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JPanel {


    public MainMenuScreen(){
        setLayout(new GridBagLayout());

        JPanel centerPane = new JPanel();
        centerPane.setOpaque(false);
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.add(new RoyaleLabel("We're in the Main Menu Screen", RoyaleLabel.LabelType.TITLE));

        add(centerPane, new GridBagConstraints());
    }

}
