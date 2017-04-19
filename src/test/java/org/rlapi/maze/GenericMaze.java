package org.rlapi.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The purpose of this class is to act as foundation to the creation of 
 * individual mazes.
 * 
 * @author Alexandre Lima
 */
public class GenericMaze {
    
    private final List<Place> places = new ArrayList<>();
    private Place currentPlace;
    
    public void addPlaces(Place... newPlaces){
        places.addAll(Arrays.asList(newPlaces));
    }
    
    public Place getCurrentPlace(){
        if(currentPlace == null)
            throw new IllegalStateException("Current place is null");
        return currentPlace;
    }
    
    public void setCurrentPlace(String placeId){
        Optional<Place> place = getPlace(placeId);
        if(!place.isPresent())
            throw new IllegalArgumentException("There is no place with that id");
        this.currentPlace = place.get();
    }
    
    private Optional<Place> getPlace(String placeId){
        return places.stream()
                .filter(p -> p.getId().equals(placeId))
                .findAny();
    }
}
