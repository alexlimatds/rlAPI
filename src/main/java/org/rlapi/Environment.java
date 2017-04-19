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
     * @return the return value for the action performed if the action is allowed. 
     * In the other case, returns null.
     */
    public Double performAction(String action);
    
    /**
     * Restarts this environment. Note that for some algorithms the initial state 
     * must be selected at random.
     */
    public void reset();
    
    /**
     * Tells if this environment reached a terminal state.
     * @return 
     */
    public boolean isInTerminalState();
    
    /**
     * Returns the available actions to a particular state.
     * @param state
     * @return 
     */
    public List<String> getStateActions(String state);
}
