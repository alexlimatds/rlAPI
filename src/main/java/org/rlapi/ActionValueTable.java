package org.rlapi;

import java.util.List;
import java.util.Set;

/**
 * Defines the operations for a table that stores action's values for all 
 * available states.
 * 
 * @author Alexandre Lima
 */
public interface ActionValueTable {

    /**
     * Returns the registered actions for a specific state. It must return an
     * empty Set if the inputed state is not registered.
     * @param state the state identifier.
     * @return
     */
    Set<String> getActions(String state);

    /**
     * Returns the actions with the maximum value (Q) for a specific state.
     * It must return an empty List if the state is not recognized.
     * @param state the state identifier.
     * @return
     */
    List<String> getBestActions(String state);

    /**
     * Returns the registered states.
     * @return
     */
    Set<String> getStates();

    /**
     * Returns the value (Q) associated to a specific state-action pair. It 
     * must return null if one of the arguments is not recognized.
     * @param state the state identifier.
     * @param action the action identifier.
     * @return
     */
    Double getValue(String state, String action);

    /**
     * Inserts, or updates, a value (Q) for a specific state-action pair.
     * @param state the state identifier.
     * @param action action identifier.
     * @param value value associated to the action.
     */
    void putValue(String state, String action, Double value);
    
}
