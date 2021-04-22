package presentation.view.customcomponents;


import javax.swing.*;
import java.awt.*;

/**
 * RoyaleLabel is the custom {@code JLabel} of the program.
 * <p>The label will be formatted according to the {@link LabelType} provided in the constructor.
 *
 * @see JLabel
 * @version 1.0
 */
public class RoyaleLabel extends JLabel {


    public RoyaleLabel(ImageIcon imageIcon) {
        super(imageIcon);
    }

    public enum LabelType{
        TITLE,
        PARAGRAPH,
        SMALL
    }


    public RoyaleLabel(String s, LabelType labelType){
        super(s);

        setForeground(Color.WHITE);
        switch (labelType){
            case TITLE:
                setFont(getFont().deriveFont(40f));
                break;
            case PARAGRAPH:
                setFont(getFont().deriveFont(26f));
                break;
            default:
                setFont(getFont().deriveFont(16f));
        }
    }


}
