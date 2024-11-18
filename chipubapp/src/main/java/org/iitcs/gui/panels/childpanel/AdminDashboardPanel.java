package org.iitcs.gui.panels.childpanel;

import org.iitcs.gui.panels.parentpanel.SimplePanel;
import org.iitcs.controller.state.concretestate.CardholderRegisterState;
import org.iitcs.controller.state.concretestate.SearchBookState;
import org.iitcs.controller.state.concretestate.SearchCardholderState;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardPanel extends SimplePanel {
    public AdminDashboardPanel(){
        setLayout(new GridLayout(3,1, 20, 20));

        JButton registerCardholder = new JButton("Register new cardholder");
        JButton lookupCardholder = new JButton("Look up cardholders");
        JButton lookupBooks = new JButton("Look up books");

        lookupCardholder.addActionListener(e-> context.setState(new SearchCardholderState(context)));
        lookupBooks.addActionListener(e-> context.setState(new SearchBookState(context)));
        registerCardholder.addActionListener(e->context.setState(new CardholderRegisterState(context)));

        add(registerCardholder);
        add(lookupCardholder);
        add(lookupBooks);
    }
}
