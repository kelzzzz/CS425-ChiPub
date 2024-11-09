package org.iitcs.database.dao;

public class Book {
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

    public String toJLabelDetail(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        appendIfNotNull(sb, "ISBN", isbn);
        appendIfNotNull(sb, "Title", title);
        appendIfNotNull(sb, "Author", authorFirstName.concat(" ").concat(authorLastName));
        appendIfNotNull(sb, "Language", language);
        appendIfNotNull(sb, "Subject", subject);
        appendIfNotNull(sb, "Genre", genre);
        sb.append("</html>");
        return sb.toString();
    }
    public void appendIfNotNull(StringBuilder sb, String tag, String s){
        if(s!=null){
            sb.append(tag.concat(": ").concat(s).concat("<br/>"));
        }else{
            sb.append(tag.concat(": ").concat( "N/A").concat("<br/>"));
        }
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
