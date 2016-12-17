package org.rlapi;

import java.util.List;

/**
 * Defines an environment used by a learning algorithm in order to discover the 
 * available states, actions and return values (rewards) from actions.
 * 
 * @author Alexandre Lima
 */
public interface Environment {
    
    /**
     * Returns the actions available from the current state of this environment.
     * @return 
     */
    public List<String> getAvailableActions();
    
    /**
     * Returns the current state of this environment.
     * @return 
     */
    public String getCurrentState();
    
    /**
     * Performs an action on this environment.
     * @param action
     * @return true if the input action is valid for the current state.
     */
    public boolean performAction(String action);
    
    /**
     * Returns the return value for an action that can be performed on the 
     * inputed state.
     * @param action
     * @return 
     */
    public double getReturnValueForAction(String state, String action);
    
    /**
     * Restarts this environment. Note that for some algorithms the initial state 
     * must be selected at random.
     */
    public void reset();
    
}
