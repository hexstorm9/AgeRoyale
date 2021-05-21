package presentation.view;

import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


/**
 * BattleEndedFrontPanel is a {@link FrontPanel} that will be displayed just after the Battle has ended.
 * <p>It will inform about whether the Battle has been a victory or a defeat, the crowns won/lost and will give
 * the option to give a name to the Battle (all battles are saved, and you can name them).
 *
 * <p>A BattleEndedFrontPanel has to be controlled from a {@link presentation.controller.BattleEndedFrontPanelController}
 *
 * @version 1.0
 * @see FrontPanel
 * @see presentation.controller.BattleEndedFrontPanelController
 */
public class BattleEndedFrontPanel extends FrontPanel{


    /**
     * Exits the BattleEndedFrontPanel on clicked
     */
    private RoyaleButton okButton;

    private RoyaleLabel setBattleNameLabel;
    private RoyaleLabel battleNameErrorLabel;

    private RoyaleTextField battleNameTextField;


    public static final String OK_BUTTON_ACTION_COMMAND = "ok_button";
    public static final String BATTLE_NAME_LABEL_ACTION_COMMAND = "battle_name_label";


    /**
     * Default BattleEndedFrontPanel Constructor.
     *
     * @param panelWidth The Width of the FrontPanel
     * @param panelHeight The Height of the FrontPanel
     * @param totalPlayerCrowns The crowns that the player had before the battle
     * @param playerCrownsInBattle The crowns that the player won or lost in the battle.
     * @param winRate The updated winRate of the player
     */
    public BattleEndedFrontPanel(int panelWidth, int panelHeight, int totalPlayerCrowns, int playerCrownsInBattle, int winRate) {
        super(panelWidth, panelHeight);

        setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        String victoryOrDefeat = playerCrownsInBattle > 0 ? "Victory!": "Defeat...";
        Color victoryOrDefeatColor = playerCrownsInBattle > 0 ? MenuGraphics.YELLOW: MenuGraphics.RED;
        RoyaleLabel victoryOrDefeatLabel = new RoyaleLabel(victoryOrDefeat, RoyaleLabel.LabelType.TITLE);
        victoryOrDefeatLabel.setAlignmentX(CENTER_ALIGNMENT);
        victoryOrDefeatLabel.setForeground(victoryOrDefeatColor);

        JPanel crownsPanel = new JPanel();
        crownsPanel.setAlignmentX(CENTER_ALIGNMENT);
        crownsPanel.setOpaque(false);
        crownsPanel.setLayout(new BoxLayout(crownsPanel, BoxLayout.X_AXIS));
        String crownsText = (playerCrownsInBattle > 0? "+": "-") + Math.abs(playerCrownsInBattle) + " ";
        RoyaleLabel crownsLabel = new RoyaleLabel(crownsText, RoyaleLabel.LabelType.PARAGRAPH);
        crownsLabel.setForeground(playerCrownsInBattle > 0? Color.GREEN: MenuGraphics.RED);
        crownsPanel.add(crownsLabel);
        crownsPanel.add(new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getCrown(), crownsLabel.getFontMetrics(crownsLabel.getFont()).getHeight()))));

        JPanel currentCrownsPanel = new JPanel();
        currentCrownsPanel.setAlignmentX(CENTER_ALIGNMENT);
        currentCrownsPanel.setOpaque(false);
        currentCrownsPanel.setLayout(new BoxLayout(currentCrownsPanel, BoxLayout.X_AXIS));
        RoyaleLabel currentCrownsLabel = new RoyaleLabel("Total Crowns: " + (totalPlayerCrowns + playerCrownsInBattle < 0? 0: totalPlayerCrowns+playerCrownsInBattle) + " ",
                RoyaleLabel.LabelType.PARAGRAPH);
        currentCrownsPanel.add(currentCrownsLabel);
        currentCrownsPanel.add(new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getCrown(), currentCrownsLabel.getFontMetrics(currentCrownsLabel.getFont()).getHeight()))));

        JPanel currentWinRatePanel = new JPanel();
        currentWinRatePanel.setAlignmentX(CENTER_ALIGNMENT);
        currentWinRatePanel.setOpaque(false);
        currentWinRatePanel.setLayout(new BoxLayout(currentWinRatePanel, BoxLayout.X_AXIS));
        RoyaleLabel percentageWinRateLabel = new RoyaleLabel(winRate + "%", RoyaleLabel.LabelType.PARAGRAPH);
        percentageWinRateLabel.setForeground(winRate >= 50? Color.GREEN: MenuGraphics.RED);
        currentWinRatePanel.add(new RoyaleLabel("WinRate: ", RoyaleLabel.LabelType.PARAGRAPH));
        currentWinRatePanel.add(percentageWinRateLabel);


        setBattleNameLabel = new RoyaleLabel("Set Battle Name", RoyaleLabel.LabelType.LINK);
        setBattleNameLabel.setForeground(MenuGraphics.LIGHT_GREY);
        setBattleNameLabel.setAlignmentX(CENTER_ALIGNMENT);


        battleNameTextField = new RoyaleTextField();
        battleNameTextField.setAlignmentX(CENTER_ALIGNMENT);
        battleNameTextField.setMaximumSize(new Dimension(currentCrownsPanel.getPreferredSize().width, battleNameTextField.getMaximumSize().height));
        battleNameTextField.setVisible(false);

        battleNameErrorLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        battleNameErrorLabel.setAlignmentX(CENTER_ALIGNMENT);


        okButton = new RoyaleButton("Ok");
        okButton.setAlignmentX(CENTER_ALIGNMENT);


        centerPanel.add(victoryOrDefeatLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        centerPanel.add(crownsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        centerPanel.add(currentCrownsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        centerPanel.add(currentWinRatePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 35)));
        centerPanel.add(setBattleNameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 10)));
        centerPanel.add(battleNameTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 10)));
        centerPanel.add(battleNameErrorLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        centerPanel.add(okButton);

        add(centerPanel, new GridBagConstraints());
    }


    /**
     * Adds Action listeners for all the buttons in the view
     * @param al ActionListener that will listen to button events
     */
    @Override
    public void addButtonsListener(ActionListener al) {
        okButton.setActionCommand(OK_BUTTON_ACTION_COMMAND);
        okButton.addActionListener(al);
    }


    /**
     * Adds MouseListener for all clickable Labels in the view
     * @param ml MouseListener that will listen to clickable Label events
     */
    @Override
    public void addLabelsListener(MouseListener ml) {
        setBattleNameLabel.addMouseListener(ml);
        setBattleNameLabel.setActionCommand(BATTLE_NAME_LABEL_ACTION_COMMAND);
        setBattleNameLabel.setClickable(true);
    }


    /**
     * The BattleNameTextField is hidden by default. In order to show it, call this method
     */
    public void showBattleNameTextField(){
        battleNameTextField.setVisible(true);
        setBattleNameLabel.setClickable(false);
        setBattleNameLabel.setForeground(MenuGraphics.YELLOW);

        repaint();
        revalidate();
    }


    /**
     * Returns the battle name the user wrote in the TextField.
     * @return The battle name the user wrote in the TextField.
     */
    public String getBattleNameTextField(){
        return battleNameTextField.getText();
    }


    /**
     * To be called whenever an error needs to be shown because the battle name is not correct
     * @param error The error to show
     */
    public void showBattleNameError(String error){
        battleNameErrorLabel.setText(error);
        repaint();
        revalidate();
    }

    /**
     * Clears the error that is shown when the battle name is not correct
     */
    public void emptyBattleNameError(){
        battleNameErrorLabel.setText("   ");
        repaint();
        revalidate();
    }
}
