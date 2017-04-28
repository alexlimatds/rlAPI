package org.rlapi;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Alexandre Lima
 */
public class EGreedyPolicy implements Policy{
    
    private final Environment environment;
    private double epsilon;
    private final Random rand = new Random();
    private final ActionValueTable actionValueTable;

    public EGreedyPolicy(double epsilon, Environment environment, ActionValueTable table) {
        this.environment = environment;
        this.epsilon = epsilon;
        this.actionValueTable = table;
    }
    
    @Override
    public String selectAction() {
        List<String> actions = environment.getAvailableActions();
        String currentState = environment.getCurrentState();
        String bestAction = actionValueTable.getBestActions(currentState).get(0);
        if(actions.size() > 1){
            if(rand.nextDouble() >= 1.0 - epsilon){
                //exploitation: select best action
                return bestAction;
            }
            else{
                //exploration: select other action than the best
                actions.remove(bestAction);
                int i = rand.nextInt(actions.size());
                return actions.get(i);
            }
        }
        return bestAction;
    }
    
}
