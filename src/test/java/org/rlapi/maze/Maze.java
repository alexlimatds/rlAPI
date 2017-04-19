package org.rlapi.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.rlapi.Environment;

/**
 *
 * @author Alexandre Lima
 */
public class Maze implements Environment{
    private final List<Place> places = new ArrayList<>();
    private Place currentPlace;
    
    public void addPlaces(Place... newPlaces){
        places.addAll(Arrays.asList(newPlaces));
    }
    
    private Place getPlace(String placeId){
        for(Place p : places){
            if(placeId.equals(p.getId()))
                return p;
        }
        return null;
    }

    @Override
    public List<String> getAvailableActions() {
        return currentPlace.getActions();
    }

    @Override
    public String getCurrentState() {
        return currentPlace.getId();
    }

    @Override
    public Double performAction(String action) {
        if(getAvailableActions().contains(action)){
            currentPlace = currentPlace.getDestinationFor(action);
            return (currentPlace.isTerminal() ? 10.0 : 0.0);
        }
        return null;
    }

    @Override
    public void reset() {
        Place selectedPlace;
        do{
            Random rand = new Random();
            int i = rand.nextInt(places.size());
            selectedPlace = places.get(i);
        }while(selectedPlace.isTerminal());
        currentPlace = selectedPlace;
    }

    @Override
    public boolean isInTerminalState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getStateActions(String state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}