package org.iitcs.gui.panel;

import org.iitcs.database.dao.CardholderDao;

import javax.swing.*;
import java.awt.*;


public abstract class CardholderEditInformationPanel extends AbstractPanel{
    CardholderDao chd;
    JLabel cardholderID = new JLabel("Cardholder ID: ");
    JTextField cardholderIdField = new JTextField(10);
    JLabel cardholderNumber = new JLabel("Cardholder Num: ");
    JTextField cardholderNumberField = new JTextField(10);
    JLabel firstName = new JLabel("First Name: ");
    JTextField firstNameField = new JTextField(10);
    JLabel lastName = new JLabel("Last Name");
    JTextField lastNameField = new JTextField(10);
    JLabel Address_Num = new JLabel("Address Num: ");
    JTextField Address_NumField = new JTextField(10);

    JLabel Street = new JLabel("Street: ");
    JTextField StreetField = new JTextField(10);
    JLabel Apt = new JLabel("Apt: ");
    JTextField AptField = new JTextField(10);
    JLabel City = new JLabel("City: ");
    JTextField CityField = new JTextField(10);
    JLabel State = new JLabel("State: ");
    JTextField StateField = new JTextField(10);
    JLabel Zip = new JLabel("Zip: ");
    JTextField ZipField = new JTextField(10);
    JLabel Email = new JLabel("Email: ");
    JTextField EmailField = new JTextField(10);
    public CardholderEditInformationPanel(){
        setLayout(new BorderLayout());
        try{
            chd = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderEditInformationPanel tried to use uninitialized dao.");
        }

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        upperPanel.add(getRowPanel(getLabelPackedWithField(cardholderID, cardholderIdField),
                getLabelPackedWithField(cardholderNumber, cardholderNumberField)),
                BorderLayout.NORTH);
        upperPanel.add(getRowPanel(getLabelPackedWithField(firstName, firstNameField),
                getLabelPackedWithField(lastName, lastNameField))
                ,BorderLayout.CENTER);
        upperPanel.add(getRowPanel(getLabelPackedWithField(Address_Num, Address_NumField),
                getLabelPackedWithField(Street, StreetField)),
                BorderLayout.SOUTH);

        lowerPanel.add(getRowPanel(getLabelPackedWithField(Apt, AptField),
                        getLabelPackedWithField(City, CityField)),
                BorderLayout.NORTH);
        lowerPanel.add(getRowPanel(getLabelPackedWithField(State, StateField),
                        getLabelPackedWithField(Zip, ZipField))
                ,BorderLayout.CENTER);
        lowerPanel.add(getRowPanel(getLabelPackedWithField(Email, EmailField),
                        new JPanel()),
                BorderLayout.SOUTH);

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
    }
    public JPanel getLabelPackedWithField(JLabel a, JTextField b){
        JPanel packedPanel = new JPanel();
        packedPanel.setLayout(new BorderLayout());
        packedPanel.add(a, BorderLayout.WEST);
        packedPanel.add(b, BorderLayout.EAST);
        return packedPanel;
    }
    public JPanel getRowPanel(JPanel a, JPanel b){
        JPanel packedPanel = new JPanel();
        packedPanel.setLayout(new BorderLayout());
        packedPanel.add(a, BorderLayout.WEST);
        packedPanel.add(b, BorderLayout.EAST);
        return packedPanel;
    }
}
