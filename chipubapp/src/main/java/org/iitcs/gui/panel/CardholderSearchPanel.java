package org.iitcs.gui.panel;

import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Cardholder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.iitcs.util.Util.setGridBagConstraints;

public class CardholderSearchPanel extends AbstractPanel{
    CardholderDao dao;
    DefaultListModel<Cardholder> chs = new DefaultListModel<>();
    public CardholderSearchPanel(){
        try{
            dao = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderSearchPanel tried to use uninitialized dao.");
        }
        chs = as.lastUserList;
        //refreshSearchOnPageReentry(lastSearchTerm);
        setLayout(new BorderLayout());
        packInnerPanels();
        setVisible(true);
    }

    private void refreshSearchOnPageReentry(String lastSearchTerm) {
        if(lastSearchTerm != null){
            searchCardholders(lastSearchTerm);
        }
    }

    private void packInnerPanels() {
        addSearchBarContainer();
        add(getScrollableListOfItems(chs, 200, 200),BorderLayout.SOUTH);
    }

    private void addSearchBarContainer() {
        JPanel searchBarContainer = new JPanel();
        searchBarContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        setGridBagConstraints(c,0,0,0);
        JLabel label = new JLabel("Search for member: ");
        searchBarContainer.add(label, c);

        setGridBagConstraints(c,1,0,0);
        JTextField searchBar = new JTextField(15);
        searchBarContainer.add(searchBar, c);

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

        setGridBagConstraints(c,2,0,0);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButtonAction(searchBar));
        searchBarContainer.add(searchButton, c);

        setGridBagConstraints(c,3,0,0);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch(searchBar));
        searchBarContainer.add(clearButton, c);
        add(searchBarContainer, BorderLayout.NORTH);
    }

    public void clickSearchButtonAction(JTextField searchBar){
        searchCardholders(searchBar.getText());
    }
    public void clearSearch(JTextField searchBar){
        chs.clear();
        searchBar.setText("");
        as.lastUserList = chs;
        revalidate();
        repaint();
    }
    public void searchCardholders(String searchTerm){
        chs.clear();
        chs.addAll(dao.search(searchTerm));
        as.lastUserList = chs;
        revalidate();
    }
}
