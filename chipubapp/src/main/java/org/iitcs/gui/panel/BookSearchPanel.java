package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.BookDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;

import static org.iitcs.util.Util.setGridBagConstraints;

public class BookSearchPanel extends AbstractPanel {
    BookDao dao;
    DefaultListModel<Book> books;
    public BookSearchPanel(){
        try{
            dao = new BookDao();
        }catch(InstantiationException e){
            logPanelException(e, "BookSearchPanel tried to use uninitialized dao.");
        }
        books = as.lastBookList;
        //refreshSearchOnPageReentry(lastSearchTerm);
        setLayout(new BorderLayout());
        packInnerPanels();
        setVisible(true);
    }

    private void refreshSearchOnPageReentry(String lastSearchTerm) {
        if(lastSearchTerm != null){
            searchBooks(lastSearchTerm);
        }
    }

    private void packInnerPanels() {
        addSearchBarContainer();
        add(getScrollableListOfItems(books, 200, 200),BorderLayout.SOUTH);
    }

    private void addSearchBarContainer() {
        JPanel searchBarContainer = new JPanel();
        searchBarContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        setGridBagConstraints(c,0,0,0);
        JLabel label = new JLabel("Search for book: ");
        searchBarContainer.add(label, c);

        setGridBagConstraints(c,1,0,0);
        JTextField searchBar = new JTextField(15);
        searchBarContainer.add(searchBar, c);

        setGridBagConstraints(c,2,0,0);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButtonAction(searchBar));
        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //no impl
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    clickSearchButtonAction(searchBar);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        searchBarContainer.add(searchButton, c);

        setGridBagConstraints(c,3,0,0);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch(searchBar));
        searchBarContainer.add(clearButton, c);

        add(searchBarContainer, BorderLayout.NORTH);
    }
    public void clearSearch(JTextField searchBar){
        books.clear();
        searchBar.setText("");
        as.lastBookList = books;
        revalidate();
        repaint();
    }
    public void clickSearchButtonAction(JTextField searchBar){
        searchBooks(searchBar.getText());
    }
    public void searchBooks(String searchTerm){
        books.clear();
        books.addAll(dao.search(searchTerm));
        as.lastBookList = books;
        revalidate();
    }

}
