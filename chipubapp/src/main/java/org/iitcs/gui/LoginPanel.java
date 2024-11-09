package org.iitcs.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class LoginPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    JPanel innerPanel = new JPanel();
    JButton loginButton = new JButton();
    JLabel failure = new JLabel();
    public LoginPanel(){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
        packInnerPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(innerPanel, c);

        GridBagConstraints c2 = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        add(failure, c);

        setVisible(true);
    }

    private void packInnerPanel(){ //TODO majorly improve this
        innerPanel.setLayout(new BorderLayout());

        JLabel loginLabel = new JLabel("Log in", SwingConstants.CENTER);
        innerPanel.add(loginLabel, BorderLayout.NORTH);

        JPanel chPanel = new JPanel();
        chPanel.setLayout(new BorderLayout());

        JLabel cardholderLabel = new JLabel("Card number: ");
        chPanel.add(cardholderLabel, BorderLayout.WEST);
        JTextField cardnumberField = new JTextField(20);
        chPanel.add(cardnumberField, BorderLayout.EAST);

        JPanel pwPanel = new JPanel();
        pwPanel.setLayout(new BorderLayout());

        JLabel passwordLabel = new JLabel("Password: ");
        pwPanel.add(passwordLabel, BorderLayout.WEST);
        JPasswordField passwordField = new JPasswordField(20);
        pwPanel.add(passwordField, BorderLayout.EAST);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BorderLayout());
        fieldsPanel.add(chPanel, BorderLayout.NORTH);
        fieldsPanel.add(pwPanel, BorderLayout.SOUTH);
        innerPanel.add(fieldsPanel, BorderLayout.CENTER);

        loginButton.setText("Login");

        loginButton.addActionListener(e -> loginAction(cardnumberField.getText(),passwordField.getPassword()));

        innerPanel.add(loginButton, BorderLayout.SOUTH);
    }
    private void loginAction(String cardholderId, char[] password){
        boolean validated = true;
        //try to use dao and validate box text with db
        if(validated){
            as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK);
        }
        else{
            failure.setText("Login attempt failed. Try again.");
        }
    }
}
