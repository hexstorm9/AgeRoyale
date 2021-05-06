package presentation.view;

import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;

public class LoadingBattleScreen extends JPanel {

    public LoadingBattleScreen(int height) {
        RoyaleLabel buenosDias = new RoyaleLabel("Cargando partida papa", RoyaleLabel.LabelType.TITLE);
        buenosDias.setAlignmentX(CENTER_ALIGNMENT);

        JPanel centeredPanel = new JPanel();
        centeredPanel.setOpaque(false);
        centeredPanel.setLayout(new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));
        centeredPanel.add(buenosDias);

        setLayout(new GridBagLayout());
        add(centeredPanel, new GridBagConstraints());

    }
}
