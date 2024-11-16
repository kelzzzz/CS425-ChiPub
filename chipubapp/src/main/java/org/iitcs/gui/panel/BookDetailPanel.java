package org.iitcs.gui.panel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.gui.ApplicationStateManager.UserContext.ADMIN;
import static org.iitcs.gui.ApplicationStateManager.UserContext.CARDHOLDER;
import static org.iitcs.util.Util.setGridBagConstraints;

public class BookDetailPanel extends AbstractPanel {
    Book focusedBook;
    BookDao dao;

    public BookDetailPanel(Book book) {
        super();
        try{
            this.dao = new BookDao();
        }catch(InstantiationException e){
            //do something
        }
        this.focusedBook = book;
        this.setLayout(new BorderLayout());
        JLabel bookDetail = new JLabel(book.toStringJLabelDetail());
        add(bookDetail, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2,20,20));
        packButtonsBasedOnUserContext(buttonsPanel);

        add(buttonsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void packButtonsBasedOnUserContext(JPanel buttonsPanel) {
        if (as.userContext == ADMIN) {
            packAdminButtons(buttonsPanel);
        } else if (as.userContext == CARDHOLDER) {
            packUserButtons(buttonsPanel);
        }
        buttonsPanel.add(getBackButton());
    }

    private void packAdminButtons(JPanel buttonsPanel) {
        JButton checkOut = new JButton("Check Out");
        checkOut.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.ADMIN_CHECKOUT));
        buttonsPanel.add(checkOut);

        JButton checkIn = new JButton("Check In");
        checkIn.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.ADMIN_CHECKIN));
        buttonsPanel.add(checkIn);
    }

    private void packUserButtons(JPanel buttonsPanel) {
        if (as.getCurrentUser().getHoldIds().contains(focusedBook.getBookId())) {
            addCancelHoldButton(buttonsPanel);
        }else if(as.getCurrentUser().getCheckOutsIds().contains(focusedBook.getBookId())){
            //do nothin'
        }else{
            addPlaceHoldButton(buttonsPanel);
        }
    }

    private void addPlaceHoldButton(JPanel buttonsPanel) {
        JButton userPlaceHold = new JButton("Place Hold");
        userPlaceHold.addActionListener(e -> {
            try {
                placeHoldAction();
            } catch (InstantiationException ex) {
                logPanelException(ex, "Book detail panel tried to use uninitialized dao.");
            }
        });
        buttonsPanel.add(userPlaceHold);
    }

    private void placeHoldAction() throws InstantiationException {
        dao.placeHold(focusedBook, as.getCurrentUser());
        if(dao.getQuerySuccessCode() == 1){
            JOptionPane.showMessageDialog(this,"Hold successfully placed.");
        }else{
            JOptionPane.showMessageDialog(this,"Hold request failed.", "Hold Request Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        as.getCurrentUser().addBookToHolds(focusedBook);
    }

    private void addCancelHoldButton(JPanel buttonsPanel) {
        JButton userCancelHold = new JButton("Cancel Hold");
        userCancelHold.addActionListener(e -> {
            try {
                cancelHoldAction();
            } catch (InstantiationException ex) {
                logPanelException(ex, "Book detail panel tried to use uninitialized dao.");
            }
        });
        buttonsPanel.add(userCancelHold);
    }

    private void cancelHoldAction() throws InstantiationException {
        dao.cancelHold(focusedBook, as.getCurrentUser());
        if(dao.getQuerySuccessCode() == 1){
            JOptionPane.showMessageDialog(this,"Hold successfully cancelled.");
        }else{
            JOptionPane.showMessageDialog(this,"Hold cancellation failed.", "Hold Cancel Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        as.getCurrentUser().removeBookFromHoldsById(focusedBook.getBookId());
    }
}
