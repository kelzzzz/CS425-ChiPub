package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.Util;

import javax.swing.*;
import java.awt.*;

public class CardholderDetailPanel extends AbstractPanel {
    DefaultListModel<Book> holds = new DefaultListModel<>();
    DefaultListModel<Book> checkOuts = new DefaultListModel<>();
    Cardholder currentUser;
    public CardholderDetailPanel(Cardholder currentUser, ApplicationStateManager.GuiState lastState){
        setLayout(new GridBagLayout());
        this.currentUser = currentUser;
        JLabel userDetail = new JLabel(currentUser.toStringJLabelDetail());
        add(userDetail);
        add(getBackButton());
        if(as.userContext == ApplicationStateManager.UserContext.CARDHOLDER){
            packUserDashboard();
        }
        else if(as.userContext == ApplicationStateManager.UserContext.ADMIN){
            packAdminDashboard();
        }
        setVisible(true);
    }
    private void packAdminDashboard(){
        //TODO
    }

    private void packUserCheckoutsAndHoldsDisplay(){
        holds.clear();
        holds.addAll(currentUser.getHolds());

        checkOuts.clear();
        checkOuts.addAll(currentUser.getCheckOuts());
        GridBagConstraints c = new GridBagConstraints();
        Util.setGridBagConstraints(c, 0,1,0);
        add(new JLabel("Holds"), c);
        Util.setGridBagConstraints(c, 0,2,30);
        add(getScrollableListOfBooks(holds, 200,50),c);
        Util.setGridBagConstraints(c, 0,3,0);
        add(new JLabel("Check outs"), c);
        Util.setGridBagConstraints(c, 0,4,30);
        add(getScrollableListOfBooks(checkOuts, 200, 50),c);
        revalidate();
    }
    private void packUserDashboard(){
        packUserCheckoutsAndHoldsDisplay();
    }
}
