package org.iitcs.database.dao.models;

import java.util.ArrayList;

public class Cardholder extends AbstractCplEntity {
    public Cardholder(long chid, String cardNum, String firstName, String lastName, CardholderAddress address, String email) {
        this.chid = chid;
        this.cardNum = cardNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }
    private final Long chid;

    public Long getChid() {
        return chid;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getLastName() {
        return lastName;
    }

    public CardholderAddress getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public ArrayList<Book> getCheckOuts() {
        return checkOuts;
    }

    private final String cardNum;
    private final String firstName;
    private final String lastName;
    private final CardholderAddress address;
    private final String email;
    private String[] phoneNumbers;
    private ArrayList<Book> checkOuts = new ArrayList<>();
    private ArrayList<Book> holds = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }
    public ArrayList<Book> getHolds() {
        return holds;
    }
    public ArrayList<Long> getHoldIds(){
        ArrayList<Long> holdIds = new ArrayList<>();
        for(Book book : holds){
            holdIds.add(book.getBookId());
        }
        return holdIds;
    }
    public ArrayList<Long> getCheckOutsIds(){
        ArrayList<Long> checkOutIds = new ArrayList<>();
        for(Book book : checkOuts){
            checkOutIds.add(book.getBookId());
        }
        return checkOutIds;
    }
    public void removeBookFromHoldsById(Long id){
        Book remove = null;
        for(Book b : holds){
            if(b.getBookId()==id){
                remove = b;
            }
        }
        holds.remove(remove);
    }
    public void addBookToHolds(Book book){
        holds.add(book);
    }
    public void setHolds(ArrayList<Book> holds){
        this.holds = holds;
    }
    public void setCheckOuts(ArrayList<Book> checkOuts){
        this.checkOuts = checkOuts;
    }
    @Override
    public String toStringJLabelDetail() {
        StringBuilder sb = new StringBuilder();
        String cardholderNumber = String.valueOf(chid).concat(" ").concat(cardNum);
        appendHtmlOpenTag(sb);
        appendLineBreakWithLabel(sb, "Number", cardholderNumber);
        appendLineBreakWithLabel(sb, "First Name", firstName);
        appendLineBreakWithLabel(sb, "Last Name", lastName);
        appendLineBreakWithLabel(sb, "Address", address.getFormattedAddress());
        appendLineBreakWithLabel(sb, "Email", email);
        appendHtmlClosingTag(sb);
        return sb.toString();
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String fn = firstName;
        String ln = lastName;
        if(firstName.isEmpty()){
            fn = "[No first name on file]";
        }
        if(lastName.isEmpty()){
            ln = "[No last name on file]";
        }
        sb.append(chid).append(" ").append(cardNum).append(" | ").append(fn).append(" ").append(ln);
        return sb.toString();
    }
}

