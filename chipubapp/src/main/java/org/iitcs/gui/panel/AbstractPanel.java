package org.iitcs.gui.panel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

import java.awt.*;

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
        backButton.addActionListener(e -> as.setState(as.lastState));
        add(backButton);
    }

}
