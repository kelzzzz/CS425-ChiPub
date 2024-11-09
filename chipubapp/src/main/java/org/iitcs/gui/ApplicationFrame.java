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
    }
    public void setPanelWithMenu(JPanel newPanel){
        this.mainPanel = newPanel;
        getContentPane().removeAll();
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 120;
        getContentPane().add(mainPanel, c);
        addUserMessagePanel();
        revalidate();
    }

    public void addUserMessagePanel(){
        JPanel userMessage = new JPanel();
        //userMessage.setSize(50,50);
        JLabel msg = new JLabel("Hi, User!");
        if(!loggedIn){
            msg.setText("Hi, guest!");
        }
        userMessage.add(msg);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        getContentPane().add(userMessage, c);
    }
    public void setSubPanel(JPanel newPanel){
        this.subPanel = newPanel;
        getContentPane().add(subPanel);
        revalidate();
    }

}
