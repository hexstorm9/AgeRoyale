package presentation.view.customcomponents;


import javax.swing.*;


/**
 * RoyaleButton is the custom JButton of the application.
 * <p>It features a custom style depending on the {@link ButtonType} provided on the constructor.
 *
 * @see JButton
 * @version 1.0
*/
public class RoyaleButton extends JButton {

    public enum ButtonType{
        NORMAL,
        BIG
    }

    public RoyaleButton(String buttonMessage){
        super(buttonMessage);
    }

}
