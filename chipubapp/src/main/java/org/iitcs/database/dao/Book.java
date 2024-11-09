package org.iitcs.database.dao;

public class Book {
    long bookId;
    String isbn;
    String title;
    String authorLastName;
    String authorFirstName;
    String language;
    String subject;
    String genre;

    public Book(long id, String isbn, String title, String aln, String afn, String lang, String subj, String gnr){
        this.bookId = id;
        this.isbn = isbn;
        this.title = title;
        this.authorLastName = aln;
        this.authorFirstName = afn;
        this.language = lang;
        this.subject = subj;
        this.genre = gnr;
    }

    @Override
    public String toString(){
        return String.format("ISBN: %-10s | Title: %s", isbn, title);
    }
}
