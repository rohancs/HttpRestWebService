package com.rohancs.tools;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.instrument.Instrumentation;
import java.util.*;

public abstract class BaseCache<K,V> implements ICache<K,V> {
    HashMap<K,V> data = null;
    TreeMap<Long,K> lru = null;
    HashMap<K,Long> metadata = null;
    int cacheDuration = Integer.MAX_VALUE
            ,cacheSizeBytes=Integer.MAX_VALUE
            ,cacheSizeElements=Integer.MAX_VALUE;
    static Instrumentation instrumentation;

    public BaseCache(Properties properties) {
        data = new HashMap<K, V>();
        lru = new TreeMap<Long, K>();
        metadata = new HashMap<K,Long>();

        try {
            cacheDuration = Integer.parseInt(properties.getProperty("cacheDuration"));
            cacheSizeBytes = Integer.parseInt(properties.getProperty("cacheSizeBytes"));
            cacheSizeElements = Integer.parseInt(properties.getProperty("cacheSizeElements"));
        }
        catch (Exception e){
            System.out.println("No Cache settings specified....using defaults");

            //Default Values
            cacheDuration = 0;
            cacheSizeBytes=Integer.MAX_VALUE;
            cacheSizeElements=Integer.MAX_VALUE;
        }
    }

    @Override
    final public V get(K key){
        V val = data.get(key);

        if(data.get(key) == null || refreshCacheEntry(key)) {
            val = load(key);
            if(val != null)
                insert(key,val);
        }
        else
            val = data.get(key);

        return val;
    }

    final public void set(K key,V value){
        insert(key,value);
    }

    final private boolean refreshCacheEntry(K key){
        return (Calendar.getInstance().getTimeInMillis() - metadata.get(key)) > cacheDuration;
    }

    final private long size(Object o) {
        ByteArrayOutputStream bo = null;
        ObjectOutput out=null;
        try {
            bo = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bo);
            out.writeObject(o);
            return bo.toByteArray().length;
        }catch (Exception e){
            e.printStackTrace();
            try {
                out.close();
                bo.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return 0;
    }

    final private void insert(K key,V value) {
        while(cacheSizeElements > 0 && data.size() >= cacheSizeElements) {
            K k = lru.get(lru.firstKey());
            data.remove(k);
            metadata.remove(k);
            lru.remove(lru.firstKey());
        }

        long keySize = size(key);
        long valueSize = size(value);
        while( cacheSizeBytes > 0 && size(data) + keySize + valueSize >= cacheSizeBytes) {
            if(data.size() == 0) {
                System.out.println("Cache Size too low. Unable to accomodate Key/Value");
                return;
            }
            else {
                K k = lru.get(lru.firstKey());
                data.remove(k);
                metadata.remove(k);
                lru.remove(lru.firstKey());
            }
        }

        // Insert Data & Metadata
        Long timestamp = Calendar.getInstance().getTimeInMillis();
        data.put(key,value);
        lru.put(timestamp, key);
        metadata.put(key, timestamp);
    }

    final public int size(){
        int size = 0;
        for(K key : data.keySet())
            size = size + (refreshCacheEntry(key)? 0: 1);
        return size;
    }
    //Force definition of a load method
    protected abstract V load(K key);
}
