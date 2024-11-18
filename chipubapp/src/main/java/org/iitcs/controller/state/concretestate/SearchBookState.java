package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.controller.state.State;
import org.iitcs.gui.panels.childpanel.SearchBookPanel;

public class SearchBookState extends State {

    public SearchBookState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new SearchBookPanel());
    }

}
