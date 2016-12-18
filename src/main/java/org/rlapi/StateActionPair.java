package org.rlapi;

/**
 *
 * @author Alexandre Lima
 */
public class StateActionPair {
    
    private final String state;
    private final String action;

    public StateActionPair(String state, String action) {
        this.state = state;
        this.action = action;
    }

    public String getState() {
        return state;
    }

    public String getAction() {
        return action;
    }
    
}
