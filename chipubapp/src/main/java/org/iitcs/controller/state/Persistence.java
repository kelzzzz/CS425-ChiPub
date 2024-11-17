package org.iitcs.controller.state;

import org.iitcs.database.dao.models.AbstractCplEntity;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;

import javax.swing.*;

public class Persistence {

    private Book bookFocus;
    private Cardholder cardholderFocus;
    private Cardholder loggedInUser;
    private DefaultListModel<Book> lastBookList = new DefaultListModel<Book>();
    private DefaultListModel<Cardholder> lastUserList = new DefaultListModel<Cardholder>();

    public Persistence() {}

    public Cardholder getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Cardholder loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public DefaultListModel<Book> getLastBookList() {
        return lastBookList;
    }

    public void setLastBookList(DefaultListModel<Book> lastBookList) {
        this.lastBookList = lastBookList;
    }

    public DefaultListModel<Cardholder> getLastUserList() {
        return lastUserList;
    }

    public void setLastUserList(DefaultListModel<Cardholder> lastUserList) {
        this.lastUserList = lastUserList;
    }

    public Cardholder getCardholderFocus() {
        return cardholderFocus;
    }

    public void setCardholderFocus(Cardholder cardholderFocus) {
        this.cardholderFocus = cardholderFocus;
    }
    public Book getBookFocus() {
        return bookFocus;
    }

    public void setBookFocus(Book bookFocus) {
        this.bookFocus = bookFocus;
    }
}