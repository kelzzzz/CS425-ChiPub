package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.AdminCheckInPanel;
import org.iitcs.controller.state.State;

public class CheckInState extends State {

    public CheckInState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new AdminCheckInPanel());
    }
}
