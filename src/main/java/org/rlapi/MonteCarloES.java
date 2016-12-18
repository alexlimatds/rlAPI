package org.rlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of Monte Carlo with Exploring Starts algorithm described in 
 * the book Reinforcement Learning: An Introduction from R. S. Sutton and A. G. Barton. 
 * Here are described important notes about this algorithm.
 * First, the algorithm is based on episodes. So, the environment must lead the 
 * episode for an end, i. e., a situation where no more actions are available. If 
 * this first condition is not assured, the training phase will can enter in an 
 * infinity loop.
 * Second, the environment must ensure the exploring starts condition. This means 
 * that every new episode, the algorithm will reset the environment, whose initial 
 * state must be randomly selected. If the second condition is not assured, the 
 * algorithm's convergence won't be assured.
 * 
 * @author Alexandre Lima
 */
public class MonteCarloES {
    private final Environment environment;
    private final ActionValueTable actionValueTable;
    private final TableBuilder tableBuilder;
    private final Random rand;

    public MonteCarloES(Environment environment, TableBuilder tableBuilder) {
        rand = new Random();
        this.environment = environment;
        this.tableBuilder = tableBuilder;
        this.actionValueTable = tableBuilder.createActionValueTable();
    }
    
    /**
     * Starts the learning process.
     * @param episodes the number of episodes, i. e., the number of algorithm's iterations.
     * @return the generated policy, i. e., a state-action map that keeps the best action for a state.
     */
    public Map<String, String> train(int episodes){
        //final policy generated
        Map<String, String> policy = tableBuilder.createMap();
        //Lists of return values from state-action pairs. The entry's key is the 
        //string junction of state and action separated by a comma.
        Map<String, List<Double>> stateActionReturnValues = tableBuilder.createMap();
        //keeps the state-action pairs visited in the episode
        Map<String, String> visitedPairs = tableBuilder.createMap();
        
        for(int i = 0; i < episodes; i++){
            environment.reset();
            List<String> actions = environment.getAvailableActions();
            visitedPairs.clear();
            //performs actions based on the policy if there are available actions
            boolean episodeStart = true;
            while(actions.size() > 0){
                String currentState = environment.getCurrentState();
                String action;
                if(episodeStart){
                    action = selectActionAtRandom(actions);
                    episodeStart = false;
                }
                else{
                    if(policy.containsKey(currentState)){ //choices the action according to the policy
                        action = policy.get(currentState);
                    }
                    else{
                        //According to the policy there is no action for the state, so it picks a random action
                        action = selectActionAtRandom(actions);
                    }
                }
                Double returnValue = environment.performAction(action);
                if(returnValue != null){ //the action was performed, so register its return and the occurrence of state-action
                    visitedPairs.put(currentState, action);
                    registerReturnValue(currentState, action, returnValue, stateActionReturnValues);
                }
                else{ //the action wasn't performed, so there is an implementation bug
                    throw new IllegalStateException("Action not performed: there is some BUG");
                }
                
                actions = environment.getAvailableActions();
            }
            
            //Gets the average return of each state-action visited during the episode
            for(String state : visitedPairs.keySet()){
                String action = visitedPairs.get(state);
                List<Double> returnsList = stateActionReturnValues.get(state + ";" + action);
                double avg = 0;
                for(Double d : returnsList){
                    avg += d;
                }
                avg = avg / returnsList.size();
                //Updates the value of the state-action pair
                actionValueTable.putValue(state, action, avg);
                //Updates the policy with the best action
                policy.put(state, actionValueTable.getBestActions(state).get(0));
            }
        }
        
        return policy;
    }
    
    private void registerReturnValue(String state, String action, Double returnValue, 
            Map<String, List<Double>> returnsListMap){
        String key = state + ";" + action;
        List<Double> returnsList = returnsListMap.get(key);
        if(returnsList == null){//There is no return list for this state-action pair, so create one
            returnsList = new ArrayList<>();
            returnsList.add(returnValue);
            returnsListMap.put(key, returnsList);
        }
        returnsList.add(returnValue);
    }
    
    private String selectActionAtRandom(List<String> actions){
        int i = rand.nextInt(actions.size());
        return actions.get(i);
    }
    
    public ActionValueTable getActionValueTable(){
        return actionValueTable;
    }
}
