package org.iitcs.gui;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class ApplicationFrame extends JFrame{
    JPanel mainPanel = new JPanel();
    JPanel subPanel;
    boolean loggedIn = false;
    public ApplicationFrame(){
        setSize(APP_W,APP_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setSimplePanel(JPanel newPanel){
        this.mainPanel = newPanel;
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        revalidate();
        repaint();
    }

    public void setPanelWithMenu(JPanel newPanel, String greetingName){
        this.mainPanel = newPanel;
        getContentPane().removeAll();
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 120;
        getContentPane().add(mainPanel, c);
        addUserMessagePanel(greetingName);
        revalidate();
        repaint();
    }

    public void addUserMessagePanel(String greetingName){
        JPanel userMessage = new JPanel();
        JButton msg = new JButton("<html><u>Hi, ".concat(greetingName).concat("!</u></html>"));
        if(greetingName.isEmpty()){
            msg.setText("<html><u>Hi, User!</u></html>");
        }
        msg.setFocusPainted(false);
        msg.setMargin(new Insets(0, 0, 0, 0));
        msg.setContentAreaFilled(false);
        msg.setBorderPainted(false);
        msg.setOpaque(false);
        msg.setForeground(Color.BLUE);
        ApplicationStateManager as = ApplicationStateManager.getInstance();
        msg.addActionListener(e -> as.setState(ApplicationStateManager.GuiState.USER_DETAIL));
        userMessage.add(msg);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        getContentPane().add(userMessage, c);
    }
    public void addSubPanel(JPanel newPanel){

    }

}
