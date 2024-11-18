package org.iitcs.gui.panels.parentpanel;

import org.iitcs.database.dao.BookDao;
import org.iitcs.database.dao.CardholderDao;
import org.iitcs.database.dao.models.Book;
import org.iitcs.gui.panels.components.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class TransactionPanel extends SimplePanel{
    protected BookDao bd;
    protected CardholderDao chd;
    protected ArrayList<Long> copyIds = new ArrayList<>();
    protected final JLabel noCopiesAvailable = new JLabel("No copies for this title are available.");
    protected final JLabel copyIdLabel = new JLabel("CopyId: ");
    protected final JLabel isbn = new JLabel("ISBN: ");
    protected final JTextField isbnBox = new JTextField(20);
    protected final JLabel cardholderId = new JLabel("CardholderID: ");
    protected final JTextField chIdBox = new JTextField(20);
    public TransactionPanel() {
        super();
        setLayout(new BorderLayout());
        try {
            bd = new BookDao();
            chd = new CardholderDao();
        } catch (InstantiationException e) {
            LOGGER.error("Transaction Panel attempted to use uninitialized dao objects.");
        }
        setCopyIds(context.getMemory().getBookFocus());
        DefaultComboBoxModel<Long> cbCopyIdModel = new DefaultComboBoxModel<>(copyIds.toArray(new Long[0]));
        JComboBox<Long> ids = new JComboBox<>(cbCopyIdModel);

        add(getTopContainer(context.getMemory().getBookFocus(), ids), BorderLayout.NORTH);
        addTranscactionButton(ids);
        add(ComponentFactory.createBackButton(context),BorderLayout.SOUTH);
    }

    private JPanel getTopContainer(Book book, JComboBox<Long> ids) {
        JPanel copyInformationContainer = new JPanel();
        copyInformationContainer.setLayout(new BorderLayout());

        JPanel isbnInformationContainer = new JPanel();
        isbnInformationContainer.setLayout(new BorderLayout());

        JPanel cardholderInformationPanel = new JPanel();
        cardholderInformationPanel.setLayout(new BorderLayout());

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BorderLayout());

        packCopyInformationContainer(copyInformationContainer, ids);
        packIsbnContainer(book, isbnInformationContainer);
        packCardholderContainer(cardholderInformationPanel, cardholderId, chIdBox);
        packTopContainer(copyInformationContainer, isbnInformationContainer, cardholderInformationPanel, topContainer);
        return topContainer;
    }

    private static void packTopContainer(JPanel copyInformationContainer, JPanel isbnInformationContainer, JPanel cardholderInformationPanel, JPanel fieldsContainer) {
        fieldsContainer.add(copyInformationContainer, BorderLayout.NORTH);
        fieldsContainer.add(isbnInformationContainer, BorderLayout.CENTER);
        fieldsContainer.add(cardholderInformationPanel, BorderLayout.SOUTH);
    }

    private void packCardholderContainer(JPanel cardholderInformationPanel, JLabel cardholderId, JTextField chIdBox) {
        cardholderInformationPanel.add(cardholderId, BorderLayout.WEST);
        cardholderInformationPanel.add(chIdBox, BorderLayout.EAST);
    }

    private void packIsbnContainer(Book book, JPanel isbnInformationContainer) {
        isbnBox.setText(book.getIsbn());
        isbnBox.setEditable(false);
        packCardholderContainer(isbnInformationContainer, isbn, isbnBox);
    }

    private void packCopyInformationContainer(JPanel copyInformationContainer, JComboBox<Long> ids) {
        copyInformationContainer.add(copyIdLabel, BorderLayout.WEST);

        if(copyIds.isEmpty()){
            copyInformationContainer.add(noCopiesAvailable, BorderLayout.EAST);
        }else{
            copyInformationContainer.add(ids, BorderLayout.EAST);
        }
    }

    public abstract void setCopyIds(Book book);
    public abstract void addTranscactionButton(JComboBox idSelection);
}
