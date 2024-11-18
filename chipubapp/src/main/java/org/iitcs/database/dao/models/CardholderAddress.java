package org.iitcs.database.dao.models;

public class CardholderAddress {
    private final String addrNum;

    private final String addrStrt;
    private final String addrApt;
    private final String addrCity;
    private final String addrState;
    private final String addrZip;

    public CardholderAddress(String addr_num, String addr_street, String addr_apt, String addr_city, String addr_state, String addr_zip) {
        this.addrNum = addr_num;
        this.addrStrt = addr_street;
        this.addrApt = addr_apt;
        this.addrCity = addr_city;
        this.addrState = addr_state;
        this.addrZip = addr_zip;
    }

    public String getFormattedAddress(){
        StringBuilder sb = new StringBuilder();
        sb.append(addrNum).append(" ").append(addrStrt);
        if(!addrApt.isEmpty()) {
            sb.append(" ").append(addrApt);
        }
        sb.append(", ").append(addrCity).append(", ").append(addrState).append(" ").append(addrZip);
        return sb.toString();
    }

    public String getAddrNum() {
        return addrNum;
    }

    public String getAddrStrt() {
        return addrStrt;
    }

    public String getAddrApt() {
        return addrApt;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public String getAddrState() {
        return addrState;
    }

    public String getAddrZip() {
        return addrZip;
    }
}
