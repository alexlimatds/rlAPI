package org.rlapi;

import java.util.List;
import java.util.Map;

/**
 * Implementation of QLearning algorithm as described in 
 * the book Reinforcement Learning: An Introduction from R. S. Sutton and 
 * A. G. Barton.
 * 
 * @author Alexandre Lima
 */
public class QLearning {
    private final Environment environment;
    private final ActionValueTable actionValueTable;
    private final TableBuilder tableBuilder;
    

    public QLearning(Environment environment, TableBuilder tableBuilder) {
        this.environment = environment;
        this.tableBuilder = tableBuilder;
        this.actionValueTable = tableBuilder.createActionValueTable();
    }
    
    /**
     * Starts the learning process.
     * @param tMax the number of algorithm's iterations.
     * @param alpha the learning rate. Must be a value between 0 and 1.
     * @param gama  the discount factor. Must be a value between 0 and 1.
     * @param policy policy used to choose the actions through the tranning.
     * @return the generated policy, i. e., a state-action map that keeps the best action for a state.
     */
    public Map<String, String> train(int tMax, double alpha, double gama, Policy policy){
        for(int i = 0; i < tMax; i++){
            while(environment.isInTerminalState()){ //reached a terminal state
                environment.reset();
            }
            
            String state1 = environment.getCurrentState(); //previous state
            initializeStateQValues();
            String choosenAction = policy.selectAction();
            Double returnValue = environment.performAction(choosenAction);
            String state2 = environment.getCurrentState(); //following state
            initializeStateQValues();
            
            if(returnValue == null){ //The action wasn't performed, so there is an implementation bug
                throw new IllegalStateException("Action not performed: there is some BUG");
            }
            //calculates the Q delta value
            double state2BestQ = 0.0;
            if(!environment.isInTerminalState()){
                String state2BestAction = actionValueTable.getBestActions(state2).get(0);
                state2BestQ = actionValueTable.getValue(state2, state2BestAction);
            }
            double state1Q = actionValueTable.getValue(state1, choosenAction);
            double delta = alpha * (returnValue + gama * state2BestQ - state1Q);
            actionValueTable.putValue(state1, choosenAction, state1Q + delta);
            
        }
        
        Map<String, String> policyFromTrainning = tableBuilder.createMap();
        actionValueTable.getStates()
                .forEach(state -> policyFromTrainning.put(state, actionValueTable.getBestActions(state).get(0)));
        return policyFromTrainning;
    }
    
    public ActionValueTable getActionValueTable(){
        return actionValueTable;
    }
    
    private void initializeStateQValues(){
        String state = environment.getCurrentState();
        if(!actionValueTable.getStates().contains(state)){
            List<String> actions = environment.getAvailableActions();
            actions.forEach(action -> actionValueTable.putValue(state, action, 0.0));
        }
    }
}
