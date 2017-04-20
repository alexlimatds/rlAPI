package org.rlapi.maze;

import java.util.List;
import java.util.Random;
import org.rlapi.Environment;

/**
 * This class represents the 4x3 maze described in the book Artificial 
 * Intelligence: A Modern Approach, 3rd ed, written by S. Russell and N. Peter.
 *
 * @author Alexandre Lima
 */
public class Maze4x3 implements Environment{
    
    private GenericMaze maze;

    public Maze4x3() {
        Place p0 = new Place("0", false);
        Place p1 = new Place("1", false);
        Place p2 = new Place("2", false);
        Place p3 = new Place("3", true);
        Place p4 = new Place("4", false);
        Place p6 = new Place("6", false); //there is no place with id 5
        Place p7 = new Place("7", true);
        Place p8 = new Place("8", false);
        Place p9 = new Place("9", false);
        Place p10 = new Place("10", false);
        Place p11 = new Place("11", false);
        p0.addNeighbour(p1, "E"); //E = east
        p0.addNeighbour(p4, "S"); //S = south
        p1.addNeighbour(p0, "W"); //W = west
        p1.addNeighbour(p2, "E");
        p2.addNeighbour(p1, "W");
        p2.addNeighbour(p3, "E");
        p2.addNeighbour(p6, "S");
        p4.addNeighbour(p0, "N"); //N = north
        p4.addNeighbour(p8, "S");
        p6.addNeighbour(p2, "N");
        p6.addNeighbour(p7, "E");
        p6.addNeighbour(p10, "S");
        p8.addNeighbour(p4, "N");
        p8.addNeighbour(p9, "E");
        p9.addNeighbour(p8, "W");
        p9.addNeighbour(p10, "E");
        p10.addNeighbour(p9, "W");
        p10.addNeighbour(p11, "E");
        p10.addNeighbour(p6, "N");
        p11.addNeighbour(p7, "N");
        p11.addNeighbour(p10, "W");
        maze = new GenericMaze();
        maze.addPlaces(p0, p1, p2, p3, p4, p6, p7, p8, p9, p10, p11);
        reset();
    }

    @Override
    public List<String> getAvailableActions() {
        return maze.getCurrentPlace().getActions();
    }

    @Override
    public String getCurrentState() {
        return maze.getCurrentPlace().getId();
    }

    @Override
    public Double performAction(String action) {
        Place destination = maze.getCurrentPlace().getDestinationFor(action);
        if(destination == null)
            throw new IllegalArgumentException("Invalid action");
        maze.setCurrentPlace(destination.getId());
        if("3".equals(destination.getId()))
            return 1.0;
        else if("7".equals(destination.getId()))
            return -1.0;
        return 0.0;
    }

    @Override
    public void reset() {
        Random rand = new Random();
        do{
            int k = rand.nextInt(11);
            if(k != 3 && k != 7 && k != 5){
                maze.setCurrentPlace(String.valueOf(k));
                break;
            }
        }while(true);
    }

    @Override
    public boolean isInTerminalState() {
        return maze.getCurrentPlace().isTerminal();
    }

    @Override
    public List<String> getStateActions(String state) {
        return maze.getCurrentPlace().getActions();
    }
}
