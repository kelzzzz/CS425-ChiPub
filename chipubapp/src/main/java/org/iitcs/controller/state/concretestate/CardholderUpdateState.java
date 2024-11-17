package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.CardholderUpdatePanel;
import org.iitcs.controller.state.State;

public class CardholderUpdateState extends State {

    public CardholderUpdateState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new CardholderUpdatePanel(context.getMemory().getCardholderFocus()));
    }
}
