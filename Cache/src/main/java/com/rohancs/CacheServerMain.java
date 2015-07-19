package com.rohancs;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

public class CacheServerMain {
    final static String CACHE_PROPERTIES = System.getProperty("user.dir")+"\\Cache\\src\\main\\resources\\cache.properties";

    static Properties  getCacheProperties(){
        Properties prop = new Properties();

        try{
            InputStream inputStream = new FileInputStream(CACHE_PROPERTIES);
            prop.load(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }

        return prop;
    }

    public static void main(String[] args) throws Exception {

        //Instantiate Cache
        CacheServer cacheServer = new CacheServer(getCacheProperties());

        //Adding Cache to MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.rohancs:type=CacheServer");
        mbs.registerMBean(cacheServer, name);

        System.out.println("com.sun.management.jmxremote.port : "+System.getProperty("com.sun.management.jmxremote.port"));
        System.out.println("com.sun.management.jmxremote.authenticate : "+System.getProperty("com.sun.management.jmxremote.authenticate"));
        System.out.println("com.sun.management.jmxremote.ssl : "+System.getProperty("com.sun.management.jmxremote.ssl"));

        //Keep Server Alive
        Thread.sleep(Long.MAX_VALUE);
    }
}
