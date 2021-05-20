package presentation.view;

import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class BattleEndedFrontPanel extends FrontPanel{


    public BattleEndedFrontPanel(int panelWidth, int panelHeight, int playerCrowns) {
        super(panelWidth, panelHeight);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new RoyaleLabel("Victory", RoyaleLabel.LabelType.TITLE));
    }


    @Override
    public void addButtonsListener(ActionListener al) {

    }

    @Override
    public void addLabelsListener(MouseListener ml) {}

}
