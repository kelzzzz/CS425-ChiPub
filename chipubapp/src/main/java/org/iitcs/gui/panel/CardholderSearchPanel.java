package org.iitcs.gui.panel;

import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Cardholder;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Util.setGridBagConstraints;

public class CardholderSearchPanel extends AbstractPanel{
    CardholderDao dao;
    DefaultListModel<Cardholder> chs = new DefaultListModel<>();
    public CardholderSearchPanel(String lastSearchTerm){
        try{
            dao = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderSearchPanel tried to use uninitialized dao.");
        }

        //refreshSearchOnPageReentry(lastSearchTerm);
        setLayout(new BorderLayout());
        packInnerPanels(lastSearchTerm);
        setVisible(true);
    }

    private void refreshSearchOnPageReentry(String lastSearchTerm) {
        if(lastSearchTerm != null){
            searchCardholders(lastSearchTerm);
        }
    }

    private void packInnerPanels(String lastSearchTerm) {
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

        setGridBagConstraints(c,2,0,0);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButtonAction(searchBar));
        searchBarContainer.add(searchButton, c);

        add(searchBarContainer, BorderLayout.NORTH);
    }

    public void clickSearchButtonAction(JTextField searchBar){
        //as.setPersistedSearch(searchBar.getText());
        searchCardholders(searchBar.getText());
    }
    public void searchCardholders(String searchTerm){
        chs.clear();
        chs.addAll(dao.search(searchTerm));
        revalidate();
    }
}
