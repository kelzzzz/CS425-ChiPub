package org.iitcs.controller.state;

import org.iitcs.controller.ApplicationContext;

public abstract class State {
    protected ApplicationContext context;

    public State(ApplicationContext context){
        this.context = context;
    }

    public abstract void updateUI();
}
