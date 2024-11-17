package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.CardholderDao;
import org.iitcs.gui.panels.parentpanel.SearchPanel;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Util.setGridBagConstraints;

public class SearchCardholderPanel extends SearchPanel {
    public SearchCardholderPanel(){
        super();
        try{
            dao = new CardholderDao();
        }catch(InstantiationException e){
            logPanelException(e, "SearchCardholderPanel tried to use uninitialized dao.");
        }
        list = context.getMemory().getLastUserList();
        searchForLabel = new JLabel("Enter cardholder keyword: ");
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0, 1, 0);
        packTopContainer(c);
    }

    @Override
    public void setContextList() {
        context.getMemory().setLastUserList(list);
    }
}