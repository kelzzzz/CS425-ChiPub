package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.database.dao.models.CardholderAddress;

import javax.swing.*;
import java.awt.*;

public class CardholderUpdatePanel extends CardholderEditInformationPanel{
    public CardholderUpdatePanel(Cardholder user) {
        super();
        JButton updateBtn = new JButton("Update User Info");
        add(updateBtn, BorderLayout.SOUTH);
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

        JPanel selections = new JPanel();
        selections.setLayout(new BorderLayout());
        selections.add(updateBtn, BorderLayout.WEST);
        selections.add(getBackButton(), BorderLayout.EAST);

        add(selections,BorderLayout.SOUTH);
    }
    private void updateButtonAction(){
        CardholderAddress addr = new CardholderAddress(Address_NumField.getText(), StreetField.getText(), AptField.getText(),
                CityField.getText(),StateField.getText(), ZipField.getText());
        Cardholder result = new Cardholder(Long.valueOf(cardholderIdField.getText()), cardholderNumberField.getText(), firstNameField.getText(),
                lastNameField.getText(), addr, EmailField.getText());
        chd.update(result, null);
    }
}
