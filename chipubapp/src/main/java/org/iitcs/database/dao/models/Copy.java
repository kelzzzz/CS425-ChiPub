package org.iitcs.database.dao.models;

public class Copy extends AbstractCplEntity{
    long copyId;
    long bookId;
    long cardholderId;

    public Copy(long cid, long bid, long chid){
        copyId = cid;
        bookId = bid;
        cardholderId = chid;
    }
    @Override
    public String toStringJLabelDetail() {
        return "Copy ID: ".concat(String.valueOf(copyId));
    }
}
