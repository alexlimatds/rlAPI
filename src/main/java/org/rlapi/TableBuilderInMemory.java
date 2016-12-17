package org.rlapi;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class must be used in order to create data structures which 
 * stores their information in RAM memory.
 * 
 * @author Alexandre Lima
 */
public class TableBuilderInMemory implements TableBuilder{

    @Override
    public ActionValueTable createActionValueTable() {
        return new ActionValueTableInMemory();
    }

    @Override
    public <K, V> Map<K, V> createMap() {
        return new HashMap<>();
    }
    
}
