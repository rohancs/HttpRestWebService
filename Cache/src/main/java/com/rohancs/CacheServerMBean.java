package com.rohancs;

/**
 * Created by ksainani on 7/18/2015.
 */
public interface CacheServerMBean {
    public String get(String key);
    public void set(String key,String value);
    public int size();
}
