package org.iitcs.gui;

import org.iitcs.database.dao.Book;
import org.iitcs.database.dao.BookDao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class BookSearchPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    BookDao dao;
    DefaultListModel<Book> books = new DefaultListModel<>();
    public BookSearchPanel(){
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

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks(searchBar.getText()));


        innerPanel.add(searchButton, c);
        add(innerPanel, BorderLayout.NORTH);

        JScrollPane scollPane = new JScrollPane(booksJlist);
        scollPane.setPreferredSize(new Dimension(200, 200));
        add(scollPane, BorderLayout.CENTER);

        setVisible(true);
    }
        public void searchBooks(String searchTerm){
            books.clear();
            books.addAll(dao.search(searchTerm));
            revalidate();
        }
}
