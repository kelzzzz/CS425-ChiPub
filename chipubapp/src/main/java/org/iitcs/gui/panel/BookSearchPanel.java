package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.BookDao;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Util.setGridBagConstraints;

public class BookSearchPanel extends AbstractPanel {
    BookDao dao;
    DefaultListModel<Book> books = new DefaultListModel<>();
    public BookSearchPanel(String lastSearchTerm){
        try{
            dao = new BookDao();
        }catch(InstantiationException e){
            logPanelException(e, "BookSearchPanel tried to use uninitialized dao.");
        }

        refreshSearchOnPageReentry(lastSearchTerm);
        setLayout(new BorderLayout());
        packInnerPanels(lastSearchTerm);
        setVisible(true);
    }

    private void refreshSearchOnPageReentry(String lastSearchTerm) {
        if(lastSearchTerm != null){
            searchBooks(lastSearchTerm);
        }
    }

    private void packInnerPanels(String lastSearchTerm) {
        addSearchBarContainer();
        add(getScrollableListOfBooks(books),BorderLayout.SOUTH);
    }

    private void addSearchBarContainer() {
        JPanel searchBarContainer = new JPanel();
        searchBarContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        setGridBagConstraints(c,0,0,0);
        JLabel label = new JLabel("Search: ");
        searchBarContainer.add(label, c);

        setGridBagConstraints(c,1,0,0);
        JTextField searchBar = new JTextField(15);
        searchBarContainer.add(searchBar, c);

        setGridBagConstraints(c,2,0,0);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButtonAction(searchBar));
        searchBarContainer.add(searchButton, c);

        add(searchBarContainer, BorderLayout.NORTH);
    }

    public void clickSearchButtonAction(JTextField searchBar){
        as.setPersistedSearch(searchBar.getText());
        searchBooks(searchBar.getText());
    }
    public void searchBooks(String searchTerm){
        books.clear();
        books.addAll(dao.search(searchTerm));
        revalidate();
    }

}
