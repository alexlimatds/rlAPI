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
    public boolean performAction(String action) {
        if(getAvailableActions().contains(action)){
            currentPlace = currentPlace.getDestinationFor(action);
            return true;
        }
        return false;
    }

    @Override
    public double getReturnValueForAction(String state, String action) {
        Place nextPlace = getPlace(state).getDestinationFor(action);
        return (nextPlace.isTerminal() ? 10 : 0);
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
}