package org.rlapi;

import java.util.Map;

/**
 * Defines the operations for an object used by the learning algorithms to 
 * get the tables for information store.
 * 
 * @author Alexandre Lima
 */
public interface TableBuilder {
    
    ActionValueTable createActionValueTable();
    
    <K, V> Map<K, V> createMap();
    
}
