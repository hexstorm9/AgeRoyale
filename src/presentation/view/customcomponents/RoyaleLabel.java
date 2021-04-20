package presentation.view.customcomponents;

import com.sun.source.tree.LambdaExpressionTree;

import javax.swing.*;
import java.awt.*;

public class RoyaleLabel extends JLabel {


    enum LabelType{
        TITLE,
        NORMAL_TEXT
    }


    public RoyaleLabel(LabelType l){

    }


    public RoyaleLabel(String s) {
        super(s);
        setForeground(Color.WHITE);
    }

    public RoyaleLabel(String s, float fontSize) {
        super(s);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(fontSize));
    }
}
