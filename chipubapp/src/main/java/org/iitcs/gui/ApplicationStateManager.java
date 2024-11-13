package org.iitcs.gui;

import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.panel.*;

public class ApplicationStateManager {
    private static ApplicationStateManager instance = null;
    private ApplicationFrame fw = new ApplicationFrame();
    public GuiState state;
    public GuiState lastState;
    public UserContext userContext;
    private Cardholder currentUser;
    private Book bookDetailResponse;
    private String persistedSearch = null;
    private ApplicationStateManager(){}
    public static synchronized ApplicationStateManager getInstance(){
        if(instance == null){
            instance = new ApplicationStateManager();
        }
        return instance;
    }

    public enum GuiState {
        LOGIN, SEARCH_BOOK, ADV_SEARCH, BOOK_DETAIL,
        USER_DETAIL, ADMIN_CHECKOUT, ADMIN_BOOK_ADD_REMOVE,
        ADMIN_REGISTER_CARDHOLDER, ADMIN_CARDHOLDER_SEARCH
    }
    public enum UserContext {
        ADMIN, CARDHOLDER, GUEST
    }
    public void setState(GuiState state){
        this.lastState = this.state;
        this.state = state;

        switch(state){
            case LOGIN:
                fw.setSimplePanel(new LoginPanel());
                break;
            case SEARCH_BOOK:
                fw.setPanelWithMenu(new BookSearchPanel(persistedSearch), currentUser.getFirstName());
                break;
            case BOOK_DETAIL:
                fw.setPanelWithMenu(new BookDetailPanel(bookDetailResponse), currentUser.getFirstName());
                break;
            case USER_DETAIL:
                fw.setSimplePanel(new CardholderDetailPanel(currentUser, lastState));
                break;
        }
    }
    public Cardholder getCurrentUser() {
        return currentUser;
    }

    public void setUserContext(UserContext userContext){
        this.userContext = userContext;
    }
    public void setBookDetailResponse(Book book) {this.bookDetailResponse = book;}
    public void setPersistedSearch(String searchTerm) {this.persistedSearch = searchTerm;}
    public String getPersistedSearch(){return this.persistedSearch;}
    public void setCurrentUser(Cardholder currentUser) {
        this.currentUser = currentUser;
    }
}
