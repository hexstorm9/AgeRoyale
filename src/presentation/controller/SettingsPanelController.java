package presentation.controller;

import presentation.view.SettingsPanel;
import presentation.view.customcomponents.RoyaleLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class SettingsPanelController implements ActionListener, MouseListener {

    private SettingsPanel settingsPanel;

    public SettingsPanelController(SettingsPanel settingsPanelToControl){
        this.settingsPanel = settingsPanelToControl;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case SettingsPanel.EXIT_BUTTON_ACTION_COMMAND:
                    System.exit(0);
                break;
            case SettingsPanel.CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND:
                settingsPanel.showLanguagesPanel();
                break;
            case SettingsPanel.CREDITS_BUTTON_ACTION_COMMAND:
                settingsPanel.showCreditsPanel();
                break;
            case SettingsPanel.LOG_OUT_BUTTON_ACTION_COMMAND:
                //TODO: Logout
                break;
        }
    }


    //We need to capture all mouse events so as not to let the user allow to click behind the SettingsPanel
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(SettingsPanel.RETURN_TO_MAIN_MENU_ACTION_COMMAND)){
                settingsPanel.showMainPanel();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
