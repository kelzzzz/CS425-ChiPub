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
        if(as.userContext == ApplicationStateManager.UserContext.CARDHOLDER){
            this.currentUser = as.getCurrentUser();
        }
        else{
            this.currentUser = currentUser;
        }
        JLabel userDetail = new JLabel(currentUser.toStringJLabelDetail());
        add(userDetail);
        add(getBackButton());
        packUserDashboard();
        setVisible(true);
    }
    private void packUserDashboard(){
        packUserCheckoutsAndHoldsDisplay();
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
        add(getScrollableListOfItems(holds, 200,50),c);
        Util.setGridBagConstraints(c, 0,3,0);

        add(new JLabel("Check outs"), c);
        Util.setGridBagConstraints(c, 0,4,30);
        add(getScrollableListOfItems(checkOuts, 200, 50),c);

        revalidate();
        repaint();
    }
}
