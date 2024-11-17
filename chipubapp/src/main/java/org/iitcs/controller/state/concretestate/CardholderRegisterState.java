package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.CardholderRegisterPanel;
import org.iitcs.controller.state.State;

public class CardholderRegisterState extends State {

    public CardholderRegisterState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new CardholderRegisterPanel());
    }
}
