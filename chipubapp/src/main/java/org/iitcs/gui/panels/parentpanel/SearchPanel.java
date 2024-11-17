package org.iitcs.gui.panels.parentpanel;

import org.iitcs.database.dao.IDao;
import org.iitcs.gui.panels.components.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.iitcs.util.Util.setGridBagConstraints;

public abstract class SearchPanel extends PanelWithGreeting{
    protected IDao dao;
    protected DefaultListModel list;
    protected JLabel searchForLabel;
    public SearchPanel(){}

    protected void packTopContainer(GridBagConstraints c) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        packInnerPanels(container);
        add(container, c);
        setVisible(true);
    }

    private void packInnerPanels(JPanel container) {
        addSearchBarContainer(container);
        container.add(ComponentFactory.createScrollableList(context, list, 200, 200),BorderLayout.SOUTH);
    }

    private void addSearchBarContainer(JPanel container) {
        JPanel searchBarContainer = new JPanel();
        searchBarContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        searchBarContainer.add(searchForLabel, c);

        setGridBagConstraints(c,1,0,0);
        JTextField searchBar = new JTextField(15);
        searchBarContainer.add(searchBar, c);

        setGridBagConstraints(c,2,0,0);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> clickSearchButtonAction(searchBar));
        searchBar.addKeyListener(getEnterKeyListener(searchBar));

        searchBarContainer.add(searchButton, c);

        setGridBagConstraints(c,3,0,0);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch(searchBar));
        searchBarContainer.add(clearButton, c);

        container.add(searchBarContainer, BorderLayout.CENTER);
    }

    private KeyListener getEnterKeyListener(JTextField searchBar) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //no impl
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    clickSearchButtonAction(searchBar);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //no impl
            }
        };
    }

    public abstract void setContextList();
    public void clearSearch(JTextField searchBar){
        list.clear();
        searchBar.setText("");
        setContextList();
        revalidate();
        repaint();
    }
    public void clickSearchButtonAction(JTextField searchBar){
        searchBooks(searchBar.getText());
    }
    public void searchBooks(String searchTerm){
        list.clear();
        list.addAll(dao.search(searchTerm));
        setContextList();
        revalidate();
    }
}
