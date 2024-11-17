package org.iitcs.controller.state.concretestate;

import org.iitcs.controller.ApplicationContext;
import org.iitcs.gui.panels.childpanel.*;
import org.iitcs.controller.state.State;

public class LoginState extends State {

    public LoginState(ApplicationContext context) {
        super(context);
    }

    @Override
    public void updateUI() {
        context.getUIFrame().packSimpleFrame(new LoginPanel());
    }
}

