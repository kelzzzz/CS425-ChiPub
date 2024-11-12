package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.gui.ApplicationStateManager.UserContext.ADMIN;
import static org.iitcs.gui.ApplicationStateManager.UserContext.CARDHOLDER;
import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class BookDetailPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    public BookDetailPanel(Book book){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
        JLabel bookDetail = new JLabel(book.toStringJLabelDetail());
        add(bookDetail);
        //TODO back button
        //TODO place hold button
        //TODO check out button, remove, update for admin
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK));
        add(backButton);

        if(as.userContext == ADMIN){
            packAdminButtons();
        }else if(as.userContext == CARDHOLDER){
            packUserButtons();
        }

        setVisible(true);
    }
    private void packAdminButtons(){
        JButton checkOut = new JButton("Check Out"); //TODO --> checkout page, input ID and such
        add(checkOut);
    }
    private void packUserButtons(){
        JButton userPlaceHold = new JButton("Place Hold");
        add(userPlaceHold);
    }
}
