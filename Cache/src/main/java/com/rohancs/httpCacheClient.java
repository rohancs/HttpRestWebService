package com.rohancs;

import javax.annotation.ManagedBean;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ManagedBean
@Path("/")
public  class httpCacheClient {
    final static int JMX_PORT = 8009;
    final static String JMX_HOST = "localhost";
    CacheServerMBean cacheProxy = null;

    public httpCacheClient(){
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+JMX_HOST+":"+JMX_PORT+"/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            ObjectName cacheServer = new ObjectName("com.rohancs:type=CacheServer");
            cacheProxy = JMX.newMBeanProxy(mbsc, cacheServer, CacheServerMBean.class, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @GET
    @Path("get/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getValue(@PathParam("key") String key) {
        String value = null;
        try{
            value = cacheProxy.get(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(value == null) value = "null";
        return value;
    }

    @GET
    @Path("set/{key}/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public String setValue(@PathParam("key") String key,@PathParam("value") String value) {
        try{
            cacheProxy.set(key, value);
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }

        return "true";
    }

    @GET
    @Path("size")
    @Produces(MediaType.TEXT_PLAIN)
    public int getCacheSize() {
        int size = -1;
        try{
            size = cacheProxy.size();
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
}
