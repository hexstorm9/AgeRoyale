package presentation.view;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


/**
 * FrontPanel is a type of {@code JPanel} that can be put on top of everything else (only one
 * {@code FrontPanel} at a time) by directly using the {@link RoyaleFrame} of the application
 * with the method {@link RoyaleFrame#setFrontPanel(FrontPanel)}.
 *
 * @see RoyaleFrame#setFrontPanel(FrontPanel)
 * @version 1.0
 */
public abstract class FrontPanel extends JPanel {

    private Color backgroundColor = new Color(0, 0, 0, 220);
    protected final int PANEL_HEIGHT, PANEL_WIDTH;

    private boolean woodTableEnabled;
    private Image woodTable;
    private int woodTableXCoord, woodTableYCoord;


    public FrontPanel(int panelWidth, int panelHeight){

        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;

        setOpaque(false);
        setLayout(new GridBagLayout());

        woodTableEnabled = false;
    }


    /**
     * Add the buttons listener of the current {@code FrontPanel}
     * @param al Listener of the Buttons in this view
     */
    public abstract void addButtonsListener(ActionListener al);


    /**
     * Add the labels listener of the current {@code FrontPanel}
     * @param ml Listener of the Labels in this view
     */
    public abstract void addLabelsListener(MouseListener ml);


    /**
     * Shows a Wood Table in the center of the screen with specified height.
     * @param tableHeight Height of the table (Width will be resized accordingly)
     *
     * @return int representing the Table's width
     */
    public int setWoodTableVisible(int tableHeight){
        woodTable = MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getWoodTable(), tableHeight);
        woodTableEnabled = true;

        int tableWidth = woodTable.getWidth(null);

        woodTableXCoord = PANEL_WIDTH/2 - tableWidth/2;
        woodTableYCoord = PANEL_HEIGHT/2 - tableHeight/2;

        return tableWidth;
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT, 0, 0);

        if(woodTableEnabled)
            g2d.drawImage(woodTable, woodTableXCoord, woodTableYCoord, null);

        super.paintComponent(g);
    }

}
