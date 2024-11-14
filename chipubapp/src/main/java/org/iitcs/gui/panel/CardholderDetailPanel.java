package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.Util;

import javax.swing.*;
import java.awt.*;

public class CardholderDetailPanel extends AbstractPanel {
    DefaultListModel<Book> books = new DefaultListModel<>();
    Cardholder currentUser;
    public CardholderDetailPanel(Cardholder currentUser, ApplicationStateManager.GuiState lastState){
        setLayout(new GridBagLayout());
        this.currentUser = currentUser;
        JLabel userDetail = new JLabel(currentUser.toStringJLabelDetail());
        add(userDetail);
        addBackButton();
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
        books.clear();
        books.addAll(currentUser.getHolds());
        GridBagConstraints c = new GridBagConstraints();
        Util.setGridBagConstraints(c, 0,1,0);
        add(getScrollableListOfBooks(books),c);
        revalidate();
    }
    private void packUserDashboard(){
        packUserCheckoutsAndHoldsDisplay();
    }
}
