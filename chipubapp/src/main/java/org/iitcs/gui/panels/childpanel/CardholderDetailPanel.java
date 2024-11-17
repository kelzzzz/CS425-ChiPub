package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Admin;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.panels.parentpanel.SimplePanel;
import org.iitcs.gui.panels.components.ComponentFactory;
import org.iitcs.controller.state.concretestate.CardholderUpdateState;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CardholderDetailPanel extends SimplePanel {
    DefaultListModel<Book> holds = new DefaultListModel<>();
    DefaultListModel<Book> checkOuts = new DefaultListModel<>();
    Cardholder currentUser;
    CardholderDao chd;
    public CardholderDetailPanel(){
        setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2,20,20));

        try{
            chd = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderDetailPanel tried to use uninitialized dao.");
        }
        if(context.getMemory().getLoggedInUser() instanceof Admin){
            this.currentUser = context.getMemory().getCardholderFocus();
            JButton closeAccButton = new JButton("Close this account");
            closeAccButton.addActionListener(e-> deleteButtonAction());
            buttonsPanel.add(closeAccButton);
            JButton updateAccButton = new JButton("Update account");
            updateAccButton.addActionListener(e->context.setState(new CardholderUpdateState(context)));
            buttonsPanel.add(updateAccButton);
        }

        else{
            this.currentUser = context.getMemory().getLoggedInUser();
        }

        JLabel userDetail = new JLabel(this.currentUser.toStringJLabelDetail());
        add(userDetail, BorderLayout.NORTH);
        buttonsPanel.add(ComponentFactory.createBackButton(context));
        add(buttonsPanel, BorderLayout.SOUTH);
        packUserDashboard();
        setVisible(true);
    }
    private void packUserDashboard(){
        packUserCheckoutsAndHoldsDisplay();
    }
    private void packUserCheckoutsAndHoldsDisplay(){
        holds.clear();
        holds.addAll(currentUser.getHolds());

        checkOuts.clear();
        checkOuts.addAll(currentUser.getCheckOuts());

        JPanel holdsCheckOuts = new JPanel();
        holdsCheckOuts.setLayout(new GridLayout(4,1));
        holdsCheckOuts.add(new JLabel("Holds"));
        holdsCheckOuts.add(ComponentFactory.createScrollableList(context, holds, 200,60));

        holdsCheckOuts.add(new JLabel("Check outs"));
        holdsCheckOuts.add(ComponentFactory.createScrollableList(context,checkOuts, 200, 60));

        add(holdsCheckOuts, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    private void deleteButtonAction(){
        Object[] options = {"Yes",
                "No"};
        AtomicInteger choice = new AtomicInteger();
        choice.set(JOptionPane.showOptionDialog(this,
                "Are you sure you want to delete this cardholder? This cannot be undone.",
                "Delete Cardholder",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]));
        if(choice.get() == 0){
            chd.delete(currentUser, null);
            JOptionPane.showMessageDialog(this,"User was successfully deleted.");
        }
    }
}
