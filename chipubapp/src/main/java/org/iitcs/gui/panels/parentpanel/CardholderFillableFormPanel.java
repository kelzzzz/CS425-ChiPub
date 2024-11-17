package org.iitcs.gui.panels.parentpanel;

import org.iitcs.database.dao.CardholderDao;

import javax.swing.*;
import java.awt.*;


public abstract class CardholderFillableFormPanel extends SimplePanel {
    protected CardholderDao chd;
    protected JLabel cardholderID = new JLabel("Cardholder ID: ");
    protected JTextField cardholderIdField = new JTextField(10);
    protected JLabel cardholderNumber = new JLabel("Cardholder Num: ");
    protected JTextField cardholderNumberField = new JTextField(10);
    protected JLabel firstName = new JLabel("First Name: ");
    protected JTextField firstNameField = new JTextField(10);
    protected JLabel lastName = new JLabel("Last Name: ");
    protected JTextField lastNameField = new JTextField(10);
    protected JLabel Address_Num = new JLabel("Address Num: ");
    protected JTextField Address_NumField = new JTextField(10);
    protected JLabel Street = new JLabel("Street: ");
    protected JTextField StreetField = new JTextField(10);
    protected JLabel Apt = new JLabel("Apt: ");
    protected JTextField AptField = new JTextField(10);
    protected JLabel City = new JLabel("City: ");
    protected JTextField CityField = new JTextField(10);

    protected JLabel State = new JLabel("State: ");
    protected JTextField StateField = new JTextField(10);
    protected JLabel Zip = new JLabel("Zip: ");
    protected JTextField ZipField = new JTextField(10);
    protected JLabel Email = new JLabel("Email: ");
    protected JTextField EmailField = new JTextField(10);
    public CardholderFillableFormPanel(){
        setLayout(new BorderLayout());
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new GridLayout(6,2,0,10));
        try{
            chd = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderFillableFormPanel tried to use uninitialized dao.");
        }

        packTopContainer(topContainer);
        add(topContainer, BorderLayout.NORTH);

    }

    private void packTopContainer(JPanel fieldsPanel) {
        fieldsPanel.add(getLabelPackagedWithField(cardholderID, cardholderIdField));
        fieldsPanel.add(getLabelPackagedWithField(cardholderNumber, cardholderNumberField));

        fieldsPanel.add(getLabelPackagedWithField(firstName, firstNameField));
        fieldsPanel.add(getLabelPackagedWithField(lastName, lastNameField));

        fieldsPanel.add(getLabelPackagedWithField(Address_Num, Address_NumField));
        fieldsPanel.add(getLabelPackagedWithField(Street, StreetField));

        fieldsPanel.add(getLabelPackagedWithField(Apt, AptField));
        fieldsPanel.add(getLabelPackagedWithField(City, CityField));

        fieldsPanel.add(getLabelPackagedWithField(State, StateField));
        fieldsPanel.add(getLabelPackagedWithField(Zip, ZipField));

        fieldsPanel.add(getLabelPackagedWithField(Email, EmailField));
    }

    public JPanel getLabelPackagedWithField(JLabel a, JTextField b){
        JPanel packedPanel = new JPanel();
        packedPanel.setLayout(new BorderLayout());
        packedPanel.add(a, BorderLayout.WEST);
        packedPanel.add(b, BorderLayout.EAST);
        return packedPanel;
    }
    public void setChd(CardholderDao chd) {
        this.chd = chd;
    }

    public void setCardholderID(JLabel cardholderID) {
        this.cardholderID = cardholderID;
    }

    public void setCardholderIdField(JTextField cardholderIdField) {
        this.cardholderIdField = cardholderIdField;
    }

    public void setCardholderNumber(JLabel cardholderNumber) {
        this.cardholderNumber = cardholderNumber;
    }

    public void setCardholderNumberField(JTextField cardholderNumberField) {this.cardholderNumberField = cardholderNumberField;}

    public void setFirstName(JLabel firstName) {
        this.firstName = firstName;
    }

    public void setFirstNameField(JTextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public void setLastName(JLabel lastName) {
        this.lastName = lastName;
    }

    public void setLastNameField(JTextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public void setAddress_Num(JLabel address_Num) {
        Address_Num = address_Num;
    }

    public void setAddress_NumField(JTextField address_NumField) {
        Address_NumField = address_NumField;
    }

    public void setStreet(JLabel street) {
        Street = street;
    }

    public void setStreetField(JTextField streetField) {
        StreetField = streetField;
    }

    public void setApt(JLabel apt) {
        Apt = apt;
    }

    public void setAptField(JTextField aptField) {
        AptField = aptField;
    }

    public void setCity(JLabel city) {
        City = city;
    }

    public void setCityField(JTextField cityField) {
        CityField = cityField;
    }

    public void setState(JLabel state) {
        State = state;
    }

    public void setStateField(JTextField stateField) {
        StateField = stateField;
    }

    public void setZip(JLabel zip) {
        Zip = zip;
    }

    public void setZipField(JTextField zipField) {
        ZipField = zipField;
    }

    public void setEmail(JLabel email) {
        Email = email;
    }

    public void setEmailField(JTextField emailField) {
        EmailField = emailField;
    }
}
