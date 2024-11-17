package org.iitcs.gui.panels.parentpanel;

import org.iitcs.database.dao.models.Admin;
import org.iitcs.controller.state.concretestate.AdminDashState;
import org.iitcs.controller.state.concretestate.DetailCardholderState;

import javax.swing.*;
import java.awt.*;

import static org.iitcs.util.Util.setGridBagConstraints;

public abstract class PanelWithGreeting extends SimplePanel {
    private static final String GENERIC_GREETING = "<html><u>Hi, User!</u></html>";
    public PanelWithGreeting(){
        super();
        String greetingName = context.getMemory().getLoggedInUser().getFirstName();
        add(getGreetingContainer(greetingName),getConstraintsAnchorTopRight());
    }

    private static GridBagConstraints getConstraintsAnchorTopRight() {
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        return c;
    }

    protected JPanel getGreetingContainer(String greetingName){
        JPanel greetingContainer = new JPanel();
        addGreetingButtonToContainer(greetingName, greetingContainer);
        greetingContainer.setVisible(true);
        return greetingContainer;
    }

    private static void addGreetingButtonToContainer(String greetingName, JPanel greetingContainer) {
        JButton greetingButton = new JButton(getGreetingWithName(greetingName));
        setGenericGreetingIfNoName(greetingName, greetingButton);
        formatGreetingButton(greetingButton);

        if(context.getMemory().getLoggedInUser() instanceof Admin){
            greetingButton.addActionListener(e -> context.setState(new AdminDashState(context)));
        }else{
            greetingButton.addActionListener(e -> context.setState(new DetailCardholderState(context)));
        }

        greetingContainer.add(greetingButton);
        greetingButton.setVisible(true);
    }

    private static void formatGreetingButton(JButton greetingButton) {
        greetingButton.setFocusPainted(false);
        greetingButton.setMargin(new Insets(0, 0, 0, 0));
        greetingButton.setContentAreaFilled(false);
        greetingButton.setBorderPainted(false);
        greetingButton.setOpaque(false);
        greetingButton.setForeground(Color.BLUE);
    }

    private static void setGenericGreetingIfNoName(String greetingName, JButton greetingButton) {
        if(greetingName.isEmpty()){
            greetingButton.setText(GENERIC_GREETING);
        }
    }

    private static String getGreetingWithName(String greetingName) {
        return "<html><u>Hi, ".concat(greetingName).concat("!</u></html>");
    }
}
