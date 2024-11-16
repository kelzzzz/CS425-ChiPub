package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Admin;
import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.PropertiesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.iitcs.util.Util.setGridBagConstraints;

public class LoginPanel extends AbstractPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    JPanel loginComponentContainer = new JPanel();
    JTextField cardnumberField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JButton loginButton = new JButton();
    JLabel failureLabel = new JLabel();
    public LoginPanel(){
        setLayout(new GridBagLayout());
        packComponentContainer();
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c,0,0,0);

        ImageIcon imageIcon = new ImageIcon(LoginPanel.class.getClassLoader().getResource("chipub_logo.png"));
        JLabel label = new JLabel(imageIcon);
        add(label);

        setGridBagConstraints(c,0,1,0);
        add(loginComponentContainer, c);

        setGridBagConstraints(c,0,2,0);
        add(failureLabel, c);

        setVisible(true);
    }

    private void packComponentContainer(){
        loginComponentContainer.setLayout(new BorderLayout());
        packHeaderLabel();
        packFields();
        packLoginButton();
    }

    private void packHeaderLabel() {
        JLabel loginHeaderLabel = new JLabel("Log in", SwingConstants.CENTER);
        loginComponentContainer.add(loginHeaderLabel, BorderLayout.NORTH);
    }

    private void packFields() {
        JPanel fieldContainer = getFormattedFieldContainer(createFieldPanel("Username: ", cardnumberField), createFieldPanel("Password: ", passwordField));
        loginComponentContainer.add(fieldContainer, BorderLayout.CENTER);
    }

    private void packLoginButton() {
        loginButton.setText("Login");
        loginButton.addActionListener(e -> loginAction(cardnumberField.getText(),passwordField.getPassword()));
        loginComponentContainer.add(loginButton, BorderLayout.SOUTH);
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //no impl
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loginAction(cardnumberField.getText(),passwordField.getPassword());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private static JPanel getFormattedFieldContainer(JPanel cardHolderFieldContainer, JPanel passwordFieldContainer) {
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new BorderLayout());
        infoContainer.add(cardHolderFieldContainer, BorderLayout.NORTH);
        infoContainer.add(passwordFieldContainer, BorderLayout.SOUTH);
        return infoContainer;
    }

    private JPanel createFieldPanel(String text, JTextField field) {
        JPanel fieldContainer = new JPanel();
        fieldContainer.setLayout(new BorderLayout());

        JLabel fieldLabel = new JLabel(text);
        fieldContainer.add(fieldLabel, BorderLayout.WEST);
        fieldContainer.add(field, BorderLayout.EAST);
        return fieldContainer;
    }

    private void loginAction(String userName, char[] password){
        boolean validated = false;

        try{
            CardholderDao chd = new CardholderDao();

            if(isAdminCredentials(userName, password)){
                validated = sendUserContextResponse(ApplicationStateManager.UserContext.ADMIN, new Admin());
            }else if(isUserCredentials(userName, password, chd)){
                Cardholder user = chd.get(chd.getChIDFromUsername(userName)).get();
                validated = sendUserContextResponse(ApplicationStateManager.UserContext.CARDHOLDER,
                        user);
            }

        }catch(InstantiationException e){
            logPanelException(e, "Login Panel tried to use uninitialized cardholder dao.");
        }

        if(validated){
            as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK);
        }
        else{
            showFailureMessage();
        }
    }

    private void showFailureMessage() {
        failureLabel.setText("Login attempt failed. Try again.");
    }

    private boolean sendUserContextResponse(ApplicationStateManager.UserContext ctx, Cardholder user){
        as.setUserContext(ctx);
        as.setCurrentUser(user);
        return true;
    }
    private static boolean isUserCredentials(String cardholderId, char[] password, CardholderDao chd) {
        return chd.validateCredentials(cardholderId, new String(password));
    }

    private static boolean isAdminCredentials(String cardholderId, char[] password) {
        return cardholderId.equals(PropertiesLoader.getInstance().getDbAdminUsername())
                && new String(password).equals(PropertiesLoader.getInstance().getDbAdminPassword());
    }
}
