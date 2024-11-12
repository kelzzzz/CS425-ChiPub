package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class CardholderDetailPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    public CardholderDetailPanel(Cardholder currentUser, ApplicationStateManager.GuiState lastState){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
        JLabel userDetail = new JLabel(currentUser.toStringJLabelDetail());
        add(userDetail);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> as.setState(lastState));
        add(backButton);
        setVisible(true);
        //TODO show currently checked out books and holds in a list
    }
    private void packAdminButtons(){

    }
}
