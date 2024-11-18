package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.CardholderDetailPanel;
import org.iitcs.controller.state.State;

public class DetailCardholderState extends State {

    public DetailCardholderState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new CardholderDetailPanel());
    }
}
