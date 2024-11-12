package org.iitcs.gui.panel;

import org.iitcs.gui.ApplicationStateManager;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public abstract class GenericCPLPanel extends JPanel {
    ApplicationStateManager as = ApplicationStateManager.getInstance();

    public GenericCPLPanel(){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
    }
}
