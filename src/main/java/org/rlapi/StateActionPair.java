package org.rlapi;

import java.util.Objects;

/**
 *
 * @author Alexandre Lima
 */
public class StateActionPair {
    
    private final String state;
    private final String action;

    public StateActionPair(String state, String action) {
        this.state = state;
        this.action = action;
    }

    public String getState() {
        return state;
    }

    public String getAction() {
        return action;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.action);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StateActionPair other = (StateActionPair) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return Objects.equals(this.action, other.action);
    }

}
