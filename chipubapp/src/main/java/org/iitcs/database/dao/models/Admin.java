package org.iitcs.database.dao.models;

public class Admin extends Cardholder {
    public Admin(){
        //TODO Have admin information stored in DB; this class is a temporary solution for now
        super(99999,"admin", "Administrator", "", null, "admin@cpl.com");
    }
}
