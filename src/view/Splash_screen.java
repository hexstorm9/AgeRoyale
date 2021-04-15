package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Splash_screen extends JFrame {
    public static final String FRAME_TITLE = "Splash_screen";
    public static final String NORTH_LABEL_TEXT = "Age Royale ";
    JFrame frame = new JFrame("JPanel Example");

    private JLabel labelUsername = new JLabel("Age Royale ");
    private BufferedImage image = null;

    private void configureFrame() {
        setSize(800,600);
        setTitle(FRAME_TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public Splash_screen() throws IOException {




        initComponents();

      /*  configureFrame();

        JLabel jlNorth = new JLabel(NORTH_LABEL_TEXT);


        getContentPane().add(jlNorth, BorderLayout.NORTH);

        getContentPane().add( new ImagePanel("Resources/images/load.png"), BorderLayout.CENTER);
*/


    }

    private void initComponents() {

       JPanel jPanel1 = new JPanel();
       JLabel jLabel2 = new JLabel();
       JLabel jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 153));

        jPanel1.setBackground(new Color(0, 0, 102));
        jPanel1.setForeground(new Color(0, 0, 102));
        jPanel1.setToolTipText("");

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/Resources/images/Cargar_anim3.gif"))); // NOI18N

        jLabel1.setFont(new Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Age Royale");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(247, 247, 247)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(412, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(53, Short.MAX_VALUE)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel2)
                                .addGap(70, 70, 70))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(276, Short.MAX_VALUE))
        );

        pack();
    }



    }
