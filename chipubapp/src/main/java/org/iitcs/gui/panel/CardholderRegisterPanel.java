package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.database.dao.models.CardholderAddress;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CardholderRegisterPanel extends CardholderEditInformationPanel{
    public CardholderRegisterPanel() {
        super();
        JButton registerButton = new JButton("Register Cardholder");

        //TODO ---> THIS SHOULD HAPPEN IN THE DB, NOT THE APPLICATION!
        Random rand = new Random();
        int randomChid = rand.nextInt(12000);
        long min = 10000000L;
        long max = 100000000L;
        long randomCardNum = min+((long)(rand.nextDouble()*(max-min)));


        cardholderIdField.setText(String.valueOf(randomChid));
        cardholderIdField.setEditable(false);
        cardholderNumberField.setText(String.valueOf(randomCardNum));
        cardholderNumberField.setEditable(false);

        registerButton.addActionListener(e-> registerButtonAction(randomChid, randomCardNum));

        JPanel selections = new JPanel();

        selections.setLayout(new GridLayout(2,1,10,10));

        selections.add(registerButton);
        selections.add(getBackButton());

        add(selections,BorderLayout.SOUTH);
    }
    private void registerButtonAction(int chid, long cardNum){
        CardholderAddress addr = new CardholderAddress(Address_NumField.getText(), StreetField.getText(), AptField.getText(),
                CityField.getText(),StateField.getText(), ZipField.getText());
        Cardholder result = new Cardholder(chid, String.valueOf(cardNum), firstNameField.getText(),
                lastNameField.getText(), addr, EmailField.getText());
        chd.save(result);
        if(chd.getQuerySuccess() == true){
            JOptionPane.showMessageDialog(this,"Cardholder successfully registered.");
        }else{
                JOptionPane.showMessageDialog(this,"Cardholder registration failed.", "Registration Failure", JOptionPane.WARNING_MESSAGE, null);
        }
    }
}
