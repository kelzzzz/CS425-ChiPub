package org.iitcs.database.dao.models;

public class Book extends AbstractCplEntity {
    private long bookId;
    private String isbn;
    private String title;
    private String authorLastName;
    private String authorFirstName;
    private String language;
    private String subject;
    private String genre;

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

    @Override
    public String toStringJLabelDetail(){
        StringBuilder sb = new StringBuilder();
        appendHtmlOpenTag(sb);
        appendLineBreakWithLabel(sb, "ISBN", isbn);
        appendLineBreakWithLabel(sb, "Title", title);
        appendLineBreakWithLabel(sb, "Author", authorFirstName.concat(" ").concat(authorLastName));
        appendLineBreakWithLabel(sb, "Language", language);
        appendLineBreakWithLabel(sb, "Subject", subject);
        appendLineBreakWithLabel(sb, "Genre", genre);
        appendHtmlClosingTag(sb);
        return sb.toString();
    }
    public long getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getLanguage() {
        return language;
    }

    public String getSubject() {
        return subject;
    }

    public String getGenre() {
        return genre;
    }
}
