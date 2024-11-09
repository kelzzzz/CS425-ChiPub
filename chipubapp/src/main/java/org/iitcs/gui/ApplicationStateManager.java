package org.iitcs.gui;

import org.iitcs.database.dao.Book;

public class ApplicationStateManager {
    private static ApplicationStateManager instance = null;
    private ApplicationFrame fw = new ApplicationFrame();;
    public GuiState state;
    public UserContext userContext;
    private Book bookResponse;
    private String persistedSearch = null;
    private ApplicationStateManager(){}
    public static synchronized ApplicationStateManager getInstance(){
        if(instance == null){
            instance = new ApplicationStateManager();
        }
        return instance;
    }

    public enum GuiState {
        LOGIN, SEARCH_BOOK, SEARCH_CARDHOLDER, ADD_BOOK, ADV_SEARCH, BOOK_INFO, CARDHOLDER_INFO
    }
    public enum UserContext {
        ADMIN, CARDHOLDER, GUEST
    }
    public void setState(GuiState state){
        this.state = state;

        switch(state){
            case LOGIN:
                fw.setSimplePanel(new LoginPanel());
                break;
            case SEARCH_BOOK:
                fw.setPanelWithMenu(new BookSearchPanel(persistedSearch));
                break;
            case BOOK_INFO:
                fw.setPanelWithMenu(new BookDetailPanel(bookResponse));
                break;
        }
    }

    public void setUserContext(UserContext userContext){
        this.userContext = userContext;
    }
    public void setBookResponse(Book book) {this.bookResponse = book;}
    public void setPersistedSearch(String searchTerm) {this.persistedSearch = searchTerm;}
    public String getPersistedSearch(){return this.persistedSearch;}
}
