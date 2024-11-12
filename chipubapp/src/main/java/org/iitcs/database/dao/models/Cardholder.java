package org.iitcs.database.dao.models;

public class Cardholder extends CplEntity {
    public Cardholder(long chid, String cardNum, String firstName, String lastName, CardholderAddress address, String email) {
        this.chid = chid;
        this.cardNum = cardNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }
    private Long chid;
    private String cardNum;
    private String firstName;
    private String lastName;
    private CardholderAddress address;
    private String email;
    private String[] phoneNumbers;
    private Copy[] checkOuts;
    private Book[] holds;

    public String getFirstName() {
        return firstName;
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

