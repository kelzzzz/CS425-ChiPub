package org.iitcs.gui;

import org.iitcs.database.dao.Book;
import org.iitcs.database.dao.BookDao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class BookSearchPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    BookDao dao;
    DefaultListModel<Book> books = new DefaultListModel<>();
    public BookSearchPanel(String lastSearchTerm){
        try{
            dao = new BookDao();
        }catch(InstantiationException e){
            //do something
        }

        setSize(APP_W,APP_H);
        setLayout(new BorderLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        JLabel label = new JLabel("Search: ");
        innerPanel.add(label, c);

        c.gridx = 1;
        c.gridy = 0;
        JTextField searchBar = new JTextField(15);
        innerPanel.add(searchBar, c);

        c.gridx = 2;
        c.gridy = 0;

        JList booksJlist = new JList(books);

        if(lastSearchTerm != null){
            searchBooks(lastSearchTerm);
        }

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButton(searchBar));
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                    if (booksJlist.getSelectedIndex() != -1) {
                        int index = booksJlist.locationToIndex(evt.getPoint());
                        selectBook((Book) booksJlist.getSelectedValue());
                    }
                }
            }
        };

        booksJlist.addMouseListener(ml);

        innerPanel.add(searchButton, c);
        add(innerPanel, BorderLayout.NORTH);

        JScrollPane scollPane = new JScrollPane(booksJlist);
        scollPane.setPreferredSize(new Dimension(200, 200));
        add(scollPane, BorderLayout.CENTER);

        setVisible(true);
    }
        public void clickSearchButton(JTextField searchBar){
            as.setPersistedSearch(searchBar.getText());
            searchBooks(searchBar.getText());
        }
        public void searchBooks(String searchTerm){
            books.clear();
            books.addAll(dao.search(searchTerm));
            revalidate();
        }

        public void selectBook(Book book){
            as.setBookResponse(book);
            as.setState(ApplicationStateManager.GuiState.BOOK_INFO);
        }
}
