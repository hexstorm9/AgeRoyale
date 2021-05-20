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
    private boolean canHideFrontPanel; //By default, if the user clicks outside the front panel, it will hide.
                                       //This behaviour can be modified toggling this attribute to false


    /**
     * Default FrontPanelController constructor.
     * @param frontPanel The frontPanel it will control.
     * @param royaleFrame The royaleFrame that the frontPanel will be put in.
     */
    public FrontPanelController(FrontPanel frontPanel, RoyaleFrame royaleFrame){
        this.frontPanel = frontPanel;
        this.royaleFrame = royaleFrame;
        frontPanel.addButtonsListener(this);
        frontPanel.addMouseListener(this);

        isFrontPanelVisible = false;
        canHideFrontPanel = true;
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
    public void mousePressed(MouseEvent e) {
        //If somebody has clicked directly in the FrontPanel (not the panels above
        //the FrontPanel) let's hide the panel
        if(e.getSource() instanceof FrontPanel){
            if(canHideFrontPanel) setFrontPanelVisibility(false);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


    /**
     * Returns whether the FrontPanel is currently visible or not
     * @return Whether the FrontPanel is currently visible or not
     */
    public boolean isFrontPanelVisible(){
        return isFrontPanelVisible;
    }

    /**
     * It tells to the FrontPanel whether it can be hidden (clicking outside of it) or not.
     * <p>By default, a FrontPanel can be hidden
     * @param canBeHidden Whether the FrontPanel can be hidden or not.
     */
    public void canBeHidden(boolean canBeHidden){
        canHideFrontPanel = canBeHidden;
    }

}
