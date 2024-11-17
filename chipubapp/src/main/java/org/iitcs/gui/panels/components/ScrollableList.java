package org.iitcs.gui.panels.components;

import org.iitcs.database.dao.models.AbstractCplEntity;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.controller.ApplicationContext;
import org.iitcs.controller.state.concretestate.DetailBookState;
import org.iitcs.controller.state.concretestate.DetailCardholderState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScrollableList extends JScrollPane {
    ApplicationContext context;
    public ScrollableList(ApplicationContext context, DefaultListModel item, int w, int h){
        this.context = context;
        JList itemList = new JList(item);
        itemList.addMouseListener(doubleClickListAction(itemList));
        setViewportView(itemList);
        setPreferredSize(new Dimension(w, h));
        setVisible(true);
    }

    private MouseListener doubleClickListAction(JList list) {
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                    if (list.getSelectedIndex() != -1) {
                        int index = list.locationToIndex(evt.getPoint());
                        selectItem((AbstractCplEntity) list.getSelectedValue());
                    }
                }
            }
        };
        return ml;
    }
    public void selectItem(AbstractCplEntity item){
        if(item instanceof Book){
            context.getMemory().setBookFocus((Book)item);
            context.setState(new DetailBookState(context));
        }
        else{
            context.getMemory().setCardholderFocus((Cardholder) item);
            context.setState(new DetailCardholderState(context));
        }
    }
}
