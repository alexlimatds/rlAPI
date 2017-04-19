package org.rlapi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.rlapi.maze.Maze;
import org.rlapi.maze.Place;

/**
 * @author Alexandre Lima
 */
public class MonteCarloESTest {
    
    private Maze createSimpleMaze(){
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
        Maze maze = new Maze();
        maze.addPlaces(p1, p2, p3, p4, p5, p6);
        return maze;
    }
    
    @Test
    public void testTrain(){
        MonteCarloES rlAgent = new MonteCarloES(createSimpleMaze(), new TableBuilderInMemory());
        Map<String, String> policy = rlAgent.train(200);
        Assert.assertEquals("east", policy.get("1"));
        Assert.assertEquals("east", policy.get("2"));
        Assert.assertEquals("east", policy.get("3"));
    }
    
    @Test
    public void testComputeReturnsAverage(){
        //*** configuration of data ***
        StateActionPair pair1 = new StateActionPair("s1", "a1");
        StateActionPair pair2 = new StateActionPair("s1", "a2");
        StateActionPair pair3 = new StateActionPair("s2", "a3");
        
        Set<StateActionPair> visitedActionPairs = new HashSet<>();
        visitedActionPairs.add(pair1);
        visitedActionPairs.add(pair2);
        visitedActionPairs.add(pair3);
        
        Map<StateActionPair, List<Double>> mapOfReturnsList = new HashMap<>();
        mapOfReturnsList.put(pair1, Arrays.asList(2.0, 3.0, 7.0)); //avg -> 4.0
        Double pair1Avg = 4.0;
        mapOfReturnsList.put(pair2, Arrays.asList(7.0));           //avg -> 7.0
        Double pair2Avg = 7.0;
        mapOfReturnsList.put(pair3, Arrays.asList(2.0, 3.0));      //avg -> 2.5
        Double pair3Avg = 2.5;
        
        MonteCarloES mc = new MonteCarloES(null, new TableBuilderInMemory());
        ActionValueTable valueTable = mc.getActionValueTable();
        valueTable.putValue(pair1.getState(), pair1.getAction(), 0.0);
        //omiting the pair2
        valueTable.putValue(pair3.getState(), pair3.getAction(), 2.0);
        
        //*** performing method ***
        mc.computeReturnsAverage(visitedActionPairs, mapOfReturnsList);
        
        //*** checking results ***
        Assert.assertEquals(pair1Avg, valueTable.getValue(pair1.getState(), pair1.getAction()), 0.00001);
        Assert.assertEquals(pair2Avg, valueTable.getValue(pair2.getState(), pair2.getAction()), 0.00001);
        Assert.assertEquals(pair3Avg, valueTable.getValue(pair3.getState(), pair3.getAction()), 0.00001);
    }
    
    @Test
    public void testUpdatePolicy(){
        //*** Data preparation ***
        MonteCarloES mc = new MonteCarloES(null, new TableBuilderInMemory());

        ActionValueTable valueTable = mc.getActionValueTable();
        valueTable.putValue("s1", "a1", 53.6);
        valueTable.putValue("s1", "a2", 88.9); //best action for s1
        valueTable.putValue("s1", "a3", 75.6);
        valueTable.putValue("s2", "a4", 22.8); //best action for s2
        valueTable.putValue("s2", "a5", 18.6);
        valueTable.putValue("s3", "a6", 2.7);  //best action for s3
        valueTable.putValue("s3", "a7", 0.6);
        valueTable.putValue("s4", "a8", 1.6);
        valueTable.putValue("s4", "a9", 9.0);  //best action for s4
        
        Set<StateActionPair> visitedPairs = new HashSet<>();
        visitedPairs.add(new StateActionPair("s1", "a1"));
        visitedPairs.add(new StateActionPair("s1", "a2"));
        visitedPairs.add(new StateActionPair("s1", "a3"));
        visitedPairs.add(new StateActionPair("s2", "a5"));
        visitedPairs.add(new StateActionPair("s4", "a8"));
        visitedPairs.add(new StateActionPair("s4", "a9"));
        
        Map<String, String> policy = new HashMap<>();
        policy.put("s1", "a3");
        policy.put("s2", "a4");
        policy.put("s3", "a6");
        
        //*** Performing method ***
        mc.updatePolicy(visitedPairs, policy);
        
        //*** Checking results ***
        Assert.assertEquals("a2", policy.get("s1"));
        Assert.assertEquals("a4", policy.get("s2"));
        Assert.assertEquals("a6", policy.get("s3"));
        Assert.assertEquals("a9", policy.get("s4"));
    }
}