package org.iitcs.gui;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;
import static org.iitcs.util.Util.setGridBagConstraints;

public class ApplicationFrame extends JFrame{
    JPanel mainPanel = new JPanel();
    public ApplicationFrame(){
        setSize(APP_W,APP_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ApplicationFrame.class.getClassLoader().getResource("cplicon.png"));
        setIconImage(icon.getImage());
        setTitle("Chicago Public Library Catalog");
        setVisible(true);
    }

    public void packSimpleFrame(JPanel newPanel){
        setMainPanel(newPanel);
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0,1, 120);
        getContentPane().add(mainPanel,c);
        refreshPanel();
    }

    private void refreshPanel() {
        revalidate();
        repaint();
    }

    private void setMainPanel(JPanel newPanel) {
        this.mainPanel = newPanel;
        getContentPane().removeAll();
    }

}
