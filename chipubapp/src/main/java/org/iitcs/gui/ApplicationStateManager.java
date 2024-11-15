package org.iitcs.gui;

import org.iitcs.database.dao.models.AbstractCplEntity;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.panel.*;

import java.util.Stack;

import static org.iitcs.gui.ApplicationStateManager.GuiState.ADMIN_CARDHOLDER_SEARCH;
import static org.iitcs.gui.ApplicationStateManager.GuiState.SEARCH_BOOK;

public class ApplicationStateManager {
    private static ApplicationStateManager instance = null;
    private ApplicationFrame fw = new ApplicationFrame();
    public GuiState state;
    public Stack<GuiState> stateTrail = new Stack<>();
    public GuiState lastState;
    public UserContext userContext;
    private Cardholder currentUser;

    public Cardholder getUserFocus() {
        return userFocus;
    }

    private Cardholder userFocus;
    private AbstractCplEntity itemDetailResponse;
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
        USER_DETAIL, ADMIN_CHECKOUT, ADMIN_CHECKIN, ADMIN_BOOK_ADD_REMOVE,
        ADMIN_REGISTER_CARDHOLDER, ADMIN_CARDHOLDER_SEARCH, ADMIN_DASHBOARD
    }
    public enum UserContext {
        ADMIN, CARDHOLDER, GUEST
    }
    public GuiState rewindState(){
        this.stateTrail.pop();
        return this.stateTrail.pop();
    }
    public void setState(GuiState state){
        this.state = state;
        if(this.state == SEARCH_BOOK || this.state == ADMIN_CARDHOLDER_SEARCH){
            this.stateTrail.clear();
        }
        this.stateTrail.add(this.state);
        System.out.println(stateTrail);
        switch(state){
            case LOGIN:
                fw.packSimpleFrame(new LoginPanel());
                break;
            case SEARCH_BOOK:
                fw.packFrameWithUserDetailButton(new BookSearchPanel(persistedSearch), currentUser.getFirstName());
                break;
            case BOOK_DETAIL:
                fw.packFrameWithUserDetailButton(new BookDetailPanel((Book) itemDetailResponse), currentUser.getFirstName());
                break;
            case USER_DETAIL:
                fw.packSimpleFrame(new CardholderDetailPanel(userFocus, lastState));
                break;
            case ADMIN_CHECKOUT:
                fw.packSimpleFrame(new AdminCheckinCheckoutPanel((Book) itemDetailResponse, true));
                break;
            case ADMIN_CHECKIN:
                fw.packSimpleFrame(new AdminCheckinCheckoutPanel((Book) itemDetailResponse, false));
                break;
            case ADMIN_DASHBOARD:
                fw.packSimpleFrame(new AdminDashboardPanel());
                break;
            case ADMIN_CARDHOLDER_SEARCH:
                fw.packFrameWithUserDetailButton(new CardholderSearchPanel(persistedSearch), currentUser.getFirstName());
                break;
        }
    }
    public Cardholder getCurrentUser() {
        return currentUser;
    }

    public void setUserContext(UserContext userContext){
        this.userContext = userContext;
    }
    public void setItemDetailResponse(AbstractCplEntity item) {this.itemDetailResponse = item;}
    public void setPersistedSearch(String searchTerm) {this.persistedSearch = searchTerm;}
    public String getPersistedSearch(){return this.persistedSearch;}
    public void setCurrentUser(Cardholder currentUser) {
        this.currentUser = currentUser;
    }
    public void setUserFocus(Cardholder userFocus){this.userFocus = userFocus;}
}
