package presentation.view;

import presentation.controller.BattleController;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;

public class BattleScreen extends JPanel {

    private BattleController battleController;

    private BattlePanel battlePanel;
    private SouthPanel southPanel;

    public BattleScreen(BattleController battleController){
        setLayout(new BorderLayout());

        this.battleController = battleController;
        battlePanel = new BattlePanel();
        southPanel = new SouthPanel();

        add(battlePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }



    public JPanel getBattlePanel(){ return battlePanel;}


    public Dimension getBattlePanelSize(){
        return new Dimension(getSize().width, getSize().height - southPanel.getSize().height);
    }

    private class BattlePanel extends JPanel{


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            battleController.paintBattle(g);
        }

    }


    private class SouthPanel extends JPanel{

        public SouthPanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            add(new RoyaleLabel("Battle Screen", RoyaleLabel.LabelType.TITLE), BorderLayout.CENTER);
            add(new RoyaleLabel("Mana", RoyaleLabel.LabelType.TITLE), BorderLayout.SOUTH);
            add(Box.createRigidArea(new Dimension(50, 175)));
        }

    }


}
