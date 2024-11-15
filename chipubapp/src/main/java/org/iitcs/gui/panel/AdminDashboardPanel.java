package org.iitcs.gui.panel;

import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

public class AdminDashboardPanel extends AbstractPanel{
    public AdminDashboardPanel(){
        JButton registerCardholder = new JButton("Register new cardholder");
        JButton lookupCardholder = new JButton("Look up cardholders");
        JButton lookupBooks = new JButton("Look up books");
        lookupCardholder.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.ADMIN_CARDHOLDER_SEARCH));
        lookupBooks.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK));
        add(registerCardholder);
        add(lookupCardholder);
        add(lookupBooks);
    }
}
