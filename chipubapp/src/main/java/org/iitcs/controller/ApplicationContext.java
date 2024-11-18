package org.iitcs.controller;

import org.iitcs.controller.state.Persistence;
import org.iitcs.controller.state.concretestate.SearchBookState;
import org.iitcs.controller.state.concretestate.SearchCardholderState;
import org.iitcs.controller.state.State;
import org.iitcs.gui.ApplicationFrame;

import java.util.Stack;
public class ApplicationContext {
    private static ApplicationContext instance = null;
    private ApplicationFrame fw = new ApplicationFrame();
    protected Persistence memory = new Persistence();
    private State state;
    private Stack<State> stateRecord = new Stack<State>();
    private ApplicationContext(){}
    public static synchronized ApplicationContext getInstance(){
        if(instance == null){
            instance = new ApplicationContext();
        }
        return instance;
    }
    public ApplicationFrame getUIFrame() {
        return fw;
    }
    public State getState() {
        return state;
    }
    public void setState(State currentState) {
        this.state = currentState;
        recordPastStates();
        currentState.updateUI();
    }

    private void recordPastStates() {
        if(this.state instanceof SearchBookState || this.state instanceof SearchCardholderState){
            this.stateRecord.clear();
        }
        this.stateRecord.add(this.state);
    }

    public State rewindState(){
        this.stateRecord.pop();
        return this.stateRecord.pop();
    }
    public Persistence getMemory() {
        return memory;
    }
}
