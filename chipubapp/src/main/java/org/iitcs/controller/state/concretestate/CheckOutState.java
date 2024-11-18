package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.controller.state.State;
import org.iitcs.gui.panels.childpanel.AdminCheckoutPanel;

public class CheckOutState extends State {

    public CheckOutState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new AdminCheckoutPanel());
    }
}
