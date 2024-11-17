package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.panels.parentpanel.TransactionPanel;

import javax.swing.*;
import java.awt.*;

public class AdminCheckoutPanel extends TransactionPanel {
    boolean success = false;
    public AdminCheckoutPanel() {
        super();
    }

    @Override
    public void setCopyIds(Book book) {
        copyIds = bd.getAvailableCopyIdsForBook(book);
    }

    @Override
    public void addTranscactionButton(JComboBox idSelection) {
        JButton checkOutButton = new JButton("Check Out");
        checkOutButton.addActionListener(e-> checkoutAction(idSelection, Long.parseLong(chIdBox.getText())));
        setButtonVisible(checkOutButton);
        add(checkOutButton, BorderLayout.CENTER);
    }

    private void setButtonVisible(JButton checkOutButton) {
        if(copyIds.isEmpty()){
            checkOutButton.setEnabled(false);
        }
    }

    public void checkoutAction(JComboBox<Long> ids, Long chId){
        success = bd.checkOut((Long) ids.getSelectedItem(),chId);
        showConfirmationMessage();
        context.getMemory().setCardholderFocus(chd.get(chId).get());
        context.setState(context.rewindState());
    }

    private void showConfirmationMessage() {
        if(success){
            JOptionPane.showMessageDialog(this,"Book successfully checked out.");
        }else{
            JOptionPane.showMessageDialog(this,"Check out request failed.", "Check Out Failure", JOptionPane.WARNING_MESSAGE, null);
        }
    }
}
