package org.rlapi.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexandre Lima
 */
public class Place {
    private final String id;
    private final Map<String, Place> actionPlaceMap = new HashMap<>();
    private final boolean terminal;

    public Place(String id, boolean terminal) {
        this.id = id;
        this.terminal = terminal;
    }

    public String getId() {
        return id;
    }
    
    public List<String> getActions() {
        return new ArrayList(actionPlaceMap.keySet());
    }

    public void addNeighbour(Place place, String action){
        if(place == null || action== null)
            throw new IllegalArgumentException("Both arguments can not be null");
        actionPlaceMap.put(action, place);
    }

    public boolean isTerminal() {
        return terminal;
    }
    
    public Place getDestinationFor(String action){
        return actionPlaceMap.get(action);
    }
}
