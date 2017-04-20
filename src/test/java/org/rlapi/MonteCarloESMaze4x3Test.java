package org.rlapi;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.rlapi.maze.Maze4x3;

/**
 * Validation of MonteCarloES agent.
 * 
 * @author Alexandre Lima
 */
public class MonteCarloESMaze4x3Test {
    
   @Test
   public void testTrain(){
       Maze4x3 maze = new Maze4x3();
       MonteCarloES agent = new MonteCarloES(maze, new TableBuilderInMemory());
       Map<String, String> policy = agent.train(10);
       
       Assert.assertEquals("E", policy.get("1"));
       Assert.assertEquals("E", policy.get("2"));
       Assert.assertEquals("N", policy.get("6"));
       
       Maze4x3Printer.print(policy, agent.getActionValueTable());
   }
}
