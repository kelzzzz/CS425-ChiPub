package org.iitcs.gui.panels.components;

import org.iitcs.controller.ApplicationContext;

import javax.swing.*;

public class ComponentFactory {
    public static JScrollPane createScrollableList(ApplicationContext context, DefaultListModel item, int w, int h) {
        return new ScrollableList(context, item, w, h);
    }
    public static JButton createBackButton(ApplicationContext context){
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> context.setState(context.rewindState()));
        return backButton;
    }
}
