package org.rlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
        Map<String, String> policy = tableBuilder.createMap();
        //Lists of return values from state-action pairs
        Map<StateActionPair, List<Double>> stateActionReturnValues = tableBuilder.createMap();
        //Keeps the state-action pairs visited in the episode
        Set<StateActionPair> visitedPairs = tableBuilder.createSet();
        
        for(int i = 0; i < episodes; i++){
            environment.reset(); //The environment's reset must generate a random initial state
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
                if(returnValue != null){ //The action was performed
                    //If is the first occurrence of this state-action pair, register its return and its occurrence
                    StateActionPair stateActionPair = new StateActionPair(currentState, action);
                    if(!visitedPairs.contains(stateActionPair)){
                        registerReturnValue(stateActionPair, returnValue, stateActionReturnValues);
                        visitedPairs.add(stateActionPair);
                    }
                }
                else{ //The action wasn't performed, so there is an implementation bug
                    throw new IllegalStateException("Action not performed: there is some BUG");
                }
                
                actions = environment.getAvailableActions();
            }
            
            //Computes the average return of each state-action visited during the episode
            computeReturnsAverage(visitedPairs, stateActionReturnValues);
            
            //Updates the policy with the best action
            updatePolicy(visitedPairs, policy);
        }
        
        return policy;
    }
    
    void updatePolicy(Set<StateActionPair> visitedActionPairs, 
            Map<String, String> policy){
        
        Set<String> visitedStates = visitedActionPairs.stream()
                .map(pair -> pair.getState())
                .collect(Collectors.toSet());
        
        visitedStates.stream().forEach((state) -> {
            policy.put(state, actionValueTable.getBestActions(state).get(0));
        });
        //OBS: maybe this code would be better to put this code in the 
        //computeReturnsAverage method in orde to evict the iteration over 
        //the visitedStates
    }
    
    void computeReturnsAverage(Set<StateActionPair> visitedActionPairs, 
            Map<StateActionPair, List<Double>> mapOfReturnsList){
        for(StateActionPair pair : visitedActionPairs){
            List<Double> returnsList = mapOfReturnsList.get(pair);
            double avg = 0.0;
            for(Double d : returnsList){
                avg += d;
            }
            avg = avg / returnsList.size();
            //Updates the value of the state-action pair
            actionValueTable.putValue(pair.getState(), pair.getAction(), avg);
        }
    }
    
    private void registerReturnValue(StateActionPair stateActionPair, Double returnValue, 
            Map<StateActionPair, List<Double>> returnsListMap){
        List<Double> returnsList = returnsListMap.get(stateActionPair);
        if(returnsList == null){//There is no return list for this state-action pair, so creates one
            returnsList = new ArrayList<>();
            returnsListMap.put(stateActionPair, returnsList);
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
