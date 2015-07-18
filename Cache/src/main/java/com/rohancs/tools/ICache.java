package com.rohancs.tools;

public interface ICache<K,V> {
    public V get(K key);
    public void set(K key,V value);
    public int size();
}
