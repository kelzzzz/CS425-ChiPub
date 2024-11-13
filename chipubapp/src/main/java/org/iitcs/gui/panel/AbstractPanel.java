package org.iitcs.gui.panel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public abstract class AbstractPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(AbstractPanel.class);

    public AbstractPanel(){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
    }

    public void logPanelException(Exception e, String message){
        LOGGER.error(message);
        LOGGER.error(e.getMessage());
    }

    public void addBackButton(){
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> as.setState(as.rewindState()));
        add(backButton);
    }

    public JScrollPane getScrollableListOfBooks(DefaultListModel books) {
        JList booksJlist = new JList(books);
        booksJlist.addMouseListener(doubleClickListAction(booksJlist));
        JScrollPane scollPane = new JScrollPane(booksJlist);
        scollPane.setPreferredSize(new Dimension(200, 200));
        return scollPane;
    }

    private MouseListener doubleClickListAction(JList list) {
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                    if (list.getSelectedIndex() != -1) {
                        int index = list.locationToIndex(evt.getPoint());
                        selectBook((Book) list.getSelectedValue());
                    }
                }
            }
        };
        return ml;
    }
    public void selectBook(Book book){
        as.setBookDetailResponse(book);
        as.setState(ApplicationStateManager.GuiState.BOOK_DETAIL);
    }
}
