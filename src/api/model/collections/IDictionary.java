package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

public interface IDictionary<K, T> {
    /**
     Check if a key is defined inside the dictionary

     @param key Target key
     @return Whether the key is defined inside the dictionary
     */
    boolean isDefined(K key);

    /**
     Set the value of a key.
     if the key is already defined, it's value is overwritten.

     @param key   Target key
     @param value New value
     */
    void set(K key, T value);

    /**
     Remove the entry defined by a given key.

     @param key Target entry's key
     @return Removed value of the entry
     */
    T remove(K key) throws OutOfBoundsException;

    /**
     Get the value assigned to a given key.

     @param key Target key
     @return value assigned to the key.
     */
    T get(K key) throws OutOfBoundsException;
}
