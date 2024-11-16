package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.database.dao.models.CardholderAddress;

import javax.swing.*;
import java.awt.*;

public class CardholderUpdatePanel extends CardholderEditInformationPanel{
    public CardholderUpdatePanel(Cardholder user) {
        super();
        JButton updateBtn = new JButton("Update User Info");
        JPanel selections = new JPanel();
        selections.setLayout(new GridLayout(2,1,10,10));

        cardholderIdField.setText(String.valueOf(user.getChid()));
        cardholderIdField.setEditable(false);
        cardholderNumberField.setText(String.valueOf(user.getCardNum()));
        cardholderNumberField.setEditable(false);

        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());

        Address_NumField.setText(user.getAddress().getAddrNum());
        StreetField.setText(user.getAddress().getAddrStrt());
        AptField.setText(user.getAddress().getAddrApt());
        CityField.setText(user.getAddress().getAddrCity());
        StateField.setText(user.getAddress().getAddrState());
        ZipField.setText(user.getAddress().getAddrZip());
        EmailField.setText(user.getEmail());

        updateBtn.addActionListener(e->updateButtonAction());

        selections.add(updateBtn);
        selections.add(getBackButton());

        add(selections,BorderLayout.CENTER);
    }
    private void updateButtonAction(){
        CardholderAddress addr = new CardholderAddress(Address_NumField.getText(), StreetField.getText(), AptField.getText(),
                CityField.getText(),StateField.getText(), ZipField.getText());
        Cardholder result = new Cardholder(Long.valueOf(cardholderIdField.getText()), cardholderNumberField.getText(), firstNameField.getText(),
                lastNameField.getText(), addr, EmailField.getText());
        chd.update(result, null);
        if(chd.getQuerySuccess() == true){
            JOptionPane.showMessageDialog(this,"Cardholder successfully updated.");
        }else{
            JOptionPane.showMessageDialog(this,"Cardholder update failed.", "Registration Failure", JOptionPane.WARNING_MESSAGE, null);
        }
        as.setUserFocus(chd.get(Long.parseLong(cardholderIdField.getText())).get());
    }
}
