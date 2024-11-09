package org.iitcs.gui;

import org.iitcs.database.dao.Book;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class BookDetailPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    BookDetailPanel(Book book){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
        JLabel bookDetail = new JLabel(book.toJLabelDetail());
        add(bookDetail);
        //TODO back button
        //TODO place hold button
        //TODO check out button, remove, update for admin
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK));
        add(backButton);
        setVisible(true);
    }
}
