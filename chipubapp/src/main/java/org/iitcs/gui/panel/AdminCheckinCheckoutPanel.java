package org.iitcs.gui.panel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminCheckinCheckoutPanel extends AbstractPanel{

    BookDao bd;
    CardholderDao chd;
    ArrayList<Long> copyIds = new ArrayList<>();
    public AdminCheckinCheckoutPanel(Book book, boolean isCheckout){
        setLayout(new BorderLayout());
        try{
            bd = new BookDao();
            chd = new CardholderDao();
        }catch(InstantiationException e){

        }
        JPanel copyInformationContainer = new JPanel();
        copyInformationContainer.setLayout(new BorderLayout());
        JLabel copyId = new JLabel("CopyId: ");

        if(isCheckout){
            copyIds = bd.getAvailableCopyIdsForBook(book);
        }
        else{
            copyIds = bd.getCheckedOutCopyIdsForBook(book);
        }
        JLabel noCopiesAvailable = new JLabel("No copies for this title are available.");
        DefaultComboBoxModel<Long> cbCopyIdModel = new DefaultComboBoxModel<>(copyIds.toArray(new Long[0]));
        JComboBox<Long> ids = new JComboBox<>(cbCopyIdModel);

        copyInformationContainer.add(copyId, BorderLayout.WEST);
        if(copyIds.isEmpty()){
            copyInformationContainer.add(noCopiesAvailable, BorderLayout.EAST);
        }else{
            copyInformationContainer.add(ids, BorderLayout.EAST);
        }

        JPanel isbnInformationContainer = new JPanel();
        isbnInformationContainer.setLayout(new BorderLayout());
        JLabel isbn = new JLabel("ISBN: ");
        JTextField isbnBox = new JTextField(20);
        isbnBox.setText(book.getIsbn());
        isbnBox.setEditable(false);
        isbnInformationContainer.add(isbn,BorderLayout.WEST);
        isbnInformationContainer.add(isbnBox,BorderLayout.EAST);

        JPanel cardholderInformationPanel = new JPanel();
        cardholderInformationPanel.setLayout(new BorderLayout());
        JLabel cardholderId = new JLabel("CardholderID: ");
        JTextField chIdBox = new JTextField(20);
        cardholderInformationPanel.add(cardholderId,BorderLayout.WEST);
        cardholderInformationPanel.add(chIdBox,BorderLayout.EAST);

        JPanel fieldsContainer = new JPanel();
        fieldsContainer.setLayout(new BorderLayout());
        fieldsContainer.add(copyInformationContainer, BorderLayout.NORTH);
        fieldsContainer.add(isbnInformationContainer, BorderLayout.CENTER);
        fieldsContainer.add(cardholderInformationPanel, BorderLayout.SOUTH);

        add(fieldsContainer, BorderLayout.NORTH);
        if(isCheckout){
            JButton checkOutButton = new JButton("Check Out");
            checkOutButton.addActionListener(e->
                            checkoutAction(ids, Long.parseLong(chIdBox.getText())));


            if(copyIds.isEmpty()){
                checkOutButton.setEnabled(false);
            }
            add(checkOutButton, BorderLayout.CENTER);
        }
        else{
            JButton checkInButton = new JButton("Check In");
            checkInButton.addActionListener(e->checkinAction(ids, Long.parseLong(chIdBox.getText())));
            if(copyIds.isEmpty()){
                checkInButton.setEnabled(false);
            }
            add(checkInButton, BorderLayout.CENTER);
        }

        add(getBackButton(),BorderLayout.SOUTH);
    }
    public void checkoutAction(JComboBox<Long> ids, Long chId){
        bd.checkOut((Long) ids.getSelectedItem(),chId);
        if(bd.getQuerySuccessCode() == 1){
            JOptionPane.showMessageDialog(this,"Book successfully checked out.");
        }else{
            JOptionPane.showMessageDialog(this,"Check out request failed.", "Check Out Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        as.setUserFocus(chd.get(chId).get());
        as.setState(as.rewindState());
    }

    public void checkinAction(JComboBox<Long> ids, Long chId){
        bd.checkIn((Long) ids.getSelectedItem(), chId);
        if(bd.getQuerySuccessCode() == 1){
            JOptionPane.showMessageDialog(this,"Book successfully checked in.");
        }else{
            JOptionPane.showMessageDialog(this,"Check in request failed.", "Check In Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        as.setUserFocus(chd.get(chId).get());
        as.setState(as.rewindState());
    }
}
