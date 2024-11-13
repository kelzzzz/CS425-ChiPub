package org.iitcs.database.dao.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Cardholder extends AbstractCplEntity {
    public Cardholder(long chid, String cardNum, String firstName, String lastName, CardholderAddress address, String email) {
        this.chid = chid;
        this.cardNum = cardNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }
    private Long chid;

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

    public Copy[] getCheckOuts() {
        return checkOuts;
    }

    private String cardNum;
    private String firstName;
    private String lastName;
    private CardholderAddress address;
    private String email;
    private String[] phoneNumbers;
    private Copy[] checkOuts;
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
    @Override
    public String toStringJLabelDetail() {
        StringBuilder sb = new StringBuilder();
        String cardholderNumber = String.valueOf(chid).concat(cardNum);
        appendHtmlOpenTag(sb);
        appendLineBreakWithLabel(sb, "Number", cardholderNumber);
        appendLineBreakWithLabel(sb, "First Name", firstName);
        appendLineBreakWithLabel(sb, "Last Name", lastName);
        appendLineBreakWithLabel(sb, "Address", "Not yet implemented");
        appendLineBreakWithLabel(sb, "Email", email);
        appendHtmlClosingTag(sb);
        return sb.toString();
    }
}

