package org.rlapi;

import java.util.Map;
import java.util.Set;

/**
 * Defines the operations for an object used by the learning algorithms to 
 * get the tables and other data structures for information store.
 * 
 * @author Alexandre Lima
 */
public interface TableBuilder {
    
    ActionValueTable createActionValueTable();
    
    <K, V> Map<K, V> createMap();

    <T> Set<T> createSet();
    
}
