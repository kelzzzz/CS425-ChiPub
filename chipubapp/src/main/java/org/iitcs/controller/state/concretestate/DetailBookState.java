package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.BookDetailPanel;
import org.iitcs.controller.state.State;

public class DetailBookState extends State {

    public DetailBookState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new BookDetailPanel());
    }
}
