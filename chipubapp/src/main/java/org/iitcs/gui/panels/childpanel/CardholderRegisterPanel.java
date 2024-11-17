package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.database.dao.models.CardholderAddress;
import org.iitcs.gui.panels.components.ComponentFactory;
import org.iitcs.gui.panels.parentpanel.CardholderFillableFormPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CardholderRegisterPanel extends CardholderFillableFormPanel {
    public CardholderRegisterPanel() {
        super();
        JButton registerButton = new JButton("Register Cardholder");

        //TODO This should happen in the DB, not the application
        int randomChid = createRandomChid();
        long randomCardNum = createRandomCardNumber();

        cardholderIdField.setText(String.valueOf(randomChid));
        cardholderIdField.setEditable(false);
        cardholderNumberField.setText(String.valueOf(randomCardNum));
        cardholderNumberField.setEditable(false);

        registerButton.addActionListener(e-> registerButtonAction(randomChid, randomCardNum));

        JPanel selections = new JPanel();

        selections.setLayout(new GridLayout(2,1,10,10));

        selections.add(registerButton);
        selections.add(ComponentFactory.createBackButton(context));

        add(selections,BorderLayout.SOUTH);
    }
    private void registerButtonAction(int chid, long cardNum){
        CardholderAddress addr = new CardholderAddress(Address_NumField.getText(), StreetField.getText(), AptField.getText(),
                CityField.getText(),StateField.getText(), ZipField.getText());
        Cardholder result = new Cardholder(chid, String.valueOf(cardNum), firstNameField.getText(),
                lastNameField.getText(), addr, EmailField.getText());
        boolean success = chd.save(result);
        if(success){
            JOptionPane.showMessageDialog(this,"Cardholder successfully registered.");
        }else{
                JOptionPane.showMessageDialog(this,"Cardholder registration failed.", "Registration Failure", JOptionPane.WARNING_MESSAGE, null);
        }
    }
    private int createRandomChid(){
        Random rand = new Random();
        return rand.nextInt(12000);
    }

    private long createRandomCardNumber(){
        Random rand = new Random();
        long min = 10000000L;
        long max = 100000000L;
        return min+((long)(rand.nextDouble()*(max-min)));
    }
}
