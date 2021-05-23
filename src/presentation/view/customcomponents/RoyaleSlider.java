package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * {@code RoyaleSlider} is the {@link JSlider} of the game.
 * <p>It provides the same functionality as a normal {@link JSlider}, but also has
 * a new look that doesn't depend on the SO.
 *
 * @see JSlider
 * @version 1.0
 */
public class RoyaleSlider extends JSlider {

    private String actionCommand;

    /**
     * Default RoyaleSlider constructor.
     * <p>Creates a {@code Slider} with selected orientation, a minimum value of 0,
     * a maximum value of 100, and a default value of 100
     * @param orientation Orientation of the Slider
     */
    public RoyaleSlider(int orientation){
        super(orientation, 0, 100, 100);
        setOpaque(false);
        setPaintTicks(false);
        setSnapToTicks(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUI() {
        setUI(new CustomSliderUI(this));
    }

    /**
     * Sets the ActionCommand of this RoyaleSlider
     * @param actionCommand String representing the actionCommand of the slider
     */
    public void setActionCommand(String actionCommand){
        this.actionCommand = actionCommand;
    }

    /**
     * Returns the ActionCommand of this RoyaleSlider
     * @return String representing the actionCommand of the slider
     */
    public String getActionCommand(){
        return actionCommand;
    }


   private class CustomSliderUI extends BasicSliderUI {
       private final int TRACK_HEIGHT = 8;
       private final int TRACK_WIDTH = 16;
       private final int TRACK_ARC = 6;
       private final Dimension THUMB_SIZE = new Dimension(15, 15);
       private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

       public CustomSliderUI(final JSlider b) {
           super(b);
       }

       @Override
       protected void calculateTrackRect() {
           super.calculateTrackRect();
           if (isHorizontal()) {
               trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
               trackRect.height = TRACK_HEIGHT;
           } else {
               trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
               trackRect.width = TRACK_WIDTH;
           }
           trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
       }

       @Override
       protected void calculateThumbLocation() {
           super.calculateThumbLocation();
           if (isHorizontal()) {
               thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
           } else {
               thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
           }
       }

       @Override
       protected Dimension getThumbSize() {
           return THUMB_SIZE;
       }

       private boolean isHorizontal() {
           return slider.getOrientation() == JSlider.HORIZONTAL;
       }

       @Override
       public void paint(final Graphics g, final JComponent c) {
           ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           super.paint(g, c);
       }

       @Override
       public void paintTrack(final Graphics g) {
           Graphics2D g2 = (Graphics2D) g;
           Shape clip = g2.getClip();

           boolean horizontal = isHorizontal();
           boolean inverted = slider.getInverted();

           // Paint shadow.
           g2.setColor(new Color(170, 170 ,170));
           g2.fill(trackShape);

           // Paint track background.
           g2.setColor(new Color(200, 200 ,200));
           g2.setClip(trackShape);
           trackShape.y += 1;
           g2.fill(trackShape);
           trackShape.y = trackRect.y;

           g2.setClip(clip);

           // Paint selected track.
           if (horizontal) {
               boolean ltr = slider.getComponentOrientation().isLeftToRight();
               if (ltr) inverted = !inverted;
               int thumbPos = thumbRect.x + thumbRect.width / 2;
               if (inverted) {
                   g2.clipRect(0, 0, thumbPos, slider.getHeight());
               } else {
                   g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
               }

           } else {
               int thumbPos = thumbRect.y + thumbRect.height / 2;
               if (inverted) {
                   g2.clipRect(0, 0, slider.getHeight(), thumbPos);
               } else {
                   g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
               }
           }
           g2.setColor(MenuGraphics.RED);
           g2.fill(trackShape);
           g2.setClip(clip);
       }

       @Override
       public void paintThumb(final Graphics g) {
           g.setColor(MenuGraphics.YELLOW);
           g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
       }

       @Override
       public void paintFocus(final Graphics g) {}
   }



}
