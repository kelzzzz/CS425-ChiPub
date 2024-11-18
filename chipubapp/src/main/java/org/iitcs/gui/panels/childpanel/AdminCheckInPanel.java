package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.panels.parentpanel.TransactionPanel;

import javax.swing.*;
import java.awt.*;

public class AdminCheckInPanel extends TransactionPanel {
    boolean success = false;
    public AdminCheckInPanel() {
        super();
    }

    @Override
    public void setCopyIds(Book book) {
        copyIds = bd.getCheckedOutCopyIdsForBook(book);
    }

    @Override
    public void addTranscactionButton(JComboBox idSelection) {
        JButton checkInButton = new JButton("Check In");
        checkInButton.addActionListener(e-> checkinAction(idSelection, Long.parseLong(chIdBox.getText())));
        setButtonClickable(checkInButton);
        add(checkInButton, BorderLayout.CENTER);
    }

    private void setButtonClickable(JButton checkInButton) {
        if(copyIds.isEmpty()){
            checkInButton.setEnabled(false);
        }
    }

    public void checkinAction(JComboBox<Long> ids, Long chId){
        success = bd.checkIn((Long) ids.getSelectedItem(), chId);
        showConfirmationMessage();
        context.getMemory().setCardholderFocus(chd.get(chId).get());
        context.setState(context.rewindState());
    }

    private void showConfirmationMessage() {
        if(success){
            JOptionPane.showMessageDialog(this,"Book successfully checked in.");
        }else{
            JOptionPane.showMessageDialog(this,"Check in request failed.", "Check In Failure", JOptionPane.WARNING_MESSAGE, null);
        }
    }
}
