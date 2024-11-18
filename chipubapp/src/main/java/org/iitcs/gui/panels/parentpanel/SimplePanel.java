package org.iitcs.gui.panels.parentpanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.controller.ApplicationContext;
import org.iitcs.database.dao.models.Admin;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public abstract class SimplePanel extends JPanel {
    public static ApplicationContext context = ApplicationContext.getInstance();
    protected static final Logger LOGGER = LogManager.getLogger(SimplePanel.class);

    public SimplePanel(){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
    }

    public void logPanelException(Exception e, String message){
        LOGGER.error(message);
        LOGGER.error(e.getMessage());
    }

    protected boolean isLoggedInAsAdmin() {
        return context.getMemory().getLoggedInUser() instanceof Admin;
    }
}
