package org.iitcs.gui.panel;

import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardPanel extends AbstractPanel{
    public AdminDashboardPanel(){
        JButton registerCardholder = new JButton("Register new cardholder");
        JButton lookupCardholder = new JButton("Look up cardholders");
        JButton lookupBooks = new JButton("Look up books");

        setLayout(new GridLayout(3,1, 20, 20));

        lookupCardholder.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.ADMIN_CARDHOLDER_SEARCH));
        lookupBooks.addActionListener(e-> as.setState(ApplicationStateManager.GuiState.SEARCH_BOOK));
        registerCardholder.addActionListener(e->as.setState(ApplicationStateManager.GuiState.ADMIN_REGISTER_CARDHOLDER));
        add(registerCardholder);
        add(lookupCardholder);
        add(lookupBooks);
    }
}
