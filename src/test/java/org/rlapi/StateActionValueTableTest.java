package org.rlapi;

import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Alexandre
 */
public class StateActionValueTableTest {
    
    @Test
    public void testGetStates(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        String stateA = "A";
        String stateB = "B";
        table.putValue(stateA, "action", 3.0);
        table.putValue(stateB, "aaction", 8.0);
        Set<String> states = table.getStates();
        Assert.assertTrue(states.contains(stateA));
        Assert.assertTrue(states.contains(stateB));
    }
    
    @Test
    public void testGetValue(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        String stateA = "s1";
        String stateB = "s2";
        String actionA = "a1";
        Double actionAValue = 5.0;
        String actionB = "a2";
        Double actionBValue = 7.0;
        String actionC = "a3";
        Double actionCValue = 1.0;
        table.putValue(stateA, actionA, actionAValue);
        table.putValue(stateA, actionB, actionBValue);
        table.putValue(stateB, actionC, actionCValue);
        
        Assert.assertEquals(actionAValue, table.getValue(stateA, actionA), 0.0001);
        Assert.assertEquals(actionBValue, table.getValue(stateA, actionB), 0.0001);
        Assert.assertEquals(actionCValue, table.getValue(stateB, actionC), 0.0001);
    }
    
    @Test
    public void testGetActions_1(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        String stateA = "s1";
        String stateB = "s2";
        String actionA = "a1";
        String actionB = "a2";
        String actionC = "a3";
        table.putValue(stateA, actionA, 5.0);
        table.putValue(stateA, actionB, 7.0);
        table.putValue(stateB, actionA, 9.0);
        table.putValue(stateB, actionC, 1.0);
        
        Set<String> stateAActions = table.getActions(stateA);
        Assert.assertEquals(2, stateAActions.size());
        Assert.assertTrue(stateAActions.contains(actionA));
        Assert.assertTrue(stateAActions.contains(actionB));
        
        Set<String> stateABctions = table.getActions(stateB);
        Assert.assertEquals(2, stateABctions.size());
        Assert.assertTrue(stateABctions.contains(actionA));
        Assert.assertTrue(stateABctions.contains(actionC));
    }
    
    @Test
    public void testGetActions_2(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        
        Set<String> stateAActions = table.getActions("state");
        Assert.assertNotNull(stateAActions);
        Assert.assertTrue(stateAActions.isEmpty());
        
    }
    
    @Test
    public void testGetBestActions_1(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        table.putValue("s1", "a1", 3.0);
        table.putValue("s1", "a2", 8.0);
        List<String> maxActions = table.getBestActions("s1");
        Assert.assertEquals(1, maxActions.size());
        Assert.assertTrue(maxActions.contains("a2"));
    }
    
    @Test
    public void testGetBestActions_2(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        table.putValue("s1", "a1", 8.0);
        table.putValue("s1", "a2", 2.0);
        table.putValue("s1", "a3", 8.0);
        List<String> maxActions = table.getBestActions("s1");
        Assert.assertEquals(2, maxActions.size());
        Assert.assertTrue(maxActions.contains("a1"));
        Assert.assertTrue(maxActions.contains("a3"));
    }
    
    @Test
    public void testGetBestActions_3(){
        ActionValueTableInMemory table = new ActionValueTableInMemory();
        table.putValue("s1", "a1", 8.0);
        table.putValue("s1", "a2", 2.0);
        List<String> maxActions = table.getBestActions("s2");
        Assert.assertTrue(maxActions.isEmpty());
    }
}
