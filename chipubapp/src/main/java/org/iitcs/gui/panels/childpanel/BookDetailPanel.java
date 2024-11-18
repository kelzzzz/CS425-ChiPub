package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.models.Admin;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.panels.components.ComponentFactory;
import org.iitcs.gui.panels.parentpanel.SimplePanel;
import org.iitcs.controller.state.concretestate.CheckInState;
import org.iitcs.controller.state.concretestate.CheckOutState;

import javax.swing.*;

import java.awt.*;

public class BookDetailPanel extends SimplePanel {
    Book focusedBook;
    BookDao dao;
    Cardholder currentUser;

    public BookDetailPanel() {
        super();
        try{
            this.dao = new BookDao();
        }catch(InstantiationException e){
            LOGGER.error("Book detail panel tried to use uninitialized dao");
        }
        this.currentUser = context.getMemory().getLoggedInUser();
        this.focusedBook = context.getMemory().getBookFocus();

        this.setLayout(new BorderLayout());

        add(getButtonsPanel(), BorderLayout.SOUTH);
        addBookDetail();

        setVisible(true);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2,20,20));
        packButtonsBasedOnUserContext(buttonsPanel);
        return buttonsPanel;
    }

    private void addBookDetail() {
        JLabel bookDetail = new JLabel(focusedBook.toStringJLabelDetail());
        add(bookDetail, BorderLayout.NORTH);
    }

    private void packButtonsBasedOnUserContext(JPanel buttonsPanel) {
        if (isLoggedInAsAdmin()) {
            packAdminButtons(buttonsPanel);
        } else{
            packUserButtons(buttonsPanel);
        }
        buttonsPanel.add(ComponentFactory.createBackButton(context));
    }

    private void packAdminButtons(JPanel buttonsPanel) {
        JButton checkOut = new JButton("Check Out");
        checkOut.addActionListener(e-> context.setState(new CheckOutState(context)));
        buttonsPanel.add(checkOut);

        JButton checkIn = new JButton("Check In");
        checkIn.addActionListener(e-> context.setState(new CheckInState(context)));
        buttonsPanel.add(checkIn);
    }

    private void packUserButtons(JPanel buttonsPanel) {
        if (currentUser.getHoldIds().contains(focusedBook.getBookId())) {
            addCancelHoldButton(buttonsPanel);
        }else if(currentUser.getCheckOutsIds().contains(focusedBook.getBookId())){
            //do nothin'
        }else{
            addPlaceHoldButton(buttonsPanel);
        }
    }

    private void addPlaceHoldButton(JPanel buttonsPanel) {
        JButton userPlaceHold = new JButton("Place Hold");
        userPlaceHold.addActionListener(e -> placeHoldAction());
        buttonsPanel.add(userPlaceHold);
    }

    private void placeHoldAction() {
        boolean success = dao.placeHold(focusedBook, context.getMemory().getLoggedInUser());
        if(success){
            JOptionPane.showMessageDialog(this,"Hold successfully placed.");
        }else{
            JOptionPane.showMessageDialog(this,"Hold request failed.", "Hold Request Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        currentUser.addBookToHolds(focusedBook);
    }

    private void addCancelHoldButton(JPanel buttonsPanel) {
        JButton userCancelHold = new JButton("Cancel Hold");
        userCancelHold.addActionListener(e -> cancelHoldAction());
        buttonsPanel.add(userCancelHold);
    }

    private void cancelHoldAction() {
        boolean success = dao.cancelHold(focusedBook, currentUser);
        if(success){
            JOptionPane.showMessageDialog(this,"Hold successfully cancelled.");
        }else{
            JOptionPane.showMessageDialog(this,"Hold cancellation failed.", "Hold Cancel Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        currentUser.removeBookFromHoldsById(focusedBook.getBookId());
    }
}
