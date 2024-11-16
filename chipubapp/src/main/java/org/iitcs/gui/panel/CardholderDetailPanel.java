package org.iitcs.gui.panel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CardholderDetailPanel extends AbstractPanel {
    DefaultListModel<Book> holds = new DefaultListModel<>();
    DefaultListModel<Book> checkOuts = new DefaultListModel<>();
    Cardholder currentUser;
    CardholderDao chd;
    public CardholderDetailPanel(Cardholder currentUser, ApplicationStateManager.GuiState lastState){
        setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2,20,20));

        try{
            chd = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "CardholderDetailPanel tried to use uninitialized dao.");
        }
        if(as.userContext == ApplicationStateManager.UserContext.CARDHOLDER){
            this.currentUser = as.getCurrentUser();
        }

        else{
            this.currentUser = currentUser;
            JButton closeAccButton = new JButton("Close this account");
            closeAccButton.addActionListener(e-> deleteButtonAction());
            buttonsPanel.add(closeAccButton);
            JButton updateAccButton = new JButton("Update account");
            updateAccButton.addActionListener(e->as.setState(ApplicationStateManager.GuiState.ADMIN_EDIT_CARDHOLDER));
            buttonsPanel.add(updateAccButton);
        }

        JLabel userDetail = new JLabel(this.currentUser.toStringJLabelDetail());
        add(userDetail, BorderLayout.NORTH);
        buttonsPanel.add(getBackButton());
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
        holdsCheckOuts.add(getScrollableListOfItems(holds, 200,60));

        holdsCheckOuts.add(new JLabel("Check outs"));
        holdsCheckOuts.add(getScrollableListOfItems(checkOuts, 200, 60));

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
