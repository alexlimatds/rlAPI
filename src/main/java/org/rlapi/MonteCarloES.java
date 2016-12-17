package org.rlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Monte Carlo with Exploring Starts algorithm described in 
 * the book Reinforcement Learning: An Introduction from R. S. Sutton and A. G. Barton.
 * 
 * @author Alexandre Lima
 */
public class MonteCarloES {
    private final Environment environment;
    private final ActionValueTable actionValueTable;
    private final TableBuilder tableBuilder;

    public MonteCarloES(Environment environment, TableBuilder tableBuilder) {
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
        
        Map<String, String> visitedPairs = tableBuilder.createMap(); //keeps the state-action pairs visited in the episode
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
                    //Choose an action randomly
                    int j = (int)Math.round(0 + Math.random() * (actions.size() - 1));
                    action = actions.get(j);
                    episodeStart = false;
                }
                else{
                    if(policy.containsKey(currentState)){ //choices the action according to the policy
                        action = policy.get(currentState);
                    }
                    else{
                        //According to the policy there is no action for the state, so it picks a random action
                        int j = (int)Math.round(0 + Math.random() * (actions.size() - 1));
                        action = actions.get(j);
                    }
                }
                boolean ok = environment.performAction(action);
                if(ok){ //the action was performed
                    visitedPairs.put(currentState, action);
                }
                else{ //the action wasn't performed, so there is an implementation bug
                    throw new IllegalStateException("Action not performed: there is some BUG");
                }
                
                actions = environment.getAvailableActions();
            }
            
            //Goes through the state-action pairs visited during the episode
            for(String state : visitedPairs.keySet()){
                String action = visitedPairs.get(state);
                double returnValue = environment.getReturnValueForAction(state, action);
                String key = state + ";" + action;
                List<Double> returnsList = stateActionReturnValues.get(key);
                if(returnsList != null){//checks if there is a return list for the state-action pair already
                    stateActionReturnValues.get(key).add(returnValue);
                }
                else{//There is no list, so it creates one and it puts the return value
                    returnsList = new ArrayList<>();
                    returnsList.add(returnValue);
                    stateActionReturnValues.put(key, returnsList);
                }
                //Gets the average return of the state-action pair
                double avg = 0;
                for(Double d : returnsList){
                    avg += d;
                }
                avg = avg / returnsList.size();
                //updates the value of the state-action pair
                actionValueTable.putValue(state, action, avg);
                //Updates the policy with the best action
                policy.put(state, actionValueTable.getBestActions(state).get(0));
            }
        }
        
        return policy;
    }
    
    public ActionValueTable getActionValueTable(){
        return actionValueTable;
    }
}
