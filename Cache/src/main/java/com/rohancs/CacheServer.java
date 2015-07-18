package com.rohancs;

import java.util.Properties;
import com.rohancs.tools.BaseCache;

public class CacheServer extends BaseCache<String,String> implements CacheServerMBean {

    public CacheServer(Properties properties) {
        super(properties);
    }

    @Override
    public String load(String key) {
        return null;
    }

}
