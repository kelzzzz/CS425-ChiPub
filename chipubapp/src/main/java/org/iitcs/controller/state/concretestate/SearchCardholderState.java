package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.controller.state.State;
import org.iitcs.gui.panels.childpanel.SearchCardholderPanel;

public class SearchCardholderState extends State {

    public SearchCardholderState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new SearchCardholderPanel());
    }
}
