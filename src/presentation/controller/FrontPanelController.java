package presentation.controller;

import presentation.view.FrontPanel;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * FrontPanelController will control a {@link FrontPanel}.
 *
 * @see FrontPanel
 * @version 1.0
 */
public abstract class FrontPanelController implements ActionListener, MouseListener {


    protected FrontPanel frontPanel;
    private RoyaleFrame royaleFrame;

    private boolean isFrontPanelVisible;


    public FrontPanelController(FrontPanel frontPanel, RoyaleFrame royaleFrame){
        this.frontPanel = frontPanel;
        this.royaleFrame = royaleFrame;
        frontPanel.addButtonsListener(this);
        frontPanel.addMouseListener(this);

        isFrontPanelVisible = false;
    }


    /**
     * Set whether this frontPanel should be visible on the current {@link RoyaleFrame}
     * or not.
 * @param visible Whether the {@code FrontPanel} that this controller controls is visible or not
     */
    public void setFrontPanelVisibility(boolean visible){
        if(visible) royaleFrame.setFrontPanel(frontPanel);
        royaleFrame.setFrontPanelVisible(visible);
        isFrontPanelVisible = visible;
    }



    //We need to capture all mouse events so as not to let the user allow to click behind the SettingsPanel
    @Override
    public void mouseClicked(MouseEvent e) {
        //If somebody has clicked directly in the FrontPanel (not the panels above
        //the FrontPanel) let's hide the panel
        if(e.getSource() instanceof FrontPanel){
            setFrontPanelVisibility(false);
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


    public boolean isFrontPanelVisible(){
        return isFrontPanelVisible;
    }

}
