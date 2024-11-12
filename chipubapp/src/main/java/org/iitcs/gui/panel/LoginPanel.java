package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Admin;
import org.iitcs.database.dao.CardholderDao;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.PropertiesLoader;

import javax.swing.*;
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

        JLabel cardholderLabel = new JLabel("Username: "); //cardnums aren't unique, so will need to do like, cID + lastname
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
        boolean validated = false;

        try{
            CardholderDao chd = new CardholderDao();
            if(cardholderId.equals(PropertiesLoader.getInstance().getDbAdminUsername())
                    && new String(password).equals(PropertiesLoader.getInstance().getDbAdminPassword())){

                as.setUserContext(ApplicationStateManager.UserContext.ADMIN);
                as.setCurrentUser(new Admin());
                validated = true;

            }else if(chd.validateCredentials(cardholderId, new String(password))){
                as.setUserContext(ApplicationStateManager.UserContext.CARDHOLDER);
                as.setCurrentUser(chd.get(chd.getChIDFromUsername(cardholderId)).get());
                validated = true;
            }
        }catch(InstantiationException e){
            //do something
        }

        if(validated){
            as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK);
        }
        else{
            failure.setText("Login attempt failed. Try again.");
        }
    }
}
