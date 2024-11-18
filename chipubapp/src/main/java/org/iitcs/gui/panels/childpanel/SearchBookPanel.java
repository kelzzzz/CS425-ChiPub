package org.iitcs.gui.panels.childpanel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.gui.panels.parentpanel.SearchPanel;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Util.setGridBagConstraints;

public class SearchBookPanel extends SearchPanel {
    public SearchBookPanel(){
        super();
        try{
            dao = new BookDao();
        }catch(InstantiationException e){
            logPanelException(e, "SearchBookPanel tried to use uninitialized dao.");
        }
        list = context.getMemory().getLastBookList();
        searchForLabel = new JLabel("Enter book keyword: ");
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0, 1, 0);
        packTopContainer(c);
    }

    @Override
    public void setContextList() {
        context.getMemory().setLastBookList(list);
    }
}
