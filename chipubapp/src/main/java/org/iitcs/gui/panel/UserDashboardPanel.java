package org.iitcs.gui.panel;

import org.iitcs.database.dao.models.Cardholder;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;

public class UserDashboardPanel extends JPanel {

    UserDashboardPanel(Cardholder user){
        setSize(APP_W,APP_H);
        setLayout(new GridBagLayout());
        JLabel userDetailLabel = new JLabel((user.toStringJLabelDetail()));
        add(userDetailLabel);
    }
}
