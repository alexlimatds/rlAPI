package org.rlapi;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.rlapi.maze.Maze4x3;

/**
 * Validation of QLearning agent.
 * 
 * @author Alexandre Lima
 */
public class QLearningMaze4x3Test {
    
   @Test
   public void testTrain(){
       Maze4x3 maze = new Maze4x3();
       QLearning agent = new QLearning(maze, new TableBuilderInMemory());
       Policy trainningPolicy = new EGreedyPolicy(0.25, maze, agent.getActionValueTable());
       Map<String, String> policy = agent.train(200, 0.1, 0.95, trainningPolicy);
       
       Assert.assertEquals("E", policy.get("1"));
       Assert.assertEquals("E", policy.get("2"));
       Assert.assertEquals("N", policy.get("6"));
       
       Maze4x3Printer.print(policy, agent.getActionValueTable());
   }
}
