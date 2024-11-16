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
    JLabel lastName = new JLabel("Last Name: ");
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
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(6,2,0,10));
        try{
            chd = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderEditInformationPanel tried to use uninitialized dao.");
        }

        fieldsPanel.add(getLabelPackedWithField(cardholderID, cardholderIdField));
        fieldsPanel.add(getLabelPackedWithField(cardholderNumber, cardholderNumberField));

        fieldsPanel.add(getLabelPackedWithField(firstName, firstNameField));
        fieldsPanel.add(getLabelPackedWithField(lastName, lastNameField));

        fieldsPanel.add(getLabelPackedWithField(Address_Num, Address_NumField));
        fieldsPanel.add(getLabelPackedWithField(Street, StreetField));

        fieldsPanel.add(getLabelPackedWithField(Apt, AptField));
        fieldsPanel.add(getLabelPackedWithField(City, CityField));

        fieldsPanel.add(getLabelPackedWithField(State, StateField));
        fieldsPanel.add(getLabelPackedWithField(Zip, ZipField));

        fieldsPanel.add(getLabelPackedWithField(Email, EmailField));

        add(fieldsPanel, BorderLayout.NORTH);

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
