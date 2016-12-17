package org.rlapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An instance of this class keeps the action's values in RAM memory.
 * 
 * @author Alexandre Lima
 */
public class ActionValueTableInMemory implements ActionValueTable {
    
    private final Map<String, Map<String, Double>> table = new HashMap<>();
    
    /**
     * Returns the value (Q) associated to an specific state-action pair.
     * @param state the state identifier.
     * @param action the action identifier.
     * @return
     */
    @Override
    public Double getValue(String state, String action){
        Map<String, Double> map = table.get(state);
        return (map != null ? map.get(action) : null);
    }
    
    /**
     * Returns the actions with the maximum value (Q) for a specific state. 
     * It will return an empty List if the state is not recognized.
     * @param state the state identifier.
     * @return
     */
    @Override
    public List<String> getBestActions(String state){
        Map<String, Double> map = table.get(state);
        if(map != null){
            Double maxValue = map.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getValue();
            List<String> listOfMax = map.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(maxValue))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            return listOfMax;
        }
        return new ArrayList<>();
    }
    
    /**
     * Inserts, or updates, a value (Q) for a specific state-action pair.
     * @param state the state identifier.
     * @param action action identifier.
     * @param value value associated to the action.
     */
    @Override
    public void putValue(String state, String action, Double value){
        Map<String, Double> map = table.get(state);
        if(map == null){
            map = new HashMap<>();
            table.put(state, map);
        }
        map.put(action, value);
    }
    
    /**
     * Returns the registered actions for a specific state. It will return an 
     * empty Set if the inputed state is not registered.
     * @param state the state identifier.
     * @return
     */
    @Override
    public Set<String> getActions(String state){
        Map<String, Double> map = table.get(state);
        return (map != null ? map.keySet() : new HashSet<>());
    }
    
    /**
     * Return the registered states.
     * @return 
     */
    @Override
    public Set<String> getStates(){
        return table.keySet();
    }
}
