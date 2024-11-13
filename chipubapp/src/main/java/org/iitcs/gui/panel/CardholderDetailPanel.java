package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class CardholderDetailPanel extends AbstractPanel {
    public CardholderDetailPanel(Cardholder currentUser, ApplicationStateManager.GuiState lastState){
        setLayout(new GridBagLayout());
        JLabel userDetail = new JLabel(currentUser.toStringJLabelDetail());
        add(userDetail);
        addBackButton();
        setVisible(true);
        //TODO show currently checked out books and holds in a list
    }
    private void packAdminButtons(){
        //TODO
    }

    private void packUserButtons(){
        //TODO read user object checkouts and holds into a jlist
    }
}
