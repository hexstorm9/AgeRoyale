package presentation.controller;

import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.view.BattleEndedFrontPanel;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


/**
 * BattleEndedFrontPanelController is a {@link FrontPanelController}. It is the {@code controller}
 * class for an instance of a {@link BattleEndedFrontPanel}.
 * <p>It will provide methods to listen to events that occurred in the {@link BattleEndedFrontPanel}.
 *
 * @version 1.0
 * @see FrontPanelController
 * @see BattleEndedFrontPanel
 */

public class BattleEndedFrontPanelController extends FrontPanelController{

    /**
     * We need a reference of the BattleController that created this {@link FrontPanelController}
     * so as to redirect some actions.
     */
    private BattleController battleController;


    /**
     * Default BattleEndedFrontPanelController constructor.
     *
     * @param battleController The battleController to redirect actions to.
     * @param frontPanel The BattleEndedFrontPanel it will control.
     * @param royaleFrame The royaleFrame that the frontPanel will be put in.
     */
    public BattleEndedFrontPanelController(BattleController battleController, BattleEndedFrontPanel frontPanel, RoyaleFrame royaleFrame) {
        super(frontPanel, royaleFrame);
        this.battleController = battleController;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(BattleEndedFrontPanel.OK_BUTTON_ACTION_COMMAND)){
            BattleEndedFrontPanel battleEndedFrontPanel = (BattleEndedFrontPanel) frontPanel;
            final String battleName = battleEndedFrontPanel.getBattleNameTextField();

            battleEndedFrontPanel.emptyBattleNameError();

            //If the battleName is valid, call return to the MainMenu passing the name of the battle
            if(battleController.getGameModel().checkIfBattleNameIsValid(battleName))
                battleController.returnToMainMenu(battleName);
            else
                battleEndedFrontPanel.showBattleNameError(LanguageManager.getSentence(Sentences.NAME_NOT_WELL_FORMATTED));
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        super.mousePressed(e);
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();
            if(labelClicked.getActionCommand().equals(BattleEndedFrontPanel.BATTLE_NAME_LABEL_ACTION_COMMAND)){
                ((BattleEndedFrontPanel)frontPanel).showBattleNameTextField();
            }
        }
    }




}
