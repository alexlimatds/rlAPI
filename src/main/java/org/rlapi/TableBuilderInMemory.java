package org.rlapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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

    @Override
    public <T> Set<T> createSet() {
        return new HashSet<>();
    }
    
}
