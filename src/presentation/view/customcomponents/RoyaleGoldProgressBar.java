package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;


public class RoyaleGoldProgressBar extends JProgressBar {

    public RoyaleGoldProgressBar(int preferredHeight){
        setPreferredSize(new Dimension(getPreferredSize().width, preferredHeight));
        setMinimumSize(new Dimension(getMinimumSize().width, preferredHeight));
        setMaximumSize(new Dimension(getMaximumSize().width, preferredHeight));

        setMaximum(100);
        setMinimum(0);

        setFont(MenuGraphics.getMainFont().deriveFont(20f));
        setStringPainted(true);
        setString("4");

        setValue(40);
    }


}
