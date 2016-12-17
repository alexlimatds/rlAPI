package org.rlapi;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rlapi.maze.Maze;
import org.rlapi.maze.Place;

/**
 * @author Alexandre Lima
 */
public class MonteCarloESTest {
    
    private Maze maze;
    
    @Before
    public void setUp(){
        Place p1 = new Place("1", false);
        Place p2 = new Place("2", false);
        Place p3 = new Place("3", false);
        Place p4 = new Place("4", true);
        Place p5 = new Place("5", false);
        Place p6 = new Place("6", false);
        p1.addNeighbour(p2, "east");
        p2.addNeighbour(p3, "east");
        p3.addNeighbour(p4, "east");
        p3.addNeighbour(p5, "north");
        p3.addNeighbour(p6, "south");
        maze = new Maze();
        maze.addPlaces(p1, p2, p3, p4, p5, p6);
    }
    
    @Test
    public void testTestTrain(){
        MonteCarloES rlAgent = new MonteCarloES(maze, new TableBuilderInMemory());
        Map<String, String> policy = rlAgent.train(200);
        Assert.assertEquals("east", policy.get("1"));
        Assert.assertEquals("east", policy.get("2"));
        Assert.assertEquals("east", policy.get("3"));
    }
}