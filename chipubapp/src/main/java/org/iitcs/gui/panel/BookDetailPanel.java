package org.iitcs.gui.panel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

import static org.iitcs.gui.ApplicationStateManager.UserContext.ADMIN;
import static org.iitcs.gui.ApplicationStateManager.UserContext.CARDHOLDER;

public class BookDetailPanel extends AbstractPanel {
    Book focusedBook;

    public BookDetailPanel(Book book) {
        super();
        this.focusedBook = book;
        JLabel bookDetail = new JLabel(book.toStringJLabelDetail());
        add(bookDetail);

        //TODO check out button, remove, update for admin

        packButtonsBasedOnUserContext();
        setVisible(true);
    }

    private void packButtonsBasedOnUserContext() {
        addBackButton();
        if (as.userContext == ADMIN) {
            packAdminButtons();
        } else if (as.userContext == CARDHOLDER) {
            packUserButtons();
        }
    }

    private void packAdminButtons() {
        JButton checkOut = new JButton("Check Out"); //TODO --> checkout page, input ID and such
        add(checkOut);
    }

    private void packUserButtons() {
        if (as.getCurrentUser().getHoldIds().contains(focusedBook.getBookId())) {
            addCancelHoldButton();
        } else {
            addPlaceHoldButton();
        }
    }

    private void addPlaceHoldButton() {
        JButton userPlaceHold = new JButton("Place Hold");
        userPlaceHold.addActionListener(e -> {
            try {
                placeHoldAction();
            } catch (InstantiationException ex) {
                logPanelException(ex, "Book detail panel tried to use uninitialized dao.");
            }
        });
        add(userPlaceHold);
    }

    private void placeHoldAction() throws InstantiationException {
        BookDao dao = new BookDao();
        dao.placeHold(focusedBook, as.getCurrentUser());
        as.getCurrentUser().addBookToHolds(focusedBook);
    }

    private void addCancelHoldButton() {
        JButton userCancelHold = new JButton("Cancel Hold");
        userCancelHold.addActionListener(e -> {
            try {
                cancelHoldAction();
            } catch (InstantiationException ex) {
                logPanelException(ex, "Book detail panel tried to use uninitialized dao.");
            }
        });
        add(userCancelHold);
    }

    private void cancelHoldAction() throws InstantiationException {
        BookDao dao = new BookDao();
        dao.cancelHold(focusedBook, as.getCurrentUser());
        as.getCurrentUser().removeBookFromHoldsById(focusedBook.getBookId());
    }
}
