package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.AdminDashboardPanel;
import org.iitcs.controller.state.State;

public class AdminDashState extends State {

    public AdminDashState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new AdminDashboardPanel());
    }
}
