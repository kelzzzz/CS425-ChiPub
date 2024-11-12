package org.iitcs.database.dao.models;

public class CardholderAddress {
    private String addr_num;

    public CardholderAddress(String addr_num, String addr_street, String addr_apt, String addr_city, String addr_state, String addr_zip) {
        this.addr_num = addr_num;
        this.addr_street = addr_street;
        this.addr_apt = addr_apt;
        this.addr_city = addr_city;
        this.addr_state = addr_state;
        this.addr_zip = addr_zip;
    }

    public String getFormattedAddress(){
        return this.addr_apt;
    }

    private String addr_street;
    private String addr_apt;
    private String addr_city;
    private String addr_state;
    private String  addr_zip;
}
